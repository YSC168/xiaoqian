package com.oq.app;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.*;
import java.util.*;
import android.graphics.*;
import java.net.*;
import java.io.*;
import android.view.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;

public class DemoFragment3 extends Fragment {
	private boolean isopent = false;
	private ColorList[] color = {new ColorList("红色", R.drawable.image_1)};
    public List<ColorList> colorList = new ArrayList<>();
    public ColorListAdapter adapter;
	private SwipeRefreshLayout swipeRefresh;
    public static DemoFragment3 newInstance(String info) {
        Bundle args = new Bundle();
        DemoFragment3 fragment = new DemoFragment3();
        args.putString("picture", info);
        fragment.setArguments(args);
        return fragment;
    }
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);
		
		initColor();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);//this
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ColorListAdapter(colorList);
        recyclerView.setAdapter(adapter);
		swipeRefresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					swipeRefresh.setColorSchemeColors(getRandomColor());
					refreshColor();
				}
			});
	}
	
	private void refreshColor() {
		new Thread(new Runnable() {
				@Override
				public void run() {
					initColor();
					adapter.notifyDataSetChanged();
					swipeRefresh.setRefreshing(false);
				}
			}).start();
    }
	private void initColor() {
        colorList.clear();
        for (int i = 0; i < 600; i++) {
            Random random = new Random();
            int index = random.nextInt(color.length);
            colorList.add(color[index]);	
        }
    }
	
	public int getRandomColor() {
        Random rand = new Random();
        return Color.argb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

    }
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.picture, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
		if(id==R.id.suiji){
			if (!isopent) {
				RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
				GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);//this
				recyclerView.setLayoutManager(layoutManager);
				isopent = true;
			} else {
				RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
				GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);//this
				recyclerView.setLayoutManager(layoutManager);
				isopent = false;
			}	
		}
		else
            return super.onOptionsItemSelected(item);
		return false;
	}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   View view = inflater.inflate(R.layout.fragment_demo3, null);
	
	         return   inflater.inflate(R.layout.fragment_demo3, container, false);		
			
//        TextView tvInfo = (TextView) view.findViewById(R.id.text);
//        tvInfo.setText(getArguments().getString("info"));
//        tvInfo.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT).show();
//				}     setHasOptionsMenu(true);
//			});
      
   }
}
