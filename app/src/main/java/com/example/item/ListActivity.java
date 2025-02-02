package com.example.item;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String urlString = "http://172.34.2.18:8000/api/items";

    private List<Item> item_list;
    private RecyclerView rv;
    private MyAdapter adapter;
    Context mContext;
    private SwipeRefreshLayout swipeContainer;
//    private SwipeAdapter swipeAdapter;
//    private URL myURL;

    //    private String accessToken;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        mContext = getApplicationContext();
        rv = (RecyclerView) findViewById(R.id.itemrecyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        item_list = new ArrayList<>();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        getItemData();
        swipeContainer.setOnRefreshListener(() -> {
            getItemData();
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void getItemData() {
        item_list.clear();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                urlString, null, response -> {
            try {

                JSONArray array = response.getJSONArray("data");
                Log.i("data", array.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject ob = array.getJSONObject(i);
                    Item listData = new Item(ob.getString("description")
                            , ob.getString("sell_price"),
                            ob.getString("cost_price"),
                            ob.getString("img_path"),
                            ob.getInt("item_id"));
                    item_list.add(listData);
                }
                adapter = new MyAdapter(mContext, item_list);
//                swipeAdapter = new SwipeAdapter(mContext, item_list);
                rv.setAdapter(adapter);
//                Intent i=getIntent();
//                accessToken = i.getStringExtra("access_token");
//                swipeAdapter = new SwipeAdapter(mContext, item_list, accessToken);
//                rv.setAdapter(swipeAdapter);

                swipeContainer.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Log.e("error :", "cannot get items"));
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonRequest);
    

    };

}



