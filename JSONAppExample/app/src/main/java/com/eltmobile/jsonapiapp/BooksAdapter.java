package com.eltmobile.jsonapiapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//binding data to view using a Recycler view
public class BooksAdapter extends  RecyclerView.Adapter<BooksAdapter.BookViewHOlder>{

     ArrayList<BookModelClass> bookModelClasses;
    public BooksAdapter(ArrayList<BookModelClass> bookModelClasses){
        this.bookModelClasses = bookModelClasses;
    }
    @NonNull
    @Override
    public BookViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list, parent, false);
        return new BookViewHOlder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull BookViewHOlder holder, int position) {
        BookModelClass bookModelClass = bookModelClasses.get(position);
        holder.bind(bookModelClass);
    }
    @Override
    public int getItemCount() {
        return bookModelClasses.size();

    }
    public class BookViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mAuthors;
        TextView mDate;
        TextView mPublishers;

    public BookViewHOlder(@NonNull View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.book_name);
            mAuthors = (TextView) itemView.findViewById(R.id.book_author);
            mDate = (TextView) itemView.findViewById(R.id.pulishing_date);
            mPublishers = (TextView) itemView.findViewById(R.id.book_publisher);
            itemView.setOnClickListener(this);
      }

      public void  bind (BookModelClass books){
             mTitle.setText(books.title);
             mAuthors.setText(books.authors);
             mPublishers.setText(books.publisher);
             mDate.setText(books.publishedDate);
      }

        @Override
        public void onClick(View view) {
        int position = getAdapterPosition();
        BookModelClass selectedBookModelClass = bookModelClasses.get(position);
            Intent intent = new Intent(view.getContext(), BookDetails.class);
            intent.putExtra("BookModelClass", selectedBookModelClass);
            view.getContext().startActivity(intent);

        }
    }
}
