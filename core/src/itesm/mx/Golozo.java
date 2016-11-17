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

    public Golozo(String imagen1, String imgen2, float x, float y){
        personaje = new Sprite(new Texture("Gula1.png"));
        personaje.setCenter(x,y);
        animacion = new Animation(0.17f,new TextureRegion(new Texture("Gula1.png")), new TextureRegion(new Texture("Gula2.png")));
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        deltaTime = 0;
    }

    public void draw(SpriteBatch batch){
        deltaTime+= Gdx.graphics.getDeltaTime();
        personaje.draw(batch);
        personaje.setRegion(animacion.getKeyFrame(deltaTime));

    }

    public enum Estado{

        Normal, Saltando, Cayendo
    }
}
