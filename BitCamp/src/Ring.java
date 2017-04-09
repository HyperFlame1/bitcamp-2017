import java.awt.Graphics;
import java.awt.Color;
public class Ring {
	float x,y,r;
	Color c;
	boolean killself;
	public Ring(int x, int y) {
		this.x=x;
		this.y=y;
		r=1;
		c = Keyboard.randomNeon();
		killself = false;
	}
	
	public void draw(Graphics g){
		r+=1;
		x+=.5;
		y+=.5;
		if(r>1500)
			killself=true;
		g.setColor(c);
		g.drawOval((int)(x-r), (int)(y-r), (int)r, (int)r);
		g.drawOval((int)(x-r), (int)(y-r), (int)r+1, (int)r+1);
		g.drawOval((int)(x-r), (int)(y-r), (int)r+2, (int)r+2);
		g.drawOval((int)(x-r), (int)(y-r), (int)r+3, (int)r+3);
		g.drawOval((int)(x-r), (int)(y-r), (int)r+4, (int)r+4);
	}

}
