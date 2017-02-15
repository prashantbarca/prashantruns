package at.prashant.prashantruns;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import android.location.LocationListener;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by prashant on 2/9/17.
 */

public class TrackingService extends Service implements SensorEventListener {

    Location prev;
    LocationManager manager;
    IBinder binder = new TrackingBinder();
    ExerciseEntry exerciseEntry;
    NotificationManager notificationManager;
    float curspeed;
    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;



    public TrackingService()
    {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    void setNotification()
    {
        Log.d("Set notif", "Set notif");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , new Intent(this, MapActivity.class)
                .addCategory(Intent.CATEGORY_LAUNCHER).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    void unsetNotification()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        setNotification();
        return START_STICKY;
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Location changed", "Location changed");
            Log.d("Speed", String.valueOf(location.getSpeed()));
            Log.d("Altitude", String.valueOf(location.getAltitude()));
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d("Lat Long", String.valueOf(latLng));
            curspeed = location.getSpeed();
            exerciseEntry.addToList(latLng);
            if(prev!=null)
            {
                exerciseEntry.setmDistance((exerciseEntry.getmDistance() + Math.abs(location.distanceTo(prev)))/1000);
                double a = (location.getAltitude() - prev.getAltitude())/1000;
                exerciseEntry.setmClimb((float) a);
                exerciseEntry.setmCalorie((int) exerciseEntry.getmDistance()*2);
            }
            prev = location;
            Intent intent = new Intent("Broadcast");
            sendBroadcast(intent);
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

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class TrackingBinder extends Binder
    {
        TrackingService getService()
        {
            return TrackingService.this;
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        notificationManager.cancelAll();
    }

    @Override
    public void onCreate()
    {
        Log.d("Tracking started","tracking started");

        super.onCreate();

        exerciseEntry = new ExerciseEntry();

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        String provider = manager.getBestProvider(criteria, true);
        try {
            manager.requestLocationUpdates(provider, 0, 0, listener);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public ExerciseEntry getExerciseEntry()
    {
        return exerciseEntry;
    }

    float getCurspeed()
    {
        return curspeed;
    }

}
