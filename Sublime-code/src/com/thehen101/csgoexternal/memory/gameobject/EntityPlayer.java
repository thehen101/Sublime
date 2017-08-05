package com.thehen101.csgoexternal.memory.gameobject;

import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.value.ValueBoolean;
import com.thehen101.csgoexternal.memory.value.ValueFloat;
import com.thehen101.csgoexternal.memory.value.ValueInteger;

public class EntityPlayer extends Entity {
	private ValueFloat[] viewOffset; //x, y, z
	private ValueBoolean immune;
	private int boneManagerAddress;
	
	public EntityPlayer() {
		this(null, null, null, null, null, 0, null, null, 0);
	}
	
	public EntityPlayer(ValueFloat[] position, ValueInteger health, ValueInteger lst, ValueInteger flags,
			ValueInteger team, int baseAddress, ValueFloat[] viewOffset, ValueBoolean isImmune,
			int boneManager) {
		super(position, health, lst, flags, team, baseAddress);
		this.viewOffset = viewOffset;
		this.immune = isImmune;
		this.boneManagerAddress = boneManager;
	}
	
	@Override
	public int[] getNetvarOffsets() {
		return new int[] { Netvar.ORIGIN.getOffset(), Netvar.HEALTH.getOffset(), Netvar.LIFESTATE.getOffset(),
				Netvar.FLAGS.getOffset(), Netvar.TEAM.getOffset(), Netvar.VIEWOFFSET.getOffset(),
				Offset.BONEMANAGER.getAddress(), Netvar.IMMUNE.getOffset() };
	}

	public ValueFloat[] getViewOffset() {
		return viewOffset;
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
