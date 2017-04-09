import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Keyboard {
	static final int whiteKeyWidth = 114;
	static final int blackKeyWidth = 76; // 2/3 of white
	static final int whiteKeyHeight = 600;
	static final int blackKeyHeight = (int) ((whiteKeyHeight*3)/5.0);
	public Color[] neons;
	public Rectangle[] whiteKeys;
	public boolean[] whitePressed;
	public Rectangle[] blackKeys;
	public boolean[] blackPressed;
	public Keyboard() {
		neons = new Color[12];
		whiteKeys = new Rectangle[7];
		whitePressed = new boolean[7];
		blackKeys = new Rectangle[5];
		blackPressed = new boolean[5];
		for(int i=0;i<7;i++){
			whiteKeys[i] = new Rectangle(i*whiteKeyWidth, 0, whiteKeyWidth, whiteKeyHeight);
		}
		blackKeys[0] = new Rectangle((int) ((2.0/3)*whiteKeyWidth) ,0,blackKeyWidth,blackKeyHeight);
		blackKeys[1] = new Rectangle((int) ((5.0/3)*whiteKeyWidth) ,0,blackKeyWidth,blackKeyHeight);
		blackKeys[2] = new Rectangle((int) ((11.0/3)*whiteKeyWidth) ,0,blackKeyWidth,blackKeyHeight);
		blackKeys[3] = new Rectangle((int) ((14.0/3)*whiteKeyWidth) ,0,blackKeyWidth,blackKeyHeight);
		blackKeys[4] = new Rectangle((int) ((17.0/3)*whiteKeyWidth) ,0,blackKeyWidth,blackKeyHeight);
		for(int i=0;i<12;i++){
			neons[i] = randomNeon(); 
		}
	}

	public void draw(Graphics g){
		//White keys
		for(int i=0;i<7;i++){
			if(whitePressed[i])
				if(Visualizer.daft)
					g.setColor(neons[i]);
				else
					g.setColor(Color.CYAN);
			else
				if(Visualizer.daft)
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.WHITE);
			g.fillRect(whiteKeys[i].x, whiteKeys[i].y, whiteKeys[i].width, whiteKeys[i].height);
		}
		if(Visualizer.daft)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK); //Outline
		for(int i=0;i<7;i++){
			g.drawRect(whiteKeys[i].x, whiteKeys[i].y, whiteKeys[i].width, whiteKeys[i].height);
		}
		
		//Black Keys
		for(int i=0;i<5;i++){
			if(blackPressed[i])
				if(Visualizer.daft)
					g.setColor(neons[i+7]);
				else
					g.setColor(Color.CYAN);
			else
				if(Visualizer.daft)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.BLACK);
			g.fillRect(blackKeys[i].x, blackKeys[i].y, blackKeys[i].width, blackKeys[i].height);	
		}
		if(Visualizer.daft)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.BLACK);
		for(int i=0;i<5;i++){
			g.drawRect(blackKeys[i].x, blackKeys[i].y, blackKeys[i].width, blackKeys[i].height);	
		}
	
	}
	
	/*public void presskey(boolean isWhite, int index){
		if(isWhite){
			//play sound
		}else{
			//play sound
		}
	}
	
	public boolean isPressed(int i)
	{
		return whitePressed[i];
	}*/
	public static Color randomNeon(){
       float frequency = (float) (Math.random()*.6+.01);
       float redShift = (float) (Math.random()*Math.PI*2);
       float greenShift = (float) (Math.random()*Math.PI*2);
       float blueShift = (float) (Math.random()*Math.PI*2);
        
        short red, green, blue;
        red = (short) (64 * Math.sin(frequency * Visualizer.timer/50+ redShift) + 192);
        green = (short) (64 * Math.sin(frequency * Visualizer.timer/50 + greenShift) + 192);
        blue = (short) (64 * Math.sin(frequency * Visualizer.timer/50 + blueShift) + 192);
		//Color c = new Color(
			/*(int)(127*Math.sin(Visualizer.timer+offset)+128),
			(int)(127*Math.sin(Visualizer.timer+offset+(Math.PI/3.0))+128),
			(int)(127*Math.sin(Visualizer.timer+offset+(2*Math.PI/3.0))+128)
				);*/
		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		return new Color(Color.HSBtoRGB(hsb[0], 1, hsb[2]));
		//return new Color((int)(Math.random()*56+200),(int)(Math.random()*56+200),(int)(Math.random()*56+200));
	}
	
}
