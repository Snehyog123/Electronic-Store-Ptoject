package com.bikkadit.electronicstore.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;
import javax.validation.Payload;


@Target({ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface Imagenamevalid  {

    public String message() default "Invalid Image Name !!!";
    //represents group of constraints
    public Class<?>[] groups() default { };
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default { };


}
