package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Daniel Riv on 07/10/2016.
 */
public class Ira implements Screen,InputProcessor {

    //variable tipo juego para poder intarcambiar de escenas
    private final itesm.mx.juego juego;

    //variables constantes del ancho y largo de la pantalla
    private final float ancho = 1280;
    private final float alto = 720;

    //escena para la pantalla
    private Stage escena;

    //administra la carga de assets
    private final AssetManager assetManager;// = new AssetManager();

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

    //declarar elementos de la pausa
    private Sprite btnPausa;
    private Sprite fondoPausa;
    private Sprite btnContinuar;
    private Sprite btnSalir;
    private Estado estado;


    //settings
    private Settings_save settings;

    //constructor
    public Ira(juego Juego, int vidas, int almas, int nivel, Dificultad escNivel,Settings_save settings ){
        assetManager = Juego.getAssetManager();
        this.juego=Juego;
        this.vidas=vidas;
        this.almas=almas;
        this.nivel = nivel;
        this.escNivel=escNivel;
        this.settings=settings;

    }

    //Objetos en el escenario
    private Furioso furioso;
    private ManoIra mano;


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
        furioso = new Furioso(ancho/2, alto*0.35f,nivel);//se alinea con respecto al centro
        mano = new ManoIra(ancho,alto,furioso);
        //inicializar variables tiempo
        instrucciones = true;
        deltaTime = 0;
        estado = Estado.Normal;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);


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
        /*
        //assetManager.load("back.png",Texture.class);
        assetManager.load("Ira.png",Texture.class);
        assetManager.load("instrucciones_ira.png",Texture.class);


        //bloquea hasta que se carguen las imgenes
        assetManager.finishLoading();
        */

        //cuando termina, leemos las texturas
        //texturaback = assetManager.get("back.png");
        texturaFondo = new Texture("Ira.png");
        texturaInstrucciones =  new Texture("instrucciones_ira.png");



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

        //para declarar los elementos de la pausa
        btnPausa = new Sprite(new Texture("pausaNS.png"));

        fondoPausa = new Sprite(new Texture("Pausa.png"));
        fondoPausa.setCenter(ancho/2,alto/2);
        btnContinuar = new Sprite(new Texture("botonContinuar.png"));
        btnContinuar.setCenter(ancho/3,alto/2);
        btnSalir = new Sprite(new Texture("botonSalir.png"));
        btnSalir.setCenter(ancho/3*2,alto/2);



        /**
        //para asignar funcionalidad a la imagen como boton
        TextureRegionDrawable trBtnBack = new TextureRegionDrawable(new TextureRegion(texturaback));
        ImageButton btnBack = new ImageButton(trBtnBack);
        btnBack.setPosition(btnBack.getWidth()/2, alto - btnBack.getHeight());
        escena.addActor(btnBack);


        //registrar listener para registarar evento del boton
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de regresar");
                juego.setScreen(new MenuPrincipal(juego));
            }
        });**/

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

            }
        }
        else {
            escena.draw();

            //para el batch
            batch.setProjectionMatrix(camara.combined);
            batch.begin();

            if(estado == Estado.Pausa){
                fondoPausa.draw(batch);
                btnContinuar.draw(batch);
                btnSalir.draw(batch);
            }
            else{
                furioso.draw(batch);
                mano.draw(batch);
                btnPausa.draw(batch);
            }

            batch.end();
            if (furioso.getEstado() == Furioso.Estado.Gano) {
                juego.setScreen(new Lobby(juego,vidas,almas+1,true,escNivel,settings));
            } else if (furioso.getEstado() == Furioso.Estado.Perdio) {
                juego.setScreen(new Lobby(juego,vidas,almas,false,escNivel,settings));
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
        texturaInstrucciones.dispose();
        /*
        assetManager.unload("Ira.png");
        assetManager.unload("instrucciones_ira.png");
        assetManager.unload("pausaNS.png");
        assetManager.unload("Pausa.png");
        assetManager.unload("botonContinuar.png");
        assetManager.unload("botonSalir.png");
        */
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode== Input.Keys.BACK) {
            // Regresar al menú
            estado=Estado.Pausa; // Cambio de pantalla
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
                juego.setScreen(new MenuPrincipal(juego,settings));
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
