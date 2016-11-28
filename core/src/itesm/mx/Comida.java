package itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Daniel Riv on 17/11/2016.
 */

public class Comida {

    Sprite[] comida = new Sprite[6];
    Random rand = new Random();
    int espacioMax;
    int espacioMin;
    private boolean perdio;
    private Rectangle r;
    private float x;
    private float y;
    private float ancho;
    private float alto;
    private float velocidad;


    public Comida(String uno, String dos, String tres, int espacioMin, int espacioMax,float x, float y, float velocidad){

        this.espacioMax = espacioMax;
        this.espacioMin =  espacioMin;

        comida[0] = new Sprite(new Texture(uno));
        comida[1] = new Sprite(new Texture(dos));
        comida[2] = new Sprite(new Texture(tres));
        comida[3] = new Sprite(new Texture(dos));
        comida[4] = new Sprite(new Texture(tres));
        comida[5] = new Sprite(new Texture(uno));

        for (int i=0;i<8;i++){
            cambiarPos(comida, rand.nextInt(6));
        }

        comida[0].setCenter(x, y + comida[0].getHeight()/2);
        for(int i=1; i<6;i++){
            System.out.println("El random es: " + (espacioMax-espacioMin));
            comida[i].setCenter(comida[i-1].getX()+comida[i-1].getHeight()/2 +rand.nextInt(espacioMax-espacioMin)+espacioMin, y + comida[i].getHeight()/2);
        }

        perdio = false;

        this.velocidad = velocidad;

    }


    public void draw(SpriteBatch batch, Sprite personaje){





        for(int i = 0; i<comida.length;i++){
            comida[i].draw(batch);
            if(!perdio){
                comida[i].setCenter(comida[i].getX()+comida[i].getWidth()/2-velocidad,comida[i].getY()+comida[i].getHeight()/2 );
            }

            if(personaje.getBoundingRectangle().overlaps(comida[i].getBoundingRectangle())){

                ancho = 22;
                alto = 98;
                x = 0;
                y = 40;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }

                x +=ancho;
                y += -19;
                ancho = 26;
                alto +=19;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += -5;
                ancho = 25;
                alto +=5;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += -10;
                ancho = 23;
                alto +=10;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += -6;
                ancho = 27;
                alto +=6;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 6;
                ancho = 17;
                alto -=6;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y +=8;
                ancho = 18;
                alto -=8;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 12;
                ancho = 15;
                alto -=12;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 12;
                ancho = 10;
                alto -=12;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 8;
                ancho = 9;
                alto -=8;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 5;
                ancho = 4;
                alto -=5;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 6;
                ancho = 5;
                alto -=6;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }
                x +=ancho;
                y += 8;
                ancho = 13;
                alto -=8;
                r = personaje.getBoundingRectangle();
                if( (new Rectangle(r.getX() + x,r.getY()+y,ancho,alto)).overlaps(comida[i].getBoundingRectangle())){
                    perdio = true;
                }




            }
            if(comida[i].getX() <= -comida[i].getWidth()){
                pasarAlFinal(comida,i);
                i--;
            }

        }


    }

    private void cambiarPos(Sprite[] arreglo, int pos){
        if(pos == 0 ){
            return;
        }
        Sprite temp = arreglo[0];
        arreglo[0] = arreglo[pos];
        arreglo[pos]  = temp;
    }

    private void pasarAlFinal(Sprite[] arr, int pos){
        Sprite temp = arr[pos];
        for(int i=pos+1; i<arr.length;i++){
            arr[i-1] = arr[i];
        }

        temp.setCenter(arr[arr.length-2].getX()+temp.getWidth()/2+ rand.nextInt(espacioMax-espacioMin)+espacioMin,temp.getY()+temp.getHeight()/2);

        arr[arr.length-1] = temp;


    }

    public boolean isPerdio() {
        return perdio;
    }

    public void moverImagen(Sprite img){
        if(!isPerdio()){
            img.setCenter(img.getX()+(img.getWidth()/2)-velocidad,img.getY()+img.getHeight()/2);
            if(img.getX() <= -1280){
                img.setX(1280);
            }
        }

    }

    public void moverFondo(Sprite img, Sprite img2){
        if(!isPerdio()){
            img.setCenter(img.getX()+(img.getWidth()/2)-velocidad,img.getY()+img.getHeight()/2);
            img2.setCenter(img2.getX()+(img2.getWidth()/2)-velocidad,img2.getY()+img2.getHeight()/2);

            if(img.getX()<img2.getX()){
                if(img.getX() <= -2000){
                    img.setX(img2.getX()+2000);
                }
            }
            else{
                if(img2.getX() <= -2000){
                    img2.setX(img.getX()+2000);
                }
            }



        }

    }
}
