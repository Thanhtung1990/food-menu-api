package repo;

import com.google.gson.JsonElement;
import model.Food_Combine_Flexible_Data;
import model.Food_Description_Info;
import model.Food_Menu_Info;
import org.slf4j.Logger;
import utils.JSONUtil;
import utils.PostgreSQLJDBC;
import utils.PropertiesCache;

import java.sql.*;
import java.util.*;

public class Food_Menu_Repo {

    private static final String TABLE_FOOD_MENU = "food_menu";
    private static final String TABLE_FOOD_DESCRIPTION = "food_description";
    JSONUtil jsonUtil = new JSONUtil();
    PropertiesCache propertiesCache = new PropertiesCache();
    private Logger logger;

// Insert new food_id to food_menu table by Shop ID
    public Map<String, String> InsertNewFood_In_Menu(PostgreSQLJDBC post_Connection, Food_Menu_Info food_menu_info) {

        Statement stmt = null;
        JsonElement response_data = null;
        Map<String, String> element_data = new HashMap<>();
        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("InsertNewFood_In_Menu : Opened database successfully");

            String _get_return_food_id=null;

            String insert_query = "INSERT INTO " + TABLE_FOOD_MENU + " (food_place_id," +
                                                                        "food_multi_img_url, " +
                                                                        "food_multi_video_url, " +
                                                                        "food_single_price, " +
                                                                        "food_score_review, " +
                                                                        "food_shared_amount, " +
                                                                        "food_is_created, " +
                                                                        "food_is_removed, " +
                                                                        "food_created_since, " +
                                                                        "food_removed_since, " +
                                                                        "food_issues_warning_level) " +

                                                                        "VALUES ('"+ food_menu_info.getFood_place_id() +"'," +
                                                                        " '"+ food_menu_info.getFood_multi_img_url() +"'," +
                                                                        " '"+ food_menu_info.getFood_multi_video_url() +"'," +
                                                                        " '"+ food_menu_info.getFood_single_price() +"'," +
                                                                        " "+ food_menu_info.getFood_score_review() +"," +
                                                                        " "+ food_menu_info.getFood_shared_amount() +"," +
                                                                        " '"+ food_menu_info.getFood_is_created() +"'," +
                                                                        " "+ food_menu_info.getFood_is_removed() +"," +
                                                                        " "+ food_menu_info.getFood_created_since() +"," +
                                                                        " "+ food_menu_info.getFood_removed_since() +"," +
                                                                        " '"+ food_menu_info.getFood_issues_warning_level() +"') RETURNING food_id;";

            // execute 1st select query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            ResultSet rs1 = stmt.executeQuery(insert_query);
            while (rs1.next()) {

                _get_return_food_id = rs1.getString("food_id");
            }

            // close all parameters
            rs1.close();
            stmt.close();

            // this part is to add all JSON data from List into Map
            System.out.print("Return food_id : " + _get_return_food_id);

            element_data.put("status", "SUCCESS");
            element_data.put("food_id", _get_return_food_id);

            // return all food_place data in JSON format
            return element_data;

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            element_data.put("status", "ERROR");
            element_data.put("message : ", e.getMessage().toString());
            return element_data;
        }
    }

