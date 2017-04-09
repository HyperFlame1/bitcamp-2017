import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.leapmotion.leap.*;

public class Visualizer{
	static final int width = 798;
	static final int height = 600;
	final float fps = 60;
	final float spf = 1000f/fps;
	long timer;
	Clip[] whitePianoNotes = new Clip[7];
	Clip[] blackPianoNotes = new Clip[5];
	File file;
	JFrame frame;
	Keyboard keyboard;
	FingerList fingers = new FingerList();
	InteractionBox box = new InteractionBox();
	public Visualizer(Keyboard keyboard) {
		this.keyboard = keyboard;
		frame = new JFrame("Piano");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer = System.currentTimeMillis();
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
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
							whitePianoNotes[keyIndex].start();
						}
						else
						{
							if(normalized.getX() > 2.0/21.0 && normalized.getX() < 4.0/21.0)
							{
								keyboard.blackPressed[0] = true;
								blackPianoNotes[0].start();
							}
							else if(normalized.getX() > 5.0/21.0 && normalized.getX() < 7.0/21.0)
							{
								keyboard.blackPressed[1] = true;
								blackPianoNotes[1].start();
							}
							else if(normalized.getX() > 12.0/21.0 && normalized.getX() < 14.0/21.0)
							{
								keyboard.blackPressed[2] = true;
								blackPianoNotes[2].start();
							}
							else if(normalized.getX() > 15.0/21.0 && normalized.getX() < 17.0/21.0)
							{
								keyboard.blackPressed[3] = true;
								blackPianoNotes[3].start();
							}
							else if(normalized.getX() > 18.0/21.0 && normalized.getX() < 20.0/21.0)
							{
								keyboard.blackPressed[4] = true;
								blackPianoNotes[4].start();
							}
						}
						g.setColor(Color.GREEN);
						g.fillOval((int)(normalized.getX()*798-w/2), (int)(normalized.getZ()*600-w/2), w, w);
					}
					else{
						g.setColor(Color.RED);
						g.fillOval((int)(normalized.getX()*798-w/2), (int)(normalized.getZ()*600-w/2), w, w);
						/*for(Finger f : fingers)
						{
							if(!f.equals(fingers.get(i))){
								fingerTip = f.bone(Bone.Type.TYPE_DISTAL);
								xTip = (float) (fingerTip.center().getX() + (fingerTip.length()/2.0) *  fingerTip.direction().getX());
								yTip = (float) (fingerTip.center().getY() + (fingerTip.length()/2.0) *  fingerTip.direction().getY());
								zTip = (float) (fingerTip.center().getZ() + (fingerTip.length()/2.0) *  fingerTip.direction().getZ());
								tipPos = new Vector(xTip, yTip, zTip);
								normalized = box.normalizePoint(tipPos);
								int keyIndexOther = (int)(normalized.getX()*7);
								if((keyIndex == keyIndexOther)){
										whitePianoNotes[keyIndex].start();
										
								}
							}
						}*/
						//if(!(previousFinger == keyIndex))
							
						
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
					whitePianoNotes[j].stop();
					whitePianoNotes[j].setMicrosecondPosition(0);
				}
				if(j < 5)
				{
					if(keyboard.blackPressed[j] == true){
						//whitePianoNotes[j].start();
					}
					else{
						blackPianoNotes[j].stop();
						blackPianoNotes[j].setMicrosecondPosition(0);
					}
				}
			}
		}
	}
}
