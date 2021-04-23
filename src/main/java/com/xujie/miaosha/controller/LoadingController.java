package com.xujie.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.xujie.miaosha.result.CodeMsg;
import com.xujie.miaosha.result.Result;
import com.xujie.miaosha.services.MiaoshaUserService;
import com.xujie.miaosha.services.UserService;
import com.xujie.miaosha.util.ValidatorUtil;
import com.xujie.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/login")
public class LoadingController {

    private static Logger logger = LoggerFactory.getLogger(LoadingController.class);

    @Resource
    private MiaoshaUserService miaoshaUserService;


    @RequestMapping("/to_login")
    public String hello() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin( HttpServletResponse response, @Valid LoginVo loginVo ) {
        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);
    }
}
