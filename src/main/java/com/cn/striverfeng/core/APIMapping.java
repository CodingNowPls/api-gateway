/**
 * 
 */
package com.cn.striverfeng.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author StriverFeng
 *
 * 说明:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIMapping {
	String value();
}
