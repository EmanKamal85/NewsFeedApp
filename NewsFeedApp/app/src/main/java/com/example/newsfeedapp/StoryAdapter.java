package com.example.newsfeedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StoryAdapter extends ArrayAdapter<Story> {
    public StoryAdapter(@NonNull Context context, @NonNull List<Story> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Story currentStory = getItem(position);

        TextView sectionNameTextView = (TextView) listItemView.findViewById(R.id.section_name);
        sectionNameTextView.setText(currentStory.getSectionName());

        TextView webTitleTextView = (TextView) listItemView.findViewById(R.id.web_title);
        webTitleTextView.setText(currentStory.getWebTitle());

        TextView webPublicationDateTextView = (TextView) listItemView.findViewById(R.id.web_publication_date);
        webPublicationDateTextView.setText(currentStory.getWebPublicationDate());
        if (currentStory.getWebPublicationDate().contentEquals(" ")) {
            webPublicationDateTextView.setVisibility(View.GONE);
        }


        TextView authorNameTextView = (TextView) listItemView.findViewById(R.id.author_name);
        authorNameTextView.setText(currentStory.getAuthorName());
        if (currentStory.getAuthorName().contentEquals(" ")) {
            authorNameTextView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
