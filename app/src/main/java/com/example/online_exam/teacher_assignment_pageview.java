package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class teacher_assignment_pageview extends AppCompatActivity {

    TabLayout tabLayout ;
    ViewPager2 viewPager2 ;
    teacher_assignmentview_adapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment_pageview);

        tabLayout = findViewById(R.id.tab_layout1) ;
        viewPager2 = findViewById(R.id.view_pager2) ;

        FragmentManager fm = getSupportFragmentManager() ;
        adapter = new teacher_assignmentview_adapter(fm, getLifecycle()) ;
        viewPager2.setAdapter(adapter) ;

        tabLayout.addTab(tabLayout.newTab().setText("Question"));
        tabLayout.addTab(tabLayout.newTab().setText("Student Answer"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}