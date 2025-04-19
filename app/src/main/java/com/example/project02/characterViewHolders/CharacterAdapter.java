package com.example.project02.characterViewHolders;

import android.annotation.SuppressLint;
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

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
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
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCharacterList(List<Character> characters) {
        this.characterList = characters;
        notifyDataSetChanged();
    }


    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final TextView characterNameTextView;

        public CharacterViewHolder(View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterRecyclerItemTextView);
        }
    }
}
