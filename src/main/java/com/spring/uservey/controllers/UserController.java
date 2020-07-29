package com.spring.uservey.controllers;

import com.spring.uservey.models.UserModel;
import com.spring.uservey.payload.JWTLoginSuccessResponse;
import com.spring.uservey.payload.LoginRequest;
import com.spring.uservey.security.JwtTokenProvider;
import com.spring.uservey.services.MapValidationErrorService;
import com.spring.uservey.services.UserService;
import com.spring.uservey.validator.UserValidate;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



import static com.spring.uservey.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidate userValidate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserModel user, BindingResult result) {

        userValidate.validate(user,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)
            return errorMap;
        UserModel newUser = userService.saveUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null)
            return errorMap;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);


        return new ResponseEntity<>(new JWTLoginSuccessResponse(true,jwt),HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody String token){

        UserModel newUser =  userService.updateUser(id,token);
        Long credits = newUser.getCredits();
        return new ResponseEntity<>(credits,HttpStatus.OK);
    }

    @GetMapping("/updateByOne/{id}")
    public ResponseEntity<?> updateUserByOne(@PathVariable Long id){

        UserModel newUser =  userService.updateUserByOne(id);
        Long credits = newUser.getCredits();
        return new ResponseEntity<>(credits,HttpStatus.OK);
    }


}
