package com.split.reader.adapters;



import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.split.reader.model.Bookmark;
import com.split.reader.reader.R;
import com.split.reader.reader.databinding.BookmarkHeaderBinding;
import com.split.reader.reader.databinding.BookmarkListChildBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ExpandableFavoritesAdapter extends BaseExpandableListAdapter {

    private final List<Bookmark> bookmarkList;
    private onBookmarkClickListener listener;

    public void setListener(onBookmarkClickListener listener) {
        this.listener = listener;
    }

    @Inject
    public ExpandableFavoritesAdapter() {
        this.bookmarkList = new ArrayList<>();
    }

    public void setBookmarkList(List<Bookmark> Bookmark) {
        bookmarkList.clear();
        bookmarkList.addAll(Bookmark);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bookmarkList.size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {

        BookmarkHeaderBinding parentBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.bookmark_header, viewGroup, false);
        view = parentBinding.getRoot();
        parentBinding.setSize(String.valueOf(bookmarkList.size()));
        parentBinding.executePendingBindings();
        view.setTag(parentBinding);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup viewGroup) {

        BookmarkListChildBinding childBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.bookmark_list_child, viewGroup, false);
        view = childBinding.getRoot();
        childBinding.setBookmark(bookmarkList.get(childPosition));
        childBinding.setBookmarkListener(listener);
        childBinding.executePendingBindings();
        childBinding.executePendingBindings();
        view.setTag(childBinding);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public interface onBookmarkClickListener{
        void onClick(Bookmark bookmark);
    }
}
