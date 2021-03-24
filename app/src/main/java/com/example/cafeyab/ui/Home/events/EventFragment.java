package com.example.cafeyab.ui.Home.events;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cafeyab.R;
import com.example.cafeyab.ui.Home.events.ConcertEventFragment;
import com.example.cafeyab.ui.Home.events.GameEventFragment;
import com.example.cafeyab.ui.Home.events.SportEventFragment;
import com.example.cafeyab.ui.Home.events.WorkshopEventFragment;
import com.google.android.material.tabs.TabLayout;




public class EventFragment extends Fragment{

   private Context mcontext;
   private ViewPager mViewPager;
   private TabLayout tabLayout;

//    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Titles of the individual pages (displayed in tabs)
    private final String[] PAGE_TITLES = new String[] {
            "Sports",
            "Games",
            "Concerts",
            "WorkShops"
    };

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[] {
            new SportEventFragment(),
            new GameEventFragment(),
            new ConcertEventFragment(),
            new WorkshopEventFragment()
    };
    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        mViewPager = view.findViewById(R.id.viewpager_event);
       tabLayout =view.findViewById(R.id.tab);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(mViewPager);



        return view;
    }

                    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
            public class MyPagerAdapter extends FragmentStatePagerAdapter {

                public MyPagerAdapter(FragmentManager fragmentManager) {
                    super(fragmentManager);
                }



                @Override
                public Fragment getItem(int position) {
                    return PAGES[position];
                }

                @Override
                public int getCount() {
                    return PAGES.length;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return PAGE_TITLES[position];
                }

            }
}

