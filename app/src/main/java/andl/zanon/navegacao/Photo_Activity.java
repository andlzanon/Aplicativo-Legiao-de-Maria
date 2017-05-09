package andl.zanon.navegacao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class Photo_Activity extends AppCompatActivity {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_);

        Intent it = getIntent();
        if (it.getAction().equals("andl.zanon.MUDA_FOTO")) {
            acessaCamera();
        }
    }

    public void acessaCamera() {
        // intent para tentar acessar arquivos do computador
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();

            Bitmap bitmap;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri), null, options);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                byte []b = baos.toByteArray();
                String imageString = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString(getString(R.string.key_foto_perfil), imageString);
                editor.putBoolean(getString(R.string.key_foto_modificada), true);
                editor.apply();
                Toast.makeText(this, "Foto definida como perfil", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
