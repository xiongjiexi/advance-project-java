package org.example;

import org.example.model.OrderInfo;

import java.util.List;

public interface OrderService {
    int update(List<OrderInfo> infos);
}
