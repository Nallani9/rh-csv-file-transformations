package com.nallani;

import com.nallani.model.Transactions;
import com.nallani.service.RobinhoodCSVReader;

import java.util.List;

import static com.nallani.util.TransactionUtil.*;

public class App
{
    public static void main( String[] args ) {
        RobinhoodCSVReader reader = new RobinhoodCSVReader();
        List<Transactions> transactions = reader.readCSV();

        groupByTransCode(transactions);

    }
}
