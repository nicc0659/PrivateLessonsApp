package model;

public class Account {

    private int id;
    private String username;
    private int admin;
    private String token;

    public Account(int id, String username, int admin) {
        this.id = id;
        this.username = username;
        this.admin = admin;
        token = null;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() { return token; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", admin=" + admin +
                ", token='" + token + '\'' +
                '}';
    }
}
