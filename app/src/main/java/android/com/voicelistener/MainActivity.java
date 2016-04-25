package android.com.voicelistener;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecognitionListener{

    private Button btn_mic = null;
    private TextView txt_status = null;
    private final int RESULT_SPEECH = 101;

    private SpeechRecognizer speech;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * initalizing view elements.
     */
    private void init(){
        btn_mic = (Button) findViewById(R.id.btn_mic);
        txt_status = (TextView) findViewById(R.id.txt_status);

        btn_mic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_mic){
            initateListeninig();
        }
    }


    /**
     * initates voice listeninig
     */
    private void initateListeninig(){

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());

        speech.startListening(intent);

    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.e(TAG, "THIS IS 1");
        btn_mic.setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.e(TAG, "THIS IS 2");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.e(TAG, "THIS IS 3");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.e(TAG, "THIS IS 4");
    }

    @Override
    public void onEndOfSpeech() {
        Log.e(TAG, "THIS IS 5");
    }

    @Override
    public void onError(int error) {
        Log.e(TAG, "THIS IS 6");
        btn_mic.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION);

        if(matches.size() >0)
            txt_status.setText(matches.get(0));

        Log.e(TAG, "THIS IS 7");
        try {

            speech.destroy();
            speech = null;
        }catch (Exception e){
            e.printStackTrace();
        }

        btn_mic.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.e(TAG, "THIS IS 8");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.e(TAG, "THIS IS 9");
    }
}
