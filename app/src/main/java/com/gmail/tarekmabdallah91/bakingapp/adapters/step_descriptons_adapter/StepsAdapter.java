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
package com.gmail.tarekmabdallah91.bakingapp.adapters.step_descriptons_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.models.ParentInExpendableRecyclerView;

import java.util.List;

import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {
    private final OnStepClickListener onStepClickListener;
    private List<ParentInExpendableRecyclerView> data;
    private Context context;

    public StepsAdapter(OnStepClickListener onStepClickListener) {
        this.onStepClickListener = onStepClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.step_item, parent, false);
        return new StepsViewHolder(root, data, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsViewHolder holder, int position) {
        ParentInExpendableRecyclerView parentInExpendableRecyclerView = data.get(position);
        final String parentName = parentInExpendableRecyclerView.getParentName();

        holder.parentName.setText(parentName);
        List<String> textsList = parentInExpendableRecyclerView.getChildes();
        int noOfChildTextViews = holder.childItemsLayout.getChildCount();
        int noOfChildes = parentInExpendableRecyclerView.getChildes().size();

        // to hide any text view while it's id is more than size of childes list
        if (noOfChildes < noOfChildTextViews) {
            for (int i = noOfChildes; i < noOfChildTextViews; i++) {
                TextView currentTextView = (TextView) holder.childItemsLayout.getChildAt(i);
                currentTextView.setVisibility(View.GONE);
            }
        }

        // to visible only text views which match size of parent's childes ,
        // then set onClickListener for each one to get it's position
        for (int i = ZERO; i < noOfChildes; i++) {
            final TextView currentTextView = (TextView) holder.childItemsLayout.getChildAt(i);
            currentTextView.setText(textsList.get(i));
            currentTextView.setTag(i);
            View.OnClickListener onClickListenerForTextView = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context.getString(R.string.steps_label).equals(parentName)) {
                        int position = Integer.parseInt(String.valueOf(currentTextView.getTag()));
                        onStepClickListener.onStepClicked(position);
                    }
                }
            };
            currentTextView.setOnClickListener(onClickListenerForTextView);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) return ZERO;
        return data.size();
    }

    public void swapList(List<ParentInExpendableRecyclerView> parentInExpendableRecyclerViews) {
        this.data = parentInExpendableRecyclerViews;
        notifyDataSetChanged();
    }

}
