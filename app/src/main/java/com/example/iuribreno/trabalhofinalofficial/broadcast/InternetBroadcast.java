package com.example.iuribreno.trabalhofinalofficial.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class InternetBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Estado do WIFI alterado", Toast.LENGTH_SHORT).show();
    }
}
