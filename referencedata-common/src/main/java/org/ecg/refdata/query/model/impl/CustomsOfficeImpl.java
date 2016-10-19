package org.ecg.refdata.query.model.impl;

import java.util.Collection;
import java.util.Date;

import org.ecg.refdata.query.model.CustomsOffice;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeDedicatedTrader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class stores an information about customs office.
 *
 */
public class CustomsOfficeImpl implements CustomsOffice {

    private static final long serialVersionUID = 2189557501260170989L;

    /**
     * Dedicated trader's data.
     */
    public static class CustomsOfficeDedicatedTraderImpl implements CustomsOfficeDedicatedTrader {

        private String name;
        private String tin;
        private String languageCode;
        private static final long serialVersionUID = 1L;

        public CustomsOfficeDedicatedTraderImpl(String name, String tin, String languageCode) {
            this.name = name;
            this.tin = tin;
            this.languageCode = languageCode;
        }

        /**
         * Get the 'name' attribute value. Name.
         *
         * @return value
         */
        public String getName() {
            return name;
        }

        /**
         * Set the 'name' attribute value. Name.
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Get the 'tin' attribute value. Trader Identification Number.
         *
         * @return value
         */
        public String getTin() {
            return tin;
        }

        /**
         * Set the 'tin' attribute value. Trader Identification Number.
         *
         * @param tin
         */
        public void setTin(String tin) {
            this.tin = tin;
        }

        public String getCode() {
            return getTin();
        }

        public String getDescription() {
            return getName();
        }

    }

    /**
     * Customs office - timetable.
     *
     */
    public static class CustomsOfficeTimetableImpl implements CustomsOfficeTimetable {

        private List<CustomsOfficeTimetableLine> customsOfficeTimetableLineList = new ArrayList<CustomsOfficeTimetableLine>();
        private String seasonCode;
        private String seasonName;
        private String seasonNameLNG;
        private Date seasonStartDate;
        private Date seasonEndDate;
        private static final long serialVersionUID = 1L;

        public CustomsOfficeTimetableImpl(String seasonCode, String seasonName, String seasonNameLNG, Date seasonStartDate, Date seasonEndDate, List<CustomsOfficeTimetableLine> customsOfficeTimetableLineList) {
            this.seasonCode = seasonCode;
            this.seasonName = seasonName;
            this.seasonNameLNG = seasonNameLNG;
            this.seasonStartDate = seasonStartDate;
            this.seasonEndDate = seasonEndDate;
            this.customsOfficeTimetableLineList.addAll(customsOfficeTimetableLineList);
        }

        public String getCode() {
            return getSeasonCode();
        }

        public String getDescription() {
            return getSeasonName();
        }

        /**
         * Get the list of 'CustomsOfficeTimetableLine' element items.
         *
         * @return list
         */
        public List<CustomsOfficeTimetableLine> getCustomsOfficeTimetableLines() {
            return customsOfficeTimetableLineList;
        }

        /**
         * Get the 'seasonCode' attribute value. Season code.
         *
         * @return value
         */
        public String getSeasonCode() {
            return seasonCode;
        }

        /**
         * Set the 'seasonCode' attribute value. Season code.
         *
         * @param seasonCode
         */
        public void setSeasonCode(String seasonCode) {
            this.seasonCode = seasonCode;
        }

        /**
         * Get the 'seasonName' attribute value. Season name.
         *
         * @return value
         */
        public String getSeasonName() {
            return seasonName;
        }

        /**
         * Set the 'seasonName' attribute value. Season name.
         *
         * @param seasonName
         */
        public void setSeasonName(String seasonName) {
            this.seasonName = seasonName;
        }

        /**
         * Get the 'seasonNameLNG' attribute value. Season name - language code.
         *
         * @return value
         */
        public String getSeasonNameLNG() {
            return seasonNameLNG;
        }

        /**
         * Set the 'seasonNameLNG' attribute value. Season name - language code.
         *
         * @param seasonNameLNG
         */
        public void setSeasonNameLNG(String seasonNameLNG) {
            this.seasonNameLNG = seasonNameLNG;
        }

        /**
         * Get the 'seasonStartDate' attribute value.
         *
         * @return value
         */
        public Date getSeasonStartDate() {
            return seasonStartDate;
        }

