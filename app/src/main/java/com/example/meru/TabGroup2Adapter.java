package com.example.meru;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabGroup2Adapter  extends FragmentStatePagerAdapter {


    public TabGroup2Adapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new JoinedEvnts();
            case 1:

               return  new JoinedEvnts();

            case 2:
                return new JoinedEvnts();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Joined";
            case 1:
                return "Going";

            case 2:
                return "All";
        }
        return null;
    }



}


