package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class teacher_assignmentview_adapter extends FragmentStatePagerAdapter {

    int no_of_tabs ;

    public  teacher_assignmentview_adapter(FragmentManager fm, int no_of_tabs)
    {
        super(fm);
        this.no_of_tabs = no_of_tabs ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                teacher_assignment_question tab1 = new teacher_assignment_question() ;
                return tab1 ;
            case 1 :
                teacher_assignment_student_ans tab2 = new teacher_assignment_student_ans() ;
                return  tab2 ;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
