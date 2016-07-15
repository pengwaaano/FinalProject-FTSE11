package com.jacobgreenland.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A class that downloads the SVG image from the Network and stores it on a file. Also it uses ImageCacheUtil to store it in cache
 * <p/>
 * Created by betomaluje on 1/2/16.
 */
public class HttpImageRequestTask extends AsyncTask<String, Void, Drawable> {

    private Context context;
    private ImageView mImageView;
    private View progressBar;

    public HttpImageRequestTask(Context context, ImageView mImageView) {
        this.context = context;
        this.mImageView = mImageView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Drawable doInBackground(String... params)
    {
        try
        {
            if(!params[0].contains("https"))
                params[0] = params[0].replace("http","https");

            final URL url = new URL(params[0]);
            Log.d("test", "\""+url.toString() + "\"");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVG.getFromInputStream(inputStream);

            PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());

            ImageCacheUtil imageCacheUtil = ImageCacheUtil.getInstance(context);

            imageCacheUtil.putImage(params[0], drawable);

            String filename = URLEncoder.encode(params[0],"UTF-8").replace(".svg", ".png");

            imageCacheUtil.saveImageToFile(drawable, filename);

            return drawable;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        // Update the view
        updateImageView(drawable);
        //this.progressBar.setVisibility(View.GONE);
    }

    @SuppressLint("NewApi")
    private void updateImageView(Drawable drawable) {
        if (drawable != null) {
            // Try using your library and adding this layer type before switching your SVG parsing
            mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            mImageView.setImageDrawable(drawable);
        }
    }
}