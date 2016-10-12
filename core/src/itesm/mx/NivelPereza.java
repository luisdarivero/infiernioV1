package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Karlo on 21/09/2016.
 */

public class NivelPereza implements Screen, InputProcessor {
    private final juego Juego;

    private Texture texturafondo;
    private Texture texturaGana;
    private Texture texturaPierde;
    private Texture texturaPreza;
    private Texture texturaPreza2;
    private Texture texturaPreza3;
    private Texture texturaPreza4;
    private Texture texturaPreza5;
    private Texture texturaInfo;

    //Musica
    private final Music Musica;
    private final Music Winnie;
    private final Music Bop;
    private final Music Bop2;
    private final Music Bop3;
    private final Music Bop4;
    private int bC = 0;

    //El Tiempo
    private long startTime = System.currentTimeMillis();

    //SpriteBatch
    private SpriteBatch batch;

    //COSAS DE CAMARA

    private OrthographicCamera camara;
    private StretchViewport vista;
    private Fondo fondo;
    private Fondo info;


    //Perezas
    private Array<Pereza> perezas;

    //temporizador de toques a pereza
    private int temporizador=5;
    private int toques=0;

    public NivelPereza(juego Juego)
    {
        this.Juego=Juego;
        Musica = Gdx.audio.newMusic(Gdx.files.internal("time.mp3"));
        Winnie = Gdx.audio.newMusic(Gdx.files.internal("bueno.mp3"));
        Bop = Gdx.audio.newMusic(Gdx.files.internal("bop1.mp3"));
        Bop2 = Gdx.audio.newMusic(Gdx.files.internal("bop2.mp3"));
        Bop3 = Gdx.audio.newMusic(Gdx.files.internal("bop3.mp3"));
        Bop4 = Gdx.audio.newMusic(Gdx.files.internal("bop4.mp3"));
        Musica.setVolume(0.6f);
        Musica.play();
    }

    //Marcador
    private Texto texto;

    @Override
    public void show() {
        cargarTexturas();
        inicializarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        texto = new Texto();
    }

    private void crearEscena() {
        batch = new SpriteBatch();
        fondo = new Fondo(texturafondo);
        info = new Fondo(texturaInfo);

        //Pereza
        perezas = new Array<Pereza>(5);
        for(int j = 0;j<5;j++)
        {
            switch (j)
            {
                case 0 :
                    Pereza pereza = new Pereza(texturaPreza,230,0,j);
                    perezas.add(pereza);
                    break;
                case 1 :
                    Pereza pereza1 = new Pereza(texturaPreza2,230,0,j);
                    perezas.add(pereza1);
                    break;
                case 2 :
                    Pereza pereza2 = new Pereza(texturaPreza3,50,0,j);
                    perezas.add(pereza2);
                    break;
                case 3 :
                    Pereza pereza3 = new Pereza(texturaPreza4,230,0,j);
                    perezas.add(pereza3);
                    break;
                case 4 :
                    Pereza pereza4 = new Pereza(texturaPreza5,230,0,j);
                    perezas.add(pereza4);
                    break;
            }
        }
    }

    private void inicializarCamara()
    {
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280/2,800/2,0);
        camara.update();

        vista = new StretchViewport(1280,800,camara);
    }
    //quizas necesite cargar otras texturas
    private void cargarTexturas()
    {
        texturafondo = new Texture("Fondo2.png");
        texturaPreza = new Texture("Pereza1.png");
        texturaPreza2 = new Texture("Pereza2.png");
        texturaPreza3 = new Texture("Pereza3.png");
        texturaPreza4 = new Texture("Pereza4.png");
        texturaPreza5 = new Texture("Pereza5.png");
        texturaGana = new Texture("win.jpg");
        texturaPierde = new Texture("Tache.png");
        texturaInfo = new Texture("instruccionesPereza01.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //avisar a batch cual es la camara
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //aki se dibujan los elementos
        fondo.draw(batch);

        // Aqui dibujamos perezs distintas
        if (toques < 5)
        {
            if(toques == 0)
            {
                info.draw(batch);
            }
            Pereza p = perezas.get(0);

            p.draw(batch);
        }
        else if (toques>=5 && toques < 10)
        {
            if(bC == 0)
            {
                Bop.play();
                bC++;
            }
            Pereza p = perezas.get(1);
            p.draw(batch);
        }
        else if (toques>=10 && toques < 15)
        {
            if(bC == 1)
            {
                Bop2.play();
                bC++;
            }
            Pereza p = perezas.get(2);
            p.draw(batch);
        }
        else if (toques>=15 && toques < 20)
        {
            if(bC == 2)
            {
                Bop3.play();
                bC++;
            }
            Pereza p = perezas.get(3);
            p.draw(batch);
        }
        else if ( toques >= 20)
        {
            if (bC == 3) {
                Bop4.play();
                bC++;
            }

            if (bC >= 44 && bC < 54)
            {
                bC++;
                Pereza p = perezas.get(4);
                p.draw(batch);
            }
            else if (bC >= 94 && bC < 104)
            {
                bC++;
                Pereza p = perezas.get(4);
                p.draw(batch);
            }
            else
            {
                bC++;
                Pereza p = perezas.get(3);
                p.draw(batch);
            }
        }


        //MArcador y tiempo

        if(toques<20)
        {
            texto.mostrarMensaje(batch, "Tiempo: " + (temporizador - ((System.currentTimeMillis() - startTime) / 1000)), 1050, 800);
        }
        else
        {
            texto.mostrarMensaje(batch, "Tiempo: 0", 1050, 800);
        }

        texto.mostrarMensaje(batch, "Toques: " + toques,200, 800);

        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0 && toques < 90 )
        {
            Musica.stop();
            //Aqui me deberia regresar al Lobby
            Juego.setScreen(new MenuPrincipal(Juego));
        }

        if(toques>=20)
        {
            Musica.stop();
            if(bC == 4)
            {
                Winnie.setVolume(0.4f);
                Winnie.play();
                Winnie.setVolume(0.4f);
                bC++;
            }
            //batch.draw(texturaGana,300,300);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        // VErificar si toca la Pereza
        // transformar coordenadas
        Vector3 v = new Vector3(screenX,screenY,0);
        camara.unproject(v);
        float x = v.x;
        float y = v.y;
        for (Pereza m : perezas)
        {
            if (m.contiene(x, y) && toques<5 && m.estado == Pereza.Estado.UNCUARTO)
            {
                toques++;
            }
            else if (m.contiene(x, y) && (toques>=5 && toques <10) && m.estado == Pereza.Estado.UNMEDIO)
            {
                toques++;
            }
            else if (m.contiene(x, y) && (toques>=10 && toques <15) && m.estado == Pereza.Estado.TRESCUARTOS)
            {
                toques++;
            }
            else if (m.contiene(x, y) && (toques>=15 && toques <20) && m.estado == Pereza.Estado.CASI)
            {
                toques++;
            }
            m.toquesP++;
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
