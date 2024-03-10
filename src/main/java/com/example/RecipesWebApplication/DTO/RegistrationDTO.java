package com.example.RecipesWebApplication.DTO;

public class RegistrationDTO {
    private String email;
    private String nickname;
    private String password;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String email, String nickname, String password){
        super();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "Registration info: username: " + this.email + " password: " + this.password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
