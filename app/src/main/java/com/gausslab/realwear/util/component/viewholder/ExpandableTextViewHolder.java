package com.gausslab.realwear.util.component.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.adapter.ReportComponentRecyclerViewAdapter;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.databinding.ComponentTextExpandableBinding;

public class ExpandableTextViewHolder extends BaseViewHolder
{
    final TextView tv_label;
    final TextView tv_content;
    final RecyclerView recyclerView;
    final ConstraintLayout cl_container;
    boolean expanded = false;

    public ExpandableTextViewHolder(@NonNull ComponentTextExpandableBinding binding)
    {
        super(binding.getRoot());
        tv_label = binding.compExpandableTextTvLabel;
        tv_content = binding.compExpandableTextTvContent;
        recyclerView = binding.compExpandableTextRvSubtext;
        cl_container = binding.compExpandableTextClContainer;
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void bindData(ViewComponent viewComponent)
    {
        ComponentData data = viewComponent.getData();
        if(data.getLabelText() == null)
            tv_label.setVisibility(View.GONE);
        else
        {
            tv_label.setText(data.getLabelText());
            tv_label.setVisibility(View.VISIBLE);
        }
        if(data.getTextContent() == null)
            tv_content.setVisibility(View.GONE);
        else
        {
            tv_content.setText(data.getTextContent());
            tv_content.setVisibility(View.VISIBLE);
        }
        if(data.getSubComponents() != null && data.getSubComponents().size() > 0)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            ReportComponentRecyclerViewAdapter adapter = new ReportComponentRecyclerViewAdapter(data.getSubComponents());
            recyclerView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
        }
        cl_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                expanded = !expanded;
                if(expanded)
                    expand();
                else
                    collapse();
            }
        });
    }

    private void expand()
    {
        //listener.onExpand();
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void collapse()
    {
        //listener.onCollapse();
        recyclerView.setVisibility(View.GONE);
    }
}
