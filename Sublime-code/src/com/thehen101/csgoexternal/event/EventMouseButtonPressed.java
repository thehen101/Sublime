package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;

public class EventMouseButtonPressed extends Event {
	private final int buttonPressed;
	
	public EventMouseButtonPressed(int buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
	public int getButtonPressed() {
		return this.buttonPressed;
	}
}
