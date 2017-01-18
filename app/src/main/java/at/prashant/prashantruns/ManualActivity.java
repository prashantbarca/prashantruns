package at.prashant.prashantruns;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
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

import java.util.Calendar;
import java.util.List;

/*
Inspired from
https://developer.android.com/guide/topics/ui/layout/listview.html
 */

public class ManualActivity extends ListActivity implements AdapterView.OnItemClickListener
{
    static final String[] manual_strings = {"Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_manual);
        ListView listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, manual_strings);
        setListAdapter(adapter);
        setContentView(R.layout.activity_manual);
        listView = getListView();
        listView.setOnItemClickListener(this);
    }
    public void onCancel1(View v)
    {
        Toast.makeText(this, "Entry Discarded!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void onSave1(View v)
    {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Clicked", String.valueOf(position));
        switch (position) {
            case 0:
                Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DATE);
                DatePickerDialog dialog0 = new DatePickerDialog(ManualActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

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

                    }
                }, h, m, false);
                dialog1.show();
                break;
            case 2:
                EditText editText2 = new EditText(ManualActivity.this);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog2 = new AlertDialog.Builder(ManualActivity.this);
                dialog2.setTitle("Duration");
                dialog2.setView(editText2);
                dialog2.setCancelable(true);
                dialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog2.show();
                break;
            case 3:
                EditText editText3 = new EditText(ManualActivity.this);
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

                    }
                });
                dialog3.show();
                break;
            case 4:
                EditText editText4 = new EditText(ManualActivity.this);
                editText4.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder dialog4 = new AlertDialog.Builder(ManualActivity.this);
                dialog4.setTitle("Calories");
                dialog4.setView(editText4);
                dialog4.setCancelable(true);
                dialog4.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int fd) {

                    }
                });
                dialog4.show();
                break;
            case 5:
                EditText editText5 = new EditText(ManualActivity.this);
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

                    }
                });
                dialog5.show();
                break;
            case 6:
                EditText editText = new EditText(ManualActivity.this);
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

                    }
                });
                dialog.show();
                break;
        }
    }

}
