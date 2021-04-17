package com.xujie.miaosha.controller;

import com.xujie.miaosha.domain.MiaoshaUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {


    @RequestMapping("/to_list")
    public String toLList( Model model, MiaoshaUser user ) {
        model.addAttribute("user", user);
        return "goods_list";
    }


}
