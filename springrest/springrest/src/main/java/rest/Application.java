package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import rest.config.CsrfSecurityRequestMatcher;
import rest.config.CsrfTokenGeneratorFilter;
import rest.config.CustomAuthenticationSuccessHandler;
import rest.domain.Manager;
import rest.repository.ManagerRepository;

@SpringBootApplication
public class Application {
	
	public static final String PICTURE_PATH="H:/hotel/upload/"; 
	public static final String PREFIX = "file:";
	public static final String MESSAGE_PREFIX = "/topic";
			
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		//配置使用中文的错误信息
		messageSource.setBasename("messages_zh_CN");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
//配置websocket
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
    	//启动一个内存消息代理者，代理者在topic和user这两个域上可以向客户端发消息,声明后台传递信息的通道
    	config.enableSimpleBroker(Application.MESSAGE_PREFIX);
        //config.enableSimpleBroker("/topic","/user");
        //表示客户端向服务端发送时的信息上面需要加"/app"作为前缀
        config.setApplicationDestinationPrefixes("/app");
        //表示给指定用户发送（一对一）的主题前缀是“/user/”
        //config.setUserDestinationPrefix("/user");
    }
 
    //注册"/hello"终端, 启用SockJS回调选项，当websocket不可用是可以发送替代的信息
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	//客户端创建连接时使用的url，客户端传递给后台信息的url为prefix+/hello
        registry.addEndpoint("/order").withSockJS();
    }
 
}
@Configuration
class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	//额外配置文件上传的位置
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/picture/**").addResourceLocations(Application.PREFIX+Application.PICTURE_PATH);
        super.addResourceHandlers(registry);
	}
}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	ManagerRepository managerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
		    .passwordEncoder(Manager.PASSWORD_ENCODER);
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username)
					throws UsernameNotFoundException {
				//用户登录时查询用户信息
				Manager account = managerRepository.findByAccount(username);
				if (account != null) {
					return new User(account.getAccount(),
							account.getPassword(), true, true, true, true,
							AuthorityUtils.createAuthorityList(account
									.getEnRole()));
				} else {
					throw new UsernameNotFoundException(
							"could not find the user '" + username + "'");
				}
			}

		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//使用自定义的验证成功后处理器
	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
		//配置生成csrf token
		//.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)
//		.csrf()
//		    .requireCsrfProtectionMatcher(new CsrfSecurityRequestMatcher())
//            .and()
		.headers()
		     .frameOptions().sameOrigin()//解决ajax异步上传时的跨域问题refused to display in a frame because it set 'x-frame-options' to 'deny' 
		     .and()
		.authorizeRequests()
		   //配置静态资源不需要权限
			.antMatchers("/back/**","/css/**","/fonts/**","/images/**","/picture/**","/js/**").permitAll()
		   //配置不同的url需要的权限
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/staff/**").access("hasRole('ROLE_STAFF')")
            .antMatchers("/hotel/**","/api/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/backlogin/")
			.usernameParameter("account").passwordParameter("password")
			//.defaultSuccessUrl("/back")
            .failureUrl("/backlogin/?error=true")
            .successHandler(authenticationSuccessHandler)
			.permitAll()
			.and()
		.httpBasic()
			.and()
		.csrf().disable()
		.logout()
			.logoutSuccessUrl("/backlogin/");
	}

}