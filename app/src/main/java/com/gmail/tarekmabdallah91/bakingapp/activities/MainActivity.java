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
package com.gmail.tarekmabdallah91.bakingapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.RecipesAdapter;
import com.gmail.tarekmabdallah91.bakingapp.adapters.main_activity_adapter.OnRecipeClickListener;
import com.gmail.tarekmabdallah91.bakingapp.room.PresenterRoom;
import com.gmail.tarekmabdallah91.bakingapp.room.recipe.RecipeEntry;
import com.gmail.tarekmabdallah91.bakingapp.room.recipe.RecipeViewModel;
import com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class MainActivity extends AppCompatActivity
implements OnRecipeClickListener {

    @BindView(R.id.rv_recipes)
    RecyclerView recyclerView;

    private RecipesAdapter adapter;
    private static Bundle lastInsertedData;
    private PresenterRoom presenterRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        initiateValues();
        setViewModel();

        // to get data from notification message while app is running in background
        getDataFromNotificationMessage();

    }

    private void initiateValues(){
        adapter = new RecipesAdapter(this);
        setRecyclerView();
        presenterRoom = PresenterRoom.getInstance(this);

    }

    private void setRecyclerView (){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenterRoom.DeleteRecipeDataFromRoom(getApplicationContext(),(int) viewHolder.itemView.getTag());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void setViewModel (){

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                // update UI
                if (null == recipeEntries || recipeEntries.isEmpty() ){
                    Toast.makeText(getBaseContext(),"no data" , Toast.LENGTH_LONG).show();
                }else{
                    adapter.swapList(recipeEntries);
                }
            }
        });
    }

    @Override
    public void onRecipeClicked(RecipeEntry recipeEntry) {
        Intent openDetailsActivity = new Intent(this , DetailsActivity.class);
        openDetailsActivity.putExtra(BakingConstants.RECIPE_KEYWORD,recipeEntry);
        startActivity(openDetailsActivity);
    }

    /**
     * to get data from notification message while app is running in background
     */
    private void getDataFromNotificationMessage(){
        Intent coming = getIntent();
        if (null != coming){
            Bundle data = coming.getExtras();
            if (null == lastInsertedData || !lastInsertedData.equals(data)) {
                // to avoid reloading and inserting data each time the device rotated
                lastInsertedData = data;
                presenterRoom.getRecipeDataFromMessageStoreItInRoom(this, data, false);
            }
        }
    }

}
