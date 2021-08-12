/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/12/21, 3:07 AM
 */

package com.lezurex.captchacord.commands.setup;

import com.lezurex.captchacord.App;
import com.lezurex.captchacord.config.ConfigLoader;
import com.lezurex.captchacord.config.ServerConfig;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupSession {

    @Getter
    private static Map<Member, SetupSession> sessions = new HashMap<>();

    private int stage = 0;
    private final TextChannel channel;
    private final List<Message> sentMessages = new ArrayList<>();
    private final ServerConfig serverConfig;
    private final Member member;

    private TextChannel waitingChannel;
    private Role verifiedRole;

    public SetupSession(Member member, TextChannel channel) {
        if (sessions.containsKey(member)) {
            sessions.get(member).destroySession();
        }

        this.channel = channel;
        this.member = member;
        this.serverConfig = App.getInstance().getBotConfig().getServerConfigbById(channel.getGuild().getId()).orElseThrow(() -> new NullPointerException("Guild config not found!"));

        sessions.put(member, this);
        this.printStage();
    }

    private void printStage() {
        switch (this.stage) {
            case 0:
                this.sendMessage("Hey! We will now be setting up Captchacord for your server. Please mention the channel, where your users will be waiting while getting verified!");
                nextStage();
                break;
            case 2:
                this.sendMessage("Alright, now copy the ID of the role, which should be given after verification.\n" +
                        "You can do this by right-clicking the role in your server settings.");
                nextStage();
                break;
            case 4:
                this.sendMessage("Okay, " + verifiedRole.getName() + " role it is!\n" +
                        ":white_check_mark: The setup is now finished and your configuration has been saved!");
                new Thread(() -> {
                    try {
                        Thread.sleep(10000);
                        destroySession();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
        }
    }

    private void nextStage() {
        this.stage++;
        this.printStage();
    }

    public void handleMessage(Message message) {
        if (message.getContentDisplay().equalsIgnoreCase("cancel")) this.destroySession();
        if (message.getContentDisplay().contains("setup")) return;

        this.sentMessages.add(message);

        switch (this.stage) {
            case 1:
                if (!message.getMentionedChannels().isEmpty()) {
                    this.waitingChannel = message.getMentionedChannels().get(0);
                    this.nextStage();
                } else {
                    this.sendMessage(":x: You didn't mention a channel! Try again!");
                }
                break;
            case 3:
                Role role = message.getGuild().getRoleById(message.getContentDisplay());
                if (role == null) {
                    this.sendMessage(":x: Can't find this role! Try again!");
                } else {
                    this.verifiedRole = role;
                    this.serverConfig.verifiedRole = role.getId();
                    this.serverConfig.waitingChannel = this.waitingChannel.getId();
                    new ConfigLoader().save(App.getInstance().getBotConfig());
                    this.nextStage();
                }
        }
    }

    private void sendMessage(String message) {
        this.sentMessages.add(this.channel.sendMessage(message).complete());
    }

    private void destroySession() {
        this.sentMessages.forEach(message -> message.delete().queue());
        sessions.remove(this.member);
    }

}
