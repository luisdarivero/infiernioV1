package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
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
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

/**
KRLO 2/11/16
 */
public class SetName implements Screen, InputProcessor, Input.TextInputListener{
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //tope de chars
    private boolean chars = false;
    private boolean top = false;

    //prefs
    Preferences prefs = Gdx.app.getPreferences("ScoresPref");

    //Para los scores
    private Map mapaP = new HashMap();
    private ArrayList<String> nombresL;

    //puntos a recibir
    int pointer ;

    //teclado
    private boolean teclado =false;

    //Texto
    private String text;
    private ArrayList<String> nombresAcomodados =new ArrayList<String>();



    //textura para la imagen de fondo
    private Texture texturaFondo;
    private Fondo imgFondo;
    private Texture texturaFondo2;
    private Fondo imgFondo2;
    private long startTime;
    private long temporizador = 3;

    //SpriteBatch
    private SpriteBatch batch;

    private final AssetManager assetManager;// = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    private Settings_save settings;

    //constructor
    public SetName(itesm.mx.juego juego, int points,Settings_save settings){
        this.juego =  juego;
        pointer = points;
        assetManager = juego.getAssetManager();
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("gameOver.mp3"));
        preferencias();
        this.top = compararScores();
        this.settings=settings;
        if (this.settings.getMusic()){
            musica.setLooping(true);
            musica.play();
            musica.setVolume(0.6f);
        }
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

    private void preferencias()
    {
        prefs = Gdx.app.getPreferences("ScoresPref");

        if(prefs.getBoolean("Scores"))
        {
            int x = 0;
        }
        else
        {
            prefs.putBoolean("Scores",true);
            prefs.flush();
            prefs = Gdx.app.getPreferences("ScoresNames");
            prefs.putInteger("Karlo",0);
            prefs.putInteger("Marina",0);
            prefs.putInteger("Daniel",0);
            prefs.putInteger("Becky",0);
            prefs.putInteger("Samantha",0);
            prefs.flush();
        }
        prefs = Gdx.app.getPreferences("ScoresNames");
        mapaP = prefs.get();
        Set keys = mapaP.keySet();
        nombresL = new ArrayList<String>(keys);
    }

    public void cargarTexturas(){

        assetManager.load("GameOver.png",Texture.class);
        assetManager.load("Continue.png",Texture.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("GameOver.png");
        texturaFondo2 = assetManager.get("Continue.png");
    }

    public boolean compararScores()
    {
        boolean top =false;
        for (String name: nombresL)
        {
            if(Integer.parseInt(mapaP.get(name).toString())<=pointer)
            {
                top = true;
            }
        }
        return top;
    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        boolean im = false;
        batch.begin();
        if(teclado == false)
        {
            imgFondo.draw(batch);
        }
        else if (teclado == true && (text != null)||(temporizador - ((System.currentTimeMillis() - startTime) / 1000))<=1)
        {
            imgFondo2.draw(batch);
        }

        if(Gdx.input.justTouched() && teclado == false && top == true)
        {
            Gdx.input.getTextInput(this, "Type your name", "", "Type here");
            teclado = true;
            if(!im)
            {
                startTime = System.currentTimeMillis();
                im = true;
            }
        }

        if(Gdx.input.justTouched() && teclado == false && top == false)
        {
            teclado = true;
        }

        if(teclado == true && Gdx.input.justTouched() && text != null)
        {
            StringBuilder cincoChar = new StringBuilder();
            char[] cinco = text.toCharArray();
            if(cinco.length>=8)
            {
                for (int i = 0; i < 8; i++)
                    cincoChar.append(cinco[i]);
                this.text = cincoChar.toString();
                System.out.println(cincoChar.toString());
            }
            escribirScores(text);
            juego.setScreen(new Score(juego,musica,true,settings));
        }

        if(teclado == true && Gdx.input.justTouched() &&  text == null && (temporizador - ((System.currentTimeMillis() - startTime) / 1000))<=0)
        {

            escribirScores(text);
            juego.setScreen(new Score(juego,musica,true,settings));
        }


        if(teclado == true && Gdx.input.justTouched() && top == false)
        {
            juego.setScreen(new Score(juego,musica,true,settings));
        }
        System.out.println((temporizador - ((System.currentTimeMillis() - startTime) / 1000)));
        batch.end();
    }

    private void escribirScores(String texto)
    {
        prefs = Gdx.app.getPreferences("ScoresNames");
        String max = "";
        if(!texto.equals( "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"))
        {
            if(texto.equals("")||texto.equals(" ")||texto.equals("  ")||texto.equals("\n"))
            {
                texto = "No Name";
                mapaP.put(texto,pointer);
            }
        }
        Set keys = mapaP.keySet();
        ArrayList<String> keis = new ArrayList<String>(keys);
        for(String s: nombresL)
        {
            prefs.remove(s);
        }
        nombresL = new ArrayList<String>(keys);

        for (int j = 0; j < 5; j++)
        {
            max = nombresL.get(0);
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

        for(String x: nombresAcomodados )
        {
            prefs.putInteger(x,Integer.parseInt(mapaP.get(x).toString()));
        }
        prefs.flush();
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
    public void dispose()
    {
        texturaFondo.dispose();
        /*
        assetManager.unload("GameOver.png");
        assetManager.unload("Continue.png");
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
        this.text =text;
    }

    @Override
    public void canceled()
    {
        this.text = "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    }

}

