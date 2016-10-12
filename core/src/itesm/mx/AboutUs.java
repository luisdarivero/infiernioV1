package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Daniel Riv on 16/09/2016.
 */
public class AboutUs implements Screen, InputProcessor {
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //para saber en que modo esta la pantalla
    private boolean contributor = false;

    //escena para la pantalla
    private Stage escena;

    //textura para la imagen de fondo
    private Texture texturaFondo;
    private Fondo imgFondo;
    private Fondo karlo;
    private Fondo becky;
    private Fondo sam;
    private Fondo daniel;
    private Fondo samantha;

    //SpriteBatch
    private SpriteBatch batch;

    //Textura vacio
    private Texture texturavac;

    //texturas de las demas imagenes
    private Texture texturaBtnBack;

    private Array<Lujuria> decentes;   //Lista de Lujurias 5
    Lujuria btnBack ;
    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //constructor
    public AboutUs(itesm.mx.juego juego, Music musica){
        this.juego =  juego;
        this.musica = musica;
    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        escena = new Stage();
        //habilitar el manejo de eventos
        Gdx.input.setInputProcessor(escena);
    }

    private void inicializarCamara()
    {
        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista = new StretchViewport(ancho,alto,camara);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        decentes = new Array<Lujuria>(12);
        //DECENTES
        for(int i = 0;i<5;i++)
        {
            switch(i)
            {
                case 0:
                    Lujuria lujuria = new Lujuria(texturavac,110,270,1);
                    lujuria.contributor = 0;
                    decentes.add(lujuria);
                    break;
                case 1:
                    Lujuria lujuria1 = new Lujuria(texturavac,330,270,1);
                    lujuria1.contributor = 1;
                    decentes.add(lujuria1);
                    break;
                case 2:
                    Lujuria lujuria2 = new Lujuria(texturavac,550,270,1);
                    lujuria2.contributor = 2;
                    decentes.add(lujuria2);
                    break;
                case 3:
                    Lujuria lujuria3 = new Lujuria(texturavac,770,270,1);
                    lujuria3.contributor = 3;
                    decentes.add(lujuria3);
                    break;
                case 4:
                    Lujuria lujuria4 = new Lujuria(texturavac,990,270,1);
                    lujuria4.contributor = 4;
                    decentes.add(lujuria4);
                    break;
            }
        }
        anadirTexturas();
    }


    public void cargarTexturas(){

        //textura de vac
        assetManager.load("LujuriaP1.png",Texture.class);

        //textura de fondo
        assetManager.load("aboutUs.png",Texture.class);

        //texturas de botones
        assetManager.load("back.png",Texture.class);

        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes
        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("aboutUs.png");
        texturaBtnBack = assetManager.get("back.png");
        texturavac = assetManager.get("LujuriaP1.png");
    }

    public void anadirTexturas()
    {
        //para el fondo
        imgFondo = new Fondo(texturaFondo);
        //botones
         btnBack =  new Lujuria(texturaBtnBack,ancho*.02f,alto * .02f,4);
    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        imgFondo.draw(batch);

        if(contributor==false)
        {
            btnBack.draw(batch);
            btnBack.setRotation();
            for (Lujuria l : decentes)
            {
                if(l.showInfo==false)
                {
                    l.draw(batch);
                    l.setRotation();
                    if (l.info == false) {
                        l.setSize(l.getW() - 80, l.getH() - 5);
                        l.info = true;
                    }
                }
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
        //dispose
    }

    @Override
    public void dispose() {
        //liberar los recursos utilizados en la memoria

        texturaBtnBack.dispose();
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
        // VErificar si le pego a un topo
        // transformar coordenadas
        Vector3 v = new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x = v.x;
        float y = v.y;
        for (Lujuria l : decentes)
        {
            if (l.contiene(x, y) && l.showInfo == false && l.contributor == 0)
            {
                l.showInfo = true;
                contributor = true;
            }
            else if (l.contiene(x, y) && l.showInfo == false && l.contributor == 1)
            {
                l.showInfo = true;
                contributor = true;
            }
            else if (l.contiene(x, y) && l.showInfo == false && l.contributor == 2)
            {
                l.showInfo = true;
                contributor = true;
            }
            else if (l.contiene(x, y) && l.showInfo == false && l.contributor == 3)
            {
                l.showInfo = true;
                contributor = true;
            }
            else if (l.contiene(x, y) && l.showInfo == false && l.contributor == 4)
            {
                l.showInfo = true;
                contributor = true;
            }
            if (btnBack.contiene(x,y))
            {
                juego.setScreen(new MenuPrincipal(juego,musica));
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

