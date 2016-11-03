package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
                Log.v("CAL", "A call from " + numberPhone);

                try {

                    if (MainActivity.file.exists()) {
                        Log.i("FIL", "STORAGE FILE FOUND!\n" + context.getFilesDir() + MainActivity.filename);
                        InputStream inputStream = context.openFileInput(context.getFilesDir() + MainActivity.filename);
                        if (inputStream != null) {
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String registeredNumber = "";
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((registeredNumber = bufferedReader.readLine()) != null) {
                                stringBuilder.append(registeredNumber);
                            }
                            inputStream.close();
                            compareTo = registeredNumber.toString();
                        }
                    } else {
                        OutputStreamWriter streamWriter = new OutputStreamWriter(context.openFileOutput(context.getFilesDir() + MainActivity.filename, Context.MODE_PRIVATE));
                        streamWriter.write(numberPhone);
                        streamWriter.close();
                        Log.i("FIL", "STORAGE FILE CREATED!\n" + context.getFilesDir() + MainActivity.filename);
                    }

                } catch (FileNotFoundException e) {
                    Log.e("FIL", "STORAGE FILE NOT FOUND!");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("FIL", "STORAGE FILE CREATION FAILED!");
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e("FIL", "STORAGE FILE CREATION NOT ALLOWED!");
                    e.printStackTrace();
                }

                Log.i("MSG", MainActivity.message);
                MainActivity.message = "This is an automated message!\nThe person you called is available at this area: "
                        + MainActivity.message +
                        "\nUse Google map to see the detected position.";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    if (compareTo == numberPhone) {
                        smsManager.sendTextMessage(incomingCall.numberPhone, null, MainActivity.message, null, null);
                        Log.i("SMS", "Message sent successfully!");
                    }
                } catch (Exception e) {
                    Log.e("SMS", "Message not sent!");
                    e.printStackTrace();
                }
            }
        }

    }
}
