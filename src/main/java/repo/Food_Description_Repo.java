package repo;

import com.google.gson.JsonElement;
import model.Food_Description_Info;
import model.Food_Menu_Info;
import org.slf4j.Logger;
import utils.JSONUtil;
import utils.PostgreSQLJDBC;
import utils.PropertiesCache;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Food_Description_Repo {

    private static final String TABLE_FOOD_DESCRIPTION = "food_description";
    JSONUtil jsonUtil = new JSONUtil();
    PropertiesCache propertiesCache = new PropertiesCache();
    private Logger logger;

// Insert new food_description to food_description table by food_menu_id
    public Map<String, String> InsertNewFood_Description(PostgreSQLJDBC post_Connection, Food_Description_Info food_description_info) {

        Statement stmt = null;
        JsonElement response_data = null;
        Map<String, String> element_data = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("InsertNewFood_In_Menu : Opened database successfully");

            String _get_return_description_id = "";

            String insert_query = "INSERT INTO " + TABLE_FOOD_DESCRIPTION + " (food_id," +
                    "language_type, " +
                    "food_name, " +
                    "food_combo_name, " +
                    "food_price_in_combo, " +
                    "food_description) " +

                    "VALUES ('"+ food_description_info.getFood_id() +"'," +
                    " '"+ food_description_info.getLanguage_type() +"'," +
                    " '"+ food_description_info.getFood_name() +"'," +
                    " '"+ food_description_info.getFood_combo_name() +"'," +
                    " "+ food_description_info.getFood_price_in_combo() +"," +
                    " '"+ food_description_info.getFood_description() +"') RETURNING description_id;";

            // execute 1st select query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            ResultSet rs1 = stmt.executeQuery(insert_query);
            while (rs1.next()) {

                _get_return_description_id = rs1.getString("description_id");
            }

            // close all parameters
            rs1.close();
            stmt.close();

            // this part is to add all JSON data from List into Map
            System.out.print("Return description_id : " + _get_return_description_id);
            element_data.put("description_id", _get_return_description_id);

            // return all food_place data in JSON format
            return element_data;

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            element_data.put("status", "ERROR");
            element_data.put("message : ", e.getMessage().toString());
            return element_data;
        }
    }






}
