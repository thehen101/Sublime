package com.thehen101.csgoexternal.memory.value;

import com.thehen101.csgoexternal.CSGOExternal;

public class ValueBoolean extends MemoryReference {
	private boolean value;

	public ValueBoolean(int memoryReference) {
		this(memoryReference, CSGOExternal.INSTANCE.getCSGOProcess().readBoolean(memoryReference));
	}
	
	public ValueBoolean(int memoryReference, boolean value) {
		super(memoryReference);
		this.value = value;
	}
	
	public boolean getValueBoolean() {
		return this.value;
	}
	
	public void setValueBoolean(boolean newValue) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeBoolean(this.getMemoryReference(), newValue);
		this.value = newValue;
	}
}
