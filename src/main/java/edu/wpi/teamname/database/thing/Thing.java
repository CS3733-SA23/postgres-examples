package edu.wpi.teamname.database.thing;

public class Thing {
  private static final String tableName = "thing";
  private String name;
  private String data;
  private Integer id;

  public Thing(String name, String data) {
    this.name = name;
    this.data = data;
    this.id = null; // automatically set on insert
  }

  public Thing(String name, String data, Integer id) {
    this.name = name;
    this.data = data;
    this.id = id;
  }
  public static String getTableName() {
    return tableName;
  }

  public Integer getID() {
    return id;
  }

  public String getName() { return name; }

  public String getData() { return data; }

  public void setId(Integer id) { this.id = id; }
}
