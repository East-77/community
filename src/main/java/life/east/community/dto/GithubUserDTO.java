package life.east.community.dto;

/**
 * @author 7777777
 * @date 2019/11/23 15:18:56
 * @description 接收GitHub返回的用户信息
 */
public class GithubUserDTO {

    private String name;
    private Long id;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
