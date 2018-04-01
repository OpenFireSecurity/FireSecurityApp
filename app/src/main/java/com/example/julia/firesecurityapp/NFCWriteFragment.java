package com.example.julia.firesecurityapp;

import android.app.DialogFragment;
import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;

public class NFCWriteFragment extends DialogFragment {

    public static final String TAG = NFCWriteFragment.class.getSimpleName();

    public static NFCWriteFragment newInstance() {

        return new NFCWriteFragment();
    }

    private TextView mTvMessage;
    private ProgressBar mProgress;
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mListener = (MainActivity) context;
//        mListener.onDialogDisplayed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener.onDialogDismissed();
    }

    public void onNfcDetected(Tag tag, MainActivity context) {

        mProgress.setVisibility(View.VISIBLE);
        writeToNfc(tag, context);
    }

    private void writeToNfc(Tag tag, MainActivity context) {

        mTvMessage.setText(getString(R.string.message_write_progress));

        String text = "{id: \"sensorid@test\", text: \"Датчик пожарной безопасности - уловитель дыма - инвертарный номер 51c33a50-eefe-4bdb-9bcf-2953fcbe2876\", status:1}";

        try (Ndef ndef = Ndef.get(tag)) {
            if (ndef != null) {
                ndef.connect();
                NdefRecord mimeRecord = NdefRecord.createMime("text/plain", text.getBytes(Charset.forName("UTF-8")));
                ndef.writeNdefMessage(new NdefMessage(mimeRecord));
                mTvMessage.setText(getString(R.string.message_write_success));
                context.log("Write to sensor: " + text);
            }
        } catch (Exception e) {
            mTvMessage.setText(getString(R.string.message_write_error));
            context.log("Error while writing to sensor: " + e.getMessage());
        }

        try (NdefFormatable formatable = NdefFormatable.get(tag)) {
            if (formatable != null) {
                formatable.connect();
                NdefMessage message = new NdefMessage(
                        new NdefRecord[]{NdefRecord.createMime(
                                "application/vnd.com.example.android.beam", text.getBytes())
                        });
                formatable.format(message);
                mTvMessage.setText(getString(R.string.message_write_success));
                context.log("Write to sensor: " + text);
            }
        } catch (Exception e) {
            mTvMessage.setText(getString(R.string.message_write_error));
            context.log("Error while writing to sensor: " + e.getMessage());
        }

        context.isWrite = false;

        mProgress.setVisibility(View.GONE);
    }
}