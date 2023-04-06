package artdroid.com.smartbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grafik_Fragment extends android.support.v4.app.Fragment {

    View rootview;

    BarChart barchart;

    ArrayList<String> bulan;
    ArrayList<BarEntry> jumlah;

    Spinner status;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_grafik, container, false);

        barchart = (BarChart) rootview.findViewById(R.id.barchart);
        status = (Spinner) rootview.findViewById(R.id.spinStatus);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GrafikAct();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootview;
    }

    public void GrafikAct(){
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

                            bulan = new ArrayList<>();
                            jumlah = new ArrayList<>();

                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject post = posts.optJSONObject(i);
                                String strbulan = post.optString("bulan");
                                String strjumlah = post.optString("jumlah");

                                bulan.add(strbulan);
                                jumlah.add(new BarEntry(Float.parseFloat(i+"f"),Float.parseFloat(strjumlah+"f")));
                            }

                            BarDataSet barDataSet = new BarDataSet(jumlah, "Jumlah Buku");
                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                            ArrayList<BarDataSet> dataSets = new ArrayList<>();
                            dataSets.add(barDataSet);

                            BarData data = new BarData(barDataSet);
                            barchart.setData(data);
                            barchart.getDescription().setText("Data Buku Diambil");
                            barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bulan));
                            barchart.getXAxis().setGranularity(1);
                            barchart.getXAxis().setGranularityEnabled(true);
                            barchart.animateXY(2000, 2000);
                            barchart.invalidate();

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
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("req","grafik");
                params.put("status",String.valueOf(status.getSelectedItemPosition()));
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
