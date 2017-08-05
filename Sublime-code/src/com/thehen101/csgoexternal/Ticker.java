package com.thehen101.csgoexternal;

import com.github.jonatino.misc.MemoryBuffer;
import com.thehen101.csgoexternal.event.EventLocalPlayerUpdate;
import com.thehen101.csgoexternal.event.EventTick;
import com.thehen101.csgoexternal.memory.Netvar;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.gameobject.EntityPlayer;
import com.thehen101.csgoexternal.memory.value.ValueBoolean;
import com.thehen101.csgoexternal.memory.value.ValueFloat;
import com.thehen101.csgoexternal.memory.value.ValueInteger;

public class Ticker {
	private final int delayMS;

	public Ticker(int ticksPerSecond) {
		this.delayMS = 1000 / ticksPerSecond;
	}

	public void startTicker() {
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
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventTick());
		
		EntityPlayer localPlayer = new EntityPlayer();
		final int lpa = Offset.LOCALPLAYER.getAddress();
		MemoryBuffer localPlayerMemory = CSGOExternal.INSTANCE.getCSGOProcess().read(lpa,
				localPlayer.bytesToRead());
		ValueFloat[] pos = new ValueFloat[] { 
				new ValueFloat(lpa + Netvar.ORIGIN.getOffset(), localPlayerMemory.getFloat(Netvar.ORIGIN.getOffset())),
				new ValueFloat(lpa + Netvar.ORIGIN.getOffset() + 0x8, localPlayerMemory.getFloat(Netvar.ORIGIN.getOffset()) + 0x8),
				new ValueFloat(lpa + Netvar.ORIGIN.getOffset() + 0x4, localPlayerMemory.getFloat(Netvar.ORIGIN.getOffset()) + 0x4) };
		ValueInteger health = new ValueInteger(lpa + Netvar.HEALTH.getOffset(),
				localPlayerMemory.getInt(Netvar.HEALTH.getOffset()));
		ValueInteger lifeState = new ValueInteger(lpa + Netvar.LIFESTATE.getOffset(),
				localPlayerMemory.getInt(Netvar.LIFESTATE.getOffset()));
		ValueInteger flags = new ValueInteger(lpa + Netvar.FLAGS.getOffset(),
				localPlayerMemory.getInt(Netvar.FLAGS.getOffset()));
		ValueInteger team = new ValueInteger(lpa + Netvar.TEAM.getOffset(),
				localPlayerMemory.getInt(Netvar.TEAM.getOffset()));
		ValueFloat[] viewOffset = new ValueFloat[] { 
				new ValueFloat(lpa + Netvar.VIEWOFFSET.getOffset(), localPlayerMemory.getFloat(Netvar.VIEWOFFSET.getOffset())),
				new ValueFloat(lpa + Netvar.VIEWOFFSET.getOffset() + 0x8, localPlayerMemory.getFloat(Netvar.VIEWOFFSET.getOffset()) + 0x8),
				new ValueFloat(lpa + Netvar.VIEWOFFSET.getOffset() + 0x4, localPlayerMemory.getFloat(Netvar.VIEWOFFSET.getOffset()) + 0x4) };
		ValueBoolean immune = new ValueBoolean(lpa + Netvar.IMMUNE.getOffset(),
				localPlayerMemory.getByte(Netvar.IMMUNE.getOffset()) == 0 ? false : true);
		localPlayer.setPos(pos);
		localPlayer.setHealth(health);
		localPlayer.setLifeState(lifeState);
		localPlayer.setFlags(flags);
		localPlayer.setTeam(team);
		localPlayer.setViewOffset(viewOffset);
		localPlayer.setImmune(immune);
		CSGOExternal.INSTANCE.getEventManager().callEvent(new EventLocalPlayerUpdate(localPlayer));
	}
}
