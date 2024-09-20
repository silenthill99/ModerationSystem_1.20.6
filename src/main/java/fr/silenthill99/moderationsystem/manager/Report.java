package fr.silenthill99.moderationsystem.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Report {
    private final UUID uuid;
    private final String date;
    private final String author;
    private final String reason;

    public Report(UUID uuid, String date, String author, String reason) {
        this.uuid = uuid;
        this.date = date;
        this.author = author;
        this.reason = reason;
    }

    public Report(UUID uuid, String author, String reason) {
        this(uuid, new SimpleDateFormat("dd/MM/yy - HH:mm").format(new Date()), author, reason);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getReason() {
        return reason;
    }
}
