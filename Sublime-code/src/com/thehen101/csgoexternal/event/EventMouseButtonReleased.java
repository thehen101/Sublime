package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;

public class EventMouseButtonReleased extends Event {
	private final int buttonReleased;
	
	public EventMouseButtonReleased(int buttonReleased) {
		this.buttonReleased = buttonReleased;
	}
	
	public int getButtonReleased() {
		return this.buttonReleased;
	}
}
