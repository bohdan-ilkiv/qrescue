package com.example.qrescue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(v -> startQRScanner());
    }

    private final ActivityResultLauncher<Intent> qrScannerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.hasExtra(QRCodeScannerActivity.QR_CODE_RESULT)) {
                        String qrCodeResult = data.getStringExtra(QRCodeScannerActivity.QR_CODE_RESULT);
                        Toast.makeText(this, qrCodeResult, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void startQRScanner() {
        Intent intent = new Intent(this, QRCodeScannerActivity.class);
        qrScannerLauncher.launch(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_QR_SCAN) {
            if (data != null && data.hasExtra(QRCodeScannerActivity.QR_CODE_RESULT)) {
                String qrCodeResult = data.getStringExtra(QRCodeScannerActivity.QR_CODE_RESULT);
                Toast.makeText(this, qrCodeResult, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
