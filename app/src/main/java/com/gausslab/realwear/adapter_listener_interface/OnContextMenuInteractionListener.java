package com.gausslab.realwear.adapter_listener_interface;

public interface OnContextMenuInteractionListener<T> extends OnItemInteractionListener<T>
{
    void onContextEdit(T obj);

    void onContextRemove(T obj);
}

