package com.example.cit.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cit.R;
import com.example.cit.model.StartClockModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CompletetTaskAdapter extends ArrayAdapter<StartClockModel> {

    private final List<StartClockModel> list;
    private final Activity context;
    private ModelFilter filter;
    private LayoutInflater inflater;
    private List<StartClockModel> allModelItemsArray;
    private List<StartClockModel> filteredModelItemsArray;

    public CompletetTaskAdapter(List<StartClockModel> list, Activity context) {
        super(context, R.layout.completed_task_layout,list);
        this.list = list;
        this.context = context;
        this.allModelItemsArray = new ArrayList<StartClockModel>(list);
        this.filteredModelItemsArray = new ArrayList<StartClockModel>(allModelItemsArray);
        inflater = context.getLayoutInflater();
        getFilter();
    }

    static class ViewHolder {
        protected TextView prjname;
        protected TextView taskname;
        protected TextView starttime;
        protected TextView endtime;
        protected TextView ttltimeTaken;

    }
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ModelFilter();
        }
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= null;


        if(convertView ==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.completed_task_layout, null);

            final CompletetTaskAdapter.ViewHolder holder = new CompletetTaskAdapter.ViewHolder();

            holder.prjname = view.findViewById(R.id.CprojName);
            holder.taskname = view.findViewById(R.id.CTaskname);
            holder.starttime = view.findViewById(R.id.Cstartedtime);
            holder.endtime = view.findViewById(R.id.Cendtime);
            holder.ttltimeTaken = view.findViewById(R.id.CtimeTaken);

            view.setTag(holder);

        }else{

            view = convertView;

        }

        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.prjname.setText(list.get(position).getProjectName());
        holder.taskname.setText(list.get(position).getTaskName());
        holder.starttime.setText(list.get(position).getStartTime());
        holder.endtime.setText(list.get(position).getEndTime());
        holder.ttltimeTaken.setText(list.get(position).getTtlTimeTaken());


        return view;
    }

    private class ModelFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<StartClockModel> filteredItems = new ArrayList<StartClockModel>();

                for (int i = 0, l = allModelItemsArray.size(); i < l; i++) {
                    StartClockModel customer = allModelItemsArray.get(i);
                    String strNum = customer.getProjectName();
                    String strName = customer.getTaskName();
                    if (strNum.toLowerCase().contains(constraint)|| strName.toLowerCase().contains(constraint))
                        filteredItems.add(customer);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = allModelItemsArray;
                    result.count = allModelItemsArray.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            filteredModelItemsArray = (ArrayList<StartClockModel>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filteredModelItemsArray.size(); i < l; i++)
                add(filteredModelItemsArray.get(i));
            notifyDataSetInvalidated();
        }
    }
}
