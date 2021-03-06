package com.example.aishwarya.onlinebookstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.aishwarya.onlinebookstore.AdminCategoriesFragment.other;

/**
 * Created by aishwarya on 11-Apr-17.
 */

public class AdminOtherFragment extends Fragment {

    GridView grid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.other_fragment, container, false);
        AdminHomeActivity.title.setText("Other");

        grid = (GridView) mainView.findViewById(R.id.xCategoryGridView5);

        ArrayList<String> bookName = new ArrayList<>();
        ArrayList<String> authorName = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> bookImagePath = new ArrayList<>();

        for (int i = 0; i< other.size(); i++){
            bookName.add(other.get(i).getBookName());
            authorName.add(other.get(i).getAuthorName());
            price.add(other.get(i).getPrice());
            bookImagePath.add(other.get(i).getBookImagePath());
        }
        grid.setAdapter(new AdminOtherFragment.gridAdapter(getContext(),bookName,authorName,price,bookImagePath));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdminHomeActivity.book_id = other.get(i).getBookId();
                AdminHomeActivity.book_name=other.get(i).getBookName();
                AdminHomeActivity.author_name=other.get(i).getAuthorName();
                AdminHomeActivity.description=other.get(i).getDescription();
                AdminHomeActivity.price=other.get(i).getPrice();
                AdminHomeActivity.imagePath=other.get(i).getBookImagePath();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.xUserFrame,new AdminViewBookFragment()).addToBackStack(null).commit();
            }
        });

        return mainView;
    }

    class DashboardItem {
        String dashboardIconName;
        String authorName;
        String price;
        DashboardItem(String dashboardIconName,String authorName,String price){
            this.dashboardIconName=dashboardIconName;
            this.authorName=authorName;
            this.price=price;
        }
    }

    class gridAdapter extends BaseAdapter {
        ArrayList<AdminOtherFragment.DashboardItem> list;
        Context context;
        ArrayList<String> bookName = new ArrayList<>();
        ArrayList<String> authorName = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> imagePath = new ArrayList<>();

        gridAdapter(Context context, ArrayList<String> bookName, ArrayList<String> authorName, ArrayList<String> price, ArrayList<String> imagePath) {
            this.context = context;
            this.bookName = bookName;
            this.authorName = authorName;
            this.price = price;
            this.imagePath = imagePath;

            list = new ArrayList<AdminOtherFragment.DashboardItem>();

            for (int i = 0; i < bookName.size() ; i++) {
                AdminOtherFragment.DashboardItem tempDashboardItem = new AdminOtherFragment.DashboardItem(
                        bookName.get(i),
                        authorName.get(i),price.get(i));
                list.add(tempDashboardItem);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        //this one is used for the databases related activities
        public long getItemId(int i) {
            return i;
        }

        class ViewHolder {
            ImageView myDashboardIcon;
            TextView myDashboardIconName,myAuthorName,myPrice;
            RelativeLayout relative;

            ViewHolder(View v) {
                myDashboardIcon = (ImageView) v.findViewById(R.id.singleItem);
                myDashboardIconName=(TextView)v.findViewById(R.id.singleItemName);
                myAuthorName = (TextView)v.findViewById(R.id.xAuthorName);
                myPrice = (TextView)v.findViewById(R.id.xPrice);
                relative=(RelativeLayout)v.findViewById(R.id.xRelative);
            }
        }

        AdminOtherFragment.gridAdapter.ViewHolder holder;
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View row = view;
            holder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_item, viewGroup, false);
                holder = new AdminOtherFragment.gridAdapter.ViewHolder(row);
                row.setTag(holder);
            } else {
                holder = (AdminOtherFragment.gridAdapter.ViewHolder) row.getTag();
            }
            AdminOtherFragment.DashboardItem temp = list.get(i);

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imagePath.get(i)).getContent());
                        holder.myDashboardIcon.setImageBitmap(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            holder.myDashboardIconName.setText(temp.dashboardIconName);
            holder.myAuthorName.setText(temp.authorName);
            holder.myPrice.setText("Rs. "+temp.price);
            return row;
        }
    }

}


