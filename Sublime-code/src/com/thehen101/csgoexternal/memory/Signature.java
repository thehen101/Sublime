package com.thehen101.csgoexternal.memory;

import com.thehen101.csgoexternal.util.TypeUtil;

public class Signature {
	private String stringSignature;
	private final transient int[] signature;
	private final Dereference[] dereferences;
	private final int signatureLength;
	private final Offset offset;
	private final byte offsetFromStart;
	private final ModuleLocation module;
	public transient int bytesFoundCounter;
	
	public Signature(Offset off, String sig, byte offset, int sigLength, Dereference[] deref, ModuleLocation mod) {
		this(off, TypeUtil.stringToByteArray(sig), offset, sigLength, deref, mod);
		this.stringSignature = sig;
	}

	public Signature(Offset off, int[] sig, byte offset, int sigLength, Dereference[] deref, ModuleLocation mod) {
		this.offset = off;
		this.signature = sig;
		this.dereferences = deref;
		this.offsetFromStart = offset;
		this.signatureLength = sigLength;
		this.module = mod;
	}

	public Offset getOffsetEnum() {
		return this.offset;
	}

	public int[] getSignature() {
		return this.signature;
	}

	public int getSignatureLength() {
		return this.signatureLength;
	}

	public byte getOffset() {
		return this.offsetFromStart;
	}

	public Dereference[] getDereferences() {
		return dereferences;
	}

	public String getStringSignature() {
		return stringSignature;
	}

	public ModuleLocation getModule() {
		return this.module;
	}

	public enum ModuleLocation {
		CLIENT, ENGINE
	}
}
