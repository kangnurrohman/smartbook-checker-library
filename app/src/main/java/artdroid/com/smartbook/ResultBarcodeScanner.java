package artdroid.com.smartbook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultBarcodeScanner extends AppCompatActivity {

    ImageView gambar;
    TextView idbuku,judulbuku,namapengarang,posisisemula,posisiterakhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_barcode_scanner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gambar = (ImageView) findViewById(R.id.gambar);
        idbuku = (TextView) findViewById(R.id.idbuku);
        judulbuku = (TextView) findViewById(R.id.judulbuku);
        namapengarang = (TextView) findViewById(R.id.namapengarang);
        posisisemula = (TextView) findViewById(R.id.posisisemula);
        posisiterakhir = (TextView) findViewById(R.id.posisiterakhir);

        StatusBukuAct();
    }

    public void StatusBukuAct(){
        final ProgressDialog dialog = new ProgressDialog(ResultBarcodeScanner.this);
        dialog.setMessage("Sedang mengambil data...");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SERVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data = ",response);
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray posts = json.optJSONArray("result");

                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);
                                String strgambar = post.optString("gambarbuku");
                                String stridbuku = post.optString("idbuku");
                                String strjudulbuku = post.optString("judulbuku");
                                String strnamapengarang = post.optString("namapengarang");
                                String strposisisemula = post.optString("posisisemula");
                                String strposisiterakhir = post.optString("posisiterakhir");

                                Glide.with(ResultBarcodeScanner.this).load(strgambar)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(gambar);
                                idbuku.setText(stridbuku);
                                judulbuku.setText(strjudulbuku);
                                namapengarang.setText(strnamapengarang);
                                posisisemula.setText(strposisisemula);
                                posisiterakhir.setText(strposisiterakhir);
                            }

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResultBarcodeScanner.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("req","detailbuku");
                params.put("idbuku", getIntent().getStringExtra("code"));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ResultBarcodeScanner.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
