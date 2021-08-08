/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 5:15 PM
 */

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
