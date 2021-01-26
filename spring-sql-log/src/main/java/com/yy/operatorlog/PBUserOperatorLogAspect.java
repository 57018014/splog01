package com.yy.operatorlog;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yy.scope.CurrentScope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 	操作日志代理类
 */
@Component
@Aspect
@Slf4j
public class PBUserOperatorLogAspect implements Ordered {
	
    private static final Logger logger = LoggerFactory.getLogger(PBUserOperatorLogAspect.class);

    @Pointcut("@annotation(com.yy.operatorlog.PBUserOperatorLog)")
    private void pointCutMethod() {  
    }  
      
    //环绕通知  
    @Around("pointCutMethod()")  
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
    	log.info("aspect 0......");
    	// 方法执行前
        boolean flag = setPBUserOperator(joinPoint);
        // 调用目标方法
        Object result = joinPoint.proceed();
        // 方法执行后
        if(flag) {
        	printPBUserOperator();
        }
        return result;
    }
    
	// 设置参数
    private boolean setPBUserOperator(ProceedingJoinPoint joinPoint) {
    	try {
    		String interfaceComment = getInterfaceComment(((MethodSignature) joinPoint.getSignature()).getMethod());
    		if(StringUtils.isBlank(interfaceComment)) {
    			return false;
    		}
			PBUserOperator pbUserOperator = PBUserOperator.get();
			if(pbUserOperator == null) {
				pbUserOperator = new PBUserOperator();
			}
			pbUserOperator.setUserId(CurrentScope.getLoginUserId());
			if(CurrentScope.getLoginUser() != null) {
				pbUserOperator.setUserName(CurrentScope.getLoginUser().getName());
			}
			pbUserOperator.setInterfaceUri(getInterfaceUri());
			
			pbUserOperator.setInterfaceComment(interfaceComment);
			pbUserOperator.setRequestArgs(getRequestArgs(joinPoint));
			pbUserOperator.setOperatorTime( DateUtil.formatDateTime(new Date()));
			PBUserOperator.set(pbUserOperator);
		} catch (Exception e) {
			logger.debug("【用户操作日志】创建PBUserOperator对象失败", e);
			e.printStackTrace();
			return false;
		}
    	return true;
	}
    
    // 设置请求URI
	private String getInterfaceUri() {
    	return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI();
	}
	
	// 设置接口操作信息
	private String getInterfaceComment(Method method) {
    	return PBUserOperator.getMethodComment(method);
	}
	
    // 设置请求参数
	private String getRequestArgs(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		// 1.参数名称
        String[] argNames = methodSignature.getParameterNames();
        // 2.参数值
        Object[] argsValue = joinPoint.getArgs();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < argNames.length; i++) {
        	sb.append(argNames[i]).append(":").append(argsValue[i]);
			if(i != argNames.length -1) {
				sb.append(",");
			}
		}
        return sb.toString();
	}
	
    private void printPBUserOperator() {
    	PBUserOperator.insert();
	}
    
	@Override  
    public int getOrder() {  
        return 1001;  
    } 
	
} 