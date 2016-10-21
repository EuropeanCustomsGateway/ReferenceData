package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.CountryHoliday;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class stores an information about country holiday.
 *
 */
public class CountryHolidayImpl implements CountryHoliday {

    public static class HolidayImpl implements Holiday {

        private static final long serialVersionUID = -1540355694218465937L;

        private String day;
        private String month;
        private Integer year;
        private Boolean variable;
        private String name;

        public String getDay() {
            return day;
        }

        public String getMonth() {
            return month;
        }

        public Integer getYear() {
            return year;
        }

        public Boolean getVariable() {
            return variable;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return getName();
        }

        public String getDescription() {
            return null;
        }

        public HolidayImpl(String day, String month, Integer year, Boolean variable, String name) {
            this.day = day;
            this.month = month;
            this.year = year;
            this.variable = variable;
            this.name = name;
        }

    }

    private static final long serialVersionUID = -1540355694218465937L;
    /**
     * @serial Country code
     */
    private String countryCode;

    /**
     * Basic constructor takes one parameter
     *
     * @param countryCode the country code
     */
    public CountryHolidayImpl(String countryCode, Collection<Holiday> holidays) {

        this.countryCode = countryCode;
        this.holidays.addAll(holidays);
    }

    /**
     * @see org.ecg.refdata.query.model.CountryHoliday#getCountryCode()
     */
    public String getCountryCode() {
        return countryCode;
    }

    List<Holiday> holidays = new ArrayList<Holiday>();

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public String getCode() {
        return null;
    }

    public String getDescription() {
        return null;
    }
}
