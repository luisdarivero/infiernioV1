package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Daniel Riv on 11/10/2016.
 */
public class ManoIra {

    private Texture mano;
    private Texture manoCerrada;
    private Sprite sprite;
    private boolean isCerrada;

    public ManoIra(float x, float y){//se modifica la posicion al centro
        cargarTexturas();
        sprite = new Sprite(mano);
        sprite.setCenter(x,y);
        sprite.rotate(45);
        cambiarSprite(sprite.getX(),sprite.getY(),sprite.getRotation());
    }
    private void cargarTexturas(){
        mano = new Texture("Mano.png");
        manoCerrada = new Texture("mano2.png");

    }

    public void cambiarSprite(float x, float y, float rotacion){
        if(isCerrada){
            isCerrada = false;
            sprite = new Sprite(mano);
        }
        else{
            isCerrada = true;
            sprite = new Sprite(manoCerrada);
        }
        sprite.setX(x);
        sprite.setY(y);
        sprite.setRotation(rotacion);
    }

    public void draw(SpriteBatch batch, Furioso furioso){
        actualizar(furioso);
        sprite.draw(batch);
    }

    private void actualizar(Furioso furioso){
        setPosAFurioso(furioso);
    }

    private void setPosAFurioso(Furioso furioso){

        if(furioso.getRotacionActual() == null){
            return;
        }


        sprite.setCenter(
                ((float)Math.cos(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getX(),
                ((float)Math.sin(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getY()
        );
    }


    public enum Estado{

    }
}
