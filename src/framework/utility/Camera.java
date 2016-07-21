package framework.utility;

import code.solarsystem.SolarSystemScene;
import framework.engine.*;
import framework.interfaces.*;
import processing.core.*;

/**
 * This class implements the base Camera functionality. It controls the position and view direction
 * of the camera in your {@link Scene}. You may add functionality by creating a new class that inherits
 * {@code Camera}, e.g. {@code public class MyCamera extends Camera}. You should <b>not</b> edit this class directly.
 * <p>
 * Since {@code Camera} implements the interface {@link Input}, you will not need to explicitly add
 * this to your subclass. Similarly, it is not necessary to define all {@code Input} handling functions
 * in your subclass.
 * @author wil
 * @version 1.2.1
 * <br>8 Feb 2016
 */
public class Camera implements Input{
	/**
	 * Rendering window to which your {@link Camera} is part of. You are controlling the viewing properties of this {@link Scene}.
	 * <p>
	 * Contains Processing links with OpenGL. All calls to the renderer must be made directly through parent.
	 */
	private Scene parent;
	/**
	 * Vectors containing eye coordinates, i.e. the position of the camera.
	 * <p>
	 * Position in space that the camera is looking at equals {@code eye+}{@link #view}.
	 */
	protected PVector eye;
	/**
	 * The vector along which the camera are looking.
	 * <p>
	 * Position in space that the camera is looking at equals {@link #eye}{@code +view}.
	 */
	protected PVector view;
	/**
	 * Vector representing one of the orthogonal axes of your {@link Camera} space.
	 */
	protected PVector forward, right, up;
	/**
	 * State of key press.
	 */
	private int wKey = 0, aKey = 0, sKey = 0, dKey = 0;
	/**
	 * Current button pressed (LEFT, RIGHT or CENTER) or 0 if no button pressed.
	 */
	private int button = 0;
	/**
	 * Previous mouse coordinates.
	 */
	private int pmouseX = 0, pmouseY = 0; 
/////// Camera Functions ////////////////////////////////////////////
	/**
	 * Constructor for {@link Camera} to set up viewing properties in rendering window.
     * @param parent Your {@link Scene}.
	 */
	public Camera(Scene parent){
		this.parent = parent;		// Set renderer
		// Initialise vectors
		eye = new PVector();
		view = new PVector();
		forward = new PVector();
		right = new PVector();
		up = new PVector();
		reset();					// Set default camera properties
	}
	
	public PVector getEye(){
		return eye;
	}
	
	public float getEyeAngle(){
		return 0;
	}
	
	/**
	 * Called by {@link Scene} {@code parent} to position camera.
	 * <p>
	 * Sets up position ({@link #eye}), look at ({@code cen=} {@link #eye} {@code +} {@link #view}) and
	 * up vector ({@link #up}) and updates Scene viewing.
	 * @see #update(float)
	 * @see #reset()
	 */
	public void setup() {
		PVector cen = PVector.add(eye,view);	// calculate point camera is facing
		// set look-at properties
		parent.camera(eye.x, eye.y, eye.z,		// camera position
					  cen.x, cen.y, cen.z,		// centre position
			          up.x, up.y, up.z);		// up direction
		
	}
	/**
	 * Update the position of the camera and look-at vectors based on keyboard input.
	 * @param dT (float) change in time since previous call (unused)
	 */
	public void update(float dT) {
		float speed = 4.f;										// Sets default speed of movement
		calculateVectors();										// Ensures axial vectors are updated
		
		if (aKey == 1){											// If 'a' is pressed
			eye = PVector.sub(eye, PVector.mult(right, speed));		// Move left (eye - right)
		}
		if (dKey == 1){											// If 'd' is pressed
			eye = PVector.add(eye, PVector.mult(right, speed));		// Move right (eye + right)
		}
		if (wKey == 1){											// If 'w' is pressed
			eye = PVector.add(eye, PVector.mult(forward,speed));	// Move forward (eye + forward)
		}
		if (sKey == 1){											// If 's' is pressed
			eye = PVector.sub(eye, PVector.mult(forward, speed));	// Move backward (eye - forward)
		}
	}
////// Directional Axes Methods /////////////////////////////////////
	/**
	 * Resets {@link Camera} vectors to default values. Sets position of camera at (0,0) in x,y-plane
	 * and puts z-position at {@code 0.5*height/tan(π/6)} which puts the coordinate width and height of window
	 * into view (if projection is in perspective view, and both {@code fovy = 60}&deg; and {@code aspect =  width/height})
	 * <p>
	 * {@code width} and {@code height} refer to the window size of {@code Scene} {@link #parent}.
	 */
	public void reset(){
		//eye.set(0.f,0.5f,-0.5f);
		eye.set(0.f,0.f,0.f);									// Initially situate at origin
		eye.z = 0.5f*parent.height/PApplet.tan(PI/6.f);			// Default camera radius (depth)
		view.set(0.f,0.f,-1.f);									// Set view direction forward
		forward.set(0.f,0.f,-1.f);								// Forward axis follows view direction
		right.set(1.f,0.f,0.f);									// Right axis initially right along world coordinates
		up.set(0.f,1.f,0.f);									// Up is up
	}
	
