package com.wisecoders.dbs.sys;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Action {
  String a() default "";
  
  String b() default "";
  
  String c() default "";
  
  String d() default "";
  
  String e() default "default";
}
