package ru.slidefragmentsapp.Views;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.slidefragmentsapp.Core.SalePoint;
import ru.slidefragmentsapp.Core.SalePointAdapter;
import ru.slidefragmentsapp.Data.DataAccess;
import ru.slidefragmentsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstTabFragment extends Fragment {

    private Context context;
    private List<SalePoint> salesPoints = new ArrayList<>();
    private View view;

    public FirstTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_tab, container, false);
        context = getActivity();
        DataAsyncTask dataAsyncTask = new DataAsyncTask();
        dataAsyncTask.execute();
        return view;
    }

    private void loadDataCallback() {
        if (salesPoints.size() > 0) {
            SalePointAdapter adapter = new SalePointAdapter(context, salesPoints);
            ListView listView = (ListView) view.findViewById(R.id.lvSalePoints);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    RelativeLayout layout = (RelativeLayout) view;
                    TextView tvAddress = (TextView) layout.findViewById(R.id.tvAddress);
                    Toast.makeText(context, tvAddress.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class DataAsyncTask extends AsyncTask<Void, Void, Void> {

        private DataAsyncTask() {
        }

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Пожалуйста, подождите...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DataAccess dataAccess = DataAccess.getInstance(context);
            int total = dataAccess.countSalePoints();
            if (total == 0) {
                //int added = dataAccess.addSalePoints();
            } else {
                salesPoints = dataAccess.getSalePoints(100);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            loadDataCallback();
        }
    }
}
