package at.prashant.prashantruns;

import android.icu.util.Calendar;

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
   // private ArrayList<LatLng> mLocationList; // Location list


    /*
     * Thanks to android studio for auto generating these.
     */

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

    public void setmHeartRate(int mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    public void setmAvgSpeed(float mAvgSpeed) {
        this.mAvgSpeed = mAvgSpeed;
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

    public String getmInputType()
    {
        if(mInputType == 1)
            return "Manual Entry";
        else
            return "Map Entry";
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
}