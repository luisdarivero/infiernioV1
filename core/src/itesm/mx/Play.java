package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
public class Play implements Screen {
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
    private Texture texturaPereza;


    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //constructor
    public Play(itesm.mx.juego juego){
        this.juego =  juego;
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
        assetManager.load("blank.png",Texture.class);

        //texturas de botones
        assetManager.load("back.png",Texture.class);
        assetManager.load("Pereza3.png", Texture.class);

        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes
        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("blank.png");
        texturaBtnBack = assetManager.get("back.png");
        texturaPereza = assetManager.get("Pereza3.png");

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
        btnBack.setPosition(btnBack.getWidth()/2, alto - btnBack.getHeight());
        escena.addActor(btnBack);

        //pereza
        Image pereza = new Image(texturaPereza);
        pereza.setPosition(ancho/2-pereza.getWidth()/2,alto*.05f);
        escena.addActor(pereza);



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

