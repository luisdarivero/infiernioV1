package itesm.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Marina on 06/11/2016.
 */

public class Settings_save {
    private Preferences prefs;

    //música
    private boolean  music;

    //historia
    private boolean history;


    public Settings_save(){
        prefs= Gdx.app.getPreferences("Settings_S_H");
        this.music=this.prefs.getBoolean("Music",true);
        this.music=this.prefs.getBoolean("History",true);
    }

    public void setMusic(boolean m){
        this.music=m;
        this.prefs.putBoolean("Music", this.music);
        this.prefs.flush();
    }
    public void setHistory(boolean h){
        this.history=h;
        this.prefs.putBoolean("History", this.history);
        this.prefs.flush();
    }

    public boolean getMusic() {
        return this.music;
    }

    public boolean getHistory() {
        return this.history;
    }
}

