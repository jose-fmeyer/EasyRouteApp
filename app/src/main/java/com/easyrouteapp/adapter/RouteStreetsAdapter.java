package com.easyrouteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyrouteapp.R;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.domain.RouteStreet;
import com.easyrouteapp.event.StartDetailRouteEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by fernando on 10/08/2016.
 */
public class RouteStreetsAdapter extends RecyclerView.Adapter<RouteStreetsAdapter.RouteStreetViewHolder> {

    private List<RouteStreet> routes;
    private LayoutInflater layoutInflater;

    public RouteStreetsAdapter(Context context, List<RouteStreet> routes){
        this.routes = routes;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RouteStreetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.street_detail, parent, false);
        return new RouteStreetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteStreetViewHolder holder, int position) {
        holder.textSequence.setText(String.valueOf((routes.get(position)).getSequence()));
        holder.textName.setText((routes.get(position)).getName());
    }

    public void addListItem(RouteStreet route, int position){
        routes.add(position, route);
        notifyDataSetChanged();
    }

    public void clearData(){
        routes.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteStreetViewHolder extends RecyclerView.ViewHolder {
        public TextView textSequence;
        public TextView textName;

        public RouteStreetViewHolder(View itemView) {
            super(itemView);
            textSequence = (TextView) itemView.findViewById(R.id.street_sequence);
            textName = (TextView) itemView.findViewById(R.id.street_name);
        }
    }
}
