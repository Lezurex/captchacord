/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 5:21 PM
 */

package com.lezurex.captchacord.listeners;

import com.lezurex.captchacord.App;
import com.lezurex.captchacord.config.ConfigLoader;
import com.lezurex.captchacord.config.ServerConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoadListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        checkGuilds();
    }

    private void checkGuilds() {
        App app = App.getInstance();
        List<Guild> guilds = app.getJda().getGuilds();
        if (app.getBotConfig().servers == null) app.getBotConfig().servers = new ArrayList<>();
        List<String> savedGuildIds = app.getBotConfig().servers.stream().map(serverConfig -> serverConfig.guildId).collect(Collectors.toList());
        guilds.stream().map(ISnowflake::getId).forEach(guildId -> {
            if (!savedGuildIds.contains(guildId)) {
                app.getBotConfig().servers.add(new ServerConfig(guildId, null, null, "cc."));
            }
        });

        savedGuildIds.stream().forEach(guildId -> {
            if (!guilds.stream().map(ISnowflake::getId).collect(Collectors.toList()).contains(guildId)) {
                ServerConfig serverConfig = app.getBotConfig().getServerConfigbById(guildId).orElseThrow(() -> new RuntimeException("Something happened here..."));
                app.getBotConfig().servers.remove(serverConfig);
            }
        });
        new ConfigLoader().save(app.getBotConfig());
    }
}
