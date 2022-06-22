package com.noveiry.assignment.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
