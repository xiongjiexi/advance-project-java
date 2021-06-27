package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.model.OrderEntity;

import java.util.List;

public interface OrderMapper {
    @Select("select * from t_order")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "orderNo", column = "order_no")
    })
    List<OrderEntity> getAll();

    @Select("select * from t_order where id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "orderNo", column = "order_no")
    })
    OrderEntity getOne(long id);

    @Insert("insert into t_order(user_id, order_no, amount) values(#{userId}, #{orderNo}, #{amount})")
    int insert(OrderEntity orderEntity);

    @Delete("delete from t_order where id = #{id}")
    int delete(long id);

    @Update("update t_order set user_id=#{userId}, order_no=#{orderNo}, amount=#{amount} where id=#{id} and user_id=#{userId}")
    int update(OrderEntity orderEntity);


    @Update("update t_order set order_no=#{orderNo} where id=#{id} and user_id=#{userId}")
    int updateOrderNo(OrderEntity orderEntity);
}
