/*
 * Copyright (c) 2021 Lenny Angst. All rights reserved.
 * For more information about the license read the LICENSE file at the root of this repo.
 * Written for Project: captchacord
 * Last modified: 8/8/21, 4:38 PM
 */

package com.lezurex.captchacord.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

@Getter
public class ConfigLoader {

    private static final File configLocation = new File("config.yml");

    public BotConfig load() {
        if (!configLocation.exists() || !configLocation.isFile()) {
            this.generateConfig();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(configLocation);
            Yaml yaml = new Yaml();
            return yaml.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateConfig() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.yml");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(configLocation);
            if (inputStream != null) {
                inputStream.transferTo(fileOutputStream);
                inputStream.close();
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(BotConfig botConfig) {
        try {
            FileWriter fileWriter = new FileWriter(configLocation);
            Yaml yaml = new Yaml();
            yaml.dump(botConfig, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
