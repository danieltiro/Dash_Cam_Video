package com.rodimisas.dash_cam_video.Responce;

import java.io.Serializable;

public class EquipmentItemRuntime implements Serializable {
    private float value;

    public EquipmentItemRuntime(float runtime)
    {
        this.value = runtime;
    }

    public float getValue()
    {
        return value;
    }
}
