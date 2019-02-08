package populate;

import java.io.IOException;
import java.sql.*;
import FileIO.FileRead;
     
    /** 
     * 
     */ 
    public class PopulateDB { 
        public static void main(String args[]) throws IOException { 
           PopulateDB pDB = new PopulateDB();
           pDB.run(); 
       } 
    
       private void run() throws IOException { 
           Connection con = null; 
           try { 
               con = openConnection();
    
               populateMovie_Countries(con);
               populateMovie_Genres(con);
               populateMovie_Tags(con);
               populateMovie_location(con);
               populateMovies(con);
               populateTags(con);
               populateUser_TaggedMovies(con);
               showTableContent(con); 
           } catch (SQLException e) { 
               System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
           } catch (ClassNotFoundException e) { 
               System.err.println("Cannot find the database driver"); 
           } finally { 
               // Never forget to close database connection 
               closeConnection(con); 
           } 
       } 
    
       /** 
        * Using prepared statement that is faster than regular statement. 
        * @param con 
        * @throws SQLException 
     * @throws IOException
        */ 
       
       private void populateMovie_Countries(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM MOVIE_COUNTRIES");
    	   FileRead fR = new FileRead("DatFiles/movie_countries.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_COUNTRIES VALUES(?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\s");
				for(int j = 2; j < words.length; j++)
				{
					words[1] += " " + words[j] + " ";
				}
				if(words.length<2)
				{
					stmt.setString(1, words[0]);
		            stmt.setString(2, " ");
				}
				else
				{
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
				}
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
       
       private void populateMovie_Genres(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM MOVIE_GENRES");
    	   FileRead fR = new FileRead("DatFiles/movie_genres.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_GENRES VALUES(?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\s");
				for(int j = 2; j < words.length; j++)
				{
					words[1] += " " + words[j] + " ";
				}
				if(words.length<2)
				{
					stmt.setString(1, words[0]);
		            stmt.setString(2, " ");
				}
				else
				{
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
				}
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
       
       private void populateMovie_Tags(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM MOVIE_TAGS");
    	   FileRead fR = new FileRead("DatFiles/movie_tags.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_TAGS VALUES(?,?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\s");
				for(int j = 3; j < words.length; j++)
				{
					words[2] += " " + words[j] + " ";
				}
				
				if(words.length<3)
				{
					stmt.setString(1, words[0]);
		            stmt.setString(2, " ");
				}
				else
				{
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
	            stmt.setString(3, words[2]);
				}
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
       
       private void populateMovies(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM MOVIE");
    	   FileRead fR = new FileRead("DatFiles/movies.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE VALUES(?,?,?,?,?,?,?,?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\t");
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
	            stmt.setString(3, words[5]);
	            if(words[7].equals("\\N")) {
	            	words[7] = "0";
	            }
	            stmt.setString(4, words[7]);
	            if(words[8].equals("\\N")) {
	            	words[8] = "0";
	            }
	            stmt.setString(5, words[8]);
	            if(words[12].equals("\\N")) {
	            	words[12] = "0";
	            }
	            stmt.setString(6, words[12]);
	            if(words[13].equals("\\N")) {
	            	words[13] = "0";
	            }
	            stmt.setString(7, words[13]);
	            if(words[17].equals("\\N")) {
	            	words[17] = "0";
	            }
	            stmt.setString(8, words[17]);
	            if(words[18].equals("\\N")) {
	            	words[18] = "0";
	            }
	            stmt.setString(9, words[18]);
	            
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
       
       private void populateMovie_location(Connection con) throws SQLException{
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM MOVIE_LOCATIONS");
    	   FileRead fR = new FileRead("DatFiles/movie_locations.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   //System.out.println(str[].length());
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO MOVIE_LOCATIONS VALUES(?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\t");
				stmt.setString(1, words[0]);
				if(words.length == 1) {
					stmt.setString(2, "");
				}
				else {
					stmt.setString(2, words[1]);
				}
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
      }
       
       private void populateTags(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM TAGS");
    	   FileRead fR = new FileRead("DatFiles/tags.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO TAGS VALUES(?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\s");
				for(int j = 2; j < words.length; j++)
				{
					words[1] += " " + words[j] + " ";
				}
				if(words.length<2)
				{
					stmt.setString(1, words[0]);
		            stmt.setString(2, " ");
				}
				else
				{
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
				}
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
       
       private void populateUser_TaggedMovies(Connection con) throws SQLException {
    	   Statement stmt1 = con.createStatement();
    	   stmt1.executeQuery("DELETE FROM USER_TAGGEDMOVIES");
    	   FileRead fR = new FileRead("DatFiles/USER_TAGGEDMOVIES.dat");
    	   String[] str = null;
		try {
			str = fR.openFile();
		
    	   String[] words = null;
    	   
           PreparedStatement stmt = con.prepareStatement("INSERT INTO USER_TAGGEDMOVIES VALUES(?,?,?,?,?,?,?,?,?)");
			for(int i = 1; i < str.length; i++)
			{
				words = str[i].split("\\s");
				stmt.setString(1, words[0]);
	            stmt.setString(2, words[1]);
	            stmt.setString(3, words[2]);
	            stmt.setString(4, words[3]);
	            stmt.setString(5, words[4]);
	            stmt.setString(6, words[5]);
	            stmt.setString(7, words[6]);
	            stmt.setString(8, words[7]);
	            stmt.setString(9, words[8]);
	            stmt.executeUpdate();
			}
			stmt.close();
			stmt1.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Cannot close connection: " + e.getMessage());
		}
       }
    
       private void showTableContent(Connection con) throws SQLException { 
           Statement stmt = con.createStatement(); 
           ResultSet result = stmt.executeQuery("SELECT * FROM USER_TAGGEDMOVIES");
    
           /* 
           We use ResultSetMetaData.getColumnCount() to know the number columns 
           that are contained. 
           */ 
           ResultSetMetaData meta = result.getMetaData(); 
           for (int col = 1; col <= meta.getColumnCount(); col++) { 
               System.out.println("Column" + col + ": " + meta.getColumnName(col) + 
                                  "\t, Type: " + meta.getColumnTypeName(col));
           } 
    
           /* 
           Every time we call ResultSet.next(), its internal cursor will advance 
           one tuple. By calling this method continuously, we can iterate through 
           the whole ResultSet. 
           */ 
           while (result.next()) { 
               for (int col = 1; col <= meta.getColumnCount(); col++) { 
                   System.out.print("\"" + result.getString(col) + "\","); 
               } 
               System.out.println(); 
           } 
    
          /* 
          It is always a good practice to close a statement as soon as we 
          no longer use it. 
          */ 
          stmt.close(); 
      } 
   
      /** 
       * 
       * @return a database connection 
       * @throws java.sql.SQLException when there is an error when trying to connect database 
       * @throws ClassNotFoundException when the database driver is not found. 
       */ 
      private Connection openConnection() throws SQLException, ClassNotFoundException { 
          // Load the Oracle database driver 
          DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
   
          /* 
          Here is the information needed when connecting to a database 
          server. These values are now hard-coded in the program. In 
          general, they should be stored in some configuration file and 
          read at connectToDB time.
          */ 
          String host = "127.0.0.1";
          String port = "1521";
          String dbName = "orcl"; 
          String userName = "hr";
          String password = "oracle";
   
          // Construct the JDBC URL 
          String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
          return DriverManager.getConnection(dbURL, userName, password); 
      } 
   
      /** 
       * Close the database connection 
       * @param con 
       */ 
      private void closeConnection(Connection con) { 
          try { 
              con.close(); 
          } catch (SQLException e) { 
              System.err.println("Cannot close connection: " + e.getMessage()); 
          } 
      } 
  }