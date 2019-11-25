package life.east.community.service;

import life.east.community.mapper.UserMapper;
import life.east.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 7777777
 * @date 2019/11/25 18:56:59
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void insertOrUpdate(User user) {
       User dbUser =  userMapper.findByAccountId(user.getAccountId());
       if(dbUser == null){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insertUser(user);
       }else{
           //更新
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.updateUser(dbUser);
       }
    }
}
