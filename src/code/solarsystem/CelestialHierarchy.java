package code.solarsystem;

import java.util.ArrayList;

import framework.engine.DisplayableObject;
import framework.engine.Scene;
import framework.interfaces.Animation;

/**
 * A hierarchy group representing something celestial. E.g. a planet with its
 * moons would make one CelestialHierarchy.
 * 
 * This class will hierarchically call update and display on its sub elements.
 */
public class CelestialHierarchy extends DisplayableObject implements Animation {

	ArrayList<CelestialHierarchy> system = new ArrayList<CelestialHierarchy>();
	
	public CelestialHierarchy(Scene parent) {
		super(parent);
	}
	
	public void addCelestialObject( CelestialHierarchy cHierarchy ){
		system.add(cHierarchy);
	}
	
	/**
	 * Diverts update call to elements of the system.
	 */
	@Override
	public void update(float dT) {
		for( CelestialHierarchy cHierarchy : system ){
			cHierarchy.update(dT);
		}
	}

	/**
	 * Display of a hierarchy without a visual representation will
	 * just apply the hierarchies position offset and divert the call
	 * to hierarchical display that should do actual drawing.
	 */
	@Override
	public void display() {	
		parent.pushMatrix();
		parent.pushStyle();
			parent.translate(pos.x,pos.y,pos.z);
			hierarchicalDisplay();
		parent.popStyle();
		parent.popMatrix();		
	}
	
	/**
	 * Display all celestial hierarchies that are part of this system.
	 */
	protected void hierarchicalDisplay(){
		for( CelestialHierarchy cHierarchy : system ){
			cHierarchy.hierarchicalDisplay();
		}
	}
	
}
