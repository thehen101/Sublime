package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;

public class EventLocalPlayerUpdate extends Event {
	private EntityPlayer entityPlayer;
	
	public EventLocalPlayerUpdate(EntityPlayer entityPlayer) {
		this.entityPlayer = entityPlayer;
	}
	
	public EntityPlayer getLocalPlayer() {
		return this.entityPlayer;
	}
}
