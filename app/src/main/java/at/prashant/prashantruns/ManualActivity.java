package at.prashant.prashantruns;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import android.os.Handler;
/*
Inspired from
https://developer.android.com/guide/topics/ui/layout/listview.html
 */

public class ManualActivity extends ListActivity implements AdapterView.OnItemClickListener
{
    static final String[] manual_strings = {"Date", "Time", "Duration", "Distance", "Calories",
            "Heart Rate", "Comment"};
    double duration = -1;
    int calories = -1;
    int heart_rate = -1;
    int newdate = -1;
    int newyear = -1;
    int newmonth = -1;
    int newhour = -1;
    int newmin = -1;
    int activity_type = -1;
    String comment = "";
    double distance = -1.0;
    /* On create
     * Set the content view
     * It looks for R.id.list then.
     * Initialise an adapter, and set it to listAdapter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        activity_type = Integer.parseInt(intent.getStringExtra("Activity"));
        setContentView(R.layout.activity_manual);
        ListView listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, manual_strings);
        setListAdapter(adapter);
        listView = getListView();
        listView.setOnItemClickListener(this);
    }

    /* Cancel button */
    public void onCancel1(View v)
    {
        RunnerSQLHelper helper = new RunnerSQLHelper(this);
        helper.fetchEntries();
        Toast.makeText(this, "Entry Discarded!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /* Save button */
    public void onSave1(View v)
    {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                RunnerSQLHelper dbHelper = new RunnerSQLHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Calendar calendar = Calendar.getInstance();
                if(distance == -1)
                    distance = 0;
                if(comment != "")
                    values.put(RunnerSQLHelper.columns[11],comment);
                if(calories == -1)
                    calories = 0;
                if(newyear != -1 && newmonth != -1 && newdate != -1)
                {
                    if(newhour != -1)
                        calendar.set(newyear, newmonth, newdate, newhour, newmin);
                    else
                        calendar.set(newyear, newmonth, newdate);
                }
                else if(newyear == -1 && newhour != -1)
                {
                    calendar.set(newyear, newmonth, newdate, newhour, newmin);
                }
                else
                {
                    calendar = Calendar.getInstance();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DATE), Calendar.getInstance().
                                    get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));
                }
                if(heart_rate == -1)
                    heart_rate = 0;
                if(duration == -1)
                    duration = 0.0;
                values.put(RunnerSQLHelper.columns[1],1); // This is for manual type
                values.put(RunnerSQLHelper.columns[2],activity_type);
                values.put(RunnerSQLHelper.columns[3], calendar.getTimeInMillis());
                values.put(RunnerSQLHelper.columns[4],duration);
                values.put(RunnerSQLHelper.columns[5],distance);
                values.put(RunnerSQLHelper.columns[8],calories);
                values.put(RunnerSQLHelper.columns[10],heart_rate);
                long id = db.insert(RunnerSQLHelper.TABLE_NAME, null, values);
                db.close();
                Toast.makeText(getApplicationContext(), "Recorded # " + String.valueOf(id),
                        Toast.LENGTH_SHORT).show();
            }
        };
        runnable.run();
        finish();
    }

    /*
     * Has all cases to handle all the listview items depending on the list item.
     * Each has a positive and negative button that do nothing as of now.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Clicked", String.valueOf(position));
        switch (position) {
            case 0:
                Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DATE);
                DatePickerDialog dialog0 = new DatePickerDialog(ManualActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        newyear = year;
                        newmonth = month;
                        newdate = dayOfMonth;
                    }
                }, yy, mm, dd);
                dialog0.show();
                break;
            case 1:
                Calendar calendar1 = Calendar.getInstance();
                int h = calendar1.get(Calendar.HOUR);
                int m = calendar1.get(Calendar.MINUTE);
                int s = calendar1.get(Calendar.SECOND);
                TimePickerDialog dialog1 = new TimePickerDialog(ManualActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker p, int h, int m) {
                        newhour = h;
                        newmin = m;
                    }
                }, h, m, false);
                dialog1.show();
                break;
            case 2:
                final EditText editText2 = new EditText(ManualActivity.this);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog2 = new AlertDialog.Builder(ManualActivity.this);
                dialog2.setTitle("Duration");
                dialog2.setView(editText2);
                dialog2.setCancelable(true);
                dialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {
                        // Do nothing
                    }
                });
                dialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {
                        if(!editText2.getText().toString().equals(""))
                            duration = Double.parseDouble(editText2.getText().toString());
                    }
                });
                dialog2.show();
                break;
            case 3:
                final EditText editText3 = new EditText(ManualActivity.this);
                editText3.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog3 = new AlertDialog.Builder(ManualActivity.this);
                dialog3.setTitle("Distance");
                dialog3.setView(editText3);
                dialog3.setCancelable(true);
                dialog3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {
                        if(!editText3.getText().toString().equals(""))
                            distance = Float.parseFloat(editText3.getText().toString());
                    }
                });
                dialog3.show();
                break;
            case 4:
                final EditText editText4 = new EditText(ManualActivity.this);
                editText4.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog4 = new AlertDialog.Builder(ManualActivity.this);
                dialog4.setTitle("Calories");
                dialog4.setView(editText4);
                dialog4.setCancelable(true);
                dialog4.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {
                        // Do nothing
                    }
                });
                dialog4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                        if(!editText4.getText().toString().equals(""))
                            calories = Integer.parseInt(editText4.getText().toString());
                    }
                });
                dialog4.show();
                break;
            case 5:
                final EditText editText5 = new EditText(ManualActivity.this);
                editText5.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog5 = new AlertDialog.Builder(ManualActivity.this);
                dialog5.setTitle("Heart Rate");
                dialog5.setView(editText5);
                dialog5.setCancelable(true);
                dialog5.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog5.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {
                        if(!editText5.getText().toString().equals(""))
                            heart_rate = Integer.parseInt(editText5.getText().toString());
                    }
                });
                dialog5.show();
                break;
            case 6:
                final EditText editText = new EditText(ManualActivity.this);
                editText.setHint("How did it go? Notes here.");
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManualActivity.this);
                dialog.setTitle("Comment");
                dialog.setView(editText);
                dialog.setCancelable(true);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                        if(!editText.getText().toString().equals(""))
                            comment = editText.getText().toString();
                    }
                });
                dialog.show();
                break;
        }
    }

}
