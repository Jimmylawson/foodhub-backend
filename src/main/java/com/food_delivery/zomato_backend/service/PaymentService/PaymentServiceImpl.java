package com.food_delivery.zomato_backend.service.PaymentService;


import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentRequestDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.entity.Payment;
import com.food_delivery.zomato_backend.entity.Restaurant;
import com.food_delivery.zomato_backend.enumTypes.Status;
import com.food_delivery.zomato_backend.exceptions.PaymentNotFoundException;
import com.food_delivery.zomato_backend.exceptions.orderException.OrderNotFoundException;
import com.food_delivery.zomato_backend.exceptions.restaurantException.RestaurantNotFoundException;
import com.food_delivery.zomato_backend.mapper.OrderMapper;
import com.food_delivery.zomato_backend.mapper.PaymentMapper;
import com.food_delivery.zomato_backend.repository.OrderRepository;
import com.food_delivery.zomato_backend.repository.PaymentRepository;
import com.food_delivery.zomato_backend.service.OrderService.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServiceInterface {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderServiceInterface orderServiceInterface;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    /// Find the payment
    private Payment getPaymentOrThrowError(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    /// CRUD operations
    @Override
    public PaymentResponseDto savePayment(PaymentRequestDto payRequestDto) {
        var payment = paymentMapper.toEntity(payRequestDto);
        payment.setStatus(Status.PENDING);

        return paymentMapper.toPaymentResponseDto(paymentRepository.save(payment));

    }

    @Override
    public PaymentResponseDto getPayment(Long id) {
        var payment  = getPaymentOrThrowError(id);

        return paymentMapper.toPaymentResponseDto(payment);
    }

    @Override
    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto payRequestDto) {
        /// fetch the payment
        var payment = getPaymentOrThrowError(id);

        /// update the payment
        paymentMapper.updatePaymentFromDto(payRequestDto, payment);

        /// save the payment
        return paymentMapper.toPaymentResponseDto(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {

        paymentRepository.delete(getPaymentOrThrowError(id));

    }

    /// Update the payment status
    @Override
    public PaymentResponseDto updatePaymentStatus(Long id, Status newStatus) {
        var payment = getPaymentOrThrowError(id);


        // Business rule validation
        if (payment.getStatus() == Status.COMPLETED && newStatus == Status.PENDING) {
            throw new IllegalStateException("Cannot change status from COMPLETED to PENDING");
        }

        if (newStatus == Status.REFUNDED && payment.getStatus() != Status.COMPLETED) {
            throw new IllegalStateException("Only COMPLETED payments can be refunded");
        }

        payment.setStatus(newStatus); /// change the status of the payment

        return paymentMapper.toPaymentResponseDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDto processingPayment(PaymentRequestDto payRequestDto) {
       /// 1.Get the order
        var savedOrderEntity = orderRepository.findById(payRequestDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(payRequestDto.getOrderId()));

        /// 2.Save the payment
        var payment = Payment.builder()
                .order(savedOrderEntity)
                .paymentType(payRequestDto.getPaymentType())
                .status(Status.SUCCESS)
                .transactionId(payRequestDto.getTransactionId())
                .paidAt(LocalDateTime.now())
                .build();

        /// 3.Save the payment
        return paymentMapper.toPaymentResponseDto(paymentRepository.save(payment));


    }
}
