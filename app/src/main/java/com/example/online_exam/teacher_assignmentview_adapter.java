package com.example.online_exam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class teacher_assignmentview_adapter extends FragmentStateAdapter {


    public teacher_assignmentview_adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new teacher_assignment_student_ans() ;
        }
        return new teacher_assignment_question();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
