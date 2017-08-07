package com.thehen101.csgoexternal.cheat;

import com.thehen101.csgoexternal.cheat.base.Cheat;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventPlayerGlowLooped;
import com.thehen101.csgoexternal.event.base.Event;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.gameobject.GlowEntity;

public class CheatGlowESP extends Cheat {
	private EntityPlayer localPlayer;

	public CheatGlowESP(String cheatName, int cheatKeybind) {
		super(cheatName, cheatKeybind);
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventLocalPlayerUpdate) {
			EventLocalPlayerUpdate update = (EventLocalPlayerUpdate) event;
			this.localPlayer = update.getLocalPlayer();
		}
		if (event instanceof EventPlayerGlowLooped) {
			EventPlayerGlowLooped glowLooped = (EventPlayerGlowLooped) event;
			EntityPlayer otherPlayer = glowLooped.getPlayer();
			GlowEntity glowEntity = glowLooped.getGlow();
			if (otherPlayer.getDormant().getValueInteger() != 0)
				return;
			float[] colours = new float[4];
			if (this.localPlayer != null
					&& this.localPlayer.getTeam().getValueInteger() == otherPlayer.getTeam().getValueInteger()) {
				colours[0] = 0.0F;
				colours[1] = 0.0F;
				colours[2] = 1.0F;
				colours[3] = 1.0F;
			} else {
				float health = (float) (otherPlayer.getHealth().getValueInteger() / 100.0F);
				colours[0] = 1.0F - health;
				colours[1] = health * 0.5F;
				colours[2] = 0.0F;
				colours[3] = 1.0F;
			}
			glowEntity.setR(colours[0]);
			glowEntity.setG(colours[1]);
			glowEntity.setB(colours[2]);
			glowEntity.setA(colours[3]);
			glowEntity.setRenderU(true);
			glowEntity.setRenderO(false);
			glowEntity.write();
		}
	}

}
