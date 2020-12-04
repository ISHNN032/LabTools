package com.ishnn.labtools.util.adapter

interface OnMoveAndSwipedListener {
    fun onItemMove(start: Int, end: Int): Boolean
    fun onItemDismiss(position: Int)
}