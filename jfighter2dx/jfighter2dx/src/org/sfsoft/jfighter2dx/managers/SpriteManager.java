package org.sfsoft.jfighter2dx.managers;

import static org.sfsoft.jfighter2dx.util.Constants.SCREEN_HEIGHT;
import static org.sfsoft.jfighter2dx.util.Constants.SCREEN_WIDTH;

import java.util.List;

import org.sfsoft.jfighter2dx.JFighter2DX;
import org.sfsoft.jfighter2dx.characters.Block;
import org.sfsoft.jfighter2dx.characters.Bullet;
import org.sfsoft.jfighter2dx.characters.Enemy;
import org.sfsoft.jfighter2dx.characters.Ship;
import org.sfsoft.jfighter2dx.characters.ShooterEnemy;
import org.sfsoft.jfighter2dx.characters.StaticShooterEnemy;
import org.sfsoft.jfighter2dx.effects.Explosion;
import org.sfsoft.jfighter2dx.powerups.Powerup;
import org.sfsoft.jfighter2dx.screens.GameOverScreen;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Genera y actualiza todos los elementos que se dibujan en la pantalla
 * @author Santiago Faci
 * @version 1.0
 */
public class SpriteManager {

	private Texture background;
	private Ship ship;
	private Array<Enemy> enemies;
	private Array<Bullet> enemyBullets;
	private Array<Explosion> explosions;
	private Array<Powerup> powerups;
	private Array<Block> blocks;
	
	private JFighter2DX game;
	
