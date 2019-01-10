package com.epam.chat;


public class ChatConstants {
    public static String SESSION_USER = "user";

    public static String NO_VALID_USER = "User didn't pass validation";
    public static String EXISTED_USER_LOGIN =
            "User with this login already exist";
    public static String NOT_EXISTED_USER_LOGIN = "User with this login don't" +
            " exist";

    public static String WRONG_PASS_LOGIN =
            "User login and password  are wrong";
    public static String SUCSESS = "Success";
    public static String GO_TO_ADMIN = "Error occurs, send message to \" +\n" +
            "                        \"administrator";

    private ChatConstants() {

    }
}