        /**
         * Set the 'seasonStartDate' attribute value.
         *
         * @param seasonStartDate
         */
        public void setSeasonStartDate(Date seasonStartDate) {
            this.seasonStartDate = seasonStartDate;
        }

        /**
         * Get the 'seasonEndDate' attribute value.
         *
         * @return value
         */
        public Date getSeasonEndDate() {
            return seasonEndDate;
        }

        /**
         * Set the 'seasonEndDate' attribute value.
         *
         * @param seasonEndDate
         */
        public void setSeasonEndDate(Date seasonEndDate) {
            this.seasonEndDate = seasonEndDate;
        }

        /**
         * Customs office - timetable line.
         *
         */
        public static class CustomsOfficeTimetableLineImpl implements CustomsOfficeTimetableLine {

            private List<CustomsOfficeRoleTraffic> customsOfficeRoleTrafficList = new ArrayList<CustomsOfficeRoleTraffic>();
            private String beginDay;
            private String hourFrom;
            private String hourTo;
            private String endDay;
            private String secondHourFrom;
            private String secondHourTo;
            private static final long serialVersionUID = 1L;

            public CustomsOfficeTimetableLineImpl(String beginDay, String hourFrom, String hourTo, String endDay,
                    String secondHourFrom, String secondHourTo, List<CustomsOfficeRoleTraffic> customsOfficeRoleTrafficList) {
                this.beginDay = beginDay;
                this.hourFrom = hourFrom;
                this.hourTo = hourTo;
                this.endDay = endDay;
                this.secondHourFrom = secondHourFrom;
                this.secondHourTo = secondHourTo;
                this.customsOfficeRoleTrafficList.addAll(customsOfficeRoleTrafficList);
            }

            public String getCode() {
                return null;
            }

            public String getDescription() {
                return null;
            }

            /**
             * Get the list of 'CustomsOfficeRoleTraffic' element items.
             *
             * @return list
             */
            public List<CustomsOfficeRoleTraffic> getCustomsOfficeRoleTraffics() {
                return customsOfficeRoleTrafficList;
            }

            /**
             * Get the 'beginDay' attribute value. Day in the week (begin day).
             *
             * @return value
             */
            public String getBeginDay() {
                return beginDay;
            }

            /**
             * Set the 'beginDay' attribute value. Day in the week (begin day).
             *
             * @param beginDay
             */
            public void setBeginDay(String beginDay) {
                this.beginDay = beginDay;
            }

            /**
             * Get the 'hourFrom' attribute value. Opening hours time first
             * period from.
             *
             * @return value
             */
            public String getHourFrom() {
                return hourFrom;
            }

            /**
             * Set the 'hourFrom' attribute value. Opening hours time first
             * period from.
             *
             * @param hourFrom
             */
            public void setHourFrom(String hourFrom) {
                this.hourFrom = hourFrom;
            }

            /**
             * Get the 'hourTo' attribute value. Opening hours time first period
             * to.
             *
             * @return value
             */
            public String getHourTo() {
                return hourTo;
            }

            /**
             * Set the 'hourTo' attribute value. Opening hours time first period
             * to.
             *
             * @param hourTo
             */
            public void setHourTo(String hourTo) {
                this.hourTo = hourTo;
            }

            /**
             * Get the 'endDay' attribute value. Day in the week (end day).
             *
             * @return value
             */
            public String getEndDay() {
                return endDay;
            }

            /**
             * Set the 'endDay' attribute value. Day in the week (end day).
             *
             * @param endDay
             */
            public void setEndDay(String endDay) {
                this.endDay = endDay;
            }

            /**
             * Get the 'secondHourFrom' attribute value. Opening hours time
             * second period from.
             *
             * @return value
             */
            public String getSecondHourFrom() {
                return secondHourFrom;
            }

            /**
             * Set the 'secondHourFrom' attribute value. Opening hours time
             * second period from.
             *
             * @param secondHourFrom
             */
            public void setSecondHourFrom(String secondHourFrom) {
                this.secondHourFrom = secondHourFrom;
            }

            /**
             * Get the 'secondHourTo' attribute value. Opening hours time second
             * period to.
             *
             * @return value
             */
            public String getSecondHourTo() {
                return secondHourTo;
            }

