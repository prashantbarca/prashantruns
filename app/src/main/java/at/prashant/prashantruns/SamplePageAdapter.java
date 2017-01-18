package at.prashant.prashantruns;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by prashant on 1/16/17.
 */
public class SamplePageAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public SamplePageAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments)
    {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    public String getPageTitle(int code)
    {
        if(code == 0)
            return "Start";
        else if(code == 1)
            return "History";
        else
            return "Settings";
    }
}
