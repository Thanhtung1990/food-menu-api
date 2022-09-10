package controller;

import model.Food_Description_Info;
import repo.Food_Description_Repo;
import utils.PostgreSQLJDBC;

import java.util.Map;

public class Food_Description_Controller {

    PostgreSQLJDBC post_Connection = new PostgreSQLJDBC();
    Food_Description_Repo food_description_repo = new Food_Description_Repo();

    public Food_Description_Controller(){}

//************************************************************************************************************************
// Insert new food_description to food_description table by food_menu_id
    public Map<String, String> InsertNewFood_Description_Controller(Food_Description_Info food_description_info) {

        return food_description_repo.InsertNewFood_Description(post_Connection, food_description_info);
    }











}
