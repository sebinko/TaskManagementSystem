package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.NotStarted;
import dk.via.taskmanagement.model.InProgress;
import dk.via.taskmanagement.model.Completed;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.TaskState;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.builders.TaskBuilder;

import java.sql.*;
import java.util.ArrayList;

public class TaskDaoImplementation implements TaskDao{
    private static TaskDaoImplementation instance;

    private TaskDaoImplementation() {
    }

    public static TaskDaoImplementation getInstance() {
        if (instance == null) {
            instance = new TaskDaoImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=task_management_system", "postgres", "");
    }

    @Override
    public Task createTask(Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement insertTask = connection.prepareStatement("INSERT INTO tasks (name, description, deadline, workspace_id) VALUES (?, ?, ?, ?) returning task_id");
            insertTask.setString(1, task.getName());
            insertTask.setString(2, task.getDescription());
            insertTask.setDate(3, Date.valueOf(task.getDeadline()));
            insertTask.setInt(4, task.getWorkspace().getId());

            ResultSet resultSet = insertTask.executeQuery();

            System.out.println(resultSet);

            if (resultSet.next()) {
                task.setId(resultSet.getInt(1));
            }

            PreparedStatement insertTaskState = connection.prepareStatement("INSERT INTO task_states (task_id, task_state) VALUES (?, ?)");
            insertTaskState.setInt(1, task.getId());
            insertTaskState.setString(2, task.getState().toString());

            insertTaskState.executeUpdate();

            PreparedStatement insertTaskPriority = connection.prepareStatement("INSERT INTO task_priorities (task_id, task_priority) VALUES (?, ?)");
            insertTaskPriority.setInt(1, task.getId());
            insertTaskPriority.setString(2, task.getPriority().toString());

            insertTaskPriority.executeUpdate();

            return task;
        }
    }

    @Override
    public Task updateTask(Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            // the same as createTask, but with an update statement
            PreparedStatement updateTask = connection.prepareStatement("UPDATE tasks SET name = ?, description = ?, deadline = ? WHERE task_id = ?");
            updateTask.setString(1, task.getName());
            updateTask.setString(2, task.getDescription());
            updateTask.setDate(3, Date.valueOf(task.getDeadline()));
            updateTask.setInt(4, task.getId());

            updateTask.executeUpdate();

            PreparedStatement updateTaskPriority = connection.prepareStatement("UPDATE task_priorities SET task_priority = ? WHERE task_id = ?");

            updateTaskPriority.setString(1, task.getPriority().toString());
            updateTaskPriority.setInt(2, task.getId());

            updateTaskPriority.executeUpdate();


            return task;
        }
    }

    @Override
    public Task deleteTask(Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement deleteTaskPriority = connection.prepareStatement("DELETE FROM task_priorities WHERE task_id = ?");
            deleteTaskPriority.setInt(1, task.getId());
            deleteTaskPriority.executeUpdate();

            PreparedStatement deleteTaskState = connection.prepareStatement("DELETE FROM task_states WHERE task_id = ?");
            deleteTaskState.setInt(1, task.getId());
            deleteTaskState.executeUpdate();

            PreparedStatement deleteTask = connection.prepareStatement("DELETE FROM tasks WHERE task_id = ?");
            deleteTask.setInt(1, task.getId());
            deleteTask.executeUpdate();

            return task;
        }
    }

    @Override
    public Task updateTaskState(Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement updateTaskState = connection.prepareStatement("UPDATE task_states SET task_state = ? WHERE task_id = ?");
            updateTaskState.setString(1, task.getState().toString());
            updateTaskState.setInt(2, task.getId());

            updateTaskState.executeUpdate();

            return task;
        }

    }

    @Override
    public ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement getTasks = connection.prepareStatement("SELECT tasks.*, tp.task_priority, ts.task_state FROM tasks JOIN task_priorities tp ON tasks.task_id = tp.task_id JOIN task_states ts ON tasks.task_id = ts.task_id WHERE tasks.workspace_id = ?");

            getTasks.setInt(1, workspace.getId());

            ResultSet resultSet = getTasks.executeQuery();

            ArrayList<Task> tasks = new ArrayList<>();

            while (resultSet.next()) {
                TaskBuilder taskBuilder = new TaskBuilder();

                TaskState taskState = switch (resultSet.getString("task_state")) {
                    case "NotStarted" -> new NotStarted();
                    case "InProgress" -> new InProgress();
                    case "Completed" -> new Completed();
                    default -> throw new IllegalStateException("Unexpected value: " + resultSet.getString("task_state"));
                };

                taskBuilder.setId(resultSet.getInt("task_id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"))
                        .setDeadline(resultSet.getDate("deadline").toLocalDate())
                        .setPriority(Task.Priority.valueOf(resultSet.getString("task_priority")))
                        .setState(taskState)
                        .setWorkspace(workspace);


                tasks.add(taskBuilder.build());
            }

            return tasks;
        }
    }
}
