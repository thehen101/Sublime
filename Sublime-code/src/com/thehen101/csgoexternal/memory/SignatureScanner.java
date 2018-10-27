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
		//re-written, old sig scanner was a pile of crap
		if (this.offsets.length == 0)
			return;
		
		int[] firstBytes = new int[this.offsets.length];
		
		for (int i = 0; i < this.offsets.length; i++)
			firstBytes[i] = this.offsets[i].getSignature().getSignature()[0];
		
		MemoryBuffer moduleMemory = this.module.read(0, this.module.size());
		for (int currentOffset = 0; currentOffset < moduleMemory.size(); currentOffset++) {
			int currentByte = moduleMemory.getByte(currentOffset);
			
			for (int i = 0; i < firstBytes.length; i++) {
				if (currentByte == firstBytes[i]) {
					//We have found the first byte to be correct, now scan all the other ones.
					//It's a little inefficient, but it has a 100% success rate.
					Offset offset = this.offsets[i];
					if (offset != null) {
						Signature sig = offset.getSignature();
						boolean validSig = true;
						for (int a = 0; a < sig.getSignatureLength(); a++) {
							int b = moduleMemory.getByte(currentOffset + a);
							int toMatch = sig.getSignature()[a];
							if (!(b == toMatch || toMatch == 0)) {
								validSig = false;
								break;
							}
						}
						if (validSig) {
							int address = currentOffset;
							System.out.print(offset.getName() + " signature found in memory! Location: 0x"
									+ Integer.toHexString(address));
							offset.setAddress(address + sig.getOffset());
							for (int d = 0; d < sig.getDereferences().length; d++) {
								Dereference deref = sig.getDereferences()[d];
								switch (deref.getLocation()) {
								case MODULE:
									offset.setAddress(this.module.readInt(offset.getAddress()) + deref.getOffset());
									break;
								case PROCESS:
									offset.setAddress(
											this.process.readInt(offset.getAddress()) + deref.getOffset());
									break;
								}
							}
							System.out.println(", final address: 0x" + Integer.toHexString(offset.getAddress()));
							this.offsets[i] = null;
						}
					}
				}
			}
		}
	}
}
