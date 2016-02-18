package com.sfaci.link.managers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sfaci.link.characters.Enemy;
import com.sfaci.link.characters.GreenEnemy;
import com.sfaci.link.characters.YellowEnemy;
import com.sun.prism.TextureMap;

/**
 * Clase encargada de generar los niveles
 * @author Santiago Faci
 * @version curso 2014-2015
 *
 */
public class LevelManager {

    private int currentLevel;
    private SpriteManager spriteManager;

    TiledMap map;
    TiledMapTileLayer collisionLayer;
    OrthogonalTiledMapRenderer mapRenderer;

	public LevelManager(SpriteManager spriteManager) {

        this.spriteManager = spriteManager;
        currentLevel = 1;

        spriteManager.enemies = new Array<>();
    }

    public void loadCurrentLevel() {

        // Carga el mapa y obtiene la capa de colisión (objetos con los que el personaje puede chocar)
        map = new TmxMapLoader().load("levels/tiledmap" + currentLevel + ".tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("base");

        // Crea el renderizador del tiledmap
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        loadEnemies();
        loadItems();
    }

    public void restarCurrentLevel() {

    }

    public void passNextLevel() {

        currentLevel++;
        loadCurrentLevel();
    }

    public void loadEnemies() {

        Enemy enemy = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("cosas").getObjects()) {
            if (object instanceof TextureMapObject) {
                TextureMapObject rectangleObject = (TextureMapObject) object;
                if (rectangleObject.getProperties().containsKey("bicho")) {
                    String enemyType = (String) rectangleObject.getProperties().get("bicho");
                    switch (enemyType) {
                        case "verde":
                            int speed = Integer.parseInt((String) rectangleObject.getProperties().get("velocidad"));
                            enemy = new GreenEnemy(rectangleObject.getX(),
                                    rectangleObject.getY(), "green_bubble", speed);
                            break;
                        default:
                            break;
                    }
                    spriteManager.enemies.add(enemy);
                }
            }
        }
    }

    public void loadItems() {

    }
}
