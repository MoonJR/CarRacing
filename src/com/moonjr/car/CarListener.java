package com.moonjr.car;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class CarListener implements GpioPinListenerDigital {
	private GpioController mGpioController;
	private Pin listenPin;
	private PinPullResistance listenStatus;
	private GpioPinDigitalInput mGpioPinDigitalInput;

	public CarListener(Pin listenPin, PinPullResistance listenStatus) {
		this.mGpioController = GpioFactory.getInstance();
		this.listenPin = listenPin;
		this.listenStatus = listenStatus;
		this.mGpioPinDigitalInput = mGpioController.provisionDigitalInputPin(listenPin, listenStatus);
	}

	public void addListener() {
		mGpioController.addListener(this, mGpioPinDigitalInput);
	}

	public void removeListener(GpioController gpio) {
		mGpioController.removeListener(this, mGpioPinDigitalInput);
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent arg0) {
		System.out.println("pin:" + listenPin);
		System.out.println("status:" + listenStatus);
	}

}
