package com.lezurex.captchacord;

import com.lezurex.captchacord.config.BotConfig;
import com.lezurex.captchacord.config.ConfigLoader;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.logging.log4j.Level;

import javax.security.auth.login.LoginException;

@Getter
@Log4j2
public class App {

    private static App instance;

    private BotConfig botConfig;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        instance = this;

        ConfigLoader configLoader = new ConfigLoader();
        this.botConfig = configLoader.load();

        createBot();
    }

    private void createBot() {
        JDABuilder builder = JDABuilder.createDefault(botConfig.botToken);
        builder.setActivity(Activity.competing("spam bots"));

        try {
            builder.build();
        } catch (LoginException e) {
            log.fatal("Failed to connect to discord! Please check your bot token in config.yml!");
            System.exit(1);
        }
    }
}
