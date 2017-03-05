package at.prashant.prashantruns.sync;

import at.prashant.prashantruns.ExerciseEntry;
import at.prashant.prashantruns.RunnerSQLHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by prashant on 2/19/17.
 */

public class SyncData {

    Context mContext;

    public SyncData(Context context) {
        mContext = context;
    }

    public void sync() {

        new AsyncTask<Void, Void, String>() {
            private Context context;

            @Override
            protected String doInBackground(Void... params)
            {
                String syncResult = "Sync Success";
                Log.d("Workss", "Workss");
                try {
                    RunnerSQLHelper runnerSQLHelper = new RunnerSQLHelper(mContext);
                    ArrayList<ExerciseEntry> entries = runnerSQLHelper.fetchEntries();
                    JSONArray jsonArray = new JSONArray();
                    for (ExerciseEntry entry : entries) {
                        jsonArray.put(entry.getObject());
                    }

                    JSONObject object = new JSONObject();
                    object.put("JSON", jsonArray);

                    byte[] bytes = ("entries_json ="+object.toString()).getBytes();
                    HttpURLConnection urlConnection;
            URL url = new URL("https://mercurial-cairn-159519.appspot.com/postdata?entries_json="
                            +object.toString());
                    Log.d("URL", url.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setFixedLengthStreamingMode(bytes.length);
                    urlConnection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded;charset=UTF-8");

                    /*
                     * Frankly speaking, I don't know which of the two work. I just couldn't
                     * figure out. Damn.
                     */
                    OutputStream out = urlConnection.getOutputStream();
                    out.write(bytes);
                      out.close();
                    int status = urlConnection.getResponseCode();
                    if (status != 200) {
                        throw new IOException("Post failed with error code " + status);
                    }
                    urlConnection.disconnect();
                    Log.d("TAG", object.toString());
                    Log.d("JSON", String.valueOf(status));
                }
                catch (Exception e)
                {
                    syncResult = "Failed Sync";
                    e.printStackTrace();

                }
                return syncResult;
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
            }

        }.execute();
    }

    }
