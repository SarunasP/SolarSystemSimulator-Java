package code.solarsystem;

import framework.engine.Scene;
import framework.interfaces.Input;
import processing.core.PVector;

/**
 * Special celestial hierarchy that represents a big portion of our solar
 * system. Contains all planets (+ Pluto) and major moons. Saturn has a ring.
 */
public class SolarSystem extends CelestialHierarchy implements Input {

	CelestialObject sun = null;
	CelestialObject mercury = null;
	CelestialObject venus = null;
	CelestialObject earth = null;
	CelestialObject moon = null;
	CelestialObject mars = null;
	CelestialObject jupiter = null;
	CelestialObject saturn = null;
	CelestialObject uranus = null;
	CelestialObject neptune = null;
	CelestialObject pluto = null;
	
	boolean paused = false;
	
	public SolarSystem(Scene parent) {
		super(parent);
		// Create planets
		populateSolarSystem();
		
		// Setup planetary speeds to seem realistic
		almostRealisticSpeeds();
		
		// Reset the sun rotations to a bit more easier pace
		sun.setAxisRotationSpeed(0.05f);
		sun.setOrbitRotation(0.0f);
	}
	
	/**
	 * Create all planets and their moons. Sets things in their proper hierarchies.
	 */
	private void populateSolarSystem(){	
		if( !(sun == null && system.isEmpty()) ){
			return;
		}
		
		float distanceStep = 110.0f;
				
		sun = new CelestialObject(parent, 0.f, 50.f, "sun");
		sun.setColour(255, 255, 0);
		sun.position(0.f, 150.f, 0.f);
		sun.setAxisRotationSpeed(0.f);
		// Suns lights are within it so lighting would actually get ignored on it.
		// Sun looks quite good with texturing only... try and find out what is wrong with the other textured planets
		sun.setLighted(false);
		addCelestialObject(sun);
				
		mercury = new CelestialObject(parent, distanceStep, 6.2f, "mercury");
		//mercury.setColour(200,50,0);
		sun.addCelestialObject(mercury);
		
		venus = new CelestialObject(parent, 2.0f * distanceStep, 9.5f, "venus");
		//venus.setColour(200,200,50);
		sun.addCelestialObject(venus);
		
		earth = new CelestialObject(parent, 3.f * distanceStep, 10.f, "earth");
		//earth.setColour(0,50,255);
		sun.addCelestialObject(earth);
		
			moon = new CelestialObject(parent, 30.f, 5.f, "moon");
			moon.setColour(255,255,255);
			earth.addCelestialObject(moon);
		
		mars = new CelestialObject(parent, 4.f * distanceStep, 7.3f, "mars");
		//mars.setColour(255,0,0);
		sun.addCelestialObject(mars);
		
			// Note, all moons share the same texture/object since they are too small to be seen		
			CelestialObject phobos = new CelestialObject(parent, 12.f, 1.f, "moon");
			phobos.setColour(200, 170, 170);
			mars.addCelestialObject(phobos);
			
			CelestialObject deimos = new CelestialObject(parent, 15.f, 1.7f, "moon");
			deimos.setColour(250, 200, 20);
			mars.addCelestialObject(deimos);
		
		jupiter = new CelestialObject(parent, 5.f * distanceStep, 31.f, "jupiter");
		//jupiter.setColour(200,180,50);
		sun.addCelestialObject(jupiter);
		
			CelestialObject europa = new CelestialObject(parent, 35.f, 2.f, "moon");
			europa.setColour(200, 100, 100);
			jupiter.addCelestialObject(europa);
			
			CelestialObject ganymede = new CelestialObject(parent, 40.f, 1.7f, "moon");
			ganymede.setColour(250, 100, 100);
			jupiter.addCelestialObject(ganymede);
			
			CelestialObject calisto = new CelestialObject(parent, 43.f, 2.1f, "moon");
			calisto.setColour(200, 200, 200);
			jupiter.addCelestialObject(calisto);
			
			CelestialObject io = new CelestialObject(parent, 46.f, 1.5f, "moon");
			io.setColour(10, 250, 250);
			jupiter.addCelestialObject(io);				
		
		saturn = new CelestialObject(parent, 6.f * distanceStep, 30.f, "saturn");
		saturn.setHasRings(true);	// Important not to forget the ring
		//saturn.setColour(200,18,200);
		sun.addCelestialObject(saturn);
		
			CelestialObject titan = new CelestialObject(parent, 78.f, 3.1f, "moon");
			titan.setColour(50, 250, 50);
			saturn.addCelestialObject(titan);	
			
			CelestialObject enceladus = new CelestialObject(parent, 72.f, 2.8f, "moon");
			enceladus.setColour(200, 250, 200);
			saturn.addCelestialObject(enceladus);	
			
			CelestialObject iapetus = new CelestialObject(parent, 78.f, 2.3f, "moon");
			iapetus.setColour(100, 250, 200);
			saturn.addCelestialObject(iapetus);	
		
		uranus = new CelestialObject(parent, 7.f * distanceStep, 19.8f, "uranus");
		//uranus.setColour(50,50,200);
		sun.addCelestialObject(uranus);		
		
			CelestialObject titania = new CelestialObject(parent, 25.f, 3.1f, "moon");
			titania.setColour(200, 250, 200);
			uranus.addCelestialObject(titania);	
			
			CelestialObject miranda = new CelestialObject(parent, 30.f, 2.8f, "moon");
			miranda.setColour(200, 250, 200);
			uranus.addCelestialObject(miranda);	

		neptune = new CelestialObject(parent, 8.f * distanceStep, 19.4f, "neptune");
		//neptune.setColour(30,30,250);
		sun.addCelestialObject(neptune);
			
			CelestialObject triton = new CelestialObject(parent, 25.f, 2.8f, "moon");
			triton.setColour(200, 250, 200);
			neptune.addCelestialObject(triton);	
		
		pluto = new CelestialObject(parent, 9.f * distanceStep, 4.2f, "pluto");
		//pluto.setColour(200,200,200);
		sun.addCelestialObject(pluto);
	}
	
