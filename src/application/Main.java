//package application;

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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.chart.*;

public class Main extends Application {
	JavaDB db = new JavaDB();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: rgb(" + 168 + "," + 198 + ", " + 250 + ");");

		Text header = new Text("Data Trends For COVID-19 In South America");
		header.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
		Line line = new Line(100, 150, 1000, 150);

		Text header1 = new Text("Weekly trends of Covid-19 as of April 20, 2022-19");
		header1.setStyle("-fx-font: 24 Calibri;");
		Text header2 = new Text("Select the data you wish to display from the boxes below:");
		header2.setStyle("-fx-font: 18 Calibri;");

		ComboBox<String> cb = new ComboBox<String>();
		cb.getItems().add("Nothing Selected");
		cb.getItems().add("Deaths");
		cb.getItems().add("Cases");

		// instantiate the bar chart
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

		Button week_deaths = new Button("Visualize week percent deaths");
		week_deaths.setPrefSize(250, 40);

		Button deaths_last = new Button("Visualize deaths in the last week");
		deaths_last.setPrefSize(250, 40);

		Button deaths_preceding = new Button("Visualize deaths in the preceding week");
		deaths_preceding.setPrefSize(250, 40);

		Button week_cases = new Button("Visualize week percent cases");
		week_cases.setPrefSize(250, 40);
		Button cases_last = new Button("Visualize cases in the last week");
		cases_last.setPrefSize(250, 40);
		Button cases_preceding = new Button("Visualize cases in the preceding week");
		cases_preceding.setPrefSize(250, 40);

		// vbox holding all buttons for death
		VBox death_vb = new VBox(week_deaths, deaths_last, deaths_preceding);
		death_vb.setStyle("-fx-padding: 20;");
		death_vb.setSpacing(50);

		// vbox holding all buttons for case
		VBox case_vb = new VBox(week_cases, cases_last, cases_preceding);
		case_vb.setStyle("-fx-padding: 20;");
		case_vb.setSpacing(50);

		GridPane vb_holder = new GridPane();

