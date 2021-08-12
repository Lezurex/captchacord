/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/12/21, 2:41 AM
 */

package com.lezurex.captchacord.listeners;

import com.lezurex.captchacord.App;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentDisplay();
        String prefix = App.getInstance().getBotConfig().getServerConfigbById(event.getGuild().getId()).orElseThrow(() -> new NullPointerException("sus")).prefix;

        if (content.startsWith(prefix)) {
            String[] args = content.substring(prefix.length()).split(" ");
            if (!App.getInstance().getCommandManager().perform(args[0], event.getMember(), event.getChannel(), event.getMessage(), args)) {
                event.getChannel().sendMessage(":x: Command not found!").complete().delete().queueAfter(5, TimeUnit.SECONDS);
            }
            event.getMessage().delete().queue();
        }
    }
}
