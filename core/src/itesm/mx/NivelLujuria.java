package itesm.mx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.util.Random;

/**
 * Created by Karlo on 06/10/2016.
 */

public class NivelLujuria implements Screen, InputProcessor {
    private final juego Juego;

    //Esto son el tiempo y la dificultad que se va a tener
    private int dificultad;
    private long tempo;

    //Los valores que necesito tener guardados para el Lobby
    private  int vidas;
    private int almas ;
    private Dificultad escNivel;

    private Texture texturafondo;
    private Texture texturaPierde;
    private Texture texturaLujuria;
    private Texture texturaLujuria2;
    private Texture texturaLujuria3;
    private Texture texturaLujuriaV;
    private Texture texturaLujuria2V;
    private Texture texturaLujuria3V;
    private Texture texturaGana;
    private Texture texturaInstr;

    //Valores iniciales necesarios
    private long startTime = System.currentTimeMillis();
    private int toques = 0;
    private int totals = 0;

    //Musica
    private final Music Musica;
    private final Music Winnie;
    private final Music Bop;
    private int bC = 0;

    //SpriteBatch
    private SpriteBatch batch;

    //COSAS DE CAMARA
    private OrthographicCamera camara;
    private StretchViewport vista;
    private Fondo fondo;
    private Fondo inst;

    //Lujurias
    private Array<Lujuria> lujurias;   //Lista de Lujurias 12
    private Array<Lujuria> decentes;   //Lista de Lujurias 12

    //Contador de lujurias volteadas correctamente
    private int contador=0;

    //TODO: PONER LAS COSAS CORRECTAS Musica
    public NivelLujuria(juego Juego, int vidas, int almas, int dificultad, Dificultad escNivel)
    {
        this.Juego = Juego;
        this.vidas = vidas;
        this.almas = almas;
        this.dificultad = dificultad;
        this.escNivel=escNivel;

        this.tempo = 5;
        Musica = Gdx.audio.newMusic(Gdx.files.internal("time.mp3"));
        Winnie = Gdx.audio.newMusic(Gdx.files.internal("bueno.mp3"));
        Bop = Gdx.audio.newMusic(Gdx.files.internal("OK.mp3"));
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
        texto = new Texto("fuenteAv_a.fnt");
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        fondo = new Fondo(texturafondo);
        inst = new Fondo(texturaInstr);
//Lujurias
        Random rnd = new Random();
        int num; int color;
        lujurias = new Array<Lujuria>(12);
        decentes = new Array<Lujuria>(3);
        //DECENTES
        for(int i = 0;i<3;i++)
        {
            if(i==0)
            {
                Lujuria lujuria = new Lujuria(texturaLujuriaV,1);
                decentes.add(lujuria);
            }
            else if(i==1)
            {
                Lujuria lujuria = new Lujuria(texturaLujuria2V,1);
                decentes.add(lujuria);
            }
            else if(i==2)
            {
                Lujuria lujuria = new Lujuria(texturaLujuria3V,1);
                decentes.add(lujuria);
            }
        }
        //ALEATORIO
        for(int i = 0;i<3;i++)
        {
            for(int j = 0;j<4;j++)
            {
                num = rnd.nextInt(2);
                color = rnd.nextInt(3);
                switch (num)
                {
                    case 0 :
                        if(color==0)
                        {
                            Lujuria lujuria = new Lujuria(texturaLujuria,50+j*300,i*260,num);
                            lujurias.add(lujuria);
                            totals++;
                        }
                        else if(color==1)
                        {
                            Lujuria lujuria = new Lujuria(texturaLujuria2,50+j*300,i*260,num);
                            lujurias.add(lujuria);
                            totals++;
                        }
                        else if(color==2)
                        {
                            Lujuria lujuria = new Lujuria(texturaLujuria3,50+j*300,i*260,num);
                            lujurias.add(lujuria);
                            totals++;
                        }
                        break;
                    case 1 :
                        if(color==0)
                        {
                            Lujuria lujuriaV = new Lujuria(texturaLujuriaV,50+j*300,i*260,num);
                            lujurias.add(lujuriaV);
                        }
                        else if(color==1)
                        {
                            Lujuria lujuriaV = new Lujuria(texturaLujuria2V,50+j*300,i*260,num);
                            lujurias.add(lujuriaV);
                        }
                        else if(color==2)
                        {
                            Lujuria lujuriaV = new Lujuria(texturaLujuria3V,50+j*300,i*260,num);
                            lujurias.add(lujuriaV);
                        }
                        break;
                }
            }
        }
    }

