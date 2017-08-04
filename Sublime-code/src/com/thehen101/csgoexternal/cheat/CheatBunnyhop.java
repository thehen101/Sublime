package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.event.EventTick;
import com.thehen101.csgoexternal.memory.event.base.Event;
import com.thehen101.csgoexternal.util.Constants;

public class CheatBunnyhop extends Cheat {

	public CheatBunnyhop(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventTick) {
			if (this.onGround()) {
				this.jump();
			}
		}
	}
	
	@Override
	public void onDisable() {
		this.setOnGround();
	}

	private boolean onGround() {
		return (CSGOExternal.INSTANCE.getCSGOProcess().readInt
				(Offset.LOCALPLAYER.getAddress() + Constants.NETVAR_FLAGS) & 1 << 0) == 0 ? false : true;
	}
	
	private void jump() {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCEJUMP.getAddress(), 6);
	}
	
	private void setOnGround() {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCEJUMP.getAddress(), 4);
	}
}
