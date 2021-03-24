package com.example.cafeyab.ui.Home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.ui.Home.cafepagesinfo.FavDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavDB favDB;
    private  List<Cafe> cafes=new ArrayList<>();
    private Adapter_MyFavList favAdapter;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        favDB = new FavDB(getActivity());
        recyclerView = view.findViewById(R.id.recyclerview_favourite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // add item touch helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // set swipe to recyclerview
        loadData();

        return view;
    }


    private void loadData() {
        if (cafes != null) {
            cafes.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {

                String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                String image = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE));
                Cafe itemlis=new Cafe(id,title,image);
//                Toast.makeText(getActivity(),itemlis.toString(),Toast.LENGTH_LONG).show();
                cafes.add(itemlis);


            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        favAdapter = new Adapter_MyFavList(cafes, getActivity(), getFragmentManager());
        recyclerView.setAdapter(favAdapter);

    }


    // remove item after swipe
    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition(); // get position which is swipe
            final Cafe favItem = cafes.get(position);
            if (direction == ItemTouchHelper.LEFT) { //if swipe left
                favAdapter.notifyItemRemoved(position); // item removed from recyclerview
                cafes.remove(position); //then remove item
                favDB.remove_fav(favItem.getId()); // remove item from database
            }
        }
    };
}
