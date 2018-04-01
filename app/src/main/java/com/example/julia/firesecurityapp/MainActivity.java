package com.example.julia.firesecurityapp;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.nio.charset.Charset;

import openFire.security.monitoring.Sender;

public class MainActivity extends AppCompatActivity implements Listener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEtMessage;
    private Button mBtWrite;

    private NFCWriteFragment mNfcWriteFragment;

    private boolean isDialogDisplayed = false;
    public boolean isWrite = false;

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initNFC();
    }

    private void initViews() {

        mBtWrite = (Button) findViewById(R.id.btn_write);

        mBtWrite.setOnClickListener(view -> showWriteFragment());
    }

    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private void showWriteFragment() {

        isWrite = true;

        mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);

        if (mNfcWriteFragment == null) {

            mNfcWriteFragment = NFCWriteFragment.newInstance();
        }
        mNfcWriteFragment.show(getFragmentManager(),NFCWriteFragment.TAG);

    }

    @Override
    public void onDialogDisplayed() {

        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {

        isDialogDisplayed = false;
        isWrite = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    public void log (String message) {
        ScrollView sv = findViewById(R.id.scrollView);
        LinearLayout ll = findViewById(R.id.linearLayout);

        TextView tv = new TextView(this);
        tv.setText(message);

        ll.addView(tv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    public void onOkClick(View view) {
        sendBlock();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        log("intent.getAction()==ACTION_TAG_DISCOVERED: " + NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()));
//        log("intent.getAction()==ACTION_NDEF_DISCOVERED: " + NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()));
//        log("intent.getAction()==ACTION_TECH_DISCOVERED: " + NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()));
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.d(TAG, "onNewIntent: " + intent.getAction());
//            log("EXTRA_TAG: " + (intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)!=null));
//            log("ACTION_ADAPTER_STATE_CHANGED: " + (intent.getParcelableExtra(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)!=null));
//            log("ACTION_NDEF_DISCOVERED: " + (intent.getParcelableExtra(NfcAdapter.ACTION_NDEF_DISCOVERED)!=null));
//            log("ACTION_TAG_DISCOVERED: " + (intent.getParcelableExtra(NfcAdapter.ACTION_TAG_DISCOVERED)!=null));
//            log("ACTION_TECH_DISCOVERED: " + (intent.getParcelableExtra(NfcAdapter.ACTION_TECH_DISCOVERED)!=null));
//            log("EXTRA_NDEF_MESSAGES: " + (intent.getParcelableExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)!=null));

            if (tag != null) {
                if (isWrite) {
                    mNfcWriteFragment.onNfcDetected(tag, this);
                }
                else {
                    Parcelable[] msgs =
                            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    log("msgs!=null: " + (msgs!=null));
                    if (msgs != null) {
                        log("msgs.length: " + msgs.length);
                        if (msgs.length > 0) {
                            NdefRecord relayRecord = ((NdefMessage)msgs[0]).getRecords()[0];
                            String nfcData = new String(relayRecord.getPayload());
                            log("message: " + nfcData);
                            try {
                                JSONObject data = new JSONObject(nfcData);
                                String id = data.getString("id");
                                String text = data.getString("text");
                                String status = data.getString("status");
                                TextView tv = findViewById(R.id.textView);
                                tv.setText(text);
                                tv.setBackgroundColor(Color.rgb(127, 199, 240));

                            } catch(Exception ex) {
                                log(ex.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    private void sendBlock () {
        Sender sender = null;
        try {
            sender = new Sender("192.168.1.227", 50051);

            sender.sendVerifierUpdate("verifier@test", "sensorid@test", true, "Everything is fine");
            Toast.makeText(this, "Сообщение отправлено успешно", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            log(e.getMessage());
            Toast.makeText(this, "Ошибка при отправке сообщения!", Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (sender != null) {
                    sender.shutdown();
                }
            } catch (InterruptedException e) {
                log(e.getMessage());
            }
        }
    }

}
