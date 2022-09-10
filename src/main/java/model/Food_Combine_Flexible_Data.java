package model;

import com.google.gson.JsonElement;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Food_Combine_Flexible_Data {

    private UUID food_id;
    private Array food_multi_img_url;
    private Array food_multi_video_url;
    private String food_single_price;
    private Double food_score_review;
    private String food_shared_amount;
    private Timestamp food_created_since;
    private int food_issues_warning_level;
    private List<JsonElement> list_description_food;

    // Constructor to Select query data
    public Food_Combine_Flexible_Data(UUID food_id,
                                      Array food_multi_img_url,
                                      Array food_multi_video_url,
                                      String food_single_price,
                                      Double food_score_review,
                                      String food_shared_amount,
                                      Timestamp food_created_since,
                                      int food_issues_warning_level,
                                      List<JsonElement> list_description_food) {

        this.food_id = food_id;
        this.food_multi_img_url = food_multi_img_url;
        this.food_multi_video_url = food_multi_video_url;
        this.food_single_price = food_single_price;
        this.food_score_review = food_score_review;
        this.food_shared_amount = food_shared_amount;
        this.food_created_since = food_created_since;
        this.food_issues_warning_level = food_issues_warning_level;
        this.list_description_food = list_description_food;
    }


    public UUID getFood_id() {
        return food_id;
    }

    public void setFood_id(UUID food_id) {
        this.food_id = food_id;
    }

    public Array getFood_multi_img_url() {
        return food_multi_img_url;
    }

    public void setFood_multi_img_url(Array food_multi_img_url) {
        this.food_multi_img_url = food_multi_img_url;
    }

    public Array getFood_multi_video_url() {
        return food_multi_video_url;
    }

    public void setFood_multi_video_url(Array food_multi_video_url) {
        this.food_multi_video_url = food_multi_video_url;
    }

    public String getFood_single_price() {
        return food_single_price;
    }

    public void setFood_single_price(String food_single_price) {
        this.food_single_price = food_single_price;
    }

    public Double getFood_score_review() {
        return food_score_review;
    }

    public void setFood_score_review(Double food_score_review) {
        this.food_score_review = food_score_review;
    }

    public String getFood_shared_amount() {
        return food_shared_amount;
    }

    public void setFood_shared_amount(String food_shared_amount) {
        this.food_shared_amount = food_shared_amount;
    }

    public Timestamp getFood_created_since() {
        return food_created_since;
    }

    public void setFood_created_since(Timestamp food_created_since) {
        this.food_created_since = food_created_since;
    }

    public int getFood_issues_warning_level() {
        return food_issues_warning_level;
    }

    public void setFood_issues_warning_level(int food_issues_warning_level) {
        this.food_issues_warning_level = food_issues_warning_level;
    }

    public List<JsonElement> getList_description_food() {
        return list_description_food;
    }

    public void setList_description_food(List<JsonElement> list_description_food) {
        this.list_description_food = list_description_food;
    }
}
