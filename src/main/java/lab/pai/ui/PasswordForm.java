package lab.pai.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;

import lab.pai.model.User;

public class PasswordForm extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2927888073483650531L;

	private PasswordField password = new PasswordField("New Password");

    private Button modify = new Button("Modify");
    private Button close = new Button("Close");
    BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

    public PasswordForm(){
        binder.forField(password).asRequired("New password is required").bind("password");
        binder.setBean(new User());

        addClassName("password-Form");
        add(password, createButtonLayout());
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
        close.addClickListener(buttonClickEvent -> fireEvent(new PasswordForm.CloseEvent(this)));
        return new HorizontalLayout(modify, close);
    }

    public static abstract class PasswordFormEvent extends ComponentEvent<PasswordForm> {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1220072019384487247L;
		private User user;

        protected PasswordFormEvent(PasswordForm source, User user){
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class ModifyEvent extends PasswordFormEvent{
        /**
		 * 
		 */
		private static final long serialVersionUID = 4876164008359899242L;

		ModifyEvent(PasswordForm source, User user){
            super(source, user);
        }
    }

    public static class CloseEvent extends PasswordFormEvent{
        /**
		 * 
		 */
		private static final long serialVersionUID = -7983753150758826962L;

		CloseEvent(PasswordForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }
}
