package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Marina on 08/10/2016.
 */
//
public class Lobby implements Screen, InputProcessor {
    private final juego juego;

    //Texturas
    private Texture texFondo;
    private Texture texCora;

    private SpriteBatch batch;
    private Fondo fondo;
    private Music musica;
    private  Music Winnie;


    //array
    private Array <Corazon> arrCora;

    //texto
    private Texto texto;
    private Texto textTiempo;


    //Variables
    private int vidas;
    private int almas;
    private boolean estado;

    //variables constantes de ancho y alto de la pamtalla
    private final float ancho = 1280;
    private final float alto = 720;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //tiempo
    private long startTime = System.currentTimeMillis();
    private int temporizador=3;

    //dificultad
    private Dificultad escNivel;

    //settings
    private Settings_save settings;

    public Lobby(juego juego,Settings_save sett){
        this.juego=juego;
        this.vidas=3;
        this.almas=0;
        this.estado=true;
        this.escNivel=new Dificultad();
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby1.mp3"));
        this.Winnie = Gdx.audio.newMusic(Gdx.files.internal("goodgoodnotBad.mp3"));
        Winnie.setVolume(0);

        this.settings=sett;
        if (this.settings.getMusic()){
            this.musica.play();
        }

    }
    public Lobby(juego juego, int vidas, int almas, boolean estado, Dificultad escNivel, Settings_save sett){
        this.juego=juego;
        this.vidas=vidas;
        this.almas=almas;
        this.estado=estado;
        this.escNivel=escNivel;
        this.settings=sett;

        if(estado)
        {
            this.Winnie = Gdx.audio.newMusic(Gdx.files.internal("goodgoodnotBad.mp3"));
        }
        else
        {
            this.Winnie = Gdx.audio.newMusic(Gdx.files.internal("badbadnotgood.mp3"));
        }

        if((almas>= 0 && almas <3)&& vidas >0 )
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby1.mp3"));
        else if((almas>= 3 && almas <6)&& vidas >0)
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby2.mp3"));
        else if((almas>= 6 && almas <9)&& vidas >0 )
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby3.mp3"));
        else if((almas>= 9 && almas <12)&& vidas >0 )
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby4.mp3"));
        else if((almas>= 12 && almas <15)&& vidas >0 )
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("Lobby5.mp3"));
        else if(vidas <= 0)
            this.musica = Gdx.audio.newMusic(Gdx.files.internal("LobbyF.mp3"));

        if (this.settings.getMusic()){
            this.musica.play();
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarCamara();
        crearEscena();
        Gdx.input.setInputProcessor(this);
        texto=new Texto();
        textTiempo=new Texto();
    }
    private void cargarCamara(){
        camara=new OrthographicCamera(ancho,alto);
        camara.position.set(ancho/2,alto/2,0);
        camara.update();
        vista=new FitViewport(ancho,alto,camara);
    }

    private void cargarTexturas(){
        texFondo=new Texture("Lobby.png");
        texCora=new Texture("vida.png");
    }

    private void crearEscena(){
        batch=new SpriteBatch();
        fondo=new Fondo(texFondo);

        if (vidas==0){
            juego.setScreen(new MenuPrincipal(juego));

        }else {

            arrCora = new Array<Corazon>(vidas);
            for (int i = 1; i <= vidas; i++) {
                Corazon cor = new Corazon(texCora, 300 + i * 150, 550);
                arrCora.add(cor);
            }
        }
        this.musica.setVolume(0.4f);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Avisar a batch cual es la camara que usamos

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        //Dibujar todos los elementos
        fondo.draw(batch);

        for (Corazon h: arrCora){
            h.draw(batch);
        }
        texto.mostrarMensaje(batch,""+almas,720,490);
        //textTiempo.mostrarMensaje(batch,"Time: "+(temporizador - ((System.currentTimeMillis() - startTime) / 1000)),640,700);

        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) == 2){
            if (estado==false){
                Corazon h = arrCora.get(arrCora.size-1);
                h.setPosition();
                Winnie.play();
            }

        }
        if((temporizador - ((System.currentTimeMillis() - startTime)/1000)) == 0) {
            if (estado==false){
                vidas-=1;
            }
            if (vidas==0) {
                this.musica.stop();
                juego.setScreen(new SetName(juego,this.almas));
            }else{
                int nivel=escNivel.cambiarNivel();
                int dif=escNivel.getDificultad();
                switch (nivel){
                    case 1:
                        //soberbia
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new Soberbia(juego,vidas,almas,dif,escNivel,settings));

                        break;
                    case 2:
                        //envidia
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new Envidia(juego,vidas,almas,dif,escNivel,settings));

                        break;
                    case 3:
                        //ira
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new Ira(juego,vidas,almas,dif,escNivel,settings));

                        break;
                    case 4:
                        //Pereza
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new NivelPereza(juego,vidas,almas,dif,escNivel,settings));

                        break;
                    case 5:
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new Avaricia(juego,vidas,almas,dif,escNivel,settings));
                        //Avaricia

                        break;
                    case 6:
                        //Gula
                        this.musica.stop();
                        this.dispose();
                        juego.setScreen(new NivelLujuria(juego,vidas,almas,dif,escNivel,settings));

                        break;
                    case 7:
                        //Lujuria
                        this.musica.stop();
                        this.dispose();
                        break;
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

    }

    @Override
    public void dispose() {
        texCora.dispose();
        texFondo.dispose();
        musica.dispose();
        Winnie.dispose();
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
