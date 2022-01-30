package com.github.sebastianfrey.joa.resources.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import com.github.sebastianfrey.joa.models.Bbox;

/**
 * Bbox validator annotation.
 *
 * @author sfrey
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidBbox.Validator.class })
public @interface ValidBbox {

  public class Validator implements ConstraintValidator<ValidBbox, Bbox> {
    @Override
    public boolean isValid(Bbox bbox, ConstraintValidatorContext context) {
      if (bbox == null) {
        return true;
      }

      return bbox.validate();
    }
  }

  String message() default "bbox is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}