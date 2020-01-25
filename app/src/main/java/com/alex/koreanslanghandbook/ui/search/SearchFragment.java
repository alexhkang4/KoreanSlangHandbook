package com.alex.koreanslanghandbook.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.koreanslanghandbook.R;
import com.alex.koreanslanghandbook.data.DataCache;
import com.alex.koreanslanghandbook.data.Parser;
import com.alex.koreanslanghandbook.data.Vocab;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private SearchAdapter adapter;
    private List<Vocab> vocabList;
    DataCache dataCache;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        dataCache = DataCache.getInstance();
        setUpList();
        setUpRecyclerView(view);
        return view;
    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new SearchAdapter(vocabList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpList() {
        if (dataCache.getAllArrayList() == null) {
            new Parser(getContext());
        }
        vocabList = dataCache.getAllArrayList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setFocusable(false);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
        private List<Vocab> vocabList;
        private List<Vocab> vocabListFull;

        class SearchViewHolder extends RecyclerView.ViewHolder {
            ImageView voice;
            TextView korean;
            TextView roman;
            TextView definition;

            SearchViewHolder(View itemView) {
                super(itemView);
                voice = itemView.findViewById(R.id.voice_button);
                korean = itemView.findViewById(R.id.vocab_korean);
                roman = itemView.findViewById(R.id.vocab_roman);
                definition = itemView.findViewById(R.id.vocab_definition);
            }
        }

        SearchAdapter(List<Vocab> vocabList) {
            this.vocabList = vocabList;
            vocabListFull = new ArrayList<>(vocabList);
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_view,
                    parent, false);
            return new SearchViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            Vocab currentItem = vocabList.get(position);
            holder.korean.setText(currentItem.getKorean());
            holder.roman.setText(currentItem.getRoman());
            holder.definition.setText(currentItem.getDefinition());
        }


        @Override
        public int getItemCount() {
            return vocabList.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Vocab> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(vocabListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Vocab vocab : vocabListFull) {
                        if (vocab.getDefinition().toLowerCase().contains(filterPattern) ||
                                vocab.getKorean().contains(filterPattern) ||
                                vocab.getRoman().toLowerCase().contains(filterPattern)) {
                            filteredList.add(vocab);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                vocabList.clear();
                vocabList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }
}