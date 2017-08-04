package com.thehen101.csgoexternal.memory;

import com.github.jonatino.misc.MemoryBuffer;
import com.github.jonatino.process.Module;
import com.github.jonatino.process.Process;

public class SignatureScanner {
	private final Process process;
	private final Module module;
	private final Offset[] offsets;

	public SignatureScanner(Process process, Module module, Offset[] offsets) {
		this.process = process;
		this.module = module;
		this.offsets = offsets;
	}
	
	/**
	 * Actually scans the module for the signatures. To make the process a little faster,
	 * an entire snapshot of the module is taken - as it is quicker to manipulate our own
	 * memory than a different processes memory. However, this has a side effect of using
	 * up a lot of memory in the JVM.
	 */
	public void scan() {
		if (this.offsets.length == 0)
			return;
		int[] firstBytes = new int[this.offsets.length];
		for (int i = 0; i < this.offsets.length; i++) {
			firstBytes[i] = this.offsets[i].getSignature().getSignature()[0];
		}
		MemoryBuffer moduleMemory = this.module.read(0, this.module.size());
		for (int currentOffset = 0; currentOffset < moduleMemory.size(); currentOffset++) {
			int currentByte = moduleMemory.getByte(currentOffset);
			for (int i = 0; i < firstBytes.length; i++) {
				Offset offset = this.offsets[i];
				Signature sig = offset.getSignature();
				if (currentByte == firstBytes[i] || sig.bytesFoundCounter != 0) {
					int cByte = sig.getSignature()[sig.bytesFoundCounter];
					if (currentByte == cByte || cByte == 0) {
						sig.bytesFoundCounter++;
						if (sig.bytesFoundCounter == sig.getSignatureLength()) {
							int address = currentOffset - (sig.bytesFoundCounter - 1);
							System.out.print(offset.getName() + " signature found in memory! Location: 0x"
									+ Integer.toHexString(address));
							sig.bytesFoundCounter = 0;
							offset.setAddress(address + sig.getOffset());
							for (int d = 0; d < sig.getDereferences().length; d++) {
								Dereference deref = sig.getDereferences()[d];
								switch (deref.getLocation()) {
								case MODULE:
									offset.setAddress(this.module.readInt(offset.getAddress()) + deref.getOffset());
									break;
								case PROCESS:
									offset.setAddress(this.process.readInt(offset.getAddress()) + deref.getOffset());
									break;
								}
							}
							System.out.println(", final address: 0x" + Integer.toHexString(offset.getAddress()));
						}
					} else {
						sig.bytesFoundCounter = 0;
					}
				} else {
					sig.bytesFoundCounter = 0;
				}
			}
		}
	}
}
