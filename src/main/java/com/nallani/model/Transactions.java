package com.nallani.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    private String activityDate;
    private String processDate;
    private String settleDate;
    private String instrument;
    private String description;
    private String transCode;
    private String quantity;
    private String price;
    private String amount;

    @Override
    public String toString() {
        return String.format("%-12s %-10s %-12s %-12s %-45s %-8s %-10s %-8s %-8s",
                activityDate, processDate, settleDate, instrument, description.replace("\n", " "), transCode, quantity, price, amount);
    }
}
