package com.sfaci.link.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

/**
 * Clase para crear el textureatlas a partir de las texturas
 *
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class Packer {

    public static void main(String[] args) {
        TexturePacker2.Settings settings = new TexturePacker2.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.filterMin = Texture.TextureFilter.Linear;

        TexturePacker2.process(settings, "core/assets/robin", "core/assets/robin", "link.pack");
    }
}
