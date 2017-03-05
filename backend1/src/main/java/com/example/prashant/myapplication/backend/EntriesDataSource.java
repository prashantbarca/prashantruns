package com.example.prashant.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sun.rmi.runtime.Log;

/**
 * Created by prashant on 2/22/17.
 */

public class EntriesDataSource {


    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    public static boolean deleteall()
    {
        // Calls the method to delete individual ones.
        boolean success = true;
        for (ExerciseEntry entry : fetchAll()) {
            success = delete(""+entry.id);
        }

        return success;
    }

    public static boolean delete(String id) {
        // Used for deleting single ones.
        Query query = new Query(ExerciseEntryEntityConverter.ENTITY_NAME);
        Query.Filter filter = new Query.FilterPredicate("row_id",
                Query.FilterOperator.EQUAL, Long.parseLong(id));
        query.setFilter(filter);
        PreparedQuery preparedQuery = mDatastore.prepare(query);
        Entity result = preparedQuery.asSingleEntity();
        if (result == null) { return false; }
        mDatastore.delete(result.getKey());

        return true;
    }
    public static void add(JSONObject jsonObject)
    {
        try {
            ExerciseEntry entry = new ExerciseEntry();
            entry.fromObject(jsonObject);
            Entity entity = ExerciseEntryEntityConverter.toEntity(entry);
            Logger l = Logger.getLogger("ABCD");
            l.info(entity.toString());
            mDatastore.put(entity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static List<ExerciseEntry> fetchAll()
    {
        List<ExerciseEntry> resultList = new ArrayList<ExerciseEntry>();
        Query query = new Query(ExerciseEntryEntityConverter.ENTITY_NAME);
        query.setFilter(null);
        PreparedQuery preparedQuery = mDatastore.prepare(query);

        for (Entity entity : preparedQuery.asIterable()) {
            resultList.add(ExerciseEntryEntityConverter.fromEntity(entity));
        }
        return resultList;
    }
}
