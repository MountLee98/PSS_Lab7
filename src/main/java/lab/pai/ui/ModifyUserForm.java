package lab.pai.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;

import lab.pai.model.User;
import lab.pai.service.UserService;
import lab.pai.ui.PasswordForm.ModifyEvent;
import lab.pai.ui.PasswordForm.PasswordFormEvent;

public class ModifyUserForm extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 522357713383919985L;

	@Autowired
    UserService userService;

	TextField name = new TextField("Name");
    TextField lastName = new TextField("Last Name");
    EmailField email = new EmailField("Email");
    TextField companyName = new TextField("Company Name");
    TextField companyAddress = new TextField("Company Address");
    TextField companyNip = new TextField("Company NIP");

    private Button modify = new Button("Modify");
    private Button close = new Button("Close");
    BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

    public ModifyUserForm(){
    	binder.forField(name).bind("name");
        binder.forField(lastName).bind("lastName");
        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .asRequired("email is required").bind(User::getEmail, User::setEmail);
        binder.forField(companyName).bind("companyName");
        binder.forField(companyAddress).bind("companyAddress");
        binder.forField(companyNip).bind("companyNip");
        binder.setBean(new User());

        add(name, lastName, email, companyName, companyAddress, companyNip, createButtonLayout());
        modify.setEnabled(false);
        binder.addStatusChangeListener(statusChangeEvent -> {
            modify.setEnabled(!statusChangeEvent.hasValidationErrors() && !binder.getFields().anyMatch(HasValue::isEmpty));
        });
    }

    public void setUser(User user){
        binder.setBean(user);
    }

    private Component createButtonLayout(){
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        modify.addClickListener(buttonClickEvent -> fireEvent(new ModifyEvent(this, binder.getBean())));
        close.addClickListener(buttonClickEvent -> fireEvent(new ModifyUserForm.CloseEvent(this)));
        return new HorizontalLayout(modify, close);
    }

	public static abstract class ModifyUserFormEvent extends ComponentEvent<ModifyUserForm> {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1220072019384487247L;
		private User user;

        protected ModifyUserFormEvent(ModifyUserForm source, User user){
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class ModifyEvent extends ModifyUserFormEvent{
        /**
		 * 
		 */
		private static final long serialVersionUID = 4876164008359899242L;

		ModifyEvent(ModifyUserForm source, User user){
            super(source, user);
        }
    }

    public static class CloseEvent extends ModifyUserFormEvent{
        /**
		 * 
		 */
		private static final long serialVersionUID = -7983753150758826962L;

		CloseEvent(ModifyUserForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }
}
