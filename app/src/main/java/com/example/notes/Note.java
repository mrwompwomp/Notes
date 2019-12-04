package com.example.notes;

import java.util.Date;

public class Note {
    private int id;
    private String Category;
private String Title;
private String Content;
private Date CreatedDate;
private Date DueDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public Note(int id, String category, String title, String content, Date createdDate, Date dueDate) {
        this.id = id;
        Category = category;
        Title = title;
        Content = content;
        CreatedDate = createdDate;
        DueDate = dueDate;
    }

    public Note(){}
}
