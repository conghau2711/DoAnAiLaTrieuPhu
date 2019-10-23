package com.nvk.doanailatrieuphu.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nvk.doanailatrieuphu.Database.DBHelper;
import com.nvk.doanailatrieuphu.Model.NguoiChoi;

import java.util.ArrayList;
import java.util.List;

public class NguoiChoiController {
    private DBHelper db;
    private SQLiteDatabase sqLiteDatabase;

    private static final String TABLE_NGUOICHOI = "NguoiChoi";

    private static final String COLUMN_TEN_DANG_NHAP = "ten_dang_nhap";
    private static final String COLUMN_MAT_KHAU = "mat_khau";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CREDIT = "credit";
    private static final String COLUMN_HINH_DAI_DIEN = "hinh_dai_dien";
    private static final String COLUMN_DIEM_CAO_NHAT = "diem_cao_nhat";




    public NguoiChoiController(Context context) {
        db = new DBHelper(context);
    }





    public Boolean CheckUser(String tenTaiKhoan,String matKhau){
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NGUOICHOI + " WHERE "
                        + COLUMN_TEN_DANG_NHAP + " =  '" + tenTaiKhoan + "' AND "
                        + COLUMN_MAT_KHAU      + " =  '" + matKhau +"' "
                ,null);
        boolean result = false;
        if (cursor.getCount() > 0){
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    public Boolean CheckTKAndEmail(String tenTaiKhoan,String email){
        sqLiteDatabase = db.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NGUOICHOI + " WHERE "
                        + COLUMN_TEN_DANG_NHAP + " =  '" + tenTaiKhoan + "' AND "
                        + COLUMN_EMAIL +" = '" + email +"' "
                ,null);
        Boolean result = false;
        if (cursor.getCount() > 0){
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    //err
    public List<NguoiChoi> getAllTK(String tenTaiKhoan){
        sqLiteDatabase = db.getReadableDatabase();
        List<NguoiChoi> nguoiChois = new ArrayList<NguoiChoi>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NGUOICHOI + " WHERE "
                        + COLUMN_TEN_DANG_NHAP + " =  '" + tenTaiKhoan+ " ' "
                ,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                NguoiChoi nguoiChoi = new NguoiChoi();

                String tenDangNhap = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_DANG_NHAP));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_MAT_KHAU));

                nguoiChoi.setTenDangNhap(tenDangNhap);
                nguoiChoi.setEmail(email);
                nguoiChoi.setMatKhau(matKhau);

                nguoiChois.add(nguoiChoi);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return nguoiChois;

    }

    public NguoiChoi getTK(String tenTaiKhoan){
        sqLiteDatabase = db.getReadableDatabase();
        NguoiChoi nguoiChoi = new NguoiChoi();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NGUOICHOI + " WHERE "
                        + COLUMN_TEN_DANG_NHAP + " =  '" + tenTaiKhoan+ "' "
                ,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){


                String tenDangNhap = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_DANG_NHAP));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String matKhau = cursor.getString(cursor.getColumnIndex(COLUMN_MAT_KHAU));
                int credit = cursor.getInt(cursor.getColumnIndex(COLUMN_CREDIT));

                nguoiChoi.setTenDangNhap(tenDangNhap);
                nguoiChoi.setEmail(email);
                nguoiChoi.setMatKhau(matKhau);
                nguoiChoi.setCredit(credit);

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return nguoiChoi;

    }

    public String GetMatKhau(String tenTaiKhoan,String email){
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NGUOICHOI + " WHERE "
                        + COLUMN_TEN_DANG_NHAP + " =  '" + tenTaiKhoan + "' AND "
                        + COLUMN_EMAIL + " = '" + email +"' "
                ,null);
        String matkhau = null;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            matkhau = cursor.getString(cursor.getColumnIndex(COLUMN_MAT_KHAU));
        }
        cursor.close();
        db.close();
        return matkhau;
    }

    public Boolean DangKiUser(NguoiChoi nguoiChoi){
        sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_DANG_NHAP,nguoiChoi.getTenDangNhap());
        contentValues.put(COLUMN_MAT_KHAU,nguoiChoi.getMatKhau());
        contentValues.put(COLUMN_EMAIL,nguoiChoi.getEmail());
        long result = sqLiteDatabase.insert(TABLE_NGUOICHOI,null,contentValues);
        db.close();

        if (result > 0){
            return true;
        }
        return  false;
    }

    public Boolean UpdateUser(NguoiChoi nguoiChoi){
        sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_DANG_NHAP,nguoiChoi.getTenDangNhap());
        contentValues.put(COLUMN_MAT_KHAU,nguoiChoi.getMatKhau());
        contentValues.put(COLUMN_EMAIL,nguoiChoi.getEmail());
        long result = sqLiteDatabase.update(TABLE_NGUOICHOI,contentValues,COLUMN_TEN_DANG_NHAP + " = ? ",new String[]{nguoiChoi.getTenDangNhap()});
        db.close();
        if (result > 0){
            return true;
        }
        return  false;
    }

    public Boolean UpdateGoiCredit(NguoiChoi nguoiChoi){
        sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CREDIT,nguoiChoi.getCredit());
        long result = sqLiteDatabase.update(TABLE_NGUOICHOI,contentValues,COLUMN_TEN_DANG_NHAP + " = ? ",new String[]{nguoiChoi.getTenDangNhap()+""});
        db.close();
        if (result > 0){
            return true;
        }
        return  false;
    }

    public List<NguoiChoi> getBangXepHang(){
        sqLiteDatabase = db.getReadableDatabase();
        List<NguoiChoi> nguoiChois = new ArrayList<>();
        String sql ="SELECT * FROM "+ TABLE_NGUOICHOI +"" +
                " ORDER BY "+ COLUMN_DIEM_CAO_NHAT + " DESC ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String tenDangNhap = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_DANG_NHAP));
                String hinhDaiDien = cursor.getString(cursor.getColumnIndex(COLUMN_HINH_DAI_DIEN));
                int diemCaoNhat = cursor.getInt(cursor.getColumnIndex(COLUMN_DIEM_CAO_NHAT));
                NguoiChoi nguoiChoi = new NguoiChoi();
                nguoiChoi.setTenDangNhap(tenDangNhap);
                nguoiChoi.setHinhDaiDien(hinhDaiDien);
                nguoiChoi.setDiemCaoNhat(diemCaoNhat);
                nguoiChois.add(nguoiChoi);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return nguoiChois;
    }



}
