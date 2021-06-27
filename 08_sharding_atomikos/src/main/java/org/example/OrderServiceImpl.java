package org.example;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.example.mapper.OrderMapper;
import org.example.model.OrderEntity;
import org.example.model.OrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public int update(List<OrderInfo> infos) {
        for (OrderInfo info : infos) {
            orderMapper.updateOrderNo(new OrderEntity(info.getId(), info.getUserId(), info.getOrderNo()));
            if (info.getUserId() % 2 != 0) {
                throw new IllegalArgumentException("单数的用户id不能更新");
            }
        }
        return 0;
    }

}
