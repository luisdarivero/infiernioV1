package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sam on 13/09/2016.
 */
public class Billete
{
    private Sprite sprite;

    //'Velocidad'
    private float velocidad=2.5f;

    //Estado
    private Estado estado= Estado.SUBIENDO;

    //Arriba-Abajo
    private float alturaActual;
    private float alturaOriginal;

    //Izquierda derecha
    private float anchoActual;


    public Billete(Texture textura, float x, float y){
        sprite=new Sprite(textura);
        sprite.setPosition(x,y);

        //Arriba-Abajo
        alturaActual=sprite.getY();
        alturaOriginal=alturaActual;
        //Izquiera-Derecha
        anchoActual=sprite.getX();
    }

    public  void draw(SpriteBatch batch){
        sprite.draw(batch);
        actualizar();
    }

    private void actualizar() {
        //Animación
        switch (estado){

            case SUBIENDO:
                alturaActual+=velocidad;
                if(alturaActual<-75)
                    anchoActual+=velocidad;
                else
                    anchoActual-=velocidad;

                if (alturaActual>0){
                    alturaActual=0;
                    estado= Estado.BAJANDO;
                }
                break;

            case BAJANDO:
                alturaActual-=velocidad;
                if(alturaActual>-75)
                    anchoActual-=velocidad;
                else
                    anchoActual+=velocidad;

                if(alturaActual<-150){
                    alturaActual=-150;
                    estado= Estado.SUBIENDO;
                }
                break;

        }

        sprite.setRegion(0,0,(int)sprite.getWidth(),(int)sprite.getHeight());
        sprite.setPosition(anchoActual,alturaActual);

        }

    public boolean contiene(float x, float y) {
        return sprite.getBoundingRectangle().contains(x,y);
    }


    public enum Estado{
        SUBIENDO,
        BAJANDO,

    }
}
