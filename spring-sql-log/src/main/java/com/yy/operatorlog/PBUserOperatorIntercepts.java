package com.yy.operatorlog;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * sql 拦截器用来保存sql
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class PBUserOperatorIntercepts implements Interceptor {
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		setPBUserOperatorSql(((StatementHandler)(invocation.getTarget())).getBoundSql());
		return invocation.proceed();
	}

	private void setPBUserOperatorSql(BoundSql boundSql) {
		PBUserOperator pbUserOperator = PBUserOperator.get();
		if(pbUserOperator == null) {
			// 如果当前线程没有PBUserOperator对象则表示当前线程没有需要监控操作日志的需求
			return;
		}
		pbUserOperator.setSqls(removeBreakingWhitespace(boundSql.getSql()));
		// 设置sql到ThreadLocal中
		PBUserOperator.set(pbUserOperator);
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {}
	
	// 格式化sql
	public String removeBreakingWhitespace(String original) {
	    StringTokenizer whitespaceStripper = new StringTokenizer(original);
	    StringBuilder builder = new StringBuilder();
	    while (whitespaceStripper.hasMoreTokens()) {
	      builder.append(whitespaceStripper.nextToken());
	      builder.append(" ");
	    }
	    return builder.toString();
	}
}
