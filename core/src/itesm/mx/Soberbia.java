package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

/**
 * Created by Daniel Riv on 07/10/2016.
 */
public class Soberbia implements Screen, InputProcessor {

    //variable tipo juego para poder intarcambiar de escenas
    private final itesm.mx.juego juego;

    //variables constantes del ancho y largo de la pantalla
    private final float ancho = 1280;
    private final float alto = 720;

    //escena para la pantalla
    private Stage escena;

    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //spritebatch . administra trazos
    private SpriteBatch batch;

    //para saber si se ponen las intstrucciones
    private boolean instrucciones;

    //variables para el Lobby
    private  int vidas;
    private int almas ;
    private boolean sizeF = false;
    private Dificultad escNivel;

    private  int nivel;
    //constructor
    public Soberbia(juego Juego, int vidas, int almas, int nivel, Dificultad escNivel ){
        this.juego=Juego;
        this.vidas=vidas;
        this.almas=almas;
        this.nivel = nivel;
        this.escNivel=escNivel;

    }


    //texturas
    //private Texture texturaback;
    private Texture texturaFondo;
    private Texture texturaInstrucciones;
    private  Image imgInstrucciones;
    private  Image imgFondo;
    //private Sprite botonRojo;
    //private Sprite botonAzul;
    //private Sprite[] listaMovibles;
    private FichaSoberbia[] listaMovibles;
    private FichaSoberbia[] listaEstaticas;


    //manejador del tiempo
    private float deltaTime;

    //para manejar el arrastrado
    private boolean estaTocando;
    private float xAnt;
    private float yAnt;
    private Random rand;
    private boolean banderaGano;

    @Override
    public void show() {
        //arrays imagenes
        //listaMovibles = new Sprite[3];
        listaMovibles = new FichaSoberbia[3];
        listaEstaticas = new FichaSoberbia[3];
        rand = new Random();
        //inicializar la camara
        inicializarCamara();
        //crear la escena
        crearEscena();
        //cargar texturas
        cargarTexturas();
        //inicializar los objetos en el escenario

        //inicializar variables tiempo
        instrucciones = true;
        deltaTime = 0;
        //los imputs procesors
        estaTocando = false;



    }

    public  void crearEscena(){
        batch = new SpriteBatch();
    }

    public void inicializarCamara(){
        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista = new FitViewport(ancho,alto,camara);
        escena = new Stage();
        Gdx.input.setInputProcessor(this);

    }

    public void cargarTexturas(){
        //assetManager.load("back.png",Texture.class);
        assetManager.load("fondo_inicio.png",Texture.class);
        assetManager.load("instrucciones_ira.png",Texture.class);


        //bloquea hasta que se carguen las imgenes
        assetManager.finishLoading();

        //cuando termina, leemos las texturas
        //texturaback = assetManager.get("back.png");
        texturaFondo = assetManager.get("fondo_inicio.png");
        texturaInstrucciones = assetManager.get("instrucciones_ira.png");
        String listaMovImagenes[] = {"LujuriaS1.png","LujuriaS2.png","LujuriaS3.png"};
        String listaIndex[] = {"1","2","3"};

        for (int i = 0; i<=5; i ++){
            int random = rand.nextInt(3);
            cambiarArrayPosPrincipio(listaMovImagenes,random);
            cambiarArrayPosPrincipio(listaIndex,random);
        }

        for (int i=0; i<=2; i++){
            listaMovibles[i] = new FichaSoberbia(listaIndex[i],listaMovImagenes[i]);
        }

        listaMovibles[0].setCenter(ancho*.20f,alto*.75f);
        listaMovibles[1].setCenter(ancho*.50f,alto*.75f);
        listaMovibles[2].setCenter(ancho*.80f,alto*.75f);

        listaMovImagenes[0] = "LujuriaS1.png";
        listaMovImagenes[1] = "LujuriaS2.png";
        listaMovImagenes[2] = "LujuriaS3.png";

        listaIndex[0] = "1";
        listaIndex[1] = "2";
        listaIndex[2] = "3";

        for (int i = 0; i<=5; i ++){
            int random = rand.nextInt(3);
            cambiarArrayPosPrincipio(listaMovImagenes,random);
            cambiarArrayPosPrincipio(listaIndex,random);
        }

        for (int i=0; i<=2; i++){
            listaEstaticas[i] = new FichaSoberbia(listaIndex[i],listaMovImagenes[i]);
        }

        listaEstaticas[0].setCenter(ancho*.20f,alto*.25f);
        listaEstaticas[1].setCenter(ancho*.50f,alto*.25f);
        listaEstaticas[2].setCenter(ancho*.80f,alto*.25f);

        imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);
        //escena.addActor(imgFondo);