    private void inicializarCamara()
    {
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280/2,400,0);
        camara.update();
        vista = new StretchViewport(1280,800,camara);
    }

    private void cargarTexturas() {
        texturafondo = new Texture("Fondo5.png");
        texturaPierde = new Texture("Tache.png");
        texturaLujuriaV = new Texture("LujuriaP1.png");
        texturaLujuria2V = new Texture("LujuriaP2.png");
        texturaLujuria3V = new Texture("LujuriaP3.png");
        texturaLujuria = new Texture("LujuriaS1.png");
        texturaLujuria2 = new Texture("LujuriaS2.png");
        texturaLujuria3 = new Texture("LujuriaS3.png");
        texturaGana = new Texture("win.jpg");
        texturaInstr = new Texture("instruccionesLujuria.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //avisar a batch cual es la camara
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        //aqui se dibujan los elementos
        fondo.draw(batch);

        if((tempo - ((System.currentTimeMillis() - startTime)/1000)) <= 4-dificultad)
        {
            for (Lujuria l : lujurias) {
                if (l.sexy == 0 && l.estado == Lujuria.Estado.ALREVES && l.dec == 5) {
                    Random rnd = new Random();
                    l.dec = rnd.nextInt(3);
                    Lujuria ll = decentes.get(l.dec);
                    ll.setCoordenates(l.getX(), l.getY());
                    ll.draw(batch);
                    if (ll.info==false)//
                    {
                        ll.setSize(l.getW()-15, l.getH() - 15);
                        ll.info = true;
                    }
                } else if (l.sexy == 0 && l.estado == Lujuria.Estado.ALREVES && l.dec != 5) {
                    Random rnd = new Random();
                    Lujuria ll = decentes.get(l.dec);
                    ll.setCoordenates(l.getX(), l.getY());
                    ll.draw(batch);
                    if (ll.info==false)
                    {
                        ll.setSize(l.getW()-15, l.getH() - 15);
                        ll.info = true;
                    }
                } else {
                    l.draw(batch);
                    if(l.info==false)
                    {
                        l.setSize(l.getW()-15, l.getH() - 15);
                        l.info = true;
                    }
                }
            }

            //MArcador
            if (toques < totals) {
                texto.mostrarMensaje(batch, "Time: " + (tempo - ((System.currentTimeMillis() - startTime) / 1000)), 640, 400);
            } else {
                texto.mostrarMensaje(batch, "Time: 0", 640, 400);
            }

            // texto.mostrarMensaje(batch, "Toques: " + toques, 200, 800);

            if (contador >= totals) {
                Musica.stop();
                Winnie.setVolume(0.4f);
                //Winnie.play();
                Winnie.setVolume(0.4f);
                //Aqui me deberia regresar al Lobby
                almas++;
                Juego.setScreen(new Lobby(Juego,vidas,almas,true,escNivel));
            }
        }
        else
        {
            inst.draw(batch);
        }

        if((tempo - ((System.currentTimeMillis() - startTime)/1000)) <= 0 && contador < totals)
        {
            Musica.stop();
            //Aqui me deberia regresar al Lobby
            Juego.setScreen(new Lobby(Juego,vidas,almas,false,escNivel));
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
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
        for (Lujuria l : lujurias)
        {
            if (l.contiene(x, y) && l.sexy == 0 && l.cdr == 0)
            {
                //le pico
                Bop.stop();
                contador++;
                toques++;
                l.cdr++;
                Bop.setVolume(.4f);
                Bop.play();
                l.setEstado(Lujuria.Estado.ROTANDO);
            }
            if (l.contiene(x, y) && toques == -1)
            {
                toques++;
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
