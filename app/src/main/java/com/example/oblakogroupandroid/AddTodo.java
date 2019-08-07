package com.example.oblakogroupandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class AddTodo extends AppCompatActivity {

    private Button cancelButton;
    private Spinner categorySpinner;
    private Integer categorySelected;
    private Button addButton;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);
        cancelButton = findViewById(R.id.cancel_buttonAT);
        addButton = findViewById(R.id.create_buttonAT);
        categorySpinner = findViewById(R.id.spinnerAT);
        editText = findViewById(R.id.edit_textAT);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BUTTON", "post button pressed");
                Log.d("BUTTON", "category" + categorySelected);
                categorySelected = Project.getProjectByName(categorySpinner.getSelectedItem().toString()).getProjectId();
                Log.d("BUTTON", "category" + categorySelected);
                postTodo();
                Intent intent = new Intent(AddTodo.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        List<String> projects = new ArrayList<String>();

        for (Project project : Project.instances) {
            projects.add(project.getTitle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,
                projects);
        categorySpinner.setAdapter(spinnerAdapter);

    }

    public void postTodo() {
        if (categorySelected != null) {
            JsonObject json = new JsonObject();
            json.addProperty("text", editText.getText().toString());
            json.addProperty("project_id", categorySelected);
            json.addProperty("isCompleted", false);

            Ion.with(this)
                    .load("https://evening-fjord-32551.herokuapp.com/todos")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.d("POST", "Post request completed");
                        }
                    });
        }
    }
}