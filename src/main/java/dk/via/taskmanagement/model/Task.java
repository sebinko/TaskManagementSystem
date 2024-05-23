package dk.via.taskmanagement.model;

import java.util.ArrayList;

public class Task
{
  private String taskName;
  private String description;
  private String tag;
  private TaskState state;

  private Priority Priority;
  private ArrayList<User> users = new ArrayList<>();

  enum Priority {
    LOW, MEDIUM, HIGH
  }
  public Task(String taskName, users User)
  {
    this.taskName="";
    state= new NotStarted();
  }

  public TaskState getTaskState(){
    return state;
  }
  public void setTaskState (TaskState state)
  {
    this.state=state;
  }
  public void startTask(){
    state.startTask(this);
  }
  public void finishTask(){
    state.finnishTask(this);
  }

  public TaskState(){

  }

}
