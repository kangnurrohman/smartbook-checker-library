package artdroid.com.smartbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListviewAdapterStatusBukuTidakAda extends ArrayAdapter<String> {
    private String[] idhistory;
    private String[] judul;
    private String[] posisisemula;
    private String[] tanggal;
    private Activity context;


    public ListviewAdapterStatusBukuTidakAda(Activity context, String[] idhistory, String[] judul, String[] posisisemula, String[] tanggal) {
        super(context, R.layout.item_statustidakada, idhistory);
        this.context = context;
        this.idhistory = idhistory;
        this.judul = judul;
        this.posisisemula = posisisemula;
        this.tanggal = tanggal;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_statustidakada, null);
            mHolder = new ViewHolder();

            mHolder.tvjudul = (TextView) convertView.findViewById(R.id.judul);
            mHolder.tvposisisemula = (TextView) convertView.findViewById(R.id.posisisemula);
            mHolder.tvtanggal = (TextView) convertView.findViewById(R.id.tanggal);

            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvjudul.setText(judul[position]);
        mHolder.tvposisisemula.setText(posisisemula[position]);
        mHolder.tvtanggal.setText(tanggal[position]);

        return convertView;
    }

    private class ViewHolder {
        private TextView tvjudul,tvposisisemula,tvtanggal;
    }
}