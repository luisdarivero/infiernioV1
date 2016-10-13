package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sam on 13/09/2016.
 */
public class Fondo {
    private Sprite sprite;
    public Fondo(Texture texturaFondo){
        sprite =new Sprite(texturaFondo);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setSizeF(int x,int y)
    {
        sprite.setSize(sprite.getWidth()+x,sprite.getHeight()+y);
    }

    public void setPositionF(int x,int y)
    {
        sprite.setX(sprite.getX()+x);
        sprite.setY(sprite.getY()+y);
    }
}
