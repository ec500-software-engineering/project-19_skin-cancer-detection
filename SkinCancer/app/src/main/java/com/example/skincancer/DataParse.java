package com.example.skincancer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParse {
    private HashMap<String,String> getplace(JSONObject googlePlaceJson){
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitue = "";
        String longtitue = "";
        String reference = "";
        try {
                if(!googlePlaceJson.isNull("name")){
                    
                    placeName = googlePlaceJson.getString("name");
                } 
                if(!googlePlaceJson.isNull("vicinity")){
                    vicinity = googlePlaceJson.getString("vicinity");
                }
                latitue = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longtitue = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = googlePlaceJson.getString("reference");
                googlePlacesMap.put("place_name",placeName);
                googlePlacesMap.put("vicinity",vicinity);
                googlePlacesMap.put("latitue",latitue);
                googlePlacesMap.put("longtitue",longtitue);
                googlePlacesMap.put("reference", reference);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;

    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray) throws JSONException {
        int count = jsonArray.length();
        List<HashMap<String,String>> placelist = new ArrayList<>();
        HashMap<String,String> placeMap = null;

        for(int i = 0; i<count; i++){
            try {
                placeMap = getplace((JSONObject) jsonArray.get(i));
                placelist.add((placeMap));

            }catch (JSONException e){
                e.printStackTrace();
            }
        };
        return placelist;
    }

    public List<HashMap<String,String>> parse(String jsonData) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray  = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

}
