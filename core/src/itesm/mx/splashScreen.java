package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Random;

/**
 * Created by KRLO on 11/16/2016.
 */

public class splashScreen implements Screen
{
    private final juego Juego;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private StretchViewport vista;
    private Texture texturaCargando;
    private Texture texturaf1;
    private Texture texturaf2;
    private Texture texturaf3;
    private Sprite spriteCargando;
    Random rnd = new Random();
    int x = 0;
    int y = 3;
    private final AssetManager assetManager;// = new AssetManager();
    private Array<Lujuria> flamas;

    public splashScreen(juego juego)
    {
        this.Juego = juego;
        assetManager = Juego.getAssetManager();
    }

    @Override
    public void show()
    {
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280/2,400,0);
        camara.update();
        vista = new StretchViewport(1280,800,camara);
        batch = new SpriteBatch();
        assetManager.load("Splash.png",Texture.class);
        assetManager.finishLoading();
        texturaCargando = assetManager.get("Splash.png");
        texturaf1 = new Texture("Llama1.png");
        texturaf2 = new Texture("Llama2.png");
        texturaf3 = new Texture("Llama3.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(1280/2-spriteCargando.getWidth()/2,800/2-spriteCargando.getHeight()/2);
        flamas = new Array<Lujuria>(3);
        for(int i = 0;i<3;i++)
        {
            if(i==0)
            {
                Lujuria lujuria = new Lujuria(texturaf1,1280/2-50,800/2-200,1);
                flamas.add(lujuria);
            }
            else if(i==1)
            {
                Lujuria lujuria = new Lujuria(texturaf2,1280/2-50,800/2-200,1);
                flamas.add(lujuria);
            }
            else if(i==2)
            {
                Lujuria lujuria = new Lujuria(texturaf3,1280/2-50,800/2-200,1);
                flamas.add(lujuria);
            }
        }
        cargarRecursos();
        Gdx.input.setCatchBackKey(true);
    }

    private void cargarRecursos()
    {
        assetManager.load("piedras_inicio.png",Texture.class);
        assetManager.load("btnPlay.png",Texture.class);
        assetManager.load("btnSettings.png",Texture.class);
        assetManager.load("btnAboutUs.png",Texture.class);
        assetManager.load("btnScore.png",Texture.class);
        assetManager.load("Scores.png",Texture.class);
        assetManager.load("back.png",Texture.class);
        assetManager.load("settings.png",Texture.class);
        assetManager.load("back2.png",Texture.class);
        assetManager.load("btnNo.png",Texture.class);
        assetManager.load("btnYes.png",Texture.class);
        assetManager.load("btnNo_A.png",Texture.class);
        assetManager.load("btnYes_A.png",Texture.class);
        assetManager.load("LujuriaP1.png",Texture.class);
        assetManager.load("aboutUs.png",Texture.class);
        assetManager.load("Karlo.png",Texture.class);
        assetManager.load("Becky.png",Texture.class);
        assetManager.load("Marina.png",Texture.class);
        assetManager.load("Daniel.png",Texture.class);
        assetManager.load("Sam.png",Texture.class);
        assetManager.load("ok.png",Texture.class);
        assetManager.load("Reset.png",Texture.class);
        assetManager.load("Reset.png",Texture.class);
        assetManager.load("Reseted.png",Texture.class);
        /*
        assetManager.load("FondoSoberbia.png",Texture.class);
        assetManager.load("instruccionesSoberbia.png",Texture.class);
        */
    }

    @Override
    public void render(float delta)
    {
        actualizarCarga();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Aqui la flama flamea
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        if (y == 3)
        {
            x = rnd.nextInt(3);
            y = 0;
        }
        if (y!=3)
        {
            y++;
        }

        flamas.get(x).draw(batch);

        for(Lujuria l:flamas)
        {
            l.setRotation();
        }
        batch.end();
    }

    private void actualizarCarga()
    {
        if (assetManager.update())
        {
            Juego.setScreen(new MenuPrincipal(Juego));
        }
        else
        {
            float avance = assetManager.getProgress();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        vista.update(width,height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        texturaCargando.dispose();
        assetManager.unload("Splash.png");
    }
}
