package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;

public class EventEntityPlayerLooped extends Event {
	private final EntityPlayer player;
	
	public EventEntityPlayerLooped(EntityPlayer player) {
		this.player = player;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
}
