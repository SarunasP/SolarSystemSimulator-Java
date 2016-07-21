package code.solarsystem;

import framework.utility.Camera;
import processing.core.PVector;

/**
 * A celestial object specialisation that represents the 
 * starry background. This object follows the camera movement
 * so that there is an appearance of distance.
 */
public class CelestialBackground extends CelestialObject {
	
	public CelestialBackground(SolarSystemScene parent, float size) {
		super(parent, 0.0f, size, "background");
	}

	/**
	 * Grabs the current camera from what is assumed is MyScene
	 * and adjusts position accordingly.
	 */
	@Override
	public void update(float dT) {
		// TODO Scene class should be refactored to accommodate this.
		Camera camera = ((SolarSystemScene)parent).getCamera();
		if( camera != null ){
			PVector camPos = camera.getEye();
			this.position(camPos.x, camPos.y, camPos.z);	
		}
	}

}
