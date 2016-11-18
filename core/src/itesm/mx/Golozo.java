package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Daniel Riv on 17/11/2016.
 */

public class Golozo {

    //para la animacion del personaje
    private Animation animacion;
    private Sprite personaje;
    private float deltaTime;
    private float x;
    private float y;
    private Estado estado;

    public Golozo(String imagen1, String imgen2, float x, float y){
        personaje = new Sprite(new Texture("Gula1.png"));
        personaje.setCenter(x,y);
        animacion = new Animation(0.17f,new TextureRegion(new Texture("Gula1.png")), new TextureRegion(new Texture("Gula2.png")));
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        deltaTime = 0;
        this.x = x;
        this.y = y;
        estado = Estado.Normal;
    }

    public void draw(SpriteBatch batch){
        deltaTime+= Gdx.graphics.getDeltaTime();
        personaje.draw(batch);
        personaje.setRegion(animacion.getKeyFrame(deltaTime));
        if (estado == Estado.Saltando){
            personaje.setCenter(personaje.getX(),personaje.getY() + 1);
            System.out.println("esta saltando");
        }

    }

    public Estado getEstado() {

        return estado;
    }

    public void setEstado(Estado estado) {
        if(estado == Estado.Normal){
            this.estado = estado;
        }

    }

    public enum Estado{

        Normal, Saltando, Cayendo
    }
}
