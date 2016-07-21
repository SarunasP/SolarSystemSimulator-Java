/**
 * 
 */
package code;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;
import framework.interfaces.Lighting;
import processing.core.PVector;

/**
 * @author Wil
 *
 * Mainly built from the demo code provided to us,
 * however with relevant adjustments to simulate "sun" light
 */
public class Light extends DisplayableObject implements Animation, Lighting {
	/* Lighting properties */
	float diffuse[]  = new float[3];
	float specular[] = new float[3];
	
	private boolean debugDraw = false;
	
	
	/**
	 * @param parent
	 */
	public Light(Scene parent, PVector position) {
		super(parent);
		
		diffuse[0] = 204.f;
		diffuse[1] = 200.f;
		diffuse[2] = 200.f;
		
		specular[0] = 255.f;
		specular[1] = 255.f;
		specular[2] = 255.f;
		
		this.pos = position;
	}

	@Override
	public void setupLighting() {
		
		/* Sets the specular property of lights created
		 * IMPORTANT: light properties, such as lightSpecular affect ALL lights generated after
		 * so they must be reset at the end of the call.
		 */
		parent.lightSpecular(specular[0],specular[1],specular[2]); 
		parent.pointLight(diffuse[0],diffuse[1],diffuse[2],-pos.x,-pos.y,-pos.z);		
		parent.lightSpecular(0,0,0); // Reset specular light property!
	}

	@Override
	public void update(float dT) {
	}

	@Override
	public void display() {
		// Some basic code to draw the position and direction of the light
		// Not suitable for a directional light source, as directional light has no positon.
		if (debugDraw){
			// Disable lighting effects on this geometry
			parent.noLights();
				parent.pushMatrix();
				parent.pushStyle();
				// Style light source to match diffuse colour
					parent.fill((int)diffuse[0],(int)diffuse[1],(int)diffuse[2]);
					parent.noStroke();
					parent.translate(pos.x,pos.y,pos.z); // positon
					// Create sphere to represent light source
					parent.sphereDetail(10, 10);
					parent.sphere(10);
					// Create line indicating lighting direction
					parent.beginShape(Scene.LINES);
						parent.stroke((int)diffuse[0],(int)diffuse[1],(int)diffuse[2]);
						parent.vertex(0.f,0.f,0.f);
						parent.vertex(-pos.x,-pos.y,-pos.z);
					parent.endShape();
				parent.popStyle();
				parent.popMatrix();
			parent.lights(); // !IMPORTANT! Renable lighting after this
		}
	}

	public void setDebugDraw( boolean draw ){
		debugDraw = draw;
	}
}
