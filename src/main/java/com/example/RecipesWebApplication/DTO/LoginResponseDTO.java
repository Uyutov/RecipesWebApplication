package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.Cook;

public class LoginResponseDTO {
    private Cook cook;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(Cook cook, String jwt){
        this.cook = cook;
        this.jwt = jwt;
    }

    public Cook getCook(){
        return this.cook;
    }

    public void setCook(Cook cook){
        this.cook = cook;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
