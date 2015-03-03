package com.aware.plugin.backpainv2;


import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
        Log.d(MYTAG, "Got a request to popup q no: " + q);
        if (q == 1){
            playNotification(context);
        }
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON = "[" + Questions.getInstance().getQuestion(q) + "]";
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        context.sendBroadcast(queue_esm);

    }

    private void playNotification(Context c) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(c, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}