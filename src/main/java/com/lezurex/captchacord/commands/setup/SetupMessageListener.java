/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/12/21, 3:34 AM
 */

package com.lezurex.captchacord.commands.setup;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SetupMessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (SetupSession.getSessions().containsKey(event.getMember())) {
            SetupSession.getSessions().get(event.getMember()).handleMessage(event.getMessage());
        }
    }
}
