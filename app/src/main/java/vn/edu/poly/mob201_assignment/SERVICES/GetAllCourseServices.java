package vn.edu.poly.mob201_assignment.SERVICES;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.DTO.ObjCourse;
import vn.edu.poly.mob201_assignment.MyDatabase.CourseManagementSql;

public class GetAllCourseServices extends IntentService {

    public GetAllCourseServices() {
        super("GetAllCourseServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            CourseManagementSql courseManagementSql = new CourseManagementSql(getApplicationContext());

            Intent i = new Intent("GetAllCourseServices");
            final String action = intent.getAction();
            String idStu = intent.getStringExtra("idStudent");
            boolean isMine = intent.getBooleanExtra("isMine", false);

            if (!isMine) {
                ArrayList<ObjCourse> allCourse = courseManagementSql.getAllCourse();
                i.putExtra("allCourse", allCourse);
            }

            ArrayList<ObjCourse> allCourseRegister = courseManagementSql.getAllCourseRegister(idStu);
            i.putExtra("allCourseRegister", allCourseRegister);
            courseManagementSql.close();
            i.putExtra("action", action);
            i.putExtra("resultCode", Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }
}
