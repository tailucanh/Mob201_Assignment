package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.edu.poly.mob201_assignment.MainActivity;
import vn.edu.poly.mob201_assignment.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.icMenu).setOnClickListener(ic ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });
        view.findViewById(R.id.icCallAciCourse).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new CourseFragment()).commit();
        });
        view.findViewById(R.id.icCallAciMaps).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new MapsFragment()).commit();
        });
        view.findViewById(R.id.icCallAciSocial).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new SocialNetworkFragment()).commit();
        });
        view.findViewById(R.id.icCallAciNews).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new NewsFragment()).commit();
        });




    }
}
