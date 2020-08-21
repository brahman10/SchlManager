package com.example.schlmanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class DoubtAdapter extends ArrayAdapter<Doubt> {

    private Activity context;
    private List<Doubt> UploadList;

    public DoubtAdapter(Activity context , List<Doubt> UploadList){

        super(context , R.layout.list_doubt,UploadList);
        this.context = context;
        this.UploadList = UploadList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //LayoutInflater layoutInflater = context.getLayoutInflater();
        //View listViewItem = layoutInflater.inflate(R.layout.list_layout,null,true);
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_doubt,parent,false);
        }
        TextView ques = (TextView)convertView.findViewById(R.id.text_doubt);
        TextView post = (TextView)convertView.findViewById(R.id.textPost);

        Doubt doubt = (Doubt) this.getItem(position);

        ques.setText(doubt.getQuestion());
        post.setText("Posted By : "+doubt.getPost());
        //dop.setText(doubt.getDop());


        return convertView;
    }

    @Override
    public int getCount() {
        return UploadList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public Doubt getItem(int position) {
        return UploadList.get(position);
    }


}
