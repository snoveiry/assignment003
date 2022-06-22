package com.noveiry.assignment.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noveiry.assignment.models.EventModel;
import com.noveiry.assignment.models.IsinModel;

@RestController
public class InstrumentController {

    @GetMapping(value = "/v1.0/instruments")
	public ResponseEntity<String> getInstrumentAll(){
        String outputString = callThirdparty();
        return new ResponseEntity<String>(outputString,HttpStatus.OK); 
    }
  
    @GetMapping(value = "/v1.0/instrument/{isin}")
	public ResponseEntity<IsinModel> getInstrumentOne(@PathVariable("isin") String isin){

        String outputString = callThirdparty();
        IsinModel outputIsin = new IsinModel();

        ObjectMapper om = new ObjectMapper();
        IsinModel[] root;
        try {
            root = om.readValue(outputString, IsinModel[].class);
            for (int i = 0 ; i < root.length ; i++) {
                if ( root[i].getIsin().equalsIgnoreCase(isin) ) {
                    System.out.println(root[i].getIsin());
                    outputIsin = root[i];
                    break;
                }  
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
                  
        return new ResponseEntity<IsinModel>(outputIsin,HttpStatus.OK);
    }


    @GetMapping(value = "/v1.0/instrument/{isin}/{start}/{end}")
	public ResponseEntity<IsinModel> getInstrumentByEventDate(@PathVariable("isin") String isin,@PathVariable("start") String fromDate,@PathVariable("end") String toDate) throws ParseException{
        String outputString = callThirdparty();
        IsinModel outputIsin = new IsinModel();

        ObjectMapper om = new ObjectMapper();
        IsinModel[] root;
        /*converting string dates with a special format */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*start json process */
        try {
            root = om.readValue(outputString, IsinModel[].class);
            /*loop for control isin key value */
            for (int i = 0 ; i < root.length ; i++) {
                if ( root[i].getIsin().equalsIgnoreCase(isin) ) {
                    ArrayList<EventModel> events = root[i].getEvents();
                    ArrayList<EventModel> changedEvents = new ArrayList<>();
                    /*loop for control event date */
                    for (int j = 0 ; j < events.size() ; j++){
                        EventModel temp = events.get(j);
                        if (sdf.parse(temp.getDate()).before(sdf.parse(toDate)) && sdf.parse(temp.getDate()).after(sdf.parse(fromDate))){
                            changedEvents.add(temp);
                        } 
                    }
                    root[i].setEvents(changedEvents);
                    outputIsin = root[i];
                    break;
                }  
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<IsinModel>(outputIsin,HttpStatus.OK);

    }

    /*this function will handle all 3rd party call */
    private String callThirdparty(){
        try {
            URL url = new URL("https://ssr.finanstilsynet.no/api/v2/instruments");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
    
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
    
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    
            String outputLine;
            String all = "";
            while ((outputLine = br.readLine()) != null) {
                all += outputLine;
            }
    
            conn.disconnect();
            return all;
    
          } catch (MalformedURLException e) {
    
            e.printStackTrace();
            return null;
    
          } catch (IOException e) {
    
            e.printStackTrace();
            return null;
    
          }
    }
    
}
