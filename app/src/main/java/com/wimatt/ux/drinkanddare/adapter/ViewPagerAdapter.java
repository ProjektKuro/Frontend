package com.wimatt.ux.drinkanddare.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    /**
     * The Fragment list.
     */
    private final List<Fragment> fragmentList = new ArrayList<>();

    /**
     * Instantiates a new View pager adapter.
     *
     * @param fm the fm
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /**
     * Add fragment.
     *
     * @param fragment the fragment
     */
    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }

    /**
     * Add fragments.
     *
     * @param fragments the fragments
     */
    public void addFragments(Fragment... fragments){
        fragmentList.addAll(Arrays.asList(fragments));
    }
}
