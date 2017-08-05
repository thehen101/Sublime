package com.thehen101.csgoexternal.cheat;

import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;

public class CheatAimbot extends Cheat {

	public CheatAimbot(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventTick) {
			//TODO
		}
	}
	
	private int getMaxEntities() {
		return CSGOExternal.INSTANCE.getCSGOProcess().readInt(
				Offset.ENGINEPOINTER.getAddress() + Offset.ENTITYLOOPDISTANCE.getAddress());
	}
	
	private float[] getPlayerAimPosition() {
		MemoryBuffer originBuffer = CSGOExternal.INSTANCE.getCSGOProcess().read(
				Offset.LOCALPLAYER.getAddress() + Netvar.ORIGIN.getOffset(), 0x4 * 3);
		float playerHeight = CSGOExternal.INSTANCE.getCSGOProcess().readFloat(
				Offset.LOCALPLAYER.getAddress() + Netvar.VIEWOFFSET.getOffset() + 0x8);
		float[] origin = new float[3];
		origin[0] = originBuffer.getFloat();
		origin[1] = originBuffer.getFloat(0x8) + playerHeight;
		origin[2] = originBuffer.getFloat(0x4);
		return origin;
	}
	
	private float getYaw(float[] start, float[] end) {
		double yaw = 0F;
		double xDiff = start[0] - end[0];
		double zDiff = start[2] - end[2];
		if ((xDiff < 0 && zDiff < 0) || (xDiff < 0 && zDiff > 0)) {
			yaw = Math.toDegrees(Math.atan(zDiff / xDiff)) - 90;
		} else if (xDiff > 0 && zDiff < 0) {
			zDiff = end[2] - start[2];
			yaw = Math.toDegrees(Math.atan(xDiff / zDiff));
		} else if (xDiff > 0 && zDiff > 0) {
			xDiff = end[0] - start[0];
			yaw = Math.toDegrees(Math.atan(xDiff / zDiff)) - 180;
		}
		return (float) yaw + 90;
	}
	
	private float getPitch(float[] start, float[] end) {
		final double xd = start[0] - end[0];
		final double yd = start[1] - end[1];
		final double zd = start[2] - end[2];
		final double xzDiff = Math.sqrt(xd * xd + zd * zd);
		return (float) Math.toDegrees(Math.atan(yd / xzDiff));
	}
}
