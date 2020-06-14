package io.github.final_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private static final int NUM_TABS = 3;

    private Fragment[] fragments;

    public ViewPagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm);

        fragments = new Fragment[]{new FragMain(), new FragSearch(), new FragStar()};
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return fragments[position];

//        switch (position)
//        {
//            case 0:
//                return FragMain.newInstance();
//            case 1:
//                return FragSearch.newInstance();
//            case 2:
//                return FragStar.newInstance();
//            default:
//                return null;
//        }
    }

    @Override
    public int getCount()
    {
        return NUM_TABS;
    }
}
