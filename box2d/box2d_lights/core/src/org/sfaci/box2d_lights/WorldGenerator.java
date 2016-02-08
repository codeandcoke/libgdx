package org.sfaci.box2d_lights;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Clase que genera diferentes formas para hacer pruebas en un mondo Box2D
 * @author Santiago Faci
 * @version 2.0
 *
 * Otros par�metros que se pueden definir a un cuerpo (clase Body)
 * - Velocidad angular
 *  body.setAngularVelocity(0.8f);
 * - Velocidad lineal
 *  body.setLinearVelocity(new Vector2(10f, 10f));
 * - Estado (Despierto, apagado)
 *  body.setAwake(true);
 */
public class WorldGenerator {
	
	/**
	 * Genera un cuerpo con forma circular
	 * @param world El mundo
	 * @param x posici�n X
	 * @param y posicion Y
	 * @return El cuerpo creado
	 */
	public static Body createCircleBody(World world, float x, float y) {
		
		// Define un cuerpo f�sico din�mico con posici�n en el mundo 2D
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		
		// Define una forma circular de 6 unidades de di�metro
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		
		// Define un elemento f�sico y sus propiedades y le asigna la forma circular
		FixtureDef fixtureDef = new FixtureDef();
		// Forma del elemento f�sico
		fixtureDef.shape = circle;
		// Densidad (kg/m^2)
		fixtureDef.density = 10f;
		// Coeficiente de fricci�n (0 - 1)
		fixtureDef.friction = 0.5f;
		// Elasticidad (0 - 1)
		fixtureDef.restitution = 0.5f;
		// A�ade el elemento f�sico al cuerpo del mundo 2D
		Fixture fixture = body.createFixture(fixtureDef);
		
		circle.dispose();
		
		return body;
	}
	
	/**
	 * Genera un cuerpo con forma de caja
	 * @param world El mundo
	 * @param x Posici�n x
	 * @param y Posici�n y
	 * @return El cuerpo creado
	 */
	public static Body createBoxBody(World world, float x, float y, float width, float height) {
		
		// Define un cuerpo f�sico din�mico con posici�n en el mundo 2D
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		
		// Define una forma circular de 6 unidades de di�metro
		PolygonShape box = new PolygonShape();
		box.setAsBox(height, width);
		
		// Define un elemento f�sico y sus propiedades y le asigna la forma circular
		FixtureDef fixtureDef = new FixtureDef();
		// Forma del elemento f�sico
		fixtureDef.shape = box;
		// Densidad (kg/m^2)
		fixtureDef.density = 10f;
		// Coeficiente de fricci�n (0 - 1)
		fixtureDef.friction = 0.8f;
		// Elasticidad (0 - 1)
		fixtureDef.restitution = 0.2f;
		// A�ade el elemento f�sico al cuerpo del mundo 2D
		Fixture fixture = body.createFixture(fixtureDef);
		
		box.dispose();
		
		return body;
	}
	
	/**
	 * Genera un pol�gono
	 * @param world El mundo
	 * @param x Posici�n x
	 * @param y Posici�n y
	 * @return El cuerpo creado
	 */
	public static Body createPolygonBody(World world, float x, float y, float width, float height) {
		
		// Define un cuerpo f�sico din�mico con posici�n en el mundo 2D
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		//bodyDef.position.set(x, y);
		Body body = world.createBody(bodyDef);
		
		// Define una forma circular de 6 unidades de di�metro
		EdgeShape polygon = new EdgeShape();
		polygon.set(new Vector2(x, y), new Vector2(x + width, y + height));
		
		// Define un elemento f�sico y sus propiedades y le asigna la forma circular
		FixtureDef fixtureDef = new FixtureDef();
		// Forma del elemento f�sico
		fixtureDef.shape = polygon;
		// Densidad (kg/m^2)
		fixtureDef.density = 1f;
		// Coeficiente de fricci�n (0 - 1)
		fixtureDef.friction = 1f;
		// Elasticidad (0 - 1)
		fixtureDef.restitution = 0.2f;
		// A�ade el elemento f�sico al cuerpo del mundo 2D
		Fixture fixture = body.createFixture(fixtureDef);
		
		polygon.dispose();
		
		return body;
	}
}
