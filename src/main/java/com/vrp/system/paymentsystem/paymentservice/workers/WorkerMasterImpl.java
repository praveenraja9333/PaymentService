package com.vrp.system.paymentsystem.paymentservice.workers;


import com.vrp.system.paymentsystem.paymentservice.queue.RegistrationEventQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Component
public class WorkerMasterImpl<T extends Runnable>  implements WorkerMaster<T> {


	private static Logger LOG= LogManager.getLogger(WorkerMasterThread.class);
	private static boolean exitThread=false;
	private Class<T> clazz;
	private ExecutorService executorService = null;
	@Autowired
	private RegistrationEventQueue registrationEventQueue;
	@Autowired
	private ThreadPoolImpl threadPool;

	@Autowired
	private ApplicationContext applicationContext;

	public  static boolean isExitThread() {
		return exitThread;
	}

	public synchronized static void setExitThread(boolean exitThread) {
		WorkerMasterImpl.exitThread = exitThread;
	}

	@Override
	public void scanLoadoFApp() {
		int queuesize = registrationEventQueue.queueSize();
		System.out.println("Queue size found is "+queuesize);
		double CPU = Math.ceil((queuesize == 0 ? 0 : queuesize / 5));
		System.out.println("CPU formula yield value "+CPU);
		double noThreads = CPU == 0 ? 1 : CPU * 3;
		scaleTheSystem((int) noThreads);
	}

	@Override
	public void scanTheSystem() {

	}

	@Override
	public void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void scaleTheSystem(int noThreads) {
		if (executorService == null)
			LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Executor is null: No shutdown is necessary");
		else {
			setExitThread(true);
			LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Executor is loaded, Trying to reboot");
			executorService.shutdown();
			while (true) {
				LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Showdownhook called");
				try {
					if (executorService.awaitTermination(10, TimeUnit.SECONDS))
						break;
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+" Awaiting the Threads to shutdown");
			}
		}
		setExitThread(false);
		LOG.info(Thread.currentThread().getThreadGroup()+":==:"+Thread.currentThread().getName()+"Executor Service Shutdown successfully.reinitializing");
		executorService = threadPool.populateThreadpool(noThreads);
		IntStream.range(0, noThreads).forEach(i -> {
			executorService.execute(applicationContext.getBean(clazz));
		});

	}
}
