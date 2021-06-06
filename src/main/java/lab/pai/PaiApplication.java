package lab.pai;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@RestController
public class PaiApplication {

//	@GetMapping("/oauth2/authorization/github")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        Map<String, Object> userData = new HashMap<>();
//
//        userData.put("name", principal.getAttribute("name"));
//        userData.put("email", principal.getAttribute("email"));
//        userData.put("company", principal.getAttribute("company"));
//        userData.put("location", principal.getAttribute("location"));
//
//        return userData;
//    }
	
	public static void main(String[] args) {
		SpringApplication.run(PaiApplication.class, args);
	}

}
