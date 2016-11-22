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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by Marina on 16/10/2016.
 */

public class Envidia implements Screen, InputProcessor {

    private final juego juego;

    //Texturas
    private Texture texFondo;
    private Texture texInstr;
    private Texture texMonedaA;
    private Texture texMonedaB;

    //Batch
    private SpriteBatch batch;

    //administra la carga de assets
    private final AssetManager assetManager;// = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;
    private final float ancho = 1280;
    private final float alto = 720;

    //implementacion de menu pausa
    private Estado estado;


    private Sprite btnPausa;
    private Sprite fondoPausa;
    private Sprite btnContinuar;
    private Sprite btnSalir;


    //clases
    private Fondo fondo;
    private Fondo instr;

    //Texto
    private Texto textoIns;
    private Texto textTiempo;

    //Monedas array
    private Array<Monedas> monedasA;
    private Array<Monedas> monedasB;

    //variables
    private int vidas;
    private int almas;
    private Dificultad escNivel;
    private float velocidad;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private long temporizador=10;
    private long tiempoInit;
    private boolean ok1 =false;
    private long pausaT;

    //Musica
    private final Music Musica;
    private final Music ok;

    //settings
    private Settings_save settings;


    public Envidia(juego juego, int vidas, int almas, int nivel, Dificultad escNivel, Settings_save settings ){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        assetManager = juego.getAssetManager();
        this.escNivel=escNivel;
        this.settings=settings;

        if (nivel==1){
            velocidad=0.4f;
        }else if(nivel==2){
            velocidad=0.8f;
        }else{
            velocidad=1.2f;
        }

        //extras
        this.tiempoInit=temporizador-2;
        this.Musica=Gdx.audio.newMusic(Gdx.files.internal("time.mp3"));
        this.ok=Gdx.audio.newMusic(Gdx.files.internal("OK.mp3"));
        this.settings=settings;

        if(this.settings.getMusic()){
            Musica.play();
        }
    }



    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        textoIns=new Texto("fuenteAv_a.fnt");
        textTiempo=new Texto("fuenteAv_a.fnt");
        estado = Estado.Normal;
        Gdx.input.setCatchBackKey(true);
    }

    private void crearEscena() {
        batch=new SpriteBatch();
        fondo=new Fondo(texFondo);
        instr=new Fondo(texInstr);


        //pos
        ArrayList<Integer> pos=new ArrayList<Integer>(10);
        for (int j=0;j<10;j++){
            pos.add(j*120);
        }

        int range = ((pos.size()-1) -0) + 1;
        int rnd=(int)(Math.random() * range) + 0;
        //agragar monedas A
        monedasA = new Array<Monedas>(5);
        for (int i=0;i<5;i++){
            Monedas monA=new Monedas(texMonedaA,pos.get(rnd),720);
            monA.setVelocidad(velocidad);
            monedasA.add(monA);
            pos.remove(rnd);
            range = ((pos.size()-1) -0) + 1;
            rnd=(int)(Math.random() * range) + 0;

        }

        //agragar monedas b
        range = ((pos.size()-1) -0) + 1;
        rnd= (int)(Math.random() * range) + 0;

        monedasB = new Array<Monedas>(5);
        rnd= (int)(Math.random() * range) + 0;
        for (int i=0;i<5;i++){

            Monedas monB=new Monedas(texMonedaB,pos.get(rnd), 720);
            monB.setVelocidad(velocidad);
            monedasB.add(monB);
            pos.remove(rnd);
            range = ((pos.size()-1) -0) + 1;
            rnd= (int)(Math.random() * range) + 0;
        }

    }

    private void inicializarCamara() {
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista=new FitViewport(ancho,alto,camara);
    }

    private void cargarTexturas() {
        texMonedaA=new Texture("heladoA.png");
        texMonedaB=new Texture("heladoB.png");
        texFondo=new Texture("fondop.png");
        texInstr=new Texture("instrucciones_envidia.png");

        //para declarar los elementos de la pausa
        btnPausa = new Sprite(new Texture("pausaNS.png"));

        fondoPausa = new Sprite(new Texture("Pausa.png"));
        fondoPausa.setCenter(ancho/2,alto/2);
        btnContinuar = new Sprite(new Texture("botonContinuar.png"));
        btnContinuar.setCenter(ancho/3,alto/2);
        btnSalir = new Sprite(new Texture("botonSalir.png"));
        btnSalir.setCenter(ancho/3*2,alto/2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        if(estado == Estado.Pausa){
            fondo.draw(batch);
            fondoPausa.draw(batch);
            btnContinuar.draw(batch);
            btnSalir.draw(batch);

            if(ok1==false)
            {
                temporizador = pausaT;
                ok1=true;
            }
        }
        else{
            //Fondo

            fondo.draw(batch);

            if ((temporizador - ((System.currentTimeMillis() - startTime) / 1000)) >= tiempoInit) {
                instr.draw(batch);
            } else {

                //Monedas A

                for (Monedas mA : monedasA) {
                    mA.draw(batch);
                }

                //Monedas B

                for (Monedas mB : monedasB) {
                    mB.draw(batch);
                }

                textTiempo.mostrarMensaje(batch, "Time: " + (temporizador - ((System.currentTimeMillis() - startTime) / 1000)), 640, 700);

            }
            for (Monedas mA : monedasA) {
                if (mA.getyActual() < -40) {
                    juego.setScreen(new Lobby(juego, vidas, almas, false, escNivel,settings));
                    Musica.stop();
                    ok.stop();
                }
            }
            if ((temporizador - ((System.currentTimeMillis() - startTime) / 1000)) <= 0 && estado==Estado.Normal) {
                almas += 1;
                juego.setScreen(new Lobby(juego, vidas, almas, true, escNivel,settings));
                Musica.stop();
                ok.stop();
            }
            btnPausa.draw(batch);
        }
        batch.end();



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texFondo.dispose();
        texInstr.dispose();
        texMonedaA.dispose();
        texMonedaB.dispose();
        Musica.dispose();
        ok.dispose();/*
        assetManager. unload("heladoA.png");
        assetManager. unload("heladoB.png");
        assetManager. unload("fondop.png");
        assetManager. unload("instrucciones_envidia.png");
        assetManager. unload("instrucciones_envidia.png");
        assetManager. unload("pausaNS.png");
        assetManager. unload("Pausa.png");
        assetManager. unload("botonContinuar.png");
        assetManager. unload("botonSalir.png");*/
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode== Input.Keys.BACK) {
            // cambia a pausa
            estado = Estado.Pausa;
            Musica.pause();
            ok1 = false;
            pausaT = (temporizador - ((System.currentTimeMillis() - startTime) / 1000)); // Cambio a pausa
        }
        return true;
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
        Vector3 v=new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x=v.x;
        float y=v.y;


        if(btnPausa.getBoundingRectangle().contains(x,y)){
            estado = Estado.Pausa;
            Musica.pause();
            ok.pause();
            ok1 = false;
            pausaT = (temporizador - ((System.currentTimeMillis() - startTime) / 1000));
            return false;
        }

        if(estado == Estado.Pausa){
            if(btnContinuar.getBoundingRectangle().contains(x,y)){
                Musica.play();
                ok.pause();
                startTime = System.currentTimeMillis();
                estado = Estado.Normal;
                return false;
            }
            else if(btnSalir.getBoundingRectangle().contains(x,y)){
                juego.setScreen(new MenuPrincipal(juego,settings));
                Musica.stop();
                ok.stop();
                return false;
            }
        }


        for (Monedas mA:monedasA){
            if (mA.contiene(x,y)){
                //le pegó
                ok.stop();
                mA.setEstado(Monedas.Estado.GOLPEADO);
                ok.setVolume(.4f);
                ok.play();
            }
        }
        for (Monedas mB:monedasB){
            if(mB.contiene(x,y)){
                Musica.stop();
                ok.stop();
                juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel,settings));
            }
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

    public enum Estado{

        Normal, Pausa
    }

}
