package com.lezurex.captchacord.config;

public class ServerConfig {

    public String waitingChannel;
    public String verifiedRole;

    @Override
    public String toString() {
        return "ServerConfig{" +
                "waitingChannel='" + waitingChannel + '\'' +
                ", verifiedRole='" + verifiedRole + '\'' +
                '}';
    }
}
