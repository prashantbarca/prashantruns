package at.prashant.prashantruns;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.preference.*;

/**
 * Created by prashant on 1/17/17.
 */

public class SettingsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Just extend the PreferenceFragment class and include this method.
        addPreferencesFromResource(R.xml.preference);
    }
}
