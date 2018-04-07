package com.railway.wm.listen;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Component
public class ThreadPoolUtil  extends ExecutorConfigurationSupport implements InitializingBean {
    public static final String PriorityBlockingQueue = "PriorityBlockingQueue";

    public static final String LinkedBlockingQueue = "LinkedBlockingQueue";

    public static final String ArrayBlockingQueue = "ArrayBlockingQueue";

    public static final String DelayQueue = "DelayQueue";

    public static final String LinkedBlockingDeque = "LinkedBlockingDeque";

    private final Object poolSizeMonitor = new Object();

    private int corePoolSize = 1;

    private int maxPoolSize = Integer.MAX_VALUE;

    private int keepAliveSeconds = 60;

    private int queueCapacity = Integer.MAX_VALUE;

    private boolean allowCoreThreadTimeOut = false;

    private ThreadPoolExecutor threadPoolExecutor;

    private String queueType = LinkedBlockingQueue;

    private String beanName;

    /**
     * Set the ThreadPoolExecutor's core pool size. Default is 1.
     * <p>
     * <b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setCorePoolSize(int corePoolSize) {
        synchronized (this.poolSizeMonitor) {
            this.corePoolSize = corePoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setCorePoolSize(corePoolSize);
            }
        }
    }

    /**
     * Return the ThreadPoolExecutor's core pool size.
     */
    public int getCorePoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.corePoolSize;
        }
    }

    /**
     * Set the ThreadPoolExecutor's maximum pool size. Default is
     * {@code Integer.MAX_VALUE}.
     * <p>
     * <b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setMaxPoolSize(int maxPoolSize) {
        synchronized (this.poolSizeMonitor) {
            this.maxPoolSize = maxPoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
            }
        }
    }

    /**
     * Return the ThreadPoolExecutor's maximum pool size.
     */
    public int getMaxPoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.maxPoolSize;
        }
    }

    /**
     * Set the ThreadPoolExecutor's keep-alive seconds. Default is 60.
     * <p>
     * <b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setKeepAliveSeconds(int keepAliveSeconds) {
        synchronized (this.poolSizeMonitor) {
            this.keepAliveSeconds = keepAliveSeconds;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setKeepAliveTime(keepAliveSeconds,
                        TimeUnit.SECONDS);
            }
        }
    }

    /**
     * Return the ThreadPoolExecutor's keep-alive seconds.
     */
    public int getKeepAliveSeconds() {
        synchronized (this.poolSizeMonitor) {
            return this.keepAliveSeconds;
        }
    }

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue. Default is
     * {@code Integer.MAX_VALUE}.
     * <p>
     * Any positive value will lead to a LinkedBlockingQueue instance; any other
     * value will lead to a SynchronousQueue instance.
     *
     * @see java.util.concurrent.LinkedBlockingQueue
     * @see java.util.concurrent.SynchronousQueue
     */
    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    /**
     * Specify whether to allow core threads to time out. This enables dynamic
     * growing and shrinking even in combination with a non-zero queue (since
     * the max pool size will only grow once the queue is full).
     * <p>
     * Default is "false".
     *
     * @see java.util.concurrent.ThreadPoolExecutor#allowCoreThreadTimeOut(boolean)
     */
    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        BlockingQueue<Runnable> queue = createQueue(this.queueCapacity);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(this.corePoolSize,
                this.maxPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS,
                queue, threadFactory, rejectedExecutionHandler);
        if (this.allowCoreThreadTimeOut) {
            executor.allowCoreThreadTimeOut(true);
        }

        this.threadPoolExecutor = executor;
        return executor;
    }
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {

                return new LinkedBlockingQueue<Runnable>(queueCapacity);

        } else {
            return new SynchronousQueue<Runnable>();
        }
    }
}
