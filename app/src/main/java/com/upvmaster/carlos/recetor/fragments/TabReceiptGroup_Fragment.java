package com.upvmaster.carlos.recetor.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.upvmaster.carlos.recetor.R;
import com.upvmaster.carlos.recetor.activities.ListReceipt_Activity;
import com.upvmaster.carlos.recetor.activities.ViewReceipt_Activity;
import com.upvmaster.carlos.recetor.adapters.GroupAdapter;
import com.upvmaster.carlos.recetor.bbdd.DBHelper;
import com.upvmaster.carlos.recetor.bbdd.dao.ReceiptDao;
import com.upvmaster.carlos.recetor.entities.Receipt;
import com.upvmaster.carlos.recetor.utils.UtilsReceipt;
import com.upvmaster.carlos.recetor.view.RecyclerViewDividerItemDecoration;

/**
 * Created by Carlos on 03/12/2016.
 */

public class TabReceiptGroup_Fragment extends Fragment{

    private RecyclerView recyclerView;
    private GroupAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private ListReceipt_Activity parent;
    private View vistaTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (ListReceipt_Activity) getActivity();
        vistaTab =   inflater.inflate(R.layout.tab_receipt_group,container,false);
        recyclerView = (RecyclerView) vistaTab.findViewById(R.id.rv_recetas_group);
        adaptador = new GroupAdapter(vistaTab.getContext(),((ListReceipt_Activity)getActivity()).getGroup_list());
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parent.isSonidos()){
                    MediaPlayer mp_list = MediaPlayer.create(parent, R.raw.sonido_lista);
                    mp_list.start();
                }
                int pos = recyclerView.getChildAdapterPosition(view);
                ViewReceipt_Activity vista_activity = new ViewReceipt_Activity();
                Receipt r = ((ListReceipt_Activity)getActivity()).getGroup_list().get(pos);
                Intent i = new Intent(getContext(),vista_activity.getClass());
                i.putExtra(ViewReceipt_Activity.ID_VIENE_LISTA,true);
                i.putExtra(ViewReceipt_Activity.ID_RECETA, new Gson().toJson(r));
                startActivity(i);
                parent.finish();
            }
        });
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(vistaTab.getContext());
        recyclerView.setLayoutManager(layoutManager);
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adaptador);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.addItemDecoration(new RecyclerViewDividerItemDecoration(getActivity()));
        inicializarBorrado(recyclerView);
        return vistaTab;
    }

    private void eliminarReceta(final int position) {
        // Guardamos el item en memoria por si el usuario deshace la acción
        final Receipt item = adaptador.getItems().get(position);
        // Borramos el item de la lista
        adaptador.removeItem(position);
        // Construimos y mostramos la snackbar
        final Runnable undo = new Runnable() {
            @Override
            public void run() {
                adaptador.addItem(position, item);
            }
        };
        final Runnable dismiss = new Runnable() {
            @Override
            public void run() {
                new BorrarReceiptTask(item).execute();
            }
        };
        Snackbar snackbar = Snackbar.make(vistaTab, "La receta ha sido eliminada", Snackbar.LENGTH_LONG);
        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setTextColor(Color.WHITE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo.run();
            }
        };
        snackbar.setAction("Deshacer", listener);
        snackbar.setActionTextColor(Color.GRAY);
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if ((event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) || (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)) {
                    dismiss.run();
                }
            }
        });
        snackbar.show();
    }

    private void inicializarBorrado(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                eliminarReceta(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap bitmap=null;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    UtilsReceipt.pintarFondo(getActivity(), c, viewHolder, dX);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class BorrarReceiptTask extends AsyncTask<Void, Void, Void> {

        private Receipt receta;

        private BorrarReceiptTask(Receipt receta) {
            this.receta = receta;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase db = DBHelper.getDatabase(getActivity());
            try {
                new ReceiptDao(db).delete(receta.getId());
            } catch (Exception e) {
                Log.e(getClass().getName(),e.getMessage(),e);
            } finally {
                db.close();
            }
            return null;
        }

    }
}
