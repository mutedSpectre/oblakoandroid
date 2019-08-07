package com.example.oblakogroupandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_SEPARATOR = 1;

    public ArrayList<Object> data;
    Context context;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TextTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textview_1);
        }
    }

    public static class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public CheckBoxViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof Todo) {
            return TYPE_ITEM;
        }else {
            return TYPE_SEPARATOR;
        }
    }

    public CustomAdapter(ArrayList<Object> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_1, parent,
                        false);
                return new CheckBoxViewHolder(view);
            case TYPE_SEPARATOR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_2, parent,
                        false);
                return new TextTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (data.get(position) != null) {
            switch (getItemViewType(position)){
                case TYPE_ITEM:
                    ((CheckBoxViewHolder) holder).checkBox.setText(((Todo) data.get(position)).getText());
                    ((CheckBoxViewHolder) holder).checkBox.setChecked(((Todo) data.get(position)).getCompleted());
                    ((CheckBoxViewHolder) holder).checkBox.setTag((Todo) data.get(position));
                    ((CheckBoxViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            Log.d("PRESS", ((Todo) compoundButton.getTag()).getText());
                            Todo todoInFocus = (Todo) compoundButton.getTag();
                            if (todoInFocus.getCompleted() == compoundButton.isChecked()) {
                                return;
                            }else {
                                JsonObject json = new JsonObject();
                                json.addProperty("todo_id", ((Todo) compoundButton.getTag()).id);
                                json.addProperty("isCompleted", ((Todo) compoundButton.getTag()).isCompleted);
                                Ion.with(compoundButton.getContext())
                                        .load("PUT", "https://evening-fjord-32551.herokuapp.com/update_todo")
                                        .setJsonObjectBody(json)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                Log.d("STATUS", "Update successful");
                                            }
                                        });
                            }
                        }
                    });
                    break;
                case TYPE_SEPARATOR:
                    ((TextTypeViewHolder) holder).textView.setText(((Project) data.get(position)).getTitle());
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}