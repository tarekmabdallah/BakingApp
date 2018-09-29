package com.gmail.tarekmabdallah91.bakingapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.DetailsActivity;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickListener;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.RecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeViewModel;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class MainFragment extends Fragment implements OnRecipeClickListener {


    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;

    private Context context;
    private RecipesAdapter adapter;
    private RoomPresenter roomPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUI();
        setViewModel();
    }

    private void initiateValues() {
        context = getContext();
        adapter = new RecipesAdapter(this);
        roomPresenter = RoomPresenter.getInstance(context);
    }

    private void setUI() {
        initiateValues();
        setRecyclerView();
    }

    private void setRecyclerView() {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
            ItemTouchHelper itemTouchHelper =
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO, ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            roomPresenter.DeleteRecipeDataFromRoom(context, (int) viewHolder.itemView.getTag());
                        }
                    });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
            ItemTouchHelper itemTouchHelper =
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            roomPresenter.DeleteRecipeDataFromRoom(context, (int) viewHolder.itemView.getTag());
                        }
                    });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setViewModel() {

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                // update UI
                if (null == recipeEntries || recipeEntries.isEmpty()) {
                    Toast.makeText(context, R.string.no_data_msg, Toast.LENGTH_LONG).show();
                } else {
                    adapter.swapList(recipeEntries);
                }
            }
        });
    }

    @Override
    public void onRecipeClicked(RecipeEntry recipeEntry) {
        Intent openDetailsActivity = new Intent(context, DetailsActivity.class);
        openDetailsActivity.putExtra(BakingConstants.RECIPE_KEYWORD, recipeEntry);
        startActivity(openDetailsActivity);
    }


}
