package lab.pai.ui;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lab.pai.model.User;
import lab.pai.service.UserService;

@Route(value="register", layout = MainView.class)
@PageTitle("Registration")
public class RegistrationView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1058341235568740785L;
	@Autowired
    UserService userService;

    public RegistrationView(){
        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new H1("Registration"),buildForm());
    }

    private Component buildForm(){
        TextField name = new TextField("Name");
        TextField lastName = new TextField("Last Name");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        TextField companyName = new TextField("Company Name");
        TextField companyAddress = new TextField("Company Address");
        TextField companyNip = new TextField("Company NIP");
        Label label = new Label("This email is already taken");
        label.setVisible(false);

        Button registerButton = new Button("Register");
        Button clearForm = new Button("Clear");
        HorizontalLayout buttons = new HorizontalLayout(registerButton, clearForm);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Div errorLayout = new Div();

        VerticalLayout formLayout = new VerticalLayout(name,lastName,email,password,companyName,companyAddress,companyNip,label,buttons);
        Div wrapperLayout = new Div(formLayout, errorLayout);
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        errorLayout.setWidthFull();
        wrapperLayout.setHeight("80%");

        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.forField(name).bind("name");
        binder.forField(lastName).bind("lastName");
        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .asRequired("email is required").bind(User::getEmail, User::setEmail);
        binder.forField(password).bind("password");
        binder.forField(companyName).bind("companyName");
        binder.forField(companyAddress).bind("companyAddress");
        binder.forField(companyNip).bind("companyNip");
        binder.setBean(new User());

        registerButton.setEnabled(false);
        binder.addStatusChangeListener(status -> {
                    registerButton.setEnabled(!status.hasValidationErrors() && !binder.getFields().anyMatch(HasValue::isEmpty));
                    label.setVisible(false);
                }
        );

        registerButton.addClickListener(click -> {
            try {
                errorLayout.setText("");
                User user = new User();
                binder.writeBean(user);
                if(userService.findUserByEmail(user.getEmail()) == null){
                    userService.registerUser(user);
                    UI.getCurrent().navigate("login");
                }else{
                    label.setVisible(true);
                }

            } catch (ValidationException e) {
                errorLayout.add(new Html(e.getValidationErrors().stream()
                        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
                        .collect(Collectors.joining("\n"))));
            }
        });

        clearForm.addClickListener(click -> {
            name.setValue("");
            lastName.setValue("");
            email.setValue("");
            password.setValue("");
            companyName.setValue("");
            companyAddress.setValue("");
            companyNip.setValue("");
            binder.removeBean();
        });

        return wrapperLayout;
    }
	
}
