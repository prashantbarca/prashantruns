package at.prashant.prashantruns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Display extends AppCompatActivity {

    long position = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ExerciseEntry[] entry = new ExerciseEntry[1];
        ExerciseEntry exerciseEntry;
        setContentView(R.layout.activity_display);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Prashant Runs 3");
        Intent intent = getIntent();
        position = (Long) intent.getExtras().get("POSITION");
        final EditText editText = (EditText) findViewById(R.id.editText1);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);
        final EditText editText4 = (EditText) findViewById(R.id.editText4);
        final EditText editText5 = (EditText) findViewById(R.id.editText5);
        final EditText editText6 = (EditText) findViewById(R.id.editText6);
        final EditText editText7 = (EditText) findViewById(R.id.editText7);
        editText.setText("Manual Activity");
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("KK:mm:ss MMM dd yyyy");
                Calendar calendar = Calendar.getInstance();

                RunnerSQLHelper helper = new RunnerSQLHelper(getApplicationContext());
                entry[0] = helper.fetchEntryByIndex(position);
                calendar.setTimeInMillis(entry[0].getmDateTime().getTimeInMillis());
                String timeData = simpleDateFormat.format(calendar.getTime());
                editText3.setText(timeData);
                editText2.setText(entry[0].getmActivityType());
                editText4.setText(String.valueOf(entry[0].getmDuration()*60) + " secs");
                if(isKm(getApplicationContext()))
                    editText5.setText(String.valueOf(entry[0].getmDistance()*1.61) + " Km");
                else
                    editText5.setText(String.valueOf(entry[0].getmDistance()) + " Mi");
                editText6.setText(String.valueOf(entry[0].getmCalorie()));
                editText7.setText(String.valueOf(entry[0].getmHeartRate()));
            }
        };
        runnable.run();
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
    /*
      Helper to check if it is Km or Miles
     */
    public static boolean isKm(Context context) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String option = settings.getString("unit_key", "Mi");

        String option_metric = "Km";
        if (option.equals(option_metric))
            return true;
        else
            return false;
    }
}
