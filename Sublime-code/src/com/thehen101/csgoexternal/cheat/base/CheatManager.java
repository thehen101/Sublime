package com.thehen101.csgoexternal.cheat.base;

import java.util.ArrayList;

import org.jnativehook.keyboard.NativeKeyEvent;

import com.thehen101.csgoexternal.cheat.CheatAutostrafer;
import com.thehen101.csgoexternal.cheat.CheatBunnyhop;

public class CheatManager {
	
	private final ArrayList<Cheat> cheatList = new ArrayList<Cheat>();
	
	public CheatManager() {
		this.cheatList.add(new CheatAutostrafer("Autostrafer", NativeKeyEvent.VC_PAGE_UP));
		this.cheatList.add(new CheatBunnyhop("Bunnyhop", NativeKeyEvent.VC_PAGE_DOWN));
	}
	
	public <T extends Cheat> Cheat getCheat(Class<T> clazz) {
		for (final Cheat cheat : this.cheatList) {
			if (cheat.getClass().equals(clazz)) {
				return cheat;
			}
		}
		return null;
	}
	
	public void toggleCheatByKey(int key) {
		for (final Cheat cheat : this.cheatList) {
			if (cheat.getCheatKeybind() == key) {
				cheat.toggleCheat();
			}
		}
	}
	
	public ArrayList<Cheat> getCheatList() {
		return this.cheatList;
	}
}
