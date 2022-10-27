package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.loadinganimation.LoadingAnimation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.ADAPTER.RssAdapter;
import vn.edu.poly.mob201_assignment.DTO.ObjItemsRss;
import vn.edu.poly.mob201_assignment.HelloScreenActivity;
import vn.edu.poly.mob201_assignment.MainActivity;
import vn.edu.poly.mob201_assignment.R;

public class NewsFragment extends Fragment {
    LoadingAnimation loadingAnimation;
    String linkUrl = "https://vnexpress.net/rss/giao-duc.rss";
    RecyclerView recyclerViewItems;
    RssAdapter rssAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewItems = view.findViewById(R.id.listRss);
        swipeRefreshLayout = view.findViewById(R.id.swiperRefreshLayout);
        loadingAnimation = view.findViewById(R.id.loadingAnim);
        loadingAnimation.setVisibility(View.VISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo checkWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo check3G =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(checkWifi.isConnected() ||check3G.isConnected() ){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingAnimation.setVisibility(View.INVISIBLE);
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            return getData();
                        }
                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            ArrayList<ObjItemsRss> lists = (ArrayList<ObjItemsRss>)o;
                            rssAdapter = new RssAdapter(lists,getContext());
                            recyclerViewItems.setAdapter(rssAdapter);
                            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    rssAdapter = new RssAdapter(lists,getContext());
                                    recyclerViewItems.setAdapter(rssAdapter);
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    };
                    asyncTask.execute();
                }
            },2500);

        }else{
            loadingAnimation.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Đang chờ mạng...", Toast.LENGTH_LONG).show();
        }

        view.findViewById(R.id.ic_show_nav).setOnClickListener(ic ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });



    }


    private ArrayList<ObjItemsRss> getData() {
        ArrayList<ObjItemsRss> listItems = new ArrayList<>();
        try {

            URL url = new URL(linkUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, "utf-8");
            int event = parser.getEventType();
            String text = "";
            ObjItemsRss objItems = null;
            while (event != XmlPullParser.END_DOCUMENT) {
                String tag = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if(tag.equalsIgnoreCase("item")){
                            objItems = new ObjItemsRss();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(objItems != null){
                            if (tag.equalsIgnoreCase("title")) {
                                objItems.setTitle(text);
                            } else if (tag.equalsIgnoreCase("link")) {
                                objItems.setLink(text);
                            } else if (tag.equalsIgnoreCase("description")) {
                                objItems.setDescription(text);
                            } else if (tag.equalsIgnoreCase("pubDate")) {
                                objItems.setPubDate(text);
                            }else if(tag.equalsIgnoreCase("item")){
                                listItems.add(objItems);
                            }
                        }
                        break;
                }
                event = parser.next();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  listItems;
    }

}
