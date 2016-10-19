package org.ecg.referencedata.utils;


import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import org.ecg.refdata.datasource.entities.codeDescription.CodeDescriptionItem;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;
import org.ecg.refdata.datasource.entities.comodityCode.CommodityCodeDataType;
import org.ecg.refdata.datasource.entities.comodityCode.CommodityCodeItem;
import org.ecg.refdata.datasource.entities.correlation.CorrelationDataType;
import org.ecg.refdata.datasource.entities.correlation.CorrelationItem;
import org.ecg.refdata.datasource.entities.country.CountryDataType;
import org.ecg.refdata.datasource.entities.country.CountryItem;
import org.ecg.refdata.datasource.entities.countryHoliday.CountryHolidayDataType;
import org.ecg.refdata.datasource.entities.countryHoliday.CountryHolidayItem;
import org.ecg.refdata.datasource.entities.countryHoliday.HolidayItem;
import org.ecg.refdata.datasource.entities.countryHoliday.HolidayLSDItem;
import org.ecg.refdata.datasource.entities.countryRegion.CountryRegionDataType;
import org.ecg.refdata.datasource.entities.countryRegion.CountryRegionItem;
import org.ecg.refdata.datasource.entities.countryRegion.CountryRegionLSDItem;
import org.ecg.refdata.datasource.entities.countryUnavailability.CountryUnavailabilityDataType;
import org.ecg.refdata.datasource.entities.countryUnavailability.CountryUnavailabilityItem;
import org.ecg.refdata.datasource.entities.countryUnavailability.SystemUnavailabilityItem;
import org.ecg.refdata.datasource.entities.customsOffice.*;
import org.ecg.refdata.datasource.entities.documentType.DocumentTypeDataType;
import org.ecg.refdata.datasource.entities.documentType.DocumentTypeItem;
import org.ecg.refdata.datasource.entities.exchangeRate.ExchangeRateDataType;
import org.ecg.refdata.datasource.entities.exchangeRate.ExchangeRateItem;
import org.ecg.refdata.datasource.entities.localClearanceAuthorization.LocalClearanceAuthorizationDataType;
import org.ecg.refdata.datasource.entities.localClearanceAuthorization.LocalClearanceAuthorizationItem;
import org.ecg.refdata.datasource.entities.location.LocationDataType;
import org.ecg.refdata.datasource.entities.location.LocationItem;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemDataType;
import org.ecg.refdata.datasource.entities.simpleItem.SimpleItemItem;
import org.ecg.refdata.datasource.entities.unLocode.UnLocodeDataType;
import org.ecg.refdata.datasource.entities.unLocode.UnLocodeItem;
import org.ecg.refdata.datasource.entities.valueAdjustment.ValueAdjustmentDataType;
import org.ecg.refdata.datasource.entities.valueAdjustment.ValueAdjustmentItem;
import org.ecg.refdata.datasource.xml.ReferenceData;
import org.ecg.refdata.datasource.xml.ReferenceData.*;
import org.ecg.refdata.datasource.xml.ReferenceData.CountryUnavailability.SystemUnavailability;
import org.ecg.refdata.datasource.xml.XMLFileDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Utility to import xml Entities into Pertsistance entities of reference data.
 */
public class XmlToEntities {
    private static Log log = LogFactory.getLog(XmlToEntities.class);

    /**
     * helps to convert XMLGregorianCalendar to Data. Deals with null
     * value returning null Date.
     *
     * @param xCalendar
     * @return
     */
    private static Date convertXMLDate(XMLGregorianCalendar xCalendar) {
        if (xCalendar == null)
            return null;
        else
            return xCalendar.toGregorianCalendar().getTime();
    }

    /**
     * helps to convert big decimal to Integer. If BigDecimal is null
     * returns null
     *
     * @param bd
     * @return
     */
    private static Integer convertXMLBigDecimal(BigDecimal bd) {
        if (bd == null)
            return null;
        else
            return new Integer(bd.intValue());
    }

    /**
     * creates reference data type Persistent Entity from file containing valid
     * xml reference data
     *
     * @param f
     * @return
     * @throws java.lang.Exception
     */
    public static ReferenceDataAbstractDataType create(File f) throws Exception {
        ReferenceData xmlReferenceData = XMLFileDataSource.getReferenceDataFromFile(f);
        return create(xmlReferenceData);
    }

