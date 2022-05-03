package com.example.notepad.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Note implements Serializable {
    private int id;
    private String header;
    private LocalDateTime time;
    private String text;


    public Note(){

    }

    public Note(int id, String header, LocalDateTime time, String text) {
        this.id = id;
        this.header = header;
        this.time = time;
        this.text = text;
    }

    public Note(int id, String header, String time, String text) {
        this.id = id;
        this.header = header;
        this.time = LocalDateTime.parse(time);
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTimeInString() {
        return time.toString();
    }

    public void setTimeWithString(String time) {
        this.time = LocalDateTime.parse(time);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", time=" + time +
                ", text='" + text + '\'' +
                '}';
    }
}
