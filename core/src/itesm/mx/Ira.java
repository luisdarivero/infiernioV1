package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
public class Ira implements Screen {

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

    private  int nivel;
    //constructor
    public Ira(itesm.mx.juego juego, int nivel){
        this.juego = juego;
        this.nivel = nivel;
    }

    //Objetos en el escenario
    private Furioso furioso;
    private ManoIra mano;

    //texturas
    private Texture texturaback;
    private Texture texturaFondo;

    //manejador del tiempo



    @Override
    public void show() {
        //inicializar la camara
        inicializarCamara();
        //crear la escena
        crearEscena();
        //cargar texturas
        cargarTexturas();
        //inicializar los objetos en el escenario
        furioso = new Furioso(ancho/2, alto*0.35f);//se alinea con respecto al centro
        mano = new ManoIra(ancho,alto,furioso);
        //inicializar variables tiempo



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
        assetManager.load("back.png",Texture.class);
        assetManager.load("Ira.png",Texture.class);


        //bloquea hasta que se carguen las imgenes
        assetManager.finishLoading();

        //cuando termina, leemos las texturas
        texturaback = assetManager.get("back.png");
        texturaFondo = assetManager.get("Ira.png");



        Image imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);
        escena.addActor(imgFondo);



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
        });

    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(1,1,1,1);
        //borra la pantalla completamente
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.setViewport(vista);
        escena.draw();

        //para el batch
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        furioso.draw(batch);
        mano.draw(batch);
        batch.end();
        if(furioso.getEstado() == Furioso.Estado.Gano){
            //juego.setScreen(new Ira(juego,nivel + 1));
        }
        else if(furioso.getEstado() == Furioso.Estado.Perdio){
            //juego.setScreen(new Ira(juego,1));
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

    }

    @Override
    public void dispose() {

    }
}
