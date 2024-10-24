package com.example.testsqlitenormal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.testsqlitenormal.Models.Contact;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Gọi khi cơ sở dữ liệu được tạo lần đầu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contact.CREATE_TABLE);
    }

    // Gọi khi phiên bản cơ sở dữ liệu được nâng cấp
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
        onCreate(db);
    }

    // Thêm một contact vào cơ sở dữ liệu
    public void insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_EMAIL, contact.getEmail());
        db.insert(Contact.TABLE_NAME, null, values);
        db.close();
    }

    // Lấy tất cả contact từ cơ sở dữ liệu
    // Lấy tất cả contact từ cơ sở dữ liệu
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>(); // Danh sách lưu trữ contact
        SQLiteDatabase db = this.getReadableDatabase(); // Mở cơ sở dữ liệu với quyền đọc
        Cursor cursor = db.query(Contact.TABLE_NAME, null, null, null, null, null, null); // Truy vấn tất cả dữ liệu

        if (cursor != null) { // Kiểm tra nếu cursor không null
            try {
                while (cursor.moveToNext()) { // Duyệt qua tất cả các hàng trong cursor
                    int idIndex = cursor.getColumnIndex(Contact.COLUMN_ID); // Lấy chỉ số cột ID
                    int nameIndex = cursor.getColumnIndex(Contact.COLUMN_NAME); // Lấy chỉ số cột tên
                    int emailIndex = cursor.getColumnIndex(Contact.COLUMN_EMAIL); // Lấy chỉ số cột email

                    // Kiểm tra nếu các chỉ số cột hợp lệ
                    if (idIndex != -1 && nameIndex != -1 && emailIndex != -1) {
                        int id = cursor.getInt(idIndex); // Lấy giá trị ID
                        String name = cursor.getString(nameIndex); // Lấy giá trị tên
                        String email = cursor.getString(emailIndex); // Lấy giá trị email
                        contacts.add(new Contact(name, email, id)); // Tạo đối tượng Contact và thêm vào danh sách
                    }
                }
            } finally {
                cursor.close(); // Đảm bảo đóng cursor sau khi sử dụng
            }
        }
        db.close(); // Đóng cơ sở dữ liệu
        return contacts; // Trả về danh sách contact
    }

    // Cập nhật thông tin của một contact
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu với quyền ghi
        ContentValues values = new ContentValues(); // Tạo ContentValues để lưu trữ giá trị
        values.put(Contact.COLUMN_NAME, contact.getName()); // Cập nhật tên contact
        values.put(Contact.COLUMN_EMAIL, contact.getEmail()); // Cập nhật email contact
        // Cập nhật contact trong bảng dựa trên ID
        db.update(Contact.TABLE_NAME, values, Contact.COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close(); // Đóng cơ sở dữ liệu
    }

    // Xóa một contact khỏi cơ sở dữ liệu
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase(); // Mở cơ sở dữ liệu với quyền ghi
        // Xóa contact dựa trên ID
        db.delete(Contact.TABLE_NAME, Contact.COLUMN_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close(); // Đóng cơ sở dữ liệu
    }
}
