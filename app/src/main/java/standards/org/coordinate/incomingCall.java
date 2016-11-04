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
 * Created by mustapha on 17/10/2016.
 */

public class incomingCall extends BroadcastReceiver {

    public static String numberPhone;
    public static String compareTo = "";

    public incomingCall() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                numberPhone = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("CAL", "A CALL FROM " + numberPhone);

                try {
                    if (MainActivity.file.length() == 8) {
                        FileReader fileReader = new FileReader(MainActivity.file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        compareTo = bufferedReader.readLine();
                        bufferedReader.close();
                        fileReader.close();
                        //if (compareTo.equals(numberPhone)){
                        //    Toast.makeText(context,"YES",Toast.LENGTH_LONG).show();
                        //}
                    } else {
                        FileWriter fileWriter = new FileWriter(MainActivity.file);
                        fileWriter.write(numberPhone);
                        fileWriter.flush();
                        fileWriter.close();
                        compareTo = numberPhone;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.v("MSG", MainActivity.message);
                MainActivity.message = "This is an automated message!\nThe person you called is available at this area: "
                        + MainActivity.message +
                        "\nUse Google map to see the detected position.";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    if (compareTo.equals(numberPhone)) {
                        //Toast.makeText(context, compareTo + " = " + numberPhone, Toast.LENGTH_LONG).show();
                        smsManager.sendTextMessage(numberPhone, null, MainActivity.message, null, null);
                        Log.v("SMS", "MESSAGE SENT SUCCESSFULLY!");
                    }
                } catch (Exception e) {
                    Log.e("SMS", "MESSAGE NOT SENT!");
                    e.printStackTrace();
                }
            }
        }

    }

}
