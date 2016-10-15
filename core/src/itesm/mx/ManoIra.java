package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;



/**
 * Created by Daniel Riv on 11/10/2016.
 */
public class ManoIra {

    private Texture mano;
    private Texture manoCerrada;
    private Sprite sprite;
    private boolean isCerrada;
    private  Furioso furioso;
    private Float angulo;
    private  float xf;
    private float yf;


    public ManoIra(float ancho, float alto,Furioso furioso){//se modifica la posicion al centro
        cargarTexturas();
        this.furioso = furioso;
        sprite = new Sprite(mano);

        if(furioso.isDerecha()){
            sprite.setCenter(ancho*.75f, alto*.25f);
            sprite.rotate(45);

        }
        else{
            sprite.setCenter(ancho*.25f, alto*.25f);
            sprite.rotate(-45);

        }

        angulo = null;
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
                if(angulo == null){
                    generarDatos();
                }
                setPosDirigido(Gdx.graphics.getDeltaTime(),angulo);

                break;
            case Desventaja:
                setPosAFurioso();
                break;
            case Agarrado: //la mano agarra al personaje
                setPosAFurioso();
                break;
            case Callendo:
                setPosAFurioso();
                break;
        }


    }

    private void generarDatos(){


        float xm = sprite.getX() + sprite.getWidth()/2;
        float ym = sprite.getY() + sprite.getHeight()/2;
        xf = ((float)Math.cos(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.4f)) + furioso.getX();
        yf = ((float)Math.sin(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.4f)) + furioso.getY();
        System.out.println(xf + "," + yf + "    " + xm + "," + ym);
        double anguloMano = Math.toDegrees(Math.asin(((yf-ym) / (Math.sqrt(((xf-xm)*(xf-xm))+((yf-ym)*(yf-ym)))))));
        //System.out.println(anguloMano);
        if(furioso.isDerecha()){
            anguloMano = 180 - anguloMano;
        }


        angulo = (float)anguloMano;
        //System.out.println(anguloMano);
    }

    //separar metodo que calcula las componentes, ya que se pueden guardar en una variable
    private void setPosDirigido(float time, float anguloMano) {
        //las coordenadas del furioso
        float xm = sprite.getX() + sprite.getWidth()/2;
        float ym = sprite.getY() + sprite.getHeight()/2;

        sprite.setCenter(
                ((float)Math.cos(Math.toRadians(anguloMano))*(time*1000)) + xm,
                ((float)Math.sin(Math.toRadians(anguloMano))*(time*1000)) + ym
        );

        if(ym>yf){
            cambiarSprite(sprite.getX(),sprite.getY(),sprite.getRotation());
            furioso.setEstado(Furioso.Estado.Desventaja);
        }

    }
    private void setPosAFurioso(){

        if(furioso.getRotacionActual() == null){
            return;
        }
        sprite.setCenter(
                ((float)Math.cos(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.40f)) + furioso.getX(),
                ((float)Math.sin(Math.toRadians(90+furioso.getRotacionActual()))*(furioso.getAlturaSprite()*.40f)) + furioso.getY()
        );
    }



}
