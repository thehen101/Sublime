package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;

public class EventKeyPressed extends Event {
	private final int keyPressed;
	
	public EventKeyPressed(int keyPressed) {
		this.keyPressed = keyPressed;
	}
	
	public int getKeyPressed() {
		return this.keyPressed;
	}
}
