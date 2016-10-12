package itesm.mx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Karlo on 06/10/2016.
 */

public class Lujuria
{

    private Sprite sprite;
    //ESTADO
    public Estado estado = Estado.ALDERECHO;
    public int cdr=0;
    public int sexy ;
    public int flip=80;
    public int dec=5;

    public Lujuria(Texture textura)
    {
        sprite = new Sprite(textura);
    }

    public Lujuria(Texture textura,int num)
    {
        this(textura);
        sexy = num;
        estado = Estado.ALREVES;
    }

    public Lujuria(Texture textura, float x, float y,int num)
    {
        this(textura);
        sexy = num;
        sprite.setPosition(x,y);
        switch(num)
        {
            case 0:
                estado = Estado.ALDERECHO;
                break;
            case 1:
                estado = Estado.ALREVES;
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
        //animacion
        switch(estado)
        {
            case ALDERECHO:
                sprite.setRotation(sprite.getRotation()-1);
                break ;
            case ALREVES:

                sprite.setRotation(sprite.getRotation()+1);

                if(sprite.getY()>800)
                {
                    sprite.setRotation(0);

                }
                break;
            case ROTANDO:
                flip=0;
                if(flip<=0)
                {
                    estado = Estado.ALREVES;
                }
                break;
        }
    }

    public boolean contiene(float x, float y)
    {
        return sprite.getBoundingRectangle().contains(x,y);
    }

    public void setEstado(Estado estado)
    {
        this.estado = estado;
    }

    public void setCoordenates(int x,int y)
    {
        sprite.setPosition(x,y);
    }

    public int getX()
    {
        return (int)sprite.getX();
    }

    public int getY()
    {
        return (int)sprite.getY();
    }

    public enum Estado
    {
        ALDERECHO,
        ALREVES,
        ROTANDO,
    }

}

