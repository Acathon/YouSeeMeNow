//------------------------------------------------
//    YOU SEE ME NOW! by mustapha essouri
//    facebook: fb.com/mustapha.essouri
//    twitter: twitter.com/mustapha.essouri
//    instagram: @mustaphaessouri
//    email: mustapha.essouri@gmail.com
//------------------------------------------------

package standards.org.coordinate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static String message;
    public static File file;
    public static String filename = "/contact.txt";
    public static TextView textNumber;
    public static Double latd;
    public static Double lond;
    //public static String pathTo = "/mnt/sdcard/";
    public Intent intent;
    String saved;
    private TextView textView;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textNumber = (TextView) findViewById(R.id.number);


        try {

            file = new File(getFilesDir() + filename);
            //pathTo = getFilesDir() + filename;
            if (file.exists()) {
                Log.i("FIL", "STORAGE FILE FOUND!\n" + file);
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                saved = bufferedReader.readLine();
                System.out.println(saved);
                bufferedReader.close();
                fileReader.close();
            } else {
                file.createNewFile();
                Log.i("FIL", "STORAGE FILE CREATED!\n" + file);
            }

        } catch (FileNotFoundException e) {
            Log.e("FIL", "STORAGE FILE NOT FOUND!");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("FIL", "STORAGE FILE READ/WRITE FAILED!");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("FIL", "STORAGE FILE ACCESS FAILED OR EMPTY!");
            e.printStackTrace();
        }

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

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lat = location.getLatitude();
                latd = new Double(lat);
                lon = location.getLongitude();
                lond = new Double(lon);


                Log.v("GPS", "LATITUDE:  " + lat + " LONGITUDE: " + lon + " Accuracy: " + location.getAccuracy() + "M");
                //DEPRECATED :3
                //message = (location.getLatitude() + " " + location.getLongitude() + " around " + location.getAccuracy() + "M.");
                //
                // if(saved.equals(null))
                //    saved = "Not Registered!";

                message = "http://maps.google.com/?ll=" + lat + "," + lon + " around " + location.getAccuracy() + "m.";
                textView.setText("LATITUDE: " + lat + "\nLONGITUDE: " + lon + "\nACCURACY: " + location.getAccuracy()
                        + "\nLINKED TO: " + saved);

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

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else {
            Log.v("GPS", "NETWORK PROVIDER IS DISABLED!");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Wi-Fi & mobile network location is not enabled, you must activate it before!").setTitle("Wi-Fi & mobile network location");
            builder.setNegativeButton("Location settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("GPS", "NETWORK IS NOT ACTIVATED!");
                    intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                }
            });
            builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TO-DO nothing
                    finish();
                    System.exit(0);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            Log.v("GPS", "GPS PROVIDER IS DISABLED!");
            //TO-DO nothing
        }

    }

}
