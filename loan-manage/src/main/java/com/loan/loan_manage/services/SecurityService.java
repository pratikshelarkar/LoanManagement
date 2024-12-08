package com.loan.loan_manage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.loan.loan_manage.entities.JwtRequest;
import com.loan.loan_manage.entities.JwtResponse;
import com.loan.loan_manage.external.AuthenticationFeignClient;

@Service
public class SecurityService {

	@Autowired
    private AuthenticationFeignClient authenticationFeignClient;

    public boolean isAuthenticated() {
        // Create authentication request
        JwtRequest authRequest = new JwtRequest();
        authRequest.setUserName("username");
        authRequest.setPassword("password");

        JwtResponse jwtResponse = authenticationFeignClient.createAuthenticationToken(authRequest);
//        JwtResponse jwtResponse = (JwtResponse) response.getBody();

        String token = jwtResponse.getToken();

        boolean isAuthorized = authenticationFeignClient.authorizeTheRequest("Bearer " + token);
        
        if (isAuthorized) {
            return true;
        } else {
            return false;
        }
    }
}
