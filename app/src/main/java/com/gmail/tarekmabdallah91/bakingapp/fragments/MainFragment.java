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

import android.support.v4.app.Fragment;

public class MainFragment extends Fragment {

//    @BindView(R.id.empty_tv)
//    TextView emptyTV;
//    @BindView(R.id.rv_recipes)
//    RecyclerView recyclerView;
//
//    private Context context;
//    private RecipesAdapter adapter;
//    private RoomPresenter roomPresenter;
//    private static final String TAG = MainFragment.class.getSimpleName();
//
//
//    public MainFragment() {
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setUI();
//        setViewModel();
//    }
//
//    private void initiateValues() {
//        context = getContext();
//        roomPresenter = RoomPresenter.getInstance(context);
//    }
//
//
//
//    private void setUI() {
//        initiateValues();
//        setRecyclerView();
//    }
//
//    private void setRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        ItemTouchHelper itemTouchHelper =
//                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ZERO, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                        roomPresenter.DeleteRecipeDataFromRoom(context, (int) viewHolder.itemView.getTag());
//                    }
//                });
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//    }
//
//    private void setViewModel() {
//
//        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
//        recipeViewModel.getRecipes().observe(this, new Observer<List<RecipeEntry>>() {
//            @Override
//            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
//                // update UI
//                if (null == recipeEntries || recipeEntries.isEmpty()) {
//                    recyclerView.setVisibility(GONE);
//                    emptyTV.setVisibility(VISIBLE);
//                    Toast.makeText(context, R.string.no_data_msg, Toast.LENGTH_LONG).show();
//                } else {
//                    emptyTV.setVisibility(GONE);
//                    recyclerView.setVisibility(VISIBLE);
//                    adapter.swapList(recipeEntries);
//                }
//            }
//        });
//    }
//
//

}
