
//Eric Velosky
import java.io.IOException;
import com.leapmotion.leap.*;
import java.awt.Point;
import java.awt.geom.Point2D;

public class HelloWorld{
	static Visualizer visual;
	static Keyboard keyboard;
	public static void main(String[] args) {
		keyboard = new Keyboard();
		visual = new Visualizer(keyboard);
		// Create a sample listener and controller
		SampleListener listener = new SampleListener();
		Controller controller = new Controller();

		// Have the sample listener receive events from the controller
		controller.addListener(listener);
		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// Remove the sample listener when done
		controller.removeListener(listener);
		
	}

	static class SampleListener extends Listener {

		public void onConnect(Controller controller) {
			System.out.println("Connected");
		}

		public void onFrame(Controller controller) {
			//System.out.println("Frame available");
			Frame frame = controller.frame();
			InteractionBox box = frame.interactionBox();
			//System.out.println("Frame id: " + frame.id() + ", timestamp: " + frame.timestamp() + ", hands: "
				//	+ frame.hands().count() + ", fingers: " + frame.fingers().count());
			//System.out.println("weens: " + frame.fingers());
			FingerList fingers = frame.fingers();
			//for drawing everything
			visual.setEverything(fingers, box); //Kreygasm
			//System.out.println("fingers box");
			for(Finger f : fingers){
				Bone fingerTip = f.bone(Bone.Type.TYPE_DISTAL);
				float xTip = (float) (fingerTip.center().getX() + (fingerTip.length()/2.0) *  fingerTip.direction().getX());
				float yTip = (float) (fingerTip.center().getY() + (fingerTip.length()/2.0) *  fingerTip.direction().getY());
				float zTip = (float) (fingerTip.center().getZ() + (fingerTip.length()/2.0) *  fingerTip.direction().getZ());
				Vector tipPos = new Vector(xTip, yTip, zTip);
				//System.out.println(tipPos);
				Vector normalized = box.normalizePoint(tipPos);
				if(normalized.getY()<.25){ //if finger is pressing down
					//playNote((int) (normalized.getX()*6));

				}else{
					//System.out.println("NotPressing");
				}
			}
		}
	}

}
