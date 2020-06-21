package io.github.final_project.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private static final int NUM_FRAGMENTS = 3;

    private Fragment[] fragments;

    /*
     * Ctrl + Q
     *
     * Constructor for FragmentPagerAdapter.
     * If BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT is passed in, then only the current Fragment is in the Lifecycle.State.RESUMED state.
     * All other fragments are capped at Lifecycle.State.STARTED. If BEHAVIOR_SET_USER_VISIBLE_HINT is passed,
     * all fragments are in the Lifecycle.State.RESUMED state and there will be callbacks to Fragment.setUserVisibleHint(boolean).
     *
     * */
    public ViewPagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragments = new Fragment[]{new FragMain(), new FragSearch(), new FragStar()};
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return fragments[position];
    }

    @Override
    public int getCount()
    {
        return NUM_FRAGMENTS;
    }
}
