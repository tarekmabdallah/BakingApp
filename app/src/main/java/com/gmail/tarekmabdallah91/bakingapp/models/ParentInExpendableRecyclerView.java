package com.gmail.tarekmabdallah91.bakingapp.models;

import java.io.Serializable;
import java.util.List;

public class ParentInExpendableRecyclerView implements Serializable {

    private String parentName;
    private List<String> childes;

    public ParentInExpendableRecyclerView(String parentName, List<String> childes) {
        this.parentName = parentName;
        this.childes = childes;
    }

    public List<String> getChildes() {
        return childes;
    }

    public void setChildes(List<String> childes) {
        this.childes = childes;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
