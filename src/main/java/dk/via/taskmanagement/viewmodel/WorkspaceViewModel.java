package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.*;
import dk.via.taskmanagement.model.builders.TaskBuilder;
import dk.via.taskmanagement.utilities.Auth;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class WorkspaceViewModel implements PropertyChangeListener {
    private final Model model;

    StringProperty workspaceName;
    ArrayList<Task> tasks;

    StringProperty taskName;
    StringProperty taskDescription;
    StringProperty taskPriority;
    StringProperty taskState;
    ObjectProperty<LocalDate> taskDeadline;

    ListProperty<Task> notStartedTasks;
    ListProperty<Task> inProgressTasks;
    ListProperty<Task> completedTasks;

    ObjectProperty<Task> selectedTask;

    Task selectedTaskObject = null;

    ListProperty<Task.Priority> checkedFilterPriorities;

    StringProperty nameFilter;



    public WorkspaceViewModel(Model model) {
        this.model = model;
        workspaceName = new SimpleStringProperty();
        taskName = new SimpleStringProperty();
        taskDescription = new SimpleStringProperty();
        taskPriority = new SimpleStringProperty();
        taskState = new SimpleStringProperty();
        taskDeadline = new SimpleObjectProperty<>();
        notStartedTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        inProgressTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        completedTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        checkedFilterPriorities = new SimpleListProperty<>(FXCollections.observableArrayList(Task.Priority.values()));
        selectedTask = new SimpleObjectProperty<>();
        nameFilter = new SimpleStringProperty();

        model.addPropertyChangeListener(this);

        selectedTask.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                taskName.setValue(newValue.getName());
                taskDescription.setValue(newValue.getDescription());
                taskPriority.setValue(newValue.getPriority().toString());
                taskState.setValue(newValue.getState().toString());
                taskDeadline.setValue(newValue.getDeadline());

                selectedTaskObject = newValue;
            }
        });

        checkedFilterPriorities.addListener((observable, oldValue, newValue) -> {
            filterTasks();
        });

        nameFilter.addListener((observable, oldValue, newValue) -> {
            filterTasks();
        });
    }

    public void getTasksForWorkspace() {
        tasks = model.getTasksForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());

        notStartedTasks.clear();
        inProgressTasks.clear();
        completedTasks.clear();

        tasks.stream()
                .sorted(Comparator.comparing((Task task) -> task.getPriority().ordinal()).reversed())
                .forEach(task -> {
                    switch (task.getState().toString()) {
                        case "NotStarted":
                            notStartedTasks.add(task);
                            break;
                        case "InProgress":
                            inProgressTasks.add(task);
                            break;
                        case "Completed":
                            completedTasks.add(task);
                            break;
                    }
                });
    }

    public void bindWorkspaceName(StringProperty property) {
        property.bindBidirectional(workspaceName);
    }

    public void bindTaskName(StringProperty property) {
        property.bindBidirectional(taskName);
    }

    public void bindTaskDescription(StringProperty property) {
        property.bindBidirectional(taskDescription);
    }

    public void bindTaskPriority(StringProperty property) {
        property.bindBidirectional(taskPriority);
    }

    public void bindTaskState(StringProperty property) {
        property.bindBidirectional(taskState);
    }

    public void bindTaskDeadline(ObjectProperty<LocalDate> property) {
        property.bindBidirectional(taskDeadline);
    }

    public void bindNotStartedTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(notStartedTasks);
    }

    public void bindInProgressTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(inProgressTasks);
    }

    public void bindCompletedTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(completedTasks);
    }

    public void bindSelectedTask(ObjectProperty<Task> property) {
        property.bindBidirectional(selectedTask);
    }

    public void bindCheckedFilterPriorities(ListProperty<Task.Priority> property) {
        property.bindBidirectional(checkedFilterPriorities);
    }

    public void bindNameFilter(StringProperty property) {
        property.bindBidirectional(nameFilter);
    }

    public void createTask() {
        Task task = getTask();

        model.createTask(task);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("updateTasks")) {
            getTasksForWorkspace();
        }
    }

    public void updateTask() {
        model.updateTask(getTask());
    }

    private Task getTask() {
        TaskBuilder taskBuilder = new TaskBuilder();

        TaskState state = switch (taskState.getValue()) {
            case "NotStarted" -> new NotStarted();
            case "InProgress" -> new InProgress();
            case "Completed" -> new Completed();
            default -> null;
        };

        Workspace workspace = Auth.getInstance().getCurrentUser().getWorkspace();

        taskBuilder
                .setId(selectedTask.getValue().getId())
                .setName(taskName.getValue())
                .setDescription(taskDescription.getValue())
                .setPriority(Task.Priority.valueOf(taskPriority.getValue()))
                .setState(state)
                .setDeadline(taskDeadline.getValue())
                .setWorkspace(workspace);

        return taskBuilder.build();
    }

    public void deleteTask() {
        model.deleteTask(getTask());
    }

    public void startTask() {
        model.startTask(getTask());
    }

    public void completeTask() {
        model.completeTask(getTask());
    }

    public void filterTasks() {
        if(tasks == null) return;

        notStartedTasks.clear();
        tasks.stream()
                .filter(task -> task.getState().toString().equals("NotStarted"))
                .filter(task -> checkedFilterPriorities.getValue().contains(task.getPriority()))
                .forEach(notStartedTasks::add);

        inProgressTasks.clear();
        tasks.stream()
                .filter(task -> task.getState().toString().equals("InProgress"))
                .filter(task -> checkedFilterPriorities.getValue().contains(task.getPriority()))
                .forEach(inProgressTasks::add);

        completedTasks.clear();
        tasks.stream()
                .filter(task -> task.getState().toString().equals("Completed"))
                .filter(task -> checkedFilterPriorities.getValue().contains(task.getPriority()))
                .forEach(completedTasks::add);

        if(nameFilter.getValue() != null && !nameFilter.getValue().isEmpty()) {
            notStartedTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
            inProgressTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
            completedTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
        }
    }
}
