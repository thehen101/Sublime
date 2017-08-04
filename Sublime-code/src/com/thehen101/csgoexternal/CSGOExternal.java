package com.thehen101.csgoexternal;

import java.io.File;
import java.util.ArrayList;

import com.github.jonatino.process.Module;
import com.github.jonatino.process.Process;
import com.github.jonatino.process.Processes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.Signature;
import com.thehen101.csgoexternal.memory.SignatureScanner;
import com.thehen101.csgoexternal.util.FileUtil;
import com.thehen101.csgoexternal.util.SignatureDeserialiser;

public enum CSGOExternal {
	INSTANCE;

	private Process csgoProcess;
	private Module clientModule, engineModule;
	
	/**
	 * Initialises the cheat - this method is currently unfinished.
	 */
	public void initialise() {
		csgoProcess = Processes.byName(Constants.PROCESS_NAME);
		clientModule = csgoProcess.findModule(Constants.CLIENT_DLL_NAME);
		engineModule = csgoProcess.findModule(Constants.ENGINE_DLL_NAME);

		this.readSignaturesFile();
		this.performSignatureScan();
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
	}

	public Process getCsgoProcess() {
		return csgoProcess;
	}

	public Module getClientModule() {
		return clientModule;
	}

	public Module getEngineModule() {
		return engineModule;
	}
}
