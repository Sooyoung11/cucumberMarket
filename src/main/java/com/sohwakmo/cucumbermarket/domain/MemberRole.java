package com.sohwakmo.cucumbermarket.domain;

public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String role;

    MemberRole(String role){
        this.role= role;
    }

    public String getRole(){
        return this.role;
    }
}
