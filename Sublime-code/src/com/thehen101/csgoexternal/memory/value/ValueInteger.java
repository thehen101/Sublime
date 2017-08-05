package com.thehen101.csgoexternal.memory.value;

import com.thehen101.csgoexternal.CSGOExternal;

public class ValueInteger extends MemoryReference {
	private int value;

	public ValueInteger(int memoryReference) {
		this(memoryReference, CSGOExternal.INSTANCE.getCSGOProcess().readInt(memoryReference));
	}
	
	public ValueInteger(int memoryReference, int value) {
		super(memoryReference);
		this.value = value;
	}
	
	public int getValueInteger() {
		return this.value;
	}
	
	public void setValueInteger(int newValue) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeInt(this.getMemoryReference(), newValue);
		this.value = newValue;
	}
}
