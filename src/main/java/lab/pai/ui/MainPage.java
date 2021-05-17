package lab.pai.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lab.pai.model.User;
import lab.pai.service.UserService;

@Route(value="main")
@PageTitle("Main Page")
public class MainPage extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3625350152999237233L;

	@Autowired
    private UserService userService;

    private User user;
    private final PasswordForm form;

    Label name = new Label();
    Label idUser = new Label();
    Label userLastName = new Label();
    Label userEmail = new Label();

    Button changePassword = new Button("Change password");

    public MainPage(UserService userService) {
        this.userService = userService;
        addClassName("userDataView");
        setSizeFull();

        user = userService.findUserByEmail(currentUser());
        initialazeFields();

        form = new PasswordForm();
        form.setVisible(false);
        form.addListener(PasswordForm.ModifyEvent.class, this::modifyPassword);
        form.addListener(PasswordForm.CloseEvent.class, e -> closeEditor());
        changePassword.addClickListener(actionEvent -> form.setVisible(true));
        Div content = new Div(new VerticalLayout(idUser,name,userLastName,userEmail, changePassword), form);
        add(content);
    }

    private void initialazeFields(){
        name.setText("Imię: " + user.getName());
        idUser.setText("Id: " + user.getUserId());
        userLastName.setText("Nazwisko: " + user.getLastName());
        userEmail.setText("Email: " + user.getEmail());
    }

    private void modifyPassword(PasswordForm.ModifyEvent usr){
        userService.changePassword(user.getUserId(), usr.getUser().getPassword());
    }

    public void closeEditor(){
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }
}
