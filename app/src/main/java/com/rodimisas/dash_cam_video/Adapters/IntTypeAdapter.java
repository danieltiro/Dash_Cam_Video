package com.rodimisas.dash_cam_video.Adapters;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IntTypeAdapter extends TypeAdapter<Number> {
    @Override
    public Number read(JsonReader reader) throws IOException {
        if(reader.peek() == JsonToken.NULL){
            reader.nextNull();
            return null;
        }
        String stringValue = reader.nextString();
        try{
            if ("".equals(stringValue)) {
                return 0;
            }
            return Integer.parseInt(stringValue);
        }catch(NumberFormatException e){
            return null;
        }
    }
    @Override
    public void write(JsonWriter writer, Number value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }

}
