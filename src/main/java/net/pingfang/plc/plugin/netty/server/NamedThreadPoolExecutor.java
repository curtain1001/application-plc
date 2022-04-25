/**
 * 创建日期: 2017/10/19
 * 创建作者：helloworldyu
 * 文件名称：NamedThreadPoolExecutor.java
 * 功能:
 */
package net.pingfang.plc.plugin.netty.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能: 重写了 ThreadFactory 可以设置名字的线程组(不同的业务用不同的线程组) 线程个数固定不能无限增长的。
 * 队列大小固定,队列大小为最大线程个数的10倍。 队列满的时候有错误日志提示。
 *
 * 创建作者：helloworldyu 文件名称：NamedThreadPoolExecutor.java 创建日期: 2017/10/19
 */
public class NamedThreadPoolExecutor extends ThreadPoolExecutor {
	private static Logger logger = LoggerFactory.getLogger(NamedThreadPoolExecutor.class);
	private String poolName;

	public NamedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveSecond, String poolName) {
		super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10 * maximumPoolSize), new NamedThreadFactory(poolName));

		this.poolName = poolName;

		setRejectedExecutionHandler(new DiscardPolicy() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
				StackTraceElement stes[] = Thread.currentThread().getStackTrace();
				for (StackTraceElement ste : stes) {
					logger.warn(ste.toString());
				}
			}

		});
	}

	public NamedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveSecond) {
		super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(10 * corePoolSize));
		setRejectedExecutionHandler(new DiscardPolicy() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
				StackTraceElement stes[] = Thread.currentThread().getStackTrace();
				for (StackTraceElement ste : stes) {
					logger.warn(ste.toString());
				}
			}

		});
	}

	@Override
	public void execute(Runnable command) {
		super.execute(command);

		// 对列里任务已经满了。也就是说有些请求不能响应了
		if (super.getCorePoolSize() * 10 - this.getQueue().remainingCapacity() > 100) {
			logger.error(poolName + " ThreadPool blocking Queue  size : "
					+ (super.getCorePoolSize() * 10 - this.getQueue().remainingCapacity()));
		}
	}

	/**
	 * The default thread factory
	 */
	static class NamedThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		NamedThreadFactory(final String name) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = name + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}
