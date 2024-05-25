package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.viewmodel.WorkspaceViewModel;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.controlsfx.control.SegmentedButton;

public class WorkspaceView {
    private ViewHandler viewHandler;
    private WorkspaceViewModel viewModel;

    @FXML
    Label workspaceName;

    @FXML
    Button manageWorkspaceButton;

    @FXML
    Region manageWorkspaceRegion;

    @FXML
    TextField name;

    @FXML
    TextArea description;

    @FXML
    SegmentedButton priority;

    @FXML
    SegmentedButton state;

    @FXML
    DatePicker deadline;

    StringProperty taskState;
    StringProperty taskPriority;

    @FXML
    ListView<Task> notStartedTasks;

    @FXML
    ListView<Task> inProgressTasks;

    @FXML
    ListView<Task> completedTasks;

    ObjectProperty<Task> selectedTask;

    BooleanProperty isNewTask;

    @FXML
    Button newButton;

    @FXML
    Button deleteButton;

    @FXML
    Button startButton;

    @FXML
    Button completeButton;

    public void init(ViewHandler viewHandler, WorkspaceViewModel workspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        taskState = new SimpleStringProperty();
        taskPriority = new SimpleStringProperty();
        viewModel = workspaceViewModel;
        selectedTask = new SimpleObjectProperty<>();
        isNewTask = new SimpleBooleanProperty();

        viewModel.bindWorkspaceName(workspaceName.textProperty());
        viewModel.bindTaskName(name.textProperty());
        viewModel.bindTaskDescription(description.textProperty());
        viewModel.bindTaskPriority(taskPriority);
        viewModel.bindTaskState(taskState);
        viewModel.bindTaskDeadline(deadline.valueProperty());
        viewModel.bindNotStartedTasks(notStartedTasks.itemsProperty());
        viewModel.bindInProgressTasks(inProgressTasks.itemsProperty());
        viewModel.bindCompletedTasks(completedTasks.itemsProperty());
        viewModel.bindSelectedTask(selectedTask);

        // TODO toto do viewmodelu
        workspaceName.setText(Auth.getInstance().getCurrentUser().getWorkspace().getName());

        // if user is admin then show manage workspace button and region otherwise hide
        boolean isAdmin = Auth.getInstance().getCurrentUser().getRole().equals("admin");

        manageWorkspaceButton.setVisible(isAdmin);
        manageWorkspaceRegion.setVisible(isAdmin);

        initPriority();
        initState();
        initSelectedTask();
        initIsNewTask();

        workspaceViewModel.getTasksForWorkspace();
    }

    private void initIsNewTask() {
        isNewTask.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.clear();
                description.clear();
                deadline.setValue(null);
                taskPriority.set(Task.Priority.LOW.toString());
                taskState.set("NotStarted");
                newButton.setVisible(false);
                deleteButton.setVisible(false);
                startButton.setVisible(false);
                completeButton.setVisible(false);

                // deselect all tasks
                notStartedTasks.getSelectionModel().clearSelection();
                inProgressTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
            } else {
                newButton.setVisible(true);
                deleteButton.setVisible(true);
                startButton.setVisible(true);
                completeButton.setVisible(true);
            }
        });

        isNewTask.set(true);
    }

    private void initSelectedTask() {
        notStartedTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                inProgressTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });

        inProgressTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                notStartedTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });

        completedTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                notStartedTasks.getSelectionModel().clearSelection();
                inProgressTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });
    }

    private void initState() {
        ToggleGroup taskStateToggleGroup = new ToggleGroup();

        ToggleButton notStartedButton = new ToggleButton("Not Started");
        notStartedButton.setToggleGroup(taskStateToggleGroup);
        notStartedButton.setUserData("NotStarted");
        notStartedButton.setDisable(true);

        ToggleButton inProgressButton = new ToggleButton("In Progress");
        inProgressButton.setToggleGroup(taskStateToggleGroup);
        inProgressButton.setUserData("InProgress");
        inProgressButton.setDisable(true);

        ToggleButton completedButton = new ToggleButton("Completed");
        completedButton.setToggleGroup(taskStateToggleGroup);
        completedButton.setUserData("Completed");
        completedButton.setDisable(true);

        state.getButtons().addAll(notStartedButton, inProgressButton, completedButton);
        state.setToggleGroup(taskStateToggleGroup);

        state.getToggleGroup().selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                taskState.set(newValue.getUserData().toString());
            }
        });

        taskState.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                for (ToggleButton button : state.getButtons()) {
                    if (button.getUserData().toString().equals(newValue)) {
                        button.setSelected(true);
                    }
                }
            }
        });

    }

    private void initPriority() {
        ToggleGroup priorityToggleGroup = new ToggleGroup();
        for (Task.Priority taskPriorityEnum : Task.Priority.values()) {
            ToggleButton radioButton = new ToggleButton(taskPriorityEnum.toString());
            radioButton.setToggleGroup(priorityToggleGroup);
            radioButton.setUserData(taskPriorityEnum);
            priority.getButtons().add(radioButton);
        }

        priority.setToggleGroup(priorityToggleGroup);


        priority.getToggleGroup().selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                taskPriority.set(newToggle.getUserData().toString());
            }
        });

        priorityToggleGroup.selectToggle(priority.getButtons().get(0));

        taskPriority.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                for (ToggleButton button : priority.getButtons()) {
                    if (button.getUserData().toString().equals(newValue)) {
                        button.setSelected(true);
                    }
                }
            }
        });
    }

    @FXML
    public void openManageWorkspaceView() {
        viewHandler.openView(ViewFactory.MANAGE_WORKSPACE);
    }

    @FXML
    public void onSubmit() {
        if (isNewTask.get()) {
            viewModel.createTask();
        } else {
            viewModel.updateTask();
        }
    }

    @FXML
    public void createTask() {
        isNewTask.set(true);
    }

    @FXML
    public void deleteTask() {
        viewModel.deleteTask();
    }

    @FXML
    public void startTask() {
        viewModel.startTask();
    }

    @FXML
    public void completeTask() {
        viewModel.completeTask();
    }
}
