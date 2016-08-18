package com.easyrouteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyrouteapp.R;
import com.easyrouteapp.domain.RouteTimetables;

import java.util.List;

/**
 * Created by fernando on 10/08/2016.
 */
public class RouteTimetablesAdapter extends RecyclerView.Adapter<RouteTimetablesAdapter.RouteTimetamblesViewHolder> {

    private List<RouteTimetables> timetables;
    private LayoutInflater layoutInflater;

    public RouteTimetablesAdapter(Context context, List<RouteTimetables> timetables){
        this.timetables = timetables;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RouteTimetamblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.timestable_detail, parent, false);
        return new RouteTimetamblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteTimetamblesViewHolder holder, int position) {
        holder.calendar.setText((timetables.get(position)).getCalendar());
        holder.time.setText((timetables.get(position)).getTime());
    }

    public void addListItem(RouteTimetables route, int position){
        timetables.add(position, route);
        notifyDataSetChanged();
    }

    public void clearData(){
        timetables.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return timetables.size();
    }

    public class RouteTimetamblesViewHolder extends RecyclerView.ViewHolder {
        public TextView calendar;
        public TextView time;

        public RouteTimetamblesViewHolder(View itemView) {
            super(itemView);
            calendar = (TextView) itemView.findViewById(R.id.time_calendar);
            time = (TextView) itemView.findViewById(R.id.tt_hour);
        }
    }
}
