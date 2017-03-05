package com.example.prashant.myapplication.backend;

/**
 * Created by prashant on 2/22/17.
 */

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.util.Calendar;

public class ExerciseEntryEntityConverter {

    public static final String ENTITY_NAME = "ExerciseEntry";

    public static ExerciseEntry fromEntity(Entity entity) {
        ExerciseEntry entry = new ExerciseEntry();

        entry.setId((Long)entity.getProperty("row_id"));
        entry.setmInputType(0);
        entry.setmActivityType(1);
        entry.setmDuration(((Long)entity.getProperty("duration")).intValue());
        entry.setmDistance(((Double) entity.getProperty("distance")).floatValue());
        entry.setmAvgSpeed(((Double)entity.getProperty("avgSpeed" )).floatValue());
        entry.setmCalorie( ((Long)entity.getProperty("calorie" )).intValue());
        entry.setmClimb (((Double) entity.getProperty("climb")).floatValue());
        entry.setmHeartRate(((Long)entity.getProperty("heartrate")).intValue());
        entry.setmComment((String)entity.getProperty("comment"));

        return entry;
    }

    public static Entity toEntity(ExerciseEntry entry) {
        Entity entity = new Entity(ENTITY_NAME, entry.getId());

        entity.setProperty("row_id", entry.getId());
        entity.setProperty("inputType", entry.getmInputType());
        entity.setProperty("activityType", entry.getmActivityType());
        entity.setProperty("duration", entry.getmDuration());
        entity.setProperty("distance", entry.getmDistance());
        entity.setProperty("avgSpeed", entry.getmAvgSpeed());
        entity.setProperty("calorie", entry.getmCalorie());
        entity.setProperty("climb", entry.getmClimb());
        entity.setProperty("heartrate", entry.getmHeartRate());
        entity.setProperty("comment", entry.getmComment());

        return entity;
    }

}
