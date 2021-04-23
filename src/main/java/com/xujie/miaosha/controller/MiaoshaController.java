package com.xujie.miaosha.controller;

import com.xujie.miaosha.domain.MiaoshaOrder;
import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.domain.OrderInfo;
import com.xujie.miaosha.result.CodeMsg;
import com.xujie.miaosha.result.Result;
import com.xujie.miaosha.services.GoodsService;
import com.xujie.miaosha.services.MiaoshaService;
import com.xujie.miaosha.services.OrderService;
import com.xujie.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private MiaoshaService miaoshaservice;


    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha( Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId ) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1,req2
        Integer stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_ORVER);
        }
        //是否重复秒杀
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEAT_MIAO_SHA);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo info = miaoshaservice.miaosha(user, goods);
        return Result.success(info);
    }

}
