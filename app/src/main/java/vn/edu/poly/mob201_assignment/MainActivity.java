package vn.edu.poly.mob201_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import vn.edu.poly.mob201_assignment.FRAGMENTS.CourseFragment;
import vn.edu.poly.mob201_assignment.FRAGMENTS.HomeFragment;
import vn.edu.poly.mob201_assignment.FRAGMENTS.MapsFragment;
import vn.edu.poly.mob201_assignment.FRAGMENTS.NewsFragment;
import vn.edu.poly.mob201_assignment.FRAGMENTS.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    public static DrawerLayout drawerLayout;
    private boolean isBackPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findById();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();

                        drawerLayout.close();
                        break;
                    case R.id.ic_course:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new CourseFragment()).commit();

                        drawerLayout.close();
                        break;
                    case R.id.ic_news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new NewsFragment()).commit();

                        drawerLayout.close();
                        break;
                    case R.id.ic_social:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new SocialNetworkFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_gg_maps:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new MapsFragment()).commit();
                        drawerLayout.close();
                        break;

                }
                return true;
            }
        });

    }
    private void findById(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

    }


    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(MainActivity.this,"Chạm lần nữa để thoát !", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);
    }


}