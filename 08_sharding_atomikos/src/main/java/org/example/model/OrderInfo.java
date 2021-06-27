package org.example.model;

import lombok.Data;

@Data
public class OrderInfo {
    private long id;
    private long userId;
    private String orderNo;
}
