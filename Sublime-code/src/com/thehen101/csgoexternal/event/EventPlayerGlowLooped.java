package com.thehen101.csgoexternal.event;

import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.gameobject.GlowEntity;

public class EventPlayerGlowLooped extends Event {
	private EntityPlayer player;
	private final GlowEntity glow;
	
	public EventPlayerGlowLooped(EntityPlayer player, GlowEntity glow) {
		this.player = player;
		this.glow = glow;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	public GlowEntity getGlow() {
		return this.glow;
	}
}