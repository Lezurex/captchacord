package com.lezurex.captchacord.config;

import net.dv8tion.jda.api.entities.Guild;

import java.util.Optional;

public class ServerConfig {

    public String guildId;
    public String waitingChannel;
    public String verifiedRole;

    public ServerConfig() {}

    public ServerConfig(String guildId, String waitingChannel, String verifiedRole) {
        this.guildId = guildId;
        this.waitingChannel = waitingChannel;
        this.verifiedRole = verifiedRole;
    }

    public ServerConfig(Guild guild) {
        this.guildId = guild.getId();
        this.waitingChannel = null;
        this.verifiedRole = null;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "guildId='" + guildId + '\'' +
                ", waitingChannel='" + waitingChannel + '\'' +
                ", verifiedRole='" + verifiedRole + '\'' +
                '}';
    }
}
