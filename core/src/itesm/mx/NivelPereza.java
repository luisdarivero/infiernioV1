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

//Esto son el tiempo y la dificultad que se va a tener
    private int dificultad;
    private int temporizador;

//Los valores que necesito tener guardados para el Lobby
    private  int vidas;
    private int almas ;
    private boolean sizeF = false;
    private Dificultad escNivel;

    //Toques
    private int goal = 20+(dificultad-1);

    //Texturas
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
    private int toques=0;

    //settings
    private Settings_save settings;

    public NivelPereza(juego Juego, int vidas, int almas, int dificultad, Dificultad escNivel, Settings_save settings)
    {
        this.Juego = Juego;
        this.vidas = vidas;
        this.almas = almas;
        this.dificultad = dificultad;
        this.escNivel=escNivel;
        this.settings=settings;

        this.temporizador = 5;

        Musica = Gdx.audio.newMusic(Gdx.files.internal("time.mp3"));
        Winnie = Gdx.audio.newMusic(Gdx.files.internal("goodgoodnotBad.mp3"));
        Bop = Gdx.audio.newMusic(Gdx.files.internal("bop1.mp3"));
        Bop2 = Gdx.audio.newMusic(Gdx.files.internal("bop2.mp3"));
        Bop3 = Gdx.audio.newMusic(Gdx.files.internal("bop3.mp3"));
        Bop4 = Gdx.audio.newMusic(Gdx.files.internal("bop4.mp3"));
        Musica.setVolume(0.6f);
        if (this.settings.getMusic()){
            Musica.play();
        }

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
                    Pereza pereza2 = new Pereza(texturaPreza3,30,0,j);
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
        camara = new OrthographicCamera(1280,720);
        camara.position.set(1280/2,720/2,0);
        camara.update();
        vista = new StretchViewport(1280,720,camara);
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
        fondo.setSizeF(0, 10);


        // Aqui dibujamos perezs distintas
        if (toques < (goal*1/4))
        {
            if(toques == 0)
            {
                info.draw(batch);
                if(sizeF == false)
                {
                info.setPositionF(0,-200);
                sizeF = true;
                }
            }
            Pereza p = perezas.get(0);

            p.draw(batch);
        }
        else if (toques>=(goal*1/4) && toques < (goal*1/2))
        {
            if(bC == 0)
            {
                Bop.play();
                bC++;
            }
            Pereza p = perezas.get(1);
            p.draw(batch);
        }
        else if (toques>=(goal*1/2) && toques < (goal*3/4))
        {
            if(bC == 1)
            {
                Bop2.play();
                bC++;
            }
            Pereza p = perezas.get(2);
            p.draw(batch);
        }
        else if (toques>=(goal*3/4) && toques < goal)
        {
            if(bC == 2)
            {
                Bop3.play();
                bC++;
            }
            Pereza p = perezas.get(3);
            p.draw(batch);
        }
        else if ( toques >= goal)
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

        if(toques<goal)
        {
            texto.mostrarMensaje(batch, "Time: " + (temporizador - ((System.currentTimeMillis() - startTime) / 1000)), 640, 720);
        }
        else
        {
            texto.mostrarMensaje(batch, " ", 640, 720);
        }
//Si es necesario modificar los toques
        //texto.mostrarMensaje(batch, "Toques: " + toques,200, 720);

        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= 0 && toques < goal )
        {
            Musica.stop();
            //Aqui me regresa al Lobby
            this.dispose();
            Juego.setScreen(new Lobby(Juego,vidas,almas,false,escNivel,settings));
        }

        if(toques>=goal && (temporizador - ((System.currentTimeMillis() - startTime)/1000)) <= -2)
        {
            Musica.stop();
            if(bC == 4)
            {
                Winnie.play();
                bC++;
            }
            almas++;
            this.dispose();
            Juego.setScreen(new Lobby(Juego,vidas,almas,true,escNivel,settings));
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

    }

    @Override
    public void dispose()
    {
        Musica.dispose();
        Winnie.dispose();
        Bop.dispose();
        Bop2.dispose();
        Bop3.dispose();
        Bop4.dispose();
         texturafondo.dispose();
         texturaGana.dispose();
         texturaPierde.dispose();
         texturaPreza.dispose();
         texturaPreza2.dispose();
         texturaPreza3.dispose();
         texturaPreza4.dispose();
         texturaPreza5.dispose();
         texturaInfo.dispose();
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
            if (m.contiene(x, y) && toques<(goal*1/4) && m.estado == Pereza.Estado.UNCUARTO)
            {
                toques++;
                m.tocado = false;
            }
            else if (m.contiene(x, y) && (toques>=(goal*1/4) && toques <(goal*1/2)) && m.estado == Pereza.Estado.UNMEDIO)
            {
                toques++;
                m.tocado = false;
            }
            else if (m.contiene(x, y) && (toques>=(goal*1/2) && toques <(goal*3/4)) && m.estado == Pereza.Estado.TRESCUARTOS)
            {
                toques++;
                m.tocado = false;
            }
            else if (m.contiene(x, y) && (toques>=(goal*3/4) && toques <goal) && m.estado == Pereza.Estado.CASI)
            {
                toques++;
                m.tocado = false;
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
