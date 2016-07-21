package code.solarsystem;

import processing.core.*;
import framework.engine.*;
import framework.utility.Camera;
import code.Light;

/**
 * Scene that displays an almost complete solar system. 
 * @author {your_name}
 * @version 1.0.0
 */
public class SolarSystemScene extends Scene {
	
	/**
	 * Expose the camera object for the CelestialBackground object.
	 * @return
	 */
	
	static int viewType = 1;
	
	public Camera getCamera(){
		return camera;
	}
	
	/**
	 * Setup the Solar System, lighting and the celestial background.
	 */
	@Override
	public void initialise(){			
		setBackgroundColour(0.f,0.f,0.f,1.f);	// set clear colour to black
		
		SolarSystem solarSystem = new SolarSystem(this);		
		solarSystem.position(0.f, 250.f, 0.f);
		addObjectToScene( solarSystem, "csun");

		float distance = 10.f;
		Light l = new Light(this, new PVector(distance/2.f, 500.f, -distance/2.f));
		addObjectToScene(l);	
		
		Light l2 = new Light(this, new PVector(-distance/2.f, 500.f, -distance/2.f));
		addObjectToScene(l2);
		
		Light l3 = new Light(this, new PVector(0.f, 500.f, distance));
		addObjectToScene(l3);
		
		CelestialBackground background = new CelestialBackground(this, 3000.f);
		background.setLighted(false);
		addObjectToScene(background, "background");
				
		// Added an argument to initialise function to be able to add my own camera
		super.initialise( new CelestialCamera(this) ); 
	}
	
	@Override
	protected void globalLighting(){
		//super.globalLighting();		// DISABLE GLOBAL LIGHTING WHEN IMPLEMENTING OWN
		//ambientLight(100.f,100.f,100.f);
	}
	
	/**
	 * Override default reshape function. Called during every iteration of {@link #draw()}.
	 * Use this method to handle resizing objects based on your window size.
	 * @see #getObject(String)
	 * @see #projection()
	 */
	protected void reshape(){
		super.reshape();
	}
	
	/**
	 * Override default initial window size (600x400). Adjust variables in {@code super} class to change values.
	 */
	@Override
	protected void setInitWindowSize(){
		super.initWidth = 1200;	// must override variables in super class to affect size
		super.initHeight = 700;
	}
	
	/**
	 * Override projection properties here. Remove call to {@code super.projection()} and replace with
	 * perspective mode.
	 * @see #perspective(float, float, float, float)
	 * @see #ortho(float, float, float, float, float, float)
	 * @see #frustum(float, float, float, float, float, float)
	 */
	@Override
	protected void projection(){
		//super.projection();	// calls default projection setup in Scene (orthographic)
		//perspective(radians(60.f),(float)width/(float)height, 1.f, 5000.f);
		if (viewType == 1){
			perspective(radians(60.f),(float)width/(float)height, 1.f, 5000.f);
		} else if (viewType == 0){
			//ortho(radians(60.f),(float)width/(float)height, 1.f, 5000.f);
			ortho(-width/2.f,width/2.f,-height/2.f,height/2.f,1.f,5000.f);     // orthographic

		}
	}
	
	/**
	 * If statements to set values for two different viewingTypes (perspective and ortho)
	 */
	public static void changeViewingType(int view){
		if (view == 1){
			viewType = 0;
		} else if (view == 0){
			viewType = 1;
		}
	}
	
}
