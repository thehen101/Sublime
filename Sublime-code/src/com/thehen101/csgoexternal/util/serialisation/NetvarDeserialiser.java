package com.thehen101.csgoexternal.util.serialisation;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.thehen101.csgoexternal.memory.Netvar;

public class NetvarDeserialiser implements JsonDeserializer<Netvar> {
	@Override
	public Netvar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jobject = json.getAsJsonObject();
		Netvar netvar = Netvar.valueOf(jobject.get("name").getAsString());
		netvar.setOffset(jobject.get("offset").getAsInt());
		return netvar;
	}
}
