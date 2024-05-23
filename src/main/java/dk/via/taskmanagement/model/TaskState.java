package dk.via.taskmanagement.model;

public interface TaskState
{
  public void startTask(Task task);
  public void finnishTask(Task task);
}
