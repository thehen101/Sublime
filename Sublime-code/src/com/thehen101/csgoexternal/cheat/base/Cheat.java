package com.thehen101.csgoexternal.cheat.base;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.event.base.Listener;

public abstract class Cheat implements Listener {
	private final String name;
	private final int keybind;
	private boolean enabled;
	
	public Cheat(String cheatName, int cheatKeybind) {
		this.name = cheatName;
		this.keybind = cheatKeybind;
	}
	
	public void onEnable() { }
	
	public void onDisable() { }
	
	public final String getCheatName() {
		return this.name;
	}
	
	public final int getCheatKeybind() {
		return this.keybind;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void toggleCheat() {
		this.enabled = !this.enabled;
		if (this.enabled) {
			CSGOExternal.INSTANCE.getEventManager().addListener(this);
			this.onEnable();
		} else {
			CSGOExternal.INSTANCE.getEventManager().removeListener(this);
			this.onDisable();
		}
	}
}
