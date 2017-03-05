package at.prashant.prashantruns;

import android.icu.util.Calendar;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashant on 2/1/17.
 */

public class ExerciseEntry {

    private Long id;

    private int mInputType;  // Manual, GPS or automatic

    private int mActivityType;     // Running, cycling etc.
    private Calendar mDateTime;    // When does this entry happen
    private int mDuration;         // Exercise duration in seconds
    private float mDistance;      // Distance traveled. Either in meters or feet.
    private float mAvgPace;       // Average pace
    private float mAvgSpeed;     // Average speed
    private int mCalorie;        // Calories burnt
    private float mClimb;         // Climb. Either in meters or feet.
    private int mHeartRate;       // Heart rate
    private String mComment;       // Comments
    private ArrayList<LatLng> mLocationList; // Location list

    private Map<String, Integer> countActivities;


    /*
     * Thanks to android studio for auto generating these.
     */

    public int getMaxActivity()
    {
        int max = 0;
       int maxAct = 15;
        if(countActivities.get("Standing") >= max)
        {max = countActivities.get("Standing");
             maxAct = 2;}
        if(countActivities.get("Walking") >= max)
        { max = countActivities.get("Walking");
        maxAct = 1;}
        if(countActivities.get("Running") >= max)
        {
            max = countActivities.get("Running");
            maxAct = 0;
        }
        if (countActivities.get("Other") >= max)
            maxAct = 15;
        return maxAct;
    }

    void setCountActivities(String key)
    {
        countActivities.put(key, countActivities.get(key)+1);
    }

    public ExerciseEntry()
    {
        mLocationList = new ArrayList<LatLng>();
        countActivities = new HashMap<String, Integer>();
        countActivities.put("Standing", 0);
        countActivities.put("Running", 0);
        countActivities.put("Walking", 0);
        countActivities.put("Other", 0);
    }

    public Long getId(int position)
    {
        return id;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public void setmDateTime(Calendar mDateTime) {
        this.mDateTime = mDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setmDistance(float mDistance) {
        this.mDistance = mDistance;
    }

    public void setmAvgPace(float mAvgPace) {
        this.mAvgPace = mAvgPace;
    }

    public void setmClimb(float mClimb) {
        this.mClimb = mClimb;
    }

    float getmClimb()
    {
        return mClimb;
    }

    public void setmHeartRate(int mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    public void setmAvgSpeed(float mAvgSpeed) {
        this.mAvgSpeed = mAvgSpeed;
    }

    float getmAvgSpeed()
    {
        return mAvgSpeed;
    }

    public void setmCalorie(int calorie)
    {
        this.mCalorie = calorie;
    }

    public void setmActivityType(int mActivityType) {
        this.mActivityType = mActivityType;
    }

    public void setmInputType(int mInputType) {
        this.mInputType = mInputType;
    }

    public int getmHeartRate(){
        return mHeartRate;
    }

    public byte[] getmLocationByte()
    {
        double[] coor = new double[mLocationList.size()*2];
        int c = 0;
        for(LatLng l: mLocationList)
        {
            coor[c] = l.latitude;
            c++;
            coor[c] = l.longitude;
            c++;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(coor.length*Double.SIZE);
        DoubleBuffer buffer = byteBuffer.asDoubleBuffer();
        buffer.put(coor);
        return byteBuffer.array();
    }

    public void setLocationByte(byte[] bytesLocation)
    {
        if(bytesLocation==null || bytesLocation.length == 0)
            return;
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytesLocation);
        DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
        double[] location = new double[bytesLocation.length / Double.SIZE];
        doubleBuffer.get(location);

        int coor = location.length/2;
        for(int iter = 0; iter < coor; iter ++)
        {
            LatLng latLng = new LatLng(location[iter * 2], location[iter * 2 + 1]);
            mLocationList.add(latLng);
        }
    }


    public String getmInputType()
    {
        if(mInputType == 0)
            return "Manual Entry";
        else if(mInputType == 1)
            return "Map Entry";
        else
            return "Automatic Entry";
    }

    public String getmActivityType()
    {
        String activity="Other";
        switch (mActivityType)
        {
            case 0:
                activity = "Running";
                break;
            case 1:
                activity = "Walking";
                break;
            case 2:
                activity = "Standing";
                break;
            case 3:
                activity = "Cycling";
                break;
            case 4:
                activity = "Hiking";
                break;
            case 5:
                activity = "Downhill Skiing";
                break;
            case 6:
                activity = "Cross-Country Skiing";
                break;
            case 7:
                activity = "Snowboarding";
                break;
            case 8:
                activity = "Skating";
                break;
            case 9:
                activity = "Swimming";
                break;
            case 10:
                activity = "Mountain Biking";
                break;
            case 11:
                activity = "Wheelchair";
                break;
            case 12:
                activity = "Elliptical";
                break;
            case 13:
                activity = "Unknown";
                break;
            default:
                activity = "Other";
                break;
        }
        return activity;
    }

    public int getmCalorie()
    {
        return mCalorie;
    }

    public Calendar getmDateTime()
    {
        return mDateTime;
    }

    public float getmDistance()
    {
        return mDistance;
    }

    public String toString()
    {
        return String.valueOf(id);
    }

    public int getmDuration()
    {
        return mDuration;
    }

    public ArrayList<LatLng> getmLocationList()
    {
        return mLocationList;
    }

    public void addToList(LatLng a)
    {
        mLocationList.add(a);
    }

    public JSONObject getObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("inputType", mInputType);
            jsonObject.put("activityType", mActivityType);
            jsonObject.put("dateTime", mDateTime.getTimeInMillis());
            jsonObject.put("duration", mDuration);
            jsonObject.put("distance", mDistance);
            jsonObject.put("avgSpeed", mAvgSpeed);
            jsonObject.put("calorie", mCalorie);
            jsonObject.put("climb", mClimb);
            jsonObject.put("heartrate", mHeartRate);
            jsonObject.put("comment", mComment);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }
}