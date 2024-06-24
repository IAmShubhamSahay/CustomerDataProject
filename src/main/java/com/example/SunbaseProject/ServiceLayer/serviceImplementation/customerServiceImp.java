package com.example.SunbaseProject.ServiceLayer.serviceImplementation;


import com.example.SunbaseProject.Dto.RequestDtos.CustomerRequestDto;
import com.example.SunbaseProject.Dto.ResponseDtos.CustomerResponseDto;
import com.example.SunbaseProject.Exceptions.CustomerAlreadyExits;
import com.example.SunbaseProject.Exceptions.CustomerNotFound;
import com.example.SunbaseProject.Models.Customer;
import com.example.SunbaseProject.RepositoryLayer.customerRepository;
import com.example.SunbaseProject.ServiceLayer.CustomerService;
import com.example.SunbaseProject.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class customerServiceImp implements CustomerService {

    @Autowired
    customerRepository customerRepository;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto,boolean SyncDb){

        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail());

        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        if (SyncDb && customer != null){
//            if a customer is already existing and if we are performing sync then just update that customer
            customerResponseDto = udapteCustomer(customerRequestDto.getEmail(), customerRequestDto);
        }else  if(customer!=null){
            //if customer is exits with that email and sync value is false then throw an error
            throw new CustomerAlreadyExits("Customer Already exits");
        }else{
            //making customer object  from customer request dto
            customer = CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);

            //given customer unique id
            customer.setUid(String.valueOf(UUID.randomUUID()));

            //saving customer to db
            Customer savedCustomer = customerRepository.save(customer);

            //making customer response dto from customer and return it
            customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
            customerResponseDto.setMessage("user has been added");
        }
        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto udapteCustomer(String email, CustomerRequestDto customerRequestDto) {

        //first checking if customer is present or not in db by given email ID
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            //if customer is null then we cant update it so throw error
            throw  new CustomerNotFound("Customer not found");
        }

        //check  value  from request dto which is not empty and assign its value to customer
        if (customerRequestDto.getFirstName() != null){
            customer.setFirstName(customerRequestDto.getFirstName());
        }
        if (customerRequestDto.getLastName() != null){
            customer.setLastName(customerRequestDto.getLastName());
        }
        if (customerRequestDto.getStreet() != null){
            customer.setStreet(customerRequestDto.getStreet());
        }
        if (customerRequestDto.getAddress() != null){
            customer.setAddress(customerRequestDto.getAddress());
        }
        if (customerRequestDto.getCity() != null){
            customer.setCity(customerRequestDto.getCity());
        }
        if (customerRequestDto.getState() != null){
            customer.setState(customerRequestDto.getState());
        }
        if (customerRequestDto.getPhone() != null){
            customer.setPhone(customerRequestDto.getPhone());
        }
        if (customerRequestDto.getEmail() != null){
            customer.setEmail(customerRequestDto.getEmail());
        }

        //after saving new value in customer , save customer to db
        Customer savedCustomer =customerRepository.save(customer);

        //making response dto from customer and return it
        CustomerResponseDto customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
        customerResponseDto.setMessage("user has been successfully updated");

        return customerResponseDto;

    }

    @Override
    public Page<CustomerResponseDto> getAllCustomers(int pageNo, int rowsCount, String sortBy, String searchBy){



        // this function will return a list of customers with the required number of rows
        Pageable currentPageWithRequiredRows;

        if (sortBy != null) {

            //  if sort by value is provided
            currentPageWithRequiredRows = PageRequest.of(pageNo-1, rowsCount, Sort.by(sortBy));
        }else {
            currentPageWithRequiredRows = PageRequest.of(pageNo-1, rowsCount);
        }

        Page<Customer> customersPage = customerRepository.findAll(currentPageWithRequiredRows);
        return customersPage.map(this::convertToDto);
    }
    public CustomerResponseDto convertToDto(Customer customer){
        return CustomerTransformer.customerToCustomerResponseDto(customer);
    }

    @Override
    public CustomerResponseDto getCustomerWithId(String email) {

        //first checking if customer is present or not in db by given email ID
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            //if customer is null then throw error
            throw new CustomerNotFound("no account found with the given email");
        }

        //making response dto from customer and return it
        CustomerResponseDto customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(customer);
        customerResponseDto.setMessage("account found with " + email);
        return customerResponseDto;
    }
    @Override
    public List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery) {
        List<Customer> searchRes = new ArrayList<>();

        //check which search type is given and then find it from repo
        if (searchBy.equals("firstName")) {
            searchRes = customerRepository.findByFirstNameLike(searchQuery);
        } else if (searchBy.equals("city")) {
            searchRes = customerRepository.findByCityLike(searchQuery);
        } else if (searchBy.equals("phone")) {
            searchRes = customerRepository.findByPhoneLike(searchQuery);
        } else if (searchBy.equals("email")) {
            searchRes = customerRepository.findByEmailLike(searchQuery);
        }
        List<CustomerResponseDto> searchList = new ArrayList<>();

        for (Customer cust: searchRes) {
            searchList.add(CustomerTransformer.customerToCustomerResponseDto(cust));
        }
        return searchList;
    }

    @Override
    @Transactional
    public String deleteCustomer(String email){

        Customer customer=customerRepository.findByEmail(email);

        if(customer==null){
            throw new CustomerNotFound("Customer not found with this emailId");
        }

        customerRepository.deleteByEmail(email);

        return "Customer deleted Successfully";




    }



}
