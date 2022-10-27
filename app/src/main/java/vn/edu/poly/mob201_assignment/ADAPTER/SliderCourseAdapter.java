package vn.edu.poly.mob201_assignment.ADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.DTO.PhotoSliderCourse;
import vn.edu.poly.mob201_assignment.R;

public class SliderCourseAdapter  extends RecyclerView.Adapter<SliderCourseAdapter.PhotoViewHolder>{
    ArrayList<PhotoSliderCourse> listPhotos;

    public SliderCourseAdapter(ArrayList<PhotoSliderCourse> listPhotos) {
        this.listPhotos = listPhotos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items_slide_onboarding_course,parent,false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoSliderCourse photo = listPhotos.get(position);
        holder.img_slide.setImageResource(photo.getIdPhoto());
    }

    @Override
    public int getItemCount() {
        return listPhotos.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView img_slide;
        public PhotoViewHolder(@NonNull View view) {
            super(view);
            img_slide = view.findViewById(R.id.imgSlider);

        }
    }
}
