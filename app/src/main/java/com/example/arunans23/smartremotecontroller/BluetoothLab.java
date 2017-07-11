package com.example.arunans23.smartremotecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

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

    // Tag for logging
    private static final String TAG = "BluetoothActivity";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    private final String address = "00:06:66:66:33:89";

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    /**
     * Launch the Bluetooth thread.
     */
    public void connect() {
        Log.v(TAG, "Connect button pressed.");

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
//                    TextView tv = (TextView) findViewById(R.id.statusText);
//                    tv.setText("Connected.");
//                    Button b = (Button) findViewById(R.id.writeButton);
//                    b.setEnabled(true);
                } else if (s.equals("DISCONNECTED")) {
//                    TextView tv = (TextView) findViewById(R.id.statusText);
//                    Button b = (Button) findViewById(R.id.writeButton);
//                    b.setEnabled(false);
//                    tv.setText("Disconnected.");
                } else if (s.equals("CONNECTION FAILED")) {
//                    TextView tv = (TextView) findViewById(R.id.statusText);
//                    tv.setText("Connection failed!");
//                    btt = null;
                } else {
//                    TextView tv = (TextView) findViewById(R.id.readField);
//                    tv.setText(s);
                }
            }
        });

        // Get the handler that is used to send messages
        writeHandler = btt.getWriteHandler();

        // Run the thread
        btt.start();

//        TextView tv = (TextView) findViewById(R.id.statusText);
//        tv.setText("Connecting...");
    }

    /**
     * Kill the Bluetooth thread.
     */
    public void disconnect() {
        Log.v(TAG, "Disconnect button pressed.");

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    /**
     * Send a message using the Bluetooth thread's write handler.
     */
    public void writeButtonPressed() {
        Log.v(TAG, "Write button pressed.");

//        TextView tv = (TextView)findViewById(R.id.writeField);
//        String data = tv.getText().toString();

        String data = "A90";

        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }


    /**
     * Kill the thread when we leave the activity.
     */



}
