package cn.agtsci.commons.mybatis;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 *
 * @author caiyt
 * @date 2019-11-05
 *
 * **/
@Component
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class SqlPrintInterceptor implements Interceptor {

	Logger logger = LogManager.getLogger(SqlPrintInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
			BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
			Configuration configuration = mappedStatement.getConfiguration();
			ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
			Object parameterObject = parameterHandler.getParameterObject();
			String sql = getSql(boundSql, parameterObject, configuration);
			logger.info(sql);
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

	private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					sql = replacePlaceholder(sql, value);
				}
			}
		}
		return sql;
	}

	private String formatParameterObject(Object object) {
		if (object == null) {
			return "NULL";
		} else if (object instanceof String) {
			return "'" + escapeString((String) object) + "'";
		} else if (object instanceof Boolean) {
			return ((Boolean) object).booleanValue() ? "1" : "0";
		} else if (object instanceof java.sql.Time) {
			return "'" + new SimpleDateFormat("HH:mm:ss").format(object) + "'";
		} else if (object instanceof java.sql.Date) {
			return "'" + new SimpleDateFormat("yyyy-MM-dd").format(object) + "'";
		} else if (object instanceof java.util.Date) {
			return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object) + "'";
		} else {
			return object.toString();
		}
	}

	private String escapeString(String in) {
		StringBuilder out = new StringBuilder();
		for (int i = 0, j = in.length(); i < j; i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append(c);
			}
			out.append(c);
		}
		return out.toString();
	}

	private String replacePlaceholder(String sql, Object propertyValue) {
		String result = formatParameterObject(propertyValue);
		return sql.replaceFirst("\\?", Matcher.quoteReplacement(result));
	}

}
