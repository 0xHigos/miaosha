package com.xujie.miaosha.controller;

import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.redis.GoodsKey;
import com.xujie.miaosha.result.Result;
import com.xujie.miaosha.services.GoodsService;
import com.xujie.miaosha.services.RedisService;
import com.xujie.miaosha.vo.GoodsDetailVo;
import com.xujie.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private RedisService redisService;

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Resource
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toLList( HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user ) {
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isNoneEmpty(html)) {
            return html;
        }
        //查询商品列表
        List<GoodsVo> goods = goodsService.listGoodVo();
        model.addAttribute("goodsList", goods);
        model.addAttribute("user", user);
//        return "goods_list";

        SpringWebContext context = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (StringUtils.isNoneEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;

    }


    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail( HttpServletRequest request, HttpServletResponse response,
                            Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId ) {
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "", String.class);
        if (StringUtils.isNoneEmpty(html)) {
            return html;
        }

        //查询商品列表
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus;
        int remainSeconds = 0;

        if (now < startAt) {
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goods);
        model.addAttribute("user", user);
        //return "goods_detail";

        SpringWebContext context = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if (StringUtils.isNoneEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "", html);
        }
        return html;

    }

    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail( HttpServletRequest request, HttpServletResponse response,
                                         Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId ) {
        //查询商品列表
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus;
        int remainSeconds = 0;

        if (now < startAt) {
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 0;
        }

        //return "goods_detail";

        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setMiaoshaStatus(miaoshaStatus);
        vo.setRemainSeconds(remainSeconds);

        return Result.success(vo);

    }

}
