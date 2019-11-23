package life.east.community.Provider;

import com.alibaba.fastjson.JSON;
import life.east.community.dto.AccessTokenDTO;
import life.east.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 7777777
 * @date 2019/11/23 14:41:18
 * @description 使用户登录GitHub并返回GitHub上的此用户信息
 *              参考GitHub API:https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
 */
@Component
public class GithubProvider {

    /**
     * 通过使用OkHttp向GitHub发送POST请求，取得GitHub返回的access_token
     *      OkHttp参考：https://square.github.io/okhttp/
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //返回为：access_token=2c1440e2fa93bf695f6924a9eae14c1f5885f5a2&scope=user&token_type=bearer
            String string = response.body().string();
            //取出access_token的值
            String tokenStr = string.split("&")[0].split("=")[1];
            return tokenStr;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 携带GitHub返回的access_token再次向GitHub发送GET请求，真正获取用户资料
     * @param accessToken
     * @return
     */
    public GithubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
        }

        return null;
    }
}
