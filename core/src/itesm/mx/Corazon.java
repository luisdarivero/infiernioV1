package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Marina on 08/10/2016.
 */
public class Corazon {
    private Sprite sprite;


    public Corazon(Texture textura) {
        sprite = new Sprite(textura);


    }

    public Corazon(Texture textura, float x, float y){
        this(textura);
        sprite.setPosition(x,y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void setPosition(){
        sprite.setPosition(1300,730);
    }



}
