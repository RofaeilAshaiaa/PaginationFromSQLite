package rofaeil.ashaiaa.idea.paginationfromsqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 29/10/17.
 */

public class SQLiteItemSource implements Item.ItemSource {

    private SQLiteDatabase database;
    private Cursor _cursor = null;
    private Cursor cursor;

    public SQLiteItemSource(SQLiteDatabase database) {
        this.database = database;

    }

    @Override
    public int getCount() {
        return getCursor().getCount();
    }

    @Override
    public Item getItem(int position) {
        getCursor().moveToPosition(position);
        return getItem(getCursor());
    }

    @Override
    public void close() {
        getCursor().close();
    }

    private Item getItem(Cursor c) {
        return new Item(c.getString(0), c.getString(1));
    }

    public Cursor getCursor() {
        if (_cursor == null || _cursor.isClosed() != false) {
            _cursor = database.rawQuery("SELECT title, content FROM data", null);
        }
        return _cursor;
    }
}
