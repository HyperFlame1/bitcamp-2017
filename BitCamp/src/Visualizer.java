import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.leapmotion.leap.*;
import java.util.LinkedList;

public class Visualizer implements KeyListener{
	static final int width = 798;
	static final int height = 600;
	final float fps = 60;
	final float spf = 1000f/fps;
	static long timer;
	static boolean daft = false;
	LinkedList<Ring> rings;
	Clip[] whitePianoNotes = new Clip[7];
	Clip[] blackPianoNotes = new Clip[5];
	Clip[] whiteDaftNotes = new Clip[7];
	Clip[] blackDaftNotes = new Clip[5];
	File file;
	JFrame frame;
	Keyboard keyboard;
	FingerList fingers = new FingerList();
	InteractionBox box = new InteractionBox();
	public Visualizer(Keyboard keyboard) {
		this.keyboard = keyboard;
		rings = new LinkedList<>();
		frame = new JFrame("Piano");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer = 0;//System.currentTimeMillis();
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
				timer++;
				if(timer%1100==0){
					for(int i=0;i<12;i++)
						keyboard.neons[i] = Keyboard.randomNeon();
				}
				//timer += spf;
				//long sleep = timer - System.currentTimeMillis();
				//if(sleep >= 0){
				//	try{Thread.sleep(sleep);} catch (InterruptedException  e){e.printStackTrace();}
				//}
				super.paintComponent(g);
				repaint();
				draw(g);
			}
		};
		try{
			for(int i=0;i<7;i++){
				whitePianoNotes[i] = AudioSystem.getClip();
			}
			for(int i=0;i<5;i++){
				blackPianoNotes[i] = AudioSystem.getClip();
			}
			whitePianoNotes[0].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\C.wav")));
			whitePianoNotes[1].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\D.wav")));
			whitePianoNotes[2].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\E.wav")));
			whitePianoNotes[3].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\F.wav")));
			whitePianoNotes[4].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\G.wav")));
			whitePianoNotes[5].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\A.wav")));
			whitePianoNotes[6].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\B.wav")));
			blackPianoNotes[0].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\C#.wav")));
			blackPianoNotes[1].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\D#.wav")));
			blackPianoNotes[2].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\F#.wav")));
			blackPianoNotes[3].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\G#.wav")));
			blackPianoNotes[4].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\A#.wav")));
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		try{
			for(int i=0;i<7;i++){
				whiteDaftNotes[i] = AudioSystem.getClip();
			}
			for(int i=0;i<5;i++){
				blackDaftNotes[i] = AudioSystem.getClip();
			}
			whiteDaftNotes[0].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 11.wav")));
			whiteDaftNotes[1].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 12.wav")));
			whiteDaftNotes[2].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 1.wav")));
			whiteDaftNotes[3].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 5.wav")));
			whiteDaftNotes[4].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 6.wav")));
			whiteDaftNotes[5].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 7.wav")));
			whiteDaftNotes[6].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 8.wav")));
			blackDaftNotes[0].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 9.wav")));
			blackDaftNotes[1].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 10.wav")));
			blackDaftNotes[2].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 2.wav")));
			blackDaftNotes[3].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 3.wav")));
			blackDaftNotes[4].open(AudioSystem.getAudioInputStream(new File("C:\\Users\\User\\Music\\bitcamp music notes\\d_Insert 4.wav")));
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		/*
		 * Clip clip = AudioSystem.getClip();
							AudioInputStream ain;
							ain = AudioSystem.getAudioInputStream(new File(whitePianoNotes[(int)(normalized.getX()*7)]));
							clip.open(ain);
		 */
		
		frame.add(panel);
		frame.setVisible(true);
	}
	public void setEverything(FingerList f, InteractionBox b){
		fingers = f;
		box = b;
	}
	
	private void draw(Graphics g){ //Draw stuff here
		for(int i=0;i<rings.size();i++){
			if(rings.get(i).killself){
				rings.remove(i);
				i--;
			}
		}
		int w = 30;
		int[] previousFingers = new int[5];
		keyboard.draw(g);
		
		if(fingers != null){
			for(int j = 0; j < 7; j++)
			{
				keyboard.whitePressed[j] = false;
				if(j < 5)
				{
					keyboard.blackPressed[j] = false;
				}
			}
			for(int i = 0; i < fingers.count(); i++){
				Bone fingerTip = fingers.get(i).bone(Bone.Type.TYPE_DISTAL);
				float xTip = (float) (fingerTip.center().getX() + (fingerTip.length()/2.0) *  fingerTip.direction().getX());
				float yTip = (float) (fingerTip.center().getY() + (fingerTip.length()/2.0) *  fingerTip.direction().getY());
				float zTip = (float) (fingerTip.center().getZ() + (fingerTip.length()/2.0) *  fingerTip.direction().getZ());
				Vector tipPos = new Vector(xTip, yTip, zTip);
				Vector normalized = box.normalizePoint(tipPos);
				w = (int)(1/(normalized.getY()) * 10);
				int keyIndex = (int)(normalized.getX()*7);
				if (w > 70)
				{
					w = 70;
				}
				if(normalized.getX() > 0 && normalized.getX() < 1 && normalized.getZ() > 0 && normalized.getZ() < 1){//In the bounds
					if(normalized.getY() < .35)
					{
						if(normalized.getZ() > (3.0/5.0))
						{
							keyboard.whitePressed[keyIndex] = true;
							if(daft){
								whiteDaftNotes[keyIndex].start();
								if(timer%250==0)
									rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
							}else
								whitePianoNotes[keyIndex].start();
						}
						else
						{
							if(normalized.getX() > 2.0/21.0 && normalized.getX() < 4.0/21.0)
							{
								keyboard.blackPressed[0] = true;
								if(daft){
									blackDaftNotes[0].start();
									if(timer%250==0)
										rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
								}else
									blackPianoNotes[0].start();
							}
							else if(normalized.getX() > 5.0/21.0 && normalized.getX() < 7.0/21.0)
							{
								keyboard.blackPressed[1] = true;
								if(daft){
									blackDaftNotes[1].start();
									if(timer%250==0)
										rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
								}else
									blackPianoNotes[1].start();
							}
							else if(normalized.getX() > 11.0/21.0 && normalized.getX() < 13.0/21.0)
							{
								keyboard.blackPressed[2] = true;
								if(daft){
									if(timer%250==0)
										rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
									blackDaftNotes[2].start();
								}else
									blackPianoNotes[2].start();
							}
							else if(normalized.getX() > 14.0/21.0 && normalized.getX() < 16.0/21.0)
							{
								keyboard.blackPressed[3] = true;
								if(daft){
									if(timer%250==0)
										rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
									blackDaftNotes[3].start();
								}else
									blackPianoNotes[3].start();
							}
							else if(normalized.getX() > 17.0/21.0 && normalized.getX() < 19.0/21.0)
							{
								keyboard.blackPressed[4] = true;
								if(daft){
									if(timer%250==0)
										rings.add(new Ring((int)(normalized.getX()*798),(int)(normalized.getZ()*600)));
									blackDaftNotes[4].start();
								}else
									blackPianoNotes[4].start();
							}
						}
						g.setColor(Color.GREEN);
						g.fillOval((int)(normalized.getX()*798-w/2.0), (int)(normalized.getZ()*600-w/2.0), w, w);
					}
					else{
						g.setColor(Color.RED);
						g.fillOval((int)(normalized.getX()*798-w/2.0), (int)(normalized.getZ()*600-w/2.0), w, w);
					}
					/*if(normalized.getY() > .35 && keyboard.whitePressed[keyIndex]== true){//Pressing Key
						keyboard.whitePressed[keyIndex]= false;
					}*/
				}
			}
			for(int j = 0; j < 7; j++)
			{
				if(keyboard.whitePressed[j] == true){
					//whitePianoNotes[j].start();
				}
				else{
					if(daft){
						whiteDaftNotes[j].stop();
						whiteDaftNotes[j].setMicrosecondPosition(0);
					}else{
						whitePianoNotes[j].stop();
						whitePianoNotes[j].setMicrosecondPosition(0);
					}
				}
				if(j < 5)
				{
					if(keyboard.blackPressed[j] == true){
						//whitePianoNotes[j].start();
					}
					else{
						if(daft){
							blackDaftNotes[j].stop();
							blackDaftNotes[j].setMicrosecondPosition(0);
						}else{
							blackPianoNotes[j].stop();
							blackPianoNotes[j].setMicrosecondPosition(0);
						}
					}
				}
			}
		}
		for(Ring r:rings){
			r.draw(g);
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar()=='m'){
			daft = !daft;
		}
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
