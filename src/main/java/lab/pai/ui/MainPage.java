package lab.pai.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lab.pai.model.User;
import lab.pai.service.UserService;

@Route(value="", layout = MainView.class)
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
    private final ModifyUserForm userForm;

    Label name = new Label();
    Label idUser = new Label();
    Label userLastName = new Label();
    Label userEmail = new Label();
    Label companyName = new Label();
    Label companyAddress = new Label();
    Label companyNip = new Label();

    Button changePassword = new Button("Change password");
    Button modifyUser = new Button("Modify user");

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
        
        userForm = new ModifyUserForm();
        userForm.setVisible(false);
        userForm.addListener(ModifyUserForm.ModifyEvent.class, this::modifyUser);
        userForm.addListener(ModifyUserForm.CloseEvent.class, e -> closeUserEditor());
        modifyUser.addClickListener(actionEvent -> userForm.setVisible(true));
        
        Div content = new Div(new VerticalLayout(idUser, name, userLastName, userEmail, companyName, companyAddress, companyNip, changePassword, modifyUser), form, userForm);
        add(content);
    }

    private void initialazeFields(){    	
        name.setText("Imię: " + user.getName());
        idUser.setText("Id: " + user.getUserId());
        userLastName.setText("Nazwisko: " + user.getLastName());
        userEmail.setText("Email: " + user.getEmail());
        companyName.setText("Nazwa firmy: " + user.getCompanyName());
        companyAddress.setText("Adres firmy: " + user.getCompanyAddress());
        companyNip.setText("Nip: " + user.getCompanyNip());
    }

    private void modifyPassword(PasswordForm.ModifyEvent usr){
        userService.changePassword(user.getUserId(), usr.getUser().getPassword());
    }
    
    private void modifyUser(ModifyUserForm.ModifyEvent usr){
        userService.changeUser(usr.getUser().getUserId(), usr.getUser());
    }

    public void closeEditor(){
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    
    public void closeUserEditor(){
        userForm.setUser(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }

    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }
}
