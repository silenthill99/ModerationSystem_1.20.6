package fr.silenthill99;

import fr.silenthill99.moderationsystem.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum CustomFiles {
    MODERATION(new File(Main.getInstance().getDataFolder(), "moderation.yml"));
    private final File file;
    private final YamlConfiguration config;

    CustomFiles(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void addValue(Player player) throws IOException {
        @NotNull List<String> uuid = config.getStringList("uuid");
        uuid.add(player.getUniqueId().toString());
        config.set("uuid", uuid);
        config.save(file);
    }

    public void removeValue(Player player) throws IOException {
        @NotNull List<String> uuid = config.getStringList("uuid");
        uuid.remove(player.getUniqueId().toString());
        config.set("uuid", uuid);
        config.save(file);
    }
}
