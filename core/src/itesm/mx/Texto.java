package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Sam on 20/09/2016.
 */
public class Texto {
    private BitmapFont font;

    public Texto() {
        font = new BitmapFont(Gdx.files.internal("fuenteAv_a.fnt"));
    }
    public Texto(String texto) {
        font = new BitmapFont(Gdx.files.internal(texto));
    }


    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, mensaje);
        float anchoTexto = glyp.width;
        font.draw(batch,glyp,x-anchoTexto/2,y);
    }
}
