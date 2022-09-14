package controller;

import model.Food_Description_Info;
import repo.Food_Description_Repo;
import utils.PostgreSQLJDBC;

import java.util.Map;
import java.util.UUID;

public class Food_Description_Controller {

    PostgreSQLJDBC post_Connection = new PostgreSQLJDBC();
    Food_Description_Repo food_description_repo = new Food_Description_Repo();

    public Food_Description_Controller(){}

//************************************************************************************************************************
// Insert new food_description to food_description table by food_menu_id
    public Map<String, String> InsertNewFood_Description_Controller(Food_Description_Info food_description_info) {

        return food_description_repo.InsertNewFood_Description(post_Connection, food_description_info);
    }

// Update language_type by description_id
    public Map<String, String> UpdateLanguage_Type_Controller(UUID description_id, String language_type) {

        return food_description_repo.UpdateLanguage_Type(post_Connection, description_id, language_type);
    }

// Update food_name by description_id
    public Map<String, String> UpdateFood_Name_Controller( UUID description_id, String food_name) {

        return food_description_repo.UpdateFood_Name(post_Connection, description_id, food_name);
    }

// Update food_combo_name by description_id
    public Map<String, String> UpdateFood_Combo_Name_Controller(UUID description_id,
                                                                String[] string_food_combo_name) {

        return food_description_repo.UpdateFood_Combo_Name(post_Connection, description_id, string_food_combo_name);
    }

// Update food_price_in_combo by description_id
    public Map<String, String> UpdateFood_Price_In_Combo_Controller(UUID description_id,
                                                         String[] string_food_price_in_combo) {

        return food_description_repo.UpdateFood_Price_In_Combo(post_Connection, description_id, string_food_price_in_combo);
    }

// Update food_description by description_id
    public Map<String, String> UpdateFood_Description_Controller(UUID description_id, String food_description) {

        return food_description_repo.UpdateFood_Description(post_Connection, description_id, food_description);
    }



}
