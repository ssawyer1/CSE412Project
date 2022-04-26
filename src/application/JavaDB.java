
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class JavaDB {
    
    //Variable declaration
    Connection connection = null;
    Statement stmt = null;

    //constructor for JavaDB object
    //initializes connection to covid db
    public JavaDB(){

	try {    
	    connection = DriverManager.getConnection("jdbc:postgresql://localhost:8888/covid", "ben", "bwallace");
	    System.out.println("Connection successful");

	} catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}	
    }

    public void closeConnection(){

	//closing connection with db
	try{
	    connection.close();
	    System.out.println("Connection closed");
	
	}catch (Exception e) {	    
	    System.err.println(e.toString());
	    System.exit(0);
	}	
    }

    //cases methods
    public ResultSet selectWeeklyPercentCaseChange() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, week_percent_cases FROM Country, Has WHERE Country.name = Has.name;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }

    public ResultSet selectCasesLastSevenDays() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, cases_last_seven FROM Country, Has, Cases WHERE Country.name = Has.name AND Has.week_percent_cases = Cases.week_percent_cases;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }
    
    public ResultSet selectCasesPrecSevenDays() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, cases_prec_seven FROM Country, Has, Cases WHERE Country.name = Has.name AND Has.week_percent_cases = Cases.week_percent_cases;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }

    //Deaths methods
    public ResultSet selectWeeklyPercentDeathChange() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, week_percent_deaths FROM Country, Has WHERE Country.name = Has.name;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }

    public ResultSet selectDeathsLastSevenDays() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, deaths_last_seven FROM Country, Has, Deaths WHERE Country.name = Has.name AND Has.week_percent_deaths = Deaths.week_percent_deaths;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }
    
    public ResultSet selectDeathsPrecSevenDays() {
	
       	ResultSet ret_rs = null;

	try {
	    stmt = connection.createStatement();
	    ret_rs = stmt.executeQuery("SELECT Country.name, deaths_prec_seven FROM Country, Has, Deaths WHERE Country.name = Has.name AND Has.week_percent_deaths = Deaths.week_percent_deaths;");
	}catch ( Exception e ) {
	    System.err.println(e.toString());
	    System.exit(0);
	}
	
	return ret_rs;	
    }
    

}
