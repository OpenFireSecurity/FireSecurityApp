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
    private String sensorId;

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
        mEtMessage = findViewById(R.id.editText);
    }

    private void initNFC() {

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    private void showWriteFragment() {

        isWrite = true;

        mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);

        if (mNfcWriteFragment == null) {

            mNfcWriteFragment = NFCWriteFragment.newInstance();
        }
        mNfcWriteFragment.show(getFragmentManager(), NFCWriteFragment.TAG);

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
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected, tagDetected, ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    public void log(String message) {
        ScrollView sv = findViewById(R.id.scrollView);
        LinearLayout ll = findViewById(R.id.linearLayout);

        TextView tv = new TextView(this);
        tv.setText(message);

        ll.addView(tv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    public void onOkClick(View view) {
        if (sensorId != null) {
            sendBlock(sensorId, true);
        }
    }

    public void onNotOkClick(View view) {
        if (sensorId != null) {
            sendBlock(sensorId, false);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.d(TAG, "onNewIntent: " + intent.getAction());

            if (tag != null) {
                if (isWrite) {
                    mNfcWriteFragment.onNfcDetected(tag, this);
                } else {
                    Parcelable[] msgs =
                            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    if (msgs != null) {
                        log("Receive " + msgs.length + "messages");
                        if (msgs.length > 0) {
                            NdefRecord relayRecord = ((NdefMessage) msgs[0]).getRecords()[0];
                            String nfcData = new String(relayRecord.getPayload());
                            log("Message: " + nfcData);
                            try {
                                JSONObject data = new JSONObject(nfcData);
                                String id = data.getString("id");
                                if (id != null) {
                                    this.sensorId = id;
                                }
                                String text = data.getString("text");
                                String status = data.getString("status");
                                TextView tv = findViewById(R.id.textView);
                                tv.setText(text);
                                tv.setBackgroundColor(Color.rgb(127, 199, 240));

                            } catch (Exception ex) {
                                log("Error while parsing message: " + ex.getMessage());
                            }
                        }
                    }
                    else {
                        log("No messages on sensor");
                    }
                }
            }
        }
    }

    private void sendBlock(String sensorid, Boolean status) {
        Sender sender = null;
        try {
            sender = new Sender("192.168.1.227", 50051);

            String comment = mEtMessage.getText().toString();

            String verifierId = "verifier@test";
            sender.sendVerifierUpdate(verifierId, sensorid, status, comment);
            Toast.makeText(this, "Сообщение отправлено успешно", Toast.LENGTH_SHORT).show();
            log("Sending message succeed! verifierId: " + verifierId + ", sensorId: " + sensorid + ", status: " + status + ", comment: " + comment);
            resetState();
        } catch (Exception e) {
            log("Error while sending message: " + e.getMessage());
            Toast.makeText(this, "Ошибка при отправке сообщения!", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (sender != null) {
                    sender.shutdown();
                }
            } catch (InterruptedException e) {
                log("Error while shutdown sender: " + e.getMessage());
            }
        }
    }

    private void resetState() {
        TextView tv = findViewById(R.id.textView);
        tv.setText("Приложите устройство к датчику");
        tv.setBackgroundColor(Color.rgb(255, 187, 51));
        sensorId = null;
        mEtMessage.setText("");
    }
}
