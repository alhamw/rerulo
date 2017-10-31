package com.dattabot.rerulo.config;

/**
 * Created by alhamwa on 10/30/17.
 */

public class ApiUtils {
    public static DattaBot getDattaBotervice() {
        return RetrofitClient.getClient(Config.BASE_URL).create(DattaBot.class);
    }
}
