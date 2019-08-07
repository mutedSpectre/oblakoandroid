package com.example.oblakogroupandroid;

import java.util.ArrayList;

public class Todo {
    Boolean isCompleted;
    String text;
    Integer projectId;
    Integer id;
    public static ArrayList<Todo> instances = new ArrayList<Todo>();



    public Todo(Boolean isCompleted, String text, Integer projectId, Integer id) {
        this.isCompleted = isCompleted;
        this.text = text;
        this.projectId = projectId;
        this.id = id;
        instances.add(this);
    }

    public ArrayList<Todo> getInstances() {
        return instances;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getText() {
        return text;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }
}
