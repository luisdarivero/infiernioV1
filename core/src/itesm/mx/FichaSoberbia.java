package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Daniel Riv on 01/11/2016.
 */

public class FichaSoberbia {

    private Sprite sprite;
    private String etiqueta;

    public FichaSoberbia(String etiqueta, String imagen){
        this.etiqueta = etiqueta;
        sprite = new Sprite(new Texture(imagen));
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public boolean contains(float x, float y){
        if(sprite.getBoundingRectangle().contains(x,y)){
            return true;
        }
        return false;
    }

    public void setCenter(float x, float y){
        sprite.setCenter(x,y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public boolean ColisionImagen(Sprite imagen){
        if(sprite.getBoundingRectangle().contains(imagen.getBoundingRectangle())){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object ficha){
        if (ficha instanceof FichaSoberbia) {
            if(ficha != null){
                System.out.println( this.getEtiqueta() + " " + ((FichaSoberbia)ficha).getEtiqueta());
                if ((this.getEtiqueta()).equals( ((FichaSoberbia)ficha).getEtiqueta())){

                    return true;
                }
            }
        }
        return false;
    }
}
