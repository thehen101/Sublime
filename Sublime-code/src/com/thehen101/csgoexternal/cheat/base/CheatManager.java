package com.thehen101.csgoexternal.cheat.base;

import java.util.ArrayList;

import org.jnativehook.keyboard.NativeKeyEvent;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.CheatAimbot;
import com.thehen101.csgoexternal.cheat.CheatAutostrafer;
import com.thehen101.csgoexternal.cheat.CheatBunnyhop;
import com.thehen101.csgoexternal.cheat.CheatGlowESP;
import com.thehen101.csgoexternal.event.EventKeyPressed;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.event.base.Listener;

public class CheatManager implements Listener {
	
	private final ArrayList<Cheat> cheatList = new ArrayList<Cheat>();
	
	public CheatManager() {
		CSGOExternal.INSTANCE.getEventManager().addListener(this);
		this.cheatList.add(new CheatAimbot("Aimbot", NativeKeyEvent.VC_HOME));
		this.cheatList.add(new CheatAutostrafer("Autostrafer", NativeKeyEvent.VC_PAGE_UP));
		this.cheatList.add(new CheatBunnyhop("Bunnyhop", NativeKeyEvent.VC_PAGE_DOWN));
		this.cheatList.add(new CheatGlowESP("GlowESP", NativeKeyEvent.VC_END));
	}
	
	@Override
	public void onEvent(Event event) {
		if (event instanceof EventKeyPressed) {
			EventKeyPressed eventKeyPressed = (EventKeyPressed) event;
			this.toggleCheatByKey(eventKeyPressed.getKeyPressed());
		}
	}
	
	public <T extends Cheat> Cheat getCheat(Class<T> clazz) {
		for (final Cheat cheat : this.cheatList) {
			if (cheat.getClass().equals(clazz)) {
				return cheat;
			}
		}
		return null;
	}
	
	public ArrayList<Cheat> getCheatList() {
		return this.cheatList;
	}
	
	private void toggleCheatByKey(int key) {
		for (final Cheat cheat : this.cheatList) {
			if (cheat.getCheatKeybind() == key) {
				cheat.toggleCheat();
			}
		}
	}
}
