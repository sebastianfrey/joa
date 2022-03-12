package com.github.sebastianfrey.joa.resources.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import com.github.sebastianfrey.joa.models.Crs;
import com.github.sebastianfrey.joa.utils.ProjectionUtils;

/**
 * CRS validator annotation.
 *
 * @author sfrey
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SupportedCrs.Validator.class })
public @interface SupportedCrs {

  public class Validator implements ConstraintValidator<SupportedCrs, Crs> {
    @Override
    public boolean isValid(Crs crs, ConstraintValidatorContext context) {
      return ProjectionUtils.hasProjection(crs);
    }
  }

  String message() default "does reference unsupported crs";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}