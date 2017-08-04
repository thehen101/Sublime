package com.thehen101.csgoexternal.memory;

public class Dereference {
	private final int offset;
	private final Location location;

	public Dereference(int offset, Location loc) {
		this.offset = offset;
		this.location = loc;
	}

	public int getOffset() {
		return offset;
	}

	public Location getLocation() {
		return location;
	}

	public enum Location {
		PROCESS, MODULE
	}
}
