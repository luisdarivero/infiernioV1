package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by 808kh on 11/16/2016.
 */

public class Historia implements Screen, InputProcessor
{

    private final juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final int ancho = 1280;
    private final int alto = 720;
    private long startTime = System.currentTimeMillis();
    private int temporizador = 8;

    //SpriteBatch
    private SpriteBatch batch;


    private Texture texturaFondo;
    private Texture texturaFondo2;

    private Fondo  bfondo ;
    private Fondo fondo;

    //administra la carga de assets
    private final AssetManager assetManager ;//= new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //constructor
    public Historia(itesm.mx.juego juego)
    {
        this.juego =  juego;
        assetManager = juego.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        fondo = new Fondo(texturaFondo);
        bfondo =  new Fondo(texturaFondo2);
    }


    private void inicializarCamara()
    {
        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista = new StretchViewport(ancho,alto,camara);
    }

    public void cargarTexturas()
    {
        texturaFondo = new Texture("LogoTec.png");
        texturaFondo2 = new Texture("Titulo.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))>3)
            fondo.draw(batch);
        else
            bfondo.draw(batch);
        if((temporizador - ((System.currentTimeMillis() - startTime) / 1000)) <= 0)
        {
            juego.setScreen(new splashScreen(juego));
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose
    }

    @Override
    public void dispose() {
        //liberar los recursos utilizados en la memoria
        texturaFondo2.dispose();
        texturaFondo.dispose();
        /*
        assetManager.unload("Scores.png");
        assetManager.unload("back.png");
        */
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

