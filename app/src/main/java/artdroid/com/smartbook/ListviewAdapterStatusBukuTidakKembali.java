package artdroid.com.smartbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListviewAdapterStatusBukuTidakKembali extends ArrayAdapter<String> {
    private String[] idhistory;
    private String[] judul;
    private String[] posisisemula;
    private String[] posisisekarang;
    private Activity context;


    public ListviewAdapterStatusBukuTidakKembali(Activity context, String[] idhistory, String[] judul, String[] posisisemula, String[] posisisekarang) {
        super(context, R.layout.item_statustidakkembali, idhistory);
        this.context = context;
        this.idhistory = idhistory;
        this.judul = judul;
        this.posisisemula = posisisemula;
        this.posisisekarang = posisisekarang;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_statustidakkembali, null);
            mHolder = new ViewHolder();

            mHolder.tvjudul = (TextView) convertView.findViewById(R.id.judul);
            mHolder.tvposisisemula = (TextView) convertView.findViewById(R.id.posisisemula);
            mHolder.tvposisisekarang = (TextView) convertView.findViewById(R.id.posisisekarang);

            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvjudul.setText(judul[position]);
        mHolder.tvposisisemula.setText(posisisemula[position]);
        mHolder.tvposisisekarang.setText(posisisekarang[position]);

        return convertView;
    }

    private class ViewHolder {
        private TextView tvjudul,tvposisisemula,tvposisisekarang;
    }
}