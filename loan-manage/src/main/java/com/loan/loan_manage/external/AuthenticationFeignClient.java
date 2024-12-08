package com.loan.loan_manage.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.loan.loan_manage.entities.JwtRequest;
import com.loan.loan_manage.entities.JwtResponse;

@FeignClient(name = "auth-service", url = "http://localhost:8400",
path = "")
public interface AuthenticationFeignClient {
    @PostMapping("/auth/authenticate")
    JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest);

    @PostMapping("/auth/authorize")
    boolean authorizeTheRequest(@RequestHeader("Authorization") String authorizationHeader);
}