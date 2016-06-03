package rest.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	//定义用户登录成功后的一些跳转操作
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			org.springframework.security.core.Authentication authentication)
			throws IOException, ServletException {
		// do some logic here if you want something to be done whenever
		// the user successfully logs in.

		HttpSession session = httpServletRequest.getSession();
		User authUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		session.setAttribute("username", authUser.getUsername());
		session.setAttribute("authorities", authentication.getAuthorities());

		// set our response to OK status
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		// since we have created our custom success handler, its up to us to
		// where
		// we will redirect the user after successfully login
		// httpServletResponse.sendRedirect("home");
		SavedRequest savedRequest = (SavedRequest) session
				.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
	
		//String requestUrl = savedRequest.getRedirectUrl();
		//如果用户直接访问登录页，则SavedRequest为null
		if (savedRequest==null) {
			Set<String> roles = AuthorityUtils
					.authorityListToSet(authentication.getAuthorities());
			if (roles.contains("ROLE_ADMIN")) {
				httpServletResponse.sendRedirect("/admin/");
				return;
			}
			httpServletResponse.sendRedirect("/staff/");
		} else {
			//如果用户从其他页面跳到登录页的，则SavedRequest会保存用户的上一次访问url
			httpServletResponse.sendRedirect(savedRequest.getRedirectUrl());
		}
	}
}
