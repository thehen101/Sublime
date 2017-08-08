package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;

public class EventKeyReleased extends Event {
	private final int keyReleased;
	
	public EventKeyReleased(int keyReleased) {
		this.keyReleased = keyReleased;
	}
	
	public int getKeyReleased() {
		return this.keyReleased;
	}
}
