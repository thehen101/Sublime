package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.event.EventTick;
import com.thehen101.csgoexternal.memory.event.base.Event;
import com.thehen101.csgoexternal.util.Constants;

public class CheatAutostrafer extends Cheat {
	private float lastYaw = -500.0F, currentYaw;
	private boolean reset;

	public CheatAutostrafer(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventTick) {
			if (!this.onGround()) {
				if (this.lastYaw != -500.0F) {
					this.reset = true;
					this.currentYaw = this.getCurrentYaw();
					if (this.currentYaw < this.lastYaw) {
						this.setRightPressed(true);
						this.setLeftPressed(false);
					}
					if (this.currentYaw > this.lastYaw) {
						this.setRightPressed(false);
						this.setLeftPressed(true);
					}
				}
				this.lastYaw = this.currentYaw;
			} else {
				if (this.reset) {
					this.reset = false;
					this.setRightPressed(false);
					this.setLeftPressed(false);
				}
				this.lastYaw = -500.0F;
			}
		}
	}
	
	@Override
	public void onDisable() {
		this.lastYaw = -500.0F;
		this.reset = false;
		this.setRightPressed(false);
		this.setLeftPressed(false);
	}
	
	private void setLeftPressed(boolean pressed) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCELEFT.getAddress(), pressed ? 1 : 0);
	}
	
	private void setRightPressed(boolean pressed) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCERIGHT.getAddress(), pressed ? 1 : 0);
	}
	
	private float getCurrentYaw() {
		return CSGOExternal.INSTANCE.getCSGOProcess().readFloat(
				Offset.ENGINEPOINTER.getAddress() + Offset.SETVIEWANGLES.getAddress() + 0x4);
	}
	
	private boolean onGround() {
		return (CSGOExternal.INSTANCE.getCSGOProcess().readInt
				(Offset.LOCALPLAYER.getAddress() + Constants.NETVAR_FLAGS) & 1 << 0) == 0 ? false : true;
	}
}
