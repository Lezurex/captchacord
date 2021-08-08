package com.lezurex.captchacord;

import com.lezurex.captchacord.config.BotConfig;
import com.lezurex.captchacord.config.ConfigLoader;
import com.lezurex.captchacord.listeners.LoadListener;
import com.lezurex.captchacord.listeners.ServerManagingListener;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

@Getter
@Log4j2
public class App {

    public static void main(String[] args) {

    }

    @Getter
    private static final App instance = new App();

    private final BotConfig botConfig;
    private JDA jda;

    public App() {
        ConfigLoader configLoader = new ConfigLoader();
        this.botConfig = configLoader.load();

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
    }
}
