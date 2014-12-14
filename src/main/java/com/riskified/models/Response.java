package main.java.com.riskified.models;

public class Response {
  public int status;

  public ResOreder order;

  public ResError error;

  public class ResOreder {
    public String id;
    public String status;
    public String description;
  }

  public class ResError {
    public String message;
  }
}
