package com.example.spring.Security.application.demo.controller;

import com.example.spring.Security.application.user.entiry.User;
import com.example.spring.Security.shared.aws.DBSecret;
import com.example.spring.Security.shared.utils.AuthenticationUtil;
import com.example.spring.Security.shared.aws.KeySecret;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoController {

    private final AuthenticationUtil authenticationUtil;
    private final KeySecret awsSecret;
    private final DBSecret dbSecret;


    @GetMapping("/test")
    public String demo(){
//        awsSecret.getSecret();
        return dbSecret.getSecret().toString();
    }

    @GetMapping("/401")
    @SecurityRequirement(name = "bearer-key")
    public String demo1(){
        User user = authenticationUtil.currentLoggedInUser();

        System.out.println("-------" + user);
        return "yes you are doing good";
    }


}
