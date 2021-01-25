package com.yy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.bean.User;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/25 9:48
 */
public interface UserService extends IService<User> {

    public User getUserById(Long id);
}
