package lab.pai.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lab.pai.model.User;
import lab.pai.service.UserService;

@Route(value="users", layout = MainView.class)
@PageTitle("Users ")
public class UserView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9164321912906560139L;
	private UserService userService;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField filterText = new TextField();
    private UserForm form;
    private User user;

    public UserView(UserService userService) {
        this.userService = userService;
        user = userService.findUserByEmail(currentUser());
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        form = new UserForm();
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
        form.addListener(UserForm.MakeAdminEvent.class, this::makeAdmin);
        form.addListener(UserForm.MakeUserEvent.class, this::makeUser);
        closeEditor();
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();
        add(getToolbar(), content);
        updateList();

    }

    private void saveUser(UserForm.SaveEvent event) {
        userService.registerUser(event.getUser());
        updateList();
        closeEditor();
    }
    private void deleteUser(UserForm.DeleteEvent event) {
        userService.deleteUserById(event.getUser().getUserId());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setColumns("userId", "name", "lastName", "email","companyName","companyAddress", "status", "registrationDate");
        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList() {
        grid.setItems(userService.getAllUsers());
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addUser());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void makeAdmin(UserForm.MakeAdminEvent user){
        userService.makeAdmin(user.getUser().getUserId());
        updateList();
        closeEditor();
    }

    private void makeUser(UserForm.MakeUserEvent user){
        userService.makeUser(user.getUser().getUserId());
        updateList();
        closeEditor();
    }

    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentName = authentication.getName();
        return currentName;
    }
}
