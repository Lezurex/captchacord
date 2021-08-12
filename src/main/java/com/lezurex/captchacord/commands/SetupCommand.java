/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/12/21, 2:14 AM
 */

package com.lezurex.captchacord.commands;

import com.lezurex.captchacord.commands.setup.SetupSession;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

@Log4j2
public class SetupCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message, String[] args) {
        new SetupSession(member, channel);
    }
}
