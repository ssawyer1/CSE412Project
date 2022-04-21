package application;
	
import javafx.application.Application; 
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.chart.*;


public class Main extends Application {

    public static void main(String[] args ) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    BorderPane bp = new BorderPane();
    	
    	
    GridPane gp = new GridPane();
    bp.setStyle("-fx-background-color: rgb(" + 168 + "," + 198 + ", " + 250 + ");");
    gp.setAlignment(Pos.CENTER);
    gp.setHgap(20); //horizontal gap in pixels => that's what you are asking for
    gp.setVgap(20); //vertical gap in pixels
    	
    HBox bottom = new HBox();
    bottom.setAlignment(Pos.CENTER);
    bottom.setSpacing(100);
    bottom.setPadding(new Insets(50));
    	
    Text header = new Text("Data Trends For COVID-19 In South America");
    header.setFont(Font.font("Courier", FontWeight.BOLD, 30)); 
    Text header1 = new Text("Weekly trends of Covid-19 as of April 20, 2022-19");
    header1.setFont(Font.font("Courier", FontWeight.NORMAL, 15)); 
	
	primaryStage.setTitle("COVID Info");
	Button btn = new Button();
	btn.setText("Display all country population");
	
    CategoryAxis xAxis = new CategoryAxis();
	NumberAxis yAxis = new NumberAxis();
	BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
	bc.setTitle("Countries with Population > 30 Million");
	xAxis.setLabel("Country");
	yAxis.setLabel("Population");

	XYChart.Series series1 = new XYChart.Series();
	series1.setName("2022");
	
	//JavaDB db = new JavaDB(); 
	//ResultSet rs = db.selectAllFromCountry();
	ResultSet rs = null;

	try{
	    while(rs.next()){
		String name = rs.getString("name");
		int population = rs.getInt("population");
		if(population > 30000000){
		series1.getData().add(new XYChart.Data(name, population));
		}
	    }
	}catch(Exception e){}
	
	//db.closeConnection();

	bc.getData().addAll(series1);
	
	bp.setAlignment(header, Pos.CENTER);
	bp.setPadding(new Insets(20));
	bp.setTop(header);	
	bp.setCenter(gp);
	bp.setBottom(bottom);
	
	/*	StackPane root = new StackPane();
		root.getChildren().add(bc);*/
	primaryStage.setScene(new Scene(bp, 950, 700));
	primaryStage.show();	
    }
}
