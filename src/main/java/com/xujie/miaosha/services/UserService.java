package com.xujie.miaosha.services;

import com.xujie.miaosha.dao.UserDao;
import com.xujie.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public User getById( int id ) {
        return userDao.getById(id);
    }

    
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("2222");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("1111");
        userDao.insert(u2);

        return true;
    }
}
