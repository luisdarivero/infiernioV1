package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.math.BigDecimal;


/**
 * Created by Daniel Riv on 07/10/2016.
 */
public class Furioso {
    private Sprite sprite;
    private Estado estado= Estado.Acelerometro;
    private Float accelY;
    private float rotacionActual;
    private BigDecimal bd;
    private String temp;
    private float dificultad = 50;
    private Texture rojo;
    private Texture azul;
    private boolean isRojo;
    private float x;
    private float y;


    public Furioso(float x, float y){//se modifica la posicion al centro
        cargarTexturas();
        sprite = new Sprite(azul);
        isRojo = false;
        this.x = x;
        this.y=y;
        sprite.setCenter(x,y);

    }

    private void cargarTexturas(){
        rojo = new Texture("Ira_personaje.png");
        azul = new Texture("Ira_azul.png");
    }

    public void draw(SpriteBatch batch){
        actualizar();
        sprite.draw(batch);

    }

    private void actualizar(){
        switch(estado){
            case Acelerometro:
                accelY = Gdx.input.getAccelerometerY();
                rotacionActual = -(accelY*180f)/(9.0f*2.5f);
                rotacionActual = round(rotacionActual,0);
                if(rotacionActual %3 ==0){
                    sprite.setRotation(rotacionActual);
                }
                if(sprite.getRotation() >dificultad || sprite.getRotation() < -dificultad){
                    estado = Estado.Callendo;
                }
                //para cambiar de color la imagen
                if(!isRojo && (rotacionActual >20 || rotacionActual < -20)){
                    cambiarSprite();

                }
                else if(isRojo && (rotacionActual <=20 && rotacionActual >= -20)){
                    cambiarSprite();
                }
                break;
            case Callendo:
                if(sprite.getRotation() >dificultad){
                    sprite.setRotation(sprite.getRotation() +1);
                }
                else{
                    sprite.setRotation(sprite.getRotation() -1);
                }

                if(sprite.getRotation() >= 90 || sprite.getRotation() <= -90){
                    estado = Estado.Perdio;
                }
        }

    }

    public void cambiarSprite(){
        if(isRojo){
            isRojo = false;
            sprite = new Sprite(azul);
            sprite.setCenter(x,y);
            sprite.setRotation(rotacionActual);
        }
        else{
            isRojo = true;
            sprite = new Sprite(rojo);
            sprite.setCenter(x,y);
            sprite.setRotation(rotacionActual);
        }
    }


    public enum Estado{
        Acelerometro,
        Estatico, //para que no se mueva en el eje y la animacion de la mano se vea bien
        Agarrado, //cuando la mano lo tiene agarrado
        Regresando, //cuando la mano lo suelta
        Callendo, //cuando se pasa de cierto angulo cae para perder
        Perdio //indica que ya perdio
    }

    public float round(float d, int decimalPlace) {
        bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        //return bd.floatValue();
        //modificada- el valor del decimal place tiene que ser minimo 2
        temp = Float.toString(bd.floatValue());
        temp = temp.substring(0,temp.length()-2);
        return Float.parseFloat(temp);
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public Float getRotacionActual(){
        if(Estado.Acelerometro != estado){
            rotacionActual = sprite.getRotation();

        }
        if(rotacionActual %3 ==0){
            return rotacionActual;
        }
        return null;
    }

    public float getAlturaSprite(){
        return sprite.getHeight();
    }

    public float getX(){
        return  x;
    }
    public float getY(){
        return y;
    }
}
