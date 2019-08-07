package com.example.oblakogroupandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter rvAdapter;
    private RecyclerView rvView;
    private ProgressBar progressBar;
    private ArrayList<Object> mainData = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("opensans_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        progressBar = findViewById(R.id.progress_bar);
        rvView = findViewById(R.id.listviewMainActivity);
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvView.setLayoutManager(rvLayoutManager);
        rvAdapter = new CustomAdapter(mainData, MainActivity.this);
        rvView.setAdapter(rvAdapter);
        populateListView();

        floatingActionButton = findViewById(R.id.AddButton);
        floatingActionButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodo.class);
                startActivity(intent);
            }
        });

    }
    public void populateListView() {

        Ion.with(MainActivity.this)
                .load("https://evening-fjord-32551.herokuapp.com/")
                .progressBar(progressBar)
                .setHeader("Accept", "application/json")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Log.d("RETURN", result.toString());
                        mainData.addAll(Parser.parseJsonResponse(result));
                        for(int i = 0; i < mainData.size(); i++) {
                            if (mainData.get(i) instanceof Project) {
                                Log.d("PARSED", ((Project)mainData.get(i)).getTitle());
                            } else {
                                Log.d("PARSED", ((Todo)mainData.get(i)).getText());
                            }
                        }
                        Log.d("DATA", mainData.toString());
                        rvAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


    }
}
