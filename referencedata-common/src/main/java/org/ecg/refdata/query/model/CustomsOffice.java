package org.ecg.refdata.query.model;

import java.util.Date;

import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeDedicatedTrader;
import java.util.Collection;
import java.util.List;

/**
 * File contains declaration of interface which provides access for customs
 * Office information.
 *
 */
public interface CustomsOffice extends DictionaryItem {

    public static interface CustomsOfficeTimetable extends DictionaryItem {

        /**
         * Get the list of 'CustomsOfficeTimetableLine' element items.
         *
         * @return list
         */
        public List<CustomsOfficeTimetableLine> getCustomsOfficeTimetableLines();

        /**
         * Get the 'seasonCode' attribute value. Season code.
         *
         * @return value
         */
        public String getSeasonCode();

        /**
         * Get the 'seasonName' attribute value. Season name.
         *
         * @return value
         */
        public String getSeasonName();

        /**
         * Get the 'seasonNameLNG' attribute value. Season name - language code.
         *
         * @return value
         */
        public String getSeasonNameLNG();

        /**
         * Get the 'seasonStartDate' attribute value.
         *
         * @return value
         */
        public Date getSeasonStartDate();

        /**
         * Get the 'seasonEndDate' attribute value.
         *
         * @return value
         */
        public Date getSeasonEndDate();

        /**
         * Dedicated trader's data.
         */
        public static interface CustomsOfficeDedicatedTrader extends DictionaryItem {

            /**
             * Get the 'name' attribute value. Name.
             *
             * @return value
             */
            public String getName();

            /**
             * Get the 'tin' attribute value. Trader Identification Number.
             *
             * @return value
             */
            public String getTin();

        }

        /**
         * Customs office - timetable line.
         */
        public static interface CustomsOfficeTimetableLine extends DictionaryItem {

            /**
             * Get the list of 'CustomsOfficeRoleTraffic' element items.
             *
             * @return list
             */
            public List<CustomsOfficeRoleTraffic> getCustomsOfficeRoleTraffics();

            /**
             * Get the 'beginDay' attribute value. Day in the week (begin day).
             *
             * @return value
             */
            public String getBeginDay();

            /**
             * Get the 'hourFrom' attribute value. Opening hours time first
             * period from.
             *
             * @return value
             */
            public String getHourFrom();

            /**
             * Get the 'hourTo' attribute value. Opening hours time first period
             * to.
             *
             * @return value
             */
            public String getHourTo();

            /**
             * Get the 'endDay' attribute value. Day in the week (end day).
             *
             * @return value
             */
            public String getEndDay();

            /**
             * Get the 'secondHourFrom' attribute value. Opening hours time
             * second period from.
             *
             * @return value
             */
            public String getSecondHourFrom();

            /**
             * Get the 'secondHourTo' attribute value. Opening hours time second
             * period to.
             *
             * @return value
             */
            public String getSecondHourTo();

            /**
             * Customs Office - role and traffic competence.
             */
            public static interface CustomsOfficeRoleTraffic extends DictionaryItem {

                /**
                 * Get the 'role' attribute value. The role of customs office.
                 *
                 * @return value
                 */
                public String getRole();

                /**
                 * Get the 'trafficType' attribute value. Traffic type handled
                 * in customs office.
                 *
                 * @return value
                 */
                public String getTrafficType();
            }
        }
    }

    /**
     * Customs office reference number.
     *
     * @return reference number
     */
    String getReferenceNumber();

    /**
     * Reference number of the main customs office.
     *
     * @return reference number of the main office
     */
    String getReferenceNumberMainOffice();

    /**
     * Reference number of the higher authority customs office.
     *
     * @return reference number of the higher authority
     */
    String getReferenceNumberHigherAuthority();

    /**
     * Reference number of the take-over customs office.
     *
     * @return reference number of the take over
     */
    String getReferenceNumberTakeOver();

    /**
     * Reference number of the authority of enquiry customs office.
     *
     * @return reference number of the authority of enquiry
     */
    String getReferenceNumberAuthorityOfEnquiry();

    /**
     * Reference number of the authority of recovery customs office.
     *
     * @return reference number of the authority of recovery
     */
    String getReferenceNumberAuthorityOfRecovery();

    /**
     * Country code.
     *
     * @return country code
     */
    String getCountryCode();

    /**
     * Location code identifier
     *
     * @return UnLocode identifier
     */
    String getUnLocodeId();

    /**
     * Region code.
     *
     * @return region code
     */
    String getRegionCode();

    /**
     * NCTS entry date.
     *
     * @return ncts entry date
     */
    Date getNctsEntryDate();

    /**
     * Nearest office information.
     *
     * @return nearest office information
     */
    String getNearestOffice();

    /**
     * Nearest office information - language code.
     *
     * @return nearest office language code
     */
    String getNearestOfficeLNG();

    /**
     * Postal code.
     *
     * @return postal code
     */
    String getPostalCode();

    /**
     * Phone number.
     *
     * @return phone number
     */
    String getPhoneNumber();

    /**
     * Fax number.
     *
     * @return fax number
     */
    String getFaxNumber();

    /**
     * Telex number.
     *
     * @return telex number
     */
    String getTelexNumber();

    /**
     * Email address.
     *
     * @return email address
     */
    String getEmailAddress();

    /**
     * Geo info code.
     *
     * @return geo info code
     */
    String getGeoInfoCode();

    /**
     * Trader dedicated flag.
     *
     * @return trader dedicated
     */
    Boolean getTraderDedicated();

    /**
     * @return the city
     */
    public String getCity();

    /**
     * @return the prefixSuffixLevel
     */
    public String getPrefixSuffixLevel();

    /**
     * @return the prefixSuffixName
     */
    public String getPrefixSuffixName();

    /**
     * @return the streetAndNumber
     */
    public String getStreetAndNumber();

    /**
     * @return the usualName
     */
    public String getUsualName();

    /**
     * @return the prefixSuffixFlag
     */
    public Boolean getPrefixSuffixFlag();

    /**
     * @return the spaceToAdd
     */
    public Boolean getSpaceToAdd();

    public Collection<String> getSpecificNotesCodes();

    public List<CustomsOfficeTimetable> getCustomsOfficeTimetables();

    public List<CustomsOfficeDedicatedTrader> getCustomsOfficeDedicatedTraders();
}
