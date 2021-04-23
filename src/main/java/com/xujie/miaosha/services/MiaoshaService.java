package com.xujie.miaosha.services;

import com.xujie.miaosha.domain.Goods;
import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.domain.OrderInfo;
import com.xujie.miaosha.redis.MiaoshaKey;
import com.xujie.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MiaoshaService {

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha( MiaoshaUser user, GoodsVo goods ) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        System.out.println(success);
        if (success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    private void setGoodsOver( Long goodsId ) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }
}
