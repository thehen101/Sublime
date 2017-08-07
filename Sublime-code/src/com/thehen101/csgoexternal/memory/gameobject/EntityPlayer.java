package com.thehen101.csgoexternal.memory.gameobject;

import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.value.ValueBoolean;
import com.thehen101.csgoexternal.memory.value.ValueFloat;
import com.thehen101.csgoexternal.memory.value.ValueInteger;

public class EntityPlayer extends Entity {
	private ValueFloat[] viewOffset; //x, y, z
	private ValueBoolean immune;
	private ValueInteger glowIndex;
	private int boneManagerAddress;
	
	public EntityPlayer() {
		this(null, null, null, null, null, null, 0, null, null, 0, null);
	}
	
	public EntityPlayer(ValueFloat[] position, ValueInteger health, ValueInteger lst, ValueInteger flags,
			ValueInteger team, ValueInteger dormant, int baseAddress, ValueFloat[] viewOffset, ValueBoolean isImmune,
			int boneManager, ValueInteger glowIndex) {
		super(position, health, lst, flags, team, dormant, baseAddress);
		this.viewOffset = viewOffset;
		this.immune = isImmune;
		this.boneManagerAddress = boneManager;
		this.glowIndex = glowIndex;
	}
	
	@Override
	public Netvar[] getNetvarOffsets() {
		return new Netvar[] { Netvar.ORIGIN, Netvar.HEALTH, Netvar.LIFESTATE, Netvar.FLAGS, Netvar.TEAM,
				Netvar.VIEWOFFSET, Netvar.BONEMANAGER, Netvar.IMMUNE, Netvar.GLOWINDEX };
	}

	public ValueFloat[] getViewOffset() {
		return viewOffset;
	}

	public ValueInteger getGlowIndex() {
		return glowIndex;
	}

	public void setGlowIndexAddress(ValueInteger glowIndex) {
		this.glowIndex = glowIndex;
	}

	public void setViewOffset(ValueFloat[] viewOffset) {
		this.viewOffset = viewOffset;
	}

	public int getBoneManagerAddress() {
		return boneManagerAddress;
	}

	public void setBoneManagerAddress(int boneManagerAddress) {
		this.boneManagerAddress = boneManagerAddress;
	}

	public ValueBoolean getImmune() {
		return immune;
	}

	public void setImmune(ValueBoolean immune) {
		this.immune = immune;
	}
}
