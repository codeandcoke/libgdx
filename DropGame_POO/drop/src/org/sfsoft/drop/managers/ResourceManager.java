package org.sfsoft.drop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona los recursos del game
 */
public class ResourceManager {

    // TextureAtlas con los sprites del game
    private TextureAtlas atlas;

    // Recursos de sonido y música
    private Map<String, Sound> sounds;
    private Map<String, Music> musics;

    /**
     * Carga en memoria todos los recursos (gráficos, sonidos y música)
     */
    public void loadAllResources() {

        atlas = new TextureAtlas(Gdx.files.internal("sprites/drop.pack"));

        sounds = new HashMap<>();
        sounds.put("waterdrop", Gdx.audio.newSound(Gdx.files.internal("sounds/waterdrop.wav")));
        sounds.put("rock", Gdx.audio.newSound(Gdx.files.internal("sounds/rock.mp3")));
        sounds.put("frog", Gdx.audio.newSound(Gdx.files.internal("sounds/frog.mp3")));

        musics = new HashMap<>();
        musics.put("bso", Gdx.audio.newMusic(Gdx.files.internal("sounds/undertreeinrain.mp3")));
    }

    /**
     * Obtiene el atlas de texturas del game
     * @return
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * Obtiene un recurso de sonido determinado
     * @param nombre
     * @return
     */
    public Sound getSound(String nombre) {
        return sounds.get(nombre);
    }

    /**
     * Obtiene un recurso de música determinado
     * @param nombre
     * @return
     */
    public Music getMusic(String nombre) {
        return musics.get(nombre);
    }

    public void dispose() {

        atlas.dispose();
        sounds.clear();
        musics.clear();
    }
}
