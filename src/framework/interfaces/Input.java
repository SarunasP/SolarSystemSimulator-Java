/**
 * 
 */
package framework.interfaces;

import processing.core.*;
/**
 * Interface for giving an coursework object input handling. Any class you want to have input handling
 * should implement the {@code Input} interface. e.g. {@code public Class implements Input} 
 * <p>
 * Methods will be called from a {@link framework.engine.Scene Scene} renderer to give input from keyboard and mouse
 * @see framework.utility.Camera Camera
 * @author wil
 * @version 1.0.0
 * <br>11 Sep 2015
 */
public interface Input extends PConstants {
	/**
	 * Called when keyboard input is received from ASCII characters.
	 * <p>
	 * key constants: ENTER, RETURN, TAB, BACKSPACE, ESC, DELETE
	 * <p>
	 * Note: check for RETURN and ENTER for cross-platform operability.
	 * <p>
	 * Example implementation:
	 * <pre>
	 * 	void handleKey(char key, int state, int mX, int mY){
	 * 	    if (state == 1){ // if key pressed down
	 *	        switch(key){ // ASCII char
	 *	            case 'A':
	 * 	            case 'a':
	 * 	                parent.translate(-1.f,0.f,0.f); // go left
	 *	                break;
	 *	            case 'D':
	 * 	            case 'd':
	 * 	                parent.translate(1.f,0.f,0.f); // go right
	 *	                break;
	 * 	            case ENTER:  // Windows and Linux
	 * 	            case RETURN: // Mac OS X
	 * 	                // operation for using enter/return key
	 *	                break;
	 *	        }
	 *	    }
	 *	}
	 * </pre>
	 * @see #handleSpecialKey(int keyCode, int state, int mX, int mY) 
	 * @param key	ASCII character from keyboard input
	 * @param state 1 if key down, 0 if key up
	 * @param mX    X coordinate of mouse in rendering window
	 * @param mY    Y coordinate of mouse in rendering window
	 */
	void handleKey(char key, int state, int mX, int mY);
	
	/**
	 * Called when keyboard input is received from special (non-ASCII) characters.
	 * <p>
	 * keyCode constants: (arrow keys) UP, DOWN, LEFT, RIGHT, ALT, CTRL, SHIFT, PAGE_UP, PAGE_DOWN, HOME, END
	 * <p>
	 * Example implementation:
	 * <pre>
	 * 	void handleKey(int keyCode, int state, int mX, int mY){
	 * 	    if (state == 1){ // if key pressed down
	 *	        switch(keyCode){ // special key
	 *	            case LEFT:
	 * 	                parent.translate(-1.f,0.f,0.f); // go left
	 *	                break;
	 *	            case RIGHT:
	 * 	                parent.translate(1.f,0.f,0.f); // go right
	 *	                break;
	 *	        }
	 *	    }
	 *	}
	 * </pre>
	 * @see #handleKey(char key, int state, int mX, int mY)
	 * @param keyCode coded keyboard input based on {@link java.awt.event.KeyEvent}
	 * @param state 1 if key down, 0 if key up
	 * @param mX	X coordinate of mouse in rendering window
	 * @param mY	Y coordinate of mouse in rendering window
	 */
	void handleSpecialKey(int keyCode, int state, int mX, int mY);
	
	/**
	 * Called when mouse is clicked up / down in the rendering window
	 * <p>
	 * button constants: LEFT, RIGHT and CENTER
	 * @see #handleMouseDrag(int mX, int mY)
	 * @see #handleMouseMove(int mX, int mY)
	 * @param button mouse button (LEFT, RIGHT or CENTER)
	 * @param state 1 if mouse down, 0 if mouse up
	 * @param mX	X coordinate of mouse in rendering window
	 * @param mY	Y coordinate of mouse in rendering window
	 */
	void handleMouse(int button, int state, int mX, int mY);
	
	/**
	 * Called when mouse is moved in rendering window while mouse button is held down
	 * @see #handleMouse(int button, int state, int mX, int mY)
	 * @see #handleMouseMove(int mX, int mY)
	 * @param mX	X coordinate of mouse in rendering window
	 * @param mY	Y coordinate of mouse in rendering window
	 */
	void handleMouseDrag(int mX, int mY);
	
	/**
	 * Called when mouse is moved in rendering window when no mouse button is pressed
	 * @see #handleMouse(int button, int state, int mX, int mY)
	 * @see #handleMouseDrag(int mX, int mY)
	 * @param mX	X coordinate of mouse in rendering window
	 * @param mY	Y coordinate of mouse in rendering window
	 */
	void handleMouseMove(int mX, int mY);
}
