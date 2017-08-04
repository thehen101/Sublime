package com.thehen101.csgoexternal.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.thehen101.csgoexternal.memory.Dereference;
import com.thehen101.csgoexternal.memory.Offset;
import com.thehen101.csgoexternal.memory.Signature;
import com.thehen101.csgoexternal.memory.Signature.ModuleLocation;

public class SignatureDeserialiser implements JsonDeserializer<Signature> {
	public Signature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jobject = json.getAsJsonObject();
		JsonArray ja = jobject.get("dereferences").getAsJsonArray();
		Dereference[] derefs = new Gson().fromJson(ja.toString(), Dereference[].class);
		return new Signature(Offset.valueOf(jobject.get("offset").getAsString()),
				jobject.get("stringSignature").getAsString(), jobject.get("offsetFromStart").getAsShort(),
				jobject.get("signatureLength").getAsInt(), derefs,
				ModuleLocation.valueOf(jobject.get("module").getAsString()));
	}
}