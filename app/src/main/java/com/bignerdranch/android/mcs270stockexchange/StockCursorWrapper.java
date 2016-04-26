package com.bignerdranch.android.mcs270stockexchange;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class StockCursorWrapper extends CursorWrapper{

    public StockCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Stock getStock() {
        String uuidString = getString(getColumnIndex(StockDbSchema.StockTable.Cols.UUID));
        String ticker = getString(getColumnIndex(StockDbSchema.StockTable.Cols.TICKER));

        Stock stock = new Stock(UUID.fromString(uuidString));

        return stock;
    }
}
