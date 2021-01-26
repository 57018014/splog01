package com.yy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.bean.User;
import com.yy.mapper.UserMapper;
import com.yy.operatorlog.PBUserOperatorLog;
import com.yy.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/25 9:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }
}
