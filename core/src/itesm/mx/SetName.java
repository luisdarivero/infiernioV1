package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
KRLO 2/11/16
 */
public class SetName implements Screen, InputProcessor, Input.TextInputListener{
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //teclado
    private boolean teclado =false;

    //Texto
    private String text;

    //textura para la imagen de fondo
    private Texture texturaFondo;
    private Fondo imgFondo;
    private Texture texturaFondo2;
    private Fondo imgFondo2;

    //SpriteBatch
    private SpriteBatch batch;

    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //constructor
    public SetName(itesm.mx.juego juego, Music musica){
        this.juego =  juego;
        this.musica = musica;
    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
    }

    private void inicializarCamara()
    {
        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista = new StretchViewport(ancho,alto,camara);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        imgFondo = new Fondo(texturaFondo);
        imgFondo2 = new Fondo(texturaFondo2);
    }


    public void cargarTexturas(){

        assetManager.load("gO.jpg",Texture.class);
        assetManager.load("gO2.png",Texture.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("gO.jpg");
        texturaFondo2 = assetManager.get("gO2.png");
    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        if(teclado == false)
        {
            imgFondo.draw(batch);
        }
        else if (teclado == true && text != null)
        {
            imgFondo2.draw(batch);
        }

        if(Gdx.input.justTouched() && teclado == false)
        {
            Gdx.input.getTextInput(this, "Type your name", "", "Type here");
            teclado = true;
        }

        if(teclado == true && Gdx.input.justTouched() && text != null)
        {
            //TODO:WRITE TO FILE
            musica.stop();
            juego.setScreen(new Score(juego));
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
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
        texturaFondo.dispose();
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
        Vector3 v = new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x = v.x;
        float y = v.y;

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


//Texto

    @Override
    public void input(String text)
    {
        this.text = text;
    }

    @Override
    public void canceled() {

    }
}

