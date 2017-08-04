package com.thehen101.csgoexternal.memory.event.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
	private final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

	public final void addListener(Listener listener) {
		if (this.listeners.contains(listener))
			return;
		this.listeners.add(listener);
	}

	public final void removeListener(Listener listener) {
		if (!this.listeners.contains(listener))
			return;
		this.listeners.remove(listener);
	}

	public final void callEvent(Event event) {
		for (Listener listeners : this.listeners)
			if (listeners != null)
				listeners.onEvent(event);
	}

	public final List<Listener> getListeners() {
		return this.listeners;
	}
}
