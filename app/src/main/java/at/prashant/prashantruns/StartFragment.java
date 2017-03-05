package at.prashant.prashantruns;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.testing.http.apache.MockHttpClient;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import at.prashant.prashantruns.sync.SyncData;

/**
 * Created by prashant on 1/17/17.
 */

public class StartFragment extends Fragment {
    /*
     * If the Manual entry option was selected, we show different Activities.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start,container, false);
        Button button_start = (Button) view.findViewById(R.id.button_start);
        Button button_sync = (Button) view.findViewById(R.id.button_sync);
        final Spinner spinner = (Spinner) view.findViewById(R.id.input_spinner);
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.activity_spinner);
        button_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(String.valueOf(spinner.getSelectedItem()).equals("Manual Entry"))
                {
                    Intent intent = new Intent(getActivity().getBaseContext(), ManualActivity.class);
                    intent.putExtra("Activity", String.valueOf(spinner1.getSelectedItemId()));
                    Log.d("Put Type in bundle", String.valueOf(spinner.getSelectedItemId()));
                    Log.d("Activity selected", String.valueOf(spinner1.getSelectedItemId()));
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getActivity().getBaseContext(), MapActivity.class);
                    Log.d("Put Type in bundle", String.valueOf(spinner.getSelectedItemId()));
                    intent.putExtra("Type", String.valueOf(spinner.getSelectedItemId()));
                    intent.putExtra("Activity", "13");
                    startActivity(intent);
                }
            }
        }
        );
        button_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncData syncData = new SyncData(getContext());
                syncData.sync();
            }
        });
        return view;
    }
}
