package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.*;


/**
 * Created by Daniel Riv on 16/09/2016.
 * Modified by Karlo on 10/31/16
 */
public class Score implements Screen, InputProcessor {

    Preferences prefs = Gdx.app.getPreferences("ScoresPref");
    boolean yes ;

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
    Lujuria btnBack ;

    //Para los scores
    private Map mapaP = new HashMap();
    private ArrayList<String> nombresL;
    private ArrayList<String> nombresAcomodados =new ArrayList<String>();

    private Texto scr1;
    private Texto scr2;
    private Texto scr3;
    private Texto scr4;
    private Texto scr5;
    private Texto scr1N;
    private Texto scr2N;
    private Texto scr3N;
    private Texto scr4N;
    private Texto scr5N;


    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //Setiings
    private Settings_save settings;

    //constructor
    public Score(itesm.mx.juego juego, Music musica, Settings_save settings){
        this.juego =  juego;
        this.musica = musica;
        this.settings=settings;
        preferencias();

        if (this.settings.getMusic()){
            musica.play();
        }

    }

    public Score(itesm.mx.juego juego, Music musica, boolean setName, Settings_save settings){
        this.juego =  juego;
        this.musica = musica;
        yes = setName;
        preferencias();
        this.settings=settings;
        if (this.settings.getMusic()){
            musica.play();
        }
    }

    private void preferencias() {
        prefs = Gdx.app.getPreferences("ScoresPref");

        if (prefs.getBoolean("Scores")) {
            int x = 0;
        } else {
            prefs.putBoolean("Scores", true);
            prefs.flush();
            prefs = Gdx.app.getPreferences("ScoresNames");
            prefs.putInteger("Karlo", 0);
            prefs.putInteger("Marina", 0);
            prefs.putInteger("Daniel", 0);
            prefs.putInteger("Becky", 0);
            prefs.putInteger("Samantha", 0);
            prefs.flush();
        }
        prefs = Gdx.app.getPreferences("ScoresNames");
        mapaP = prefs.get();
        Set keys = mapaP.keySet();
        nombresL = new ArrayList<String>(keys);

        for (int j = 0; j < 5; j++)
        {
            String max = nombresL.get(0);
            for (int i = 0; i < nombresL.size(); i++) {
                if(i!=nombresL.size()-1)
                if (Integer.parseInt(mapaP.get(max).toString()) <= Integer.parseInt((mapaP.get(nombresL.get(i + 1))).toString()))
                {
                    max = nombresL.get(i + 1);
                }
            }
            nombresL.remove(max);
            nombresAcomodados.add(max);
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        scr1 = new Texto(true);
        scr2 = new Texto(true);
        scr3 = new Texto(true);
        scr4 = new Texto(true);
        scr5 = new Texto(true);
        scr1N = new Texto(true);
        scr2N = new Texto(true);
        scr3N = new Texto(true);
        scr4N = new Texto(true);
        scr5N = new Texto(true);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        fondo = new Fondo(texturaFondo);
        btnBack =  new Lujuria(texturaBtnBack,ancho*.02f,alto * .02f,4);
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
        btnBack.draw(batch);
        btnBack.setRotation();

        scr1.mostrarMensaje(batch, mapaP.get(nombresAcomodados.get(0)).toString(), ancho/2+140, 525);
        scr1N.mostrarMensaje(batch,nombresAcomodados.get(0), ancho/2-150, 525);

        scr2.mostrarMensaje(batch, mapaP.get(nombresAcomodados.get(1)).toString(), ancho/2+140, 465);
        scr2N.mostrarMensaje(batch,nombresAcomodados.get(1), ancho/2-150, 465);

        scr3.mostrarMensaje(batch, mapaP.get(nombresAcomodados.get(2)).toString(), ancho/2+140, 405);
        scr3N.mostrarMensaje(batch,nombresAcomodados.get(2), ancho/2-150, 405);

        scr4.mostrarMensaje(batch, mapaP.get(nombresAcomodados.get(3)).toString(), ancho/2+140, 345);
        scr4N.mostrarMensaje(batch,nombresAcomodados.get(3), ancho/2-150, 345);

        scr5.mostrarMensaje(batch, mapaP.get(nombresAcomodados.get(4)).toString(), ancho/2+140, 285);
        scr5N.mostrarMensaje(batch,nombresAcomodados.get(4), ancho/2-150, 285);

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
        if (btnBack.contiene(x,y)&& this.yes == true)
        {
            musica.stop();
            juego.setScreen(new MenuPrincipal(juego));
        }
        else if (btnBack.contiene(x,y)&& this.yes != true)
        {
            juego.setScreen(new MenuPrincipal(juego,musica,settings));//recuperar musica
        }
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

