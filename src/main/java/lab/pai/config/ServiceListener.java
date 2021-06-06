package lab.pai.config;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import lab.pai.ui.LoginView;
import lab.pai.ui.RegistrationView;

@Component
public class ServiceListener implements VaadinServiceInitListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1364027719949160901L;

	@Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
               && !RegistrationView.class.equals(event.getNavigationTarget())
                && !SecurityUti.isUserLoggedIn())  {
            event.rerouteTo(LoginView.class);
        }
    }
}