	/**
	 * Calculates planar camera axes based on view direction.
	 * <p>
	 * {@link #up} points up in {@code y}-direction, {@link #forward} follows {@link #view} direction along
	 * the {@code y}-plane and {@link #right} is the cross product of {@code up} and {@code forward}.
	 * The vectors are orthonormal.
	 * <pre>
	 * 	up  forward
	 * 	|  /
	 * 	| /
	 * 	|/_ _ _ right
	 * </pre>
	 * It is strongly recommended you do NOT attempt to override this method.
	 * @see #reset()
	 */
	protected final void calculateVectors(){
		up.set(0.f,1.f,0.f);			// up is always up in rendering window
		
		forward.set(view.x,0.f,view.z);	// forward vector always follows view direction flat on z-plane
		forward.normalize();			// normalise forward vector so magnitude is 1.0
		
		right = forward.cross(up);		// right is cross product of forward and up
		right.normalize();				// normalise right vector so magnitude is 1.0
	}
/////// Input handling functions ////////////////////////////////////
	/**
	 * Captures input from {@code wasd}-keys used for camera movement.
	 * <p>
	 * Spacebar {@linkplain #reset() reset}s the camera.
	 */
	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		switch(key){
			case 'W':
			case 'w':
				wKey = state;			// 'w' key is pressed up (0) or down (1)
				break;
			case 'A':
			case 'a':
				aKey = state;			// 'a' key is pressed up (0) or down (1)
				break;
			case 'S':
			case 's':
				sKey = state;			// 's' key is pressed up (0) or down (1)
				break;
			case 'D':
			case 'd':
				dKey = state;			// 'd' key is pressed up (0) or down (1)
				break;
			case ' ':					// spacebar
				reset();				// reset to default camera position
				break;

		}
	}
	/**
	 * Captures button click. Sets button to 0 if last mouse button is released.
	 * Saves current position of mouse at click.
	 * @see #handleMouseDrag(int, int)
	 */
	@Override
	public void handleMouse(int button, int state, int mX, int mY) {
		if (state == 1)					// if mouse pressed down
			this.button = button;			// capture most recent mouse button
		else if (state == 0 && button == this.button)	// if current mouse button is released
			this.button = 0;			// set null
		pmouseX = mX;					// save current position of mouse
		pmouseY = mY;
	}
	/**
	 * Called when mouse is moved (while a button is pressed down). Functionality currently only for <b>LEFT</b> mouse click.
	 * <p>
	 * Calculates difference in mouse position since last call and adjusts camera {@link #view} accordingly. Sensitivity is fixed at 0.01f.
	 * @see #handleMouse(int, int, int, int)
	 */
	@Override
	public void handleMouseDrag(int mX, int mY) {
		if(button != LEFT) return;		// if any other button but left clicked, return
		float dL, sens = 0.01f;			// variables: look difference and sensistivity
		
		dL = (mX - pmouseX) * sens;		// if moved left, dL is -ve. if right, dL is +ve
		view.add(PVector.mult(right,dL));	// adjust view by dL*right vector
		
		dL = (mY - pmouseY) * sens;		// if moved up, dL is +ve. if down, dL is -ve
		view.y += dL;						// adjust view by dL*up(=(0,1,0)) vector
		
		calculateVectors();				// update camera axes
		pmouseX = mX;					// save current position of mouse
		pmouseY = mY;
	}
///// Interface Methods (unused) ////////////////////////////////////
	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) {	}
	@Override
	public void handleMouseMove(int mX, int mY) { }
}
