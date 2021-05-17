package lab.pai.ui;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import lab.pai.model.Delegation;
import lab.pai.model.User;
import lab.pai.service.DelegationService;
import lab.pai.service.UserService;

public class AdminDelegationView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2358066105760386520L;
	@Autowired
    DelegationService delegationService;
    @Autowired
    UserService userService;

    private User user;
    private final DelegationForm form;
    private Grid<Delegation> grid = new Grid<Delegation>(Delegation.class);

    public AdminDelegationView(DelegationService delegationService, UserService userService){
        this.delegationService = delegationService;
        this.userService = userService;
        user = userService.findUserByEmail(currentUser());
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new DelegationForm();
        form.addListener(DelegationForm.SaveEvent.class, this::saveDelegation);
        form.addListener(DelegationForm.DeleteEvent.class, this::deleteDelegation);
        form.addListener(DelegationForm.ModifyEvent.class, this::modifyDelegation);
        form.addListener(DelegationForm.CloseEvent.class, e -> closeEditor());

        grid.setSizeFull();
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();


        add(getToolBar(),content);
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addDelegationButton = new Button("Add Delegation", click -> addDelegation());
        StreamResource res = new StreamResource("file.pdf", () -> new ByteArrayInputStream("stach".getBytes()));

        Anchor generatePDF = new Anchor(res,"");
        generatePDF.getElement().setAttribute("download", true);
        generatePDF.add(new Button("Download PDF" ,new Icon(VaadinIcon.DOWNLOAD_ALT)));
        HorizontalLayout toolbar = new HorizontalLayout(addDelegationButton, generatePDF);
        return toolbar;
    }

    /*private InputStream getInputStream(){
        try{
            StringWriter stringWriter = new StringWriter();
            CsvW
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }*/

    private void configureGrid(){
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.setColumns("description","dateTimeStart","dateTimeStop","travelDietAmount","breakfastNumber","dinnerNumber","supperNumber","transportType",
                "ticketPrice","autoCapacity","km","accomodationPrice","otherTicketsPrice","otherOutlayDesc","otherOutlayPrice");
        grid.getColumns().forEach(delegationColumn -> delegationColumn.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editDelegation(evt.getValue()));

    }

    private void addDelegation() {
        grid.asSingleSelect().clear();
        editDelegation(new Delegation());
    }

    private void deleteDelegation(DelegationForm.DeleteEvent del) {
    	LocalDateTime date = LocalDateTime.now();
        if (del.getDelegation().getDateTimeStart().compareTo(date) > 0){
            delegationService.removeDelegation(user.getUserId(), del.getDelegation().getDelegationId());
            updateList();
            closeEditor();
        }

    }

    private void saveDelegation(DelegationForm.SaveEvent del) {
        delegationService.addDelegation(user.getUserId(),del.getDelegation());
        updateList();
        closeEditor();
    }

    private void modifyDelegation(DelegationForm.ModifyEvent del){
        LocalDateTime date = LocalDateTime.now();
        if (del.getDelegation().getDateTimeStart().compareTo(date) > 0){
            delegationService.changeDelegation(del.getDelegation().getDelegationId(),del.getDelegation());
            updateList();
            closeEditor();
        }
    }

    private void editDelegation(Delegation delegation) {
        if(delegation == null){
            closeEditor();
        }else{
            form.setDelegation(delegation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setDelegation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList(){
        grid.setItems(delegationService.getAllDelegations());
    }

    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }
}
