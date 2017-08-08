package com.thehen101.csgoexternal.memory;

public enum Netvar {
	HEALTH("Health"),
	FLAGS("Flags"),
	TEAM("Team"),
	DORMANT("Dormant"),
	ORIGIN("Origin"),
	VIEWOFFSET("ViewOffset"),
	LIFESTATE("LifeState"),
	BONEMANAGER("BoneManager"),
	VIEWPUNCH("ViewPunch"),
	IMMUNE("Immune"),
	GLOWINDEX("GlowIndex");

	private final String name;
	private int offset;
	
	Netvar(String name) {
		this(name, 0);
	}
	
	Netvar(String name, int offset) {
		this.name = name;
		this.offset = offset;
	}

	public String getName() {
		return this.name;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int newOffset) {
		this.offset = newOffset;
	}
}
