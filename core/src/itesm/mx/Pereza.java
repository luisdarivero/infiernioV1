package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Karlo on 11/10/2016.
 */

public class Pereza
{
    private Sprite sprite;
    //ESTADO
    public Estado estado;
    private int contadors=0;
    public int toquesP=0;
    public Pereza(Texture textura)
    {
        sprite = new Sprite(textura);
    }

    public Pereza(Texture textura, float x, float y,int num)
    {
        this(textura);
        sprite.setPosition(x,y);
        switch(num)
        {
            case 0:
                estado = Estado.UNCUARTO;
                break;
            case 1:
                estado = Estado.UNMEDIO;
                break;
            case 2:
                estado = Estado.TRESCUARTOS;
                break;
            case 3:
                estado = Estado.CASI;
                break;
            case 4:
                estado = Estado.WIN;
                break;
        }
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
        actualizar();
    }

    private void actualizar()
    {
        contadors ++;

        //animacion
        switch(estado)
        {
            case UNCUARTO:
                if(contadors==40)
                {
                    contadors=0;
                }
                if(contadors<20)
                {
                    sprite.setX(sprite.getX()-4);
                }
                else
                {
                    sprite.setX(sprite.getX() + 4);
                }
                break;
            case UNMEDIO:
                if(contadors==30)
                {
                    contadors=0;
                }
                if(contadors<15)
                {
                    sprite.setX(sprite.getX()-4);
                }
                else
                {
                    sprite.setX(sprite.getX() + 4);
                }
                break;
            case TRESCUARTOS:
                if(toquesP < 19)
                {
                    if (contadors == 20) {
                        contadors = 0;
                    }
                    if (contadors < 10) {
                        sprite.setX(sprite.getX() - 4);
                    } else {
                        sprite.setX(sprite.getX() + 4);
                    }
                }
                break;
            case CASI:
                if(toquesP < 19)
                {
                    if (contadors == 10) {
                        contadors = 0;
                    }
                    if (contadors < 5) {
                        sprite.setX(sprite.getX() - 4);
                    } else {
                        sprite.setX(sprite.getX() + 4);
                    }
                }
                break;
            case WIN:
                //ALGO DE WIN
                break;
        }
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public enum Estado
    {
        UNCUARTO,
        UNMEDIO,
        TRESCUARTOS,
        CASI,
        WIN
    }

    public boolean contiene(float x, float y)
    {
        return sprite.getBoundingRectangle().contains(x, y);
    }
}
