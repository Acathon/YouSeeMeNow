package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mustapha essouri on 17/10/2016.
 */

public class incomingCall extends BroadcastReceiver {

    public static String numberPhone;
    public static String compareTo = "";
    MainActivity application;
    String messageSMS;

    public incomingCall() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                numberPhone = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("CAL", "A CALL FROM " + numberPhone);
                MainActivity.textNumber.setText("A CALL FROM: " + numberPhone);
                try {
                    // WORK IN TUNISIA !
                    if (MainActivity.file.length() == 8) {
                        FileReader fileReader = new FileReader(MainActivity.file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        compareTo = bufferedReader.readLine();
                        bufferedReader.close();
                        fileReader.close();


                        if (compareTo.equals(numberPhone)) {
                            Log.i("FIL", "PHONE NUMBER FOUND!");

                        }
                    } else {
                        FileWriter fileWriter = new FileWriter(MainActivity.file);
                        fileWriter.write(numberPhone);
                        fileWriter.flush();
                        fileWriter.close();
                        compareTo = numberPhone;

                        if (MainActivity.filename.length() == 8)
                            Log.i("FIL", "PHONE NUMBER SAVED!");
                        MainActivity.textNumber.setText("A CALL FROM: " + numberPhone);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("LNK", MainActivity.message);

                if (MainActivity.message == null) {
                    //TO-DO nothing
                    Log.e("SMS", "POSITION NOT DETECTED, CANNOT SEND A MESSAGE!");
                    return;
                } else {
                    messageSMS = "This is an automated message!\nThe person you called is available at this area: "
                            + MainActivity.message +
                            "\nWatch out!";
                    Log.e("SMS", messageSMS);
                    Log.e("NUM", compareTo);
                    if (numberPhone.equals(compareTo)) {
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(numberPhone, null, messageSMS, null, null);
                            Log.e("SMS", "MESSAGE SENT SUCCESSFULLY!");
                        } catch (Exception e) {
                            Log.e("SMS", "MESSAGE NOT SENT!");
                            e.printStackTrace();
                        }
                    } else {
                        //TO-DO nothing
                        return;
                    }
                }

            }
        }

    }

}
