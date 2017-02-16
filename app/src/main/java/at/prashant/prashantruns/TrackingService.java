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
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import android.location.LocationListener;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by prashant on 2/9/17.
 */

public class TrackingService extends Service implements SensorEventListener {
    int type;
    ArrayBlockingQueue<Double> buffer;
    Location prev;
    LocationManager manager;
    IBinder binder = new TrackingBinder();
    ExerciseEntry exerciseEntry;
    NotificationManager notificationManager;
    float curspeed;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public TrackingService() {


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        if (exerciseEntry.getmInputType().equals("Automatic Entry")) {
            startAccelerometer();
        }
        return binder;
    }

    void setNotification() {
        Log.d("Set notif", "Set notif");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MapActivity.class)
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

    void unsetNotification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        type = Integer.parseInt(intent.getStringExtra("Type"));
        exerciseEntry.setmInputType(type);
        Log.d("Print InputType", String.valueOf(exerciseEntry.getmInputType()));

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
            if (prev != null) {
                exerciseEntry.setmDistance((exerciseEntry.getmDistance() + Math.abs(location.distanceTo(prev))) / 1000);
                double a = (location.getAltitude() - prev.getAltitude()) / 1000;
                exerciseEntry.setmClimb((float) a);
                exerciseEntry.setmCalorie((int) exerciseEntry.getmDistance() * 2);
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
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double rms = Math.sqrt(event.values[0] * event.values[0]
                    + event.values[1] * event.values[1]
                    + event.values[2] * event.values[2]);
            try {
                buffer.add(rms);
            }
            catch(Exception e)
            {
                ArrayBlockingQueue queue = new ArrayBlockingQueue(buffer.size() * 2);
                buffer.drainTo(queue);
                buffer = queue;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class TrackingBinder extends Binder {
        TrackingService getService() {
            return TrackingService.this;
        }
    }

    void startAccelerometer() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        OnSensorChangedTask a = new OnSensorChangedTask();
        a.execute();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancelAll();
        if (type == 2)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onCreate() {
        Log.d("Tracking started", "tracking started");

        super.onCreate();


        buffer = new ArrayBlockingQueue<Double>(2048);

        exerciseEntry = new ExerciseEntry();

        if (exerciseEntry.getmInputType().equals("Automatic Entry")) {
            exerciseEntry.setmActivityType(13);
        }

        Log.d("In onCreate -- ", String.valueOf(exerciseEntry.getmInputType()));
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public ExerciseEntry getExerciseEntry() {
        return exerciseEntry;
    }

    float getCurspeed() {
        return curspeed;
    }

    private class OnSensorChangedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {

            ArrayList<Double> featureVector = new ArrayList<Double>(
                   64 + 1);
            int blockSize = 0;
            FFT fft = new FFT(64);
            double[] block = new double[64];
            double[] fftre = block;
            double[] fftim = new double[64];

            double max = Double.MIN_VALUE;

            while (true)
            {
                try {
                    if (isCancelled() == true)
                    {
                        return null;
                    }
                    block[blockSize++] = buffer.take().doubleValue();

                    if (blockSize == 64) {
                        blockSize = 0;
                        max = .0;
                        for (double val : block) {
                            if (max < val) {
                                max = val;
                            }
                        }

                        fft.fft(fftre, fftim);

                        for (int i = 0; i < fftre.length; i++) {
                            double mag = Math.sqrt(fftre[i] * fftre[i] + fftim[i]
                                    * fftim[i]);
                            featureVector.add(Double.valueOf(mag));
                            fftim[i] = .0;
                        }

                        featureVector.add(Double.valueOf(max));
                        int classifiedValue = (int) WekaClassifier
                                .classify(featureVector.toArray());
                        int activityId = 14;
                        switch (classifiedValue) {
                            case 0:
                                activityId = 0;
                                break;
                            case 1:
                                activityId = 2;
                                break;
                            case 2:
                                activityId = 1;
                                break;
                        }
                        featureVector.clear();
                        Log.d("Activity", String.valueOf(activityId));
                        exerciseEntry.setmActivityType(activityId);
                        exerciseEntry.setCountActivities(exerciseEntry.getmActivityType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

