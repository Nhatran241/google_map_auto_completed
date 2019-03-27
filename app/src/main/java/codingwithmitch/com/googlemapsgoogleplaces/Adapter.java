package codingwithmitch.com.googlemapsgoogleplaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import codingwithmitch.com.googlemapsgoogleplaces.models.PlaceMode;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<PlaceMode> data ;

    public Adapter(ArrayList<PlaceMode> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.main.setText(data.get(position).getMain());
        holder.second.setText(data.get(position).getSecond());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView main;
        TextView second;
        public ViewHolder(View itemView) {
            super(itemView);
            main=itemView.findViewById(R.id.tv_main);
            second=itemView.findViewById(R.id.tv_second);
        }
    }
}
