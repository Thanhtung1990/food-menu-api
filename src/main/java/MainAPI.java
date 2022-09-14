import controller.Food_Description_Controller;
import controller.Food_Menu_Controller;
import model.Food_Description_Info;
import model.Food_Menu_Info;
import utils.JSONUtil;
import utils.PostgreSQLJDBC;

import java.sql.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.*;

public class MainAPI {

    private static Food_Menu_Controller food_menu_controller = new Food_Menu_Controller();
    private static Food_Description_Controller food_description_controller = new Food_Description_Controller();
    private static PostgreSQLJDBC post_Connection = new PostgreSQLJDBC();

    public static void main(String[] args) {

        port(5002);

        PostgreSQLJDBC post_Connection = new PostgreSQLJDBC();

        // get number of cores CPU are running
        int coreCount = Runtime.getRuntime().availableProcessors();

        // Programming Concurrency in Java to process multi-request from Clients
        // declare new ThreadPool
        ExecutorService service = Executors.newFixedThreadPool(coreCount);

//**************************************** CRUD API ****************************************
// POST New Food Place from Shop account
        service.execute(new new_food_menu_Task());

// GET all detail data of food_id from food_menu, food_description tables by food_place_id for guest
        service.execute(new food_menu_in_shop_Task());

// PUT params food_single_price by food_id. SHOP action.
        service.execute(new food_single_price_Task());

// PUT params food_is_removed, food_removed_since by food_id. SHOP action.
        service.execute(new food_is_removed_Task());

// PUT food_multi_img_url by food_id. SHOP action.
        service.execute(new food_multi_img_url_Task());

// PUT food_multi_video_url by food_id. SHOP action.
        service.execute(new food_multi_video_url_Task());


//--------------------------------------------------------------------------------------------
// Update language_type by description_id
        service.execute(new language_type_Task());

// Update food_name by description_id
        service.execute(new food_name_Task());

// Update food_combo_name by description_id
        service.execute(new food_combo_name_Task());

// Update food_price_in_combo by description_id
        service.execute(new food_price_in_combo_Task());

// Update food_description by description_id
        service.execute(new food_description_Task());


    }// end void main

//**************************************** CRUD API ****************************************
// POST New Food Place from Shop account
    static class new_food_menu_Task implements Runnable{
        @Override
        public void run() {

            post("/v1/food-menu-operation/new-food-menu", (req, res) -> {

                // get data body to Java class object
                Array  food_multi_img_url = post_Connection.postgreSQlConnect().createArrayOf("STRING", req.queryParamsValues("food_multi_img_url")) ;
                Array  food_multi_video_url = post_Connection.postgreSQlConnect().createArrayOf("STRING", req.queryParamsValues("food_multi_video_url")) ;

                Food_Menu_Info food_menu_info = new Food_Menu_Info(UUID.fromString(req.queryParams("food_place_id")),
                                                                null,
                                                                food_multi_img_url,
                                                                food_multi_video_url,
                                                                req.queryParams("food_name_multi_lang"),
                                                                0.0,
                                                                "",
                                                                true,
                                                                false,
                                                                Timestamp.from(Instant.now()),
                                                                Timestamp.from(Instant.now()),
                                                                0);

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();

                // creating a new map to clone data from method food_menu_controller
                Map<String, String> element_data = new HashMap<String, String>();

                // clone data
                element_data.putAll(food_menu_controller.InsertNewFood_In_Menu_Controller(food_menu_info));

                // creating new object of food_description
                Food_Description_Info food_description_info = new Food_Description_Info(UUID.fromString(element_data.get("food_id")),
                                                                                        null,
                                                                                        req.queryParams("language_type"),
                                                                                        req.queryParams("food_name"),
                                                                                        post_Connection.postgreSQlConnect().createArrayOf("STRING", req.queryParamsValues("food_combo_name")),
                                                                                        post_Connection.postgreSQlConnect().createArrayOf("STRING", req.queryParamsValues("food_price_in_combo")),
                                                                                        req.queryParams("food_description"));

                // put data of food description in multi languages to DB and get return description_id
                String description_id = food_description_controller.InsertNewFood_Description_Controller(food_description_info).get("description_id");

                // return JSON in format. status:, food_id:, description_id:
                element_data.put("description_id", description_id);
                return jsonUtil._convertJavaMapToJson_Status_Message(element_data);
                //return
            });
        }
    }

// GET all detail data of food_id from food_menu, food_description tables by food_place_id
    static class food_menu_in_shop_Task implements Runnable{
        @Override
        public void run() {

            get("/v1/food-menu-operation/food-menu-in-shop/:food_place_id", (req, res) -> food_menu_controller.SelectFood_Menu_For_Guest_Controller(UUID.fromString(req.params("food_place_id"))));
        }
    }

// PUT food_single_price by food_id. SHOP action.
    static class food_single_price_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-menu-operation/food-single-price", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_menu_controller.UpdateFood_Single_Price_Controller(UUID.fromString(req.queryParams("food_id")), req.queryParams("food_single_price")));
            });
        }
    }

