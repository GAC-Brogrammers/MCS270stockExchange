package com.bignerdranch.android.mcs270stockexchange;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nbens_000 on 4/19/2016.
 */
public class StockCursorWrapper extends CursorWrapper{

    public StockCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Stock getStock() {
        String uuidString = getString(getColumnIndex(StockDbSchema.StockTable.Cols.UUID));
        String ticker = getString(getColumnIndex(StockDbSchema.StockTable.Cols.TICKER));

        Stock stock = new Stock(UUID.fromString(uuidString), ticker);

        return stock;
    }
}
