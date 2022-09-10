package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtil {

    private static ObjectMapper mapper;

    static {
        mapper= new ObjectMapper();
    }

    public JsonElement _convertJavaToJson(Object object){

        // init var
        JsonElement resultAsJson = null;
        Map<String,String> standard_JSON_Element;

        try{

            resultAsJson = new Gson().toJsonTree(object);
            System.out.println("JSON Element = " + resultAsJson.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultAsJson;
    }

    //-----------------------------------------------------------------------
    public  JsonElement _convertJavaMapToJson_Status_Message(Map<String, String> element){

        JsonElement resultAsJson = null;

        try{

            Gson gson = new Gson();
            Type gsonType = new TypeToken<HashMap>(){}.getType();
            resultAsJson = gson.toJsonTree(element,gsonType);  // convert data in Map to JSON
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultAsJson;
    }

    //-----------------------------------------------------------------------
    public  JsonElement _convertMultiJSONObjectToJson_Status_Data(Map<String, List<JsonElement>> element){

        JsonElement resultAsJson = null;

        try{

            Gson gson = new Gson();
            Type gsonType = new TypeToken<HashMap>(){}.getType();
            resultAsJson = gson.toJsonTree(element,gsonType);  // convert data in Map to JSON
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultAsJson;
    }

    //-----------------------------------------------------------------------
    public static <T> T _convertJsonToJava(String jsonString, Class<T> cls){

        T resultAsJavaObject = null;
        try{

            resultAsJavaObject = mapper.readValue(jsonString, cls);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultAsJavaObject;

    }
}
