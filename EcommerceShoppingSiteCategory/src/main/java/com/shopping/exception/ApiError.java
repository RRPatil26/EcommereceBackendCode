package com.shopping.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int statuscode;
    private String message;
    private HttpStatus statusmessage;
    private Date date;
    private String url;
}