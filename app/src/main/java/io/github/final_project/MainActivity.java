package io.github.final_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.github.final_project.fragment.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        // 뷰페이저를 이용해 탭을 설정함
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // 탭의 아이콘 설정
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_article_24px);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_24px);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_border_24px);
    }
}
