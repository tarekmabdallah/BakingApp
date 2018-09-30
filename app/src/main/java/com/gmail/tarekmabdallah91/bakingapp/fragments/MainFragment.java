/*
 Copyright 2018 tarekmabdallah91@gmail.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
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
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.activities.DetailsActivity;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickListener;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickedOnFragment;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.RecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.RoomPresenter;
import com.gmail.tarekmabdallah91.bakingapp.data.room.recipe.RecipeViewModel;
import com.gmail.tarekmabdallah91.bakingapp.models.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class MainFragment extends Fragment implements OnRecipeClickListener {

    @BindView(R.id.empty_tv)
    TextView emptyTV;
    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;

    private Context context;
    private RecipesAdapter adapter;
    private RoomPresenter roomPresenter;
    private OnRecipeClickedOnFragment callbacks;


    public MainFragment() {

    }

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

    /**
     * Override onAttach to make sure that the container activity has implemented the callback
     *
     * @param context -
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            callbacks = (OnRecipeClickedOnFragment) context;
        } catch (ClassCastException e) {
            Timber.e(e);
        }

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
                    recyclerView.setVisibility(GONE);
                    emptyTV.setVisibility(VISIBLE);
                    Toast.makeText(context, R.string.no_data_msg, Toast.LENGTH_LONG).show();
                } else {
                    emptyTV.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    adapter.swapList(recipeEntries);
                }
            }
        });
    }

    @Override
    public void onRecipeClicked(RecipeEntry recipeEntry) {
        if (getActivity().findViewById(R.id.fragment_master_sw600) == null) {
            Intent openDetailsActivity = new Intent(context, DetailsActivity.class);
            openDetailsActivity.putExtra(BakingConstants.RECIPE_KEYWORD, recipeEntry);
            startActivity(openDetailsActivity);
        } else {
            callbacks.onRecipeClickedOnFragment(recipeEntry);
        }

    }

}
