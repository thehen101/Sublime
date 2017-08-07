package com.thehen101.csgoexternal.cheat;

import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventEntityPlayerLooped;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.value.ValueFloat;

public class CheatAimbot extends Cheat {
	private EntityPlayer localPlayer, target;

	public CheatAimbot(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventEntityPlayerLooped) {
			if (this.localPlayer == null)
				return;
			EventEntityPlayerLooped otherPlayerLoopedEvent = (EventEntityPlayerLooped) event;
			EntityPlayer otherPlayer = otherPlayerLoopedEvent.getPlayer();
			if (otherPlayer.getBaseAddress() == 0 || this.target != null)
				return;
			if (otherPlayer.getHealth().getValueInteger() >= 1 
					&& otherPlayer.getLifeState().getValueInteger() == 0 
					&& otherPlayer.getImmune().getValueBoolean() == false
					&& otherPlayer.getTeam().getValueInteger() != this.localPlayer.getTeam().getValueInteger()
					&& otherPlayer.getFlags().getValueInteger() != 0
					&& otherPlayer.getDormant().getValueInteger() == 0) {
				this.target = otherPlayer;
			}
			
		}
		if (event instanceof EventTick) {
			if (this.localPlayer == null || this.target == null)
				return;
			float[] aimPos = this.getPlayerAimPosition();
			float[] target = this.getBonePosition(this.target.getBoneManagerAddress(), 8);
			float yaw = this.getYaw(aimPos, target);
			float pitch = this.getPitch(aimPos, target);
			float[] angles = new float[] { pitch, yaw };
			this.fixAngles(angles);
			ValueFloat[] aimAngles = this.getPlayerAimAngles();
			aimAngles[0].setValueFloat(angles[0]);
			aimAngles[1].setValueFloat(angles[1]);
			this.target = null;
		}
	}
	
	private void fixAngles(float[] angle) {
		while (angle[1] > 180.0F)
			angle[1] -= 360.0F;
		while (angle[1] < -180.0F)
			angle[1] += 360.0F;
		if (angle[0] > 89.0F)
			angle[0] = 89.0F;
		if (angle[0] < -89.0F)
			angle[0] = -89.0F;
		if (angle[1] > 180.0F)
			angle[1] = 180.0F;
		if (angle[1] < -180.0F)
			angle[1] = -180.0F;
	}
	
	private ValueFloat[] getPlayerAimAngles() {
		final int angleAddress = Offset.ENGINEPOINTER.getAddress() + 
				Offset.SETVIEWANGLES.getAddress();
		return new ValueFloat[] { new ValueFloat(angleAddress), 
				new ValueFloat(angleAddress + 0x4) };
	}
	
	private float[] getBonePosition(int boneManagerAddress, int boneId) {
		MemoryBuffer boneBuffer = CSGOExternal.INSTANCE.getCSGOProcess().read(
				boneManagerAddress + (0x30 * boneId), 0x30);
		return new float[] { boneBuffer.getFloat(0xC), boneBuffer.getFloat(0x2C),
				boneBuffer.getFloat(0x1C)};
	}
	
	private float[] getPlayerAimPosition() {
		float[] origin = new float[3];
		origin[0] = this.localPlayer.getPos()[0].getValueFloat() 
				+ this.localPlayer.getViewOffset()[0].getValueFloat();
		origin[1] = this.localPlayer.getPos()[1].getValueFloat() 
				+ this.localPlayer.getViewOffset()[1].getValueFloat();
		origin[2] = this.localPlayer.getPos()[2].getValueFloat() 
				+ this.localPlayer.getViewOffset()[2].getValueFloat();
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
