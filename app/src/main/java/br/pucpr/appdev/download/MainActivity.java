package br.pucpr.appdev.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView img1, img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
    }

    public void btnDownloadOnClick(View v) {
        String urls[] = {
                "http://www.moahkids.com.br/images/papaipig.png",
                "http://www.moahkids.com.br/images/pepa.png",
        };

        new DownloadTask().execute(urls);
    }

    public class DownloadTask extends AsyncTask<String, Integer, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(String... urls) {
            List<Bitmap> bitmaps =  new ArrayList<>();

            Bitmap b1 = downloadImage(urls[0]);
            if (b1 != null) {
                bitmaps.add(b1);
            }
            publishProgress(50);
            Bitmap b2 = downloadImage(urls[1]);
            if (b2 != null) {
                bitmaps.add(b2);
            }
            publishProgress(100);

            return bitmaps;
        }

        private Bitmap downloadImage(String path) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(path);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);

            img1.setImageBitmap(bitmaps.get(0));
            img2.setImageBitmap(bitmaps.get(1));
        }
    }
}
