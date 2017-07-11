package com.example.arunans23.smartremotecontroller.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arunans23.smartremotecontroller.BluetoothThread;
import com.example.arunans23.smartremotecontroller.R;
import com.example.arunans23.smartremotecontroller.model.Remote;
import com.example.arunans23.smartremotecontroller.model.RemoteKey;
import com.example.arunans23.smartremotecontroller.model.RemoteLab;

import java.util.ArrayList;
import java.util.List;

public class RemoteConfigureActivity extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = "RemoteConfigureActivity";

    //Argument for startActivityForResult() in bluetoothadapter connection
    private final int REQUEST_ENABLE_BT = 1;

    //this character has to be sent first to start receiving a character
    private final String READ_ENABLE_CHAR  = "A";

    //this character has to be sent at the end to stop receiving characters
    private final String READ_DISABLE_CHAR = "B";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    private final String address = "00:21:13:00:B4:E9";

    // The thread that does all the work
    BluetoothThread btt;
    RemoteLab sRemoteLab;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    private EditText mRemoteNameEdittext;
    private Button mPowerConfigureButton;
    private Button mChannelUpConfigureButton;
    private Button mChannelDownConfigureButton;

    private Button mSaveButton;
    private Button mCancelButton;

    private Button mCurrentButton;

    private ProgressDialog mProgressDialog;

    private ArrayList<RemoteKey> mRemoteKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_configure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRemoteKeys = new ArrayList<RemoteKey>();
        sRemoteLab = RemoteLab.get(getApplicationContext());

        mRemoteNameEdittext = (EditText) findViewById(R.id.remoteNameEditText);

        mPowerConfigureButton = (Button) findViewById(R.id.configurePowerButton);
        mPowerConfigureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showProgressDialog((Button)findViewById(R.id.configurePowerButton));
                writeData(READ_ENABLE_CHAR);
            }
        });

        mChannelUpConfigureButton = (Button) findViewById(R.id.configureChannelUpButton);
        mChannelUpConfigureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showProgressDialog((Button)findViewById(R.id.configureChannelUpButton));
                writeData(READ_ENABLE_CHAR);
            }
        });

        mChannelDownConfigureButton = (Button) findViewById(R.id.configureChannelDownButton);
        mChannelDownConfigureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showProgressDialog((Button)findViewById(R.id.configureChannelDownButton));
                writeData(READ_ENABLE_CHAR);
            }
        });

        mSaveButton = (Button) findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ((mRemoteKeys.size() == 3) && !mRemoteNameEdittext.getText().toString().equals("")){
                    sRemoteLab.addRemote(new Remote(mRemoteNameEdittext.getText().toString(), mRemoteKeys));
                    finish();
                } else if (mRemoteNameEdittext.getText().toString().equals("")){
                    Snackbar.make(view, "Fill in the remote name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Configure all the remote buttons", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        mCancelButton = (Button) findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Listening for Input");

        connectBluetooth();
    }

    /**
     * Launch the Bluetooth thread.
     */
    public void connectBluetooth() {
        Log.v(TAG, "Bluetooth Connected...");

        // Only one thread at a time
        if (btt != null) {
            Log.w(TAG, "Already connected!");
            return;
        }

        // Initialize the Bluetooth thread, passing in a MAC address
        // and a Handler that will receive incoming messages
        btt = new BluetoothThread(address, new Handler() {

            @Override
            public void handleMessage(Message message) {

                String s = (String) message.obj;

                // Do something with the message
                if (s.equals("CONNECTED")) {
                    Toast.makeText(RemoteConfigureActivity.this, "Connection stable", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                } else if (s.equals("DISCONNECTED")) {
                    Toast.makeText(RemoteConfigureActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                } else if (s.equals("CONNECTION FAILED")) {
                    Toast.makeText(RemoteConfigureActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
                    hideProgressDialog();


                } else if (s.equals("ADAPTER 404")){
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    hideProgressDialog();

                } else {
                    receiveConfigureDetails(s);

                }
            }
        });

        // Get the handler that is used to send messages
        writeHandler = btt.getWriteHandler();

        // Run the thread
        btt.start();

    }

    /**
     * Kill the Bluetooth thread.
     */
    public void disconnectBluetooth() {
        Log.v(TAG, "Bluetooth Disconnected");

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    /**
     * Send a message using the Bluetooth thread's write handler.
     */
    public void writeData(String data) {
        Log.v(TAG, "Data passed" + data);

        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }


    /**
     * Kill the thread when we leave the activity.
     */
    protected void onPause() {
        super.onPause();
        disconnectBluetooth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectBluetooth();
    }


    //show a progress dialog box whenever waiting for data
    private void showProgressDialog(Button b){
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
        this.mCurrentButton = b;
    }

    //dismiss progress dialog after listening to data
    private void hideProgressDialog(){
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        this.mCurrentButton = null;
        writeData(this.READ_DISABLE_CHAR);
    }

    //method to invoke after receiving configuration data
    private void receiveConfigureDetails(String data){
        if (this.mCurrentButton != null){
            this.mCurrentButton.setEnabled(false);
        }
        this.mRemoteKeys.add(new RemoteKey(mCurrentButton.getText().toString(), data));
        Log.i(TAG, mCurrentButton.getText().toString() + " " + data);
        hideProgressDialog();
    }
}
