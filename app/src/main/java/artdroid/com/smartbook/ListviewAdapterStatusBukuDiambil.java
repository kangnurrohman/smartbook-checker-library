package artdroid.com.smartbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListviewAdapterStatusBukuDiambil extends ArrayAdapter<String> {
    private String[] idhistory;
    private String[] judul;
    private String[] posisi;
    private String[] waktu;
    private Activity context;


    public ListviewAdapterStatusBukuDiambil(Activity context, String[] idhistory, String[] judul, String[] posisi, String[] waktu) {
        super(context, R.layout.item_statusbukudiambil, idhistory);
        this.context = context;
        this.idhistory = idhistory;
        this.judul = judul;
        this.posisi = posisi;
        this.waktu = waktu;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_statusbukudiambil, null);
            mHolder = new ViewHolder();

            mHolder.tvjudul = (TextView) convertView.findViewById(R.id.judul);
            mHolder.tvposisi = (TextView) convertView.findViewById(R.id.posisi);
            mHolder.tvwaktu = (TextView) convertView.findViewById(R.id.waktu);

            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvjudul.setText(judul[position]);
        mHolder.tvposisi.setText(posisi[position]);
        mHolder.tvwaktu.setText(waktu[position]);

        return convertView;
    }

    private class ViewHolder {
        private TextView tvjudul,tvposisi,tvwaktu;
    }
}