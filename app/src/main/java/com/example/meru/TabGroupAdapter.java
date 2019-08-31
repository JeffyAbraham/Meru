package com.example.meru;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabGroupAdapter  extends FragmentStatePagerAdapter {


    public TabGroupAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new JoinedEvnts();
            case 1:
                // Games fragment activity
                return new JoinedEvnts();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Search Groups";
            case 1:
                return "Search Friends";


        }
        return null;
    }



    }


