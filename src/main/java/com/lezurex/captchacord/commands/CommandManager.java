/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/12/21, 2:37 AM
 */

package com.lezurex.captchacord.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();
        this.registerCommands();
    }

    private void registerCommands() {
        this.commands.put("setup", new SetupCommand());
    }

    public boolean perform(String command, Member member, TextChannel channel, Message message, String[] args) {
        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(member, channel, message, args);
            return true;
        }
        return false;
    }

}
