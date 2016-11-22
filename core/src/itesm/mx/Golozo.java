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
    private float alturaSalto;
    private float acumuladorDtime;


    public Golozo(String imagen1, String imgen2, float x, float y){
        personaje = new Sprite(new Texture("Gula1.png"));
        personaje.setCenter(x,y + personaje.getHeight()/2);
        animacion = new Animation(0.17f,new TextureRegion(new Texture("Gula1.png")), new TextureRegion(new Texture("Gula2.png")));
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        deltaTime = 0;
        this.x = x;
        this.y = y + personaje.getHeight()/2;
        estado = Estado.Normal;
        alturaSalto = 260;
        acumuladorDtime = 0;
    }

    public void draw(SpriteBatch batch){
        deltaTime+= Gdx.graphics.getDeltaTime();
        personaje.draw(batch);
        if(estado == Estado.Normal){
            personaje.setRegion(animacion.getKeyFrame(deltaTime));
        }

        if (estado == Estado.Saltando){
            personaje.setCenter(x,personaje.getY()+(personaje.getHeight()/2) + 5);
            if(personaje.getY()+(personaje.getHeight()/2) > y + alturaSalto){
                estado = Estado.Estatico;
                acumuladorDtime = 0;
            }

        }
        if(estado == Estado.Estatico){
            acumuladorDtime += Gdx.graphics.getDeltaTime();
            if(acumuladorDtime>0.2){
                estado = Estado.Cayendo;
            }
        }
        if (estado == Estado.Cayendo){
            personaje.setCenter(x,personaje.getY()+(personaje.getHeight()/2) - 4);
            if(personaje.getY()+(personaje.getHeight()/2) < y ){
                estado = Estado.Normal;
                personaje.setCenter(x,y);
            }
        }




    }

    public Estado getEstado() {

        return estado;
    }

    public void saltar() {
        if(estado == Estado.Normal){
            this.estado = Estado.Saltando;
        }


    }

    public void perdio() {
        this.estado = Estado.Perdio;
    }

    public  Sprite getSprite(){
        return personaje;
    }

    public enum Estado{

        Normal, Saltando,Estatico, Cayendo, Perdio
    }
}
