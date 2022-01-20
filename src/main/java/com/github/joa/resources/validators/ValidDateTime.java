package com.github.joa.resources.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.github.joa.resources.params.DateTimeParam;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidDateTime.Validator.class })
public @interface ValidDateTime {

  public class Validator implements ConstraintValidator<ValidDateTime, DateTimeParam> {
    @Override
    public boolean isValid(DateTimeParam param, ConstraintValidatorContext context) {
      if (param == null) {
        return true;
      }

      return param.validate();
    }
  }

  String message() default "datetime is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}