package com.example.studysmartsap200;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
// https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/ Användt för att kunna implementer adapter för att skapa en gridview.
public class GridCategoryForumAdapter extends BaseAdapter {
    ArrayList<Category> categoryTitle;
    Context context;

public void onCreate(){

}
    public GridCategoryForumAdapter(Context context, ArrayList<Category> categoryTitle){
        this.context = context;
        this.categoryTitle = categoryTitle;
    }
    @Override
    public int getCount() {
        return categoryTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.cardview_kategory_layout, null);
        }
        TextView categoryTxtView = view.findViewById(R.id.textViewForumCategoryCardview);
        categoryTxtView.setText(categoryTitle.get(position).getCategoryName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            // Ger tillbaka en kategori beroende på vilken kategori som klickas och skickar över datan till nästa aktivitet.
            public void onClick(View v) {
                switch(position)
                {
                    case 0: {
                        Toast.makeText(context.getApplicationContext(), "Matte", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ThreadForumActivty.class);
                        String category = "math";
                        intent.putExtra("category", category);
                        context.startActivity(intent);
                        break;
                    }
                    case 1: {
                        Toast.makeText(context.getApplicationContext(), "Svenska", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ThreadForumActivty.class);
                        String category = "Svenska";
                        intent.putExtra("category", category);
                        context.startActivity(intent);
                        break;
                    }
                    case 2: {
                        Toast.makeText(context.getApplicationContext(), "Programmering", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ThreadForumActivty.class);
                        String category = "Programmering";
                        intent.putExtra("category", category);
                        context.startActivity(intent);
                        break;
                    }
                    case 3:
                        Toast.makeText(context.getApplicationContext(), "Fysik", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context.getApplicationContext(), "Engelska", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(context.getApplicationContext(), "Samhällskunskap", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(context.getApplicationContext(), "Geografi", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(context.getApplicationContext(), "Naturkunskap", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return view;
    }
}
