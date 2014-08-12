package org.sfsoft.drop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de la gestión de recursos del juego
 * @author Santiago Faci
 * @version 1.0
 */
public class ResourceManager implements Disposable {

    // TextureAtlas con los sprites del juego
    private TextureAtlas atlas;

    // Recursos de sonido y música
    private Map<String, Sound> sonidos;
    private Map<String, Music> musicas;

    /**
     * Carga en memoria todos los recursos (gráficos, sonidos y música)
     */
    public void loadAllResources() {

        atlas = new TextureAtlas(Gdx.files.internal("drop.pack"));

        sonidos = new HashMap<String, Sound>();
        sonidos.put("waterdrop", Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav")));
        sonidos.put("rock", Gdx.audio.newSound(Gdx.files.internal("rock.mp3")));

        musicas = new HashMap<String, Music>();
        musicas.put("bso", Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3")));
    }

    /**
     * Obtiene el atlas de texturas del juego
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
    public Sound getSonido(String nombre) {
        return sonidos.get(nombre);
    }

    /**
     * Obtiene un recurso de música determinado
     * @param nombre
     * @return
     */
    public Music getMusica(String nombre) {
        return musicas.get(nombre);
    }

    public void dispose() {

        atlas.dispose();
        sonidos.clear();
        musicas.clear();
    }
}
