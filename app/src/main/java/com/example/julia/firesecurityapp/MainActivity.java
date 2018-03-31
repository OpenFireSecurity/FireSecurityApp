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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity implements Listener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEtMessage;
    private Button mBtWrite;

    private NFCWriteFragment mNfcWriteFragment;

    private boolean isDialogDisplayed = false;
    private boolean isWrite = false;

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

    private void log (String message) {
        TextView tv = findViewById(R.id.textView2);
        tv.setText(tv.getText() + "\n" + message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
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
                    String text = "{id: \"fire9\", text: \"Датчик пожарной безопасности - уловитель дыма - инвертарный номер 51c33a50-eefe-4bdb-9bcf-2953fcbe2876\", status:1}";

                    log("ndef!=null: " + (Ndef.get(tag) != null));
                    log("formatable!=null: " + (NdefFormatable.get(tag) != null));

                    try (Ndef ndef = Ndef.get(tag)) {
                        ndef.connect();
                        NdefRecord mimeRecord = NdefRecord.createMime("text/plain", text.getBytes(Charset.forName("UTF-8")));
                        ndef.writeNdefMessage(new NdefMessage(mimeRecord));
                    } catch (Exception e) {
                        log(e.getMessage());
                    }

                    try (NdefFormatable formatable = NdefFormatable.get(tag)) {
                        formatable.connect();
                        NdefMessage message = new NdefMessage(
                                new NdefRecord[]{NdefRecord.createMime(
                                        "application/vnd.com.example.android.beam", text.getBytes())
                                });
                        formatable.format(message);
                    } catch (Exception e) {
                        log(e.getMessage());
                    }
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
//                Ndef ndef = Ndef.get(tag); // Enable I/O
//                log("ndef!=null: " + (ndef!=null));
//                try {
//                    ndef.connect(); // Write the message
//                    ndef.writeNdefMessage(message); // Close the connection
//                    ndef.close();
//                }
//                catch(Exception ex) {
//                    log(ex.getMessage());
//                }
//                log("msgs length: " + msgs.length);
//                NdefRecord firstRecord = ((NdefMessage)msgs[0]).getRecords()[0];
//                byte[] payload = firstRecord.getPayload();
//                int payloadLength = payload.length;
//                int langLength = payload[0];
//                int textLength = payloadLength - langLength - 1;
//                byte[] text = new byte[textLength];
//                System.arraycopy(payload, 1+langLength, text, 0, textLength);
//                log("MESSAGE: " + new String(text));

//                Ndef ndef = Ndef.get(tag);
//                String text = getString(R.string.message_tag_detected) + ", ndef!=null: " + (ndef != null);
//                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//
////            if (isDialogDisplayed) {
////
//                if (isWrite) {
//
//                    String messageToWrite = mEtMessage.getText().toString();
//                    mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);
//                    mNfcWriteFragment.onNfcDetected(ndef, messageToWrite);
//
//                } else {
//
//                    mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
//                    mNfcReadFragment.onNfcDetected(ndef);
//                }
//            }
            }
        }
    }

}
