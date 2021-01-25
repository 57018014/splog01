package com.yy.operatorlog;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 	操作日志注解 
 * 	注意Controller中需要添加注解的方法,参数需要重写toString方法(防止打印时保存对象地址信息)
 */
public @interface PBUserOperatorLog {
	
	String value();
	
}
