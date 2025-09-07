package com.github.sebastianfrey.joa.resources.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import com.github.sebastianfrey.joa.models.Crs;

/**
 * CRS validator annotation.
 *
 * @author sfrey
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidCrs.Validator.class })
public @interface ValidCrs {

  public class Validator implements ConstraintValidator<ValidCrs, Crs> {
    @Override
    public boolean isValid(Crs crs, ConstraintValidatorContext context) {
      if (crs == null || crs.getUri() == null) {
        return true;
      }

      return crs.validate();
    }
  }

  String message() default "is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}