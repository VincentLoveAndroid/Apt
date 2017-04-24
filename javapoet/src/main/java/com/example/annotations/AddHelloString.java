package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by vincent on 2017/4/24.
 * email-address:674928145@qq.com
 * description:
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface AddHelloString {
}
