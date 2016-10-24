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
    private Texture texturaFondo;

    private SpriteBatch batch;

    private OrthographicCamera camara;
    private Viewport vista;
    private Fondo fondo;

    private Texture texDinero;

    //Texto
    private Texto texTexto_a;
    private Texto texTexto_b;

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



    public Avaricia(juego juego, int vidas, int almas, int nivel, Dificultad escNivel){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.escNivel=escNivel;
        if (nivel==1)
            this.temporizador=5;
        else if(nivel==2)
            this.temporizador=4;
        else if (nivel>=3)
            this.temporizador=3;

    }

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        texTexto_a = new Texto("fuenteAv_a.fnt");
        texTexto_b = new Texto("fuenteAv_b.fnt");
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
        if (binario==1)
            texDinero=new Texture("Avaricia.png");
        else
            texDinero=new Texture("AVariciaFalso.png");

    }

    private void crearEscena(){
        batch=new SpriteBatch();
        fondo=new Fondo(texturaFondo);
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
        fondo.setSizeF(0, 10);

        b.draw(batch);
        //Texto
        texTexto_a.mostrarMensaje(batch,"touch the money", 750, 700);
        texTexto_b.mostrarMensaje(batch,"Don't", 460, 695);

        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0){

            switch (binario){
                case 0:
                    almas+=1;
                    juego.setScreen(new Lobby(juego,vidas,almas,true,escNivel));
                    break;
                case 1:
                    juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel));
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
                    juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel));
                    break;
                case 1:
                    almas+=1;
                    juego.setScreen(new Lobby(juego,vidas,almas,true,escNivel));
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
