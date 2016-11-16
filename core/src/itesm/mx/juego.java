package itesm.mx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Daniel Riv, Marina on 02/09/2016.
 *representa el juego completo
 */

public class juego extends Game{

    private final AssetManager assetManager = new AssetManager();
    @Override
    public void create() {
        //MApas
        assetManager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));
        //va a crear un objeto tipo menu principal
        setScreen(new MenuPrincipal(this));
    }
    //acceso del assetmanager
    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    @Override
    public void dispose()
    {
        super.dispose();
        assetManager.clear();
    }
}
