package com.example.SunbaseProject.ControllerLayer;


import com.example.SunbaseProject.Dto.RequestDtos.CustomerRequestDto;
import com.example.SunbaseProject.Dto.ResponseDtos.CustomerResponseDto;
import com.example.SunbaseProject.Exceptions.CustomerAlreadyExits;
import com.example.SunbaseProject.Exceptions.CustomerNotFound;
import com.example.SunbaseProject.ServiceLayer.CustomerService;
import com.example.SunbaseProject.ServiceLayer.ExternalApiCall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class customerController {

    @Autowired
    private CustomerService customerService;

    ExternalApiCall externalApiCall=new ExternalApiCall();

    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto,@RequestParam boolean syncDb){

        try{
            CustomerResponseDto customerResponseDto=customerService.createCustomer(customerRequestDto,syncDb );
            return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
        }
        catch(CustomerAlreadyExits e){
                return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/update/{email}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String email,@RequestBody CustomerRequestDto customerRequestDto){

        try{
                CustomerResponseDto customerResponseDto=customerService.udapteCustomer(email,customerRequestDto);
                return new ResponseEntity<>(customerResponseDto,HttpStatus.ACCEPTED);
        }
        catch(CustomerNotFound e){
                return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/getCustomers")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity<Page<CustomerResponseDto>> getAllCustomers(@RequestParam int pageNo, @RequestParam int rowsCount, @RequestParam(required = false)String sortBy, @RequestParam(required = false) String searchBy){

        Page<CustomerResponseDto> customerList = customerService.getAllCustomers(pageNo, rowsCount, sortBy, searchBy);
        return new ResponseEntity<>(customerList, HttpStatus.FOUND);

    }

    @GetMapping("/get/{email}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity<CustomerResponseDto> getCustomerWithId(@PathVariable String email){

        try{
            CustomerResponseDto customerResponseDto = customerService.getCustomerWithId(email);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.FOUND);
        }catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/searchBy")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity<List<CustomerResponseDto>> searchBySpecificType(@RequestParam String searchBy, @RequestParam String searchQuery){
        List<CustomerResponseDto>   searchedResult = customerService.searchBySpecificType(searchBy,searchQuery);
        return new ResponseEntity<>(searchedResult,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity deleteCustomer(@RequestParam String email){
        try{
            String result =customerService.deleteCustomer(email);
            return new ResponseEntity(result,HttpStatus.ACCEPTED);
        }catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/sync")
    @CrossOrigin(origins = "http://127.0.0.1:5500/index.html")
    public ResponseEntity<Object[]> getTokenFromApi(){
        Object[]   customerObject =externalApiCall.getTokenFromApi();
        return new ResponseEntity<>(customerObject,HttpStatus.ACCEPTED);
    }






}
