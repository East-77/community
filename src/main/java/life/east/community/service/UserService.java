package life.east.community.service;

import life.east.community.mapper.UserMapper;
import life.east.community.model.User;
import life.east.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        //User dbUser =  userMapper.findByAccountId(user.getAccountId());
        if (dbUsers.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUsers.get(0).getId());

            //更新传入对象的非空字段
            userMapper.updateByExampleSelective(updateUser,example);
//            userMapper.updateUser(dbUser);
        }
    }
}
