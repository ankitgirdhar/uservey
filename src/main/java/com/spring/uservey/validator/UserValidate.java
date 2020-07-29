package com.spring.uservey.validator;

import com.spring.uservey.models.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidate implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserModel.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserModel user = (UserModel) target;

        if(user.getPassword().length() < 6)
            errors.rejectValue("password","Length","Password must be at least 6 characters");

        if(!user.getPassword().equals(user.getConfirmPassword()))
            errors.rejectValue("password","Match", "Passwords must match");
    }
}
