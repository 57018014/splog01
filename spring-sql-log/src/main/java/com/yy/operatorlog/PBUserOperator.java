package com.yy.operatorlog;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PBUserOperator implements Serializable, InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(PBUserOperator.class);
	private static final ThreadLocal<PBUserOperator> data = new ThreadLocal<PBUserOperator>();
	private static ApplicationContext applicationContext = null;
	private static ConcurrentHashMap<Method, String> methodComment = new ConcurrentHashMap<Method, String>();		
	private static final long serialVersionUID = 1L;
	// 主键
	private Long id;
	// 用户id
	private Long userId;
	// 用户名
	private String userName;
	// 接口地址
	private String interfaceUri;
	// 接口操作信息
	private String interfaceComment;
	// 参数值如下name:1,age:10
	private String requestArgs;
	// 当前接口执行的sql 
	private String sqls;
	// 操作时间
	private String operatorTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInterfaceUri() {
		return interfaceUri;
	}

	public void setInterfaceUri(String interfaceUri) {
		this.interfaceUri = interfaceUri;
	}

	public String getInterfaceComment() {
		return interfaceComment;
	}

	public void setInterfaceComment(String interfaceComment) {
		this.interfaceComment = interfaceComment;
	}

	public String getRequestArgs() {
		return requestArgs;
	}

	public void setRequestArgs(String requestArgs) {
		this.requestArgs = requestArgs;
	}

	public String getSqls() {
		return sqls;
	}

	public void setSqls(String sql) {
		this.sqls = new StringBuilder(this.sqls == null ? "" : this.sqls).append(sql).append(";").toString();
	}

	public String getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Thread.currentThread().getId());
		sb.append("【用户操作日志】 ");
        sb.append(interfaceComment);
        sb.append(" 请求路径 {").append(interfaceUri).append("}");
        sb.append(" 请求参数 {").append(requestArgs).append("}");
        sb.append(" 操作用户 {").append(userId).append(":").append(userName).append("}");
        sb.append(" 操作时间 {").append(operatorTime).append("}");
        sb.append(" 执行SQL {").append(sqls).append("}");
    	return sb.toString();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)PBUserOperator.applicationContext.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
			Class<? extends Object> beanType = handlerMethod.getBeanType();
			// 2.获取bean所有方法
			Method[] methods = beanType.getDeclaredMethods();
			for (Method method : methods) {
				PBUserOperatorLog pbUserOperatorLog = method.getAnnotation(PBUserOperatorLog.class);
				// 3.判断方法是否有@PBUserOperatorLog注解
				if(pbUserOperatorLog != null) {
					// 4.根据方法名和@PBUserOperatorLog注解值来进行数据缓存
					methodComment.put(method, pbUserOperatorLog.value());
				}
			}
		});
		logger.debug("【用户操作日志】需要代理的方法" + methodComment.keys());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		PBUserOperator.applicationContext = applicationContext;
	}
	public static PBUserOperator get() {
		return PBUserOperator.data.get();
	}
	public static void set(PBUserOperator pBUserOperator) {
		PBUserOperator.data.set(pBUserOperator);
	}
	public static String getMethodComment(Method method){
		return PBUserOperator.methodComment.get(method);
	}

	public static void insert() {
		// 想模块复用所以不使用mapper.xml 直接通过写sql
		try {
			PBUserOperator pbUserOperator = PBUserOperator.get();
			logger.debug(pbUserOperator.toString());
			DruidDataSource druidDataSource = PBUserOperator.applicationContext.getBean(DruidDataSource.class);
			DruidPooledConnection connection = druidDataSource.getConnection();
			Statement statement = connection.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into pb_user_operator_log ");
			sql.append("(");
			sql.append("user_id,");
			sql.append("user_name,");
			sql.append("interface_uri,");
			sql.append("interface_comment,");
			sql.append("request_args,");
			sql.append("sqls,");
			sql.append("operator_time");
			sql.append(")");
			sql.append("values (");
			sql.append("\"" + pbUserOperator.getUserId() + "\",");
			sql.append("\"" + pbUserOperator.getUserName() + "\",");
			sql.append("\"" + pbUserOperator.getInterfaceUri() + "\",");
			sql.append("\"" + pbUserOperator.getInterfaceComment() + "\",");
			sql.append("\"" + pbUserOperator.getRequestArgs() + "\",");
			sql.append("\"" + pbUserOperator.getSqls() + "\",");
			sql.append("\"" + pbUserOperator.getOperatorTime() + "\"");
			sql.append(")");
			statement.executeUpdate(sql.toString());
			statement.close();
			connection.close();
		}catch (Exception e) {
			logger.debug("【用户操作日志】插入表pb_user_operator_log失败", e);
			e.printStackTrace();
		}
	}
}
