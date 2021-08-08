/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 5:15 PM
 */

package com.lezurex.captchacord.listeners;

import com.lezurex.captchacord.App;
import com.lezurex.captchacord.config.ConfigLoader;
import com.lezurex.captchacord.config.ServerConfig;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ServerManagingListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        ServerConfig serverConfig = new ServerConfig(event.getGuild());
        App.getInstance().getBotConfig().servers.add(serverConfig);
        new ConfigLoader().save(App.getInstance().getBotConfig());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        App app = App.getInstance();
        ServerConfig serverConfig = app.getBotConfig().getServerConfigbById(event.getGuild().getId()).orElseThrow(() -> new RuntimeException("Left server is not present in config anymore."));
        app.getBotConfig().servers.remove(serverConfig);
        new ConfigLoader().save(App.getInstance().getBotConfig());
    }
}
