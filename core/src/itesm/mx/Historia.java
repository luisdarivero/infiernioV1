package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
    private int temporizador = 19;
    private int[] hojas = {2,3,4,5};
    private int hoja1 = 15;
    private int hoja6=10;
    private int hoja7=5;
    private int hoja8=0;
    private int select = 1;
    private final Music musica;

    //SpriteBatch
    private SpriteBatch batch;


    private Texture t1;
    private Texture t2;
    private Texture t3;
    private Texture t4;
    private Texture t5;
    private Texture t6;
    private Texture t7;
    private Texture t8;

    private Fondo f1;
    private Fondo f2;
    private Fondo f3;
    private Fondo f4;
    private Fondo f5;
    private Fondo f6;
    private Fondo f7;
    private Fondo f8;

    //administra la carga de assets
    private final AssetManager assetManager ;//= new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //settings
    private Settings_save settings;

    //constructor
    public Historia(itesm.mx.juego juego, Settings_save settings)
    {
        this.juego =  juego;
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("c2.mp3"));
        assetManager = juego.getAssetManager();
        this.settings=settings;
    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        this.musica.play();
        this.musica.setVolume(0.4f);
        Gdx.input.setInputProcessor(this);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        f1 = new Fondo(t1);
        f2 =  new Fondo(t2);
        f3 =  new Fondo(t3);
        f4 =  new Fondo(t4);
        f5 =  new Fondo(t5);
        f6 =  new Fondo(t6);
        f7 =  new Fondo(t7);
        f8 =  new Fondo(t8);
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
        t1 = new Texture("libro1.png");
        t2 = new Texture("libro2.png");
        t3=new Texture("libro3.png");
        t4=new Texture("libro4.png");
        t5=new Texture("libro5.png");
        t6=new Texture("libro6.png");
        t7=new Texture("libro7.png");
        t8=new Texture("libro8.png");
    }

    public void cambioHojas(long x)
    {
        int[] arr = {150,300,450};
        if((((x - (System.currentTimeMillis() - startTime))*(-1))%1000)<=arr[0])
            f2.draw(batch);
        if((((x - (System.currentTimeMillis() - startTime))*(-1))%1000)>arr[0] &&(((x - (System.currentTimeMillis() - startTime))*(-1))%1000)<=arr[1] )
            f3.draw(batch);
        if((((x - (System.currentTimeMillis() - startTime))*(-1))%1000)>arr[1] &&(((x - (System.currentTimeMillis() - startTime))*(-1))%1000)<=arr[2] )
            f4.draw(batch);
        if((((x - (System.currentTimeMillis() - startTime))*(-1))%1000)>arr[2])
            f5.draw(batch);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))>hoja1 && select ==1)
            f1.draw(batch);
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))==hoja1) {
            cambioHojas(hoja1);
            select =6;
        }
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))>hoja6 && select ==6)
            f6.draw(batch);
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))==hoja6){
            cambioHojas(hoja6);
            select =7;
        }
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))>hoja7 && select == 7)
            f7.draw(batch);
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))==hoja7){
            cambioHojas(hoja7);
            select =8;
        }
        else if((temporizador - ((System.currentTimeMillis() - startTime) / 1000))>hoja8 && select == 8)
            f8.draw(batch);

        if((temporizador - ((System.currentTimeMillis() - startTime) / 1000)) <= 0)
        {
            this.musica.stop();
            juego.setScreen(new Lobby(juego,settings));
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
        t1.dispose();
        t2.dispose();
        t3.dispose();
        t4.dispose();
        t5.dispose();
        t6.dispose();
        t7.dispose();
        t8.dispose();
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

