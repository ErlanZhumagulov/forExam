package com.example.demo.models.enums;

public enum Status {
    ACTIVATED, NO_ACTIVATED;



    public String getAuthority() {
        return name();
    }
}
