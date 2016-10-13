package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Marina on 08/10/2016.
 */
//
public class Lobby implements Screen {
    private final juego juego;

    //Texturas
    private Texture texFondo;
    private Texture texCora;
    private Texture texTache;

    private SpriteBatch batch;
    private Fondo fondo;


    //array
    private Array <Corazon> arrCora;

    //texto
    private Texto texto;


    //Variables
    private int vidas;
    private int almas;
    private boolean estado;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private int temporizador=2;

    public Lobby(juego juego){
        this.juego=juego;
        this.vidas=3;
        this.almas=0;
        this.estado=true;
    }
    public Lobby(juego juego, int vidas, int almas, boolean estado){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.estado=estado;
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarCamara();
        crearEscena();
        texto=new Texto();


    }
    private void cargarCamara(){
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista=new FitViewport(ancho,alto,camara);
    }

    private void cargarTexturas(){
        texFondo=new Texture("Lobby.png");
        texCora=new Texture("vida.png");

    }

    private void crearEscena(){
        batch=new SpriteBatch();
        fondo=new Fondo(texFondo);

        arrCora=new Array<Corazon>(vidas);
        for(int i=1; i<=vidas;i++){

            Corazon cor=new Corazon(texCora,300+i*150,550);
            arrCora.add(cor);
        }



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

        for (Corazon h: arrCora){
            h.draw(batch);
        }
        texto.mostrarMensaje(batch,""+almas,720,490);
        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0){
            if (estado==false){
                Corazon h=arrCora.get(arrCora.size-1);
                h.setPosition();
                vidas-=1;
                juego.setScreen(new Avaricia(juego, vidas, almas));

            }else{
                juego.setScreen(new Avaricia(juego, vidas, almas));

            }

        }
        batch.end();


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

    }

    @Override
    public void dispose() {
        texCora.dispose();
        texFondo.dispose();
    }


}
