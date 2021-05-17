package lab.pai.config;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import lab.pai.ui.LoginView;

public class ServiceListener implements VaadinServiceInitListener{
	
	@Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
               // && !RegistrationView.class.equals(event.getNavigationTarget())
                && !SecurityUti.isUserLoggedIn())  {
            event.rerouteTo(LoginView.class);
        }
    }
}
