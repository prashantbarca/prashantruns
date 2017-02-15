package at.prashant.prashantruns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class ExistingMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    long position;
    GoogleMap map;
    Polyline path;
    ExerciseEntry entry;
    String distUnit = "Miles";
    String speedUnit = "m/h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_map);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Prashant Runs 3");
        Intent intent = getIntent();
        position = (Long) intent.getExtras().get("POSITION");
        entry = new ExerciseEntry();
        RunnerSQLHelper helper = new RunnerSQLHelper(getApplicationContext());
        entry = helper.fetchEntryByIndex(position);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public boolean deleteEvent(MenuItem item)
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                RunnerSQLHelper helper = new RunnerSQLHelper(getApplicationContext());
                helper.removeEntry(position);
            }
        };
        r.run();
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d("Map ready", "Map ready");
        drawPoints();
        setAllTextViews();
    }

    void drawPoints()
    {
        map.clear();
        path = map.addPolyline(new PolylineOptions());
        Log.d("Draw points ","added");
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
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 20));
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

    void setAllTextViews()
    {
        TextView v1 = (TextView) findViewById(R.id.type);
        TextView v2 = (TextView) findViewById(R.id.avgspeed);
        TextView v3 = (TextView) findViewById(R.id.curspeed);
        TextView v4 = (TextView) findViewById(R.id.climb);
        TextView v5 = (TextView) findViewById(R.id.calorie);
        TextView v6 = (TextView) findViewById(R.id.distance);
        v1.setText("Type:" + entry.getmInputType());
        v2.setText("Avg speed: " + entry.getmAvgSpeed() + " " + speedUnit);
        v3.setText("Cur speed:" +" n/a");
        v4.setText("Climb: " + entry.getmClimb() + " " + distUnit);
        v5.setText("Calorie: " + entry.getmCalorie());
        v6.setText("Distance:" + entry.getmDistance() + " " + distUnit);
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
