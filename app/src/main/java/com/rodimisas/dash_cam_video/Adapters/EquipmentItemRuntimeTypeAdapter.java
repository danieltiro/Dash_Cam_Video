package com.rodimisas.dash_cam_video.Adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.EquipmentItemRuntime;

import java.lang.reflect.Type;

public class EquipmentItemRuntimeTypeAdapter implements JsonDeserializer<EquipmentItemRuntime>, JsonSerializer<EquipmentItemRuntime> {
    @Override
    public EquipmentItemRuntime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        float runtime;
        try
        {
            runtime = json.getAsFloat();
        }
        catch (NumberFormatException e)
        {
            runtime = 0;
        }
        return new EquipmentItemRuntime(runtime);
    }

    @Override
    public JsonElement serialize(EquipmentItemRuntime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getValue());
    }
}