	/**
	 * For testing purposes, all speeds are the same for all planets.
	 */
	@SuppressWarnings("unused")
	private void synchronizeSpeeds( float speed ){
		sun.setAxisRotationSpeed(speed);
		
		mercury.setAxisRotationSpeed(speed);
		mercury.setOrbitRotationSpeed(speed);

		venus.setAxisRotationSpeed(speed);
		venus.setOrbitRotationSpeed(speed);

		earth.setAxisRotationSpeed(speed);
		earth.setOrbitRotationSpeed(speed);

		moon.setAxisRotationSpeed(speed);
		moon.setOrbitRotationSpeed(speed);

		mars.setAxisRotationSpeed(speed);
		mars.setOrbitRotationSpeed(speed);

		jupiter.setAxisRotationSpeed(speed);
		jupiter.setOrbitRotationSpeed(speed);

		saturn.setAxisRotationSpeed(speed);
		saturn.setOrbitRotationSpeed(speed);

		uranus.setAxisRotationSpeed(speed);
		uranus.setOrbitRotationSpeed(speed);

		neptune.setAxisRotationSpeed(speed);
		neptune.setOrbitRotationSpeed(speed);

		pluto.setAxisRotationSpeed(speed);
		pluto.setOrbitRotationSpeed(speed);
	}
	
	/**
	 * Hierarchy random speeds.
	 */
	private void almostRealisticSpeeds(){
		sun.hierarchyRandomSpeed(.12f, true);	
		moon.setOrbitRotationSpeed((float)(Math.PI * 1.5f));
	}
	
	public PVector getSunPosition(){
		return sun.position().add(position());
	}
	
	public CelestialObject getSun() {
		return sun;
	}

	public CelestialObject getMercury() {
		return mercury;
	}

	public CelestialObject getVenus() {
		return venus;
	}

	public CelestialObject getEarth() {
		return earth;
	}

	public CelestialObject getMoon() {
		return moon;
	}

	public CelestialObject getMars() {
		return mars;
	}

	public CelestialObject getJupiter() {
		return jupiter;
	}

	public CelestialObject getSaturn() {
		return saturn;
	}

	public CelestialObject getUranus() {
		return uranus;
	}

	public CelestialObject getNeptune() {
		return neptune;
	}

	public CelestialObject getPluto() {
		return pluto;
	}	

	@Override
	public void update(float dT) {
		if( !paused ){
			super.update(dT);
		}
	}
	
	/**
	 * Added system ambient light here to reduce the load from Light class.
	 */
	@Override
	public void display() {	
		float ambient = 38.f;
		parent.ambientLight(ambient,ambient,ambient);
		super.display();				
	}
	
	/**
	 * Toggle pause with p/P.
	 * Toggle orbit display with o/O.
	 */
	@Override
	public void handleKey(char key, int state, int mX, int mY) {
		if( (key == 'p' || key == 'P') && state == 0 ){
			paused = !paused;
		}		
		if( (key == 'o' || key == 'O') && state == 0){
			sun.setDrawOrbit( !sun.getDrawOrbit(), true );
		}
	}

	@Override
	public void handleSpecialKey(int keyCode, int state, int mX, int mY) {
		
	}

	@Override
	public void handleMouse(int button, int state, int mX, int mY) {
		
	}

	@Override
	public void handleMouseDrag(int mX, int mY) {
		
	}

	@Override
	public void handleMouseMove(int mX, int mY) {
		
	}

}
