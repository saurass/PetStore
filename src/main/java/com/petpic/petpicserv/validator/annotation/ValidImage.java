//package com.petpic.petpicserv.validator.annotation;
//
//import com.petpic.petpicserv.validator.ImageValidator;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.*;
//
//@Documented
//@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = {ImageValidator.class})
//public @interface ValidImage {
//    String message() default "Only PDF,XML,PNG or JPG images are allowed";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
