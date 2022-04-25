///**
// * 创建日期: 2017/10/19
// * 创建作者：helloworldyu
// * 文件名称：AppContext.java
// * 功能:
// */
//package net.pingfang;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
///**
// * 功能:
// *
// * 创建作者：helloworldyu 文件名称：AppContext.java 创建日期: 2017/10/19
// */
//public class AppContext implements ApplicationContextAware {
//
//	// The spring application context.
//	private static ApplicationContext applicationContext;
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		AppContext.applicationContext = applicationContext;
//	}
//
//	// 根据beanName获取bean
//	public static Object getBean(String beanName) {
//		if (null == beanName) {
//			return null;
//		}
//		return applicationContext.getBean(beanName);
//	}
//}
