package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mustapha on 17/10/2016.
 */

public class incomingCall extends BroadcastReceiver {

    public static String numberPhone;
    private FileOutputStream outputStream;
    private String compareTo = "";

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

                    if (MainActivity.file.exists()) {
                        Log.i("FIL", "STORAGE FILE FOUND!\n" + context.getFilesDir() + MainActivity.filename);
                        //compareTo = registeredNumber.toString();

                    } else {
                        MainActivity.file.createNewFile();
                        compareTo = numberPhone;
                        Log.i("FIL", "STORAGE FILE CREATED!\n" + context.getFilesDir() + MainActivity.filename);
                    }

                } catch (FileNotFoundException e) {
                    Log.e("FIL", "STORAGE FILE NOT FOUND!");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("FIL", "STORAGE FILE CREATION FAILED!");
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e("FIL", "STORAGE FILE ACCESS FAILED!");
                    e.printStackTrace();
                }

                Log.i("MSG", MainActivity.message);
                MainActivity.message = "This is an automated message!\nThe person you called is available at this area: "
                        + MainActivity.message +
                        "\nUse Google map to see the detected position.";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    Toast.makeText(context.getApplicationContext(), compareTo + " = " + numberPhone, Toast.LENGTH_LONG).show();
                    if (numberPhone.equals(compareTo)) {
                        smsManager.sendTextMessage(numberPhone, null, MainActivity.message, null, null);
                        Log.i("SMS", "MESSAGE SENT SUCCESSFULLY!");
                    }
                } catch (Exception e) {
                    Log.e("SMS", "MESSAGE NOT SENT!");
                    e.printStackTrace();
                }
            }
        }

    }
}
