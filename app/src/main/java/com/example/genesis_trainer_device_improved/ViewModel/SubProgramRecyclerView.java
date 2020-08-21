package com.example.genesis_trainer_device_improved.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genesis_trainer_device_improved.R;


public class SubProgramRecyclerView extends RecyclerView.Adapter<SubProgramRecyclerView.SubProgram_Holder> {
    private static final String TAG = "SubProgramRecyclerView";

   public interface SubprogramClick{
        void SubProgramClicked(int position, String subprogram);
    }

    private int selectedPos = RecyclerView.NO_POSITION;
    private SubprogramClick subprogramClick;
    private String[] subprograms;
    private Context context;

    public SubProgramRecyclerView(String[] subprogramlist, Context context, SubprogramClick subprogramClick ) {
        subprograms = subprogramlist;
        this.context=context;
        this.subprogramClick = subprogramClick;
    }

    @NonNull
    @Override
    public SubProgram_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subprogramlist_recycler_content,parent,false);

        return new SubProgram_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubProgram_Holder holder, int position) {
        if (subprograms!=null)
        holder.buttonSubprogram.setText(subprograms[position]);
        holder.buttonSubprogram.setSelected(selectedPos == position);

    }

    @Override
    public int getItemCount() {
        if (subprograms !=null){
            Log.d(TAG, "onBindViewHolder: subprogramsList= " + subprograms.length);
            return subprograms.length;
        }
        return 0;
    }

    public class SubProgram_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button buttonSubprogram;

        @Override
        public void onClick(View v) {

            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);

            subprogramClick.SubProgramClicked(getAdapterPosition(),buttonSubprogram.getText().toString());


        }

        public SubProgram_Holder(@NonNull View itemView) {
            super(itemView);

            buttonSubprogram = itemView.findViewById(R.id.button_subprogram);
            buttonSubprogram.setOnClickListener(this);

        }
    }
}
