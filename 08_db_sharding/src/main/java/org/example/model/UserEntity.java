package org.example.model;

import lombok.Data;

@Data
public class UserEntity {
    private int id;
    private String username;
    private String passwd;
    private String telephone;
}
