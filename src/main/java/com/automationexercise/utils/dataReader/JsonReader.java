package com.automationexercise.utils.dataReader;

import com.automationexercise.utils.logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {
    String jsonReader;
    String jsonFilename;

    private final String TEST_DATA_PATH = "src/test/resources/test-data/";

    public JsonReader(String jsonFilename) {
        this.jsonFilename = jsonFilename;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH+ jsonFilename + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
          LogsManager.error("Error reading json file:",jsonFilename,e.getMessage());
          jsonReader = "{}"; // Initialize to an empty JSON object to avoid null pointer exceptions
        }

    }


    public String getJsonData (String jsonPath){
        try{

            return JsonPath.read(jsonReader, jsonPath);
        }
        catch (Exception e){
            LogsManager.error("Error reading json file for path:",jsonPath,e.getMessage());
            return "";
        }

    }

}
