package com.food_delivery.zomato_backend.mapper;


import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentRequestDto;
import com.food_delivery.zomato_backend.dtos.PaymentDtos.PaymentResponseDto;
import com.food_delivery.zomato_backend.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {
    PaymentResponseDto toPaymentResponseDto(Payment payment);
    Payment toEntity(PaymentRequestDto paymentRequestDto);
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "paidAt", ignore = true)
    void updatePaymentFromDto(PaymentRequestDto paymentRequestDto, @MappingTarget Payment payment);

}
