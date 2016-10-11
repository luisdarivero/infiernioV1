package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Daniel Riv on 16/09/2016.
 */
public class MenuPrincipal implements Screen {
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final int ancho = 1280;
    private final int alto = 720;


    //escena para la pantalla
    private Stage escena;

    //textura para la imagen de fondo
    private Texture texturaFondo;

    //texturas de las demas imagenes
    private Texture texturaBtnScore;
    private Texture texturaBtnPlay;
    private Texture texturaBtnSettings;
    private Texture texturaBtnAboutUs;

    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //constructor
    public MenuPrincipal(itesm.mx.juego juego){
        this.juego =  juego;
        musica = Gdx.audio.newMusic(Gdx.files.internal("Cempasuchil.wav"));
        musica.setLooping(true);
        musica.play();

    }

    public MenuPrincipal(itesm.mx.juego juego , Music musica){
        this.juego = juego;
        this.musica = musica;
    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla

        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);

        camara.update();
        vista = new FitViewport(ancho,alto,camara);

        escena = new Stage();

        //habilitar el manejo de eventos
        Gdx.input.setInputProcessor(escena);
        cargarTexturas();


    }

    public void cargarTexturas(){



        //textura de fondo
        assetManager.load("principal.png",Texture.class);

        //texturas de botones
        assetManager.load("btnPlay.png",Texture.class);
        assetManager.load("btnSettings.png",Texture.class);
        assetManager.load("btnAboutUs.png",Texture.class);
        assetManager.load("btnScore.png",Texture.class);

        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes
        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("principal.png");
        texturaBtnPlay = assetManager.get("btnPlay.png");
        texturaBtnSettings = assetManager.get("btnSettings.png");
        texturaBtnAboutUs = assetManager.get("btnAboutUs.png");
        texturaBtnScore = assetManager.get("btnScore.png");

        anadirTexturas();


    }

    public void anadirTexturas(){

        //para el fondo
        Image imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);
        escena.addActor(imgFondo);

        //botones
        //btn play
        TextureRegionDrawable trBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnPlay));
        ImageButton btnPlay = new ImageButton(trBtnPlay);
        btnPlay.setPosition(ancho/2-btnPlay.getWidth()/2,0.48f*alto);

        escena.addActor(btnPlay);
        //opciones
        TextureRegionDrawable trSettings = new TextureRegionDrawable(new TextureRegion(texturaBtnSettings));
        ImageButton btnSettings = new ImageButton(trSettings);
        btnSettings.setPosition(ancho*.09f,0.37f*alto);

        escena.addActor(btnSettings);

        //acerca de
        TextureRegionDrawable trBtnAboutUs = new TextureRegionDrawable(new TextureRegion(texturaBtnAboutUs));
        ImageButton btnAboutUs = new ImageButton(trBtnAboutUs);
        btnAboutUs.setPosition(ancho*0.70f,0.40f*alto);

        escena.addActor(btnAboutUs);

        //btn score
        TextureRegionDrawable trBtnScore = new TextureRegionDrawable(new TextureRegion(texturaBtnScore));
        ImageButton btnScore = new ImageButton(trBtnScore);
        btnScore.setPosition(ancho/2-btnScore.getWidth()/2,0.17f*alto);

        escena.addActor(btnScore);

        //registrar listener para registarar evento del boton
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de jugar");
                musica.stop();
                juego.setScreen(new Avaricia(juego));
            }
        });

        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de opciones");
                juego.setScreen(new Settings(juego,musica));
            }
        });

        btnAboutUs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton acerca de....");
                //cambiar a la pantalla acerca de
                juego.setScreen(new AboutUs(juego,musica));
            }
        });

        btnScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de Puntaje");
                juego.setScreen(new Score(juego,musica));
            }
        });
    }

    @Override
    public void render(float delta) {
    //es el update
        //pantalla blanca
        Gdx.gl.glClearColor(0,0,0,1);
        //borra la pantalla completamente
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.setViewport(vista);
        escena.draw();
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
        //dispose
    }

    @Override
    public void dispose() {
        //liberar los recursos utilizados en la memoria
        texturaBtnScore.dispose();
        texturaBtnAboutUs.dispose();
        texturaBtnSettings.dispose();
        texturaBtnPlay.dispose();
        texturaFondo.dispose();
        musica.dispose();
    }
}

