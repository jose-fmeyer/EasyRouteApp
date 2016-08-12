package com.easyrouteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyrouteapp.R;
import com.easyrouteapp.domain.EntityBase;
import com.easyrouteapp.domain.Route;
import com.easyrouteapp.event.ListItemClickEvent;
import com.easyrouteapp.event.StartDetailRouteEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by fernando on 10/08/2016.
 */
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private List<Route> routes;
    private LayoutInflater layoutInflater;

    public RouteAdapter(Context c, List<Route> routes){
        this.routes = routes;
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.route_detail, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        holder.textShortName.setText((routes.get(position)).getShortName());
        holder.textLongName.setText((routes.get(position)).getLongName());
    }

    public void addListItem(Route route, int position){
        routes.add(route);
        notifyItemInserted(position);
    }

    public void clearData(){
        routes.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textShortName;
        public TextView textLongName;

        public RouteViewHolder(View itemView) {
            super(itemView);
            textShortName = (TextView) itemView.findViewById(R.id.route_shortname);
            textLongName = (TextView) itemView.findViewById(R.id.route_longname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new StartDetailRouteEvent(routes.get(getAdapterPosition())));
        }
    }
}
