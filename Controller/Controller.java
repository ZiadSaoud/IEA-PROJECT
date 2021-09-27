package SmartAgent;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Controller implements Initializable {
	//FXML variables
    @FXML
    private ImageView exit;
    @FXML
    private ComboBox<String> selectHeight;
    @FXML
    private ComboBox<String> selectWidth;
    @FXML
    private Button createEnv;
    @FXML
    private StackPane UIstack;
    @FXML
    private StackPane stackG;
    @FXML
    private Pane gridPane;
    @FXML
    private Button envButton;
    @FXML
    private Button algoButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button aboutButton;
    
    //USER defined Variables
    private int height=0;
    private int width =0;
    private int bx = 0;
    private int by = 0;
    private Label l;
    private Button next;
    private Button clean;
    private boolean selectAgent = false;
    private boolean disable = false;
    private tile[][] tiles;
    private ArrayList<tile> destination;
    private tile sourceNode;
    private HashMap<tile,ArrayList<Edge>> ad_list;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ad_list = new HashMap<tile,ArrayList<Edge>>();
		
		destination = new ArrayList<tile>();
		UIstack.getChildren().get(0).setVisible(true);
		UIstack.getChildren().get(1).setVisible(false);
		
		exit.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				System.exit(0);
			}
		});
		
		ObservableList<String> Hlist = FXCollections.observableArrayList();
		Hlist.addAll("5","6","7","8","9","10","11");
		selectHeight.setItems(Hlist);
		selectHeight.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String a = selectHeight.getSelectionModel().getSelectedItem();
				System.out.println("height: "+a);
				height = Integer.parseInt(selectHeight.getSelectionModel().getSelectedItem());
				
			}
		});
		
		ObservableList<String> Wlist = FXCollections.observableArrayList();
		Wlist.addAll("5","6","7","8","9","10","11");
		selectWidth.setItems(Wlist);
		selectWidth.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("width: "+selectWidth.getSelectionModel().getSelectedItem());
				width = Integer.parseInt(selectWidth.getSelectionModel().getSelectedItem());
			}
		});
		
		createEnv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("env created");
				if(height==0 || width ==0) {
					//please select h and w;
				}else {
					bx = (800-(50*width)-((width-1)*4))/2;
					by = (750-(50*height)-((height-1)*4))/2;
					Rectangle r3 = new Rectangle(bx,by,width*50+(width-1)*4,height*50+(height-1)*4);
					r3.setId("rect");
					gridPane.getChildren().add(r3);
					
					Line l1 = drawLine(0,0,bx,by);
					Line l2 = drawLine(800,0,bx+width*50+(width-1)*4,by);
					Line l3 = drawLine(0,750,bx,by+height*50+(height-1)*4);
					Line l4 = drawLine(800,750,bx+width*50+(width-1)*4,by+height*50+(height-1)*4);
					gridPane.getChildren().addAll(l1,l2,l3,l4);
					
					tiles = new tile[height][width];//create matrix representing the tiles.
					for(int i=0; i<height; i++) {
						for(int j=0;j<width;j++) {
							gridPane.getChildren().add(tiles[i][j] = new tile(j*54+bx,by+i*54,"tiles", Controller.this));
						}
					}
					
					System.out.println(bx);
					System.out.println(by);
					Rectangle r = new Rectangle(bx,by,width*50+(width-1)*4,height*50+(height-1)*4);
					Rectangle r1 = new Rectangle(bx-5,by-5,width*50+(width-1)*4+10,height*50+(height-1)*4+10);
					gridPane.getChildren().addAll(Shape.subtract(r1, r));
					
					clean = new Button("Clean");
					clean.setId("clean");
					clean.setLayoutX(bx-50+((width*50+(width-1)*4))/2);
					clean.setLayoutY(by+(height*50+(height-1)*4)+30);
					clean.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							// TODO Auto-generated method stub
							System.out.println(width);
							System.out.println(height);
							
							for(int w=0;w<height;w++) {
								for(int h=0;h<width;h++) {
									
									if(!tiles[w][h].getWall()) {
										//add to adj_list
										ad_list.put(tiles[w][h], new ArrayList<Edge>());
									if(w==0) {
										tiles[w][h].setUp(null);
										if(!tiles[w+1][h].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w+1][h],1));
											tiles[w][h].setDown(tiles[w+1][h]);
										}//set down.
									}
									if(h==0) {
										tiles[w][h].setLeft(null);
										if(!tiles[w][h+1].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h+1],1));
											tiles[w][h].setRight(tiles[w][h+1]);
										}//set right.
									}
									if(w==(height-1)) {
										tiles[w][h].setDown(null);
										if(!tiles[w-1][h].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w-1][h],1));
											tiles[w][h].setUp(tiles[w-1][h]);
										}//set up
									}
									if(h==(width-1)) {
										tiles[w][h].setRight(null);
										if(!tiles[w][h-1].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h-1],1));
											tiles[w][h].setLeft(tiles[w][h-1]);
										}//set left
									}
									if(w>0 && w < (height-1)) {
										if(!tiles[w+1][h].getWall()) {
											//add down to children and set down
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w+1][h],1));
											tiles[w][h].setDown(tiles[w+1][h]);
										}
										if(!tiles[w-1][h].getWall()) {
											//add up to children and set up
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w-1][h],1));
											tiles[w][h].setUp(tiles[w-1][h]);
										}
									}
									if(h>0 && h<(width-1)) {
										if(!tiles[w][h+1].getWall()) {
											//add right to children and set right
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h+1],1));
											tiles[w][h].setRight(tiles[w][h+1]);
										}
										if(!tiles[w][h-1].getWall()) {
											//add left to children and set left
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h-1],1));
											tiles[w][h].setLeft(tiles[w][h-1]);
										}
									}
								}
							}
							}
							//after for loop
							
							
						}
					});
					
					next = new Button("Next");
					next.setId("next");
					next.setLayoutX(bx-50+((width*50+(width-1)*4))/2);
					next.setLayoutY(by+(height*50+(height-1)*4)+30);
					next.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							l.setText("Please select agent's position");
							selectAgent = true;
							
							
						}
					});
					gridPane.getChildren().add(next);
					
					l = new Label("Please Select dust and wall locations");
					l.setPrefWidth(360);
					l.setId("label");
					l.setLayoutX(bx-180+((width*50+(width-1)*4))/2);
					l.setLayoutY(by-50);
					gridPane.getChildren().add(l);
					
					envButton.setStyle("-fx-background-color:#146886");
					stackG.getChildren().get(0).setVisible(true);
					stackG.getChildren().get(1).setVisible(false);
					stackG.getChildren().get(2).setVisible(false);
					stackG.getChildren().get(3).setVisible(false);
					
					UIstack.getChildren().get(1).setVisible(true);
					UIstack.getChildren().get(0).setVisible(false);
				}
			}
		}); 
		
		envButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:#146886");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(true);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(false);
			}
		});
		algoButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:#146886");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(true);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(false);
			}
		});
		settingsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:#146886");
				aboutButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(true);
				stackG.getChildren().get(3).setVisible(false);
			}
		});
		aboutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:#146886");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(true);
				
			}
		});
		
	}
	
	public void setAgent(int x,int y, tile agent) {
		gridPane.getChildren().remove(next);
		gridPane.getChildren().add(clean);
		gridPane.getChildren().remove(l);
		Circle c = new Circle(25,Color.RED);
		Rectangle r = new Rectangle(25,25);
		r.setLayoutY(c.getLayoutY()-25);
		Shape Agent = Shape.subtract(c, r);
		Agent.setLayoutX(x);
		Agent.setLayoutY(y);
		Agent.setId("agent");
		gridPane.getChildren().add(Agent);
		disable=true;
		sourceNode = agent;
	}
	 public boolean checkAgent() {
		return selectAgent;
	}
	 
	 public boolean disableTiles(){
		 return disable;
	 }
	 
	 public void setDestination(tile t) {
		 destination.add(t);
	 }
	 public void removeDestination(tile t) {
		 if(destination.contains(t)) {
		 destination.remove(t);
		 }
	 }
	 private Line drawLine(int sx, int sy, int ex,int ey) {
		 	Line l = new Line();
			l.setStartX(sx);
			l.setStartY(sy);
			l.setEndX(ex);
			l.setEndY(ey);
			l.setStrokeWidth(3);
			return l;
	 }
	
}