		// Handle the Combo Box
		/*
		 * Queries: week percent deaths deaths last seven days deaths preceding seven
		 * days week percent cases cases last seven days cases preceding seven days
		 */
		cb.getSelectionModel().selectFirst();
		cb.setOnAction((event) -> {
			if (cb.getValue() == "Deaths") {
				// making sure no duplicate children are added
				if (vb_holder.getChildren().contains(case_vb)) {
					vb_holder.getChildren().remove(case_vb);
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}
				}

				// add the buttons
				vb_holder.add(death_vb, 0, 2);

				// handle the buttons
				week_deaths.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}
					bc.setTitle("             Weekly Percent Change in Deaths");
					//xAxis.setLabel("Days");
					//yAxis.setLabel("Deaths");
					XYChart.Series series1 = new XYChart.Series();
					CategoryAxis xScatterAxis = new CategoryAxis();
					NumberAxis yScatterAxis = new NumberAxis(-100, 250, 50);
					xScatterAxis.setLabel("Country");
					yScatterAxis.setLabel("Weekly % change in deaths");
					ScatterChart<String, Number> scatterChart = new ScatterChart<String, Number>(xScatterAxis, yScatterAxis);
					
					series1.setName("2022");
					
					//JavaDB db = new JavaDB();
					ResultSet rs = db.selectWeeklyPercentDeathChange();
					
					try 
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int week_percent_deaths = rs.getInt("week_percent_deaths");
							series1.getData().add(new XYChart.Data(name, week_percent_deaths));
						}
					}catch(Exception e) {}
					
					scatterChart.getData().addAll(series1);
					

					// add the graph to the borderpane
					//bp.setCenter(bc);
					bp.setCenter(scatterChart);
				});

				deaths_last.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}
					bc.setTitle("             Deaths in the Last Week");
					xAxis.setLabel("Country");
					yAxis.setLabel("Deaths");
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("2022");
					bc.getData().clear();
					yAxis.setAutoRanging(true);
					
					
					ResultSet rs = db.selectDeathsLastSevenDays();
					
					try 
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int deaths_last_seven = rs.getInt("deaths_last_seven");
							series1.getData().add(new XYChart.Data(name, deaths_last_seven));
						}
					}catch(Exception e) {}

					bc.getData().addAll(series1);
					// add the graph to the borderpane
					bp.setCenter(bc);
				});

				deaths_preceding.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}
					bc.setTitle("             Deaths in the Preceding Week");
					xAxis.setLabel("Country");
					yAxis.setLabel("Deaths");
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("2022");
					
					bc.getData().clear();
					yAxis.setAutoRanging(true);
					xAxis.setAutoRanging(true);
					

					ResultSet rs = db.selectDeathsPrecSevenDays();
					
					try
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int deaths_prec_seven = rs.getInt("deaths_prec_seven");
							series1.getData().add(new XYChart.Data(name, deaths_prec_seven));
							
						}
					}catch(Exception e) {}
					
					bc.getData().addAll(series1);

					// add the graph to the borderpane
					bp.setCenter(bc);

				});
			}
			if (cb.getValue() == "Cases") {
				// making sure no duplicate children are added
				if (vb_holder.getChildren().contains(death_vb)) {
					vb_holder.getChildren().remove(death_vb);
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}
				}
				// add the buttons
				vb_holder.add(case_vb, 0, 2);

				// handle the buttons
				week_cases.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}

					bc.setTitle("             Weekly Percent Change in Cases");
					xAxis.setLabel("Days");
					yAxis.setLabel("Cases");
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("2022");

					CategoryAxis xScatterAxis = new CategoryAxis();
					NumberAxis yScatterAxis = new NumberAxis(-200, 1150, 50);
					xScatterAxis.setLabel("Country");
					yScatterAxis.setLabel("Weekly % change in cases");
					ScatterChart<String, Number> scatterChart = new ScatterChart<String, Number>(xScatterAxis, yScatterAxis);
					
				       
					ResultSet rs = db.selectWeeklyPercentCaseChange();
					
					try 
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int week_percent_cases = rs.getInt("week_percent_cases");
							series1.getData().add(new XYChart.Data(name, week_percent_cases));
						}
					}catch(Exception e) {}
					scatterChart.getData().addAll(series1);

					// add the graph to the borderpane
					//bp.setCenter(bc);
					bp.setCenter(scatterChart);
				});

				cases_last.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}

					bc.setTitle("             Cases in the Last Week");
					xAxis.setLabel("Country");
					yAxis.setLabel("Cases");
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("2022");
					
					bc.getData().clear();
					yAxis.setAutoRanging(true);
					
					
					
					ResultSet rs = db.selectCasesLastSevenDays();
					
					try 
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int cases_last_seven = rs.getInt("cases_last_seven");
							series1.getData().add(new XYChart.Data(name, cases_last_seven));
						}
					}catch(Exception e) {}
					
					bc.getData().addAll(series1);
					

					// add the graph to the borderpane
					bp.setCenter(bc);

				});

				cases_preceding.setOnAction(value -> {
					if (bp.getChildren().contains(bc)) {
						bp.getChildren().remove(bc);
					}

					bc.setTitle("             Cases in the preceding Week");
					xAxis.setLabel("Country");
					yAxis.setLabel("Cases");
					XYChart.Series series1 = new XYChart.Series();
					series1.setName("2022");

					bc.getData().clear();
					yAxis.setAutoRanging(true);
					
					
					ResultSet rs = db.selectCasesPrecSevenDays();
					
					try 
					{
						while(rs.next())
						{
							String name = rs.getString("name");
							int cases_prec_seven = rs.getInt("cases_prec_seven");
							series1.getData().add(new XYChart.Data(name, cases_prec_seven));
						}
					}catch(Exception e) {}
					
					
					bc.getData().addAll(series1);
					
					// add the graph to the borderpane
					bp.setCenter(bc);

				});
			}
		});

		// Vertical Box that holds the Main header and line
		VBox vb1 = new VBox(header, line);
		vb1.setAlignment(Pos.CENTER);
		vb1.setSpacing(10);
		vb1.setPadding(new Insets(10));

		// vertical box that holds the combo box and text on top of it
		VBox vb = new VBox(header1, header2, cb);
		vb.setStyle("-fx-padding: 20;");
		vb.setSpacing(15);

		// just for spacing
		Text filler = new Text();

		// vb holder is a gridpane that will hold the buttons to display data
		vb_holder.add(vb, 0, 0); // left hand side of the GUI holding combo box
		vb_holder.add(filler, 0, 1);

		// bp is a borderpane, basically the root of everything. base layer
		bp.setPadding(new Insets(20));
		bp.setTop(vb1);
		bp.setLeft(vb_holder);
		// bp.setBottom();

		/*
		 * StackPane root = new StackPane(); root.getChildren().add(bc);
		 */
		primaryStage.setScene(new Scene(bp, 1050, 650));
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
					db.closeConnection();
				});
	}
}
