package com.xujie.miaosha.services;

import com.xujie.miaosha.dao.OrderDao;
import com.xujie.miaosha.domain.MiaoshaOrder;
import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.domain.OrderInfo;
import com.xujie.miaosha.redis.OrderKey;
import com.xujie.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private RedisService redisService;


    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId( Long userId, long goodsId ) {
//        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderBuUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);
    }


    @Transactional
    public OrderInfo createOrder( MiaoshaUser user, GoodsVo goods ) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0l);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderBuUidGid, "" + user.getId() + "_" + goods.getId(), MiaoshaOrder.class);
        return orderInfo;

    }

    public OrderInfo getOrderById( long orderId ) {
        return orderDao.getOrderById(orderId);
    }
}