	public SpriteManager(JFighter2DX game) {
	
		background = ResourceManager.getTexture("background");
		
		ship = new Ship(50, 50, 250, game.configurationManager);
		ship.setLives(Constants.SHIP_LIVES);
		
		enemies = new Array<Enemy>();
		enemyBullets = new Array<Bullet>();
		explosions = new Array<Explosion>();
		powerups = new Array<Powerup>();
		blocks = new Array<Block>();
		
		this.game = game;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public Array<Bullet> getEnemyBullets() {
		return enemyBullets;
	}
	
	public Array<Explosion> getExplosions() {
		return explosions;
	}
	
	public Array<Powerup> getPowerups() {
		return powerups;
	}
	
	public Array<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * Actualiza la posición de los elementos de la pantalla
	 * @param dt
	 */
	public void update(float dt) {
		
		updateEnemies(dt);
		updateBlocks(dt);
		updateShipBullets(dt);
		updatePowerups(dt);
		updateExplosions(dt);
		
		checkCollisions(dt);
	}
	
	/**
	 * Actualiza el estado de los enemigos
	 * @param dt
	 * @throws SlickException
	 */
	private void updateEnemies(float dt) {
		
		// Desplaza todos los enemigos por la pantalla
		for (int i = enemies.size - 1; i >= 0; i--) {
			
			// Desplaza al enemigo por la pantalla
			Enemy enemy = enemies.get(i);
			enemy.update(dt);
			
			if (enemy.getClass().getSimpleName().equals("ShooterEnemy"))
				((ShooterEnemy) enemy).shoot(dt);
			
			if (enemy.getClass().getSimpleName().equals("StaticShooterEnemy"))
				((StaticShooterEnemy) enemy).shoot(dt);
			
			// Si algún enemigo sale de la pantalla se hace desaparecer
			if ((enemy.getX() < 0) || (enemy.getY() < 0)) {
				enemies.removeIndex(i);
			}			
		}
	}
	
	/**
	 * Actualiza el estado de los bloques de tierra
	 * @param dt
	 */
	private void updateBlocks(float dt) {
		
		for (Block block : blocks)
			block.update(dt);
	}
	
	/**
	 * Actualiza el estado de los proyectiles del personaje
	 * @param dt
	 */
	private void updateShipBullets(float dt) {
		
		List<Bullet> bullets = ship.getBullets();
		Bullet bullet = null;
		
		for (int i = bullets.size() - 1; i >= 0; i--) {
			
			bullet = bullets.get(i);
			bullet.update(dt);
			
			// Si el proyectil sale de la pantalla se elimina
			if ((bullet.getX() < 0) || (bullet.getX() > SCREEN_WIDTH) || (bullet.getY() < 0) || (bullet.getY() > SCREEN_HEIGHT))
				bullets.remove(i);
		}
	}
	
	/**
	 * Actualiza el estado de los powerups en pantalla
	 * @param dt
	 */
	private void updatePowerups(float dt) {
		
		Powerup item = null;
		
		for (int i = powerups.size - 1; i >= 0; i--) {
			item = powerups.get(i);
			item.update(dt);
			
			if (item.getX() < 0)
				powerups.removeIndex(i);
		}
	}
	
	/**
	 * Actualiza el estado de las explosiones en pantalla
	 * @param dt
	 */
	private void updateExplosions(float dt) {
		
		Explosion explosion = null;
		
		for (int i = explosions.size - 1; i >= 0; i--) {
			
			explosion = explosions.get(i);
			explosion.update(dt);
			if (explosion.mustDie()) {
				explosions.removeIndex(i);
			}
		}
	}
	
	/**
	 * Pinta en pantalla todos los elementos del juego
	 */
	public void draw(SpriteBatch batch) {
	
		batch.disableBlending();
		batch.draw(background, 0, 0);
		batch.enableBlending();
		
		ship.draw(batch);
		
		for (Enemy enemy : enemies) {
			System.out.println(enemy.getClass().getSimpleName());
			enemy.draw(batch);
		}
		
		for (Bullet bullet : ship.getBullets()) {
			bullet.draw(batch);
		}
		
		for (Explosion explosion : explosions) {
			explosion.draw(batch);
		}
		
		for (Powerup  item : powerups) {
			item.draw(batch);
		}
		
		for (Block block : blocks) {
			block.draw(batch);
		}
	}
	
	/**
	 * Elimina todos los enemigos de la pantalla
	 * @throws SlickException
	 */
	public void killAllEnemies() {
		
		Enemy enemy = null;
		
		for (int i = enemies.size - 1; i >= 0; i--) {
			enemy = enemies.get(i);
			if (!enemy.getClass().getSimpleName().equals("Stone")) {
				Explosion explosion = new Explosion(enemy.getX(), enemy.getY());
				explosions.add(explosion);
				if (game.configurationManager.isSoundEnabled())
					ResourceManager.getSound("explosion").play();
				enemies.removeIndex(i);
				
			}
		}
	}
	
	/**
	 * Comprueba las colisiones entre proyectiles y personaje/enemigos
	 * @param dt
	 * @throws SlickException
	 */
	private void checkCollisions(float dt) {
		
		Bullet bullet = null;
		Enemy enemy = null;
		Powerup powerup = null;
		List<Bullet> bullets = ship.getBullets();
		
		// Comprueba si los proyectiles del personaje han alcanzado a algún enemigo
		for (int i = enemies.size - 1; i >= 0; i--) {
			enemy = enemies.get(i);
			for (int j = bullets.size() - 1; j >= 0; j--) {
				bullet = bullets.get(j);
				
				if (bullet.getRect().overlaps(enemy.getRect())) {
					
					// Si el enemigo no es meteorito ni bala enemiga se explosiona y se elimina
					if ((!enemy.getClass().getSimpleName().equals("Stone")) && (!enemy.getClass().getSimpleName().equals("ShooterBullet"))) {
						
						enemy.hit();
						if (enemy.getLives() == 0) {
							ship.addScore(enemy.getValue());
							
							Explosion explosion = new Explosion(enemy.getX() - Constants.ENEMY_WIDTH, enemy.getY());
							explosions.add(explosion);
							enemies.removeIndex(i);
							
							if (game.configurationManager.isSoundEnabled())
								ResourceManager.getSound("explosion").play();
						}
						
					}
					// Si se dispara contra la bala de un enemigo, el proyectil no desaparece
					// Si se dispara un misil, éste tampoco desaparece hasta que no llega al final de la pantalla,
					// aunque haya acertado a algún enemigo
					if ((!enemy.getClass().getSimpleName().equals("ShooterBullet")) && 
							(!bullet.getClass().getSimpleName().equals("Missile")))
						bullets.remove(j);
				}
			}
		}
		
		// Comprueba si la nave colisiona con algún enemigo
		if (ship.getShieldTime() <= 0) {
			for (int i = enemies.size - 1; i >= 0; i--) {
				enemy = enemies.get(i);
				
				if (enemy.getRect().overlaps(ship.getRect())) {
					
					enemy.hit();
					if (enemy.getLives() == 0) {
						ship.addScore(enemy.getValue());
						
						Explosion explosion = new Explosion(enemy.getX(), enemy.getY());
						explosions.add(explosion);
						enemies.removeIndex(i);
						
						if (game.configurationManager.isSoundEnabled())
							ResourceManager.getSound("explosion").play();
					}
					
					if (enemy.getClass().getSimpleName().equals("Stone")) {
						// El usuario muere al chocar con una roca
						game.score = ship.getScore();
						game.setScreen(new GameOverScreen(game));
					}
					
					ship.hit();
					if (game.configurationManager.isSoundEnabled())
						ResourceManager.getSound("buzz").play();
					
					if (ship.getLives() == 0) {
						// El usuario muere al quedarse sin vidas
						game.score = ship.getScore();
						game.setScreen(new GameOverScreen(game));
					}
				}
			}
		}
		
		// Comprueba si el personaje ha cogido algún item
		for (int i = powerups.size - 1; i >= 0; i--) {
			powerup = powerups.get(i);
			
			if (powerup.getRect().overlaps(ship.getRect())) {
				
				if (game.configurationManager.isSoundEnabled())
					ResourceManager.getSound("item").play();
				
				if (powerup.getClass().getSimpleName().equals("Bomb"))
					ship.setBombs(ship.getBombs() + 1);
				
				if (powerup.getClass().getSimpleName().equals("Shield"))
					ship.setShieldTime(ship.getShieldTime() + Constants.SHIELD_TIME);
				
				powerups.removeIndex(i);
			}
		}
	}
	
}
