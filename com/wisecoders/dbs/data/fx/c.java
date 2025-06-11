package com.wisecoders.dbs.data.fx;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.install4j.runtime.util.Base64;
import java.lang.reflect.Type;

class c implements JsonDeserializer, JsonSerializer {
  public byte[] a(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) {
    return Base64.decode(paramJsonElement.getAsString());
  }
  
  public JsonElement a(byte[] paramArrayOfbyte, Type paramType, JsonSerializationContext paramJsonSerializationContext) {
    return (JsonElement)new JsonPrimitive(Base64.encode(paramArrayOfbyte));
  }
}
