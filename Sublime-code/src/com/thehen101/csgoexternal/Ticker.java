package com.thehen101.csgoexternal;

import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.event.EventEntityPlayerLooped;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventPlayerGlowLooped;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.gameobject.GlowEntity;
import com.thehen101.csgoexternal.memory.value.ValueBoolean;
import com.thehen101.csgoexternal.memory.value.ValueFloat;
import com.thehen101.csgoexternal.memory.value.ValueInteger;

public class Ticker extends Thread {
	private final int delayMS;

	public Ticker(int ticksPerSecond) {
		this.delayMS = 1000 / ticksPerSecond;
	}

	@Override
	public void run() {
		for (;;) {
			this.tick();
			try {
				Thread.sleep(this.delayMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Ticks the cheat causing all of the cheats to run their code once. This
	 * method is essentially the backbone of the entire cheat as it is where the
	 * main events are called - and some memory is read here for performance
	 * purposes and the information received is passed down as an event: this is
	 * to prevent different cheats from all reading the same memory at the same
	 * time, therefore improving performance.
	 */
	private void tick() {
		EntityPlayer localPlayer = new EntityPlayer();
		final int playerAddress = CSGOExternal.INSTANCE.getCSGOProcess().readInt(Offset.LOCALPLAYER.getAddress());
		this.setupPlayer(localPlayer, playerAddress);
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventLocalPlayerUpdate(localPlayer));

		final int maxPlayers = 64;

		for (int i = 0; i < maxPlayers; i++) {
			EntityPlayer otherPlayer = new EntityPlayer();
			final int opa = CSGOExternal.INSTANCE.getCSGOProcess().readInt(
					Offset.ENTITYLIST.getAddress() + (0x10 * i));
			if (opa != 0) {
				if (this.setupPlayer(otherPlayer, opa)) {
					if (this.validatePlayer(otherPlayer)) {
						CSGOExternal.INSTANCE.getEventManager().callEvent(new EventEntityPlayerLooped(otherPlayer));
					
						int glowManagerAddress = CSGOExternal.INSTANCE.getCSGOProcess().readInt(Offset.GLOWMANAGER.getAddress());
						int glowAddress = glowManagerAddress + (0x38 * otherPlayer.getGlowIndex().getValueInteger());
						GlowEntity glowEntity = new GlowEntity(glowAddress, 
								CSGOExternal.INSTANCE.getCSGOProcess().read(glowAddress, 0x38));
						if (glowAddress != 0)
							CSGOExternal.INSTANCE.getEventManager().callEvent(new EventPlayerGlowLooped(otherPlayer, glowEntity));
					}
				}
			}
		}
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventTick());
	}

	/**
	 * Populates an EntityPlayer's fields by reading the values from memory.
	 * 
	 * @param ep
	 *            The EntityPlayer object.
	 * @param add
	 *            The EntityPlayer's offset in memory.
	 */
	private boolean setupPlayer(EntityPlayer ep, int add) {
		try {
			MemoryBuffer playerMemory = CSGOExternal.INSTANCE.getCSGOProcess().read(add, ep.bytesToRead());
			ValueFloat[] pos = new ValueFloat[] {
					new ValueFloat(add + Netvar.ORIGIN.getOffset(), playerMemory.getFloat(
							Netvar.ORIGIN.getOffset())),
					new ValueFloat(add + Netvar.ORIGIN.getOffset() + 0x8,
							playerMemory.getFloat(Netvar.ORIGIN.getOffset() + 0x8)),
					new ValueFloat(add + Netvar.ORIGIN.getOffset() + 0x4,
							playerMemory.getFloat(Netvar.ORIGIN.getOffset() + 0x4)) };
			ValueInteger health = new ValueInteger(add + Netvar.HEALTH.getOffset(),
					playerMemory.getInt(Netvar.HEALTH.getOffset()));
			ValueInteger lifeState = new ValueInteger(add + Netvar.LIFESTATE.getOffset(),
					playerMemory.getInt(Netvar.LIFESTATE.getOffset()));
			ValueInteger flags = new ValueInteger(add + Netvar.FLAGS.getOffset(),
					playerMemory.getInt(Netvar.FLAGS.getOffset()));
			ValueInteger team = new ValueInteger(add + Netvar.TEAM.getOffset(),
					playerMemory.getInt(Netvar.TEAM.getOffset()));
			ValueInteger dormant = new ValueInteger(add + Netvar.DORMANT.getOffset(),
					playerMemory.getInt(Netvar.DORMANT.getOffset()));
			ValueFloat[] viewOffset = new ValueFloat[] {
					new ValueFloat(add + Netvar.VIEWOFFSET.getOffset(),
							playerMemory.getFloat(Netvar.VIEWOFFSET.getOffset())),
					new ValueFloat(add + Netvar.VIEWOFFSET.getOffset() + 0x8,
							playerMemory.getFloat(Netvar.VIEWOFFSET.getOffset() + 0x8)),
					new ValueFloat(add + Netvar.VIEWOFFSET.getOffset() + 0x4,
							playerMemory.getFloat(Netvar.VIEWOFFSET.getOffset() + 0x4)) };
			ValueBoolean immune = new ValueBoolean(add + Netvar.IMMUNE.getOffset(),
					playerMemory.getByte(Netvar.IMMUNE.getOffset()) == 0 ? false : true);
			int bma = playerMemory.getInt(Netvar.BONEMANAGER.getOffset());
			ValueInteger glowIndex = new ValueInteger(add + Netvar.GLOWINDEX.getOffset(),
					playerMemory.getInt(Netvar.GLOWINDEX.getOffset()));
			ep.setPos(pos);
			ep.setHealth(health);
			ep.setLifeState(lifeState);
			ep.setFlags(flags);
			ep.setTeam(team);
			ep.setDormant(dormant);
			ep.setViewOffset(viewOffset);
			ep.setImmune(immune);
			ep.setBaseAddress(add);
			ep.setBoneManagerAddress(bma);
			ep.setGlowIndexAddress(glowIndex);
			return true;
		} catch (Exception e) {
			System.out.println(Integer.toHexString(add));
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean validatePlayer(EntityPlayer ep) {
		if (ep.getBaseAddress() == 0 || ep.getBoneManagerAddress() == 0)
			return false;
		if (ep.getDormant() == null || ep.getFlags() == null
				|| ep.getGlowIndex() == null || ep.getHealth() == null
				|| ep.getImmune() == null|| ep.getLifeState() == null
				|| ep.getNetvarOffsets() == null || ep.getPos() == null
				|| ep.getTeam() == null || ep.getViewOffset() == null)
			return false;
		try {
			MemoryBuffer boneBuffer = CSGOExternal.INSTANCE.getCSGOProcess().read(
					ep.getBoneManagerAddress(), 0x30);
			if (boneBuffer == null)
				throw new Exception();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
