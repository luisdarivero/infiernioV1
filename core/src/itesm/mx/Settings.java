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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Daniel Riv on 16/09/2016.
 */
public class Settings implements Screen {
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;


    //escena para la pantalla
    private Stage escena;

    //textura para la imagen de fondo
    private Texture texturaFondo;

    //texturas de las demas imagenes
    private Texture texturaBtnBack;
    //sonido
    private Texture texturaBtnOn_M;
    private Texture texturaBtnOff_M;
    //historia
    private Texture texturaBtnOn_H;
    private Texture texturaBtnOff_H;


    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    private Settings_save settings;

    //constructor

    public Settings(itesm.mx.juego juego,Music musica, Settings_save sett){
        this.juego =  juego;
        this.settings= sett;
        this.musica=musica;

        if(this.settings.getMusic()){
            musica.play();
        }else{
            musica.pause();
        }
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
        assetManager.load("settings.png",Texture.class);

        //texturas de botones
        assetManager.load("back2.png",Texture.class);
        assetManager.load("btnNo.png",Texture.class);
        assetManager.load("btnYes.png",Texture.class);



        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes

        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("settings.png");
        texturaBtnBack = assetManager.get("back2.png");
        texturaBtnOn_M=assetManager.get("btnYes.png");
        texturaBtnOff_M=assetManager.get("btnNo.png");
        texturaBtnOn_H=assetManager.get("btnYes.png");
        texturaBtnOff_H=assetManager.get("btnNo.png");

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

        //btn back
        TextureRegionDrawable trBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trBtnBack);
        btnBack.setPosition(ancho*.02f,alto * .02f);
        escena.addActor(btnBack);

        //registrar listener para registarar evento del boton
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de regresar");

                juego.setScreen(new MenuPrincipal(juego,musica,settings));
            }
        });

        //btn Musica-On
        TextureRegionDrawable trBtn_mOn= new TextureRegionDrawable(new TextureRegion(texturaBtnOn_M));
        ImageButton btn_mOn =new ImageButton(trBtn_mOn);
        btn_mOn.setPosition(450,470);
        escena.addActor(btn_mOn);

        btn_mOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Musica On");
                settings.setMusic(true);
                musica.play();
            }
        });

        //btn Musica-Off
        TextureRegionDrawable trBtn_mOff= new TextureRegionDrawable(new TextureRegion(texturaBtnOff_M));
        ImageButton btn_mOff =new ImageButton(trBtn_mOff);
        btn_mOff.setPosition(590,475);
        escena.addActor(btn_mOff);

        btn_mOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Musica Off");
                settings.setMusic(false);
                musica.pause();
            }
        });

        //btn Historia-On
        TextureRegionDrawable trBtn_hOn= new TextureRegionDrawable(new TextureRegion(texturaBtnOn_H));
        ImageButton btn_hOn =new ImageButton(trBtn_hOn);
        btn_hOn.setPosition(450,290);
        escena.addActor(btn_hOn);

        btn_hOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Historia On");
                settings.setHistory(true);
            }
        });


        //btn Historia-Off
        TextureRegionDrawable trBtn_hOff= new TextureRegionDrawable(new TextureRegion(texturaBtnOff_H));
        ImageButton btn_hOff =new ImageButton(trBtn_hOff);
        btn_hOff.setPosition(590,295);
        escena.addActor(btn_hOff);

        btn_hOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Historia False");
                settings.setHistory(false);
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

        texturaBtnBack.dispose();
        texturaFondo.dispose();
    }
}

