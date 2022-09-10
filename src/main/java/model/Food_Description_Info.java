package model;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class Food_Description_Info {

    private UUID food_id;

    private UUID description_id;
    private String language_type;
    private String food_name;
    private Array food_combo_name;
    private Array food_price_in_combo;
    private String food_description;


    // constructor to insert data
    public Food_Description_Info(UUID food_id,
                                 UUID description_id,
                                 String language_type,
                                 String food_name,
                                 Array food_combo_name,
                                 Array food_price_in_combo,
                                 String food_description) {

        this.food_id = food_id;
        this.description_id = description_id;
        this.language_type = language_type;
        this.food_name = food_name;
        this.food_combo_name = food_combo_name;
        this.food_price_in_combo = food_price_in_combo;
        this.food_description = food_description;
    }

    // constructor to select data
    public Food_Description_Info(UUID description_id,
                                 String language_type,
                                 String food_name,
                                 Array food_combo_name,
                                 Array food_price_in_combo,
                                 String food_description) {

        this.description_id = description_id;
        this.language_type = language_type;
        this.food_name = food_name;
        this.food_combo_name = food_combo_name;
        this.food_price_in_combo = food_price_in_combo;
        this.food_description = food_description;
    }

    public UUID getFood_id() {
        return food_id;
    }

    public void setFood_id(UUID food_id) {
        this.food_id = food_id;
    }

    public UUID getDescription_id() {
        return description_id;
    }

    public void setDescription_id(UUID description_id) {
        this.description_id = description_id;
    }

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Array getFood_combo_name() {
        return food_combo_name;
    }

    public void setFood_combo_name(Array food_combo_name) {
        this.food_combo_name = food_combo_name;
    }

    public Array getFood_price_in_combo() {
        return food_price_in_combo;
    }

    public void setFood_price_in_combo(Array food_price_in_combo) {
        this.food_price_in_combo = food_price_in_combo;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }



}
