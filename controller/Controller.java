package SmartAgent;


import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Stack;
import javax.swing.Timer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.util.StringConverter;

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
    @FXML                           
    private CheckBox checkSound;
    @FXML
    private ImageView SoundImage;
    @FXML
    private ColorPicker ColorChooser;
    @FXML
    private Label TestingLabel;
    @FXML
    private ColorPicker BorderColorChooser;
    @FXML
    private Pane settingsPane;
    @FXML
    private CheckBox PathCheck;
    @FXML
    private ComboBox<String> combAlgo;
    @FXML
    private Label algoName;
    @FXML
    private CheckBox smartDistance;
    @FXML
    private LineChart<Number, Number> chart;
    @FXML
    private ComboBox<String> envType;
    @FXML
    private Button ResultsButton;
    @FXML
    private CheckBox AlgoCheck;
    @FXML
    private CheckBox dustCheck;
    @FXML
    private LineChart<Number, Number> chart1;
    @FXML
    private LineChart<Number, Number> chart2;
    @FXML
    private LineChart<Number, Number> chart3;
    @FXML
    private Label algoPicker;
    @FXML
    private Slider slider;
    @FXML
    private Spinner<Integer> spinner;

    
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
    private ArrayList<tile> path;
    private Shape Agent;
    private int agentX=0;
    private int agentY=0;
    private ArrayList<ArrayList<tile>> animations;
    private int index =0;
    private Color AgentColor;
    private Color BorderColor;
    private boolean ColorChosen=false;
    private boolean BorderChosen=false;
    private Image off=new Image("off.png");
    private Image on=new  Image("on.png");
    private boolean animate=true;
    private ArrayList<tile> algoAnimation;
    private int algoIndex=0;
    private int[][] room;
    private int agentRow;
    private int agentColumn;
    private tile SuperNode;
    private ArrayList<tile> tempDestination;
    private boolean SDistance = true;
    private ArrayList<tile> visibleNodes;
    private volatile boolean Continue = true;
    private int time=-1;
    private Label timeLabel;
    private Timeline timeline;
    private Circle c;
    private ParallelTransition move;
    private Timer t;
    private double prev =15;
    private ArrayList<tile> destinations;
    private double up=2.5;
    private double down = 5;
    private double left = 7.5;
    private double right = 10;
    private int counter =5;
    private int NDtiles =0;
    private int interval = 4;
    private ArrayList<ArrayList<tile>> Islands;
    private boolean animateAlgo = true;
    private Media m;
    private MediaPlayer mp;
    private boolean sound=false;
    private tile tempSourceNode;
    private Timer t2;
    
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
		
		ObservableList<String> Algolist = FXCollections.observableArrayList();
		Algolist.addAll("BFS", "Dantzig", "BellmanFord", "A_Star", "Greedy Best First Search");
		combAlgo.setItems(Algolist);
		combAlgo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String s = combAlgo.getSelectionModel().getSelectedItem().toString();
				algoName.setText(s);
			}
		});
		
		c = new Circle(85);
		c.setId("sensor");
		c.setVisible(false);
		
		slider.setMin(0);
		slider.setMax(1);
		slider.setValue(0);
		slider.setMinorTickCount(0);
	    slider.setMajorTickUnit(1);
	    slider.setSnapToTicks(true);
	    slider.setShowTickMarks(true);
	    slider.setShowTickLabels(true);
		
	    slider.setLabelFormatter(new StringConverter<Double>() {

			@Override
			public Double fromString(String arg0) {
				// TODO Auto-generated method stub
				switch (arg0){
					case "Low":
					return 0d;
					case "High":
					return 1d;
					default:
					return 0d;
				}
			}

			@Override
			public String toString(Double arg0) {
				if(arg0<0.5) {
				return "Low";
				}
				if(arg0>0.5) {
					return "High";
				}
				return null;
			}
	    	
	    });
		
	    
	    slider.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				if(slider.getValue()==0.0) {
					c.setRadius(85);
				}
				if(slider.getValue()==1.0) {
					c.setRadius(145);
				}
			}
		});
	    
	    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
	    spinner.setValueFactory(valueFactory);
	    
	    
		ObservableList<String> envList = FXCollections.observableArrayList();
		envList.addAll("Fully Observable","Partially Observable","None Observable");
		envType.setItems(envList);
		
		ColorChooser.setValue(Color.RED);
		ColorChooser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				AgentColor=ColorChooser.getValue();
				ColorChooser.setPromptText("Agent Color");
				System.out.print(AgentColor);
				ColorChosen=true;
				Circle c=new Circle(40,Color.RED);
				Rectangle r=new Rectangle(40,40);
				r.setLayoutY(c.getLayoutY()-40);
				Shape SubAgent = Shape.subtract(c, r);
				SubAgent.setLayoutX(80);
				SubAgent.setLayoutY(255);
				SubAgent.setFill(AgentColor);
				SubAgent.setStroke(BorderColor);
				SubAgent.setStrokeWidth(3);
				settingsPane.getChildren().add(SubAgent);
				if(Agent!=null) {
					if(BorderChosen) {
						Agent.setFill(AgentColor);
						Agent.setStroke(BorderColor);
						Agent.setStrokeWidth(3);}
					else {
						Agent.setFill(AgentColor);
						Agent.setStroke(Color.BLACK);
						Agent.setStrokeWidth(3);
					}
				}
			}
			
		});
		BorderColorChooser.setValue(Color.BLACK);
		BorderColorChooser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				BorderColor=BorderColorChooser.getValue();
				BorderColorChooser.setPromptText("Border Color");
				System.out.print(BorderColor);
				BorderChosen=true;
				Circle c=new Circle(40,Color.RED);
				Rectangle r=new Rectangle(40,40);
				r.setLayoutY(c.getLayoutY()-40);
				Shape SubAgent = Shape.subtract(c, r);
				SubAgent.setLayoutX(80);
				SubAgent.setLayoutY(255);
				SubAgent.setFill(AgentColor);
				SubAgent.setStroke(BorderColor);
				SubAgent.setStrokeWidth(3);
				settingsPane.getChildren().add(SubAgent);
				if(Agent!=null) {
					if(ColorChosen) {
						Agent.setFill(AgentColor);
						Agent.setStroke(BorderColor);
						Agent.setStrokeWidth(3);}
					else {
						Agent.setFill(Color.RED);
						Agent.setStroke(BorderColor);
						Agent.setStrokeWidth(3);
				}}
				
			}
		});
		
		File f = new File("audio.mp3");
		
		checkSound.setSelected(true);
		checkSound.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(checkSound.isSelected()) {
					SoundImage.setImage(off);
					sound=false;
			}else {
				SoundImage.setImage(on);
				sound=true;
			}
		}});
		  PathCheck.setSelected(true);
		  PathCheck.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent arg0) {
				  if(PathCheck.isSelected()) {
					  animate=true;
					  System.out.println(animate);
				  }else {
					  animate=false;
					  System.out.println(animate);
				  }
			  }
		  });
		  
		  AlgoCheck.setSelected(true);
		  AlgoCheck.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent arg0) {
				  if(AlgoCheck.isSelected()) {
					  animateAlgo=true;
				  }else {
					  animateAlgo=false;
				  }
			  }
		  });
		  
		  dustCheck.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(dustCheck.isSelected()) {
					 SDistance = false;
					 smartDistance.setSelected(false);
				  }else {
					  smartDistance.setSelected(true);
					  SDistance = true;
				  }
			}
		});
		  smartDistance.setSelected(true);
		  smartDistance.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(smartDistance.isSelected()) {
					 SDistance = true;
					 dustCheck.setSelected(false);
					 System.out.println(SDistance);
				  }else {
					  dustCheck.setSelected(true);
					  SDistance = false;
					  System.out.println(SDistance);
				  }
				
			}
		});
		 
		envType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				int i = envType.getSelectionModel().getSelectedIndex();
				if(i==0) {
					smartDistance.setVisible(true);
					dustCheck.setVisible(true);
					combAlgo.setVisible(true);
					algoPicker.setVisible(true);
					slider.setVisible(false);
					spinner.setVisible(false);
					algoPicker.setText("Choose an Algorithm:");
					c.setVisible(false);
				}
				else if(i==1) {
					smartDistance.setVisible(false);
					dustCheck.setVisible(false);
					combAlgo.setVisible(false);
					algoPicker.setVisible(true);
					spinner.setVisible(true);
					algoPicker.setText("Visibilty Range:");
					slider.setVisible(true);
					c.setVisible(true);
				}else {
					smartDistance.setVisible(false);
					dustCheck.setVisible(false);
					combAlgo.setVisible(false);
					algoPicker.setVisible(false);
					slider.setVisible(false);
					c.setVisible(false);
					spinner.setVisible(false);
				}
				
			}
		}); 
		  
		  
		chart.getXAxis().setLabel("Time (sec)");
		chart.getYAxis().setLabel("% of the room is clean");
		chart.setTitle("Fully Observable Env (Closest Neighbour)");
		XYChart.Series<Number, Number> seriesBFS = new XYChart.Series<>();
		seriesBFS.setName("BFS");
		XYChart.Series<Number, Number> seriesDantzig = new XYChart.Series<>();
		seriesDantzig.setName("Dantzig");
		XYChart.Series<Number, Number> seriesBellman = new XYChart.Series<>();
		seriesBellman.setName("Bellman Ford");
		XYChart.Series<Number, Number> seriesA_star = new XYChart.Series<>();
		seriesA_star.setName("A_star");
		XYChart.Series<Number, Number> seriesGBFS = new XYChart.Series<>();
		seriesGBFS.setName("Greedy BFS");
		
		chart.getData().add(seriesBFS);
		chart.getData().add(seriesDantzig);
		chart.getData().add(seriesBellman);
		chart.getData().add(seriesA_star);
		chart.getData().add(seriesGBFS);
		
		  
		chart1.getXAxis().setLabel("Time (sec)");
		chart1.getYAxis().setLabel("% of the room is clean");
		chart1.setTitle("Fully Observable Env (Dust Grouping)");
		XYChart.Series<Number, Number> seriesBFS_I = new XYChart.Series<>();
		seriesBFS_I.setName("BFS");
		XYChart.Series<Number, Number> seriesDantzig_I = new XYChart.Series<>();
		seriesDantzig_I.setName("Dantzig");
		XYChart.Series<Number, Number> seriesBellman_I = new XYChart.Series<>();
		seriesBellman_I.setName("Bellman Ford");
		XYChart.Series<Number, Number> seriesA_star_I = new XYChart.Series<>();
		seriesA_star_I.setName("A_star");
		XYChart.Series<Number, Number> seriesGBFS_I = new XYChart.Series<>();
		seriesGBFS_I.setName("Greedy BFS");
		
		chart1.getData().add(seriesBFS_I);
		chart1.getData().add(seriesDantzig_I);
		chart1.getData().add(seriesBellman_I);
		chart1.getData().add(seriesA_star_I);
		chart1.getData().add(seriesGBFS_I);
		
		chart2.getXAxis().setLabel("Time (sec)");
		chart2.getYAxis().setLabel("% of the room is clean");
		chart2.setTitle("Partially Observable Environment");
		XYChart.Series<Number, Number> Pseries = new XYChart.Series<>();
		Pseries.setName("Partially Observable");
		chart2.getData().add(Pseries);
		
		chart3.getXAxis().setLabel("Time (sec)");
		chart3.getYAxis().setLabel("% of the room is clean");
		chart3.setTitle("None Observable Environment");
		XYChart.Series<Number, Number> Nseries = new XYChart.Series<>();
		Nseries.setName("None Observable");
		chart3.getData().add(Nseries);

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
					setDefaultAgent(80,255);
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
							
							timeLabel =new Label();
							timeLabel.setLayoutX(bx+5);
							timeLabel.setLayoutY(by-30);
							timeLabel.setPrefHeight(25);
							timeLabel.setPrefWidth(50);
							timeLabel.setTextFill(Color.WHITE);
							gridPane.getChildren().add(timeLabel);
							timeline.play();
							
							
							System.out.println(width);
							System.out.println(height);
							
							room = new int[height][width];
							
							for(int w=0;w<height;w++) {
								for(int h=0;h<width;h++) {
									//save the ENV state
									if(tiles[w][h].getWall()) {
										room[w][h]=0;
									}else if(tiles[w][h].isDirty()) {
										room[w][h]=2;
									}else {
										room[w][h]=1;
									}
									///////////////////////////////
									if(!tiles[w][h].getWall()) {
										//add to adj_list
										ad_list.put(tiles[w][h], new ArrayList<Edge>());
									if(w==0) {
										tiles[w][h].setUp(null);
										if(!tiles[w+1][h].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w+1][h],54));
											tiles[w][h].setDown(tiles[w+1][h]);
										}//set down.
									}
									if(h==0) {
										tiles[w][h].setLeft(null);
										if(!tiles[w][h+1].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h+1],54));
											tiles[w][h].setRight(tiles[w][h+1]);
										}//set right.
									}
									if(w==(height-1)) {
										tiles[w][h].setDown(null);
										if(!tiles[w-1][h].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w-1][h],54));
											tiles[w][h].setUp(tiles[w-1][h]);
										}//set up
									}
									if(h==(width-1)) {
										tiles[w][h].setRight(null);
										if(!tiles[w][h-1].getWall()) {
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h-1],54));
											tiles[w][h].setLeft(tiles[w][h-1]);
										}//set left
									}
									if(w>0 && w < (height-1)) {
										if(!tiles[w+1][h].getWall()) {
											//add down to children and set down
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w+1][h],54));
											tiles[w][h].setDown(tiles[w+1][h]);
										}
										if(!tiles[w-1][h].getWall()) {
											//add up to children and set up
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w-1][h],54));
											tiles[w][h].setUp(tiles[w-1][h]);
										}
									}
									if(h>0 && h<(width-1)) {
										if(!tiles[w][h+1].getWall()) {
											//add right to children and set right
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h+1],54));
											tiles[w][h].setRight(tiles[w][h+1]);
										}
										if(!tiles[w][h-1].getWall()) {
											//add left to children and set left
											ad_list.get(tiles[w][h]).add(new Edge(tiles[w][h-1],54));
											tiles[w][h].setLeft(tiles[w][h-1]);
										}
									}
								}
							}
							}//after for loop
							envType.setDisable(true);
							
							getDustGrouping();
							Islands.sort(new Comparator<ArrayList<tile>>() {

								@Override
								public int compare(ArrayList<tile> o1, ArrayList<tile> o2) {
									return o2.size()-o1.size();
								}
								
							});
							ArrayList<tile> dest = new ArrayList<tile>();
							for(ArrayList<tile> a: Islands) {
								for(tile t: a) {
									dest.add(t);
								}
								System.out.println("size: "+a.size());
							}
							destination=dest;
							tempSourceNode = sourceNode;
							NDtiles = destination.size();
							int env = envType.getSelectionModel().getSelectedIndex();
							if(env==-1 || env == 0) {
							SequentialTransition s = new SequentialTransition();
							animations = new ArrayList<ArrayList<tile>>();
							tempDestination = new ArrayList<tile>();
							smartDistance.setDisable(true);
							dustCheck.setDisable(true);
							/////////////////////////////
							SuperNode = new tile(0,0,"SuperNode",Controller.this);
							for(tile n: destination) {
								tempDestination.add(n);
							}
							NDtiles = tempDestination.size();
							for(int t=0;t<tempDestination.size();t++) {
								tile  N;
								if(!SDistance) {
									N = destination.get(t);
								}else {
									N = getNextTile(sourceNode);
								}
								if(sourceNode==N) {//if the first Node is the destination no need to execute the algorithm
									sourceNode.setStyle("-fx-background-image: none; -fx-background-color: white");
									sourceNode.setDirty(false);
									continue;
								}
								boolean c=false;
								if(combAlgo.getSelectionModel().getSelectedIndex()==-1) {
									 c = BFS(sourceNode, N);
									System.out.println(c);
								}else {
								switch (combAlgo.getSelectionModel().getSelectedItem().toString()) {
								case "BFS":
									c = BFS(sourceNode, N);
									break;
								case "Dantzig":
									c = Dantzig(sourceNode, N);
									break;
								case "BellmanFord":
									c = BellmanFord(sourceNode, N);
									break;
								case "A_Star":
									c = A_Star(sourceNode, N);
									System.out.println(c);
									break;
								case "Greedy Best First Search":
									c = GBFS(sourceNode, N);
									System.out.println(c);
									break;
								}
								}
								if(c) {
									if(animateAlgo) {
										Animate(algoAnimation, s);
									}
									PauseTransition p = new PauseTransition(Duration.seconds(1.5));
									s.getChildren().add(p);
								    path=backTrack(sourceNode, N,false);
								    animations.add(path);
								}else {
									continue;
								}
								sourceNode=N;
								resetWeights();
								//animation
								clean.setDisable(true);
							
								for(int a=0;a<path.size()-1;a++) {
								TranslateTransition animation = new TranslateTransition(Duration.seconds(0.3),Agent);
								animation.setInterpolator(Interpolator.LINEAR);
								tile next = path.get(a+1);
								tile currentNode = path.get(a);
								if(a==0) {
									PauseTransition pause = new PauseTransition(Duration.seconds(0.01));
									pause.setOnFinished(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent arg0) {
											if(animate){
											for(tile p: animations.get(index)) {
												if(p.isDirty()) {
													p.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: orange");
												}else {
													p.setStyle("-fx-background-color:orange");
												}
											}
										}
									}
								});
									s.getChildren().add(pause);
								}
								if(a==path.size()-2) {
									animation.setOnFinished(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent arg0) {
											next.setStyle("-fx-background-image: none; -fx-background-color:orange");
											next.setDirty(false);
											NDtiles--;
											if(sound) {
												m = new Media(f.toURI().toString());
												mp = new MediaPlayer(m);
												mp.play();
											}
											for(tile p: animations.get(index)) {
												if(p.isDirty()) {
													p.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: white");
												}else {
													System.out.println("hello");
													p.setStyle("-fx-background-color:white");
												}
											}
											index++;
										}
										
									});
								}
								if(currentNode.getUp()==next) {
									agentY=agentY-54;
									animation.setToY(agentY);
									s.getChildren().add(animation);
								}
								if(currentNode.getDown()==next) {
									agentY=agentY+54;
									animation.setToY(agentY);
									s.getChildren().add(animation);
								}
								if(currentNode.getLeft()==next) {
									agentX = agentX -54;
									animation.setToX(agentX);
									s.getChildren().add(animation);
								}
								if(currentNode.getRight()==next) {
									agentX = agentX +54;
									animation.setToX(agentX);
									s.getChildren().add(animation);
								}
								if(t==tempDestination.size()-1 && a==path.size()-2) {
							    	PauseTransition p1 = new PauseTransition(Duration.seconds(5));
									s.getChildren().add(p1);
								}
							}
							}
							s.play();
							s.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									sourceNode = tempSourceNode;
									index=0;
									algoIndex=0;
									clean.setDisable(false);
									resetWeights();
									RedrawEnv();
									gridPane.getChildren().remove(Agent);
									gridPane.getChildren().remove(c);
									Agent = drawAgent(agentColumn,agentRow);
									drawCircle(agentColumn, agentRow);
									gridPane.getChildren().add(c);
									c.setVisible(false);
									gridPane.getChildren().add(Agent);
									agentX=0;
									agentY=0;
									smartDistance.setDisable(false);
									dustCheck.setDisable(false);
									envType.setDisable(false);
									/*if(SDistance) {
									for(tile t: tempDestination) {
										destination.add(t);
									}
									}*/
									gridPane.getChildren().remove(timeLabel);
									time = -1;
									interval=4;
									timeline.stop();
									/*
									for(int i=0;i<chart3.getData().size();i++) {
										chart3.getData().remove(i);
									}
									for(int i=0;i<chart.getData().size();i++) {
										chart3.getData().add(chart.getData().get(i));
									}
									for(int i=0;i<chart1.getData().size();i++) {
										chart3.getData().add(chart1.getData().get(i));
									}
									*/
									
								
									System.out.println(agentColumn);
									System.out.println(agentRow);
								}
							});
							}else if(env==1) {
								/*
								timeLabel =new Label();
								timeLabel.setLayoutX(bx+5);
								timeLabel.setLayoutY(by-30);
								timeLabel.setPrefHeight(25);
								timeLabel.setPrefWidth(50);
								timeLabel.setTextFill(Color.WHITE);
								gridPane.getChildren().add(timeLabel);*/
								//timeline.play();
								t.start();
								spinner.setDisable(true);
								
							}else {
							
								
								t2.start();
								
							
							
								
								
								
								
								
								
							}
						}
					});
					
					
					timeline = new Timeline(
						    new KeyFrame(
						      Duration.ZERO,
						      actionEvent -> {
						    	    time++;
						    	    interval++;
						    	    int ENV = envType.getSelectionModel().getSelectedIndex();
						    	    if(time == 0) {
						    	    	clean.setDisable(true);
						    	    }
						    	    if(interval == 5) {
						    	    	double percentage = (((height*width)-NDtiles)/(double)(height*width))*100.0;
						    	    	System.out.println(percentage);
						    	    	interval=0;
						    	    	if(ENV == -1 || ENV == 0) {
						    	    	if(combAlgo.getSelectionModel().getSelectedIndex()==-1) {
						    	    		if(SDistance) {
						    	    		seriesBFS.getData().add(new XYChart.Data<>(time,percentage));
						    	    		}else {
						    	    			seriesBFS_I.getData().add(new XYChart.Data<>(time,percentage));
						    	    		}
										}else {
										switch (combAlgo.getSelectionModel().getSelectedItem().toString()) {
										case "BFS":
											if(SDistance) {
												seriesBFS.getData().add(new XYChart.Data<>(time,percentage));
											}else {
												seriesBFS_I.getData().add(new XYChart.Data<>(time,percentage));
											}
											break;
										case "Dantzig":
											if(SDistance) {
												seriesDantzig.getData().add(new XYChart.Data<>(time,percentage));
											}else {
												seriesDantzig_I.getData().add(new XYChart.Data<>(time,percentage));
											}
											break;
										case "BellmanFord":
											if(SDistance) {
												seriesBellman.getData().add(new XYChart.Data<>(time,percentage));
											}else {
												seriesBellman_I.getData().add(new XYChart.Data<>(time,percentage));
											}
											break;
										case "A_Star":
											if(SDistance) {
												seriesA_star.getData().add(new XYChart.Data<>(time,percentage));
											}else {
												seriesA_star_I.getData().add(new XYChart.Data<>(time,percentage));
											}
											break;
										case "Greedy Best First Search":
											if(SDistance) {
												seriesGBFS.getData().add(new XYChart.Data<>(time,percentage));
											}else {
												seriesGBFS_I.getData().add(new XYChart.Data<>(time,percentage));
											}
											break;
										}
										}
										}else if(ENV == 1) {
											Pseries.getData().add(new XYChart.Data<>(time,percentage));
										}else {//ENV = 2
											
											Nseries.getData().add(new XYChart.Data<>(time,percentage));
										}
						    	    }
									int minutes = time/60;
									int seconds = time - (minutes*60);
									if(minutes==spinner.getValue() && ENV == 1 ) {
										t.stop();
										
										timeline.stop();
										sourceNode = tempSourceNode;
										clean.setDisable(false);
										spinner.setDisable(false);
										resetWeights();
										RedrawEnv();
										gridPane.getChildren().remove(Agent);
										gridPane.getChildren().remove(c);
										Agent = drawAgent(agentColumn,agentRow);
										drawCircle(agentColumn, agentRow);
										gridPane.getChildren().add(c);
										gridPane.getChildren().add(Agent);
										agentX=0;
										agentY=0;
										gridPane.getChildren().remove(timeLabel);
										time = -1;
										interval=4;
										envType.setDisable(false);
									}
									String min = ""+minutes;
									String sec = ""+seconds;
									if(minutes < 10) {
										 min = "0"+minutes;
									}
									if(seconds<10) {
										 sec = "0"+seconds;
									}
									String time = min+":"+sec;
									timeLabel.setText(time);
									}
						    ),
						    new KeyFrame(
						      Duration.seconds(1)
						    )
						);
						timeline.setCycleCount(Timeline.INDEFINITE);
						
					 t = new Timer(1000,new ActionListener() {
						
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("hmar123");
							if(Continue) {
							boolean moved=false;
							visibleNodes = new ArrayList<tile>();
							destinations = new ArrayList<tile>();
							visibleNodes.add(sourceNode);
							
								int i = (sourceNode.getX()-bx)/54;
								int j = (sourceNode.getY()-by)/54;
								if(slider.getValue()==0.0) {
									int[] col = {-1,-1,-1,0,0,1,1,1};
									int[] row = {-1,0,1,-1,1,-1,0,1};
									for(int g=0;g<col.length;g++) {
										if((i+col[g]>=0 && i+col[g]<tiles[1].length)  && (j+row[g]>=0 && j+row[g]<tiles.length)) {
											visibleNodes.add(tiles[j+row[g]][i+col[g]]);
										}
									}
								}
								if(slider.getValue()==1.0) {
									int[] col = {-2,-2,-2,-2,-2,-1,-1,-1,-1,-1,0,0,0,0,1,1,1,1,1,2,2,2,2,2};
									int[] row = {-2,-1,0,1,2,-2,-1,0,1,2,-2,-1,1,2,-2,-1,0,1,2,-2,-1,0,1,2};
									for(int g=0;g<col.length;g++) {
										if((i+col[g]>=0 && i+col[g]<tiles[1].length)  && (j+row[g]>=0 && j+row[g]<tiles.length)) {
											visibleNodes.add(tiles[j+row[g]][i+col[g]]);
										}
									}
								}
								
								for(tile t:visibleNodes) {
									if(t.isDirty()) {
										destinations.add(t);
									}
								}
								if(destinations.size()!=0) {
								boolean d = BFS_partially(sourceNode, destinations.get(0), visibleNodes);
								System.out.println(d);
								if(d) {
									Continue = false;
									counter=0;
									left = 7.5;
									up = 2.5;
									if(sourceNode.getX()-destinations.get(0).getX()>0) {
										left+=2;
									}else if(sourceNode.getX() != destinations.get(0).getX()){
										left-=2;
									}
									if(sourceNode.getY()-destinations.get(0).getY()>0) {
										up+=2;
									}else if(sourceNode.getY()!=destinations.get(0).getY()){
										up-=2;
									}
									SequentialTransition s = new SequentialTransition();
									ArrayList<tile> path = backTrack(sourceNode, destinations.get(0), false);
									resetWeights();
									for(tile y: path) {
										if(y.isDirty()) {
											y.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: orange");
										}else {
											y.setStyle("-fx-background-color:orange");
										}
									}
									if(path.size()==1) {
										sourceNode.setStyle("-fx-background-color:white");
										sourceNode.setDirty(false);
										Continue=false;
										PauseTransition p = new PauseTransition(Duration.seconds(1));
										p.setOnFinished(new EventHandler<ActionEvent>() {

											@Override
											public void handle(ActionEvent arg0) {
												Continue = true;
												NDtiles--;
											}
										});
										p.play();
									}
									for(int a=0;a<path.size()-1;a++) {
										ParallelTransition parallel = new ParallelTransition();
										TranslateTransition animation = new TranslateTransition(Duration.seconds(0.3),Agent);
										TranslateTransition animation_1 = new TranslateTransition(Duration.seconds(0.3),c);
										animation.setInterpolator(Interpolator.LINEAR);
										animation_1.setInterpolator(Interpolator.LINEAR);
										tile next = path.get(a+1);
										tile currentNode = path.get(a);
										if(a==path.size()-2) {
											parallel.setOnFinished(new EventHandler<ActionEvent>() {

												@Override
												public void handle(ActionEvent arg0) {
													next.setDirty(false);
													NDtiles--;
												}
												
											});
										}
										/*
										if(path.size()==2) {
											parallel.setOnFinished(new EventHandler<ActionEvent>() {

												@Override
												public void handle(ActionEvent arg0) {
													next.setDirty(false);
													NDtiles--;
												}
												
											});
										}
										*/
										if(currentNode.getUp()==next) {
											agentY=agentY-54;
											animation.setToY(agentY);
											animation_1.setToY(agentY);
											parallel.getChildren().addAll(animation,animation_1);
										}
										if(currentNode.getDown()==next) {
											agentY=agentY+54;
											animation.setToY(agentY);
											animation_1.setToY(agentY);
											parallel.getChildren().addAll(animation,animation_1);
										}
										if(currentNode.getLeft()==next) {
											agentX = agentX -54;
											animation.setToX(agentX);
											animation_1.setToX(agentX);
											parallel.getChildren().addAll(animation,animation_1);
										}
										if(currentNode.getRight()==next) {
											agentX = agentX +54;
											animation.setToX(agentX);
											animation_1.setToX(agentX);
											parallel.getChildren().addAll(animation,animation_1);
										}
										s.getChildren().add(parallel);
									}
									s.play();
									s.setOnFinished(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent arg0) {
											sourceNode =destinations.get(0); 
											for(tile y: path) {
												if(y.isDirty()) {
													y.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: white");
												}else {
													y.setStyle("-fx-background-color:white");
												}
											}
											Continue = true;
										}
									});
									
								}}
								if(Continue) {
									System.out.println("Counter: "+counter);
									System.out.println("Left: "+left);
									System.out.println("UP:   "+up);
									if(counter == 4) {
										left = 7.5;
										up=2.5;
										
									}
									if(counter<4) {
										counter++;
									}
									move  = new ParallelTransition();
									TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.5),Agent);
									TranslateTransition t2 = new TranslateTransition(Duration.seconds(0.5),c);
									int count=0;
								while(!moved) {
									if(count == 40) {
										count =0;
										prev = 15;
									}
									double rand = Math.random()*10;
									System.out.println(rand);
								if(rand<=up && !(prev>up && prev<=down)) {//up
									if(sourceNode.getUp()!=null) {
										agentY = agentY-54;
										t1.setToY(agentY);
										t2.setToY(agentY);
										sourceNode = sourceNode.getUp();
										prev=rand;
										moved = true;
									}
								}
								if(rand>up && rand<=down && !(prev<=up)) {//down
									if(sourceNode.getDown()!=null) {
										agentY = agentY+54;
										t1.setToY(agentY);
										t2.setToY(agentY);
										sourceNode = sourceNode.getDown();
										prev=rand;
										moved = true;
									}
								}
								if(rand>down && rand<=left && !(prev>left && prev<=right)) {//left
									if(sourceNode.getLeft()!=null) {
										agentX = agentX-54;
										t1.setToX(agentX);
										t2.setToX(agentX);
										sourceNode = sourceNode.getLeft();
										prev=rand;
										moved = true;
									}
								}
								if(rand>left && rand<=right && !(prev>down && prev<=left)) {//right
									if(sourceNode.getRight()!=null) {
										agentX = agentX+54;
										t1.setToX(agentX);
										t2.setToX(agentX);
										sourceNode = sourceNode.getRight();
										prev=rand;
										moved = true;
									}
								}
								count++;
								}
								move.getChildren().addAll(t1,t2);
								//counter++; //if we generate more than 15 random number the robot is stuck.
								/*move.setOnFinished(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent arg0) {
										for(tile t:visibleNodes) {
											t.setStyle("-fx-background-color: white");
										}
									}
								});*/
								move.play();
								}
							}
						}
							
						
					});
					 
					 t2 = new Timer(1000,new ActionListener() {
							
							@Override
							public void actionPerformed(java.awt.event.ActionEvent e) {
								TranslateTransition t1 = new TranslateTransition(Duration.seconds(0.5),Agent);
							
							
								double up=2.5;
								double down=5;
								double left=7.5;
								double right=10;
								boolean choose=false;
								do {
									double rand = Math.random()*10;
								if(rand<=up) {
									if(sourceNode.getUp()!=null) {
										agentY = agentY-54;
										t1.setToY(agentY);
										sourceNode=sourceNode.getUp();
										choose=true;
								}}
								if(rand>up && rand<=down) {
									if(sourceNode.getDown()!=null) {
										agentY = agentY+54;
										t1.setToY(agentY);
										sourceNode=sourceNode.getDown();
										choose=true;
								}}
								
								if(rand>down && rand<=left) {
									if(sourceNode.getLeft()!=null) {
										agentX = agentX-54;
										t1.setToX(agentX);
										sourceNode=sourceNode.getLeft();
										choose=true;
								}}
								
								if(rand>left && rand<=right) {
									if(sourceNode.getRight()!=null) {
										agentX = agentX+54;
										t1.setToX(agentX);
										sourceNode=sourceNode.getRight();
										choose=true;
								}}}while(!choose);
								
								t1.play();
								PauseTransition p1 = new PauseTransition(Duration.seconds(0.5));
								t1.setOnFinished(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent arg0) {
										if(sourceNode.isDirty()) {
											m = new Media(f.toURI().toString());
											mp = new MediaPlayer(m);
											mp.play();
										
											sourceNode.setDirty(false);
											sourceNode.setStyle("-fx-background-image: none");
											NDtiles--;
											p1.play();
											
										}
										
									}
									
									
								});
								
									
							}});
									
								
								
								
								
							
						
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
					stackG.getChildren().get(4).setVisible(false);
					
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
				ResultsButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(true);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(false);
				stackG.getChildren().get(4).setVisible(false);
			}
		});
		algoButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:#146886");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:transparent");
				ResultsButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(true);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(false);
				stackG.getChildren().get(4).setVisible(false);
			}
		});
		settingsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:#146886");
				aboutButton.setStyle("-fx-background-color:transparent");
				ResultsButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(true);
				stackG.getChildren().get(3).setVisible(false);
				stackG.getChildren().get(4).setVisible(false);
			}
		});
		aboutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:#146886");
				ResultsButton.setStyle("-fx-background-color:transparent");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(true);
				stackG.getChildren().get(4).setVisible(false);
			}
		});
		
		ResultsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				envButton.setStyle("-fx-background-color:transparent");
				algoButton.setStyle("-fx-background-color:transparent");
				settingsButton.setStyle("-fx-background-color:transparent");
				aboutButton.setStyle("-fx-background-color:transparent");
				ResultsButton.setStyle("-fx-background-color:#146886");
				
				stackG.getChildren().get(0).setVisible(false);
				stackG.getChildren().get(1).setVisible(false);
				stackG.getChildren().get(2).setVisible(false);
				stackG.getChildren().get(3).setVisible(false);
				stackG.getChildren().get(4).setVisible(true);
			}
		});
		
	}
	
	public void setAgent(int x,int y, tile agent) {
		agentRow = y;
		agentColumn = x;
		c.setLayoutX(x);
		c.setLayoutY(y);
		Agent = drawAgent(x,y);
		gridPane.getChildren().add(clean);
		gridPane.getChildren().removeAll(next,l);
		gridPane.getChildren().add(c);
		gridPane.getChildren().add(Agent);
		disable=true;
		sourceNode = agent;
	}
	
	private Shape drawAgent(int x,int y) {
		Circle c = new Circle(25,Color.RED);
		Rectangle r = new Rectangle(25,25);
		r.setLayoutY(c.getLayoutY()-25);
		Shape agent = Shape.subtract(c, r);
		agent.setLayoutX(x);
		agent.setLayoutY(y);
		if(ColorChosen && BorderChosen) {
			agent.setStroke(BorderColor);
		 	agent.setStrokeWidth(3);
		    agent.setFill(AgentColor);}
		
		else if(!ColorChosen && BorderChosen) {
			agent.setStroke(BorderColor);
			agent.setStrokeWidth(3);
		    agent.setFill(Color.RED);
		    
		}else if(ColorChosen && !BorderChosen) {
			agent.setStroke(Color.BLACK);
			agent.setStrokeWidth(3);
		    agent.setFill(AgentColor);
		}else {
			agent.setFill(Color.RED);
			agent.setStroke(Color.BLACK);
			agent.setStrokeWidth(3);
		}
		return agent;
		
	}
	
	private void drawCircle(int x,int y) {
		c = new Circle();
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setId("sensor");
		if(slider.getValue()==0.0) {
			c.setRadius(85);
		}
		if(slider.getValue()==1.0) {
			c.setRadius(145);
		}
	}
	
	public void setDefaultAgent(int x,int y) {
		Circle c = new Circle(40,Color.RED);
		Rectangle r = new Rectangle(40,40);
		r.setLayoutY(c.getLayoutY()-40);
		Shape DefaultAgent = Shape.subtract(c, r);
		DefaultAgent.setLayoutX(x);
		DefaultAgent.setLayoutY(y);
		DefaultAgent.setFill(Color.RED);
	    DefaultAgent.setStroke(Color.BLACK);
		DefaultAgent.setStrokeWidth(3);
		settingsPane.getChildren().add(DefaultAgent);
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
	 
	 private void RedrawEnv() {
		 for(int i=0;i<height;i++) {
			 for(int j=0;j<width;j++) {
				 if(room[i][j]==1) {
					 tiles[i][j].setDirty(false);
					 tiles[i][j].setWall(false);
					 tiles[i][j].setStyle("-fx-background-color: white");
				 }
				 if(room[i][j]==0) {
					 tiles[i][j].setDirty(false);
					 tiles[i][j].setWall(true);
					 tiles[i][j].setStyle("-fx-background-color: black");
				 }
				 if(room[i][j]==2) {
					 tiles[i][j].setDirty(true);
					 tiles[i][j].setWall(false);
					 tiles[i][j].setStyle("-fx-background-image:url('dust.png'); -fx-background-color: white");
				 }
			 }
		 }
	 }
	 
	 private boolean BFS(tile start,tile destination) {
		 ArrayList<tile> arr = new ArrayList<tile>();
		 PriorityQueue<tile> q = new PriorityQueue<tile>();
		 start.setW(0);
		 q.add(start);
		 while(!q.isEmpty()) {
			 tile v= q.peek();
			 ArrayList<Edge> children = ad_list.get(v);
			 for(Edge e: children) {
				 if(!arr.contains(e.getDest())) {
					 int cw = e.getDest().getW();
					 int ccw = v.getW()+e.getWeight();
					 if(cw >ccw) {
					 e.getDest().setW(ccw);	
					}
					 if(!q.contains(e.getDest())) {
							q.add(e.getDest());
					}
				 }
			 }
			 if(v==destination) {
				 arr.add(v);
				 algoAnimation = arr;
				 return true;
			 }else {
				 for(Edge e: children) {
					 if(e.getDest()==destination) {
						 arr.add(v);
						 algoAnimation = arr;
						 return true;
					 }
				 }
			 }
			 arr.add(q.poll());
		 }
		 return false;
	 }
	 
	 private boolean BFS_partially(tile start,tile destination, ArrayList<tile> vis) {
		 ArrayList<tile> arr = new ArrayList<tile>();
		 PriorityQueue<tile> q = new PriorityQueue<tile>();
		 start.setW(0);
		 q.add(start);
		 while(!q.isEmpty()) {
			 tile v= q.peek();
			 ArrayList<Edge> children = ad_list.get(v);
			 for(Edge e: children) {
				 if(vis.contains(e.getDest())) {
				 if(!arr.contains(e.getDest())) {
					 int cw = e.getDest().getW();
					 int ccw = v.getW()+e.getWeight();
					 if(cw >ccw) {
					 e.getDest().setW(ccw);	
					}
					 if(!q.contains(e.getDest())) {
							q.add(e.getDest());
					}
				 }
			 }
				 }
			 if(v==destination) {
				 return true;
			 }else {
				 for(Edge e: children) {
					 if(e.getDest()==destination) {
						 return true;
					 }
				 }
			 }
			 arr.add(q.poll());
		 }
		 return false;
	 }
	 
	 private boolean Dantzig(tile s,tile d) {
			ArrayList<tile> arr = new ArrayList<tile>();//visited
			PriorityQueue<tile> q = new PriorityQueue<tile>();//frontier
			s.setW(0);//initialize source weight.
			arr.add(s);
			q.add(s);
			int minW;
			while(arr.size()<=ad_list.size()) {
			for(tile n: arr) {
				for(Edge e: ad_list.get(n)) {
					if(!arr.contains(e.getDest())) {
						int cw = e.getDest().getW();
						int ccw = n.getW()+e.getWeight();
						if(cw >ccw) {
							e.getDest().setW(ccw);	
						}
						if(!q.contains(e.getDest())) {
						q.add(e.getDest());
						}
					}
				}
			}
				tile removedNode = q.poll();
				minW = removedNode.getW();
				arr.add(removedNode);
				while(!q.isEmpty() && q.peek().getW()==minW) {
					removedNode = q.poll();
					arr.add(removedNode);
				}
				for(tile test: arr) {
					algoAnimation =arr;
					if(test==d) {
						return true;
					}
				}
			}
			System.out.println(arr.size());
			return false;	
		}
	 private boolean BellmanFord(tile s,tile d) {
			ArrayList<tile> arr = new ArrayList<tile>();//visited
			PriorityQueue<tile> q = new PriorityQueue<tile>();//frontier
			s.setW(0);//initialize source weight.
			q.add(s); //Add source node to frontier
			
			while(!q.isEmpty() && arr.size()<=ad_list.size()) {
				tile v = q.peek();
				ArrayList<Edge> children = ad_list.get(v);
				for(Edge e: children) {
					if(!arr.contains(e.getDest())) {
					int cw = e.getDest().getW();
					int ccw = v.getW()+e.getWeight();
					if(cw >ccw) {
						e.getDest().setW(ccw);	
					}
					if(!q.contains(e.getDest())) {
						q.add(e.getDest());
						}
				}
			}
				for(Edge e: children) {
					for(Edge n:ad_list.get(e.getDest())) {
						if(!arr.contains(n.getDest())) {
						if(n.getDest().getW()!=Integer.MAX_VALUE) {
						int cw_1 = e.getDest().getW();
						int ccw_1 = v.getW()+e.getWeight();
						if(cw_1 >ccw_1) {
							e.getDest().setW(ccw_1);	
						}
					}
					}
				}
				}
				arr.add(q.poll());
			}
			for(tile n:arr) {
				if(n==d) {
					algoAnimation = arr;
					return true;
				}
			}
			return false;
		}
	 
	 private boolean A_Star(tile s,tile d) {
			ArrayList<tile> arr = new ArrayList<tile>();//visited
			PriorityQueue<tile> q = new PriorityQueue<tile>();//frontier
			s.setW(0+s.getHeuristic(d,bx,by));//initialize source with f =0.
			s.setSpecificW(0);
			q.add(s);
			while(!q.isEmpty()) {
				tile current = q.poll();
				arr.add(current);
				System.out.println("specific: "+current.getSpecificW());
				if(current==d) {
					algoAnimation = arr;
					return true;
				}
				for(Edge e: ad_list.get(current)) {
					if(arr.contains(e.getDest())) {
						continue;
					}
					int cw = e.getDest().getSpecificW();
					int ccw = current.getSpecificW()+e.getWeight();
					int f = ccw + e.getDest().getHeuristic(d,bx,by);
					if(q.contains(e.getDest())) {
						if(cw<ccw) {
							continue;
						}
						/*else {
							e.getDest().setSpecificW(ccw);
							continue;
						}*/
					}
					e.getDest().setW(f);
					e.getDest().setSpecificW(ccw);
					if(!q.contains(e.getDest())) {
						q.add(e.getDest());
					}
				}
				
			}
			
			return false;
	 }
	 
	 private boolean GBFS(tile s,tile d) {
		 	ArrayList<tile> arr = new ArrayList<tile>();//visited
			PriorityQueue<tile> q = new PriorityQueue<tile>();//frontier
			s.setW(0+s.getHeuristic(d,bx,by));//initialize source with f =0.
			s.setSpecificW(0);
			q.add(s);
			while(!q.isEmpty()) {
			tile current = q.poll();
			arr.add(current);
			if(current == d) {
				algoAnimation =arr;
				return true;
			}else {
				for(Edge e: ad_list.get(current)) {
					if(arr.contains(e.getDest())) {
						continue;
					}
					int cw = e.getDest().getSpecificW();
					int ccw = current.getSpecificW()+e.getWeight();
					int f =  e.getDest().getHeuristic(d,bx,by);
						if(cw>ccw) {
							e.getDest().setSpecificW(ccw);
						}
					e.getDest().setW(f);
					if(!q.contains(e.getDest())) {
						q.add(e.getDest());
					}
				}
			}
			}
		 return false;
	 }
	 
	 private void resetWeights() {
		 for(int i=0;i<width;i++) {
			 for(int j=0;j<height;j++) {
				 tiles[j][i].setW(Integer.MAX_VALUE);
				 tiles[j][i].setSpecificW(Integer.MAX_VALUE);
			 }
		 }
	 }
	 
	 private ArrayList<tile> backTrack(tile start,tile destination, boolean distance){
		 ArrayList<tile> out = new ArrayList<tile>();
		 Stack<tile> path = new Stack<tile>();
		 path.add(destination);
		 tile current = destination;
		 while(start!=current) {
			 System.out.println("hmar");
			 ArrayList<Edge> children = ad_list.get(current);
			 for(Edge e:children) {
				 if(combAlgo.getSelectionModel().getSelectedIndex()>=3 && !distance) {
					 if(e.getWeight()+e.getDest().getSpecificW()  == current.getSpecificW()) {
						 path.add(e.getDest());
						 current = e.getDest();
						 break;
					 }
				 }else {
				 if(e.getWeight()+e.getDest().getW()  == current.getW()) {
					 path.add(e.getDest());
					 current = e.getDest();
					 break;
				 }
				 }
			}
		 }
		 while(!path.isEmpty()) {
			out.add(path.pop());	
		}
			return out;
	 }
	 
	 private tile getNextTile(tile source) {
		 if(!ad_list.containsKey(SuperNode)) {
			 ad_list.put(SuperNode, new ArrayList<Edge>());
			 for(tile n: destination) {
					ad_list.get(n).add(new Edge(SuperNode,0));
					ad_list.get(SuperNode).add(new Edge(n,0));
				}
		 }
		 boolean c = BFS(source,SuperNode);
		 System.out.println("Problem is not here: "+c);
		 if(c) {
			 System.out.println("before");
			 ArrayList<tile> path = backTrack(source, SuperNode,true);
			 System.out.println("after");
			 resetWeights();
			 SuperNode.setW(Integer.MAX_VALUE);
			 tile returnedTile = path.get(path.size()-2);
			 for(int e=0;e<destination.size();e++) {
				ArrayList<Edge> children = ad_list.get(destination.get(e));
					for(int i=0;i<children.size();i++) {
						if(children.get(i).getDest()==SuperNode) {
							children.remove(i);
						}
					}
			 }
			 ArrayList<Edge> children = ad_list.get(SuperNode);
			 for(int j=0;j<children.size();j++) {
					 children.remove(j);
			 }
			 for(tile n: destination) {
				 if(n==returnedTile) {
					 System.out.println("Right destination");
					 destination.remove(returnedTile);
					 ad_list.remove(SuperNode);
					 return returnedTile; 
				 }
			 }
			 System.out.println("did not return the right one");
		 }
		 return source;
	 }
	 
	 private void getDustGrouping() {
		 Islands = new ArrayList<ArrayList<tile>>();
		 Stack<tile> st = new Stack<tile>();
		 ArrayList<tile> visited = new ArrayList<tile>();
		 st.push(tiles[0][0]);
		 while(!st.isEmpty()) {
			 tile t =st.pop();
			 if(!visited.contains(t)) {
				 visited.add(t);
				 if(t.isDirty()) {
					 ArrayList<tile> island = new ArrayList<tile>();
					 island.add(t);
					 Islands.add(island);
					 discoverIsland(st,island,ad_list.get(t),visited);
				 }else {
					 for(Edge e:ad_list.get(t)) {
						 st.push(e.getDest());
					 }
				 }
			 }
		 }
	 }
	 
	 private void discoverIsland(Stack<tile> st,ArrayList<tile> island,ArrayList<Edge> children,ArrayList<tile> visited) {
		 for(Edge e:children) {
			 if(e.getDest().isDirty() && !visited.contains(e.getDest())) {
				 island.add(e.getDest());
				 visited.add(e.getDest());
				 discoverIsland(st,island,ad_list.get(e.getDest()),visited);
			 }
			 else {
				 st.push(e.getDest());
			 }
		 } 
	 }
	 
	 
	 private void Animate(ArrayList<tile> arr,SequentialTransition s) {
		 for(int i=0;i<arr.size();i++) {
			 PauseTransition p = new PauseTransition(Duration.seconds(0.1));
			 p.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
						if(algoIndex<arr.size()) {
							if(arr.get(algoIndex).isDirty()) {
								arr.get(algoIndex).setStyle("-fx-background-image:url('dust.png'); -fx-background-color: cyan");
							}else {
								arr.get(algoIndex).setStyle("-fx-background-color:cyan");
							}
						}
						
						algoIndex++;
				}
			});
			 s.getChildren().add(p);
			 if(i==arr.size()-1) {
				 PauseTransition p1 = new PauseTransition(Duration.seconds(1.5));
				 p1.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						 algoIndex=0;
							for(tile t:arr) {
								if(t.isDirty()) {
									t.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: white");
								}else {
								    t.setStyle("-fx-background-color:white");
								}
							}
					}
				});
				 s.getChildren().add(p1); 
			 }
		 }
		
	 }
}
