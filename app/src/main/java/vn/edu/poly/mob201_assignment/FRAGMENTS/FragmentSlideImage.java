package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.poly.mob201_assignment.ADAPTER.SliderCourseAdapter;
import vn.edu.poly.mob201_assignment.DTO.PhotoSliderCourse;
import vn.edu.poly.mob201_assignment.R;

public class FragmentSlideImage extends Fragment {

    ViewPager2 viewPager2;
    ArrayList<PhotoSliderCourse> listPhotos;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentItems = viewPager2.getCurrentItem();
            if(currentItems == listPhotos.size() - 1){
                viewPager2.setCurrentItem(0);
            }else {
                viewPager2.setCurrentItem(currentItems + 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide_image,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.sliderImg);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r* 0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);

        listPhotos = getListPhotos();
        SliderCourseAdapter photoAdapter = new SliderCourseAdapter(listPhotos);
        viewPager2.setAdapter(photoAdapter);



        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2500);
            }
        });
    }

    public ArrayList<PhotoSliderCourse> getListPhotos (){
        ArrayList<PhotoSliderCourse> listPhotos = new ArrayList<>();
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_4));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_1));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_3));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_2));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_5));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_6));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_slider_7));
        return listPhotos;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,2500);
    }



}
