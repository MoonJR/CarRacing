package com.moonjr;

import com.moonjr.car.CarListener;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Car Racing Start");
		CarListener listener = new CarListener(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
		listener.addListener();

		while (true) {
			Thread.sleep(500);
		}

	}
}
