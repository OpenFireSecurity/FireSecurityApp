package com.example.julia.firesecurityapp;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

public class MainActivity extends AppCompatActivity implements Listener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEtMessage;
    private Button mBtWrite;
    private Button mBtRead;

    private NFCWriteFragment mNfcWriteFragment;
    private NFCReadFragment mNfcReadFragment;

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

        mEtMessage = (EditText) findViewById(R.id.et_message);
        mBtWrite = (Button) findViewById(R.id.btn_write);
        mBtRead = (Button) findViewById(R.id.btn_read);

        mBtWrite.setOnClickListener(view -> showWriteFragment());
        mBtRead.setOnClickListener(view -> showReadFragment());
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

    private void showReadFragment() {

        isWrite = false;

        mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNfcReadFragment == null) {

            mNfcReadFragment = NFCReadFragment.newInstance();
        }
        mNfcReadFragment.show(getFragmentManager(),NFCReadFragment.TAG);

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
        TextView tv = findViewById(R.id.textView);
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
        log("onNewIntent: " + intent.getAction());
        log("intent.getAction()==ACTION_TAG_DISCOVERED: " + NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()));
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            log("tag: " + tag.toString());
            Log.d(TAG, "onNewIntent: " + intent.getAction());

            if (tag != null) {

                Parcelable[] msgs =
                        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                log("msgs!=null: " + (msgs!=null));
                if (isWrite) {
                    String text = "Fire8";
                    NdefMessage message = new NdefMessage(
                            new NdefRecord[]{NdefRecord.createMime(
                                    "application/vnd.com.example.android.beam", text.getBytes())
                                    /**
                                     * The Android Application Record (AAR) is commented out. When a device
                                     * receives a push with an AAR in it, the application specified in the AAR
                                     * is guaranteed to run. The AAR overrides the tag dispatch system.
                                     * You can add it back in to guarantee that this
                                     * activity starts when receiving a beamed message. For now, this code
                                     * uses the tag dispatch system.
                                    */
                                    //,NdefRecord.createApplicationRecord("com.example.android.beam")
                            });
                    // Get an instance of Ndef for the tag.

                    try (NdefFormatable formatable = NdefFormatable.get(tag)) {
                        log("formatable!=null: " + (formatable != null));
                        formatable.connect();

                        try {
                            formatable.format(message);
                        } catch (Exception e) {
                            // let the user know the tag refused to format
                        }
                    } catch (Exception e) {
                        // let the user know the tag refused to connect
                    }
                }
                else {
                    if (msgs != null) {
                        log("msgs.length: " + msgs.length);
                        if (msgs.length > 0) {
                            NdefRecord relayRecord = ((NdefMessage)msgs[0]).getRecords()[0];
                            String nfcData = new String(relayRecord.getPayload());
                            log("message: " + nfcData);
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
