package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.math.BigDecimal;

/**
 * Created by Daniel Riv on 07/10/2016.
 */
public class Gula implements Screen,InputProcessor {

    //variable tipo juego para poder intarcambiar de escenas
    private final itesm.mx.juego juego;

    //variables constantes del ancho y largo de la pantalla
    private final float ancho = 1280;
    private final float alto = 720;

    private float tiempo;

    private Texto texto;
    private float velocidad;
    //escena para la pantalla
    private Stage escena;
    private boolean esperar;

    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //declarando al golozo
    private Golozo golozo;
    private  Sprite piso;
    private Comida comida;

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

    //declarar elementos de la pausa
    private Sprite btnPausa;
    private Sprite fondoPausa;
    private Sprite btnContinuar;
    private Sprite btnSalir;
    private Estado estado;

    private Sprite fondoGula;
    private Sprite fondoGula2;
    private Sprite piso2;

    private int min;
    private int max;


    //settings
    private Settings_save settings;

    //constructor
    public Gula(juego Juego, int vidas, int almas, int nivel, Dificultad escNivel, Settings_save settings ){
        this.juego=Juego;
        this.vidas=vidas;
        this.almas=almas;
        this.nivel = nivel;
        this.escNivel=escNivel;
        this.settings=settings;

        velocidad = 6;
        tiempo = 6;

        if(nivel ==1){
            velocidad = 6;
            tiempo = 6;
            min =1000;
            max = 1500;

        }
        else if(nivel == 2){
            velocidad = 7;
            tiempo = 10;
            min = 1000;
            max = 1200;
        }
        else if(nivel == 3){
            velocidad = 8;
            tiempo = 12;
            min = 1100;
            max = 1150;
        }
        else if(nivel >=4){
            velocidad = 9;
            tiempo = 13;
            min = 1200;
            max = 1220;
        }

        esperar = false;

    }




    //texturas
    //private Texture texturaback;
    private Texture texturaFondo;
    private Texture texturaInstrucciones;
    private  Image imgInstrucciones;
    private  Image imgFondo;

    //manejador del tiempo
    private float deltaTime;


    @Override
    public void show() {
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
        estado = Estado.Normal;
        Gdx.input.setInputProcessor(this);
        texto = new Texto();




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
        Gdx.input.setInputProcessor(escena);

    }

    public void cargarTexturas(){
        //assetManager.load("back.png",Texture.class);
        assetManager.load("fire1.png",Texture.class);
        assetManager.load("instruccionesGula.png",Texture.class);


        //bloquea hasta que se carguen las imgenes
        assetManager.finishLoading();

        //cuando termina, leemos las texturas
        //texturaback = assetManager.get("back.png");
        texturaFondo = assetManager.get("fire1.png");
        texturaInstrucciones = assetManager.get("instruccionesGula.png");



        imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);
        escena.addActor(imgFondo);

        imgInstrucciones = new Image(texturaInstrucciones);
        //Escalar
        escalaX = ancho / imgInstrucciones.getWidth();
        escalaY = alto / imgInstrucciones.getHeight();
        imgInstrucciones.setScale(escalaX, escalaY);
        escena.addActor(imgInstrucciones);

        //para declarar los elementos de la pausa
        btnPausa = new Sprite(new Texture("pausaNS.png"));

        fondoPausa = new Sprite(new Texture("Pausa.png"));
        fondoPausa.setCenter(ancho/2,alto/2);
        btnContinuar = new Sprite(new Texture("botonContinuar.png"));
        btnContinuar.setCenter(ancho/3,alto/2);
        btnSalir = new Sprite(new Texture("botonSalir.png"));
        btnSalir.setCenter(ancho/3*2,alto/2);

        piso = new Sprite(new Texture("pisoGula.png"));
        piso.setCenter(ancho/2,piso.getHeight()/2);
        piso2 = new Sprite(new Texture("pisoGula.png"));
        piso2.setCenter(ancho/2+ancho,piso.getHeight()/2);

        golozo = new Golozo("Gula1.png","Gula2.png",235,(piso.getHeight()));

        comida = new Comida("Chocolate.png","Helado.png","Chocolate.png",min,max,ancho*.75f, piso.getY()+piso.getHeight()+25,velocidad);

        fondoGula = new Sprite(new Texture("FondoGula.png"));
        fondoGula.setCenter(2000/2,alto/2);
        fondoGula2 = new Sprite(new Texture("FondoGula2.png"));
        fondoGula2.setCenter(2000/2+2000,alto/2);




    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(0,0,0,1);
        //borra la pantalla completamente
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.setViewport(vista);

        if (instrucciones){
            escena.draw();
            deltaTime += Gdx.graphics.getDeltaTime();
            if(deltaTime > 2){
                imgInstrucciones = imgFondo;
                escena.clear();
                escena.addActor(imgFondo);
                instrucciones = false;
                deltaTime = 0;

            }
        }
        else {
            //escena.draw();

            //para el batch
            batch.setProjectionMatrix(camara.combined);
            batch.begin();

            if(estado == Estado.Pausa){

                fondoGula2.draw(batch);
                fondoPausa.draw(batch);
                fondoGula.draw(batch);
                btnContinuar.draw(batch);
                btnSalir.draw(batch);
            }
            else{
                fondoGula.draw(batch);
                fondoGula2.draw(batch);
                piso.draw(batch);
                piso2.draw(batch);
                comida.draw(batch, golozo.getSprite());
                comida.moverImagen(piso);
                comida.moverImagen(piso2);
                comida.moverFondo(fondoGula,fondoGula2);
                golozo.draw(batch);

                btnPausa.draw(batch);
                if(comida.isPerdio() && !esperar){
                    golozo.perdio();
                    deltaTime = 0;
                    esperar = true;
                }else {
                    if(!esperar){
                        tiempo -= Gdx.graphics.getDeltaTime();
                        texto.mostrarMensaje(batch,Float.toString(round(tiempo,1)),ancho*.5f, alto*.8f);
                        if(tiempo <=0){
                            //gano
                            juego.setScreen(new Gula(juego,vidas,almas,nivel+1,escNivel,settings));
                        }
                    }

                }
                if (esperar){
                    deltaTime +=Gdx.graphics.getDeltaTime();
                    System.out.println(deltaTime);
                    if(deltaTime >1){
                        //perdio
                        juego.setScreen(new Gula(juego,vidas,almas,nivel+1,escNivel,settings));
                    }
                }
            }

            batch.end();

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
        texturaInstrucciones.dispose();

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



        if(btnPausa.getBoundingRectangle().contains(x,y)){
            estado = Estado.Pausa;

            return false;
        }

        if(estado == Estado.Pausa){
            if(btnContinuar.getBoundingRectangle().contains(x,y)){
                estado = Estado.Normal;
                return false;
            }
            else if(btnSalir.getBoundingRectangle().contains(x,y)){
                juego.setScreen(new MenuPrincipal(juego));
            }
        }

        golozo.saltar();


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

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
