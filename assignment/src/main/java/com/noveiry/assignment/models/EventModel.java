package com.noveiry.assignment.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventModel {
    @JsonProperty("date") 
    public String getDate() { 
		 return this.date; } 
    public void setDate(String date) { 
		 this.date = date; } 
    String date;
    @JsonProperty("shortPercent") 
    public double getShortPercent() { 
		 return this.shortPercent; } 
    public void setShortPercent(double shortPercent) { 
		 this.shortPercent = shortPercent; } 
    double shortPercent;
    @JsonProperty("shares") 
    public int getShares() { 
		 return this.shares; } 
    public void setShares(int shares) { 
		 this.shares = shares; } 
    int shares;
    @JsonProperty("activePositions") 
    public ArrayList<ActivePositionModel> getActivePositions() { 
		 return this.activePositions; } 
    public void setActivePositions(ArrayList<ActivePositionModel> activePositions) { 
		 this.activePositions = activePositions; } 
    ArrayList<ActivePositionModel> activePositions;
}

