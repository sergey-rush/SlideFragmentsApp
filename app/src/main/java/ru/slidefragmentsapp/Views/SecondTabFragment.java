package ru.slidefragmentsapp.Views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.slidefragmentsapp.Core.DividerItemDecoration;
import ru.slidefragmentsapp.Core.Month;
import ru.slidefragmentsapp.Core.MonthAdapter;
import ru.slidefragmentsapp.Core.RecyclerTouchListener;
import ru.slidefragmentsapp.Data.DataProvider;
import ru.slidefragmentsapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondTabFragment extends Fragment {

    private Context _context;
    private RecyclerView recyclerView;
    private MonthAdapter adapter;

    public SecondTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);
        _context = view.getContext();
        List<Month> months = DataProvider.getMonths(_context);

        adapter = new MonthAdapter(months);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(_context, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(_context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView tvId = (TextView) view.findViewById(R.id.tvId);
                Toast.makeText(_context, "Item click: " + tvId.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
}
