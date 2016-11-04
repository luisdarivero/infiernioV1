package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    //camara
    private OrthographicCamera camara;
    private Viewport vista;
    private final float ancho = 1280;
    private final float alto = 720;

    //clases
    private Fondo fondo;
    private Fondo instr;

    //Texto
    private Texto textoIns;
    private Texto texCont;
    private Texto textTiempo;

    //Monedas array
    private Array<Monedas> monedasA;
    private Array<Monedas> monedasB;

    //variables
    private int vidas;
    private int almas;
    private Dificultad escNivel;
    private int contador;
    private float velocidad;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private int temporizador=8;
    private int tiempoInit;


    public Envidia(juego juego, int vidas, int almas, int nivel, Dificultad escNivel ){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.escNivel=escNivel;

        if (nivel==1){
            velocidad=0.3f;
        }else if(nivel==2){
            velocidad=0.6f;
        }else{
            velocidad=0.9f;
        }

        //extras
        this.tiempoInit=temporizador-1;
    }



    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        textoIns=new Texto("fuenteAv_a.fnt");
        texCont=new Texto("fuenteAv_a.fnt");
        textTiempo=new Texto("fuenteAv_a.fnt");
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

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

            texCont.mostrarMensaje(batch, "Marcador: " + contador, 600, 200);
            textTiempo.mostrarMensaje(batch, "Time: " + (temporizador - ((System.currentTimeMillis() - startTime) / 1000)), 640, 700);

            for (Monedas mA : monedasA) {
                if (mA.getyActual() < -40) {
                    juego.setScreen(new Lobby(juego, vidas, almas, false, escNivel));
                }
            }
            if ((temporizador - ((System.currentTimeMillis() - startTime) / 1000)) <= 0) {
                almas += 1;
                juego.setScreen(new Lobby(juego, vidas, almas, true, escNivel));
            }
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
        Vector3 v=new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x=v.x;
        float y=v.y;
        for (Monedas mA:monedasA){
            if (mA.contiene(x,y)){
                //le pegó
                mA.setEstado(Monedas.Estado.GOLPEADO);
                contador++;
            }
        }
        for (Monedas mB:monedasB){
            if(mB.contiene(x,y)){
                juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel));
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
}
