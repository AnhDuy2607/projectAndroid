package com.example.csgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdapterCustom extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<ThongTinViPham> lst;

    public AdapterCustom(Context context, int layout, ArrayList<ThongTinViPham> lst) {
        inflater= LayoutInflater.from(context);
        this.context = context;
        this.layout = layout;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return lst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Holder
    {
        TextView txtGPLX,txtNoiDung,txtSoLan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder hd=new Holder();
        if(view==null)
        {
            view=inflater.inflate(R.layout.layout_listitem,viewGroup,false);
            hd.txtNoiDung=view.findViewById(R.id.txtNoidungVP);
            hd.txtSoLan=view.findViewById(R.id.txtsolanVP);
            hd.txtGPLX=view.findViewById(R.id.txtGPLX);
            view.setTag(hd);
        }
        else
        {
            hd=(Holder)view.getTag();
        }
        hd.txtNoiDung.setText(lst.get(i).getNoidung());
        hd.txtSoLan.setText(lst.get(i).getSolan()+"");
        hd.txtGPLX.setText(lst.get(i).getGplx());
        return view;
    }
}
