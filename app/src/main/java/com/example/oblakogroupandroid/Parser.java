package com.example.oblakogroupandroid;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Parser {

    public static ArrayList<Object> parseJsonResponse(JsonArray response) {

        ArrayList<Object> resultArray = new ArrayList<Object>();

        for (int i =0; i<response.size(); i++) {
            JsonObject projectJson = (JsonObject) response.get(i);
            //converting json object to java
            Project project = new Project(projectJson.get("id").getAsInt(), projectJson.get("title").getAsString());
            resultArray.add(project);
            JsonArray todos;
            todos = projectJson.get("todo").getAsJsonArray();
            for (int j = 0; j<todos.size(); j++) {
                JsonObject todoJson = (JsonObject) todos.get(j);
                Todo todo = new Todo(todoJson.get("isCompleted").getAsBoolean(),
                        todoJson.get("text").getAsString(),
                        todoJson.get("project_id").getAsInt(),
                        todoJson.get("id").getAsInt());
                project.addTodo(todo);
                resultArray.add(todo);
            }
        }

        return resultArray;
    }

}