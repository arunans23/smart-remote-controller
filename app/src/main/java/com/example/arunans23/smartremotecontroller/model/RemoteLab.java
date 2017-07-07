package com.example.arunans23.smartremotecontroller.model;

import android.content.Context;

/**
 * Created by arunans23 on 7/7/17.
 */

public class RemoteLab {
    private static RemoteLab sRemoteLab;

    public static RemoteLab get(Context context){
        if (sRemoteLab==null){
            sRemoteLab = new RemoteLab(context);
        }
        return sRemoteLab;
    }

    private RemoteLab(Context context){
        
    }
}
