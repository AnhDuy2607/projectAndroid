package com.example.csgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DataContext extends SQLiteOpenHelper {
    public DataContext(@Nullable Context context) {
        super(context, "ViPhamGT.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists Account(id Integer primary key autoincrement,username nvarchar(50) not null, password nvarchar(50) not null)";
        String sql1="create table if not exists ThongTinViPham(id Integer primary key autoincrement," +
                "GPLX nvarchar(50) not null, " +
                "CMND nvarchar(50) not null," +
                "SoLan int not null," +
                "NoiDung nvarchar(20000) not null" +
                ")";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void TaoAcc(Account acc)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues ctv=new ContentValues();
        ctv.put("username",acc.getUsername());
        ctv.put("password",acc.getPassword());
        db.insert("Account",null,ctv);
        db.close();
    }

    public int CheckLogin(Account acc)
    {
        SQLiteDatabase db=getReadableDatabase();
        String sql="select * from Account where username='"+acc.getUsername()+"' and password='"+acc.getPassword()+"'";
        Cursor cs=db.rawQuery(sql,null);
        return cs.getCount();
    }

    public int AddViPham(ThongTinViPham tt)
    {
        SQLiteDatabase db=getWritableDatabase();
        SQLiteDatabase dbRead=getReadableDatabase();
        int checkThongTin=CheckGPLXTontai(tt.getGplx(),tt.getCmnd());
        if(checkThongTin==1)
        {
            return  -3;
        }
        Cursor cs=dbRead.rawQuery("select * from ThongTinViPham where GPLX='"+tt.getGplx()+"' and CMND='"+tt.getCmnd()+"'",null);
        int count=cs.getCount();
        if(cs.getCount()==0)
        {
            if(tt.getSolan()>1)
            {
                return -2;
            }
            ContentValues ctv=new ContentValues();
            ctv.put("GPLX",tt.getGplx());
            ctv.put("CMND",tt.getCmnd());
            ctv.put("SoLan",tt.getSolan());
            ctv.put("NoiDung",tt.getNoidung());
            db.insert("ThongTinViPham",null,ctv);
            return -1;
        }
        else if(count==1)
        {
            int id=-1;
            int solan=-1;
            String noidung=null;
            while (cs.moveToNext())
            {
                id=cs.getInt(0);
                solan=cs.getInt(3);
                noidung=cs.getString(4);
            }
            if(tt.getSolan()==solan+1)
            {
                ContentValues ctv=new ContentValues();
                ctv.put("SoLan",solan+1);
                ctv.put("NoiDung",noidung+" - "+tt.getNoidung());
                db.update("ThongTinViPham",ctv,"id="+id,null);
                return -1;
            }
            return solan;
        }
        db.close();
        dbRead.close();
        return 0;
    }

    public ArrayList<ThongTinViPham> GetAll()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cs=db.rawQuery("select * from ThongTinViPham order by id desc",null);
        ArrayList<ThongTinViPham> lst=new ArrayList<>();
        while(cs.moveToNext())
        {
            lst.add(new ThongTinViPham(cs.getInt(0),cs.getString(1),cs.getString(2),cs.getInt(3),cs.getString(4)));
        }
        return lst;
    }

    public ArrayList<ThongTinViPham> TimKiemThongTin(String str)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cs=db.rawQuery("select * from ThongTinViPham where GPLX='"+str+"'", null);
        ArrayList<ThongTinViPham> lst=new ArrayList<>();
        while (cs.moveToNext())
        {
            lst.add(new ThongTinViPham(cs.getInt(0),cs.getString(1),cs.getString(2),cs.getInt(3),cs.getString(4)));
        }
        return  lst;
    }

    private int CheckGPLXTontai(String gplx,String cmnd)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cs1=db.rawQuery("select * from ThongTinViPham",null);
        if(cs1.getCount()==0)
        {
            return -1;
        }
        while (cs1.moveToNext())
        {
            if((cs1.getString(1).equals(gplx) && !cs1.getString(2).equals(cmnd)) ||(!cs1.getString(1).equals(gplx) && cs1.getString(2).equals(cmnd)))
            {
                return 1;
            }
        }
        return  0;
    }
}
