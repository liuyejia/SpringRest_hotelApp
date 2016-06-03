package rest.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfSecurityRequestMatcher implements RequestMatcher {

	private Pattern allowedMethods = Pattern
			.compile("^(GET|HEAD|TRACE|OPTIONS)$");
//	private RegexRequestMatcher unprotectedMatcher = new RegexRequestMatcher(
//			"/unprotected", null);
	private RegexRequestMatcher apiMatcher = new RegexRequestMatcher(
			"/admin/(edit|add)-roomtype/",null);

	@Override
	public boolean matches(HttpServletRequest request) {
		// false代表不是csrf
		if (allowedMethods.matcher(request.getMethod()).matches()) {
			return false;
		}

		// No CSRF due to api call
		if (apiMatcher.matches(request))
			return false;
		
		return true;
	}

}
