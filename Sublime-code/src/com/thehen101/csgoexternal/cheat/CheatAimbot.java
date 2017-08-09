package com.thehen101.csgoexternal.cheat;

import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventEntityPlayerLooped;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventMouseButtonPressed;
import com.thehen101.csgoexternal.event.EventMouseButtonReleased;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.value.ValueFloat;

public class CheatAimbot extends Cheat {
	private EntityPlayer localPlayer, target;
	private final float fov = 4F, speed = 50.0F;
	private final int mouseHeldButton = 5;
	public boolean canAim;

	public CheatAimbot(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventMouseButtonPressed) {
			EventMouseButtonPressed buttonPressed = (EventMouseButtonPressed) event;
			if (buttonPressed.getButtonPressed() == this.mouseHeldButton)
				this.canAim = true;
		}
		if (event instanceof EventMouseButtonReleased) {
			EventMouseButtonReleased buttonReleased = (EventMouseButtonReleased) event;
			if (buttonReleased.getButtonReleased() == this.mouseHeldButton)
				this.canAim = false;
		}
		if (event instanceof EventEntityPlayerLooped) {
			if (this.localPlayer == null || !this.canAim)
				return;
			EventEntityPlayerLooped otherPlayerLoopedEvent = (EventEntityPlayerLooped) event;
			EntityPlayer otherPlayer = otherPlayerLoopedEvent.getPlayer();
			if (otherPlayer.getBaseAddress() == 0)
				return;
			if (otherPlayer.getHealth().getValueInteger() >= 1 
					&& otherPlayer.getLifeState().getValueInteger() == 0 
					&& otherPlayer.getImmune().getValueBoolean() == false
					&& otherPlayer.getTeam().getValueInteger() != this.localPlayer.getTeam().getValueInteger()
					&& otherPlayer.getFlags().getValueInteger() != 0
					&& otherPlayer.getDormant().getValueInteger() == 0
					&& otherPlayer.getBoneManagerAddress() != 0) {
				float[] aimPos = this.getPlayerAimPosition();
				float[] target = this.getBonePosition(otherPlayer.getBoneManagerAddress(), 8);
				float[] angles = new float[] { this.getPitch(aimPos, target), this.getYaw(aimPos, target) };
				ValueFloat[] aimAngles = this.getPlayerAimAngles();
				if (CSGOExternal.INSTANCE.getCheatManager().getCheat(CheatRecoilControl.class).isEnabled()) {
					ValueFloat[] vp = this.getPlayerViewPunch(this.localPlayer.getBaseAddress());
					angles[0] -= vp[0].getValueFloat() * 2.0F;
					angles[1] -= vp[1].getValueFloat() * 2.0F;
				}
				this.fixAngles(angles);
				float[] myAngles = new float[] { aimAngles[0].getValueFloat(), aimAngles[1].getValueFloat() };
				float[] angDiff = this.getAngDiff(myAngles, angles);
				if (this.canAim(angDiff))
					this.target = otherPlayer;
			}
			
		}
		if (event instanceof EventTick) {
			if (this.localPlayer == null || this.target == null || !this.canAim)
				return;
			float[] aimPos = this.getPlayerAimPosition();
			float[] target = this.getBonePosition(this.target.getBoneManagerAddress(), 8);
			float[] angles = new float[] { this.getPitch(aimPos, target), this.getYaw(aimPos, target) };
			ValueFloat[] aimAngles = this.getPlayerAimAngles();
			if (CSGOExternal.INSTANCE.getCheatManager().getCheat(CheatRecoilControl.class).isEnabled()) {
				ValueFloat[] vp = this.getPlayerViewPunch(this.localPlayer.getBaseAddress());
				angles[0] -= vp[0].getValueFloat() * 2.0F;
				angles[1] -= vp[1].getValueFloat() * 2.0F;
			}
			this.fixAngles(angles);
			float[] smoothedAngles = new float[] { aimAngles[0].getValueFloat(), aimAngles[1].getValueFloat() };
			float[] angDiff = this.getAngDiff(smoothedAngles, angles);
			if (!this.canAim(angDiff))
				return;
			smoothedAngles[0] = smoothedAngles[0] + (angDiff[0] / this.speed);
			smoothedAngles[1] = smoothedAngles[1] + (angDiff[1] / this.speed);
			this.fixAngles(smoothedAngles);
			aimAngles[0].setValueFloat(smoothedAngles[0]);
			aimAngles[1].setValueFloat(smoothedAngles[1]);
			this.target = null;
		}
	}
	
	@Override
	public void onDisable() {
		this.target = null;
		this.localPlayer = null;
		this.canAim = false;
	}
	
	public ValueFloat[] getPlayerViewPunch(final int playerAddress) {
		final int punchAddress = playerAddress + Netvar.VIEWPUNCH.getOffset();
		return new ValueFloat[] { new ValueFloat(punchAddress), 
				new ValueFloat(punchAddress + 0x4) };
	}
	
	private float[] getAngDiff(float[] myAngles, float[] snapAngles) {
		return new float[] { (snapAngles[0] - myAngles[0]), this.clampYaw(snapAngles[1] - myAngles[1]) };
	}
	
	private boolean canAim(float[] angles) {
		if (angles[0] < -this.fov || angles[0] > this.fov
				|| angles[1] < -this.fov || angles[1] > this.fov)
			return false;
		return true;
	}
	
	private float clampYaw(float yaw) {
		float newYaw = yaw;
		while (newYaw > 180.0F)
			newYaw -= 360.0F;
		while (newYaw < -180.0F)
			newYaw += 360.0F;
		return newYaw;
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
