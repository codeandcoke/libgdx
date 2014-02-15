package org.sfaci.jumper2dx.managers;

import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

/**
 * Contiene métodos para trabajar con TiledMaps
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class TiledMapManager {

	/**
	 * Coloca tiles animados en el nivel del jugador leyendo el tiledmap
	 * @param map
	 * @param collisionLayer
	 * @param animationString
	 * @param n
	 */
	public static void animateTiles(TiledMap map, TiledMapTileLayer collisionLayer, String animationString, int n) {
		
		Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(n);
		Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("tileset").iterator();
		while (tiles.hasNext()) {
			TiledMapTile tile = tiles.next();
			if ((tile.getProperties().containsKey("animation")) && (tile.getProperties().get("animation", String.class).equals(animationString))) {
				frameTiles.add((StaticTiledMapTile) tile);
			}
		}
		
		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1 / 4f, frameTiles);
		// El Tile animado tiene que heredar todas las propiedades de los tiles estáticos que lo forman
		for (TiledMapTile tile : frameTiles)
			animatedTile.getProperties().putAll(tile.getProperties());
		
		// Coloca el tile animado donde haya un tile del mismo tipo pero estático
		for (int x = 0; x < collisionLayer.getWidth(); x++) {
			for (int y = 0; y < collisionLayer.getHeight(); y++) {
				Cell cell = collisionLayer.getCell(x, y);
				if (cell == null)
					continue;
				if (cell.getTile().getProperties().containsKey("animation") && 
					cell.getTile().getProperties().get("animation", String.class).equals(animationString)) {
					cell.setTile(animatedTile);
				}
			}
		}
	}
}
