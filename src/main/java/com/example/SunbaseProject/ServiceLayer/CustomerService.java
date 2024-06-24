package com.example.SunbaseProject.ServiceLayer;

import com.example.SunbaseProject.Dto.RequestDtos.CustomerRequestDto;
import com.example.SunbaseProject.Dto.ResponseDtos.CustomerResponseDto;
import org.springframework.data.domain.Page;


import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, boolean syncDb);
    CustomerResponseDto udapteCustomer(String email , CustomerRequestDto customerRequestDto);

    Page<CustomerResponseDto> getAllCustomers(int pageNo, int rowsCount, String sortBy, String searchBy);

    CustomerResponseDto getCustomerWithId(String email);

    List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery);

    String deleteCustomer(String email);
}
