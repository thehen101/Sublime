package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;

public class CheatBunnyhop extends Cheat {
	private EntityPlayer localPlayer;

	public CheatBunnyhop(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventTick) {
			if (this.localPlayer == null)
				return;
			if (this.onGround()) {
				this.jump();
			} else {
				this.setOnGround();
			}
		}
	}
	
	@Override
	public void onDisable() {
		this.setOnGround();
	}

	private boolean onGround() {
		return (this.localPlayer.getFlags().getValueInteger() & 1 << 0) == 0 ? false : true;
	}
	
	private void jump() {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCEJUMP.getAddress(), 5);
	}
	
	private void setOnGround() {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCEJUMP.getAddress(), 4);
	}
}
