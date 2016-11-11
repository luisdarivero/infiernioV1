package itesm.mx;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
/**
 * Created by roberto on 09/11/16.
 */
public class Video extends Activity implements MediaPlayer.OnCompletionListener
{
    private VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        String archivo = "android.resource://" + getPackageName() + "/raw/video";
        vv = (VideoView)findViewById(R.id.surface);
        vv.setVideoURI(Uri.parse(archivo));
        vv.setOnCompletionListener(this);
    }//
    @Override
    protected void onPause() {
        super.onPause();
        if ( vv.isPlaying() ) {
            vv.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        vv.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if ( vv.isPlaying() ) {
            vv.stopPlayback();
        }
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        iniciarApp();
    }
    public void saltarIntro(View v) {
        vv.stopPlayback();
        vv.setOnCompletionListener(null);
        iniciarApp();
    }
    private void iniciarApp() {
        Intent intent = new Intent(this,AndroidLauncher.class);
        startActivity(intent);
        finish();
    }
}