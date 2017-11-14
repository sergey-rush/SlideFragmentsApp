package ru.slidefragmentsapp.Core;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.slidefragmentsapp.R;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {

        private List<Month> _monthList;

        public class MonthViewHolder extends RecyclerView.ViewHolder {

            TextView tvId;
            TextView tvName;

            public MonthViewHolder(View view) {
                super(view);
                tvId = (TextView) view.findViewById(R.id.tvId);
                tvName = (TextView) view.findViewById(R.id.tvName);
            }
        }

        public MonthAdapter(List<Month> monthList) {
            _monthList = monthList;
        }

        ViewGroup parent;

        @Override
        public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.parent = parent;
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_item, parent, false);
            return new MonthViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MonthViewHolder holder, int position) {
            Month month = _monthList.get(position);
            holder.tvId.setText(Integer.toString(month.Id));
            holder.tvName.setText(month.Name);
        }

        @Override
        public int getItemCount() {
            return _monthList.size();
        }
    }