            /**
             * Set the 'secondHourTo' attribute value. Opening hours time second
             * period to.
             *
             * @param secondHourTo
             */
            public void setSecondHourTo(String secondHourTo) {
                this.secondHourTo = secondHourTo;
            }

            /**
             * Customs Office - role and traffic competence.
             */
            public static class CustomsOfficeRoleTrafficImpl implements CustomsOfficeRoleTraffic {

                private String role;
                private String trafficType;
                private static final long serialVersionUID = 1L;

                public CustomsOfficeRoleTrafficImpl(String role, String trafficType) {
                    this.role = role;
                    this.trafficType = trafficType;
                }

                /**
                 * Get the 'role' attribute value. The role of customs office.
                 *
                 * @return value
                 */
                public String getRole() {
                    return role;
                }

                /**
                 * Set the 'role' attribute value. The role of customs office.
                 *
                 * @param role
                 */
                public void setRole(String role) {
                    this.role = role;
                }

                /**
                 * Get the 'trafficType' attribute value. Traffic type handled
                 * in customs office.
                 *
                 * @return value
                 */
                public String getTrafficType() {
                    return trafficType;
                }

                /**
                 * Set the 'trafficType' attribute value. Traffic type handled
                 * in customs office.
                 *
                 * @param trafficType
                 */
                public void setTrafficType(String trafficType) {
                    this.trafficType = trafficType;
                }

                public String getCode() {
                    return getRole();
                }

                public String getDescription() {
                    return getTrafficType();
                }
            }
        }
    }

    /**
     * @serial Reference number
     */
    private String referenceNumber;
    /**
     * @serial Reference number main office
     */
    private String referenceNumberMainOffice;
    /**
     * @serial Reference number higher authority
     */
    private String referenceNumberHigherAuthority;
    /**
     * @serial Reference number take over
     */
    private String referenceNumberTakeOver;
    /**
     * @serial Reference number authority of enquiry
     */
    private String referenceNumberAuthorityOfEnquiry;
    /**
     * @serial Reference number authority of recovery
     */
    private String referenceNumberAuthorityOfRecovery;
    /**
     * @serial Country code
     */
    private String countryCode;
    /**
     * @serial UnLocale Id
     */
    private String unLocodeId;
    /**
     * @serial Region code
     */
    private String regionCode;
    /**
     * @serial NCTS entry date
     */
    private Date nctsEntryDate;
    /**
     * @serial Nearest office
     */
    private String nearestOffice;
    /**
     * @serial Nearest office LNG
     */
    private String nearestOfficeLNG;
    /**
     * @serial Postal code
     */
    private String postalCode;
    /**
     * @serial Phone number
     */
    private String phoneNumber;
    /**
     * @serial FAX number
     */
    private String faxNumber;
    /**
     * @serial Telex number
     */
    private String telexNumber;
    /**
     * @serial Email address
     */
    private String emailAddress;
    /**
     * @serial Geo info code
     */
    private String geoInfoCode;
    /**
     * @serial Trader dedicated
     */
    private Boolean traderDedicated;

    /**
     * @serial Trader dedicated
     */
    private String city;

    private String prefixSuffixLevel;

    private String prefixSuffixName;

    private String streetAndNumber;

    private String usualName;

    private Boolean prefixSuffixFlag;

    private Boolean spaceToAdd;

