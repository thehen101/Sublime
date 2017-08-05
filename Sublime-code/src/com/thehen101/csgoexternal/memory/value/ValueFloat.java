package com.thehen101.csgoexternal.memory.value;

import com.thehen101.csgoexternal.CSGOExternal;

public class ValueFloat extends MemoryReference {
	private float value;

	public ValueFloat(int memoryReference) {
		this(memoryReference, CSGOExternal.INSTANCE.getCSGOProcess().readFloat(memoryReference));
	}
	
	public ValueFloat(int memoryReference, float value) {
		super(memoryReference);
		this.value = value;
	}
	
	public float getValueFloat() {
		return this.value;
	}
	
	public void setValueFloat(float newValue) {
		CSGOExternal.INSTANCE.getCSGOProcess().writeFloat(this.getMemoryReference(), newValue);
		this.value = newValue;
	}
}
