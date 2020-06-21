package io.github.final_project.fragment;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    public abstract void updateList();

    @Override
    public void onResume()
    {
        super.onResume();

        updateList();
    }
}
