package com.example.project02.characterViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.R;
import com.example.project02.database.entities.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characterList;

    public interface OnCharacterClickListener {
        void onCharacterClick(Character character);
    }

    private final OnCharacterClickListener characterClickListener;

    public CharacterAdapter(List<Character> characterList, OnCharacterClickListener characterClickListener) {
        this.characterList = characterList;
        this.characterClickListener = characterClickListener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_recycler_item, parent, false);
        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character currentCharacter = characterList.get(position);
        holder.characterNameTextView.setText(currentCharacter.toString());

        holder.itemView.setOnClickListener(v -> {
            if (characterClickListener != null) {
                characterClickListener.onCharacterClick(currentCharacter);
            }
        });

    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }



    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final TextView characterNameTextView;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterRecyclerItemTextView);
        }
    }
}
