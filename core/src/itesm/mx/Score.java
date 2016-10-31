package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.*;


//TODO:Acomodar el texto

/**
 * Created by Daniel Riv on 16/09/2016.
 * Modified by Karlo on 10/31/16
 */
public class Score implements Screen, InputProcessor {

    FileHandle scrs = Gdx.files.internal("scores.txt");

    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final int ancho = 1280;
    private final int alto = 720;

    //SpriteBatch
    private SpriteBatch batch;
    private Fondo fondo;

    //textura para la imagen de fondo
    private Texture texturaFondo;

    //texturas de las demas imagenes
    private Texture texturaBtnBack;

    //Para los scores
    private ArrayList<String> nombres = new ArrayList<String>(5);
    private ArrayList<Integer> scores = new ArrayList<Integer>(5);
    private Texto scr1;
    private Texto scr2;
    private Texto scr3;
    private Texto scr4;
    private Texto scr5;


    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //constructor
    public Score(itesm.mx.juego juego, Music musica){
        this.juego =  juego;
        this.musica = musica;
        cargarScores();
    }

    //
    private void cargarScores() {
        String alle = scrs.readString();
        StringBuilder sb = new StringBuilder(50);
        char[] arrCar = alle.toCharArray();

        for(int i=0; i< arrCar.length; i++)
        {
            if(arrCar[i]=='\n')
            {
                scores.add(Integer.parseInt(sb.toString()));
                sb = new StringBuilder();
            }
            else if (arrCar[i]==' ')
            {
                nombres.add(sb.toString());
                sb = new StringBuilder();
            }
            if(Character.isDigit(arrCar[i])||Character.isLetter(arrCar[i]))
            {
                sb.append(arrCar[i]);
            }
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        scr1 = new Texto();
        scr2 = new Texto();
        scr3 = new Texto();
        scr4 = new Texto();
        scr5 = new Texto();
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        fondo = new Fondo(texturaFondo);
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
        texturaFondo = new Texture("Scores.png");
        texturaBtnBack = new Texture("back.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //aki se dibujan los elementos
        fondo.draw(batch);

        scr1.mostrarMensaje(batch, scores.get(0)+" "+nombres.get(0), ancho/2, 720);
        scr2.mostrarMensaje(batch, scores.get(1)+" "+nombres.get(1), ancho/2, 620);
        scr3.mostrarMensaje(batch, scores.get(2)+" "+nombres.get(2), ancho/2, 520);
        scr4.mostrarMensaje(batch, scores.get(3)+" "+nombres.get(3), ancho/2, 420);
        scr5.mostrarMensaje(batch, scores.get(4)+" "+nombres.get(4), ancho/2, 320);
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
        texturaBtnBack.dispose();
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
}

