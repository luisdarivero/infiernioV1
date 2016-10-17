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

/**
 * Created by Marina on 16/10/2016.
 */

public class Envidia implements Screen, InputProcessor {

    private final juego juego;

    //Texturas
    private Texture texFondo;
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

    //Texto
    private Texto textoIns;
    private Texto texCont;

    //Monedas array
    private Array<Monedas> monedasA;
    private Array<Monedas> monedasB;

    //variables
    int vidas;
    int almas;
    private Dificultad escNivel;
    private int contador;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private int temporizador=6;



    public Envidia(juego juego, int vidas, int almas, int temporizador, Dificultad escNivel ){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.temporizador-=temporizador;
        this.escNivel=escNivel;
    }



    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        textoIns=new Texto("fuenteAv_a.fnt");
        texCont=new Texto("fuenteAv_a.fnt");
    }

    private void crearEscena() {
        batch=new SpriteBatch();
        fondo=new Fondo(texFondo);

        float rnd=(float)Math.random() * (1200-10)+10;

        //agragar monedas A
        monedasA = new Array<Monedas>(5);
        for (int i=0;i<5;i++){
            Monedas monA=new Monedas(texMonedaA,rnd,720);
            monedasA.add(monA);
            rnd=(float)Math.random() * (1200-10)+10;
        }

        //agragar monedas b
        monedasB = new Array<Monedas>(5);
        rnd=(float)Math.random() * (1200-10)+10;
        for (int i=0;i<5;i++){
            Monedas monB=new Monedas(texMonedaB,rnd, 720);
            monedasB.add(monB);
            rnd=(float)Math.random() * (1200-10)+10;
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        //Fondo
        fondo.draw(batch);

        //Monedas A
        for (Monedas mA: monedasA){
            mA.draw(batch);
        }

        //Monedas B
        for (Monedas mB: monedasB){
            mB.draw(batch);
        }

        texCont.mostrarMensaje(batch,"Marcador: "+contador,600,200);
        if(contador>=5){
            juego.setScreen(new Lobby(juego,vidas,almas+1,true,escNivel));
        }
        else if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0&&contador==5){

            almas+=1;
            juego.setScreen(new Lobby(juego,vidas,almas,true,escNivel));
        }else if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0){
            juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel));
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
                contador++;
            }
        }
        /*for (Monedas mB:monedasB){
            if(mB.contiene(x,y)){
                juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel));
            }
        }*/
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
