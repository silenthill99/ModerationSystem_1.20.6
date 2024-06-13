package fr.silenthill99.moderationsystem.mysql;

public class DbCredentials {
    private final String host;
    private final String user;
    private final String pass;
    private final String dbName;
    private final int port;

    public DbCredentials(String host, String user, String pass, String dbName, int port) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.port = port;
    }

    public String toURI() {
        StringBuilder bc = new StringBuilder();
        bc.append("jdbc:mysql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName);
        return bc.toString();
    }

    public String getPass() {
        return pass;
    }

    public String getUser() {
        return user;
    }
}