        imgInstrucciones = new Image(texturaInstrucciones);
        //Escalar
        escalaX = ancho / imgInstrucciones.getWidth();
        escalaY = alto / imgInstrucciones.getHeight();
        imgInstrucciones.setScale(escalaX, escalaY);
        escena.addActor(imgInstrucciones);


    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(1,1,1,1);
        //borra la pantalla completamente
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.setViewport(vista);

        if (instrucciones){
            escena.draw();
            deltaTime += Gdx.graphics.getDeltaTime();
            if(deltaTime > 1.3){
                imgInstrucciones = imgFondo;
                escena.clear();
                escena.addActor(imgFondo);
                instrucciones = false;

            }
        }
        else {
            escena.draw();

            //para el batch
            batch.setProjectionMatrix(camara.combined);
            batch.begin();

            for (FichaSoberbia f : listaEstaticas
                    ) {
                f.draw(batch);

            }

            for (int i = listaMovibles.length - 1; i >= 0; i--) {
                listaMovibles[i].draw(batch);
            }

            batch.end();
            if (estaTocando) {
                for (FichaSoberbia w : listaEstaticas
                        ) {

                    for (FichaSoberbia j : listaMovibles
                            ) {

                        if (w.getSprite().getBoundingRectangle().overlaps(j.getSprite().getBoundingRectangle())) {


                            if (w.getEtiqueta().equals(j.getEtiqueta())) {
                                System.out.println("esta tocando");
                                if (j.getEtiqueta().equals(listaMovibles[0].getEtiqueta())) {

                                    //pone la imagen en el mismo centro
                                    j.setCenter(w.getSprite().getX()+(w.getSprite().getWidth()*.5f), w.getSprite().getY()+(w.getSprite().getHeight()*.5f));
                                    //suelta la imagen
                                    estaTocando = false;
                                }
                            }
                        }

                    }

                }

            }
            banderaGano = true;

            if(banderaGano){
                juego.setScreen(new Soberbia(juego,0,0,0,null));
            }


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
        dispose();
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

        int contador= 0;

        for (FichaSoberbia w: listaMovibles
             ) {
            if(w.contains(x,y)){
                cambiarArrayPosPrincipio(listaMovibles,contador);

                estaTocando = true;
                xAnt = x;
                yAnt = y;
                return false;
            }
            contador++;
        }

        xAnt = x;
        yAnt = y;

        return false;
    }

    private void cambiarArrayPosPrincipio(Object[] lista, int posicion){
        if(lista.length == 0 || lista.length == 1 || posicion == 0){
            return;
        }

        Object temp = lista[0];

        lista[0] = lista[posicion];
        lista[posicion] = temp;



    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        estaTocando = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        Vector3 v = new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x = v.x;
        float y = v.y;


        for (FichaSoberbia w: listaMovibles
             ) {
            if(estaTocando && w.contains(xAnt,yAnt)){
                w.setCenter(x,y);
            }
            xAnt= x;
            yAnt = y;
            return false;


        }


        xAnt= x;
        yAnt = y;


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
