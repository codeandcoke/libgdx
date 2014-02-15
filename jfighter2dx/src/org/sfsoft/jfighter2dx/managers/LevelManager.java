package org.sfsoft.jfighter2dx.managers;

import static org.sfsoft.jfighter2dx.util.Constants.ENEMY_RATE;
import static org.sfsoft.jfighter2dx.util.Constants.POWERUP_TIME;

import java.util.Random;

import org.sfsoft.jfighter2dx.characters.Enemy;
import org.sfsoft.jfighter2dx.characters.Enemy.EnemyType;
import org.sfsoft.jfighter2dx.powerups.Powerup;
import org.sfsoft.jfighter2dx.powerups.Powerup.PowerupType;
import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Clase encargada de generar los niveles de la partida
 * Contiene métodos para generar diferentes tipos de enemigos o formaciones de enemigos
 * También contiene los métodos que generan las secuencias de enemigos según el nivel y el tiempo de juego
 * 
 * generateLevel() genera niveles estructurados y siempre iguales
 * generateRandomLevel() genera enemigos de forma aleatoria a lo largo del tiempo
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class LevelManager {

	private static float STEP = 9f;
	
	public enum SquadronAlignment {
		DIAGONAL_1, DIAGONAL_2, CROSS, DIAMOND, ONE, TWO_H, TWO_V, SCRUM_1, SCRUM_2
	}
	
	// Nivel actual
	private int currentLevel;
	private SpriteManager spriteManager;
	// Marca el tiempo entre la generación de dos enemigos seguidos
	// Sólo se utiliza para generar niveles aleatorios
	private float enemyTime;
	// Marca el tiempo entre la generación de dos powerups seguidos
	private float powerupTime;
	// Tiempo de paso en la creación de escuadrones
	private float stepTime;
	// Indica el siguiente momento en que se generarán enemigos
	private int currentStep;
	// Indica si se ha terminado el paso actual
	private boolean stepDone;
	private String[] steps;
	private Random generator;
	
	public LevelManager(SpriteManager spriteManager) {
		
		this.spriteManager = spriteManager;
		enemyTime = 0;
		powerupTime = 0;
		stepTime = 0;
		currentStep = 0;
		generator = new Random();
		currentLevel = 1;
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	/**
	 * Genera el nivel a partir del fichero cargado previamente con readCurrentLevelFile()
	 * @param dt
	 */
	public void generateLevelFromFile(float dt) {
		
		Array<Enemy> enemies = spriteManager.getEnemies();
		
		enemyTime += dt;
		powerupTime += dt;
		stepTime += dt;
		
		if (stepTime >= STEP) {
			currentStep++;
			stepTime = 0;
			stepDone = false;
		}
		
		if (currentStep > steps.length - 1)
			return;
		
		if (!stepDone) {
			// Carga la información del paso actual
			String[] squadrons = steps[currentStep].split(",");
			
			for (String squadron : squadrons) {
				String[] data = squadron.split(" ");
				enemies.addAll(generateSquadron(Float.parseFloat(data[0].trim()), Float.parseFloat(data[1].trim()), SquadronAlignment.valueOf(data[2].trim()), EnemyType.valueOf(data[3].trim())));
				stepDone = true;
			}
		}
		
		// Genera powerups aleatoriamente
		if (powerupTime >= POWERUP_TIME) {
			
			int i = generator.nextInt(PowerupType.values().length);
			
			Powerup powerup = PowerupSpawner.createPowerup(PowerupType.values()[i]);
			spriteManager.getPowerups().add(powerup);
			powerupTime = 0;
		}
}
	
	/**
	 * Lee y genera el nivel desde un fichero de nivel
	 */
	public void readCurrentLevelFile() {
		
		FileHandle file = Gdx.files.internal("levels/level" + currentLevel + ".txt");
		String levelInfo = file.readString();
		
		steps = levelInfo.split("\n");
	}
	
	/**
	 * Genera un escuadrón de enemigos
	 * @param x Coordenada x del enemigo situado más a la izquierda del escuadrón
	 * @param y Coordenada y del enemigo situado más a la izquierda del escuadrón
	 * @param alignment Alineación del escuadrón
	 * @param enemyType Tipo de unidad enemiga
	 * @return El escuadrón formado
	 */
	private Array<Enemy> generateSquadron(float x, float y, SquadronAlignment alignment, EnemyType enemyType) {
		
		Array<Enemy> squadron = new Array<Enemy>();
		
		switch (alignment) {
			case DIAGONAL_1:
				for (int i = 0; i < 4; i++) {
					squadron.add(EnemySpawner.createEnemy(enemyType, x + i * 100, y + i * 30, spriteManager));
					
				}
				break;
			case DIAGONAL_2:
				for (int i = 0; i < 4; i++) {
					squadron.add(EnemySpawner.createEnemy(enemyType, x + i * 100, y - i * 30, spriteManager));
				}
				break;
			case CROSS:
				
				squadron.add(EnemySpawner.createEnemy(enemyType, x + 50, y - 50, spriteManager));
				for (int i = 0; i < 3; i++) {
					squadron.add(EnemySpawner.createEnemy(enemyType, x + i * 50, y, spriteManager));
				}
				squadron.add(EnemySpawner.createEnemy(enemyType, x + 50, y + 50, spriteManager));
				break;
			case DIAMOND:
				squadron.add(EnemySpawner.createEnemy(enemyType, x - 40, y, spriteManager));
				squadron.add(EnemySpawner.createEnemy(enemyType, x + 40, y, spriteManager));
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y + 40, spriteManager));
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y - 40, spriteManager));
				break;
			case ONE:
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y, spriteManager));
				break;
			case TWO_H:
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y, spriteManager));
				squadron.add(EnemySpawner.createEnemy(enemyType, x + 40, y, spriteManager));
				break;
			case TWO_V:
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y, spriteManager));
				squadron.add(EnemySpawner.createEnemy(enemyType, x, y + 40, spriteManager));
				break;
			case SCRUM_1:
				int count = generator.nextInt(7) + 2;
				int padding = 0;
				
				for (int i = 0; i < count / 3; i++) {
					for (int j = 0; j < count / 2; j++) {
						padding = generator.nextInt(15) + 30;
						
						squadron.add(EnemySpawner.createEnemy(enemyType, x + j * padding, y - i * padding, spriteManager));
						squadron.add(EnemySpawner.createEnemy(enemyType, x - j * padding, y + i * padding, spriteManager));
					}
				}
				break;
			case SCRUM_2:
				count = generator.nextInt(7) + 2;
				padding = 0;
				
				for (int i = 0; i < count / 2; i++) {
					for (int j = 0; j < count / 2; j++) {
						padding = generator.nextInt(15) + 30;
						
						squadron.add(EnemySpawner.createEnemy(enemyType, x + j * padding, y - i * padding, spriteManager));
						squadron.add(EnemySpawner.createEnemy(enemyType, x - j * padding, y + i * padding, spriteManager));
					}
				}
				break;
			default:
				break;
		}

		return squadron;
	}
	
	/*
	public void generateLevel(int level, float dt) {
		
		Array<Enemy> enemies = spriteManager.getEnemies();
		
		enemyTime += dt;
		powerupTime += dt;
		stepTime += dt;
		
		if (stepTime >= STEP) {
			currentStep++;
			stepTime = 0;
			stepDone = false;
		}
		
		// Genera diversos escuadrones en cada paso
		if (!stepDone)
			switch(currentStep) {
			case 1:
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 100f, SquadronAlignment.DIAMOND, EnemyType.SMALL_ENEMY));
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 500, SquadronAlignment.DIAMOND, EnemyType.SMALL_ENEMY));
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 200, SquadronAlignment.TWO_V, EnemyType.BIG_ENEMY));
				stepDone = true;
				break;
			case 2:
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 200f, SquadronAlignment.TWO_H, EnemyType.SHOOTER_ENEMY));
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 300f, SquadronAlignment.TWO_V, EnemyType.SHOOTER_ENEMY));
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 300, SquadronAlignment.ONE, EnemyType.STATIC_SHOOTER_ENEMY));
				enemies.addAll(generateSquadron(Constants.SCREEN_WIDTH - 100, 300f, SquadronAlignment.ONE, EnemyType.STATIC_SHOOTER_ENEMY));
				stepDone = true;
				break;
			default:
			}
			
		// Genera un powerup de forma aleatoria
		if (powerupTime >= POWERUP_TIME) {
			
			int i = generator.nextInt(PowerupType.values().length);
			
			Powerup powerup = PowerupSpawner.createPowerup(PowerupType.values()[i]);
			spriteManager.getPowerups().add(powerup);
			powerupTime = 0;
		}	
	}
	*/
	/**
	 * Genera el nivel de forma totalmente aleatoria
	 */
	public void generateRandomLevel(float dt) {
		
		Enemy enemy = null;
		
		enemyTime += dt;
		powerupTime += dt;
		
		if (enemyTime > ENEMY_RATE) {
			
			// Genera los enemigos aleatoriamente
			int i = generator.nextInt(EnemyType.values().length);
			
			enemy = EnemySpawner.createEnemy(EnemyType.values()[i], spriteManager);
			
			spriteManager.getEnemies().add(enemy);
			enemyTime = 0;
		}
		 
		if (powerupTime >= POWERUP_TIME) {
			
			int i = generator.nextInt(PowerupType.values().length);
			
			Powerup powerup = PowerupSpawner.createPowerup(PowerupType.values()[i]);
			spriteManager.getPowerups().add(powerup);
			powerupTime = 0;
		}	
	}
}
