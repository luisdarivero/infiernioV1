package itesm.mx;

import java.util.ArrayList;

/**
 * Created by Marina on 14/10/2016.
 */

public class Dificultad {
    private ArrayList<Integer> niveles;
    private int rnd;
    private int ronda;
    private int dificultad;
    private int anterior;

    public Dificultad(){
        niveles=new ArrayList<Integer>(7);
        for (int i=1;i<=7;i++){
            niveles.add(i);
        }
        rnd=0;
        ronda=0;
        dificultad=1;
        anterior=0;
    }

    public int cambiarNivel(){
        int nivel;

        if(niveles.size()==0){
            for (int i=1;i<=7;i++){
                niveles.add(i);
            }
        }

            int range = ((niveles.size()-1) -0) + 1;
            rnd= (int)(Math.random() * range) + 0;

            while(niveles.get(rnd)==anterior){
                rnd=(int)(Math.random() * range) + 0;
            }
            anterior=niveles.get(rnd);
            nivel=niveles.get(rnd);
            niveles.remove(rnd);

        ronda+=1;
        if (ronda==7){
            dificultad+=1;
            ronda=0;
        }
        if (dificultad==5){
            dificultad=1;
        }
        return nivel;
    }
    public int getDificultad(){
        return dificultad;
    }
}
