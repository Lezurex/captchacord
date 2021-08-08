package com.lezurex.captchacord.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BotConfig {

    public String botToken;
    public List<ServerConfig> servers = new ArrayList<>();

    public Optional<ServerConfig> getServerConfigbById(String guildId) {
        return this.servers.stream().filter(serverConfig -> serverConfig.guildId.equals(guildId)).findFirst();
    }

    @Override
    public String toString() {
        return "BotConfig{" +
                "botToken='" + botToken + '\'' +
                ", servers=" + servers +
                '}';
    }
}
