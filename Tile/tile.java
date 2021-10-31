package SmartAgent;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class tile extends Button implements Comparable<tile>{
	private boolean black = false;
	private boolean img = false;
	private int X;
	private int Y;
	private tile up = null;
	private tile down = null;
	private tile left = null;
	private tile right = null;
	private int weight=Integer.MAX_VALUE;
	private int SW=Integer.MAX_VALUE;
	private int w1=Integer.MAX_VALUE;
	
	public tile(int x,int y, String id, Controller c) {
		X=x;
		Y=y;
		setLayoutX(x);
		setLayoutY(y);
		setId(id);
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(!c.disableTiles()){
				if(c.checkAgent() && !black) {
					c.setAgent(X+25, Y+25,tile.this);
					
				}else if(!c.checkAgent()){
					
				if(arg0.getButton() == MouseButton.PRIMARY){
					if(!black && !img) {
					setStyle("-fx-background-image:url('dust.png')");
					img=true;
					c.setDestination(tile.this);
					}else if(!black && img) {
						setStyle("-fx-background-image:none");
						img=false;
						c.removeDestination(tile.this);
					}
				}
				if(arg0.getButton() == MouseButton.SECONDARY) {
					if(img) {
						c.removeDestination(tile.this);
						setStyle("-fx-background-color: black");
						black = true;
					}else {
					setStyle("-fx-background-color: black");
					black = true;
					}
				}
				if(arg0.getClickCount() == 2) {
					setStyle("-fx-background-image:none");
					black = false;
					img = false;
					c.removeDestination(tile.this);
				}
				
				}
			}
			}
		});	
	}
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}
	
	public tile getUp() {
		return up;
	}
	public void setUp(tile up) {
		this.up = up;
	}
	public tile getDown() {
		return down;
	}
	public void setDown(tile down) {
		this.down = down;
	}
	public tile getLeft() {
		return left;
	}
	public void setLeft(tile left) {
		this.left = left;
	}
	public tile getRight() {
		return right;
	}
	public void setRight(tile right) {
		this.right = right;
	}
	public boolean getWall() {
		return black;
	}
	public void setWall(boolean b) {
		black = b;
	}
	public int getW() {
		return weight;
	}
	public void setW(int w) {
		this.weight = w;
	}
	public int getW1() {
		return w1;
	}
	public void setW1(int w1) {
		this.w1 = w1;
	}
	public boolean isDirty() {
		return img;
	}
	public void setDirty(boolean d) {
		img=d;
	}
	
	//today
	public int getHeuristic(tile destination,int bx,int by) {
		int h = Math.abs(X - destination.getX())+Math.abs(Y-destination.getY());//Manhattan Distance
		//int h = (int) Math.sqrt((X - destination.getX())^2+(Y-destination.getY())^2);//Euclidean Distance
		//int h=(int) (54*(Math.abs(X - destination.getX())+Math.abs(Y-destination.getY())) + (Math.sqrt(54)-2*54)*Math.min(Math.abs(X - destination.getX()), Math.abs(Y-destination.getY())));//Diagonal Distance
		//int h = (Math.abs(X - destination.getX())-bx)/54 + (Math.abs(Y - destination.getY())-by)/54;
		return h;
	}
	public void setSpecificW(int w) {
		SW=w;
	}
	public int getSpecificW() {
		return SW;
	}
	
	@Override
	public int compareTo(tile n) {
		if(weight<n.weight) {
			return -1;
		}else if(weight>n.weight) {
			return 1;
		}
		return 0;
	}
	public void removeDust() {
		setStyle("-fx-background-image: none");
	}
	
}
