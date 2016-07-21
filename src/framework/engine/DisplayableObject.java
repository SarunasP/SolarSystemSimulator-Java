/**
 * 
 */
package framework.engine;

import processing.core.*;
/**
 * Abstract class to be inherited by all objects to be displayed in Scene
 * <p>
 * Contains abstract {@link #display} method that must be overloaded. {@link #display()} is called from a parent {@link Scene}.
 * @author wil
 * @version 1.0.0
 * <br>11 Sep 2015
 */
public abstract class DisplayableObject {
	/**
	 * Rendering window to display this {@link DisplayableObject} in.
	 * <p>
	 * Contains Processing links with OpenGL. All calls to the renderer must be made directly through parent.
	 */
    protected Scene parent;
    /**
     * {@link PVector} containing World Space position coordinates
     */
    protected PVector pos = new PVector(0.f,0.f,0.f);
    /**
     * {@link PVector} containing relative World Space scaling values for x,y,z.
     */
    protected PVector scale = new PVector(1.f,1.f,1.f);
    /**
     * {@link PVector} containing angles of orientation in World Space for x, y, and z axes
     */
    protected PVector rotation = new PVector(0.f,0.f,0.f);
    /**
     * Constructor for DisplayableObject that sets up link to rendering window commands.
     * <p>
     * Abstract class so constructor should only be called by sub-class via super({@link #parent})
     * @see DisplayableObject
     * @param parent Your {@link Scene}.
     */
    public DisplayableObject(Scene parent){ this.parent = parent; }   // Constructor
    /**
     * Abstract class. Called from Scene parent
     */
    public abstract void display();                                     // display function (MUST OVERLOAD)
    /**
     * Set position (displacement) in World Space
     * @see #position()
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public void position(float x, float y, float z){ pos.set(x,y,z); }  // set World Space position
    /**
     * Set size in World Space
     * <p>
     * Equally scaled in all axes
     * @see #size(float sx, float sy, float sz)
     * @see #size()
     * @param s Scale in x,y,z axes
     */
    public void size(float s){
    	size(s,s,s);
    }
    /**
     * Set size in World Space
     * <p>
     * Specify separate scales for each axis
     * @see #size(float s)
     * @see #size()
     * @param sx Scale in x axis
     * @param sy Scale in y axis
     * @param sz Scale in z axis
     */
    public void size(float sx, float sy, float sz){ scale.set(sx,sy,sz); } 
    /**
     * Set orientation in World Space
     * <p>
     * Specify angles for x, y and z axial rotations
     * @see #orientation()
     * @param rx Angle of rotation around x-axis
     * @param ry Angle of rotation around y-axis
     * @param rz Angle of rotation around z-axis
     */
    public void orientation(float rx, float ry, float rz){ rotation.set(rx,ry,rz); }
    /**
     * Get position in World Space
     * @see #position(float x, float y, float z)
     * @return PVector containing position coordinates
     */
    public PVector position(){ return pos; }
    /**
     * Get size in World Space
     * @see #size(float s)
     * @see #size(float sx, float sy, float sz)
     * @return PVector containing individual axis scales
     */
    public PVector size(){ return scale; }
    /**
     * Get magnitude of object scale.
     * @return float containing magnitude norm of scale values
     */
    public float scale(){ return scale.dist(new PVector(0.f,0.f,0.f)); };
    /**
     * Get orientation in World Space
     * @see #orientation(float rx, float ry, float rz)
     * @return PVector containing angles of rotation around each axis
     */
    public PVector orientation(){ return rotation; }
    /**
     * Transform object to its relative position and orientation.
     * <p>
     * Call this function at the beginning of {@link display}.
     */
    public void transform(){
    	parent.translate(pos.x, pos.y, pos.z);
    	parent.rotateY(rotation.y);
    	parent.rotateZ(rotation.z);
    	parent.rotateX(rotation.x);
    	parent.scale(scale.x,scale.y,scale.z);
    	parent.strokeWeight(1.f/scale());
    }
}