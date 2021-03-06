//client side of TCP/IP wifi connection
//Written in Java 8 on Android Studio 2.3.3

package com.example.david.control_the_light_switch;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;



import java.io.*;
import java.net.Socket;
import java.net.InetAddress;


public class Switch extends AppCompatActivity {

    public static final int SERVERPORT = 4000;
    private static final String SERVER_IP = "192.168.5.16";
    public Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        //to override android.os.NetworkOnMainThreadException
        //bad practice; instead use Async or another Thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.toggleBtn1);

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String str;

                try{
                    //PrintStream p = new PrintStream(mSocket.getOutputStream());
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    mSocket = new Socket(serverAddr, SERVERPORT);
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream())),
                            true);

                    TextView output = (TextView) findViewById(R.id.tv1);
                    TextView output2 = (TextView) findViewById(R.id.tv2);
                    if (isChecked) {

                        output.setText("OFF");
                        output2.setText("CHARGING");
                        str = "LIGHTON";
                        out.println(str);

                    } else {
                        // The toggle is disable

                        output.setText("ON");
                        output2.setText("NOT CHARGING");
                        str = "LIGHTOFF";
                        out.println(str);

                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
    }
}
