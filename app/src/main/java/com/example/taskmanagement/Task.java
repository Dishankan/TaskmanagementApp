package com.example.taskmanagement;

public class Task {
    private int id;
    private String taskName;

    public Task(int id, String taskName) {
        this.id = id;
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }
}
