package at.prashant.prashantruns;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prashant on 2/1/17.
 */

public class MyCursorAdapter extends ArrayAdapter<HistoryFragmentItems> {

    /*
     * Initially named it CursorAdapter, but then realised it was a bad idea to use CursorAdapter
     * and switched back to ArrayAdapter
     */
    private final Context context;
    private final ArrayList<HistoryFragmentItems> itemsArrayList;
    public MyCursorAdapter(Context context, ArrayList<HistoryFragmentItems> itemsArrayList) {
        super(context, R.layout.custom, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.label);
        TextView textView1 = (TextView) view.findViewById(R.id.value);
        textView.setText(itemsArrayList.get(position).getTitle());
        textView1.setText(itemsArrayList.get(position).getDescription());
        return view;
    }
}
