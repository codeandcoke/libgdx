package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Clase que genera diferentes formas para hacer pruebas en un mondo Box2D
 * @author Santiago Faci
 * @version curso 2015-2016
 *
 * Otros parámetros que se pueden definir a un cuerpo (clase Body)
 * - Velocidad angular
 *  body.setAngularVelocity(0.8f);
 * - Velocidad lineal
 *  body.setLinearVelocity(new Vector2(10f, 10f));
 * - Estado (Despierto, apagado)
 *  body.setAwake(true);
 */
public class WorldGenerator {

	public static float GRAVITY = 10f;
	public static float DENSITY = 10f;
	public static float FRICTION = 0.4f;
	public static float RESTITUTION = 1f;
	
	/**
	 * Genera un cuerpo con forma circular
	 * @param world El mundo
	 * @param x posición X
	 * @param y posicion Y
	 * @return El cuerpo creado
	 */
	public static Body createCircleBody(World world, float x, float y) {
		
		// Masa (en kg)
		MassData mass = new MassData();
		mass.mass = 100f;
		
		// Define un cuerpo físico dinámico con posición en el mundo 2D
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		
		// Define una forma circular de 6 unidades de diámetro
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		
		// Define un elemento físico y sus propiedades y le asigna la forma circular
		FixtureDef fixtureDef = new FixtureDef();
		// Forma del elemento físico
		fixtureDef.shape = circle;
		// Densidad (kg/m^2)
		fixtureDef.density = DENSITY;
		// Coeficiente de fricción (0 - 1)
		fixtureDef.friction = FRICTION;
		// Elasticidad (0 - 1)
		fixtureDef.restitution = RESTITUTION;
		// Añade el elemento físico al cuerpo del mundo 2D
		Fixture fixture = body.createFixture(fixtureDef);
		
		// Definir la masa del cuerpo modifica las propiedades (FixtureDef) y viceversa (densidad)
		body.setMassData(mass);
		
		circle.dispose();
		
		return body;
	}
	
	/**
	 * Genera un cuerpo con forma de caja
	 * @param world El mundo
	 * @param x Posición x
	 * @param y Posición y
	 * @return El cuerpo creado
	 */
	public static Body createBoxBody(World world, float x, float y) {
	
		// Masa (en kg)
		MassData mass = new MassData();
		mass.mass = 100f;
		
		// Define un cuerpo físico dinámico con posición en el mundo 2D
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		
		// Define una forma circular de 6 unidades de diámetro
		PolygonShape box = new PolygonShape();
		box.setAsBox(100f, 100f);
		
		// Define un elemento físico y sus propiedades y le asigna la forma circular
		FixtureDef fixtureDef = new FixtureDef();
		// Forma del elemento físico
		fixtureDef.shape = box;
		// Densidad (kg/m^2)
		fixtureDef.density = 10f;
		// Coeficiente de fricción (0 - 1)
		fixtureDef.friction = 0.4f;
		// Elasticidad (0 - 1)
		fixtureDef.restitution = 0.1f;
		// Añade el elemento físico al cuerpo del mundo 2D
		Fixture fixture = body.createFixture(fixtureDef);
		
		// Definir la masa del cuerpo modifica las propiedades (FixtureDef) y viceversa (densidad)
		body.setMassData(mass);
		
		box.dispose();
		
		return body;
	}
}
