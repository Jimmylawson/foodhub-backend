package com.food_delivery.zomato_backend.service.PaymentService;

import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentRequestDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.enumTypes.Status;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PaymentServiceInterface {
    /// CRUD operations
    PaymentResponseDto savePayment(PaymentRequestDto payRequestDto);
    PaymentResponseDto getPayment(Long id);
    PaymentResponseDto updatePayment(Long id, PaymentRequestDto payRequestDto);
    //List<PaymentResponseDto> getAllPayments();
    void deletePayment(Long id);
    PaymentResponseDto updatePaymentStatus(Long id, Status status);
    PaymentResponseDto processingPayment(PaymentRequestDto payRequestDto);

}
