//package com.example.demo.oauth2;
//
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import jakarta.annotation.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.sql.DataSource;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.UUID;
//
//@Configuration
//public class SecurityConfig {
//    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";
//
//
//    @Resource
//    private DataSource dataSource;
//    @Bean
//    UserDetailsManager userDetailsManager() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        // 定义授权服务配置器
//        OAuth2AuthorizationServerConfigurer configurer = new OAuth2AuthorizationServerConfigurer();
//        configurer
//                // 自定义授权页面
//                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
//                // Enable OpenID Connect 1.0, 启用 OIDC 1.0
//                .oidc(Customizer.withDefaults());
//
//        // 获取授权服务器相关的请求端点
//        RequestMatcher endpointsMatcher = configurer.getEndpointsMatcher();
//
//        http
//                // 拦截对授权服务器相关端点的请求
//                .securityMatcher(endpointsMatcher)
//                // 拦载到的请求需要认证
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                // 忽略掉相关端点的 CSRF(跨站请求): 对授权端点的访问可以是跨站的
//                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//                // 访问端点时表单登录
//                .formLogin()
//                .and()
//                // 应用授权服务器的配置
//                .apply(configurer);
//
//        return http.build();
//    }
//
//
//
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize
//                        // 配置放行的请求
//                        .requestMatchers("/api/**", "/login").permitAll()
//                        // 其他任何请求都需要认证
//                        .anyRequest().authenticated()
//                )
//                // 设置登录表单页面
//                .formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login"));
//
//        return http.build();
//    }
//
//
//
//////   http://localhost:8089/oauth2/authorize?response_type=code&client_id=userinfo&&scope=userinfo.read&redirect_uri=http://localhost:8089/message
////    @Bean
////    public RegisteredClientRepository registeredClientRepository() {
////        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("userinfo")
////                .clientSecret("{noop}123456")
////                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                //.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
////                .redirectUri("http://localhost:8089/message")
////                .scope(OidcScopes.OPENID)
////                .scope(OidcScopes.PROFILE)
////                .scope("userinfo.read")
////                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////                .build();
////
////        return new InMemoryRegisteredClientRepository(registeredClient);
////    }
//
//
//    /**
//     * 注册客户端应用, 对应 oauth2_registered_client 表
//     */
//    @Bean
//    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//        return new JdbcRegisteredClientRepository(jdbcTemplate);
//    }
//
//    /**
//     * 令牌的发放记录, 对应 oauth2_authorization 表
//     */
//    @Bean
//    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//    }
//
//    /**
//     * 把资源拥有者授权确认操作保存到数据库, 对应 oauth2_authorization_consent 表
//     */
//    @Bean
//    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        RSAKey rsaKey = new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet(jwkSet);
//    }
//
//    private static KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        } catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }
//
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//        return AuthorizationServerSettings.builder().build();
//    }
//}
