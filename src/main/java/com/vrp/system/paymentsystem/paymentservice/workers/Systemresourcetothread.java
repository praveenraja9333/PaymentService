package com.vrp.system.paymentsystem.paymentservice.workers;

import org.springframework.stereotype.Component;

/*
This Class will find the best Thread Size for the Application based on the System Resource

Formula 1CPU=3Threads

 */
@Component
public class Systemresourcetothread {
	

	enum OS {
		Win, Lin,Mac
	}

	private int MaxThread = 3;

	public int getMaxThread() {
		return MaxThread;
	}

	public void setMaxThread(int maxThread) {
		MaxThread = maxThread;
	}

	public int calculateThreads() {
		System.out.println(System.getProperty("os.name"));
		OS os = OS.valueOf(System.getProperty("os.name").substring(0, 3));
		switch (os) {
			case Win:
				System.out.println("Its Windows");
				return Runtime.getRuntime().availableProcessors()*3;
			case Lin:
				System.out.println("Its Linux");
				return Runtime.getRuntime().availableProcessors()*3;
			case Mac:
				System.out.println("Its Mac");
				return Runtime.getRuntime().availableProcessors()*3;

			default:
				System.out.println("No Standard Operating System found, Hence setting it to Minimum 2 Threads");
				return 3;
		}

	}

	//This Main method  for testing only
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
		OS os = OS.valueOf(System.getProperty("os.name").substring(0, 3));
		switch (os) {
			case Win:
				System.out.println("Its Windows");
				System.out.println("AvailableProcessors " + Runtime.getRuntime().availableProcessors());
				break;
			case Lin:
				System.out.println("Its Linux");
				break;
			default://do nothing;

		}
	}

}

