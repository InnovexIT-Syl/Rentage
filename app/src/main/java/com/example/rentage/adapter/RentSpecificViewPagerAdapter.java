package com.example.rentage.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RentSpecificViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> my_list = new ArrayList<>();
    private final List<String> my_title = new ArrayList<>();

    public RentSpecificViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
        return my_list.get(position);
    }

    @Override
    public int getCount() {
        return my_list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return my_title.get(position);
    }

    public void addMyFragments(Fragment fragment, String string) {
        my_list.add(fragment);
        my_title.add(string);

    }
}
