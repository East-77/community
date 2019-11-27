package life.east.community.config;

import life.east.community.interceptor.MySessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 7777777
 * @date 2019/11/25 14:53:56
 * @description
 */
@Configuration
//@EnableWebMvc
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private MySessionInterceptor mySessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(mySessionInterceptor)
                .addPathPatterns("/**");
//                .excludePathPatterns("/","/index.html","/callback**");
    }
}
