package com.example.SunbaseProject.Dto.ResponseDtos;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JwtResponse {

    String jwtToken;

    String username;

}
