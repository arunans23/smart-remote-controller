package com.example.arunans23.smartremotecontroller.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import com.example.arunans23.smartremotecontroller.BluetoothThread;
import com.example.arunans23.smartremotecontroller.R;
import com.example.arunans23.smartremotecontroller.model.Remote;

public class RemoteActivity extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = "RemoteActivity";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    private final String address = "00:21:13:00:B4:E9";

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    private Button onButton;
    private Button offButton;

    private Remote mRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);


        onButton = (Button) findViewById(R.id.onButton);
        onButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                writeData("");
            }
        });
        offButton = (Button) findViewById(R.id.offButton);
        offButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                writeData("");
            }
        });
        connectButtonPressed();
    }

    /**
     * Launch the Bluetooth thread.
     */
    public void connectButtonPressed() {
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
                    Toast.makeText(RemoteActivity.this, "Connection stable", Toast.LENGTH_SHORT).show();

                } else if (s.equals("DISCONNECTED")) {
                    Toast.makeText(RemoteActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();

                } else if (s.equals("CONNECTION FAILED")) {
                    Toast.makeText(RemoteActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                } else {

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
    public void disconnectButtonPressed(View v) {
        Log.v(TAG, "Disconnect button pressed.");

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

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }
}
