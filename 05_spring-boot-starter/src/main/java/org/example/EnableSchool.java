package org.example;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用enable注解，是主动生效的方式，必须要使用此注解生效
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SchoolConfiguration.class)
public @interface EnableSchool {
}
