package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Marina on 16/10/2016.
 */
public class Monedas {
    private Sprite sprite;

    //'Velocidad'
    private float velocidad=2.5f;

    //Estado
    private Estado estado= Estado.OCULTO;

    //Tiempo
    private float tiempoOculto=(float)(Math.random()*4+0.5f);

    //Abajo
    private float yActual;
    private float yOriginal;
    private float xActual;



    public Monedas(Texture textura, float x, float y){
        sprite=new Sprite(textura);
        sprite.setPosition(x,y);
        yActual=sprite.getY();
        yOriginal=yActual;
        xActual=sprite.getX();
    }

    public  void draw(SpriteBatch batch){
        sprite.draw(batch);
        actualizar();
    }

    private void actualizar() {
        //Animación
        switch (estado) {
            case BAJANDO:
                yActual -= velocidad;
                if (yActual < -150) {
                    yActual = yOriginal;
                    tiempoOculto=(float)(Math.random()*4+0.5f);
                    estado=Estado.OCULTO;

                }
                break;
            case OCULTO:
                tiempoOculto-= Gdx.graphics.getDeltaTime();
                if (tiempoOculto<=0){
                    estado=Estado.BAJANDO;
                }
                break;
            case GOLPEADO:
                yActual=yOriginal;
                estado=Estado.BAJANDO;
        }

        sprite.setRegion(0,0,(int)sprite.getWidth(),(int)sprite.getHeight());
        sprite.setPosition(sprite.getX(),yActual);

    }

    public boolean contiene(float x, float y) {
        return sprite.getBoundingRectangle().contains(x,y);
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public float getyActual(){
        return sprite.getY();
    }
    public float getxActual(){
        return sprite.getX();
    }

    public void setVelocidad(float velocidad){
        this.velocidad+=velocidad;
    }

    public enum Estado{
        OCULTO,
        BAJANDO,
        GOLPEADO,


    }
}
