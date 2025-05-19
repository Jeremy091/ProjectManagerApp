package com.example.projectmanagerapp.model;

public class Task {
    private long id;
    private String title, description, startDate, endDate, status;
    private long projectId;

    public Task() {}

    public Task(long id, String title, String description, String startDate, String endDate, String status, long projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectId = projectId;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public long getProjectId() { return projectId; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setStatus(String status) { this.status = status; }
    public void setProjectId(long projectId) { this.projectId = projectId; }
}
