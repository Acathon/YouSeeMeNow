package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mustapha essouri on 18/10/2016.
 */

public class outgoingCall extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.v("CAL", "DIALING: " + number);
        // TO-DO nothing!!
        return;
    }
}
