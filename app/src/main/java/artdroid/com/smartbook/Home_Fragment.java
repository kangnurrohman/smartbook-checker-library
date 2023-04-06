package artdroid.com.smartbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static android.content.Context.MODE_PRIVATE;

public class Home_Fragment extends android.support.v4.app.Fragment {

    View rootview;
    Button btnScanBarcode;
    Button btnJalankanScannerHardwareOn;
    Button btnJalankanScannerHardwareOff;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_home, container, false);

        btnScanBarcode = (Button) rootview.findViewById(R.id.btnScanBarcode);
        btnJalankanScannerHardwareOn = (Button) rootview.findViewById(R.id.btnScannerHardwareon);
        btnJalankanScannerHardwareOff = (Button) rootview.findViewById(R.id.btnScannerHardwareoff);
        btnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), BarcodeScanner.class));
            }
        });

        btnJalankanScannerHardwareOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OFFAct();
            }
        });
        btnJalankanScannerHardwareOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ONAct();
            }
        });

        return rootview;
    }

    private void OFFAct() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.43.96/OFF",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Kontrol = ", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray posts = json.optJSONArray("result");
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString() ,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
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

    private void ONAct() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.43.96/ON",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Kontrol = ", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray posts = json.optJSONArray("result");
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString() ,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
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
