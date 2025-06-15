package loginInfo;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String id;
    private String password;
    private String language;
    
    public User(String name, String id, String password, String language) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.language = language;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getLanguage() {
        return language;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
}