    /**
     * converts ReferenceDataType ( xml entities ) to  ReferenceDataAbstractDataType
     * ( persistent entities)
     *
     * @param referenceDataType
     * @return
     * @throws java.lang.Exception
     */
    public static ReferenceDataAbstractDataType create(ReferenceData referenceDataItem) throws Exception {
        ReferenceDataAbstractDataType referenceDataAbstractDataType = null;

        if (!referenceDataItem.getSimpleItem().isEmpty()) {
            referenceDataAbstractDataType = new SimpleItemDataType();
            for (SimpleItem simpleItem : referenceDataItem.getSimpleItem()) {
                SimpleItemItem simpleItemItem = new SimpleItemItem(referenceDataAbstractDataType);
                simpleItemItem.setCode(simpleItem.getCode());
                simpleItemItem.setNational(simpleItem.isNational());
                simpleItemItem.setValidFrom(convertXMLDate(simpleItem.getValidFrom()));
                simpleItemItem.setValidTo(convertXMLDate(simpleItem.getValidTo()));
                for (SimpleItem.CodeDescription codeDescription : simpleItem.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem();
                    codeDescriptionItem.setDescription(codeDescription.getDescription());
                    codeDescriptionItem.setLanguageCode(codeDescription.getLanguageCode());
                    simpleItemItem.addCodeDescriptionItems(codeDescriptionItem);
                    codeDescriptionItem.setSimpleItemItem(simpleItemItem);

                }
                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(simpleItemItem);

            }
        } else if (!referenceDataItem.getUNLOCODE().isEmpty()) {
            referenceDataAbstractDataType = new UnLocodeDataType();
            //referenceDataType - code
            for (UNLOCODE uNLOCODE : referenceDataItem.getUNLOCODE()) {
                UnLocodeItem unLocodeItem = new UnLocodeItem(referenceDataAbstractDataType);
                // uN-LOCODE - atributes
                unLocodeItem.setCountryCode(uNLOCODE.getCountryCode()); // countryCode (required)
                unLocodeItem.setUnLocodeId(uNLOCODE.getUnLocodeId()); // unLocodeId (required)
                unLocodeItem.setUnLocodeName(uNLOCODE.getUnLocodeName()); // unLocodeName (required)
                unLocodeItem.setValidFrom(convertXMLDate(uNLOCODE.getValidFrom())); // validFrom (optional)
                unLocodeItem.setValidTo(convertXMLDate(uNLOCODE.getValidTo())); // validTo (optional)


                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(unLocodeItem);
            }
        } else if (!referenceDataItem.getCountryHoliday().isEmpty()) {
            referenceDataAbstractDataType = new CountryHolidayDataType();
            for (CountryHoliday countryHoliday : referenceDataItem.getCountryHoliday()) {
                CountryHolidayItem countryHolidayItem = new CountryHolidayItem(referenceDataAbstractDataType);
                // countryHoliday - atributes
                countryHolidayItem.setCountryCode(countryHoliday.getCountryCode()); // countryCode (required)
                countryHolidayItem.setValidFrom(convertXMLDate(countryHoliday.getValidFrom())); // validFrom (optional)
                countryHolidayItem.setValidTo(convertXMLDate(countryHoliday.getValidTo())); // validTo (optional)

                //countryHoliday - code
                for (CountryHoliday.Holiday holiday : countryHoliday.getHoliday()) {
                    HolidayItem holidayItem = new HolidayItem();
                    // holiday - atributes
                    holidayItem.setVariable(holiday.isVariable()); // variable (required)
                    holidayItem.setDay(holiday.getDay()); // day (required)
                    holidayItem.setMonth(holiday.getMonth()); // month (required)
                    holidayItem.setYear(convertXMLBigDecimal(holiday.getYear())); // year (optional)

                    //holiday - code
                    for (CountryHoliday.Holiday.HolidayLSD holidayLSD : holiday.getHolidayLSD()) {
                        HolidayLSDItem holidayLSDItem = new HolidayLSDItem();
                        // holidayLSD - atributes
                        holidayLSDItem.setLanguageCode(holidayLSD.getLanguageCode()); // languageCode (required)
                        holidayLSDItem.setName(holidayLSD.getName()); // name (required)


                        holidayItem.addHolidayLSDItems(holidayLSDItem);
                        holidayLSDItem.setHolidayItem(holidayItem);
                    }

                    countryHolidayItem.addHolidayItems(holidayItem);
                    holidayItem.setCountryHolidayItem(countryHolidayItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(countryHolidayItem);
            }
        } else if (!referenceDataItem.getCustomsOffice().isEmpty()) {
            referenceDataAbstractDataType = new CustomsOfficeDataType();
            for (CustomsOffice customsOffice : referenceDataItem.getCustomsOffice()) {
                CustomsOfficeItem customsOfficeItem = new CustomsOfficeItem(referenceDataAbstractDataType);
                // customsOffice - atributes
                customsOfficeItem.setReferenceNumber(customsOffice.getReferenceNumber()); // referenceNumber (required)
                customsOfficeItem.setCountryCode(customsOffice.getCountryCode()); // countryCode (required)
                customsOfficeItem.setPostalCode(customsOffice.getPostalCode()); // postalCode (required)
                customsOfficeItem.setTraderDedicated(customsOffice.isTraderDedicated()); // traderDedicated (required)
                customsOfficeItem.setReferenceNumberMainOffice(customsOffice.getReferenceNumberMainOffice()); // referenceNumberMainOffice (optional)
                customsOfficeItem.setReferenceNumberHigherAuthority(customsOffice.getReferenceNumberHigherAuthority()); // referenceNumberHigherAuthority (optional)
                customsOfficeItem.setReferenceNumberTakeOver(customsOffice.getReferenceNumberTakeOver()); // referenceNumberTakeOver (optional)
                customsOfficeItem.setReferenceNumberAuthorityOfEnquiry(customsOffice.getReferenceNumberAuthorityOfEnquiry()); // referenceNumberAuthorityOfEnquiry (optional)
                customsOfficeItem.setReferenceNumberAuthorityOfRecovery(customsOffice.getReferenceNumberAuthorityOfRecovery()); // referenceNumberAuthorityOfRecovery (optional)
                customsOfficeItem.setUnLocodeId(customsOffice.getUnLocodeId()); // unLocodeId (optional)
                customsOfficeItem.setRegionCode(customsOffice.getRegionCode()); // regionCode (optional)
                customsOfficeItem.setNctsEntryDate(convertXMLDate(customsOffice.getNctsEntryDate())); // nctsEntryDate (optional)
                customsOfficeItem.setNearestOffice(customsOffice.getNearestOffice()); // nearestOffice (optional)
                customsOfficeItem.setNearestOfficeLNG(customsOffice.getNearestOfficeLNG()); // nearestOfficeLNG (optional)
                customsOfficeItem.setPhoneNumber(customsOffice.getPhoneNumber()); // phoneNumber (optional)
                customsOfficeItem.setFaxNumber(customsOffice.getFaxNumber()); // faxNumber (optional)
                customsOfficeItem.setTelexNumber(customsOffice.getTelexNumber()); // telexNumber (optional)
                customsOfficeItem.setEmailAddress(customsOffice.getEmailAddress()); // emailAddress (optional)
                customsOfficeItem.setGeoInfoCode(customsOffice.getGeoInfoCode()); // geoInfoCode (optional)
                customsOfficeItem.setValidFrom(convertXMLDate(customsOffice.getValidFrom())); // validFrom (optional)
                customsOfficeItem.setValidTo(convertXMLDate(customsOffice.getValidTo())); // validTo (optional)

                //customsOffice - code
                for (CustomsOffice.CustomsOfficeLSD customsOfficeLSD : customsOffice.getCustomsOfficeLSD()) {
                    CustomsOfficeLSDItem customsOfficeLSDItem = new CustomsOfficeLSDItem();
                    // customsOfficeLSD - atributes
                    customsOfficeLSDItem.setLanguageCode(customsOfficeLSD.getLanguageCode()); // languageCode (required)
                    customsOfficeLSDItem.setUsualName(customsOfficeLSD.getUsualName()); // usualName (required)
                    customsOfficeLSDItem.setStreetAndNumber(customsOfficeLSD.getStreetAndNumber()); // streetAndNumber (required)
                    customsOfficeLSDItem.setCity(customsOfficeLSD.getCity()); // city (required)
                    customsOfficeLSDItem.setPrefixSuffixFlag(customsOfficeLSD.isPrefixSuffixFlag()); // prefixSuffixFlag (required)
                    customsOfficeLSDItem.setSpaceToAdd(customsOfficeLSD.isSpaceToAdd()); // spaceToAdd (required)
                    customsOfficeLSDItem.setPrefixSuffixLevel(customsOfficeLSD.getPrefixSuffixLevel()); // prefixSuffixLevel (optional)
                    customsOfficeLSDItem.setPrefixSuffixName(customsOfficeLSD.getPrefixSuffixName()); // prefixSuffixName (optional)


                    customsOfficeItem.addCustomsOfficeLSDItem(customsOfficeLSDItem);
                    customsOfficeLSDItem.setCustomsOfficeItem(customsOfficeItem);
                }
                for (CustomsOffice.CustomsOfficeDedicatedTrader customsOfficeDedicatedTrader : customsOffice.getCustomsOfficeDedicatedTrader()) {
                    CustomsOfficeDedicatedTraderItem customsOfficeDedicatedTraderItem = new CustomsOfficeDedicatedTraderItem();
                    // customsOfficeDedicatedTrader - atributes
                    customsOfficeDedicatedTraderItem.setName(customsOfficeDedicatedTrader.getName()); // name (required)
                    customsOfficeDedicatedTraderItem.setLanguageCode(customsOfficeDedicatedTrader.getLanguageCode()); // languageCode (required)
                    customsOfficeDedicatedTraderItem.setTin(customsOfficeDedicatedTrader.getTin()); // tin (optional)


                    customsOfficeItem.addCustomsOfficeDedicatedTraderItem(customsOfficeDedicatedTraderItem);
                    customsOfficeDedicatedTraderItem.setCustomsOfficeItem(customsOfficeItem);
                }
                for (CustomsOffice.CustomsOfficeSpecificNotes customsOfficeSpecificNotes : customsOffice.getCustomsOfficeSpecificNotes()) {
                    CustomsOfficeSpecificNotesItem customsOfficeSpecificNotesItem = new CustomsOfficeSpecificNotesItem();
                    // customsOfficeSpecificNotes - atributes
                    customsOfficeSpecificNotesItem.setSpecificNotesCode(customsOfficeSpecificNotes.getSpecificNotesCode()); // specificNotesCode (required)


                    customsOfficeItem.addCustomsOfficeSpecificNotesItem(customsOfficeSpecificNotesItem);
                    customsOfficeSpecificNotesItem.setCustomsOfficeItem(customsOfficeItem);
                }
                for (CustomsOffice.CustomsOfficeTimetable customsOfficeTimetable : customsOffice.getCustomsOfficeTimetable()) {
                    CustomsOfficeTimetableItem customsOfficeTimetableItem = new CustomsOfficeTimetableItem();
                    // customsOfficeTimetable - atributes
                    customsOfficeTimetableItem.setSeasonCode(customsOfficeTimetable.getSeasonCode()); // seasonCode (required)
                    customsOfficeTimetableItem.setSeasonStartDate(convertXMLDate(customsOfficeTimetable.getSeasonStartDate())); // seasonStartDate (required)
                    customsOfficeTimetableItem.setSeasonEndDate(convertXMLDate(customsOfficeTimetable.getSeasonEndDate())); // seasonEndDate (required)
                    customsOfficeTimetableItem.setSeasonName(customsOfficeTimetable.getSeasonName()); // seasonName (optional)
                    customsOfficeTimetableItem.setSeasonNameLNG(customsOfficeTimetable.getSeasonNameLNG()); // seasonNameLNG (optional)

                    //customsOfficeTimetable - code
                    for (CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine customsOfficeTimetableLine : customsOfficeTimetable.getCustomsOfficeTimetableLine()) {
                        CustomsOfficeTimetableLineItem customsOfficeTimetableLineItem = new CustomsOfficeTimetableLineItem();
                        // customsOfficeTimetableLine - atributes
                        customsOfficeTimetableLineItem.setBeginDay(customsOfficeTimetableLine.getBeginDay()); // beginDay (required)
                        customsOfficeTimetableLineItem.setHourFrom(customsOfficeTimetableLine.getHourFrom()); // hourFrom (required)
                        customsOfficeTimetableLineItem.setHourTo(customsOfficeTimetableLine.getHourTo()); // hourTo (required)
                        customsOfficeTimetableLineItem.setEndDay(customsOfficeTimetableLine.getEndDay()); // endDay (required)
                        customsOfficeTimetableLineItem.setSecondHourFrom(customsOfficeTimetableLine.getSecondHourFrom()); // secondHourFrom (optional)
                        customsOfficeTimetableLineItem.setSecondHourTo(customsOfficeTimetableLine.getSecondHourTo()); // secondHourTo (optional)

                        //customsOfficeTimetableLine - code
                        for (CustomsOffice.CustomsOfficeTimetable.CustomsOfficeTimetableLine.CustomsOfficeRoleTraffic customsOfficeRoleTraffic : customsOfficeTimetableLine.getCustomsOfficeRoleTraffic()) {
                            CustomsOfficeRoleTrafficItem customsOfficeRoleTrafficItem = new CustomsOfficeRoleTrafficItem();
                            // customsOfficeRoleTraffic - atributes
                            customsOfficeRoleTrafficItem.setRole(customsOfficeRoleTraffic.getRole()); // role (required)
                            customsOfficeRoleTrafficItem.setTrafficType(customsOfficeRoleTraffic.getTrafficType()); // trafficType (required)


                            customsOfficeTimetableLineItem.addCustomsOfficeRoleTrafficItem(customsOfficeRoleTrafficItem);
                            customsOfficeRoleTrafficItem.setCustomsOfficeTimetableLineItem(customsOfficeTimetableLineItem);
                        }

                        customsOfficeTimetableItem.addCustomsOfficeTimetableLineItem(customsOfficeTimetableLineItem);
                        customsOfficeTimetableLineItem.setCustomsOfficeTimetableItem(customsOfficeTimetableItem);
                    }

                    customsOfficeItem.addCustomsOfficeTimetableItem(customsOfficeTimetableItem);
                    customsOfficeTimetableItem.setCustomsOfficeItem(customsOfficeItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(customsOfficeItem);
            }
        } else if (!referenceDataItem.getCorrelation().isEmpty()) {
            referenceDataAbstractDataType = new CorrelationDataType();
            for (Correlation correlation : referenceDataItem.getCorrelation()) {
                CorrelationItem correlationItem = new CorrelationItem(referenceDataAbstractDataType);
                // correlation - atributes
                correlationItem.setCode1(correlation.getCode1()); // code1 (required)
                correlationItem.setCode2(correlation.getCode2()); // code2 (required)
                correlationItem.setCode3(correlation.getCode3()); // code3 (required)
                correlationItem.setCode4(correlation.getCode4()); // code4 (required)
                correlationItem.setValidFrom(convertXMLDate(correlation.getValidFrom())); // validFrom (optional)
                correlationItem.setValidTo(convertXMLDate(correlation.getValidTo())); // validTo (optional)


                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(correlationItem);
            }
        } else if (!referenceDataItem.getExchangeRate().isEmpty()) {
            referenceDataAbstractDataType = new ExchangeRateDataType();
            for (ExchangeRate exchangeRate : referenceDataItem.getExchangeRate()) {
                ExchangeRateItem exchangeRateItem = new ExchangeRateItem(referenceDataAbstractDataType);
                // exchangeRate - atributes
                exchangeRateItem.setCurrency(exchangeRate.getCurrency()); // currency (required)
                exchangeRateItem.setMultiplier(convertXMLBigDecimal(exchangeRate.getMultiplier())); // multiplier (required)
                exchangeRateItem.setRateEUR(exchangeRate.getRateEUR()); // rate (required)
                exchangeRateItem.setRateLTL(exchangeRate.getRateLTL()); // rate (required)
                exchangeRateItem.setValidFrom(convertXMLDate(exchangeRate.getValidFrom())); // validFrom (required)
                exchangeRateItem.setValidTo(convertXMLDate(exchangeRate.getValidTo())); // validTo (optional)


                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(exchangeRateItem);
            }
        } else if (!referenceDataItem.getLocation().isEmpty()) {
            referenceDataAbstractDataType = new LocationDataType();
            for (Location location : referenceDataItem.getLocation()) {
                LocationItem locationItem = new LocationItem(referenceDataAbstractDataType);
                // location - atributes
                locationItem.setReferenceNumber(location.getReferenceNumber()); // referenceNumber (required)
                locationItem.setName(location.getName()); // name (required)
                locationItem.setStreetAndNumber(location.getStreetAndNumber()); // streetAndNumber (required)
                locationItem.setPostalCode(location.getPostalCode()); // postalCode (required)
                locationItem.setCity(location.getCity()); // city (required)
                locationItem.setCountryCode(location.getCountryCode()); // countryCode (required)
                locationItem.setValidFrom(convertXMLDate(location.getValidFrom())); // validFrom (optional)
                locationItem.setValidTo(convertXMLDate(location.getValidTo())); // validTo (optional)


                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(locationItem);
            }
        } else if (!referenceDataItem.getDocumentType().isEmpty()) {
            referenceDataAbstractDataType = new DocumentTypeDataType();
            for (DocumentType documentType : referenceDataItem.getDocumentType()) {
                DocumentTypeItem documentTypeItem = new DocumentTypeItem(referenceDataAbstractDataType);
                // documentType - atributes
                documentTypeItem.setDocumentType(documentType.getDocumentType()); // documentType (required)
                documentTypeItem.setTransportDocument(documentType.isTransportDocument()); // transportDocument (required)
                documentTypeItem.setNational(documentType.isNational()); // national (optional)
                documentTypeItem.setValidFrom(convertXMLDate(documentType.getValidFrom())); // validFrom (optional)
                documentTypeItem.setValidTo(convertXMLDate(documentType.getValidTo())); // validTo (optional)

                //documentType - code
                for (DocumentType.CodeDescription codeDescription : documentType.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem();
                    // codeDescription - atributes
                    codeDescriptionItem.setDescription(codeDescription.getDescription()); // description (required)
                    codeDescriptionItem.setLanguageCode(codeDescription.getLanguageCode()); // languageCode (required)


                    documentTypeItem.addCodeDescriptionItem(codeDescriptionItem);
                    codeDescriptionItem.setDocumentTypeItem(documentTypeItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(documentTypeItem);
            }
        } else if (!referenceDataItem.getCountry().isEmpty()) {
            referenceDataAbstractDataType = new CountryDataType();
            for (Country country : referenceDataItem.getCountry()) {
                CountryItem countryItem = new CountryItem(referenceDataAbstractDataType);
                // country - atributes
                countryItem.setCountryCode(country.getCountryCode()); // countryCode (required)
                countryItem.setTccEntryDate(convertXMLDate(country.getTccEntryDate())); // tccEntryDate (required)
                countryItem.setNctsEntryDate(convertXMLDate(country.getNctsEntryDate())); // nctsEntryDate (required)
                countryItem.setCountryRegimeCode(country.getCountryRegimeCode()); // countryRegimeCode (required)
                countryItem.setGeoNomenclatureCode(country.getGeoNomenclatureCode()); // geoNomenclatureCode (optional)
                countryItem.setValidFrom(convertXMLDate(country.getValidFrom())); // validFrom (optional)
                countryItem.setValidTo(convertXMLDate(country.getValidTo())); // validTo (optional)

                //country - code
                for (Country.CodeDescription codeDescription : country.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem();
                    // codeDescription - atributes
                    codeDescriptionItem.setDescription(codeDescription.getDescription()); // description (required)
                    codeDescriptionItem.setLanguageCode(codeDescription.getLanguageCode()); // languageCode (required)


                    countryItem.addCodeDescriptionItem(codeDescriptionItem);
                    codeDescriptionItem.setCountryItem(countryItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(countryItem);
            }
        } else if (!referenceDataItem.getValueAdjustement().isEmpty()) {
            referenceDataAbstractDataType = new ValueAdjustmentDataType();
            for (ValueAdjustement valueAdjustement : referenceDataItem.getValueAdjustement()) {
                ValueAdjustmentItem valueAdjustmentItem = new ValueAdjustmentItem(referenceDataAbstractDataType);
                // country - atributes
                valueAdjustmentItem.setAdditionalCosts(valueAdjustement.getAdditionalCosts());
                valueAdjustmentItem.setCode(valueAdjustement.getCode());
                valueAdjustmentItem.setValidFrom(convertXMLDate(valueAdjustement.getValidFrom()));
                valueAdjustmentItem.setValidTo(convertXMLDate(valueAdjustement.getValidTo()));

                for (ValueAdjustement.CodeDescription codeDescription : valueAdjustement.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem();
                    // codeDescription - atributes
                    codeDescriptionItem.setDescription(codeDescription.getDescription()); // description (required)
                    codeDescriptionItem.setLanguageCode(codeDescription.getLanguageCode()); // languageCode (required)


                    valueAdjustmentItem.addCodeDescriptionItem(codeDescriptionItem);
                    codeDescriptionItem.setValueAdjustmentItem(valueAdjustmentItem);
                }
                if (valueAdjustement.getApportionment() != null) {
                    ValueAdjustement.Apportionment apportionment = valueAdjustement.getApportionment();
//                            ApportionmentItem apportionmentItem = new ApportionmentItem();

                    valueAdjustmentItem.setApportionment(apportionment.getApportionment());
                    valueAdjustmentItem.setApportionmentMode(apportionment.getApportionmentMode());
//                            apportionmentItem.setValueAdjustmentItem(valueAdjustmentItem);
//                            valueAdjustmentItem.setApportionmentItem(apportionmentItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(valueAdjustmentItem);
            }
        } else if (!referenceDataItem.getCommodityCode().isEmpty()) {
            referenceDataAbstractDataType = new CommodityCodeDataType();
            for (CommodityCode commodityCode : referenceDataItem.getCommodityCode()) {

                CommodityCodeItem commodityCodeItem = new CommodityCodeItem(referenceDataAbstractDataType);
                // country - atributes
                commodityCodeItem.setCode(commodityCode.getCode());
                commodityCodeItem.setExcise(commodityCode.isExcise());
                commodityCodeItem.setExportFlag(commodityCode.isExport());
                commodityCodeItem.setImportFlag(commodityCode.isImport());
                commodityCodeItem.setValidFrom(convertXMLDate(commodityCode.getValidFrom()));
                commodityCodeItem.setValidTo(convertXMLDate(commodityCode.getValidTo()));


                for (CommodityCode.CodeDescription codeDescription : commodityCode.getCodeDescription()) {
                    CodeDescriptionItem codeDescriptionItem = new CodeDescriptionItem();
                    // codeDescription - atributes
                    codeDescriptionItem.setDescription(codeDescription.getDescription()); // description (required)
                    codeDescriptionItem.setLanguageCode(codeDescription.getLanguageCode()); // languageCode (required)

                    commodityCodeItem.addCodeDescriptionItem(codeDescriptionItem);
                    codeDescriptionItem.setCommodityCodeItem(commodityCodeItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(commodityCodeItem);
            }
        } else if (!referenceDataItem.getCountryRegion().isEmpty()) {
            referenceDataAbstractDataType = new CountryRegionDataType();
            for (CountryRegion countryRegion : referenceDataItem.getCountryRegion()) {
                CountryRegionItem countryRegionItem = new CountryRegionItem(referenceDataAbstractDataType);
                // countryRegion - atributes
                countryRegionItem.setCountryCode(countryRegion.getCountryCode()); // countryCode (required)
                countryRegionItem.setCountryRegionCode(countryRegion.getCountryRegionCode()); // countryRegionCode (required)
                countryRegionItem.setValidFrom(convertXMLDate(countryRegion.getValidFrom())); // validFrom (optional)
                countryRegionItem.setValidTo(convertXMLDate(countryRegion.getValidTo())); // validTo (optional)

                //countryRegion - code
                for (CountryRegion.Holiday holiday : countryRegion.getHoliday()) {
                    HolidayItem holidayItem = new HolidayItem();
                    // holiday - atributes
                    holidayItem.setVariable(holiday.isVariable()); // variable (required)
                    holidayItem.setDay(holiday.getDay()); // day (required)
                    holidayItem.setMonth(holiday.getMonth()); // month (required)
                    holidayItem.setYear(convertXMLBigDecimal(holiday.getYear())); // year (optional)

                    //holiday - code
                    for (CountryRegion.Holiday.HolidayLSD holidayLSD : holiday.getHolidayLSD()) {
                        HolidayLSDItem holidayLSDItem = new HolidayLSDItem();
                        // holidayLSD - atributes
                        holidayLSDItem.setLanguageCode(holidayLSD.getLanguageCode()); // languageCode (required)
                        holidayLSDItem.setName(holidayLSD.getName()); // name (required)


                        holidayItem.addHolidayLSDItems(holidayLSDItem);
                        holidayLSDItem.setHolidayItem(holidayItem);
                    }

                    countryRegionItem.addHolidayItems(holidayItem);
                    holidayItem.setCountryRegionItem(countryRegionItem);
                }
                for (CountryRegion.CountryRegionLSD countryRegionLSD : countryRegion.getCountryRegionLSD()) {
                    CountryRegionLSDItem countryRegionLSDItem = new CountryRegionLSDItem();
                    // countryRegionLSD - atributes
                    countryRegionLSDItem.setLanguageCode(countryRegionLSD.getLanguageCode()); // languageCode (required)
                    countryRegionLSDItem.setCountryRegionName(countryRegionLSD.getCountryRegionName()); // countryRegionName (required)


                    countryRegionItem.addCountryRegionLSDItems(countryRegionLSDItem);
                    countryRegionLSDItem.setCountryRegionItem(countryRegionItem);
                }

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(countryRegionItem);
            }
        } else if (!referenceDataItem.getCountryUnavailability().isEmpty()) {
            referenceDataAbstractDataType = new CountryUnavailabilityDataType();
            for (CountryUnavailability countryUnavailability : referenceDataItem.getCountryUnavailability()) {
                CountryUnavailabilityItem countryUnavailabilityItem = new CountryUnavailabilityItem(referenceDataAbstractDataType);
                countryUnavailabilityItem.setCountryCode(countryUnavailability.getCountryCode());

                for (SystemUnavailability systemUnavailability : countryUnavailability.getSystemUnavailability()) {
                    SystemUnavailabilityItem systemUnavailabilityItem = new SystemUnavailabilityItem();
                    systemUnavailabilityItem.setBusinessFunctionality(systemUnavailability.getBusinessFunctionality());
                    systemUnavailabilityItem.setDowntimeFrom(systemUnavailability.getDowntimeFrom());
                    systemUnavailabilityItem.setDowntimeTo(systemUnavailability.getDowntimeTo());
                    systemUnavailabilityItem.setExplanation(systemUnavailability.getExplanation());
                    systemUnavailabilityItem.setExplanationLNG(systemUnavailability.getExplanationLNG());
                    systemUnavailabilityItem.setSystemUnavailabilityType(systemUnavailability.getSystemUnavailabilityType());

                    systemUnavailabilityItem.setCountryUnavailabilityItem(countryUnavailabilityItem);
                    countryUnavailabilityItem.addSystemUnavailabilityItem(systemUnavailabilityItem);
                }
                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(countryUnavailabilityItem);
            }
        } else if (!referenceDataItem.getLocalClearanceAuthorization().isEmpty()) {
            referenceDataAbstractDataType = new LocalClearanceAuthorizationDataType();
            for (LocalClearanceAuthorization localClearanceAuthorization : referenceDataItem.getLocalClearanceAuthorization()) {
                LocalClearanceAuthorizationItem localClearanceAuthorizationItem = new LocalClearanceAuthorizationItem(referenceDataAbstractDataType);

                localClearanceAuthorizationItem.setGoodsReleaseTimeLimit(localClearanceAuthorization.getGoodsReleaseTimeLimit().intValue());
                localClearanceAuthorizationItem.setReference(localClearanceAuthorization.getReference());
                localClearanceAuthorizationItem.setTin(localClearanceAuthorization.getTin());
                localClearanceAuthorizationItem.setValidFrom(convertXMLDate(localClearanceAuthorization.getValidFrom())); // validFrom (optional)
                localClearanceAuthorizationItem.setValidTo(convertXMLDate(localClearanceAuthorization.getValidTo())); // validTo (optional)

                referenceDataAbstractDataType.addReferenceDataAbstractItemEntity(localClearanceAuthorizationItem);
            }
        }

        // NameAndDescritption - common for all RDT
        for (NameAndDescription nameAndDescription : referenceDataItem.getNameAndDescription()) {
            if (referenceDataAbstractDataType == null) {
                log.warn("reference data type was null , only name and description provided. using simple data type");
                referenceDataAbstractDataType = new SimpleItemDataType();
            }
            NameAndDescriptionItem nameAndDescriptionItem = new NameAndDescriptionItem(referenceDataAbstractDataType);
            nameAndDescriptionItem.setDescription(nameAndDescription.getDescription());
            nameAndDescriptionItem.setLanguageCode(nameAndDescription.getLanguageCode());
            nameAndDescriptionItem.setName(nameAndDescription.getName());
            referenceDataAbstractDataType.addNameAndDescriptionItem(nameAndDescriptionItem);

        }
        if (referenceDataAbstractDataType == null) {
            log.warn("reference data type was null , only name and description provided. using simple data type");
            referenceDataAbstractDataType = new SimpleItemDataType();
        }
//            String dictionaryId = xmlReferenceDataEntry.getKey();


        referenceDataAbstractDataType.setRefDataId(referenceDataItem.getId()); // id (required)
        referenceDataAbstractDataType.setSource("MDAS"); // source (optional)
        referenceDataAbstractDataType.setOrigin("EU"); // origin (optional)
        referenceDataAbstractDataType.setItemType(referenceDataItem.getItemType()); // itemType (optional)
        referenceDataAbstractDataType.setItemCodeType(referenceDataItem.getItemCodeType()); // itemCodeType (optional)
        referenceDataAbstractDataType.setPublics(true); // public (optional)
        referenceDataAbstractDataType.setLastChange(new Date()); // lastChange (optional)


        //referenceDataAbstractDataType.setVersionNumber(1); // nie ustaiwac

        return referenceDataAbstractDataType;
    }
}
