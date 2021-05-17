package lab.pai.ui;

import java.time.ZoneId;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import lab.pai.model.AutoCapacity;
import lab.pai.model.Delegation;
import lab.pai.model.Transport;


public class DelegationForm extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4403403923097760270L;
	private TextField description = new TextField("Description");
    private TextField travelDietAmount = new TextField("Travel Diet");
    private TextField breakfastNumber = new TextField("Breakfast Number");
    private TextField dinnerNumber = new TextField("Dinner Number");
    private TextField supperNumber = new TextField("Breakfast Number");
    private TextField ticketPrice = new TextField("Ticket Price");
    private TextField km = new TextField("Km");
    private TextField accomodationPrice = new TextField("Accommodation Price");
    private TextField otherTicketsPrice = new TextField("Other Tickets Price");
    private TextField otherOutlayDesc = new TextField("Other Outlay Desc");
    private TextField otherOutlayPrice = new TextField("Other Outlay Price");
    private DatePicker dateTimeStart = new DatePicker("Date Start");
    private DatePicker dateTimeStop= new DatePicker("Date End");
    private ComboBox<Transport> transportType = new ComboBox<>("Transport");
    private ComboBox<AutoCapacity> autoCapacity = new ComboBox<>("Auto Capacity");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button modify = new Button("Modify");
    private Button close = new Button("Close");

    BeanValidationBinder<Delegation> binder = new BeanValidationBinder<>(Delegation.class);

    public DelegationForm(){
        binder.forField(description).bind(Delegation::getDescription, Delegation::setDescription);
        binder.forField(travelDietAmount).withConverter(new StringToIntegerConverter("Enter a number")).bind(Delegation::getTravelDietAmount, Delegation::setTravelDietAmount);
        binder.forField(breakfastNumber).withConverter(new StringToIntegerConverter("Enter a number")).bind(Delegation::getBreakfastNumber, Delegation::setBreakfastNumber);
        binder.forField(dinnerNumber).withConverter(new StringToIntegerConverter("Enter a number")).bind(Delegation::getDinnerNumber, Delegation::setDinnerNumber);
        binder.forField(supperNumber).withConverter(new StringToIntegerConverter("Enter a number")).bind(Delegation::getSupperNumber, Delegation::setSupperNumber);
        binder.forField(ticketPrice).withNullRepresentation("").withConverter(new StringToFloatConverter("Enter a number")).bind(Delegation::getTicketPrice,Delegation::setTicketPrice);
        binder.forField(km).withNullRepresentation("").withConverter(new StringToFloatConverter("Enter a number")).bind(Delegation::getKm, Delegation::setKm);
        binder.forField(accomodationPrice).withNullRepresentation("").withConverter(new StringToFloatConverter("Enter a number")).bind(Delegation::getAccomodationPrice, Delegation::setAccomodationPrice);
        binder.forField(otherTicketsPrice).withNullRepresentation("").withConverter(new StringToFloatConverter("Enter a number")).bind(Delegation::getOtherTicketsPrice, Delegation::setOtherTicketsPrice);
        binder.forField(otherOutlayDesc).bind(Delegation::getOtherOutlayDesc, Delegation::setOtherOutlayDesc);
        binder.forField(otherOutlayPrice).withNullRepresentation("").withConverter(new StringToFloatConverter("Enter a number")).bind(Delegation::getOtherOutlayPrice, Delegation::setOtherOutlayPrice);
        binder.forField(dateTimeStart).withConverter(new LocalDateToDateConverter(ZoneId.systemDefault())).bind("dateTimeStart");
        binder.forField(dateTimeStop).withConverter(new LocalDateToDateConverter(ZoneId.systemDefault())).bind("dateTimeStop");
        binder.forField(transportType).bind(Delegation::getTransportType, Delegation::setTransportType);
        binder.forField(autoCapacity).bind(Delegation::getAutoCapacityl, Delegation::setAutoCapacityl);
        binder.setBean(new Delegation());

        transportType.setItems(Transport.values());
        autoCapacity.setItems(AutoCapacity.values());

        addClassName("delegation-form");

        add(
                dateTimeStart,
                dateTimeStop,
                description,
                travelDietAmount,
                breakfastNumber,
                dinnerNumber,
                supperNumber,
                transportType,
                ticketPrice,
                autoCapacity,
                km,
                accomodationPrice,
                otherTicketsPrice,
                otherOutlayDesc,
                otherOutlayPrice,
                createButtonLayout()
        );
    }

    public void setDelegation(Delegation delegation){
        binder.setBean(delegation);
    }

    private Component createButtonLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(buttonClickEvent -> validateAndSave());
        delete.addClickListener(buttonClickEvent -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(buttonClickEvent -> fireEvent(new CloseEvent(this)));
        modify.addClickListener(buttonClickEvent -> fireEvent(new ModifyEvent(this, binder.getBean())));

        save.setEnabled(false);
        binder.addStatusChangeListener(status -> {
                    save.setEnabled(!status.hasValidationErrors() && !dateTimeStart.isEmpty() && !dateTimeStop.isEmpty());
                }
        );
        return new HorizontalLayout(save, modify, delete, close);
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class DelegationFormEvent extends ComponentEvent<DelegationForm> {
        /**
		 * 
		 */
		private static final long serialVersionUID = -5573722679585410451L;
		private Delegation delegation;

        protected DelegationFormEvent(DelegationForm source, Delegation delegation) {
            super(source, false);
            this.delegation = delegation;
        }

        public Delegation getDelegation() {
            return delegation;
        }
    }

    public static class SaveEvent extends DelegationFormEvent {
        /**
		 * 
		 */
		private static final long serialVersionUID = 3867718186812230932L;

		SaveEvent(DelegationForm source, Delegation delegation) {
            super(source, delegation);
        }
    }

    public static class ModifyEvent extends DelegationFormEvent {
        /**
		 * 
		 */
		private static final long serialVersionUID = 2695652025408110546L;

		ModifyEvent(DelegationForm source, Delegation delegation) {
            super(source, delegation);
        }
    }

    public static class DeleteEvent extends DelegationFormEvent {
        /**
		 * 
		 */
		private static final long serialVersionUID = -3556366182359705523L;

		DeleteEvent(DelegationForm source, Delegation delegation) {
            super(source, delegation);
        }
    }

    public static class CloseEvent extends DelegationFormEvent {
        /**
		 * 
		 */
		private static final long serialVersionUID = -6398439335934445123L;

		CloseEvent(DelegationForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
