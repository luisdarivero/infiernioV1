package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Collections;

/**
KRLO 2/11/16
 */
public class SetName implements Screen, InputProcessor, Input.TextInputListener{
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //puntos a recibir
    int pointer ;

    //teclado
    private boolean teclado =false;

    //Texto
    private String text;

    //Para los scores
    FileHandle scrs = Gdx.files.internal("scores.txt");
    private ArrayList<String> nombres = new ArrayList<String>(6);
    private ArrayList<Integer> scores = new ArrayList<Integer>(6);
    private ArrayList<String> nombresOrd = new ArrayList<String>(6);
    private ArrayList<Integer> scoresOrd = new ArrayList<Integer>(6);

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

    //constructor
    public SetName(itesm.mx.juego juego, int points, Music musica){
        this.juego =  juego;
        pointer = points;
        //TODO: quitar musica
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

        assetManager.load("GameOver.png",Texture.class);
        assetManager.load("Continue.png",Texture.class);
        assetManager.finishLoading();
        texturaFondo = assetManager.get("GameOver.png");
        texturaFondo2 = assetManager.get("Continue.png");
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
            boolean exist = Gdx.files.external("scores.txt").exists();
            if(exist)
            {
                scrs = Gdx.files.external("scores.txt");
                cargarScores();
            }else
            {
                cargarScores();
            }
            escribirScores(text);
            musica.stop();
            juego.setScreen(new Score(juego));
        }
        batch.end();
    }

    private void escribirScores(String texto)
    {
        pointer=14;
        boolean added;
        scores.add(pointer);
        nombres.add(texto);
        System.out.println(nombres);
        System.out.println(scores);
        for (Integer i : scores)
        {
            scoresOrd.add(i);
        }
        Collections.sort(scoresOrd);
        Collections.reverse(scoresOrd);
        for (Integer u : scoresOrd)
        {
            added =false;
            for (int k = 0;k< scores.size();k++)
            {
                if (u == scores.get(k) && added == false && Collections.frequency(nombresOrd,nombres.get(k)) < Collections.frequency(nombres,nombres.get(k)))
                {
                    nombresOrd.add(nombres.get(k));
                    added = true;
                }
            }
        }
        scrs = Gdx.files.external("scores.txt");
        scrs.writeString(nombresOrd.get(0)+" "+scoresOrd.get(0)+'|', false);
        for (int i = 1;i<=4;i++)
        {
            scrs.writeString(nombresOrd.get(i)+" "+scoresOrd.get(i)+'|', true);
        }
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

    private void cargarScores() {
        String alle = scrs.readString();
        StringBuilder sb = new StringBuilder(50);
        char[] arrCar = alle.toCharArray();

        for(int i=0; i< arrCar.length; i++)
        {
            if(arrCar[i]=='|')
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

}

