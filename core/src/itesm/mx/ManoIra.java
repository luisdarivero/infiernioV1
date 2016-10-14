package itesm.mx;

import com.badlogic.gdx.Gdx;
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
    private  Furioso furioso;


    public ManoIra(float x, float y, Furioso furioso){//se modifica la posicion al centro
        cargarTexturas();
        this.furioso = furioso;
        sprite = new Sprite(mano);
        sprite.setCenter(x,y);
        sprite.rotate(45);
        //cambiarSprite(sprite.getX(),sprite.getY(),sprite.getRotation());
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

    public void draw(SpriteBatch batch){
        actualizar();
        sprite.draw(batch);
    }

    private void actualizar(){
        switch (furioso.getEstado()){
            case Estatico:
                setPosDirigido(Gdx.graphics.getDeltaTime());

                break;
            case Agarrado: //la mano agarra al personaje
                setPosAFurioso();
                break;
        }


    }

    //separar metodo que calcula las componentes, ya que se pueden guardar en una variable
    private void setPosDirigido(float time) {
        //las coordenadas del furioso
        float xf = ((float)Math.cos(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getX();
        float yf = ((float)Math.sin(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getY();
        float xm = sprite.getX() + sprite.getWidth()/2;
        float ym = sprite.getY() + sprite.getHeight()/2;
        //System.out.println(xf + "," + yf + "    " + xm + "," + ym);
        double anguloMano = Math.toDegrees(Math.asin(((yf-ym) / (Math.sqrt(((xf-xm)*(xf-xm))+((yf-ym)*(yf-ym)))))));
        //System.out.println(anguloMano);
        anguloMano += 90;

        sprite.setCenter(
                ((float)Math.cos(Math.toRadians(anguloMano))*(time*1000)) + xm,
                ((float)Math.sin(Math.toRadians(anguloMano))*(time*1000)) + ym
        );
        if(xm<xf && ym>yf){
            furioso.setEstado(Furioso.Estado.Agarrado);
        }

    }
    private void setPosAFurioso(){

        if(furioso.getRotacionActual() == null){
            return;
        }
        sprite.setCenter(
                ((float)Math.cos(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getX(),
                ((float)Math.sin(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.25f)) + furioso.getY()
        );
    }



}