    private List<String> specificNotesCodeList = new ArrayList<String>();
    private List<CustomsOfficeTimetable> customsOfficeTimetableList = new ArrayList<CustomsOfficeTimetable>();
    private List<CustomsOfficeDedicatedTrader> customsOfficeDedicatedTraderList = new ArrayList<CustomsOfficeDedicatedTrader>();

//	/**
//	 * Basic constructor takes couple of following parameters.
//	 *
//	 * @param referenceNumber
//	 *            the reference number
//	 * @param referenceNumberMainOffice
//	 *            the reference number of main office
//	 * @param referenceNumberHigherAuthority
//	 *            the reference number of higher authority
//	 * @param referenceNumberTakeOver
//	 *            the reference number of take over
//	 * @param referenceNumberAuthorityOfEnquiry
//	 *            the reference number of authority of enquiry
//	 * @param referenceNumberAuthorityOfRecovery
//	 *            the reference number of authority of recovery
//	 * @param countryCode
//	 *            the country code
//	 * @param unLocodeId
//	 *            the United Nations code
//	 * @param regionCode
//	 *            the region code
//	 * @param nctsEntryDate
//	 *            the ncts entry code
//	 * @param nearestOffice
//	 *            the nearest office
//	 * @param nearestOfficeLNG
//	 *            the nearest office language
//	 * @param postalCode
//	 *            the postal code
//	 * @param phoneNumber
//	 *            the phone number
//	 * @param faxNumber
//	 *            the fax number
//	 * @param telexNumber
//	 *            the teletex number
//	 * @param emailAddress
//	 *            the email address
//	 * @param geoInfoCode
//	 *            the geo info code
//	 * @param traderDedicated
//	 *            the trader dedicated
//	 */
//	public CustomsOfficeImpl(String referenceNumber,
//			String referenceNumberMainOffice,
//			String referenceNumberHigherAuthority,
//			String referenceNumberTakeOver,
//			String referenceNumberAuthorityOfEnquiry,
//			String referenceNumberAuthorityOfRecovery, String countryCode,
//			String unLocodeId, String regionCode, Date nctsEntryDate,
//			String nearestOffice, String nearestOfficeLNG, String postalCode,
//			String phoneNumber, String faxNumber, String telexNumber,
//			String emailAddress, String geoInfoCode, boolean traderDedicated,
//            List<String> specificNotesCodeList,
//            List<CustomsOfficeTimetable> customsOfficeTimetableList,
//            List<CustomsOfficeDedicatedTrader> customsOfficeDedicatedTraderList) {
//		super();
//		this.referenceNumber = referenceNumber;
//		this.referenceNumberMainOffice = referenceNumberMainOffice;
//		this.referenceNumberHigherAuthority = referenceNumberHigherAuthority;
//		this.referenceNumberTakeOver = referenceNumberTakeOver;
//		this.referenceNumberAuthorityOfEnquiry = referenceNumberAuthorityOfEnquiry;
//		this.referenceNumberAuthorityOfRecovery = referenceNumberAuthorityOfRecovery;
//		this.countryCode = countryCode;
//		this.unLocodeId = unLocodeId;
//		this.regionCode = regionCode;
//		this.nctsEntryDate = nctsEntryDate;
//		this.nearestOffice = nearestOffice;
//		this.nearestOfficeLNG = nearestOfficeLNG;
//		this.postalCode = postalCode;
//		this.phoneNumber = phoneNumber;
//		this.faxNumber = faxNumber;
//		this.telexNumber = telexNumber;
//		this.emailAddress = emailAddress;
//		this.geoInfoCode = geoInfoCode;
//		this.traderDedicated = traderDedicated;
//        this.specificNotesCodeList.addAll(specificNotesCodeList);
//        this.customsOfficeTimetableList.addAll(customsOfficeTimetableList);
//        this.customsOfficeDedicatedTraderList.addAll(customsOfficeDedicatedTraderList);
//	}
    public CustomsOfficeImpl(String referenceNumber, String referenceNumberMainOffice, String referenceNumberHigherAuthority,
            String referenceNumberTakeOver, String referenceNumberAuthorityOfEnquiry, String referenceNumberAuthorityOfRecovery,
            String countryCode, String unLocodeId, String regionCode, Date nctsEntryDate, String nearestOffice, String nearestOfficeLNG,
            String postalCode, String phoneNumber, String faxNumber, String telexNumber, String emailAddress, String geoInfoCode,
            Boolean traderDedicated, String city, String prefixSuffixLevel, String prefixSuffixName, String streetAndNumber,
            String usualName, Boolean prefixSuffixFlag, Boolean spaceToAdd,
            List<String> specificNotesCodeList,
            List<CustomsOfficeTimetable> customsOfficeTimetableList,
            List<CustomsOfficeDedicatedTrader> customsOfficeDedicatedTraderList) {
        this.referenceNumber = referenceNumber;
        this.referenceNumberMainOffice = referenceNumberMainOffice;
        this.referenceNumberHigherAuthority = referenceNumberHigherAuthority;
        this.referenceNumberTakeOver = referenceNumberTakeOver;
        this.referenceNumberAuthorityOfEnquiry = referenceNumberAuthorityOfEnquiry;
        this.referenceNumberAuthorityOfRecovery = referenceNumberAuthorityOfRecovery;
        this.countryCode = countryCode;
        this.unLocodeId = unLocodeId;
        this.regionCode = regionCode;
        this.nctsEntryDate = nctsEntryDate;
        this.nearestOffice = nearestOffice;
        this.nearestOfficeLNG = nearestOfficeLNG;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.telexNumber = telexNumber;
        this.emailAddress = emailAddress;
        this.geoInfoCode = geoInfoCode;
        this.traderDedicated = traderDedicated;
        this.city = city;
        this.prefixSuffixLevel = prefixSuffixLevel;
        this.prefixSuffixName = prefixSuffixName;
        this.streetAndNumber = streetAndNumber;
        this.usualName = usualName;
        this.prefixSuffixFlag = prefixSuffixFlag;
        this.spaceToAdd = spaceToAdd;
        this.specificNotesCodeList.addAll(specificNotesCodeList);
        this.customsOfficeTimetableList.addAll(customsOfficeTimetableList);
        this.customsOfficeDedicatedTraderList.addAll(customsOfficeDedicatedTraderList);
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getReferenceNumber()
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.CustomsOffice#getReferenceNumberMainOffice()
     */
    public String getReferenceNumberMainOffice() {
        return referenceNumberMainOffice;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.CustomsOffice#getReferenceNumberHigherAuthority()
     */
    public String getReferenceNumberHigherAuthority() {
        return referenceNumberHigherAuthority;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.CustomsOffice#getReferenceNumberTakeOver()
     */
    public String getReferenceNumberTakeOver() {
        return referenceNumberTakeOver;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.CustomsOffice#getReferenceNumberAuthorityOfEnquiry()
     */
    public String getReferenceNumberAuthorityOfEnquiry() {
        return referenceNumberAuthorityOfEnquiry;
    }

    /**
     * @see
     * org.ecg.refdata.query.model.CustomsOffice#getReferenceNumberAuthorityOfRecovery()
     */
    public String getReferenceNumberAuthorityOfRecovery() {
        return referenceNumberAuthorityOfRecovery;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getCountryCode()
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getUnLocodeId()
     */
    public String getUnLocodeId() {
        return unLocodeId;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getRegionCode()
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getNctsEntryDate()
     */
    public Date getNctsEntryDate() {
        return nctsEntryDate;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getNearestOffice()
     */
    public String getNearestOffice() {
        return nearestOffice;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getNearestOfficeLNG()
     */
    public String getNearestOfficeLNG() {
        return nearestOfficeLNG;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getPostalCode()
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getPhoneNumber()
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getFaxNumber()
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getTelexNumber()
     */
    public String getTelexNumber() {
        return telexNumber;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getEmailAddress()
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getGeoInfoCode()
     */
    public String getGeoInfoCode() {
        return geoInfoCode;
    }

    /**
     * @see org.ecg.refdata.query.model.CustomsOffice#getTraderDedicated()
     */
    public Boolean getTraderDedicated() {
        return traderDedicated;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the prefixSuffixLevel
     */
    public String getPrefixSuffixLevel() {
        return prefixSuffixLevel;
    }

    /**
     * @return the prefixSuffixName
     */
    public String getPrefixSuffixName() {
        return prefixSuffixName;
    }

    /**
     * @return the streetAndNumber
     */
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    /**
     * @return the usualName
     */
    public String getUsualName() {
        return usualName;
    }

    /**
     * @return the prefixSuffixFlag
     */
    public Boolean getPrefixSuffixFlag() {
        return prefixSuffixFlag;
    }

    /**
     * @return the spaceToAdd
     */
    public Boolean getSpaceToAdd() {
        return spaceToAdd;
    }

    public Collection<String> getSpecificNotesCodes() {
        return specificNotesCodeList;
    }

    public List<CustomsOfficeTimetable> getCustomsOfficeTimetables() {
        return customsOfficeTimetableList;
    }

    public List<CustomsOfficeDedicatedTrader> getCustomsOfficeDedicatedTraders() {
        return customsOfficeDedicatedTraderList;
    }

    public String getCode() {
        return getReferenceNumber();
    }

    public String getDescription() {
        return getUsualName();
    }

}
