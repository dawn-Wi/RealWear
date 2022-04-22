package com.gausslab.realwear.util.component.viewholder;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

//import com.baec23.arlogbook.R;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnContextMenuInteractionListener;
import com.gausslab.realwear.databinding.ComponentImageBinding;

public class RemovableImageViewHolder extends ImageViewHolder
{
    private final OnContextMenuInteractionListener<ViewComponent> listener;

    public RemovableImageViewHolder(@NonNull ComponentImageBinding binding, OnContextMenuInteractionListener<ViewComponent> listener)
    {
        super(binding);
        this.listener = listener;
    }

    @Override
    public void bindData(ViewComponent viewComponent)
    {
        super.bindData(viewComponent);
        if(listener == null)
            return;
        iv_image.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
            {
                menu.setHeaderTitle("Select Action");
                MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
                MenuItem remove = menu.add(Menu.NONE, 2, 2, "Remove");
                edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        listener.onContextEdit(viewComponent);
                        return true;
                    }
                });
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        listener.onContextRemove(viewComponent);
                        return true;
                    }
                });
            }
        });
    }
}
