package code.solarsystem;

import framework.engine.Scene;
import framework.utility.Camera;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Camera specialization that enables movement up
 * and down. Initial setup of the view is with a downward 45 degree angle.
 */
public class CelestialCamera extends Camera {
	
	private int qKey = 0;
	private int eKey = 0;

	public CelestialCamera(Scene parent) {
		super(parent);
		view.x = 0.0f;
		view.y = 0.5f;
		view.z = -0.5f;
		view.normalize();
		calculateVectors();
	}
	
	
	public void birdsEyeView(){
		eye.set(0.f,-1000.f,0.f);				// set high on the Y Axis, to simulate top-down view					
		//eye.z = 0.5f*parent.height/PApplet.tan(PI/6.f);			
		view.set(0.f,5.f,-1.f);									
		forward.set(0.f,0.f,-1.f);								
		right.set(1.f,0.f,0.f);									
		up.set(0.f,0.f,0.f);									
	}
	
	/**
	 * Adds the keys to change the view, either through Q and E which move 
	 * camera up or down or reset, which resets the view when space is pressed, 
	 * birdsEyeView which looks from the top when 1 is pressed,
	 * and 2 and 3 toggle between perspective and orthographic
	 */
	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		super.handleKey(key, state, mX, mY);
		switch(key){
			case 'Q':
			case 'q':
				qKey = state;
				break;
			case 'E':
			case 'e':
				eKey = state;
				break;
			default:
				break;
			case ' ':					// spacebar
				reset();				// reset to default camera position
				break;
			case '1':
				birdsEyeView();         // look from the top down, in "birds eye view" 
				break;
			case '2':
				SolarSystemScene.changeViewingType(0);  // ortho projection
				break;
			case '3':
				SolarSystemScene.changeViewingType(1);  // perspective projection
				break;
		}
	}
	
	/**
	 * Moves camera eye up or down depending on the state of the keys q and e
	 */
	public void update(float dT) {
		float speed = 4.f;		
		super.update(dT);
		
		if (qKey == 1){											
			eye = PVector.sub(eye, PVector.mult(up, speed));
		}
		if (eKey == 1){											
			eye = PVector.add(eye, PVector.mult(up, speed));
		}
	}

}
