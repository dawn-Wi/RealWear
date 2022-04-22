package com.gausslab.realwear.util.adapter.adapter_listener_interface;

public interface OnMyTaskContextMenuInteractionListener<T> extends OnItemInteractionListener<T>
{
    void onItemClick(T obj);

    void onContextReturnTask(T obj);
}
