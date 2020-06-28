package io.github.final_project.fragment;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    // abstract메소드로 모든 프래그먼트가 리사이클러뷰속 리스트를 업데이트하는 메소드를 갖게함.
    public abstract void updateList();

    // 생명주기 onResume이 실행될때 해당 프래그먼트 속 리사이클러뷰의 list를 업데이트하도록함.
    @Override
    public void onResume()
    {
        super.onResume();

        updateList();
    }
}
