package dk.via.taskmanagement.model;

public class InProgress implements TaskState
{
  @Override public void startTask(Task task)
  {
    task.setTaskState(this);
  }

  @Override public void finnishTask(Task task)
  {
    task.setTaskState(new Completed());
  }
}
