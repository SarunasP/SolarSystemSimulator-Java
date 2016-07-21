package code.solarsystem;

import framework.engine.Scene;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

/**
 * Represents a visible celestial object, e.g. a sun or a planet.
 * Has options for displaying the object with its ring or to display
 * its orbit.
 * Some objects emmit light so it is easier not to use lighing on them.
 */
public class CelestialObject extends CelestialHierarchy {
	
	// The actual 3D object representing the celestial object
	PShape sphere = null;
	
	// Name, determines image/object file names
	String name = "";
	
	// Distance from the centre of the hierarchy
	float orbitRadius;	
	// Current angle of rotation along the orbit axis (Y)
	float orbitRotation;
	// Speed of orbiting
	float orbitRotationSpeed;
	
	// Current angle of rotation along own axis
	float axisRotation;
	// Speed of rotation
	float axisRotationSpeed;
	
	// Colour of an untextured sphere and of the orbit, default is white
	int colour;
	
	// Tells if lighting is applied to this object a sun or a background will have this false
	boolean isLighted = true;
	
	// Used for planets that have rings, used for Saturn
	boolean hasRings = false;
	// Texture is loaded using the celestial object name member
	PImage ringTexture = null;
	
	// Toggles simple orbit drawing
	boolean drawOrbit = false;
	// A shared ellipsis shape used to draw orbits
	static PShape orbit = null;
	
	public CelestialObject(Scene parent, float orbitRadius, float size, String name){
		super(parent);
		setOrbitRadius(orbitRadius);
		size(size);
		this.name = name;
		setColour(255, 255, 255);
		
		// Try loading the object. Path is absolute due to errors
		// in loading material files when relative.
		try{
			sphere = parent.loadShape("data/"+name+".obj");
		}catch(Exception e){
			sphere = null;
		}
		
		// Initial setup of the static member
		if( orbit == null ){
			orbit = createOrbitRing(parent);
		}
	}
	
	/**
	 * When rings are activated, celestial object will try to load
	 * the ring texture (not before).
	 */
	public void setHasRings( boolean hasRings ){
		this.hasRings = hasRings;
		if( hasRings ){
			ringTexture = parent.loadImage("data/" + name + "_rings.png");
		}else{
			ringTexture = null;
		}
	}
	
	/**
	 * Will draw orbit if set to true.
	 * @param drawOrbit
	 * @param suborbits If true, will set drawOrbit on the whole hierarchy.
	 */
	public void setDrawOrbit( boolean drawOrbit, boolean suborbits ){
		this.drawOrbit = drawOrbit;
		if( suborbits ){
			for( CelestialHierarchy cHierarchy : system ){
				if( cHierarchy instanceof CelestialObject ){
					((CelestialObject)cHierarchy).setDrawOrbit(drawOrbit, suborbits);
				}
			}
		}
	}
	
	public boolean getDrawOrbit(){
		return drawOrbit;
	}
	
	/**
	 * Setup orbit/axis angles with random values. To make it more interesting.
	 */
	public void randomiseInitialAngles(){
		setOrbitRotation( (float)(Math.random() * Math.PI * 2.0f) );
		setAxisRotation( (float)(Math.random() * Math.PI * 2.0f) );
	}
	
	/**
	 * A shorthand for randomising initial orbiting/rotating speeds.
	 */
	public void randomiseInitialSpeeds(){
		randomiseInitialSpeeds(1.0f, false);
	}
	
	/**
	 * Will setup orbiting and rotating speeds with random values.
	 * @param factor PI angular speed factor.
	 * @param sameDirection If true, all speeds will be generated positive.
	 */
	public void randomiseInitialSpeeds( float factor, boolean sameDirection ){		
		double randomOne = sameDirection ? Math.random() : ((Math.random()-0.5f) * 2.0f);
		double randomTwo = sameDirection ? Math.random() : ((Math.random()-0.5f) * 2.0f);
		
		setOrbitRotationSpeed( (float)(randomOne * Math.PI * factor) );
		setAxisRotationSpeed( (float)(randomTwo * Math.PI * factor) );
	}
	
	/**
	 * Propagate random speed generation in the whole hierarchy.
	 */
	public void hierarchyRandomSpeed( ){
		hierarchyRandomSpeed(1.0f, false);
	}
	
	/**
	 * Setup random speeds in the whole hierarchy.
	 * @param factor Parameter for randomiseInitialSpeeds.
	 * @param sameDirection Parameter for randomiseInitialSpeeds.
	 */
	public void hierarchyRandomSpeed( float factor, boolean sameDirection ){
		randomiseInitialSpeeds(factor, sameDirection);
		for( CelestialHierarchy cHierarchy : system ){
			if( cHierarchy instanceof CelestialObject ){
				((CelestialObject)cHierarchy).hierarchyRandomSpeed(factor, sameDirection);
			}
		}
	}
	
