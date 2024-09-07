package com.example.item;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailView extends AppCompatActivity {
    private TextView nametxt;
    private ImageView fullimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_view);

        nametxt=(TextView)findViewById(R.id.name_txt);
        fullimg=(ImageView)findViewById(R.id.full_image);
        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String imageurl=i.getStringExtra("imageurl");
        nametxt.setText(name);
        Picasso.get()
                .load(imageurl)
                .into(fullimg);
        Log.i("url","test"+imageurl);
    }
}