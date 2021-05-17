package lab.pai.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestCache {
	
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if(!SecurityUti.isFrameworkInternalRequest(request)){
            saveRequest(request, response);
        }
    }
}
