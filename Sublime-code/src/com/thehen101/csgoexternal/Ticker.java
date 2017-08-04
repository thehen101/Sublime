package com.thehen101.csgoexternal;

import com.thehen101.csgoexternal.memory.event.EventTick;

public class Ticker {
	private final int delayMS;
	
	public Ticker(int ticksPerSecond) {
		this.delayMS = 1000 / ticksPerSecond;
	}
	
	public void startTicker() {
		for (;;) {
			this.tick();
			try {
				Thread.sleep(this.delayMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void tick() {
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventTick());
	}
}
