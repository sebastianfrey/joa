package com.github.joa.resources.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.github.joa.resources.params.DoubleListParam;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { Envelope.Validator.class })
public @interface Envelope {

  public class Validator implements ConstraintValidator<Envelope, DoubleListParam> {
    @Override
    public boolean isValid(DoubleListParam param, ConstraintValidatorContext context) {
      if (param == null) {
        return true;
      }

      List<Double> bbox = param.get();

      if (bbox == null || bbox.isEmpty()) {
        return true;
      }

      if (bbox.size() == 4) {
        return true;
      }

      if (bbox.size() == 6) {
        return true;
      }

      return false;
    }
  }

  String message() default "Envelope is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}