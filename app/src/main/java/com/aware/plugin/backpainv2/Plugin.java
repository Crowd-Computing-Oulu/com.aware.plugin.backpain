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

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.utils.Aware_Plugin;
import com.aware.providers.ESM_Provider.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Plugin extends Aware_Plugin {

    public static int nextQ; //ugly hack to get it running fast...had to get a debug access from many places
    private final String MYTAG = "BACKPAINV2";
    private final String BP_PREFS = "BPPLUGINPREFS";
    private final String BP_UID = "BPUSERUID";
    private final int WEEKLY_INTENT_RC = 909090;
    private ESMStatusListener esm_statuses;
    private AlarmManager alarmManager;

    private Intent nextIntent;
    private Intent nextWeeklyIntent;

    private PendingIntent nextPendingIntent;
    private PendingIntent weeklyPendingIntent;

    private int attemptNo;

    @Override
    public void onCreate() {
        super.onCreate();

        esm_statuses = new ESMStatusListener();

        nextIntent = new Intent(this, AlarmReceiver.class);
        nextWeeklyIntent = new Intent(this, AlarmReceiver.class);

        Log.d(MYTAG, "CREATING THE BACK PAIN PLUGIN, ONCREATE()");
        Toast.makeText(getBaseContext(), "Selkäkipututkimus aloitettu.", Toast.LENGTH_LONG).show();

        //listen to ESM notifications
        IntentFilter esm_filter = new IntentFilter();
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_DISMISSED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_EXPIRED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_ANSWERED);
        registerReceiver(esm_statuses, esm_filter);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (getUserId() == null) {
            nextQ = 0;
            Log.d(MYTAG, "No uid set, let's go through the setup first..pop up in 15 secs.");
            setDelayedPopup(5000); //in 5 secs..
        } else {
            Log.d(MYTAG, "Uid set, let's just get on with the first q next Friday 20:00...");
            nextQ = 1;
            attemptNo = 1;
            setNextFridayAlarm();
        }
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
        Log.d(MYTAG, "GET UID FROM PREFS: " + uid);
        return uid;

    }

    private void setUserId(String newUID) {
        SharedPreferences settings = getSharedPreferences(BP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(BP_UID, newUID);
        prefEditor.apply();
        Log.d(MYTAG, "SET UID TO PREFS: " + newUID);
    }

    public void setNextQ(int q){
        nextQ = q;

    }

    //@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MYTAG, "onStartCommand!");
        setNextFridayAlarm(); //let's just make sure it's there! It overrides the old one, so no harm done.
        return START_STICKY;
    }

    /*private void setNextAlarm() {
        //nextQ NEEDS to be synced elsewhere..hopefully it is!
        Calendar cal = Calendar.getInstance();



        int minuteNow = cal.get(Calendar.MINUTE); //can be from 0 to 23
        cal.add(Calendar.MINUTE, 2);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date readable = new Date(cal.getTimeInMillis());
        Toast.makeText(getBaseContext(), sdf.format(readable), Toast.LENGTH_LONG).show();



        nextWeeklyIntent.putExtra("qno", 1); //ALWAYS the next friday's one starts from question number one!
        weeklyPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), WEEKLY_INTENT_RC, nextWeeklyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), weeklyPendingIntent); //use WEEKLY_INTENT_RC, so this gets overwritten in case we call this one twice...
        Log.d(MYTAG, "Set alarm for :" + sdf.format(readable));
    }*/


    private void setNextFridayAlarm() {
        Calendar cal = Calendar.getInstance();

        int diff = Calendar.FRIDAY - cal.get(Calendar.DAY_OF_WEEK);

        int hourNow = cal.get(Calendar.HOUR_OF_DAY); //can be from 0 to 23
        int minuteNow = cal.get(Calendar.MINUTE); //can be from 0 to 23

        if (diff < 0) {
            diff += 7;
            cal.add(Calendar.DAY_OF_MONTH, diff);
        } else if (diff == 0 && hourNow < 20) {
            //now, it's friday but not yet 20:00! No need to adjust day.
        } else if (diff == 0 && hourNow >= 20) {
            //now, it's friday but after 20:00! Let's schedule it next week.
            cal.add(Calendar.DAY_OF_MONTH, 7);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, diff);
        }
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date readable = new Date(cal.getTimeInMillis());
        Toast.makeText(getBaseContext(), sdf.format(readable), Toast.LENGTH_LONG).show();

        nextWeeklyIntent.putExtra("qno", 1); //ALWAYS the next friday's one starts from question number one!
        weeklyPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), WEEKLY_INTENT_RC, nextWeeklyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), weeklyPendingIntent); //use WEEKLY_INTENT_RC, so this gets overwritten in case we call this one twice...
        Log.d(MYTAG, "Set alarm for :" + cal.getTimeInMillis());
    }

    private void setDelayedPopup(int millis) {
        Calendar cal = Calendar.getInstance();
        nextIntent.putExtra("qno", nextQ);
        int b_id = (int) System.currentTimeMillis();
        nextPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), b_id, nextIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + millis, nextPendingIntent);
        long millisStr = cal.getTimeInMillis() + millis;
        Log.d(MYTAG, "Set a delayed popup for millis: " + millisStr);
    }


    private void nextPopupNow() {
        Log.d(MYTAG, "NEXT Q: " + nextQ);
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON = "[" + Questions.getInstance().getQuestion(nextQ) + "]";
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        sendBroadcast(queue_esm);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(esm_statuses);

        if (weeklyPendingIntent != null) {
            alarmManager.cancel(weeklyPendingIntent);
        }

        if (nextPendingIntent != null) {
            alarmManager.cancel(nextPendingIntent);
        }
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    public class ESMStatusListener extends BroadcastReceiver {
        private final String MYTAG = "BACKPAINV2";

        public void onReceive(Context context, Intent intent) {

            String trigger = null;
            String ans = null;

            Cursor esm_data = context.getContentResolver().query(ESM_Data.CONTENT_URI, null, null, null, null);

            if (esm_data != null && esm_data.moveToLast()) {
                ans = esm_data.getString(esm_data.getColumnIndex(ESM_Data.ANSWER));
                trigger = esm_data.getString(esm_data.getColumnIndex(ESM_Data.TRIGGER));

            }
            if (esm_data != null) {
                esm_data.close();
            }
            if (trigger.equals("com.aware.plugin.backpainv2") == false){
                Log.d(MYTAG, "Somebody else initiated the ESM, no need to react, returning.");
                return;
            }

            if (ans == null) {
                Log.d(MYTAG, "ANS was null, returning.");
                return;
            }


            int lastQ = nextQ; //just to make the code a bit more readable...it's really the last q we are exploring.


            if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_EXPIRED)) {
                Log.d(MYTAG, "ESM EXPIRED");

                if (lastQ == 0) {
                    Log.d(MYTAG, "UID ESM EXPIRED. Try again immediately.");
                    //dismissed the VERY most important one, we'll just reschedule that one..sorry folks.
                    nextQ = 0;
                    nextPopupNow();
                    return;
                } else {
                    Log.d(MYTAG, "ESM EXPIRED. Try again in 5 mins.");
                    setDelayedPopup(300000); // 5 mins = 5*60*1000 = 300000 millis
                    return;
                }


            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_DISMISSED)) {
                Log.d(MYTAG, "ESM CANCELED BY USER");


                if (lastQ == 0) {
                    //dismissed the VERY most important one, we'll just reschedule that one..sorry folks.
                    nextQ = 0;
                    nextPopupNow();
                    return;
                } else if(lastQ == 1) {
                    //the user may cancel the whole set of questions, otherwise he'll have to finish it if started
                    if (attemptNo == 1 || attemptNo == 2){
                        attemptNo++;
                        setDelayedPopup(15*60*1000); // 15 mins = 15*60*1000 = 300000 millis
                    } else if (attemptNo == 3 || attemptNo == 4){
                        attemptNo++;
                        setDelayedPopup(24*60*60*1000); // 24 hours
                    } else if(attemptNo == 5) {
                        //already had 4 chances, see you next week!
                        nextQ = 1;
                        attemptNo = 1;
                        setNextFridayAlarm();
                    }
                    return;
                } else {
                    //other questions, let's make the user answer them!
                    nextPopupNow();
                    return;
                }


            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_ANSWERED)) {
                //skiplogig...


                if (lastQ == 0) {
                    //uid question should not be empty, request again if it is
                    if (ans.trim().equalsIgnoreCase("")) {
                        nextQ = 0;
                    } else {
                        setUserId(ans);
                        nextQ = 1;
                    }
                } else if (lastQ == 1) {
                    if (ans.equalsIgnoreCase("Ei")) {
                        nextQ = 7;
                    } else {
                        nextQ = 2;
                    }
                } else if (lastQ == 7) {
                    if (ans.equalsIgnoreCase("Ei")) {
                        nextQ = 9;
                    } else {
                        nextQ = 8;
                    }
                } else if (lastQ == 10) {

                    if (ans.equalsIgnoreCase("Ei")) {
                        //we're done, ladies and gentlemen, see you next week!
                        nextQ = 1;
                        attemptNo = 1;
                        setNextFridayAlarm();
                        return;
                    } else {
                        nextQ = 11;
                    }
                } else if (lastQ == 11) {
                    //we're done, ladies and gentlemen, see you next week!
                    nextQ = 1;
                    attemptNo = 1;
                    setNextFridayAlarm();
                    return;
                } else {
                    nextQ++;
                }
                nextPopupNow();
            }
        }
    }

}
