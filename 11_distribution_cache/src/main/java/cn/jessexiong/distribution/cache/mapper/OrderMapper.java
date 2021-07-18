package cn.jessexiong.distribution.cache.mapper;


import cn.jessexiong.distribution.cache.model.OrderEntity;
import org.apache.ibatis.annotations.*;

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

    @Update("update t_order set amount=#{amount} where id=#{id}")
    int update(OrderEntity orderEntity);
}