	@Override
	public void update(float dT) {
		super.update(dT);	
		
		axisRotation += axisRotationSpeed*dT;
		if (axisRotation > Scene.TWO_PI)
			axisRotation -= Scene.TWO_PI;
		
		orbitRotation += orbitRotationSpeed*dT;
		if (orbitRotation > Scene.TWO_PI)
			axisRotation -= Scene.TWO_PI;
	}
	
	@Override
	public void display() {	
		hierarchicalDisplay();
	}

	/**
	 * Displays the orbit if needed.
	 * Displays hierarchy elements (when offset along the orbit).
	 * Displays the actual represented sphere/object.
	 * Displays rings if needed.
	 */
	protected void hierarchicalDisplay(){	

		if( drawOrbit ){
			parent.pushMatrix();
			
			// Position on the location of the planet
			parent.translate(pos.x,pos.y,pos.z);
			parent.translate(-orbitRadius, 0.f, -orbitRadius);

			// The generated ellipsis is rotated along the X-axis
			parent.rotateX(Scene.HALF_PI);
			// Scale to the orbit size
			parent.scale(orbitRadius * 2);
			orbit.setStroke(colour);
			parent.shape(orbit);
			
			parent.popMatrix();
		}
				
		parent.pushMatrix();
		parent.pushStyle();
			
			// Translate the system in its position
			parent.translate(pos.x,pos.y,pos.z);
			parent.rotateY(orbitRotation);
			parent.translate(-orbitRadius, 0.f, 0.0f);
			
			// Display remainder of the hierarchy before we draw the planet/sun sphere
			super.hierarchicalDisplay();

			// Turn off lighting for unlighted celestial objects
			if( !isLighted ){
				parent.noLights();
			}		
			parent.pushMatrix();
			
			// Apply axis rotations
			parent.rotateY(axisRotation * 2);
			parent.scale(scale.x,scale.y,scale.z);
			
			// Draw either the loaded 3D object or use the default sphere drawing
			if( sphere != null ){
				parent.shape(sphere);
			}else{
				// Default sphere drawing does not support texturing!
				parent.fill(colour);
				parent.noStroke();
				parent.sphereDetail(10, 10);
				parent.sphere(1);
			}
			parent.popMatrix();
			
			// Turn on the lights again for other systems
			if( !isLighted ){
				parent.lights();
			}
			
			// Draw rings when needed and when the texture exists
			if( hasRings && ringTexture != null ){
				// Rings are a 2D texture, lighting seems to mess things up not sure why
				if( isLighted ){
					parent.noLights();
				}
				// Ring is a properly aligned, textured quad
				parent.beginShape(Scene.QUADS);
				{
					parent.texture(ringTexture);
					float width = size().x * 2;

					parent.noStroke();
					parent.normal(0.f,0.f,0.f);
					
					parent.vertex(width, 0.f, width, 1.0f, 1.0f);    // Vertex coordinate of the top left of the quad
					parent.vertex(width, 0.f, -width, 1.0f, 0.0f);       // Vertex coordinate of the bottom left of the quad
					parent.vertex(-width, 0.f, -width, 0.0f, 0.0f);        // Vertex coordinate of the bottom right of the quad			
					parent.vertex(-width, 0.f, width, 0.0f, 1.0f);     // Vertex coordinate of the top right of the quad
				}			
				parent.endShape();
				if( isLighted ){
					parent.lights();
				}
			}
			
		parent.popStyle();
		parent.popMatrix();	
		
	}
	
	/**
	 * Will create a simple unit circle.
	 * @param parent A scene object needed to create the ellipse.
	 * @return The Circle shape.
	 */
	static private PShape createOrbitRing( Scene parent ){
		parent.sphereDetail(100);
		PShape orbitRing = parent.createShape(PApplet.ELLIPSE, 0.5f, 0.5f, 1.f, 1.f );
		orbitRing.setFill(false);
		orbitRing.setStroke( parent.color(230, 230, 230));
		orbitRing.setStrokeWeight(1f);
		orbitRing.set3D(true);
		return orbitRing;
	}
		
	public void setOrbitRadius(float r) { 
		this.orbitRadius = r; 
	}

	public void setLighted(boolean isLighted) {
		this.isLighted = isLighted;
	}

	public void setAxisRotation(float axisRotation) {
		this.axisRotation = axisRotation;
	}

	public void setOrbitRotation(float orbitRotation) {
		this.orbitRotation = orbitRotation;
	}

	public void setAxisRotationSpeed(float axisRotationSpeed) {
		this.axisRotationSpeed = axisRotationSpeed;
	}

	public void setOrbitRotationSpeed(float orbitRotationSpeed) {
		this.orbitRotationSpeed = orbitRotationSpeed;
	}
	
	public void setColour(int r, int g, int b) { 
		this.colour = parent.color(r,g,b); 
	}


}
