package com.personalweb.demo.dto;

import com.personalweb.demo.exception.CustomizeErrorCode;
import lombok.Data;

@Data
public class UserSignInDTO {
    private int property;
    private CustomizeErrorCode customizeErrorCode;
    private String token;
}
