package com.aware.plugin.backpainv2;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.content.BroadcastReceiver;


import java.util.Random;

import com.aware.ESM;

public class AlarmReceiver extends BroadcastReceiver {

    private final String MYTAG = "BACKPAINV2";

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int q = intent.getIntExtra("qno", -1);
        if (q == 1){
            Plugin p = ((Plugin)context.getApplicationContext());
            p.setNextQ(1);
        }
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON = "[" + Questions.getInstance().getQuestion(q) + "]";
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        context.sendBroadcast(queue_esm);
    }
}