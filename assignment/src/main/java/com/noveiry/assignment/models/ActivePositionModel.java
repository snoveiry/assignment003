package com.noveiry.assignment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivePositionModel {
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
    @JsonProperty("positionHolder") 
    public String getPositionHolder() { 
		 return this.positionHolder; } 
    public void setPositionHolder(String positionHolder) { 
		 this.positionHolder = positionHolder; } 
    String positionHolder;
}