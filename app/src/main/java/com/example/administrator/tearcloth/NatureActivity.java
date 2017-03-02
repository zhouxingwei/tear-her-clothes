package com.example.administrator.tearcloth;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import android.content.Intent;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NatureActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    public static int[] imageIds = new int[]{
            R.drawable.pre00, R.drawable.pre01, R.drawable.pre02,
            R.drawable.pre3, R.drawable.pre4, R.drawable.pre11,
            R.drawable.pre12, R.drawable.pre32, R.drawable.pre40,
            R.drawable.pre52,R.drawable.pre62
    };
    private int curPosition;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public NatureActivity() {
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        Gallery gall = (Gallery) findViewById(R.id.gallery);
        final ImageSwitcher swith = (ImageSwitcher) findViewById(R.id.image_switcher);
        swith.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageview = new ImageView(NatureActivity.this);
                //imageview.setBackgroundResource(0xff0000);
                imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageview.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageview;
            }
        });
        swith.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        swith.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imageIds.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imagview1 = new ImageView(NatureActivity.this);
                imagview1.setImageResource(imageIds[position % 9]);
                imagview1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imagview1.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imagview1;
            }
        };

        gall.setAdapter(adapter);
        gall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;   //it can optimize 1/2 memory
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                //opt.inSampleSize = 2;    //if picture very large,should set it! by zxw
                InputStream is = getResources().openRawResource(imageIds[position % imageIds.length]);
                Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
                BitmapDrawable bd = new BitmapDrawable(getResources(),bm);
                swith.setImageDrawable(bd);
                curPosition = position % imageIds.length;
            }
        });
        swith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String ts = "show new picture is " + Integer.toString(curPosition);
                //Toast.makeText(NatureActivity.this, ts, Toast.LENGTH_SHORT).show();
                Intent cloth = new Intent(NatureActivity.this,NatureClothActivity.class);
                cloth.putExtra("imageid",(int)(curPosition % imageIds.length));
                startActivity(cloth);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Nature Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.tearcloth/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Nature Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.tearcloth/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


