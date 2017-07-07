package com.example.arunans23.smartremotecontroller.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by arunans23 on 7/7/17.
 */

public class Remote {
    private String mRemoteID;
    private String mRemoteBrand;
    private ArrayList<RemoteKey> mRemoteKeys;

    public Remote(String remoteBrand){
        this.mRemoteID = UUID.randomUUID().toString();
        this.mRemoteBrand = remoteBrand;
        this.mRemoteKeys = new ArrayList<RemoteKey>();
    }

    public Remote(String remoteID, String remoteBrand, ArrayList<RemoteKey> remoteKeys){
        this.mRemoteID = remoteID;
        this.mRemoteBrand = remoteBrand;
        this.mRemoteKeys = remoteKeys;
    }

    public String getRemoteID() {
        return mRemoteID;
    }

    public String getRemoteBrand() {
        return mRemoteBrand;
    }

    public ArrayList<RemoteKey> getRemoteKeys() {
        return mRemoteKeys;
    }
}
