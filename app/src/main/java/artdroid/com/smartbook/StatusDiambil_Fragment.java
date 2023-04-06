package artdroid.com.smartbook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StatusDiambil_Fragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View rootview;

    ListView listView;

    private SwipeRefreshLayout swipeRefreshLayout;

    String[] arrayidhistory;
    String[] arrayjudul;
    String[] arrayposisi;
    String[] arraywaktu;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_statusdiambil, container, false);

        listView = (ListView) rootview.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        StatusBukuAct();
                                    }
                                }
        );

        return rootview;
    }

    @Override
    public void onRefresh() {
        StatusBukuAct();
    }

    public void StatusBukuAct(){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
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

                            arrayidhistory = new String[posts.length()];
                            arrayjudul = new String[posts.length()];
                            arrayposisi = new String[posts.length()];
                            arraywaktu = new String[posts.length()];

                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);
                                String idhistory = post.optString("idhistory");
                                String judul = post.optString("judul");
                                String posisi = post.optString("posisi");
                                String waktu = post.optString("waktu");

                                arrayidhistory[i] = idhistory;
                                arrayjudul[i] = judul;
                                arrayposisi[i] = posisi;
                                arraywaktu[i] = waktu;
                            }

                            ListviewAdapterStatusBukuDiambil customAdapter = new ListviewAdapterStatusBukuDiambil(getActivity(),arrayidhistory,arrayjudul,arrayposisi,arraywaktu);
                            listView.setAdapter(customAdapter);

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("req","statusdiambil");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
