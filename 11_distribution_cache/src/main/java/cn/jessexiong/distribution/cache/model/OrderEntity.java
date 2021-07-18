package cn.jessexiong.distribution.cache.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderEntity {
    private long id;
    private long userId;
    private String orderNo;
    private BigDecimal amount;
}
