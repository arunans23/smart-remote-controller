package com.example.arunans23.smartremotecontroller.model;

import java.util.UUID;

/**
 * Created by arunans23 on 7/7/17.
 */

public class RemoteKey {
    private String mRemoteKeyID;
    private String mRemoteKeyName;
    private int[] mRemoteKeyValues;

    public RemoteKey(String keyName, int[] keyValues){
        this.mRemoteKeyID = UUID.randomUUID().toString();
    }

    public RemoteKey(String remoteKeyID, String remoteKeyName, int[] keyValues){
        this.mRemoteKeyID = remoteKeyID;
        this.mRemoteKeyName = remoteKeyName;
        this.mRemoteKeyValues = keyValues;
    }

    public String getRemoteKeyID() {
        return mRemoteKeyID;
    }

    public String getRemoteKeyName() {
        return mRemoteKeyName;
    }

    public int[] getRemoteKeyValues() {
        return mRemoteKeyValues;
    }
}
