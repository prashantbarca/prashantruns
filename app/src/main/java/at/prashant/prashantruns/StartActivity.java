package at.prashant.prashantruns;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import at.prashant.prashantruns.SlidingTabLayout;

public class StartActivity extends AppCompatActivity {

    ArrayList <Fragment>  fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Inspired for XD's sample code.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        // Add all the fragments
        fragments.add(new StartFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new SettingsFragment());

        viewPager.setAdapter(new SamplePageAdapter(this.getFragmentManager(), fragments));
        slidingTabLayout.setDistributeEvenly(true); // Method in SlidingTabLayout file.
        slidingTabLayout.setViewPager(viewPager);
    }

}
