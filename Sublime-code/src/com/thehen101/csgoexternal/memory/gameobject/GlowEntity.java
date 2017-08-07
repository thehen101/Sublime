package com.thehen101.csgoexternal.memory.gameobject;

import com.github.jonatino.misc.Cacheable;
import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.util.TypeUtil;

public class GlowEntity {
	private final int address;
	private final MemoryBuffer mb;
	private float r, g, b, a;
	private boolean renderU, renderO;
	
	public GlowEntity(int address, MemoryBuffer mb) {
		this.address = address;
		this.mb = mb;
	}
	
	public void write() {
		byte[] struct = this.mb.getArray(), 
				r = TypeUtil.floatToByteArray(this.r),
				g = TypeUtil.floatToByteArray(this.g), 
				b = TypeUtil.floatToByteArray(this.b),
				a = TypeUtil.floatToByteArray(this.a);
		for (int i = 0; i < r.length; i++)
			struct[0x4 + i] = r[(r.length - 1) - i];
		for (int i = 0; i < g.length; i++)
			struct[0x8 + i] = g[(g.length - 1) - i];
		for (int i = 0; i < b.length; i++)
			struct[0xC + i] = b[(b.length - 1) - i];
		for (int i = 0; i < a.length; i++)
			struct[0x10 + i] = a[(a.length - 1) - i];
		struct[0x24] = (byte) (this.renderU ? 0x1 : 0x0);
		struct[0x25] = (byte) (this.renderO ? 0x1 : 0x0);
		this.mb.write(0, struct, 0, struct.length);
		CSGOExternal.INSTANCE.getCSGOProcess().write(Cacheable.pointer(this.address), this.mb);
	}

	public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}

	public void setA(float a) {
		this.a = a;
	}

	public void setRenderU(boolean renderU) {
		this.renderU = renderU;
	}

	public void setRenderO(boolean renderO) {
		this.renderO = renderO;
	}	
}
