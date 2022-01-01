package cn.nadow.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;


/**
 *
 *
 * 资源服务配置类
 */
//@Configuration
//@EnableResourceServer // 开启资源服务器功能
//@EnableWebSecurity // 开启web访问安全
public class ResourceServerConfiger extends ResourceServerConfigurerAdapter {


    private String sign_key = "test123"; // jwt签名密钥

    /**
     * 该⽅法⽤于定义资源服务器向远程认证服务器发起请求，进⾏token校验
     * 等事宜
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer
                                  resources) throws Exception {
        // 设置当前资源服务的资源id 与认证id保持一致  可以不设置
        resources.resourceId("cloud-oauth-server-9998");

        // 定义token服务对象（token校验就应该靠token服务对象）
        RemoteTokenServices remoteTokenServices = new
                RemoteTokenServices();
        // 校验端点/接⼝设置
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9999/oauth/check_token");
        // 携带客户端id和客户端安全码
        remoteTokenServices.setClientId("client_test");
        remoteTokenServices.setClientSecret("abcxyz");
        // 别忘了这⼀步
        resources.tokenServices(remoteTokenServices);
    }

    /**
     * 场景：⼀个服务中可能有很多资源（API接⼝）
     * 某⼀些API接⼝，需要先认证，才能访问
     * 某⼀些API接⼝，压根就不需要认证，本来就是对外开放的接⼝
     * 我们就需要对不同特点的接⼝区分对待（在当前configure⽅法中
     * 完成），设置是否需要经过认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws
            Exception {
        http // 设置session的创建策略（根据需要创建即可）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                 .antMatchers("/api/**").authenticated() // autodeliver为前缀的请求需要认证
                .antMatchers("/demo/**").authenticated() // demo为前缀的请求需要认证
                .anyRequest().permitAll(); // 其他请求不认证
    }
}
