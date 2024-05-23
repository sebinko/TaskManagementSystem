package dk.via.taskmanagement.model;

public class Completed implements TaskState
{
  @Override public void startTask(Task task)
  {
    task.setTaskState(new InProgress());
  }

  @Override public void finnishTask(Task task)
  {
    task.setTaskState(this);
  }
}
