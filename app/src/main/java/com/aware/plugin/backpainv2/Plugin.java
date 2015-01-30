package com.aware.plugin.backpainv2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;
import android.database.Cursor;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.utils.Aware_Plugin;
import com.aware.providers.ESM_Provider.*;

import java.util.Calendar;


public class Plugin extends Aware_Plugin {

    private final String MYTAG = "BACKPAINV2";

    private final String BP_PREFS = "BPPLUGINPREFS";
    private final String BP_UID = "BPUSERUID";

    private ESMStatusListener esm_statuses;
    private AlarmManager alarmManager;
    private PendingIntent nextAlarmIntent;
    private PendingIntent weeklyAlarmIntent;

    public static int nextQ; //ugly hack to get it running fast...had to get a debug access from many places

    //TODO: use the initial alarm feature JUST to set the user ID and write that to the sharedprefs. What a great idea :)
    @Override
    public void onCreate() {
        super.onCreate();

        esm_statuses = new ESMStatusListener();
        Log.d(MYTAG, "CREATING THE BACK PAIN PLUGIN, ONCREATE()");
        Toast.makeText(getBaseContext(), "BackPain-Study Started", Toast.LENGTH_LONG).show();
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
        Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, true);

        //listen to ESM notifications
        IntentFilter esm_filter = new IntentFilter();
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_QUEUE_COMPLETE);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_DISMISSED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_EXPIRED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_ANSWERED);
        registerReceiver(esm_statuses, esm_filter);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //TODO: check from sharedprefs that we have a valid UID, if not, then next one is zero. If we have, next one is 1
        if(getUserId() == null) {
            nextQ = 0;
            setInitialAlarm(15000); //in 15 secs..
            Log.d(MYTAG, "No uid set, let's brute force the first q...");
        } else {
            nextQ = 1;
            setNextFridayAlarm();
            Log.d(MYTAG, "Uid set, let's just get on with the first q next Friday 20:00...");
        }

    }

    private void setUserId(String newUID) {
        SharedPreferences settings = getSharedPreferences(BP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(BP_UID, newUID);
        prefEditor.apply();
    }

    /**
     * Gets the local user id stored in the prefs, and acquired with the question number 0.
     * This is a mandatory thing to have, so if null is returned,something is wrong and
     * we need to trigger q0
     */
    private String getUserId() {
        String uid = null;
        SharedPreferences settings = getSharedPreferences(BP_PREFS, MODE_PRIVATE);
        uid = settings.getString(BP_UID, null);
        return uid;
    }

    //@Override
    /*public int onStartCommand(Intent intent, int flags, int startId) {
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
        return START_STICKY;
    }*/

    private void setNextFridayAlarm() {
        // create a back up logic in shared prefs, store millis when last time the questionnaire was popped up and handle this in the startup oncreate
        Calendar cal = Calendar.getInstance();
        Log.d(MYTAG, "Cal now: " + cal.getTimeInMillis());
        int diff = Calendar.FRIDAY - cal.get(Calendar.DAY_OF_WEEK);
        if (diff < 0) {
            diff += 7;
        }
        cal.add(Calendar.DAY_OF_MONTH, diff);
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //NOW, we should have next friday at eight... OR immediately if today is friday and it's already over 20:00
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("qno", nextQ);
        int b_id = (int) System.currentTimeMillis();
        weeklyAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), b_id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), weeklyAlarmIntent);
    }

    private void setInitialAlarm(int millis) {
        Calendar cal = Calendar.getInstance();
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("qno", nextQ);
        int b_id = (int) System.currentTimeMillis();
        nextAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), b_id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + millis, nextAlarmIntent);
        Log.d(MYTAG, "The initial alarm set...");
    }


    private void nextPopup() {
        Log.d(MYTAG, "NEXT Q: " + nextQ);
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON = "[" + Questions.getInstance().getQuestion(nextQ) + "]";
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        sendBroadcast(queue_esm);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MYTAG, "BACK PAIN V2 deactivated");
        unregisterReceiver(esm_statuses);

        if (weeklyAlarmIntent != null) {
            alarmManager.cancel(weeklyAlarmIntent);
        }

        if (nextAlarmIntent != null) {
            alarmManager.cancel(nextAlarmIntent);
        }

        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, false);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    public class ESMStatusListener extends BroadcastReceiver {
        private final String MYTAG = "BACKPAINV2";

        public void onReceive(Context context, Intent intent) {
            int lastQ = nextQ;


            if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_EXPIRED)) {
                Log.d(MYTAG, "ESM EXPIRED.");

                //TODO: what to do here?


            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_DISMISSED)) {
                Log.d(MYTAG, "ESM DISMISSED.");
                if (lastQ == 0) {
                    //dismissed the VERY most important one, we'll just reschedule that one..sorry folks.
                    nextQ = 0;
                    nextPopup();
                    return;
                } else {
                    //TODO: what to do here?
                }


            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_QUEUE_COMPLETE)) {
                Log.d(MYTAG, "ESM QUEUE COMPLETE.");
                //do nothing here...no need to!
            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_ANSWERED)) {
                //skiplogig...

                String ans = null;
                Cursor esm_answers = context.getContentResolver().query(ESM_Data.CONTENT_URI, null, null, null, null);
                if (esm_answers != null && esm_answers.moveToLast()) {
                    ans = esm_answers.getString(esm_answers.getColumnIndex(ESM_Data.ANSWER));
                    Log.d(MYTAG, "ESM ANSWERED WITH:" + ans);
                }
                if (esm_answers != null) {
                    esm_answers.close();
                }

                if (ans == null) {

                    Log.d(MYTAG, "ANS was null - Returning...");
                    return;
                }

                if (lastQ == 0) {
                    if (ans.trim().equalsIgnoreCase("")) {
                        nextQ = 0;
                    } else {
                        setUserId(ans);
                        nextQ = 1;
                    }
                } else if (lastQ == 1) {
                    if (ans.equalsIgnoreCase("No")) {
                        nextQ = 7;
                    } else {
                        nextQ = 2;
                    }
                } else if (lastQ == 7) {
                    if (ans.equalsIgnoreCase("No")) {
                        nextQ = 9;
                    } else {
                        nextQ = 8;
                    }
                } else if (lastQ == 10) {

                    if (ans.equalsIgnoreCase("No")) {
                        nextQ = 1;
                        //we're done, ladies and gentlemen, see you next week!
                        setNextFridayAlarm();
                        return;
                    } else {
                        nextQ = 11;
                    }
                } else if (lastQ == 11) {
                    nextQ = 1;
                    //we're done, ladies and gentlemen, see you next week!
                    setNextFridayAlarm();
                    return;
                } else {
                    nextQ++;
                }
                //Plugin.this.setQuickNextAlarm(1000);
                nextPopup();

            }
        }
    }
}
