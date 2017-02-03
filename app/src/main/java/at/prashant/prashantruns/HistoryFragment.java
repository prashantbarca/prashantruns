package at.prashant.prashantruns;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by prashant on 1/17/17.
 */

public class HistoryFragment extends Fragment{

    ArrayList<HistoryFragmentItems> currentEntries = null;
    MyCursorAdapter adapter;
    LoaderManager loaderManager;
    boolean unit;
    Context mContext;
    List<ExerciseEntry> entries;
    ArrayList<ExerciseEntry> entry;
    Runnable r;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentEntries = new ArrayList<>();
        entry = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        Handler handler = new Handler();
        r = new Runnable() {
            public void run() {
                RunnerSQLHelper helper = new RunnerSQLHelper(getContext());
                currentEntries.clear();
                entry = helper.fetchEntries();
                currentEntries.addAll(generateData(entry));
                adapter = new MyCursorAdapter(getContext(), currentEntries);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), Display.class);
                        intent.putExtra("POSITION", entry.get(position).getId(position));
                        startActivity(intent);
                    }
                });

            }
        };
        r.run();
        return view;
    }

    /*
     * Resuming the view. Refresh the list using r.run()
     */
    @Override
    public void onResume() {
        r.run();
        super.onResume();
    }

    @Override
    public void onStart() {
        r.run();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /*
     * Generate data for the ListView
     */
    private ArrayList<HistoryFragmentItems> generateData(List<ExerciseEntry> exEntries)
    {
        entries = exEntries;
        //Check whether Unit for the distance field is specified as Kilometer
        unit = isKm(getContext());

        ArrayList<HistoryFragmentItems> items = new ArrayList<HistoryFragmentItems>();
        String title, description;
        //Format the data for all entries in the database
        for(int i=0;i<entries.size();i++)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("KK:mm:ss MMM dd yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(entries.get(i).getmDateTime().getTimeInMillis());

            String timeData = simpleDateFormat.format(calendar.getTime());
            title = entries.get(i).getmInputType() + ": " + entries.get(i).getmActivityType() + ", "
                    + timeData;

            //Depending on the unit display in miles or kilometers
            if(unit)
                description = (entries.get(i).getmDistance()*1.61) + " Kilometers, " +
                        entries.get(i).getmDuration() + " secs";
            else
                description = entries.get(i).getmDistance() + " Miles, " +
                        entries.get(i).getmDuration() + " secs";
            items.add(new HistoryFragmentItems(title, description));
        }

        return items;
    }
    public static boolean isKm(Context context) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String option = settings.getString("unit_key", "Mi");
        String option_metric = "Km";
        if(option.equals(option_metric))
            return true;
        else
            return false;
    }
}