// Select all detail data of food_id from food_menu, food_description tables by food_place_id
    public JsonElement SelectFood_Menu_For_Guest(PostgreSQLJDBC post_Connection, UUID food_place_id) {

        // init var
        Statement stmt_select_1, stmt_select_2, stmt_select_3;
        List<JsonElement> list_JSON_food_menu = new ArrayList<>();
        JsonElement response_data = null;
        JSONUtil jsonUtil = new JSONUtil();
        try {

            Food_Menu_Info food_menu_info = null;
            stmt_select_1 = post_Connection.postgreSQlConnect().createStatement();
            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("SelectFood_Menu_For_Guest : Opened database successfully");

            // First: Select all food_id by query food_place_id
            String select_all_food_id = "SELECT food_id FROM " + TABLE_FOOD_MENU +
                                        " WHERE food_place_id='" +
                                        food_place_id + "' ;";

            // execute 1st select query
            stmt_select_1 = post_Connection.postgreSQlConnect().createStatement();
            ResultSet rs1 = stmt_select_1.executeQuery(select_all_food_id);

            // getting food_id and using each food_id will run query Select detailed data from 2 tables: food_menu, food_description
            while (rs1.next()) { // each food_id loop

                String food_id = rs1.getString("food_id");

                // Start building and running the 2nd Select query
                String select_food_description_data_first = "SELECT description_id, " +
                                                            "language_type, " +
                                                            "food_name, " +
                                                            "food_combo_name, " +
                                                            "food_price_in_combo, " +
                                                            "food_description FROM food_description " +
                                                            "WHERE food_id='" + food_id + "' " +
                                                            " ORDER BY language_type" + " ;";

                // execute 2nd select query
                stmt_select_2 = post_Connection.postgreSQlConnect().createStatement();
                ResultSet rs2 = stmt_select_2.executeQuery(select_food_description_data_first);

                // creating a new Map to save all data from select query
                List<JsonElement> list_description_food = new ArrayList<>();

                    // getting detailed data from food_description table
                    while (rs2.next()) { // each food_description loop
                        // putting data into Java Object
                        Food_Description_Info food_description_info = new Food_Description_Info(UUID.fromString(rs2.getString("description_id")),
                                                                                                                rs2.getString("language_type"),
                                                                                                                rs2.getString("food_name"),
                                                                                                                rs2.getArray("food_combo_name"),
                                                                                                                rs2.getArray("food_price_in_combo"),
                                                                                                                rs2.getString("food_description"));

                        // converting data from Map to JSON Element
                        // creating a new Java Object to get JSON Element
                        list_description_food.add(jsonUtil._convertJavaToJson(food_description_info)); // contain only 1 JSON food_description

                    }// end while() food_description

                    // close description_food query
                    rs2.close();
                    stmt_select_2.close();

                // After adding all JSON food_description into List<>.
                // We need to create the 3rd Select query

                String select_food_menu = "SELECT food_multi_img_url, " +
                        "food_multi_video_url, " +
                        "food_single_price, " +
                        "food_score_review, " +
                        "food_shared_amount, " +
                        "food_created_since, " +
                        "food_issues_warning_level FROM food_menu " +
                //        "FULL OUTER JOIN food_description d " +
                //        "ON d.food_id = m.food_id " +
                        "WHERE food_id='" + food_id + "' " +
                        "food_is_created =" + true +
                        " AND food_is_removed =" + false +
                        " ORDER BY food_score_review" + " ;";

                // execute 2nd select query
                stmt_select_3 = post_Connection.postgreSQlConnect().createStatement();
                ResultSet rs3 = stmt_select_3.executeQuery(select_food_menu);


                // getting result in 1 SELECT query
                while (rs3.next()) { // each food_id detail data loop

                    // putting List<JSON> to Java Object food_flexible
                    Food_Combine_Flexible_Data    food_combine_flexible_data = new Food_Combine_Flexible_Data(UUID.fromString(food_id),
                            rs3.getArray("food_multi_img_url"),
                            rs3.getArray("food_multi_video_url"),
                            rs3.getString("food_single_price"),
                            rs3.getDouble("food_score_review"),
                            rs3.getString("food_shared_amount"),
                            rs3.getTimestamp("food_created_since"),
                            rs3.getInt("food_issues_warning_level"),
                            list_description_food);

                    // converting each Java object to JSON format
                    // And adding each food_place_info JSON to List


                    // converting and adding 1 new food_combine into a List<JSON>
                    list_JSON_food_menu.add(jsonUtil._convertJavaToJson(food_combine_flexible_data));

                    // Remove all elements in List. Reset size of List to 0
                    list_description_food.clear();
                }// end while() food_combine

                // close food_combine
                rs3.close();
                stmt_select_3.close();

            }// end while food_id

            System.out.println("Numner of JSON food_id are addded into List = " + list_JSON_food_menu.size() + " objects.");
            // using for each to convert Object to JSON Element
            rs1.close();
            stmt_select_1.close();

            // Creating a new Map to add List<JSON>
            Map<String, List<JsonElement>> response_JSON = new HashMap<>();

            // add List<JSON> to Map
            response_JSON.put("data", list_JSON_food_menu);

            // return 1 List<JSON> data
            return jsonUtil._convertMultiJSONObjectToJson_Status_Data(response_JSON);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

// Update food_single_price by food_id. SHOP action.
    public Map<String, String> UpdateFood_Single_Price(PostgreSQLJDBC post_Connection, UUID food_id, String food_single_price) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Single_Price : Opened database successfully");

            String update_query = "UPDATE " + TABLE_FOOD_MENU + " SET food_single_price = '" +
                                    food_single_price + "'" +
                                    " WHERE " +
                                    "food_id='" +
                                    food_id + "' RETURNING food_single_price;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Single_Price : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Single Price is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_is_removed, food_removed_since by food_id. SHOP action.
    public Map<String, String> UpdateFood_Is_Removed(PostgreSQLJDBC post_Connection,
                                                     UUID food_id,
                                                     boolean food_is_removed,
                                                     Timestamp food_removed_since) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Is_Removed : Opened database successfully");

            String update_query = "UPDATE " + TABLE_FOOD_MENU + " SET food_is_removed = " +
                                        food_is_removed + ", food_removed_since = '" +
                                        food_removed_since + "'" +
                                        " WHERE " +
                                        "food_id='" +
                                        food_id + "' RETURNING food_is_removed;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Is_Removed : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Is Removed is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_multi_img_url by food_id. SHOP action.
    public Map<String, String> UpdateFood_Multi_Img_Url(PostgreSQLJDBC post_Connection,
                                                     UUID food_id,
                                                        String[] string_food_multi_img_url) {

        PreparedStatement ps = null;
        Statement stmt = null;
        Map<String, String> element_response = new HashMap<>();

        try {

            // init connection to postgreSQL DB
            post_Connection.postgreSQlConnect().setAutoCommit(false);
            System.out.println("UpdateFood_Multi_Img_Url : Opened database successfully");

            Array array_food_multi_img_url = post_Connection.postgreSQlConnect().createArrayOf("STRING", string_food_multi_img_url);

            String update_query = "UPDATE " + TABLE_FOOD_MENU + " SET food_multi_img_url = '" +
                                    array_food_multi_img_url + "'" +
                                    " WHERE " +
                                    "food_id='" +
                                    food_id + "' RETURNING food_multi_img_url;";

            // execute sql query
            stmt = post_Connection.postgreSQlConnect().createStatement();
            stmt.executeQuery(update_query);
            post_Connection.postgreSQlConnect().commit();

            // close all
            stmt.close();
            post_Connection.postgreSQlConnect().close();
            System.out.println("UpdateFood_Multi_Img_Url : Records updated successfully");
            // build Map data
            element_response.put("status", "SUCCESS");
            element_response.put("message", "Food Multi Img Url is updated");
            return element_response;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            element_response.put("status", "ERROR");
            element_response.put("message : ", e.getMessage());
            return element_response;
        }
    }

// Update food_multi_video_url by food_id. SHOP action.
public Map<String, String> UpdateFood_Multi_Video_Url(PostgreSQLJDBC post_Connection,
                                                    UUID food_id,
                                                    String[] string_food_multi_video_url) {

    PreparedStatement ps = null;
    Statement stmt = null;
    Map<String, String> element_response = new HashMap<>();

    try {

        // init connection to postgreSQL DB
        post_Connection.postgreSQlConnect().setAutoCommit(false);
        System.out.println("UpdateFood_Multi_Video_Url : Opened database successfully");

        Array array_food_multi_video_url = post_Connection.postgreSQlConnect().createArrayOf("STRING", string_food_multi_video_url);

        String update_query = "UPDATE " + TABLE_FOOD_MENU + " SET food_multi_video_url = '" +
                            array_food_multi_video_url + "'" +
                            " WHERE " +
                            "food_id='" +
                            food_id + "' RETURNING food_multi_video_url;";

        // execute sql query
        stmt = post_Connection.postgreSQlConnect().createStatement();
        stmt.executeQuery(update_query);
        post_Connection.postgreSQlConnect().commit();

        // close all
        stmt.close();
        post_Connection.postgreSQlConnect().close();
        System.out.println("UpdateFood_Multi_Video_Url : Records updated successfully");
        // build Map data
        element_response.put("status", "SUCCESS");
        element_response.put("message", "Food Multi Video Url is updated");
        return element_response;

    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        element_response.put("status", "ERROR");
        element_response.put("message : ", e.getMessage());
        return element_response;
    }
}











}
