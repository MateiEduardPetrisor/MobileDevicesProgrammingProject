package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "db.db";
    private static final String TABLE_NAME_USERS = "USERS";
    private static final String USERS_COLUMN_ID = "U_ID";
    private static final String USERS_COLUMN_USERNAME = "U_USER";
    private static final String USERS_COLUMN_PASSWORD = "U_PASSWORD";
    private static final String TABLE_NAME_REVIEWS = "REVIEWS";
    private static final String REVIEWS_COLUMN_ID = "R_ID";
    private static final String REVIEWS_COLUMN_USERNAME = "R_USER";
    private static final String REVIEWS_COLUMN_TEXT = "R_TEXT";
    private static final String REVIEWS_COLUMN_RATING = "R_RATING";
    private static final String REVIEWS_COLUMN_PLACENAME = "R_PLACENAME";
    private static final String TABLE_CREATE_USERS = "CREATE TABLE " + TABLE_NAME_USERS
            + "("
            + USERS_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + USERS_COLUMN_USERNAME + " TEXT NOT NULL,"
            + USERS_COLUMN_PASSWORD + " TEXT NOT NULL"
            + ");";
    private static final String TABLE_CREATE_REVIEWS = "CREATE TABLE " + TABLE_NAME_REVIEWS
            + "("
            + REVIEWS_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + REVIEWS_COLUMN_USERNAME + " TEXT NOT NULL,"
            + REVIEWS_COLUMN_TEXT + " TEXT NOT NULL,"
            + REVIEWS_COLUMN_RATING + " INTEGER NOT NULL,"
            + REVIEWS_COLUMN_PLACENAME + " TEXT NOT NULL"
            + ");";
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_REVIEWS);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        ArrayList<User> lstUsers = new ArrayList<User>();
        ArrayList<Review> lstReviews = new ArrayList<Review>();
        String deleteTable1 = "DROP TABLE IF EXISTS " + TABLE_NAME_USERS + ";";
        String deleteTable2 = "DROP TABLE IF EXISTS " + TABLE_NAME_REVIEWS + ";";
        String querryTable1 = "SELECT * FROM " + TABLE_NAME_USERS + ";";
        String querryTable2 = "SELECT * FROM " + TABLE_NAME_REVIEWS + ";";
        Cursor cursorUsers = db.rawQuery(querryTable1, null);
        cursorUsers.moveToFirst();
        do {
            User u = new User();
            String uName = cursorUsers.getString(cursorUsers.getColumnIndex(USERS_COLUMN_USERNAME));
            String pUser = cursorUsers.getString(cursorUsers.getColumnIndex(USERS_COLUMN_PASSWORD));
            u.setUserName(uName);
            u.setPassword(pUser);
            lstUsers.add(u);
        }
        while (cursorUsers.moveToNext());
        cursorUsers.close();

        Cursor cursorReviews = db.rawQuery(querryTable2, null);
        cursorReviews.moveToFirst();
        do {
            Review r = new Review();
            String rUser = cursorReviews.getString(cursorReviews.getColumnIndex(REVIEWS_COLUMN_USERNAME));
            String rText = cursorReviews.getString(cursorReviews.getColumnIndex(REVIEWS_COLUMN_TEXT));
            String rPlaceName = cursorReviews.getString(cursorReviews.getColumnIndex(REVIEWS_COLUMN_PLACENAME));
            int rRating = cursorReviews.getInt(cursorReviews.getColumnIndex(REVIEWS_COLUMN_RATING));
            r.setPlaceName(rPlaceName);
            r.setUsername(rUser);
            r.setRating(rRating);
            r.setReviewText(rText);
            lstReviews.add(r);
        }
        while (cursorReviews.moveToNext());
        cursorReviews.close();
        db.execSQL(deleteTable1);
        db.execSQL(deleteTable2);
        this.onCreate(db);

        for (User user : lstUsers) {
            Cursor cursorTmp = db.rawQuery(querryTable1, null);
            int id = cursorTmp.getCount();
            cursorTmp.close();
            ContentValues cvUsers = new ContentValues();
            cvUsers.put(USERS_COLUMN_ID, id);
            cvUsers.put(USERS_COLUMN_USERNAME, user.getUserName());
            cvUsers.put(USERS_COLUMN_PASSWORD, user.getPassword());
            db.insert(TABLE_NAME_USERS, null, cvUsers);
        }

        for (Review review : lstReviews) {
            Cursor cursorTmp = db.rawQuery(querryTable2, null);
            int id = cursorTmp.getCount();
            cursorTmp.close();
            ContentValues cvReviews = new ContentValues();
            cvReviews.put(REVIEWS_COLUMN_ID, id);
            cvReviews.put(REVIEWS_COLUMN_USERNAME, review.getUsername());
            cvReviews.put(REVIEWS_COLUMN_TEXT, review.getReviewText());
            cvReviews.put(REVIEWS_COLUMN_RATING, review.getRating());
            cvReviews.put(REVIEWS_COLUMN_PLACENAME, review.getPlaceName());
            db.insert(TABLE_NAME_REVIEWS, null, cvReviews);
        }
    }

    public void insertUser(User u) throws Exception {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME_USERS + ";";
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        cv.put(USERS_COLUMN_ID, count);
        cv.put(USERS_COLUMN_USERNAME, u.getUserName());
        cv.put(USERS_COLUMN_PASSWORD, u.getPassword());
        db.insert(TABLE_NAME_USERS, null, cv);
        db.close();
    }

    public void checkUser(User u) throws Exception {
        db = this.getReadableDatabase();
        String querry = "SELECT " + USERS_COLUMN_USERNAME + " FROM " + TABLE_NAME_USERS + ";";
        Cursor c = db.rawQuery(querry, null);
        String getUser;
        if (c.moveToFirst()) {
            do {
                getUser = c.getString(0);
                if (u.getUserName().equals(getUser)) {
                    throw new Exception("User Exists");
                }
            }
            while (c.moveToNext());
        }
    }

    public String searchPassword(String username) throws Exception {
        db = this.getReadableDatabase();
        String querry = "SELECT " + USERS_COLUMN_USERNAME + "," + USERS_COLUMN_PASSWORD + " FROM " + TABLE_NAME_USERS + ";";
        Cursor c = db.rawQuery(querry, null);
        String a, b;
        b = "Not Found";
        if (c.moveToFirst()) {
            do {
                {
                    a = c.getString(0);
                    if (a.equals(username)) {
                        b = c.getString(1);
                        break;
                    }
                }
            }
            while (c.moveToNext());
        }
        return b;
    }

    public void writeReview(Review r) throws Exception {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM " + TABLE_NAME_REVIEWS + ";";
        Cursor c = db.rawQuery(query, null);
        int count = c.getCount();
        cv.put(REVIEWS_COLUMN_ID, count);
        cv.put(REVIEWS_COLUMN_USERNAME, r.getUsername());
        cv.put(REVIEWS_COLUMN_TEXT, r.getReviewText());
        cv.put(REVIEWS_COLUMN_RATING, r.getRating());
        cv.put(REVIEWS_COLUMN_PLACENAME, r.getPlaceName());
        db.insert(TABLE_NAME_REVIEWS, null, cv);
        db.close();
    }


    public ArrayList<Review> getReviewsFromDatabase(String CONDITION) throws Exception {
        ArrayList<Review> reviews = new ArrayList<Review>();
        db = this.getReadableDatabase();
        String querry = "SELECT * FROM " + TABLE_NAME_REVIEWS + " WHERE " + REVIEWS_COLUMN_PLACENAME + "=" + "'" + CONDITION + "'" + ";";
        Cursor cursor = db.rawQuery(querry, null);
        cursor.moveToFirst();
        do {
            Review r = new Review();
            String rUser = cursor.getString(cursor.getColumnIndex(REVIEWS_COLUMN_USERNAME));
            String rText = cursor.getString(cursor.getColumnIndex(REVIEWS_COLUMN_TEXT));
            int rRating = cursor.getInt(cursor.getColumnIndex(REVIEWS_COLUMN_RATING));
            String rPlaceName = cursor.getString(cursor.getColumnIndex(REVIEWS_COLUMN_PLACENAME));
            r.setUsername(rUser);
            r.setReviewText(rText);
            r.setRating(rRating);
            r.setPlaceName(rPlaceName);
            reviews.add(r);
        }
        while (cursor.moveToNext());
        cursor.close();
        db.close();
        return reviews;
    }
}