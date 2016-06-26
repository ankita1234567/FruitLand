package com.fruitland.fruitland.utils;

/**
 * Created by acer on 3/8/2016.
 */
public class Const {
    public static final String URL = "url";

    public static final String USER_ID = "user_id";

    public static final int NO = 0;
    public class ServiceCode {
        public static final int REGISTRATION = 1;
        public static final int CREATE_LINK = 2;
        public static final int GET_CUSTOMERS = 3;
        public static final int ADD_CUSTOMER = 4;
        public static final int PENDING_DELIVERY = 5;
        public static final int DELIVERY_FRUIT = 6;
        public static final int CUSTOMER_REGIONWISE = 7;
        public static final int ADD_DELIVERY = 12;
        public static final int FEEDBACK = 8;
        public static final int ADD_PURCHASE = 10;
        public static final int VIEW_PURCHASE = 11;
        public static final int CREATE_BASKET = 12;
        public static final int UPDATE_CUSTOMER = 13;



    }

    public class Params {
        public static final String MAIL = "email";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String USERID = "id";
        public static final String NAME = "name";
        public static final String CONTACT = "phone";
        public static final String CUSTOMER_IDS = "customer_ids";
        public static final String MONTH = "month";
        public static final String REGIONID = "region_id";
        public static final String WEEK = "week";
        public static final String DELIVERYID = "delivery_id";
        public static final String ISPAID = "is_paid";


    }

    public class ServiceType {
        private static final String BASE_URL = "http://fruitland.in/FruitApp/index.php/Customer/";
        public static final String REGISTRATION = BASE_URL + "registration";
        public static final String LOGIN = BASE_URL + "login";
        public static final String GET_CUSTOMERS = BASE_URL + "getAllCustomers";
        public static final String ADD_PURCHASE= BASE_URL + "add_purchase";
        public static final String ADD_CUSTOMER = BASE_URL + "add_new_customer";

        public static final String PENDING_DELIVERY = BASE_URL + "pending_deliveries";
        public static final String DELIVERY_FRUIT = BASE_URL + "deliver_fruit";
        public static final String CUSTOMER_REGIONWISE = BASE_URL + "get_customer_regionwise";
        public static final String ADD_DELIVERY = BASE_URL + "add_delivery";
        public static final String FEEDBACK = BASE_URL + "get_feedbacks";
        public static final String VIEW_PURCHASE = BASE_URL + "view_all_purchases";
        public static final String CREATE_BASKET = BASE_URL + "get_customer_avoided_fruits";
        public static final String UPDATE_CUSTOMER = BASE_URL + "update_new_customer";
    }
}
