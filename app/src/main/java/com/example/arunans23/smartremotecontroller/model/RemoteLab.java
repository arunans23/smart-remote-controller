package com.example.arunans23.smartremotecontroller.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by arunans23 on 7/7/17.
 */

public class RemoteLab {
    private static RemoteLab sRemoteLab;
    private ArrayList<Remote> mRemotes;

    public static RemoteLab get(Context context){
        if (sRemoteLab==null){
            sRemoteLab = new RemoteLab(context);
        }
        return sRemoteLab;
    }

    private RemoteLab(Context context){
        this.mRemotes = new ArrayList<Remote>();
    }

    public void addRemote(Remote remote){
        this.mRemotes.add(remote);
    }

    public Remote getRemote(String remoteID){
        for (Remote r: mRemotes){
            if (r.getRemoteID().equals(remoteID)){
                return r;
            }
        }
        return null;
    }

    public ArrayList<Remote> getRemotes(){
        return this.mRemotes;
    }

    //generate dummy remote list to populate dummy data
    public List<Remote> getDummyRemoteList(){

        for (int i = 0; i < 30; i++){
            this.mRemotes.add(new Remote(UUID.randomUUID().toString(), "remote " + i, null));
        }
        return mRemotes;
    }
}
