package com.epam.chat;


public class ChatConstants {
    public static String SESSION_USER = "user";

    public static String NO_VALID_USER = "User didn't pass validation";
    public static String NO_VALID_MESSAGE = "Message didn't pass validation";
    public static String EXISTED_USER_LOGIN =
            "User with this login already exist";

    public static String WRONG_PASS_LOGIN =
            "User login/password  are wrong";
    public static String GO_TO_ADMIN = "Error occurs, send message to \" +\n" +
            "                        \"administrator";

    public static String DATA_SOURSE_PATH = "java:/comp/env/jdbc/MyLocalDB";
    private ChatConstants() {

    }
}