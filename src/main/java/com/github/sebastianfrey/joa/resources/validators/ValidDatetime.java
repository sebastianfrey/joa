package com.github.sebastianfrey.joa.resources.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import com.github.sebastianfrey.joa.resources.params.DatetimeParam;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidDatetime.Validator.class })
public @interface ValidDatetime {

  public class Validator implements ConstraintValidator<ValidDatetime, DatetimeParam> {
    @Override
    public boolean isValid(DatetimeParam param, ConstraintValidatorContext context) {
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