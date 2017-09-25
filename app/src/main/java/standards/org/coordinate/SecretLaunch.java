package standards.org.coordinate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by musta on 25/09/2017.
 * To hide App icon from Android Launcher
 */

public class SecretLaunch extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null == bundle)
            return;

        String codePhone = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (codePhone.equals("*#0765*#")) {
            setResultData(null);
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }

}
