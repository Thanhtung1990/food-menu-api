package repo;

import com.google.gson.JsonElement;
import model.Food_Description_Info;
import model.Food_Menu_Info;
import org.slf4j.Logger;
import utils.JSONUtil;
import utils.PostgreSQLJDBC;
import utils.PropertiesCache;

import java.sql.Array;
import java.sql.PreparedStatement;
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

// Update language_type by description_id
    public Map<String, String> UpdateLanguage_Type(PostgreSQLJDBC post_Connection, UUID description_id, String language_type) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateLanguage_Type : Opened database successfully");

            String update_query = "UPDATE " + TABLE_FOOD_DESCRIPTION + " SET language_type = '" +
                                    language_type + "'" +
                                    " WHERE " +
                                    "description_id='" +
                                    description_id + "' RETURNING description_id;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateLanguage_Type : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Language Type is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_name by description_id
    public Map<String, String> UpdateFood_Name(PostgreSQLJDBC post_Connection, UUID description_id, String food_name) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Name : Opened database successfully");

            String update_query = "UPDATE " + TABLE_FOOD_DESCRIPTION + " SET food_name = '" +
                                    food_name + "'" +
                                    " WHERE " +
                                    "description_id ='" +
                                    description_id + "' RETURNING description_id;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Name : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Name is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_combo_name by description_id
    public Map<String, String> UpdateFood_Combo_Name(PostgreSQLJDBC post_Connection,
                                                        UUID description_id,
                                                        String[] string_food_combo_name) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Combo_Name : Opened database successfully");

            Array array_food_combo_name = post_Connection.postgreSQlConnect().createArrayOf("STRING", string_food_combo_name);

            String update_query = "UPDATE " + TABLE_FOOD_DESCRIPTION + " SET food_combo_name = '" +
                    array_food_combo_name + "'" +
                    " WHERE " +
                    "description_id='" +
                    description_id + "' RETURNING food_combo_name;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Combo_Name : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Combo Name is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_price_in_combo by description_id
    public Map<String, String> UpdateFood_Price_In_Combo(PostgreSQLJDBC post_Connection,
                                                     UUID description_id,
                                                     String[] string_food_price_in_combo) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Price_In_Combo : Opened database successfully");

            Array array_food_price_in_combo = post_Connection.postgreSQlConnect().createArrayOf("STRING", string_food_price_in_combo);

            String update_query = "UPDATE " + TABLE_FOOD_DESCRIPTION + " SET food_price_in_combo = '" +
                    array_food_price_in_combo + "'" +
                    " WHERE " +
                    "description_id='" +
                    description_id + "' RETURNING food_price_in_combo;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Price_In_Combo : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Price In Combo is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_description by description_id
    public Map<String, String> UpdateFood_Description(PostgreSQLJDBC post_Connection, UUID description_id, String food_description) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Description : Opened database successfully");

            String update_query = "UPDATE " + TABLE_FOOD_DESCRIPTION + " SET food_description = '" +
                                    food_description + "'" +
                                    " WHERE " +
                                    "description_id ='" +
                                    description_id + "' RETURNING description_id;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Description : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Description is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }























}
