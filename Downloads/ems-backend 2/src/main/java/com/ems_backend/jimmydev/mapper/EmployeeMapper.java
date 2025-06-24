package com.ems_backend.jimmydev.mapper;


import com.ems_backend.jimmydev.dto.EmployeeRequestDto;
import com.ems_backend.jimmydev.dto.EmployeeResponseDto;
import com.ems_backend.jimmydev.model.Employee;
import org.mapstruct.*;

@Mapper(componentModel= "spring")
public interface EmployeeMapper {
    EmployeeResponseDto toResponse(Employee employee);
    Employee toEntity(EmployeeRequestDto employeeRequestDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee update(EmployeeRequestDto employeeRequestDto, @MappingTarget Employee employee);

}
