package com.github.joa.resources.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.github.joa.resources.params.BboxParam;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidBbox.Validator.class })
public @interface ValidBbox {

  public class Validator implements ConstraintValidator<ValidBbox, BboxParam> {
    @Override
    public boolean isValid(BboxParam param, ConstraintValidatorContext context) {
      if (param == null) {
        return true;
      }

      return param.validate();
    }
  }

  String message() default "bbox is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}