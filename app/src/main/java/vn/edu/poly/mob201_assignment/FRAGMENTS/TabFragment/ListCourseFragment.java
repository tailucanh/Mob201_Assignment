package vn.edu.poly.mob201_assignment.FRAGMENTS.TabFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.ADAPTER.GetAllCourseAdapter;
import vn.edu.poly.mob201_assignment.DTO.ObjCourse;
import vn.edu.poly.mob201_assignment.MyDatabase.CourseManagementSql;
import vn.edu.poly.mob201_assignment.R;
import vn.edu.poly.mob201_assignment.SERVICES.GetAllCourseServices;

public class ListCourseFragment extends Fragment {

    RecyclerView listViewCourse;
    ArrayList<ObjCourse> listCourse = new ArrayList<>();
    CourseManagementSql courseManagementSql;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewCourse = view.findViewById(R.id.listCourse);


        courseManagementSql = new CourseManagementSql(getContext());

        IntentFilter filterGetAllCourse = new IntentFilter("GetAllCourseServices");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(getAllCourseReceiver, filterGetAllCourse);

        IntentFilter filterRegisterCourse = new IntentFilter("RegisterAndUnRegisterCourseServices");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(getAllCourseReceiver, filterRegisterCourse);

        Intent intent = new Intent(getContext(), GetAllCourseServices.class);
        intent.putExtra("idStudent", "ps00709");
        intent.putExtra("isMine", false);
        intent.setAction("GetAllCourseServices");
        getActivity().startService(intent);


    }

    private final BroadcastReceiver getAllCourseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode",getActivity().RESULT_OK);
            if (resultCode == getActivity().RESULT_OK) {
                String action = intent.getStringExtra("action");
                switch (action){
                    case "GetAllCourseServices":
                    case "RegisterAndUnRegisterCourseServices":
                        listCourse.clear();
                        ArrayList<ObjCourse> allCourse = (ArrayList<ObjCourse>) intent.getSerializableExtra("allCourse");
                        ArrayList<ObjCourse> allCourseRegister = (ArrayList<ObjCourse>) intent.getSerializableExtra("allCourseRegister");

                        for(ObjCourse course : allCourse){
                            for(ObjCourse regCourse : allCourseRegister){
                                if(course.getId() == regCourse.getId()){
                                    course.setCheckRegistration(true);
                                    break;
                                }
                            }
                            listCourse.add(course);
                        }


                        GetAllCourseAdapter adapter = new GetAllCourseAdapter(listCourse, getContext(),courseManagementSql);
                        listViewCourse.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(getAllCourseReceiver);
    }
}
