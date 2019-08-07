package com.example.oblakogroupandroid;

import java.util.ArrayList;

public class Project {

    Integer projectId;
    String title;
    ArrayList<Todo> todos;
    public static ArrayList<Project> instances = new ArrayList<Project>();

    public Project(String title) {
        this.title = title;
        projectId = instances.size() +1;
        todos = new ArrayList<Todo>();
        instances.add(this);
    }

    public Project(Integer projectId, String title) {
        this.projectId = projectId;
        this.title = title;
        todos = new ArrayList<Todo>();
        instances.add(this);
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

    public void addTodo(Todo todo) { this.todos.add(todo); }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ArrayList<Project> getInstances() {
        return instances;
    }

    public static Project getProjectByName (String name) {
        for (Project project: instances) {
            if(project.getTitle() == name){
                return project;
            }
        }
        return null;
    }
}