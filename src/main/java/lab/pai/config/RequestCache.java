package lab.pai.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;


public class RequestCache extends HttpSessionRequestCache{
	
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if(!SecurityUti.isFrameworkInternalRequest(request)){
            saveRequest(request, response);
        }
    }
}
