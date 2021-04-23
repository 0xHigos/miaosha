package com.xujie.miaosha.controller;

import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.domain.OrderInfo;
import com.xujie.miaosha.result.CodeMsg;
import com.xujie.miaosha.result.Result;
import com.xujie.miaosha.services.GoodsService;
import com.xujie.miaosha.services.OrderService;
import com.xujie.miaosha.vo.GoodsVo;
import com.xujie.miaosha.vo.OrderDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Resource
    private OrderService orderService;

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info( Model model, MiaoshaUser user, @RequestParam("orderId") long orderId ) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXISTS);
        }
        Long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoods(goods);
        return Result.success(detail);
    }
}
