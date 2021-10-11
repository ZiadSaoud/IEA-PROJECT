package SmartAgent;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

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
    Image off=new Image("off.PNG");
    Image on=new  Image("on.PNG");
    private boolean animate=false;
    private ArrayList<tile> algoAnimation;
    private int algoIndex=0;
    private int[][] room;
    private int agentRow;
    private int agentColumn;
    
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
		checkSound.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(checkSound.isSelected()) {
					SoundImage.setImage(off);
			}else {
				SoundImage.setImage(on);
			}
		}});
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
							SequentialTransition s = new SequentialTransition();
							tile tempSourceNode = sourceNode;
							animations = new ArrayList<ArrayList<tile>>();
							//after for loop
							for(tile N: destination) {
								if(sourceNode==N) {//if the first Node is the destination no need to execute the algorithm
									sourceNode.setStyle("-fx-background-image: none; -fx-background-color: white");
									sourceNode.setDirty(false);
									continue;
								}
								boolean c=Dantzig(sourceNode,N);
								System.out.println(c);
								if(c) {
									Animate(algoAnimation, s);
									PauseTransition p = new PauseTransition(Duration.seconds(1.5));
									s.getChildren().add(p);
								    path=backTrack(sourceNode, N);
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
											for(tile p: animations.get(index)) {
												if(p.isDirty()) {
													p.setStyle("-fx-background-image:url('dust.png'); -fx-background-color: orange");
												}else {
													p.setStyle("-fx-background-color:orange");
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
									Agent = drawAgent(agentColumn,agentRow);
									gridPane.getChildren().add(Agent);
									agentX=0;
									agentY=0;
									System.out.println(agentColumn);
									System.out.println(agentRow);
								}
							});
							
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
		agentRow = y;
		agentColumn = x;
		Agent = drawAgent(x,y);
		gridPane.getChildren().add(clean);
		gridPane.getChildren().removeAll(next,l);
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
			s.setW(0+s.getHeuristic(d));//initialize source with f =0.
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
					int f = ccw + e.getDest().getHeuristic(d);
					if(q.contains(e.getDest())) {
						if(cw<ccw) {
							continue;
						}
						else {
							e.getDest().setSpecificW(ccw);
							continue;
						}
					}
					e.getDest().setW(f);
					e.getDest().setSpecificW(ccw);
					q.add(e.getDest());
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
	 
	 private ArrayList<tile> backTrack(tile start,tile destination){
		 ArrayList<tile> out = new ArrayList<tile>();
		 Stack<tile> path = new Stack<tile>();
		 path.add(destination);
		 tile current = destination;
		 while(start!=current) {
			 ArrayList<Edge> children = ad_list.get(current);
			 for(Edge e:children) {//for A_Star ALGO USE: getSpecificW()   current.getSpecificW()
				 if(e.getWeight()+e.getDest().getW()  == current.getW()) {//today
					 path.add(e.getDest());
					 current = e.getDest();
					 break;
				 }
			 }
		 }
		 while(!path.isEmpty()) {
			out.add(path.pop());	
		}
			return out;
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
