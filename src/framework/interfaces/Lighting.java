/**
 * 
 */
package framework.interfaces;

/**
 * Interface for giving an coursework object lighting properties. Any class you want to have a light source, e.g. a Torch
 * should implement the {@code Lighting} interface. e.g. {@code public Class implements Lighting} 
 * <p>
 * In {@link framework.engine.Scene Scene}, {@link #setupLighting()} will be called on any {@linkplain framework.engine.DisplayableObject DisplayableObject}
 * added to the scene that {@code implements Lighting}, from the {@linkplain framework.engine.Scene#lights() lights()} method.
 * @author wil
 * @version 1.0.0
 * <br>11 Sep 2015
 */
public interface Lighting {
	/**
	 * Contains all lighting commands for your object. Called via {@link framework.engine.Scene#lights()}. Remember to give lighting positions in terms
	 * of World Space.
	 */
	void setupLighting();
}
