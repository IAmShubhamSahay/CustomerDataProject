package com.example.SunbaseProject.Dto.RequestDtos;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequestDto {
    String firstName;

    String lastName;

    String street;

    String address;

    String city;

    String state;

    String email;

    String phone;


}
