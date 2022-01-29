package com.github.sebastianfrey.joa.resources.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import com.github.sebastianfrey.joa.models.Datetime;

/**
 * Datetime validator annotation.
 *
 * @author sfrey
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidDatetime.Validator.class })
public @interface ValidDatetime {

  public class Validator implements ConstraintValidator<ValidDatetime, Datetime> {
    @Override
    public boolean isValid(Datetime datetime, ConstraintValidatorContext context) {
      if (datetime == null) {
        return true;
      }

      return datetime.validate();
    }
  }

  String message() default "datetime is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}