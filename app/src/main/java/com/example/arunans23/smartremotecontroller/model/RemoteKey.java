package com.example.arunans23.smartremotecontroller.model;

import java.util.UUID;

/**
 * Created by arunans23 on 7/7/17.
 */

public class RemoteKey {
    private String mRemoteKeyID;
    private String mRemoteKeyName;
    private String mRemoteKeyValue;

    public RemoteKey(String keyName, String keyValue){
        this.mRemoteKeyID = UUID.randomUUID().toString();
        this.mRemoteKeyName = keyName;
        this.mRemoteKeyValue = keyValue;
    }

    public RemoteKey(String remoteKeyID, String remoteKeyName, String keyValue){
        this.mRemoteKeyID = remoteKeyID;
        this.mRemoteKeyName = remoteKeyName;
        this.mRemoteKeyValue = keyValue;
    }

    public String getRemoteKeyID() {
        return mRemoteKeyID;
    }

    public String getRemoteKeyName() {
        return mRemoteKeyName;
    }

    public String getRemoteKeyValues() {
        return mRemoteKeyValue;
    }
}
