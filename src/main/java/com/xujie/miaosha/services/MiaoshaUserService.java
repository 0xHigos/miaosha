package com.xujie.miaosha.services;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.xujie.miaosha.dao.MiaoshaUserDao;
import com.xujie.miaosha.domain.MiaoshaUser;
import com.xujie.miaosha.exception.GlobalException;
import com.xujie.miaosha.redis.MiaoshaUserKey;
import com.xujie.miaosha.result.CodeMsg;
import com.xujie.miaosha.util.MD5Util;
import com.xujie.miaosha.util.UUIDUtil;
import com.xujie.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";


    @Resource
    private MiaoshaUserDao miaoshaUserDao;

    @Resource
    private RedisService redisService;

    public MiaoshaUser getById( Long id ) {
        return miaoshaUserDao.getById(id);
    }

    public boolean login( HttpServletResponse response, LoginVo loginVo ) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String fromPass = loginVo.getPassword();
        //判断手机号时候存在
        MiaoshaUser user = getById(Long.valueOf(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();

        String calcPass = MD5Util.fromPassToDBPass(fromPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        addCookie(response, user);

        return true;
    }

    private void addCookie( HttpServletResponse response, MiaoshaUser user ) {
        //生成cookie
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken( HttpServletResponse response, String token ) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if(user != null) {
            addCookie(response, user);
        }
        return user;
    }

}
