package com.example.prashant.myapplication.backend;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by prashant on 2/1/17.
 */

public class ExerciseEntry {

    public Long id;

    public int mInputType;  // Manual, GPS or automatic

    public int mActivityType;     // Running, cycling etc.
    public Calendar mDateTime;    // When does this entry happen
    public int mDuration;         // Exercise duration in seconds
    public float mDistance;      // Distance traveled. Either in meters or feet.
    public float mAvgPace;       // Average pace
    public float mAvgSpeed;     // Average speed
    public int mCalorie;        // Calories burnt
    public float mClimb;         // Climb. Either in meters or feet.
    public int mHeartRate;       // Heart rate
    public String mComment;       // Comments

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
    }

    public Long getId()
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

    public String getmComment() {return mComment; }

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

    public long getmDateTime()
    {
        return mDateTime.getTimeInMillis();
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

    public ExerciseEntry fromObject(JSONObject object)
    {
        ExerciseEntry entry = new ExerciseEntry();
        try {
            setId((Long) object.get("id"));
            setmActivityType(((Long) object.get("activityType")).intValue());
            setmInputType(((Long) object.get("inputType")).intValue());
            //setmDateTime((Calendar) object.get("dateTime"));
            setmDuration(((Long) object.get("duration")).intValue());
            setmDistance(((Long) object.get("distance")).intValue());
            setmAvgSpeed(((Long) object.get("avgSpeed")).floatValue());
            setmCalorie(((Long) object.get("calorie")).intValue());
            setmClimb(((Long) object.get("climb")).floatValue());
            setmHeartRate(((Long) object.get("heartrate")).intValue());
            setmComment((String) object.get("comment"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return entry;
    }
}