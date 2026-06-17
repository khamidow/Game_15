package uz.mobiler.gitagame15;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter1 extends RecyclerView.Adapter<RvAdapter1.VH> {

    private final List<ModelData> list;
    public RvAdapter1(List<ModelData> list) {
        this.list = list;
    }

    public class VH extends RecyclerView.ViewHolder {
        public  ImageView img;
        public  TextView type;
        public  TextView time;
        public  TextView move;

        public VH(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img_cup);
            time = view.findViewById(R.id.time);
            move = view.findViewById(R.id.count);
            type = view.findViewById(R.id.type_tv);
        }

        public void bind() {
            ModelData item = list.get(getAbsoluteAdapterPosition());
            type.setText(item.getType() + "x" + item.getType());
            img.setImageResource(item.getImg());
            time.setText(item.getTime());
            move.setText(item.getMove());
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_records, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}