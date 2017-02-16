package at.prashant.prashantruns;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MapActivity extends FragmentActivity

        implements OnMapReadyCallback, LocationListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, ServiceConnection {

    Polyline path;
    LocationManager locationManager;
    private TrackingService trackingService;
    private GoogleMap map;
    ExerciseEntry entry;
    boolean binding;

    String type;
    float avgspeed;
    float curspeed;
    float climb;
    int calorie;

    long startTime;


    boolean unit;

    String distUnit = "Miles";
    String speedUnit = "m/h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unit = isKm(getApplicationContext());
        if(savedInstanceState!=null)
        {
            startTime = savedInstanceState.getLong("start");
        }
        else
        {
            startTime = System.currentTimeMillis();
        }
        Intent i = getIntent();
        if(i.getStringExtra("Type").equals("1")) {
            type = i.getStringExtra("Activity");
        }
        else
        {
            type = "13";
        }
        Log.d("Map created", type);
        setContentView(R.layout.activity_map);
        Intent serviceIntent = new Intent(this, TrackingService.class);
        serviceIntent.putExtra("Type", i.getStringExtra("Type"));
        startService(serviceIntent);
        bindService(new Intent(this, TrackingService.class), this, Context.BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter("Broadcast");
        registerReceiver(receiver, filter);
        checkPermissions();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding = false;

    }

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT < 23)
            return;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED);
                    }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                map.setMyLocationEnabled(true);
            }
            catch(SecurityException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstance)
    {
        saveInstance.putLong("start", startTime);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
        Log.d("Marker placed","Marker places");
        map = googlemap;
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        path = map.addPolyline(new PolylineOptions());


        try {
            map.setMyLocationEnabled(true);
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }

    }

    private BroadcastReceiver receiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Broadcast"," Received");
            Log.d("Broadcast", String.valueOf(binding));
            if(binding == true)
            {
                drawPoints();
                entry = trackingService.getExerciseEntry();
                setAllTextViews(entry.getmActivityType(),
                 String.valueOf(entry.getmDistance()/((System.currentTimeMillis() - startTime)*3600)),
                 String.valueOf(trackingService.getCurspeed()) ,
                        String.valueOf(entry.getmClimb()),
                        String.valueOf(entry.getmCalorie()),
                        String.valueOf(entry.getmDistance()));
            }

        }
    };

    void onSave1(View view)
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                // public static final String[] columns = {"_id", "input_type", "activity_type", "date_time",
                   //     "duration", "distance", "avg_pace", "avg_speed", "calories", "climb", "heartrate",
                     //   "comment", "privacy", "gps_data"};
                RunnerSQLHelper dbHelper = new RunnerSQLHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Calendar calendar = Calendar.getInstance();
                if(entry.getmInputType().equals("Automatic Entry"))
                {
                    values.put(RunnerSQLHelper.columns[1],2);
                    values.put(RunnerSQLHelper.columns[2], entry.getMaxActivity());
                }
                else
                {
                    values.put(RunnerSQLHelper.columns[1],1);
                    values.put(RunnerSQLHelper.columns[2], entry.getmActivityType());
                }
                values.put(RunnerSQLHelper.columns[3], calendar.getTimeInMillis());
                values.put(RunnerSQLHelper.columns[4], (System.currentTimeMillis() - startTime)/1000);
                values.put(RunnerSQLHelper.columns[5], entry.getmDistance());
                values.put(RunnerSQLHelper.columns[7], entry.getmAvgSpeed());
                values.put(RunnerSQLHelper.columns[8],entry.getmCalorie());
                byte[] byteLocations = entry.getmLocationByte();
                values.put(RunnerSQLHelper.columns[13], byteLocations);
                long id = db.insert(RunnerSQLHelper.TABLE_NAME, null, values);
                db.close();
                Toast.makeText(getApplicationContext(), "Recorded # " + String.valueOf(id),
                        Toast.LENGTH_SHORT).show();
            }
        };
        r.run();
        finish();
    }

    void onCancel1(View view)
    {
        finish();
    }

    void setAllTextViews(String s1, String s2, String s3, String s4, String s5, String s6)
    {
        TextView v1 = (TextView) findViewById(R.id.type);
        TextView v2 = (TextView) findViewById(R.id.avgspeed);
        TextView v3 = (TextView) findViewById(R.id.curspeed);
        TextView v4 = (TextView) findViewById(R.id.climb);
        TextView v5 = (TextView) findViewById(R.id.calorie);
        TextView v6 = (TextView) findViewById(R.id.distance);
        v1.setText("Type:" + s1);
        v2.setText("Avg speed: " + s2 + " " + speedUnit);
        v3.setText("Cur speed:" + s3 + " " + speedUnit);
        v4.setText("Climb:" + s4 + " " + distUnit);
        v5.setText("Calorie:" + s5);
        v6.setText("Distance:" + s6 + " " + distUnit);
    }

    void drawPoints()
    {
        map.clear();
        path = map.addPolyline(new PolylineOptions());
        Log.d("Draw points ","added");
        entry = trackingService.getExerciseEntry();
        Log.d("Tracker service activ", entry.getmInputType());
        ArrayList<LatLng> list = entry.getmLocationList();
        if(list.size() == 1)
        {
            LatLng start = list.get(0);
            map.addMarker(new MarkerOptions()
                    .position(start));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 20));
        }
        else if(list.size()>2)
        {
        LatLng start = list.get(0);
        LatLng end = list.get(list.size()-1);
        path.setPoints(list);
        if(start!=null)
        {
            Log.d("Start point ","added");
            map.addMarker(new MarkerOptions()
                    .position(start));
        }
        if(end!=null)
        {
            Log.d("End point ","added");
            map.addMarker(new MarkerOptions()
                    .position(end)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        TrackingService.TrackingBinder binder = (TrackingService.TrackingBinder) service;
        trackingService = binder.getService();
        binding = true;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onBackPressed()
    {
        this.finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
        unbindService(this);
        stopService(new Intent(this, TrackingService.class));
    }

    public boolean isKm(Context context) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String option = settings.getString("unit_key", "Mi");
        String option_metric = "Km";
        if(option.equals(option_metric))
        {
            speedUnit = "km/h";
            distUnit = "km";
            return true;
        }
        else{
            return false;
        }
    }
}
