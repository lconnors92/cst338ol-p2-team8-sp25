package com.example.project02.characterViewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CharacterAdapter extends ListAdapter<Character, CharacterViewHolder> {
    private boolean isAdmin;

    public CharacterAdapter(@NonNull DiffUtil.ItemCallback<Character> diffCallback) {
        super(diffCallback);
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CharacterViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character current = getItem(position);
        holder.bind(current.toString());
    }

    public static class CharacterDiff extends DiffUtil.ItemCallback<Character> {
        @Override
        public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.equals(newItem);
        }
    }
}

