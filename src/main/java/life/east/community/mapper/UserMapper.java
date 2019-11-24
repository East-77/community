package life.east.community.mapper;

import life.east.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 7777777
 * @date 2019/11/23 21:49:23
 * @description
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);

    /**
     * 参数不是对象时加@Param注解
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
