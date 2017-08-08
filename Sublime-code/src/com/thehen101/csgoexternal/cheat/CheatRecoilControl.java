package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.CSGOExternal;
import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.value.ValueFloat;

public class CheatRecoilControl extends Cheat {
	private EntityPlayer localPlayer;
	private float[] oldPunch = new float[2];

	public CheatRecoilControl(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}
	
	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventTick) {
			if (this.localPlayer == null 
					|| CSGOExternal.INSTANCE.getCheatManager().getCheat(CheatAimbot.class).isEnabled())
				return;
			ValueFloat[] angles = this.getPlayerAimAngles();
			float[] newAngles = this.fixRCS(new float[] { angles[0].getValueFloat(), angles[1].getValueFloat() }, 
					this.localPlayer);
			angles[0].setValueFloat(newAngles[0]);
			angles[1].setValueFloat(newAngles[1]);
		}
	}
	
	public float[] fixRCS(float[] playerAngles, EntityPlayer localPlayer) {
		ValueFloat[] vp = this.getPlayerViewPunch(localPlayer.getBaseAddress());
		float[] viewPunch = new float[] { vp[0].getValueFloat(), vp[1].getValueFloat() };
		float[] newAng = this.fixPunch(playerAngles, viewPunch);
		this.oldPunch[0] = viewPunch[0] * 2.0F;
		this.oldPunch[1] = viewPunch[1] * 2.0F;
		this.fixAngles(newAng);
		return newAng;
	}
	
	private float[] fixPunch(float[] aimAngles, float[] viewPunch) {
		return new float[] { ((aimAngles[0] + this.oldPunch[0]) - (viewPunch[0] * 2.0F)),
				((aimAngles[1] + this.oldPunch[1]) - (viewPunch[1] * 2.0F)) };
	}
	
	@Override
	public void onDisable() {
		this.oldPunch = new float[2];
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
	
	public ValueFloat[] getPlayerViewPunch(final int playerAddress) {
		final int punchAddress = playerAddress + Netvar.VIEWPUNCH.getOffset();
		return new ValueFloat[] { new ValueFloat(punchAddress), 
				new ValueFloat(punchAddress + 0x4) };
	}
}
