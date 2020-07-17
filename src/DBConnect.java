import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnect 
{
  private static DBConnect conInstance = null;
  /**
   * Connections to the database.
   */
  private Connection connection;
  

  
  private DBConnect ()  {
    connection = null;
    try 
    {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarySys","sinhchhinh", "************");

     // Statement stmt = conInstance.createStatement ();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /**
   * Get the static instance of the database connection
   * @return
   * DBConnect instance that has access to the database if the connection is successful
   */  
  public static DBConnect getConnect () 
  {
    if (conInstance == null){
      try {
        conInstance = new DBConnect();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("Made a connection");
    }
    return conInstance;
  }
  
  /**
   * Closing the database connection 
   */
  public void closeConn () {
    System.out.println("Closing the connection and system");
    try {
      if (connection != null){
        connection.close();
        connection = null;
      }
    } catch (SQLException e){
      System.out.println(e);
    }
  }
  
  /**
   * Given the sql select the information 
   * @param sql
   * @return rows are mapped to String[], Columns are mapped to String
   */
  public List<String[]> selectSQL (String sql ){
    List<String[]> resultList = new ArrayList<>();
    String[] result;
    Statement statement = null;
    ResultSet resultSet; // A pointer that point to the database returned value
    ResultSetMetaData resultSetMD; // get the result such as col & rows

    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
      resultSetMD = resultSet.getMetaData();
      while (resultSet.next()) {
        result = new String[resultSetMD.getColumnCount()];
        for (int idx = 0 ; idx < result.length; idx++) {
          result[idx] = resultSet.getString(idx +1);
        }
        resultList.add(result);
      }
      statement.close(); //closing the select statement
        
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return resultList;
  }
  
  /** Accessed from Dickfoong's file.
   * Execute SQL Data Manipulation Language (DML) statement, such as
   * <i>INSERT</i>, <i>UPDATE</i>, or <i>DELETE</i> statement or SQL DDL
   * statement.
   * 
   * @param sql SQL Data Manipulation Language (DML) statement, such as
   *            <i>INSERT</i>, <i>UPDATE</i>, or <i>DELETE</i>; or SQL statement
   *            that returns nothing,such as a DDL statement.
   * @return either (1) the row count for SQL Data Manipulation Language (DML)
   *         statements or (2) 0 for SQL statements that return nothing or (3) -1
   *         for SQL statements failed to execute.
   */
  public int executeUpdateSQL(String sql) {
      System.out.println("Preparing DML/DDL statement");
      Statement statement = null;
      int rowCount = -1;

      try {
          statement = connection.createStatement();
          rowCount = statement.executeUpdate(sql);
          statement.close();
          System.out.println("DML/DDL statement is executed successfully.");
      } catch (SQLException sqle) {
          if (statement != null) {
            try {
              statement.close();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
      }
      return rowCount;
  }

}
