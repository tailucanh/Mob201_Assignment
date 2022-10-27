package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.loadinganimation.LoadingAnimation;

import vn.edu.poly.mob201_assignment.MainActivity;
import vn.edu.poly.mob201_assignment.R;

public class MapsFragment extends Fragment {

    private String url = "https://www.google.com/maps/place/Tr%C6%B0%E1%BB%9Dng+Cao+%C4%91%E1%BA%B3ng+FPT+Polytechnic/@21.0381278,105.7467871,15z/data=!4m5!3m4!1s0x0:0x53cefc99d6b0bf6f!8m2!3d21.0381278!4d105.7467871";
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Window window = getActivity().getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.white));

        webView = view.findViewById(R.id.webMaps);
        view.findViewById(R.id.ic_back).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
        });
        view.findViewById(R.id.ic_menu).setOnClickListener(ic ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });

        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.loadUrl(url);

    }
}
