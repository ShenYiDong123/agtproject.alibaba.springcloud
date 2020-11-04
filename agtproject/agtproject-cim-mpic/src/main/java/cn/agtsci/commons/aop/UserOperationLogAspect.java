package cn.agtsci.commons.aop;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 统一日志管理切面类
 * 
 * @author caiyt
 * @date 2019-11-05
 */
@Aspect
@Component
public class UserOperationLogAspect {

	private static final Logger logger = LogManager.getLogger(UserOperationLogAspect.class);

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	ThreadLocal<String> params = new ThreadLocal<>();

	/**
	 * 切入点
	 * 
	 */
	@Pointcut("@annotation(cn.agtsci.commons.annotation.UserOperation)")
	public void controllerAspect() {

	}

	/**
	 * 切入点方法执行前
	 * 
	 * @param joinPoint
	 *            切面对象
	 * @throws Throwable
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		// 类名.方法
		String klass_method = ms.getDeclaringTypeName() + "." + ms.getName();

		startTime.set(System.currentTimeMillis());
		// ServletRequest、ServletResponse、MultipartFile不能序列化
		Object[] args = joinPoint.getArgs();
		Object[] arguments = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
					|| args[i] instanceof MultipartFile) {
				continue;
			}
			arguments[i] = args[i];
		}
		String paramter = "";
		if (arguments != null) {
			try {
				paramter = JSONObject.toJSONString(arguments);
			} catch (Exception e) {
				paramter = arguments.toString();
			}
		}
		params.set(paramter);
		logger.info("【处理请求】args:{}, KlassMethod:{}", params.get(), klass_method);
	}

	/**
	 * 返回后执行
	 * 
	 * @param joinPoint
	 *            切面对象
	 * @param ret
	 *            返回对象
	 * @throws Throwable
	 */
	@AfterReturning(returning = "ret", pointcut = "controllerAspect()")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		// 类名.方法
		String klass_method = ms.getDeclaringTypeName() + "." + ms.getName();
		Long endTime = System.currentTimeMillis();
		logger.info("【请求结束】costTime:{}, args:{}, KlassMethod:{}, return:{}", endTime - startTime.get(), params.get(), klass_method, ret);
	}

	@AfterThrowing(throwing = "ex", pointcut = "controllerAspect()")
	public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {

		logger.error("错误信息", ex);

		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		// 类名.方法
		String klass_method = ms.getDeclaringTypeName() + "." + ms.getName();

		logger.error("【请求异常】args:{}, KlassMethod:{}, UserCreateId:{}, errorMessage:{}", params.get(), klass_method, 1,
				ex.getMessage());
	}

}
