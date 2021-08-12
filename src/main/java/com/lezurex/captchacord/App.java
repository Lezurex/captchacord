/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 5:30 PM
 */

package com.lezurex.captchacord;

import com.lezurex.captchacord.commands.CommandManager;
import com.lezurex.captchacord.commands.setup.SetupMessageListener;
import com.lezurex.captchacord.config.BotConfig;
import com.lezurex.captchacord.config.ConfigLoader;
import com.lezurex.captchacord.listeners.CommandListener;
import com.lezurex.captchacord.listeners.LoadListener;
import com.lezurex.captchacord.listeners.ServerManagingListener;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import javax.security.auth.login.LoginException;

@Getter
@Log4j2
public class App {

    public static void main(String[] args) {
        CommandLine commandLine;
        Option logOption = Option.builder("L").required(false).hasArg().desc("Sets the logging level.").longOpt("loglevel").build();
        Options options = new Options();
        options.addOption(logOption);
        CommandLineParser parser = new DefaultParser();

        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption("L")) {
                String value = commandLine.getOptionValue("L");
                Level level = Level.getLevel(value);
                if (level != null)
                    Configurator.setRootLevel(level);
                else {
                    log.printf(Level.ERROR, "'%s' is not a valid log level!", value);
                    System.exit(1);
                }
            }
        } catch (ParseException e) {
            log.error("Invalid arguments!");
            System.exit(1);
        }

        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");

        new App();
    }

    @Getter
    private static App instance;

    private final BotConfig botConfig;
    private JDA jda;
    private final CommandManager commandManager;

    public App() {
        instance = this;
        log.info("Hello darling my old frend");
        ConfigLoader configLoader = new ConfigLoader();
        this.botConfig = configLoader.load();
        this.commandManager = new CommandManager();

        createBot();
        registerListeners();
    }

    private void createBot() {
        JDABuilder builder = JDABuilder.createDefault(botConfig.botToken);
        builder.setActivity(Activity.competing("fighting spam bots"));

        try {
            this.jda = builder.build();
        } catch (LoginException e) {
            log.fatal("Failed to connect to discord! Please check your bot token in config.yml!");
            System.exit(1);
        }
    }

    private void registerListeners() {
        this.jda.addEventListener(new LoadListener());
        this.jda.addEventListener(new ServerManagingListener());
        this.jda.addEventListener(new CommandListener());
        this.jda.addEventListener(new SetupMessageListener());
    }
}
