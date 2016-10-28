package standards.org.coordinate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.location.LocationProvider.OUT_OF_SERVICE;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    public static String message;
    public TextView textView1;
    public File save;
    public String filename = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*try {
            save = new File(getApplicationContext().getFilesDir(), filename);
            if(save.exists()){
                Log.v("FIL","Storage file exist!");
            } else {
                Log.i("FIL", "Storage file created!");
            }
        } catch (Exception e){
            Log.e("FIL", "Storage file creation failed/exist!");
            e.printStackTrace();
        }*/

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        textView = (TextView) findViewById(R.id.textView);
        // textView1 = (TextView) findViewById(R.id.number);


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("GPS","LATITUDE:  "+location.getLatitude()+"\nLONGITUDE: "+location.getLongitude());
                message = (location.getLatitude()+" "+location.getLongitude()+" "+location.getAccuracy()+"M.");
                textView.setText("LATITUDE: "+location.getLatitude()+"\nLONGITUDE: "+location.getLongitude()+"\nALTITUDE: "+location.getAltitude()+"\nACCURACY: "+location.getAccuracy());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // String locationProvider = LocationManager.NETWORK_PROVIDER;
        // Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        // ..

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

}
