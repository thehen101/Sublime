package com.thehen101.csgoexternal.memory;

public enum Netvar {
	HEALTH("Health"),
	FLAGS("Flags"),
	TEAM("Team"),
	ORIGIN("Origin"),
	VIEWOFFSET("ViewOffset"),
	LIFESTATE("LifeState"),
	BONEMANAGER("BoneManager"),
	IMMUNE("Immune");

	private final String name;
	private short offset;
	
	Netvar(String name) {
		this(name, (short) 0);
	}
	
	Netvar(String name, short offset) {
		this.name = name;
		this.offset = offset;
	}

	public String getName() {
		return this.name;
	}
	
	public short getOffset() {
		return this.offset;
	}
	
	public void setOffset(short newOffset) {
		this.offset = newOffset;
	}
}
