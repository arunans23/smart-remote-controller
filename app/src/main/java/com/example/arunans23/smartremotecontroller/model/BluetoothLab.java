package com.example.arunans23.smartremotecontroller.model;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

/**
 * Created by arunans23 on 7/8/17.
 */

public class BluetoothLab {
    private static BluetoothLab mBluetoothLab;
    private BluetoothAdapter mBluetoothAdapter;



    private BluetoothLab(Context context){


    }

    public BluetoothLab get(Context context){
        if (mBluetoothLab==null){
            this.mBluetoothLab = new BluetoothLab(context);
        }
        return mBluetoothLab;
    }


}
