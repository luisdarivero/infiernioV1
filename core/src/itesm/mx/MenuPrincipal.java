package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Daniel Riv on 16/09/2016.
 */
public class MenuPrincipal implements Screen {
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ANCHO y ALTO de la pamtalla
    private final int ANCHO = 1280;
    private final int ALTO = 720;

    //para el movimiento de particulas
    private SpriteBatch batch;
    private ParticleEffect pe;

    //escena para la pantalla
    private Stage escena;

    //textura para la imagen de fondo
    private Texture texturaFondo;

    //texturas de las demas imagenes
    private Texture texturaBtnScore;
    private Texture texturaBtnPlay;
    private Texture texturaBtnSettings;
    private Texture texturaBtnAboutUs;
    private Sprite spriteFondo;


    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //Settings
    private Settings_save settings;


    //constructor
    public MenuPrincipal(itesm.mx.juego juego){
        this.juego =  juego;
        musica = Gdx.audio.newMusic(Gdx.files.internal("Cempasuchitl.mp3"));
        this.settings=new Settings_save();
        if (this.settings.getMusic()){
            musica.play();
            musica.setLooping(true);
        }

    }

    public MenuPrincipal(itesm.mx.juego juego , Music musica, Settings_save sett) {
        this.juego = juego;
        this.musica = musica;
        this.settings=sett;

        if (this.settings.getMusic()) {
            musica.play();
        }

    }

    public MenuPrincipal(itesm.mx.juego juego , Settings_save sett) {
        this.juego = juego;
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("Cempasuchitl.mp3"));
        this.settings=sett;

        if (this.settings.getMusic()) {
            musica.play();
        } else {
            musica.pause();
        }

    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla

        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO /2, ALTO /2,0);

        camara.update();
        vista = new FitViewport(ANCHO, ALTO,camara);

        escena = new Stage();

        //habilitar el manejo de eventos
        Gdx.input.setInputProcessor(escena);
        cargarTexturas();

        //cargar lo necesario para el sistema de particulas
        batch = new SpriteBatch();

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particles3.party"),Gdx.files.internal(""));
        //pe.getEmitters().first().setPosition(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        pe.setPosition(ANCHO *.53f, ALTO *.1f);

    }

    public void cargarTexturas(){



        //textura de fondo
        assetManager.load("piedras_inicio.png",Texture.class);

        //texturas de botones
        assetManager.load("btnPlay.png",Texture.class);
        assetManager.load("btnSettings.png",Texture.class);
        assetManager.load("btnAboutUs.png",Texture.class);
        assetManager.load("btnScore.png",Texture.class);

        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes
        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("piedras_inicio.png");
        texturaBtnPlay = assetManager.get("btnPlay.png");
        texturaBtnSettings = assetManager.get("btnSettings.png");
        texturaBtnAboutUs = assetManager.get("btnAboutUs.png");
        texturaBtnScore = assetManager.get("btnScore.png");
        spriteFondo = new Sprite(new Texture("fondo_inicio.png"));

        anadirTexturas();


    }

    public void anadirTexturas(){

        //para el fondo
        Image imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ANCHO / imgFondo.getWidth();
        float escalaY = ALTO / imgFondo.getHeight();
        imgFondo.setScale(escalaX, escalaY);
        escena.addActor(imgFondo);

        //botones
        //btn play
        TextureRegionDrawable trBtnPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnPlay));
        ImageButton btnPlay = new ImageButton(trBtnPlay);
        btnPlay.setPosition(ANCHO /2-btnPlay.getWidth()/2,0.48f* ALTO);

        escena.addActor(btnPlay);
        //opciones
        TextureRegionDrawable trSettings = new TextureRegionDrawable(new TextureRegion(texturaBtnSettings));
        ImageButton btnSettings = new ImageButton(trSettings);
        btnSettings.setPosition(ANCHO *.09f,0.37f* ALTO);

        escena.addActor(btnSettings);

        //acerca de
        TextureRegionDrawable trBtnAboutUs = new TextureRegionDrawable(new TextureRegion(texturaBtnAboutUs));
        ImageButton btnAboutUs = new ImageButton(trBtnAboutUs);
        btnAboutUs.setPosition(ANCHO *0.70f,0.40f* ALTO);

        escena.addActor(btnAboutUs);

        //btn score
        TextureRegionDrawable trBtnScore = new TextureRegionDrawable(new TextureRegion(texturaBtnScore));
        ImageButton btnScore = new ImageButton(trBtnScore);
        btnScore.setPosition(ANCHO /2-btnScore.getWidth()/2,0.17f* ALTO);

        escena.addActor(btnScore);

        //registrar listener para registarar evento del boton
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de jugar");
                musica.stop();
                juego.setScreen(new Lobby(juego,settings));
                
            }
        });

        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de opciones");
                juego.setScreen(new Settings(juego,musica,settings));

            }
        });

        btnAboutUs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton acerca de....");
                //cambiar a la pantalla acerca de
                musica.stop();
                juego.setScreen(new AboutUs(juego, settings));
                //juego.setScreen(new SetName(juego,musica));
            }
        });

        btnScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de Puntaje");
                juego.setScreen(new Score(juego,musica,settings));
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

        batch.setProjectionMatrix(camara.combined);
        pe.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        spriteFondo.draw(batch);
        pe.draw(batch);
        batch.end();
        if(pe.isComplete()){
            pe.reset();
        }

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