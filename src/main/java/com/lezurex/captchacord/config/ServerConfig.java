/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 5:16 PM
 */

package com.lezurex.captchacord.config;

import net.dv8tion.jda.api.entities.Guild;

import java.util.Optional;

public class ServerConfig {

    public String guildId;
    public String waitingChannel;
    public String verifiedRole;
    public String prefix;

    public ServerConfig() {}

    public ServerConfig(String guildId, String waitingChannel, String verifiedRole, String prefix) {
        this.guildId = guildId;
        this.waitingChannel = waitingChannel;
        this.verifiedRole = verifiedRole;
        this.prefix = prefix;
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
