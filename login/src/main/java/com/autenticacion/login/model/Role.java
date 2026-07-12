package com.autenticacion.login.model;

    public enum Role{

        USER("USERS"),
        ADMIN("ADMIN");
    
    private final String roleName;

    Role(String roleName){
        this.roleName = roleName;
    }
    public String getRoleName(){
        return this.roleName;
    }
}

