package com.gausslab.realwear.util.adapter.adapter_listener_interface;

interface OnApproveRejectContextMenuInteractionListener<T> extends OnItemInteractionListener<T>
{
    void onContextApprove(T obj);

    void onContextReject(T obj);
}
