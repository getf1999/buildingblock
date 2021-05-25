package com.getf.buildingblock.auth.config;

import com.getf.buildingblock.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {
    @Value("${token.jwt.secret}")
    private String jwttokenSecret;

    @Value("${clientInfos}")
    private String clientInfos;

    @Primary
    @Bean
    JwtAccessTokenConverter getJwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(jwttokenSecret);
        return jwtAccessTokenConverter;
    }


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        var clientInfoArr=clientInfos.split("\\|");
        var inMemory=clients.inMemory();
        for(var elem: clientInfoArr){
            var clientInfo=elem.split(":");
            var secret=new BCryptPasswordEncoder().encode(clientInfo[1]);

            var builder=inMemory.withClient(clientInfo[0]).secret(secret).authorizedGrantTypes("refresh_token", "authorization_code", "password");
            var accessTokenValiditySeconds=7200;
            if(clientInfo.length==3){
                accessTokenValiditySeconds=Integer.valueOf(clientInfo[2]);
            }
            builder.accessTokenValiditySeconds(accessTokenValiditySeconds).scopes("all");
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter).userDetailsService(userDetailsService).allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
    }

//    @Override
//    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        /**
//         * redis token 方式
//         */
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
//
//    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        var secret=new BCryptPasswordEncoder().encode("order-secret-8888");
//        clients.inMemory()
//                .withClient("order-client")
//                .secret(secret)
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all")
//                .and()
//                .withClient("user-client")
//                .secret(new BCryptPasswordEncoder().encode("user-secret-8888"))
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all");
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("permitAll()")
//                .allowFormAuthenticationForClients();
//    }
}
