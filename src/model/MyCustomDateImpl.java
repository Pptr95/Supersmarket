package model;

import java.time.LocalDate;

/** 
 * Basic implementation of MyCustomDate, using java.time.LocalDate class.
 */
public class MyCustomDateImpl implements MyCustomDate {

    private LocalDate date;

    /**
     * Basic constructor.
     * @param year 0 - 9999
     * @param month 1 - 12
     * @param day 1 - 31
     */
    public MyCustomDateImpl(final int year, final int month, final int day) {
        this.date = LocalDate.of(year, month, day);
    }

    private MyCustomDateImpl(final LocalDate now) {
        this(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    @Override
    public int getDifferenceInDays(final MyCustomDate other) {
        //TODO: take care of leap years
        final int daysOfThis = this.date.getYear() * 365 + this.date.getDayOfYear();
        final int daysOfOther = other.getYear() * 365 + other.getDayOfYear();
        return daysOfThis - daysOfOther;
    }


    @Override
    public int getYear() {
        return this.date.getYear();
    }

    @Override
    public int getDayOfYear() {
        return this.date.getDayOfYear();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MyCustomDateImpl.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final MyCustomDateImpl o = (MyCustomDateImpl) obj;
        return (this.getDifferenceInDays(o) == 0);
    }

    @Override
    public String toString() {
        return String.valueOf(this.date);
    }

    /**
     * Return the current date.
     * @return A MyCustomDate object representing today's date
     */
    public static MyCustomDate today() {
        return new MyCustomDateImpl(LocalDate.now());
    }

}