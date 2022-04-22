package com.gausslab.realwear.util.adapter.adapter_listener_interface;

public interface OnClickInteractionListener<T> extends OnItemInteractionListener<T>
{
    void onItemClick(T obj);
}
