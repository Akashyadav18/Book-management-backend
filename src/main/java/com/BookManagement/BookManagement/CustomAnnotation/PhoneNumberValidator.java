package com.BookManagement.BookManagement.CustomAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
//        +919876543210  ✅
//        +91-9876543210 ✅
//        +91 9876543210 ✅
//        9876543210     ✅
    private static final String PHONE_PATTERN = "^(\\+91[\\-\\s]?)?[6-9][0-9]{9}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null || phoneNumber.isBlank()){
            return false;
        }
        return phoneNumber.matches(PHONE_PATTERN);
    }
}
