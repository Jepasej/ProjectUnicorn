package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class SwimmingPool
{
    final int PRICE_CHILD = 30;
    final int PRICE_ADULT = 60;
    final int PRICE_PENSIONER = 40;
    final int ADULT_AGE = 12;
    final int PENSIONER_AGE = 65;

    public int ticketPrice(LocalDate dob, LocalDate today)
    {
        //Period period;

        //period = Period.between(today,dob);
        int yearDiff = Period.between(dob, today).getYears();
        if(yearDiff < 0)
        {
            return 0;
        }


        if(yearDiff < ADULT_AGE)
        {
            return PRICE_CHILD;
        }

        if(yearDiff < PENSIONER_AGE)
        {
            return PRICE_ADULT;
        }

        return PRICE_PENSIONER;
    }
}
