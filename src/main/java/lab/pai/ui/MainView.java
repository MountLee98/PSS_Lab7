package lab.pai.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import lab.pai.model.User;
import lab.pai.service.UserService;

public class MainView extends AppLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821398948497968242L;
	@Autowired
    UserService userService;
    private User user;

    public MainView(UserService userService) {
        this.userService = userService;
        user = userService.findUserByEmail(currentUser());
        createHeader();
        createDrawer();
    }
    private void createHeader() {
        H1 logo = new H1("DIET APP");
        logo.addClassName("logo");

        Anchor logout =  new Anchor("/logout", "Log Out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.expand(logo);
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink userLink = new RouterLink("User", UserView.class);
        RouterLink loginLink = new RouterLink("Login", LoginView.class);
        RouterLink mainLink = new RouterLink("Main", MainPage.class);
        RouterLink delegationLink = new RouterLink("Delegations", DelegationView.class);
        loginLink.setHighlightCondition(HighlightConditions.sameLocation());
        userLink.setHighlightCondition(HighlightConditions.sameLocation());
        mainLink.setHighlightCondition(HighlightConditions.sameLocation());
        delegationLink.setHighlightCondition(HighlightConditions.sameLocation());
    }

    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }
	
}
