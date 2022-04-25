package net.pingfang.plc.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.pingfang.plc.utils.GenericResult;

/**
 * 全局异常处理器
 *
 * @author pingfang
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 系统异常
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResult<String, String>> handleException(Exception e, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		log.error("请求地址'{}',请求方法'{}',发生系统异常.", requestURI, requestMethod, e);
//		String traceNo = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
//		log.warn("ExTrace: {}", traceNo);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) //
//				.header("X-ExTrace", traceNo) //
				.body(GenericResult.fail(null, e.getMessage()));
	}

}
