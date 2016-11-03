package standards.org.coordinate;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static String message;
    public static File file;
    public static String filename = "/contact.txt";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(getApplicationContext().getFilesDir() + filename);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        textView = (TextView) findViewById(R.id.textView);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.v("GPS", "LATITUDE:  " + location.getLatitude() + " LONGITUDE: " + location.getLongitude() + " Accuracy: " + location.getAccuracy() + "M");
                message = (location.getLatitude() + " " + location.getLongitude() + " around " + location.getAccuracy() + "M.");
                textView.setText("LATITUDE: " + location.getLatitude() + "\nLONGITUDE: " + location.getLongitude() + "\nALTITUDE: " + "\nACCURACY: " + location.getAccuracy());

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
