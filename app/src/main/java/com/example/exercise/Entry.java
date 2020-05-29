package com.example.exercise;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Entity
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="text")
    private String text;
    @ColumnInfo(name="date")
    private String date;
    @ColumnInfo(name="is_work")
    private boolean isWork;
    @ColumnInfo(name="place")
    private String place;
    @ColumnInfo(name="coordinates")
    private String coordinates;
    public Entry(String title, String text, String place, String date, boolean isWork, String coordinates){
        this.text=text;
        this.title=title;
        this.isWork=isWork;
        this.date=date;
        this.place=place;
        this.coordinates=coordinates;
      /*  Date dateFull= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mmm-dd");
        date = dateFormat.format(dateFull);*/
    }
    public int getId(){
        return id;
    }
    public String getTitle(){ return title; }
    public String getDate(){
        return date;
    }
    public String getText(){
        return text;
    }
    public String getPlace(){
        return place;
    }
    public boolean getIsWork(){
        return isWork;
    }
    public String getCoordinates() { return coordinates; }
    public void setId(int id){
        this.id=id;
    }
    public void setCoordinates(String coordinates) { this.coordinates = coordinates; }
    public void setTitle(String title){ this.title=title; }
    public void setText(String text){ this.text=text; }
    public void setPlace(String place){  this.place=place; }
    public void setDate(String date){   this.date=date;  }
    public void setWork(boolean isWork){
        this.isWork=isWork;
    }
}
