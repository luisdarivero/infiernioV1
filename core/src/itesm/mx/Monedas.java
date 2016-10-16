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
    private float tiempoOculto;

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
                if (yActual < -10) {
                    yActual = yOriginal;
                    xActual = (float) Math.random() * (1200 - 10) + 10;
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
        }

        //sprite.setRegion(0,0,(int)sprite.getWidth(),(int)sprite.getHeight());
        sprite.setPosition(xActual,yActual);

    }

    public boolean contiene(float x, float y) {
        return sprite.getBoundingRectangle().contains(x,y);
    }

    public enum Estado{
        OCULTO,
        BAJANDO,

    }
}
