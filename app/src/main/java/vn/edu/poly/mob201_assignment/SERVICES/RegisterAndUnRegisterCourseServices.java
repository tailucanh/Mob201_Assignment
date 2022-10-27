package vn.edu.poly.mob201_assignment.SERVICES;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.DTO.ObjCourse;
import vn.edu.poly.mob201_assignment.MyDatabase.CourseManagementSql;

public class RegisterAndUnRegisterCourseServices extends IntentService {

    public RegisterAndUnRegisterCourseServices() {
        super("RegisterAndUnRegisterCourseServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            CourseManagementSql courseManagementSql = new CourseManagementSql(getApplicationContext());

            Intent i = new Intent("RegisterAndUnRegisterCourseServices");
            final String action = intent.getAction();
            String idStu = intent.getStringExtra("idStudent");
            int idCou= intent.getIntExtra("idCourse", -1);
            boolean isRegister = intent.getBooleanExtra("isRegister", false);

            if(isRegister){
                courseManagementSql.registerCourse(idStu, idCou); //đăng ký
            }else {
                courseManagementSql.unRegisterCourse(idStu, idCou); //hủy đăng ký
            }

            ArrayList<ObjCourse> allCourse = courseManagementSql.getAllCourse();
            ArrayList<ObjCourse> allCourseRegister = courseManagementSql.getAllCourseRegister(idStu);
            courseManagementSql.close();
            i.putExtra("allCourse", allCourse);
            i.putExtra("allCourseRegister", allCourseRegister);
            i.putExtra("action",action);
            i.putExtra("resultCode", Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }
}
