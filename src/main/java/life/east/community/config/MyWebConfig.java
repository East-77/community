package life.east.community.config;

import life.east.community.interceptor.MySessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/callback**");


    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("我是MyWebConfig跨域");
//        //设置允许跨域的路径
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
//                //跨域允许时间
//                .maxAge(3600);
//    }
}
