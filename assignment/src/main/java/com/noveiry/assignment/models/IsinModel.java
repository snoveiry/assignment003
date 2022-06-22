package com.noveiry.assignment.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IsinModel {
    @JsonProperty("isin") 
    public String getIsin() { 
		 return this.isin; } 
    public void setIsin(String isin) { 
		 this.isin = isin; } 
    String isin;
    @JsonProperty("issuerName") 
    public String getIssuerName() { 
		 return this.issuerName; } 
    public void setIssuerName(String issuerName) { 
		 this.issuerName = issuerName; } 
    String issuerName;
    @JsonProperty("events") 
    public ArrayList<EventModel> getEvents() { 
		 return this.events; } 
    public void setEvents(ArrayList<EventModel> events) { 
		 this.events = events; } 
    ArrayList<EventModel> events;
}

