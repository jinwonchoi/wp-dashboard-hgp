package com.gencode.issuetool.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonUtils {

	public static Gson GetGson() {
		return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
	
		@Override
		public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		    //return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
//			Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")); 
		}
		}).create();
	}
}
