package ru.slidefragmentsapp.Core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.slidefragmentsapp.R;

public class SalePointAdapter extends ArrayAdapter<String> {

    Context context;
    List<SalePoint> salesPoints;

    public SalePointAdapter(Context context, List<SalePoint> salesPoints) {
        super(context, R.layout.sale_point_item);
        this.context = context;
        this.salesPoints = salesPoints;
    }

    @Override
    public int getCount() {
        return salesPoints.size();
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.sale_point_item, parent, false);
            holder.tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            holder.tvMetro = (TextView) view.findViewById(R.id.tvMetro);
            holder.tvOpenHours = (TextView) view.findViewById(R.id.tvOpenHours);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        SalePoint salePoint = salesPoints.get(position);
        holder.tvAddress.setText(salePoint.Address);
        holder.tvMetro.setText(salePoint.Metro);
        holder.tvOpenHours.setText(salePoint.OpenHours);

        return view;
    }

    static class ViewHolder {
        TextView tvAddress;
        TextView tvMetro;
        TextView tvOpenHours;
    }
}