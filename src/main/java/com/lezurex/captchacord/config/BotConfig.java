package com.lezurex.captchacord.config;

import java.util.List;

public class BotConfig {

    public String botToken;
    public List<ServerConfig> servers;

    @Override
    public String toString() {
        return "BotConfig{" +
                "botToken='" + botToken + '\'' +
                ", servers=" + servers +
                '}';
    }
}
