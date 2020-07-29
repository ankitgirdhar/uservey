package com.spring.uservey.services;

import com.spring.uservey.exceptions.UsernameAlreadyExistsException;
import com.spring.uservey.models.UserModel;
import com.spring.uservey.repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    public UserModel saveUser(UserModel newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username" + newUser.getUsername() + "already exists");
        }
    }

    public UserModel updateUser(Long id, String token){
        try {
            UserModel newUser = userRepository.getById(id);
            newUser.setCredits(newUser.getCredits() + 5L);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

    }

    public UserModel updateUserByOne(Long id) {
        try {
            UserModel newUser = userRepository.getById(id);
            newUser.setCredits(newUser.getCredits() - 1L);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
    }
}
