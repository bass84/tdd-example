package com.example.tdd;

import java.time.ZonedDateTime;

public class BoardDto {
    private final long id;
    private final String title;
    private final String content;
    private final String author;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

    public BoardDto(long id, String title, String content, String author, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
