package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = (Switch) findViewById(R.id.switch1);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    wifiManager.setWifiEnabled(true);
                    aSwitch.setText("WIFI ON");

                } else {

                    wifiManager.setWifiEnabled(false);
                    aSwitch.setText("WIFI OFF");
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(wifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wiBroadcastreceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(wiBroadcastreceiver);
    }

    private BroadcastReceiver wiBroadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifistate) {

                case WifiManager.WIFI_STATE_ENABLED:
                    aSwitch.setChecked(true);

                    aSwitch.setText("WiFI On");
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    aSwitch.setChecked(false);

                    aSwitch.setText("Wifi oFF");
            }
        }


    };
}
