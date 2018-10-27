package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventMouseButtonPressed;
import com.thehen101.csgoexternal.event.EventMouseButtonReleased;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;

public class CheatTriggerbot extends Cheat {
	private EntityPlayer localPlayer;
	private final int mouseHeldButton = 4;
	private boolean canTrigger;
	private boolean lastAttack, switchAttack;

	public CheatTriggerbot(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventMouseButtonPressed) {
			EventMouseButtonPressed buttonPressed = (EventMouseButtonPressed) event;
			if (buttonPressed.getButtonPressed() == this.mouseHeldButton) {
				this.canTrigger = true;
			}
		}
		if (event instanceof EventMouseButtonReleased) {
			EventMouseButtonReleased buttonReleased = (EventMouseButtonReleased) event;
			if (buttonReleased.getButtonReleased() == this.mouseHeldButton)
				this.canTrigger = false;
		}
		if (event instanceof EventTick) {
			if (this.localPlayer == null)
				return;

			if (this.canTrigger && CSGOExternal.INSTANCE.getCSGOProcess()
					.readInt(localPlayer.getBaseAddress() + Netvar.CROSSHAIR.getOffset()) != 0) {
				this.lastAttack = true;
				this.switchAttack = !this.switchAttack;
				this.setAttacking(switchAttack);
			}
			
			if (this.lastAttack && CSGOExternal.INSTANCE.getCSGOProcess()
					.readInt(localPlayer.getBaseAddress() + Netvar.CROSSHAIR.getOffset()) == 0) {
				this.lastAttack = false;
				this.setAttacking(false);
			}
		}
	}

	private void setAttacking(boolean pressed) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(Offset.FORCEATTACK.getAddress(), pressed ? 1 : 0);
	}

	@Override
	public void onDisable() {
		this.localPlayer = null;
		this.canTrigger = false;
	}
}
