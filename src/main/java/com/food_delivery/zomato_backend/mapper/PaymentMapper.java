package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentRequestDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDto toPaymentResponseDto(Payment payment);
    Payment toEntity(PaymentRequestDto paymentRequestDto);

}
