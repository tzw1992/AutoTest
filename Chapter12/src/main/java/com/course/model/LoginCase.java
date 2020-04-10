package com.course.model;

import lombok.Data;

@Data
public class LoginCase {
    private String userName;
    private String password;
    private String expected;
}
