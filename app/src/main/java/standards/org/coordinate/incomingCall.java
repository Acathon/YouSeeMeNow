package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by mustapha on 17/10/2016.
 */

public class incomingCall extends BroadcastReceiver {

    public static String numberPhone;

    public incomingCall(){}

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                numberPhone = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("CAL", "A call from " + numberPhone);
                Log.i("MSG", MainActivity.message);
                MainActivity.message = "This is an automated message!\nThe person you called is available at this area: " + MainActivity.message +
                                "\nUse Google map to see the detected position.";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(incomingCall.numberPhone, null, MainActivity.message, null, null);
                    Log.i("MSG", MainActivity.message);
                    Log.v("SMS", "Message sent successfully!");
                } catch (Exception e) {
                    Log.e("SMS", "Message not sent!");
                            e.printStackTrace();
                }
            }
        }

    }
}
