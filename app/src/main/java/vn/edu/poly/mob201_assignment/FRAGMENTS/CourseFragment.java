package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import vn.edu.poly.mob201_assignment.FRAGMENTS.TabFragment.ListCourseFragment;
import vn.edu.poly.mob201_assignment.FRAGMENTS.TabFragment.MyCourseFragment;
import vn.edu.poly.mob201_assignment.MainActivity;
import vn.edu.poly.mob201_assignment.R;

public class CourseFragment extends Fragment {

    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.ic_show_nav).setOnClickListener(ic ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });

        tabLayout = view.findViewById(R.id.tab_layout);


        tabLayout.addTab(tabLayout.newTab().setText("Danh sách"));
        tabLayout.addTab(tabLayout.newTab().setText("Khóa học của bạn"));
        if(tabLayout.getTabAt(0).isSelected()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListCourseFragment()).commit();
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getPosition() == 0){
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListCourseFragment()).commit();
               }else {
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyCourseFragment()).commit();
               }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }



}
