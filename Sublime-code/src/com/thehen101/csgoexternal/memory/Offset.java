package com.thehen101.csgoexternal.memory;

public enum Offset {
	LOCALPLAYER("LocalPlayer"),
	ENTITYLIST("EntityList"),
	FORCEJUMP("ForceJump"),
	FORCELEFT("ForceLeft"),
	FORCERIGHT("ForceRight"),
	ENGINEPOINTER("EnginePointer"),
	SETVIEWANGLES("SetViewAngles"),
	ENTITYLOOPDISTANCE("EntityLoopDistance");

	private final String name;
	private Signature signature;
	private int address;

	Offset(String name) {
		this(name, null);
	}

	Offset(String name, Signature sig) {
		this.name = name;
		this.signature = sig;
	}

	public String getName() {
		return this.name;
	}

	public Signature getSignature() {
		return this.signature;
	}

	public int getAddress() {
		return this.address;
	}

	public void setAddress(int newAddress) {
		this.address = newAddress;
	}

	public void setSignature(Signature newSig) {
		this.signature = newSig;
	}
}
