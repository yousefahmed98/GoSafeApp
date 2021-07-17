package com.example.gosafe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private DefectAdapter adapter;
    public TouchHelper(DefectAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT ) {
            adapter.deleteDefect(position);
        }
        if(direction == ItemTouchHelper.RIGHT){
            adapter.fixDefect(position);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        System.out.println("test");
        return super.isLongPressDragEnabled();
    }

    @Override
    public void onChildDraw(@NonNull  Canvas c, @NonNull  RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(Color.RED)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightBackgroundColor(R.color.logoColor)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_done_all_24)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
