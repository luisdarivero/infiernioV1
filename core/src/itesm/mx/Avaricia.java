package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Sam on 13/09/2016.
 */


public class Avaricia implements Screen, InputProcessor {
    private final juego juego;


    private SpriteBatch batch;

    private OrthographicCamera camara;
    private Viewport vista;

    //texturas y fondos
    private Fondo instr;
    private Fondo fondo;

    private Texture texturaFondo;
    private Texture textInstr;
    private Texture texDinero;

    //Texto
    private Texto texTiempo;

    //billete
    Billete b;

    //varibles
    int vidas;
    int almas;
    private Dificultad escNivel;
    private int binario;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private int temporizador;
    private int tiempoInit;

    //settings
    private Settings_save settings;


    public Avaricia(juego juego, int vidas, int almas, int nivel, Dificultad escNivel, Settings_save settings){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.escNivel=escNivel;
        this.settings=settings;
        if (nivel==1)
            this.temporizador=7;
        else if(nivel==2)
            this.temporizador=6;
        else if (nivel>=3)
            this.temporizador=5;

        //extras
        this.tiempoInit=temporizador-1;

    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        texTiempo = new Texto("fuenteAv_a.fnt");
    }
    private void inicializarCamara(){
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista=new FitViewport(ancho,alto,camara);
    }

    private void cargarTexturas(){
        texturaFondo=new Texture("FondoA.png");
        int range = (1-0) + 1;
        binario= (int)(Math.random() * range) + 0;
        textInstr=new Texture("InstruccionesAvaricia.png");

        if (binario==1)
            texDinero=new Texture("Avaricia.png");// toco
        else
            texDinero=new Texture("AvariciaFalso.png");//no toco

    }

    private void crearEscena(){
        batch=new SpriteBatch();
        fondo=new Fondo(texturaFondo);
        instr=new Fondo(textInstr);
        b=new Billete(texDinero,0,0);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Avisar a batch cual es la camara que usamos
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //Dibujar todos los elementos
        fondo.draw(batch);
        //fondo.setSizeF(0, 10);


        if ((temporizador - ((System.currentTimeMillis() - startTime)/1000)) >=tiempoInit ){
            instr.draw(batch);
        }
        else{
            b.draw(batch);
            texTiempo.mostrarMensaje(batch,"Time: "+(temporizador - ((System.currentTimeMillis() - startTime) / 1000)),640,700);
        }
        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0){

            switch (binario){
                case 0:
                    almas+=1;
                    juego.setScreen(new Lobby(juego,vidas,almas,true,escNivel,settings));
                    break;
                case 1:
                    juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel,settings));
                    break;
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
        texturaFondo.dispose();
        textInstr.dispose();
        texDinero.dispose();

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
        //Verificar si le pego a un topo
        //Transformar coordenadas
        Vector3 v=new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x=v.x;
        float y=v.y;
        if (b.contiene(x,y)){
                //Toco el billete;
            switch (binario){
                case 0:
                    juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel,settings));
                    break;
                case 1:
                    almas+=1;
                    juego.setScreen(new Lobby(juego,vidas,almas,true,escNivel,settings));
                    break;

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
