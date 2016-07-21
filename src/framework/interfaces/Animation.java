/**
 * 
 */
package framework.interfaces;

/**
 * Interface to be implemented by all animated objects to be displayed in a Scene
 * <p>
 * Contains {@link #update} method that must be overloaded. {@link #update(float dT)} is called from a parent {@link framework.engine.Scene}.
 * 
 * @author wil
 * @version 1.2.1
 * <br>8 Feb 2016
 */
public interface Animation {
	/**
	 * Called each frame to update
	 * <p>
	 * Use this to update animation sequence.
	 * @param dT change in time since previous call
	 */
	void update(float dT);
}