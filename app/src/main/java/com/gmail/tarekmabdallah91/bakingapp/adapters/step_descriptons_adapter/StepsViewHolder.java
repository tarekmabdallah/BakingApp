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
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.tarekmabdallah91.bakingapp.R;
import com.gmail.tarekmabdallah91.bakingapp.models.ParentInExpendableRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.child_items_layout)
    LinearLayout childItemsLayout;
    @BindView(R.id.parent_name_tv)
    TextView parentName;

    StepsViewHolder(View itemView, List<ParentInExpendableRecyclerView> data, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

        childItemsLayout.setVisibility(GONE);
        int paddingValue = (int) context.getResources().getDimension(R.dimen.padding2);

        // calculate the maximum number of child items
        int maxNoOfChild = ZERO;
        for (int index = ZERO; index < data.size(); index++) {
            int maxSizeTemp = data.get(index).getChildes().size();
            if (maxSizeTemp > maxNoOfChild) maxNoOfChild = maxSizeTemp;
        }

        for (int indexView = ZERO; indexView < maxNoOfChild; indexView++) {
            TextView textView = new TextView(context);
            textView.setId(indexView);
            textView.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            childItemsLayout.addView(textView, layoutParams);
        }

        parentName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.parent_name_tv) {
            if (childItemsLayout.getVisibility() == View.VISIBLE) {
                childItemsLayout.setVisibility(View.GONE);
            } else {
                childItemsLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
