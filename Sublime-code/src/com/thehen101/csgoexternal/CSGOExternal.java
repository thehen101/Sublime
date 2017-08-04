package com.thehen101.csgoexternal;

import java.io.File;
import java.util.ArrayList;

import com.github.jonatino.process.Module;
import com.github.jonatino.process.Process;
import com.github.jonatino.process.Processes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thehen101.csgoexternal.cheat.base.CheatManager;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.Signature;
import com.thehen101.csgoexternal.memory.SignatureScanner;
import com.thehen101.csgoexternal.memory.event.base.EventManager;
import com.thehen101.csgoexternal.util.Constants;
import com.thehen101.csgoexternal.util.FileUtil;
import com.thehen101.csgoexternal.util.KeyListener;
import com.thehen101.csgoexternal.util.SignatureDeserialiser;

public enum CSGOExternal {
	INSTANCE;

	private Process csgoProcess;
	private Module clientModule, engineModule;
	private CheatManager cheatManager;
	private EventManager eventManager;
	private KeyListener keyListener;
	private Ticker ticker;
	
	/**
	 * Initialises the cheat - this method is currently unfinished.
	 */
	public void initialise() {
		this.csgoProcess = Processes.byName(Constants.PROCESS_NAME);
		this.clientModule = csgoProcess.findModule(Constants.CLIENT_DLL_NAME);
		this.engineModule = csgoProcess.findModule(Constants.ENGINE_DLL_NAME);
		this.eventManager = new EventManager();
		this.cheatManager = new CheatManager();
		this.keyListener = new KeyListener();
		this.ticker = new Ticker(500);

		this.readSignaturesFile();
		this.performSignatureScan();
		
		this.keyListener.start();
		this.ticker.startTicker();
	}
	
	/**
	 * This method reads the signature JSON file and sets assigns each of the offsets their
	 * required signature.
	 */
	private void readSignaturesFile() {
		Gson gsona = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Signature.class, new SignatureDeserialiser()).create();
		Signature[] sigs = gsona.fromJson(FileUtil.fileToString(new File(Constants.SIGNATURE_FILE_NAME)), 
				Signature[].class);

		for (Signature s : sigs) {
			s.getOffsetEnum();
			s.getOffsetEnum().setSignature(s);
			System.out.println(
					s.getOffsetEnum().getName() + " -> " + s.getOffsetEnum().getSignature().getStringSignature());
		}
	}
	
	/**
	 * This method sorts and then scans for the signatures in the game's memory. Note that the actual
	 * scanning is passed down to a SignatureScanner.
	 */
	private void performSignatureScan() {
		ArrayList<Offset> clientOffsets = new ArrayList<Offset>(), engineOffsets = new ArrayList<Offset>();

		for (Offset o : Offset.values()) {
			switch (o.getSignature().getModule()) {
			case CLIENT:
				clientOffsets.add(o);
				break;
			case ENGINE:
				engineOffsets.add(o);
				break;
			}
		}

		Offset[] clientOffsetArray = new Offset[clientOffsets.size()], engineOffsetArray = new Offset[engineOffsets.size()];
		clientOffsetArray = clientOffsets.toArray(clientOffsetArray);
		engineOffsetArray = engineOffsets.toArray(engineOffsetArray);

		clientOffsets.clear();
		engineOffsets.clear();

		SignatureScanner clientSigScanner = new SignatureScanner(this.csgoProcess, this.clientModule, clientOffsetArray),
				engineSigScanner = new SignatureScanner(this.csgoProcess, this.engineModule, engineOffsetArray);

		clientSigScanner.scan();
		engineSigScanner.scan();
		
		System.out.println("Scanned for offsets - this cheat is ready to use. Press a keybind to get started.");
	}
	

	public Process getCSGOProcess() {
		return this.csgoProcess;
	}

	public Module getClientModule() {
		return this.clientModule;
	}

	public Module getEngineModule() {
		return this.engineModule;
	}
	
	public CheatManager getCheatManager() {
		return this.cheatManager;
	}
	
	public EventManager getEventManager() {
		return this.eventManager;
	}
}