// PUT params food_is_removed, food_removed_since by food_id. SHOP action.
    static class food_is_removed_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-menu-operation/food-is-removed", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_menu_controller.UpdateFood_Is_Removed_Controller(UUID.fromString(req.queryParams("food_id")),
                        Boolean.parseBoolean(req.queryParams("food_is_removed")),
                        Timestamp.valueOf(req.queryParams("food_removed_since"))));
            });
        }
    }

// PUT food_multi_img_url by food_id. SHOP action.
    static class food_multi_img_url_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-menu-operation/food-multi-img-url", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_menu_controller.UpdateFood_Multi_Img_Url_Controller(UUID.fromString(req.queryParams("food_id")),
                        req.queryParamsValues("food_multi_img_url")));
            });
        }
    }

// PUT food_multi_video_url by food_id. SHOP action.
    static class food_multi_video_url_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-menu-operation/food-multi-video-url", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_menu_controller.UpdateFood_Multi_Video_Url_Controller(UUID.fromString(req.queryParams("food_id")),
                        req.queryParamsValues("food_multi_video_url")));
            });
        }
    }

// Update language_type by description_id
    static class language_type_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-description-operation/language-type", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_description_controller.UpdateLanguage_Type_Controller(UUID.fromString(req.queryParams("description_id")),
                        req.queryParams("language_type")));
            });
        }
    }

// Update food_name by description_id
    static class food_name_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-description-operation/food_name", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_description_controller.UpdateFood_Name_Controller(UUID.fromString(req.queryParams("description_id")),
                        req.queryParams("food_name")));
            });
        }
    }

// Update food_combo_name by description_id
    static class food_combo_name_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-description-operation/food-combo-name", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_description_controller.UpdateFood_Combo_Name_Controller(UUID.fromString(req.queryParams("description_id")),
                        req.queryParamsValues("food_combo_name")));
            });
        }
    }

// Update food_price_in_combo by description_id
    static class food_price_in_combo_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-description-operation/food-price-in-combo", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_description_controller.UpdateFood_Price_In_Combo_Controller(UUID.fromString(req.queryParams("description_id")),
                        req.queryParamsValues("food_price_in_combo")));
            });
        }
    }

// Update food_description by description_id
    static class food_description_Task implements Runnable{
        @Override
        public void run() {

            put("/v1/food-description-operation/food-description", (req, res) -> {

                // insert data to DB and return response
                JSONUtil jsonUtil = new JSONUtil();
                return jsonUtil._convertJavaMapToJson_Status_Message(food_description_controller.UpdateFood_Description_Controller(UUID.fromString(req.queryParams("description_id")),
                        req.queryParams("UpdateFood_Description_Controller")));
            });
        }
    }



}
