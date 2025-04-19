package com.example.project02.characterViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.R;


public class CharacterViewHolder extends RecyclerView.ViewHolder {

    private final TextView characterRecyclerItemTextView;

    private CharacterViewHolder(View itemView) {
        super(itemView);
        characterRecyclerItemTextView = itemView.findViewById(R.id.characterRecyclerItemTextView);
    }

    public void bind(String character) {
        characterRecyclerItemTextView.setText(character);
    }

    static CharacterViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_recycler_item, parent, false);
        return new CharacterViewHolder(view);
    }
}

