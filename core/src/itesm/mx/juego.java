package itesm.mx;

import com.badlogic.gdx.Game;

/**
 * Created by Daniel Riv, Marina on 02/09/2016.
 *representa el juego completo
 */
//COMENTARIO DE KARLO
// fff
public class juego extends Game{

    @Override
    public void create() {
        //va a ccrear un objeto tipo menu principal
        setScreen(new MenuPrincipal(this));
    }
}
