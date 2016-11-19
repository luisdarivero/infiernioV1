package itesm.mx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.util.Random;

/**
 * Created by Karlo on 06/10/2016.
 */

public class NivelLujuria implements Screen, InputProcessor {
    private final juego Juego;
    private Estado estado;
    private Sprite btnPausa;
    private Sprite fondoPausa;
    private Sprite btnContinuar;
    private Sprite btnSalir;
    private int ancho = 1280;
    private int alto = 720;
    private boolean ok =false;
    private final AssetManager assetManager;// = new AssetManager();

    //Esto son el tiempo y la dificultad que se va a tener
    private int dificultad;
    private long tempo;

    //Los valores que necesito tener guardados para el Lobby
    private int vidas;
    private int almas;
    private boolean tunTun = true;
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
    private long pausaT;
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
    private int contador = 0;

    //settings
    private Settings_save settings;

    public NivelLujuria(juego Juego, int vidas, int almas, int dificultad, Dificultad escNivel,Settings_save settings)
    {
        assetManager = Juego.getAssetManager();
        this.Juego = Juego;
        this.vidas = vidas;
        this.almas = almas;
        this.dificultad = dificultad;
        this.escNivel=escNivel;
        this.settings=settings;

        this.tempo = 6;
        Musica = Gdx.audio.newMusic(Gdx.files.internal("time.mp3"));
        Winnie = Gdx.audio.newMusic(Gdx.files.internal("goodgoodnotBad.mp3"));
        Bop = Gdx.audio.newMusic(Gdx.files.internal("OK.mp3"));
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
        texto = new Texto("fuenteAv_a.fnt");
        Gdx.input.setCatchBackKey(true);
    }

    private void crearEscena()
    {
        batch = new SpriteBatch();
        fondo = new Fondo(texturafondo);
        inst = new Fondo(texturaInstr);
        btnPausa = new Sprite(new Texture("pausaNS.png"));
        fondoPausa = new Sprite(new Texture("Pausa.png"));
        fondoPausa.setCenter(ancho/2,alto/2);
        btnContinuar = new Sprite(new Texture("botonContinuar.png"));
        btnContinuar.setCenter(ancho/3,alto/2);
        btnSalir = new Sprite(new Texture("botonSalir.png"));
        btnSalir.setCenter(ancho/3*2,alto/2);
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
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //avisar a batch cual es la camara
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        if(estado == Estado.Pausa)
        {

            fondoPausa.draw(batch);
            btnContinuar.draw(batch);
            btnSalir.draw(batch);
            System.out.println();
            if(ok==false)
            {
                tempo = pausaT;
                ok =true;
            }
        }
        else {
            //aqui se dibujan los elementos
            fondo.draw(batch);
            fondo.setSizeF(10, 10);


            if ((tempo - ((System.currentTimeMillis() - startTime) / 1000)) <= 4) {
                fondo.setSizeF(0, 10);
                for (Lujuria l : lujurias) {
                    if (l.sexy == 0 && l.estado == Lujuria.Estado.ALREVES && l.dec == 5) {
                        Random rnd = new Random();
                        l.dec = rnd.nextInt(3);
                        Lujuria ll = decentes.get(l.dec);
                        ll.setCoordenates(l.getX(), l.getY());
                        ll.draw(batch);
                        if (ll.info == false)//
                        {
                            ll.setSize(l.getW() - 15, l.getH() - 15);
                            ll.info = true;
                        }
                    } else if (l.sexy == 0 && l.estado == Lujuria.Estado.ALREVES && l.dec != 5) {
                        Random rnd = new Random();
                        Lujuria ll = decentes.get(l.dec);
                        ll.setCoordenates(l.getX(), l.getY());
                        ll.draw(batch);
                        if (ll.info == false) {
                            ll.setSize(l.getW() - 15, l.getH() - 15);
                            ll.info = true;
                        }
                    } else {
                        l.draw(batch);
                        if (l.info == false) {
                            l.setSize(l.getW() - 15, l.getH() - 15);
                            l.info = true;
                        }
                    }
                }

                //MArcador
                if (toques < totals) {
                    texto.mostrarMensaje(batch, "Time: " + (tempo - ((System.currentTimeMillis() - startTime) / 1000)), 640, 400);
                } else {
                    texto.mostrarMensaje(batch, " ", 640, 400);
                }

                // texto.mostrarMensaje(batch, "Toques: " + toques, 200, 800);

                if (contador >= totals) {
                    Musica.stop();
                    if (tunTun) {
                        Winnie.play();
                        tunTun = false;
                    }
                    Winnie.setLooping(false);
                    //Winnie.setVolume(0.4f);


                    if ((tempo - ((System.currentTimeMillis() - startTime) / 1000)) < -1) {
                        almas++;
                        this.dispose();
                        Juego.setScreen(new Lobby(Juego, vidas, almas, true, escNivel, settings));
                    }
                }
            } else {
                inst.draw(batch);
            }

            if ((tempo - ((System.currentTimeMillis() - startTime) / 1000)) <= 0 && contador < totals) {
                Musica.stop();
                //Aqui me deberia regresar al Lobby
                this.dispose();
                Juego.setScreen(new Lobby(Juego, vidas, almas, false, escNivel, settings));
            }
            btnPausa.draw(batch);
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
        Musica.dispose();
        Winnie.dispose();
        Bop.dispose();
        texturafondo.dispose();
        texturaPierde.dispose();
        texturaLujuriaV.dispose();
        texturaLujuria2V.dispose();
        texturaLujuria3V.dispose();
        texturaLujuria.dispose();
        texturaLujuria2.dispose();
        texturaLujuria3.dispose();
        texturaGana.dispose();
        texturaInstr.dispose();
        /*
        assetManager.unload("Fondo5.png");
        assetManager.unload("Tache.png");
        assetManager.unload("LujuriaP1.png");
        assetManager.unload("LujuriaP2.png");
        assetManager.unload("LujuriaP3.png");
        assetManager.unload("LujuriaS1.png");
        assetManager.unload("LujuriaS2.png");
        assetManager.unload("LujuriaS3.png");
        assetManager.unload("win.jpg");
        assetManager.unload("instruccionesLujuria.png");
        assetManager.unload("pausaNS.png");
        assetManager.unload("Pausa.png");
        assetManager.unload("botonContinuar.png");
        assetManager.unload("botonSalir.png");
        */
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode== Input.Keys.BACK) {
            // Regresar al menú
            estado = Estado.Pausa;
            Musica.pause();
            ok = false;
            pausaT = (tempo - ((System.currentTimeMillis() - startTime) / 1000));// Cambio de pantalla
        }
        return true;    }

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
        if(btnPausa.getBoundingRectangle().contains(x,y)){
            estado = Estado.Pausa;
            Musica.pause();
            ok = false;
            pausaT = (tempo - ((System.currentTimeMillis() - startTime) / 1000));
            return false;
        }

        if(estado == Estado.Pausa)
        {
            if(btnContinuar.getBoundingRectangle().contains(x,y)){
                Musica.play();
                startTime = System.currentTimeMillis();
                estado = Estado.Normal;
                return false;
            }
            else if(btnSalir.getBoundingRectangle().contains(x,y)){
                Musica.stop();
                Juego.setScreen(new MenuPrincipal(Juego,settings));
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

    public enum Estado
    {
        Normal, Pausa
    }

}
