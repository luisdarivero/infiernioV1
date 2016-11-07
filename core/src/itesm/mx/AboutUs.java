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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * KRLO 16/09/2016.
 */
public class AboutUs implements Screen, InputProcessor {
    //se inicializa con una variable Tipo juego para poder pasar a otra escena
    private final itesm.mx.juego juego;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //para saber en que modo esta la pantalla
    private boolean contributor = false;

    //textura para la imagen de fondo
    private Texture texturaFondo;
    private Fondo imgFondo;
    private Fondo karlo;
    private Fondo becky;
    private Fondo sam;
    private Fondo daniel;
    private Fondo marina;

    //SpriteBatch
    private SpriteBatch batch;

    //Textura vacio
    private Texture texturavac;
    private Texture texturakarlo;
    private Texture texturamarina;
    private Texture texturadaniel;
    private Texture texturasam;
    private Texture texturabecky;

    //texturas de las demas imagenes
    private Texture texturaBtnBack;
    private Texture texturaBtnOk;

    private Array<Lujuria> decentes;   //Lista de Lujurias 5
    Lujuria btnBack ;
    Lujuria btnOk ;
    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    //constructor
    public AboutUs(itesm.mx.juego juego){
        this.juego =  juego;
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("AboutUs.mp3"));
        musica.setLooping(true);
        musica.play();
        musica.setVolume(0.4f);
    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        //habilitar el manejo de eventos
        Gdx.input.setInputProcessor(this);
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
        karlo= new Fondo(texturakarlo);
        becky=new Fondo(texturabecky);
        sam=new Fondo(texturasam);
        daniel=new Fondo(texturadaniel);
        marina=new Fondo(texturamarina);
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
        texturakarlo = new Texture("Karlo.png");
        texturabecky = new Texture("Becky.png");
        texturamarina = new Texture("Marina.png");
        texturadaniel = new Texture("Daniel.png");
        texturasam = new Texture("Sam.png");
        texturaBtnOk = new Texture("ok.png");
    }

    public void anadirTexturas()
    {
        //para el fondo
        imgFondo = new Fondo(texturaFondo);
        //botones
        btnBack =  new Lujuria(texturaBtnBack,ancho*.02f,alto * .02f,4);
        btnOk =  new Lujuria(texturaBtnOk,(ancho*.02f)-17,(alto * .02f)-17,4);
    }

    @Override
    public void render(float delta) {
        //pantalla blanca
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        if(contributor==false)
        {
            imgFondo.draw(batch);
            btnBack.draw(batch);
            btnBack.setRotation();
            for (Lujuria l : decentes)
            {
                if(l.showInfo==false)
                {
                    //l.draw(batch);
                    l.setRotation();
                    if (l.info == false) {
                        l.setSize(l.getW() - 80, l.getH() - 5);
                        l.info = true;
                    }
                }
            }
        }
        else
        {
            for (Lujuria l : decentes)
            {
                if(l.showInfo==true)
                switch (l.contributor)
                {
                    case 0:
                        becky.draw(batch);
                        break;
                    case 1:
                        karlo.draw(batch);
                        break;
                    case 2:
                        marina.draw(batch);
                        break;
                    case 3:
                        daniel.draw(batch);
                        break;
                    case 4:
                        sam.draw(batch);
                        break;
                }
            }
            btnOk.draw(batch);
            btnOk.setRotation();
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
        texturaBtnBack.dispose();
        texturaFondo.dispose();
        texturakarlo.dispose();
        texturabecky.dispose();
        texturasam.dispose();
        texturadaniel.dispose();
        texturamarina.dispose();
        texturaBtnOk.dispose();
        musica.dispose();
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
        // VErificar si le pego
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
        }
        if (btnBack.contiene(x,y)&& contributor == false)
        {
            musica.stop();
            this.dispose();
            juego.setScreen(new MenuPrincipal(juego));
        }
        if (btnOk.contiene(x,y) && contributor == true)
        {
            for (Lujuria l : decentes)
            {
                l.showInfo = false;
            }
            contributor = false;
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

