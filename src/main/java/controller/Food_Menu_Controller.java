package controller;

import com.google.gson.JsonElement;
import model.Food_Menu_Info;
import repo.Food_Menu_Repo;
import utils.PostgreSQLJDBC;

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






















}
