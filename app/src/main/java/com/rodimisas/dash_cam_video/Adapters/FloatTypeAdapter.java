package com.rodimisas.dash_cam_video.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FloatTypeAdapter extends TypeAdapter<Float> {
    @Override
    public void write(JsonWriter out, Float value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public Float read(JsonReader in) throws IOException {
        if(in.peek() == JsonToken.NULL){
            in.nextNull();
            return null;
        }
        String stringValue = in.nextString();
        try{
            Float value = Float.valueOf(stringValue);
            return value;
        }catch(NumberFormatException e){
            return null;
        }
    }
}
