package com.thehen101.csgoexternal.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.event.EventKeyPressed;
import com.thehen101.csgoexternal.event.EventKeyReleased;
import com.thehen101.csgoexternal.event.EventMouseButtonPressed;
import com.thehen101.csgoexternal.event.EventMouseButtonReleased;

public class PeripheralListener extends Thread implements NativeKeyListener, NativeMouseInputListener {

	@Override
	public void run() {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException localNativeHookException) {
		}
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		//GlobalScreen.addNativeMouseMotionListener(this);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventKeyPressed(e.getKeyCode()));
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventKeyReleased(e.getKeyCode()));
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent nme) {
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent nme) {
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventMouseButtonPressed(nme.getButton()));
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent nme) {
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventMouseButtonReleased(nme.getButton()));
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent nme) {
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent nme) {
	}
}
