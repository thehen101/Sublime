package com.thehen101.csgoexternal.memory.gameobject;

import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.value.ValueFloat;
import com.thehen101.csgoexternal.memory.value.ValueInteger;

public class Entity {
	private ValueFloat[] pos; //x, y, z
	private ValueInteger health, lifeState, flags, team, dormant;
	private int baseAddress;
	
	public Entity() {
		this(null, null, null, null, null, null, 0); //TODO: this looks ugly and it's probably a bad practice
	}
	
	public Entity(ValueFloat[] position, ValueInteger health, ValueInteger lst,
			ValueInteger flags, ValueInteger team, ValueInteger dormant, int baseAddress) {
		this.pos = position;
		this.health = health;
		this.lifeState = lst;
		this.flags = flags;
		this.team = team;
		this.dormant = dormant;
		this.baseAddress = baseAddress;
	}
	
	public Netvar[] getNetvarOffsets() {
		return new Netvar[] { Netvar.ORIGIN, Netvar.HEALTH, Netvar.LIFESTATE, Netvar.FLAGS,
				Netvar.TEAM, Netvar.DORMANT };
	}
	
	public int bytesToRead() {
		Netvar[] netvars = this.getNetvarOffsets();
		int maxOffset = 0;
		for (Netvar n : netvars) {
			int o = n.getOffset();
			if (o > maxOffset)
				maxOffset = o;
		}
		return maxOffset + 0x4; //TODO: don't hardcode 4 extra bytes
	}

	public ValueFloat[] getPos() {
		return pos;
	}

	public void setPos(ValueFloat[] pos) {
		this.pos = pos;
	}

	public ValueInteger getHealth() {
		return health;
	}

	public void setHealth(ValueInteger health) {
		this.health = health;
	}

	public ValueInteger getLifeState() {
		return lifeState;
	}

	public void setLifeState(ValueInteger lifeState) {
		this.lifeState = lifeState;
	}

	public ValueInteger getFlags() {
		return flags;
	}

	public void setFlags(ValueInteger flags) {
		this.flags = flags;
	}

	public ValueInteger getTeam() {
		return team;
	}

	public void setTeam(ValueInteger team) {
		this.team = team;
	}
	
	public ValueInteger getDormant() {
		return dormant;
	}

	public void setDormant(ValueInteger dormant) {
		this.dormant = dormant;
	}

	public int getBaseAddress() {
		return baseAddress;
	}

	public void setBaseAddress(int baseAddress) {
		this.baseAddress = baseAddress;
	}
}
