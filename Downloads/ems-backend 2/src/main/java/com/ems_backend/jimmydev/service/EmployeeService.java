package com.ems_backend.jimmydev.service;


import com.ems_backend.jimmydev.dto.EmployeeRequestDto;
import com.ems_backend.jimmydev.dto.login.LoginRequestDto;
import com.ems_backend.jimmydev.model.Employee;


import java.util.List;
import java.util.Optional;


public interface EmployeeService {
    Employee save(EmployeeRequestDto employeeRequestDto);
    Employee update(Long id, EmployeeRequestDto employeeRequestDto);
    void delete(Long id);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    public Employee savedUpdated(Employee employee);
    public  Optional<Employee> findByEmail(String email);

    String login(LoginRequestDto loginRequestDto);
}
