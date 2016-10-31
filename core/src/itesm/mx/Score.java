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
import java.io.*;
import java.util.*;

//TODO:hacer textos y mostrarlos
//TODO: guardar nuevos scores

/**
 * Created by Daniel Riv on 16/09/2016.
 */
public class Score implements Screen {
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
    private Texture texturaBtnBack;

    //Para los scores
    private ArrayList<String> nombres = new ArrayList<String>(5);
    private ArrayList<Integer> scores = new ArrayList<Integer>(5);

    //administra la carga de assets
    private final AssetManager assetManager = new AssetManager();

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //musica
    private final Music musica;

    Scanner scan = null;
    String titulo, artista, genero;
    double precio;

    //constructor
    public Score(itesm.mx.juego juego, Music musica){
        this.juego =  juego;
        this.musica = musica;
        cargarScores();
    }

    //
    private void cargarScores() {
        try {
            scan= new Scanner(new FileReader("scores.txt"));
            while(scan.hasNext())
            {
                String [] lines = scan.nextLine().toString().split("	");
                nombres.add(lines[0]);
                scores.add(Integer.parseInt(lines[1]));
            }
        }catch(FileNotFoundException e) {
            System.err.println(e);
        } catch(InputMismatchException e) {
            System.err.println(e);
        } catch (java.util.NoSuchElementException e) {
            System.err.println(e);
        }
        finally {
            scan.close();
        }
    }

    @Override
    public void show() {
        //equivalente a create o a start, se ejecuta solo al cargar la pantalla

        camara = new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);

        camara.update();
        //vista = new StretchViewport(ancho,alto,camara);
        vista = new FitViewport(ancho,alto,camara);

        escena = new Stage();

        //habilitar el manejo de eventos
        Gdx.input.setInputProcessor(escena);
        cargarTexturas();
    }

    public void cargarTexturas(){



        //textura de fondo
        assetManager.load("Scores.png",Texture.class);

        //texturas de botones
        assetManager.load("back.png",Texture.class);

        //se bloquea hasta cargar los recursos
        assetManager.finishLoading();//bloquea hasta que se carguen las imgenes
        //cuando termina, leemos las texturas
        texturaFondo = assetManager.get("Scores.png");
        texturaBtnBack = assetManager.get("back.png");

        anadirTexturas();
    }

    public void anadirTexturas(){

        //para el fondo
        Image imgFondo = new Image(texturaFondo);
        //Escalar
        float escalaX = ancho / imgFondo.getWidth();
        float escalaY = alto / imgFondo.getHeight();
        imgFondo.setScale(escalaX,escalaY);
        escena.addActor(imgFondo);

        //botones
        //btn back
        TextureRegionDrawable trBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trBtnBack);
        btnBack.setPosition(btnBack.getWidth()/6, alto*.2f);
        escena.addActor(btnBack);



        //registrar listener para registarar evento del boton
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "TAP sobre el boton de regresar");
                juego.setScreen(new MenuPrincipal(juego,musica));
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
        vista.update(width, height);
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

