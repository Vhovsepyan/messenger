package com.chat.inomma.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chat.inomma.AppConstants;
import com.chat.inomma.R;
import com.chat.inomma.entity.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vahe on 12/26/2017.
 */

public class UserAdapter extends BaseAdapter {
    List<User> users;
    private LayoutInflater mInflater;
    private String path;
    Context context;

    public UserAdapter( Activity activity) {
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.users = new ArrayList<>(20);
        path = activity.getApplicationContext().getFilesDir() + "/" + AppConstants.AVATAR_DIR;
        context = activity;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        UserViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_item_layout, null);
            holder = new UserViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UserViewHolder)convertView.getTag();
        }

        holder.displayName.setText(users.get(i).getDisplayName());


/*        Bitmap bitmap = imageLoader.loadBitmap(path + "/" + users.get(i).getUid());
        holder.avatar.setImageBitmap(bitmap);*/
        String fullPath = path  + "/" + users.get(i).getUid();
        File file = new File(fullPath);
        if (file.exists()){
            Picasso.with(context).
                    load(file).
                    resize(50,50).
                    centerCrop().into(holder.avatar);
        }
        return convertView;
    }

    private class UserViewHolder{
        ImageView avatar;
        TextView displayName;
        public UserViewHolder(View convertView) {
            displayName = (TextView)convertView.findViewById(R.id.user_display_name);
            avatar = convertView.findViewById(R.id.avatar_imageview);

        }
    }
    public void addItem(User user){
        users.add(user);
        notifyDataSetChanged();
    }
}
