package com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper

interface ItemTouchHelperCallback {

    fun removeItem(position: Int)
    fun undoLastRemove()
}