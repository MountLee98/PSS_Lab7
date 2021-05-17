package lab.pai.controller;

import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import lab.pai.config.OnRegistrationCompleteEvent;
import lab.pai.model.User;
import lab.pai.model.VerificationToken;
import lab.pai.service.UserService;

@RequestMapping("/rest")
@RestController
public class VerificationController {

	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	UserService userService;

	@PostMapping("/user/registration")
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") @Valid User user, 
	  HttpServletRequest request, Errors errors) { 
	    
	    try {
	        User registered = userService.registerNewUserAccount(user);
	        
	        String appUrl = request.getContextPath();
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
	          request.getLocale(), appUrl));
	    } catch (UserAlreadyExistException uaeEx) {
	        ModelAndView mav = new ModelAndView("registration", "user", user);
	        mav.addObject("message", "An account for that username/email already exists.");
	        return mav;
	    } catch (RuntimeException ex) {
	        return new ModelAndView("emailError", "user", user);
	    }

	    return new ModelAndView("successRegister", "user", user);
	}
	
	@GetMapping("/regitrationConfirm")
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	 
	    Locale locale = request.getLocale();
	    
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	    }
	    
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = messages.getMessage("auth.message.expired", null, locale)
	        model.addAttribute("message", messageValue);
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	    } 
	    
	    user.setEnabled(true); 
	    service.saveRegisteredUser(user); 
	    return "redirect:/login.html?lang=" + request.getLocale().getLanguage(); 
	}
	
}
