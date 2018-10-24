package com.jwt.entity;

import lombok.Data;

@Data
public class PortalUser {

    String username;
    String password;
    String authority;
}
