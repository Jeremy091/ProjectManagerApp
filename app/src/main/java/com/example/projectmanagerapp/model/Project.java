package com.example.projectmanagerapp.model;

public class Project {
    private long id;
    private String name, description, startDate, endDate;
    private long userId;

    public Project() {}

    public Project(long id, String name, String description, String startDate, String endDate, long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public long getUserId() { return userId; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setUserId(long userId) { this.userId = userId; }
}
