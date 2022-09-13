package controller;

import com.google.gson.JsonElement;
import model.Food_Menu_Info;
import repo.Food_Menu_Repo;
import utils.PostgreSQLJDBC;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class Food_Menu_Controller {

    PostgreSQLJDBC post_Connection = new PostgreSQLJDBC();
    Food_Menu_Repo food_menu_repo = new Food_Menu_Repo();

    public Food_Menu_Controller (){}

//************************************************************************************************************************
// Insert new food_id to food_menu table by Shop ID
    public Map<String, String> InsertNewFood_In_Menu_Controller(Food_Menu_Info food_menu_info) {

        return food_menu_repo.InsertNewFood_In_Menu(post_Connection, food_menu_info);
    }

// Select all detail data of food_id from food_menu, food_description tables by food_place_id
    public JsonElement SelectFood_Menu_For_Guest_Controller(UUID food_place_id) {

        return food_menu_repo.SelectFood_Menu_For_Guest(post_Connection, food_place_id);
    }

// Update food_single_price by food_id. SHOP action.
    public Map<String, String> UpdateFood_Single_Price_Controller(UUID food_id, String food_single_price) {

        return food_menu_repo.UpdateFood_Single_Price(post_Connection, food_id, food_single_price);
    }

// Update food_is_removed, food_removed_since by food_id. SHOP action.
    public Map<String, String> UpdateFood_Is_Removed_Controller(UUID food_id,
                                                                 boolean food_is_removed,
                                                                 Timestamp food_removed_since) {

        return food_menu_repo.UpdateFood_Is_Removed(post_Connection, food_id, food_is_removed, food_removed_since);
    }

// Update food_multi_img_url by food_id. SHOP action.
    public Map<String, String> UpdateFood_Multi_Img_Url_Controller(UUID food_id,
                                                                    String[] string_food_multi_img_url) {

        return food_menu_repo.UpdateFood_Multi_Img_Url(post_Connection, food_id, string_food_multi_img_url);

    }

// Update food_multi_video_url by food_id. SHOP action.
public Map<String, String> UpdateFood_Multi_Video_Url_Controller(UUID food_id,
                                                      String[] string_food_multi_video_url) {

    return food_menu_repo.UpdateFood_Multi_Video_Url(post_Connection, food_id, string_food_multi_video_url);
}










}
