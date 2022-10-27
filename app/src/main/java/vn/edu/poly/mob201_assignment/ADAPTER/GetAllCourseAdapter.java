package vn.edu.poly.mob201_assignment.ADAPTER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.DTO.ObjCourse;
import vn.edu.poly.mob201_assignment.DTO.ObjItemsRss;
import vn.edu.poly.mob201_assignment.MyDatabase.CourseManagementSql;
import vn.edu.poly.mob201_assignment.R;
import vn.edu.poly.mob201_assignment.SERVICES.RegisterAndUnRegisterCourseServices;

public class GetAllCourseAdapter extends  RecyclerView.Adapter<GetAllCourseAdapter.MyItemViewHolder> {

    ArrayList<ObjCourse> listCourse;
    Context context;
    CourseManagementSql courseManagementSql;


    public GetAllCourseAdapter(ArrayList<ObjCourse> listCourse, Context context , CourseManagementSql courseManagementSql) {
        this.listCourse = listCourse;
        this.context = context;
        this.courseManagementSql = courseManagementSql;
    }

    public void setFilter(ArrayList<ObjCourse> listCourse){
        this.listCourse = listCourse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GetAllCourseAdapter.MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_course, parent, false);
        return new GetAllCourseAdapter.MyItemViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull GetAllCourseAdapter.MyItemViewHolder holder, int position) {
        final int index = position;

        holder.tvTitleCourse.setText(listCourse.get(index).getCourseName());
        holder.imgCourse.setImageResource(listCourse.get(index).getIdCoursePhoto());
        holder.tvSchedule.setText(listCourse.get(index).getSchedule());

        if(listCourse.get(index).isCheckRegistration()){
            holder.textBtn.setText("Hủy đăng kí");
            holder.imgButton.setImageResource(R.drawable.ic_check_course);
            holder.bgButton.setBackground(context.getDrawable(R.drawable.custom_bg_button_item_course_2));
        }else  {
            holder.textBtn.setText("Đăng kí");
            holder.imgButton.setImageResource(R.drawable.ic_add_course);
            holder.bgButton.setBackground(context.getDrawable(R.drawable.custom_bg_button_item_course));
        }

        holder.bgButton.setOnClickListener(btn ->{
            Intent intent = new Intent(context, RegisterAndUnRegisterCourseServices.class);
            intent.putExtra("idStudent", "ps00709");
            intent.putExtra("idCourse", listCourse.get(index).getId());
            intent.putExtra("isRegister", !(listCourse.get(index).isCheckRegistration()));

            intent.setAction("RegisterAndUnRegisterCourseServices");
            context.startService(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listCourse.size();
    }


    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCourse,imgButton;
        TextView tvTitleCourse, tvSchedule;
        TextView textBtn;
        CardView cavBtn;
        ConstraintLayout bgButton;
        public MyItemViewHolder(@NonNull View view) {
            super(view);
            imgCourse = view.findViewById(R.id.imgCourse);
            tvTitleCourse = view.findViewById(R.id.tvTitleCourse);
            tvSchedule = view.findViewById(R.id.tvSchedule);
            textBtn = view.findViewById(R.id.tvBtn);
            imgButton = view.findViewById(R.id.imgAdd);
            bgButton = view.findViewById(R.id.bgButton);
            cavBtn = view.findViewById(R.id.cavBtn);
        }
    }
}

