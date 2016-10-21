package org.ecg.refdata.datasource.entities.customsOffice;

import org.ecg.refdata.datasource.entities.NameAndDescriptionItem;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataTypeEntity;
import org.ecg.refdata.datasource.entities.commons.ReferenceDataPeriodicValidityAbstractItem;
import org.ecg.refdata.query.DictionaryItem;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable;
import org.ecg.refdata.query.model.CustomsOffice.CustomsOfficeTimetable.CustomsOfficeDedicatedTrader;
import org.ecg.refdata.query.model.impl.CustomsOfficeImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents entity bean of JPA. Customs Office information.
 *
 */
@Entity()
@Table(name = "ref_cust_off_item")
@DiscriminatorValue(value = "CustomsOfficeItem")
@PrimaryKeyJoinColumn(name = "ref_item_mapping_id")
public class CustomsOfficeItem extends
        ReferenceDataPeriodicValidityAbstractItem {

    private static final long serialVersionUID = 1319410760494858650L;

    /**
     * @serial Set of {@link CustomsOfficeDedicatedTraderItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeItem", targetEntity = CustomsOfficeDedicatedTraderItem.class)
    private Set<CustomsOfficeDedicatedTraderItem> customsOfficeDedicatedTraderItems = new HashSet<CustomsOfficeDedicatedTraderItem>();
    /**
     * @serial Set of {@link CustomsOfficeSpecificNotesItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeItem", targetEntity = CustomsOfficeSpecificNotesItem.class)
    private Set<CustomsOfficeSpecificNotesItem> customsOfficeSpecificNotesItems = new HashSet<CustomsOfficeSpecificNotesItem>();
    /**
     * @serial Set of {@link CustomsOfficeLSDItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeItem", targetEntity = CustomsOfficeLSDItem.class)
    private Set<CustomsOfficeLSDItem> customsOfficeLSDItems = new HashSet<CustomsOfficeLSDItem>();
    /**
     * @serial Set of {@link CustomsOfficeTimetableItem}
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customsOfficeItem", targetEntity = CustomsOfficeTimetableItem.class)
    private Set<CustomsOfficeTimetableItem> customsOfficeTimetableItems = new HashSet<CustomsOfficeTimetableItem>();
    /**
     * @serial Country code field
     */
    @Column(name = "country_code", unique = false, nullable = true, length = 2)
    private String countryCode;
    /**
     * @serial Email address field
     */
    @Column(name = "email_address", unique = false, nullable = true, length = 70)
    private String emailAddress;
    /**
     * @serial Fax number field
     */
    @Column(name = "fax_number", unique = false, nullable = true, length = 35)
    private String faxNumber;
    /**
     * @serial Info code field
     */
    @Column(name = "info_code", unique = false, nullable = true, length = 6)
    private String geoInfoCode;
    /**
     * @serial NCTS entry date field
     */
    @Column(name = "ncts_entry_date", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date nctsEntryDate;
    /**
     * @serial Nearest office field
     */
    @Column(name = "nearest_office", unique = false, nullable = true, length = 175)
    private String nearestOffice;
    /**
     * @serial Nearest office LNG
     */
    @Column(name = "nearest_office_LNG", unique = false, nullable = true, length = 2)
    private String nearestOfficeLNG;
    /**
     * @serial Phone number field
     */
    @Column(name = "phone_number", unique = false, nullable = true, length = 35)
    private String phoneNumber;
    /**
     * @serial Postal code field
     */
    @Column(name = "postal_code", unique = false, nullable = true, length = 9)
    private String postalCode;
    /**
     * @serial Reference number field
     */
    @Column(name = "refer_no", unique = false, nullable = true, length = 8)
    private String referenceNumber;
    /**
     * @serial Reference number authority of enquiry field
     */
    @Column(name = "refer_no_authority_of_enquiry", unique = false, nullable = true, length = 8)
    private String referenceNumberAuthorityOfEnquiry;
    /**
     * @serial Reference number authority of recovery field
     */
    @Column(name = "refer_no_authority_of_recovery", unique = false, nullable = true, length = 8)
    private String referenceNumberAuthorityOfRecovery;
    /**
     * @serial Reference number higher authority field
     */
    @Column(name = "refer_no_higher_authority", unique = false, nullable = true, length = 8)
    private String referenceNumberHigherAuthority;
    /**
     * @serial Reference number main office field
     */
    @Column(name = "refer_no_main_office", unique = false, nullable = true, length = 8)
    private String referenceNumberMainOffice;
    /**
     * @serial Reference number take over field
     */
    @Column(name = "refer_no_take_over", unique = false, nullable = true, length = 8)
    private String referenceNumberTakeOver;
    /**
     * @serial Region code field
     */
    @Column(name = "region_code", unique = false, nullable = true, length = 3)
    private String regionCode;
    /**
     * @serial Telex number field
     */
    @Column(name = "telex_number", unique = false, nullable = true, length = 35)
    private String telexNumber;
    /**
     * @serial Trader dedicated field
     */
    @Column(name = "trader_dedicated", unique = false, nullable = true)
    private Boolean traderDedicated;
    /**
     * @serial UnLocode Id field
     */
    @Column(name = "un_locode_id", unique = false, nullable = true, length = 3)
    private String unLocodeId;

    /**
     * For hibernate only
     */
    protected CustomsOfficeItem() {
        super();
    }

    /**
     * Creates CustomsOfficeItem Item for a given
     * ReferenceDataAbstractDataTypeEntity
     *
     * @param referenceDataTypeEntity ReferenceDataAbstractDataTypeEntity to
     * which created item will belong
     */
    public CustomsOfficeItem(
            ReferenceDataAbstractDataTypeEntity referenceDataTypeEntity) {
        super(referenceDataTypeEntity);
    }

    // CustomsOfficeDedicatedTraderItem
    public Set<CustomsOfficeDedicatedTraderItem> getCustomsOfficeDedicatedTraderItems() {
        return Collections.unmodifiableSet(customsOfficeDedicatedTraderItems);
    }

    public boolean addCustomsOfficeDedicatedTraderItem(
            CustomsOfficeDedicatedTraderItem customsOfficeDedicatedTraderItem) {
        return customsOfficeDedicatedTraderItems
                .add(customsOfficeDedicatedTraderItem);
    }

    public boolean removeCustomsOfficeDedicatedTraderItem(
            CustomsOfficeDedicatedTraderItem customsOfficeDedicatedTraderItem) {
        return customsOfficeDedicatedTraderItems
                .remove(customsOfficeDedicatedTraderItem);
    }

    public void removeAllCustomsOfficeDedicatedTraderItems() {
        customsOfficeDedicatedTraderItems.clear();
    }

    // CustomsOfficeSpecificNotesItem
    public Set<CustomsOfficeSpecificNotesItem> getCustomsOfficeSpecificNotesItems() {
        return Collections.unmodifiableSet(customsOfficeSpecificNotesItems);
    }

    public boolean addCustomsOfficeSpecificNotesItem(
            CustomsOfficeSpecificNotesItem customsOfficeSpecificNotesItem) {
        return customsOfficeSpecificNotesItems
                .add(customsOfficeSpecificNotesItem);
    }

    public boolean removeCustomsOfficeSpecificNotesItem(
            CustomsOfficeSpecificNotesItem customsOfficeSpecificNotesItem) {
        return customsOfficeSpecificNotesItems
                .remove(customsOfficeSpecificNotesItem);
    }

    public void removeAllCustomsOfficeSpecificNotesItems() {
        customsOfficeSpecificNotesItems.clear();
    }

    // CustomsOfficeLSDItem
    public Set<CustomsOfficeLSDItem> getCustomsOfficeLSDItems() {
        return Collections.unmodifiableSet(customsOfficeLSDItems);
    }

    public boolean addCustomsOfficeLSDItem(
            CustomsOfficeLSDItem customsOfficeLSDItem) {
        return customsOfficeLSDItems.add(customsOfficeLSDItem);
    }

    public boolean removeCustomsOfficeSpecificNotesItem(
            CustomsOfficeLSDItem customsOfficeLSDItem) {
        return customsOfficeLSDItems.remove(customsOfficeLSDItem);
    }

    public void removeAllCustomsOfficeLSDItems() {
        customsOfficeLSDItems.clear();
    }

    // CustomsOfficeTimetableItem
    public Set<CustomsOfficeTimetableItem> getCustomsOfficeTimetableItems() {
        return Collections.unmodifiableSet(customsOfficeTimetableItems);
    }

    public boolean addCustomsOfficeTimetableItem(
            CustomsOfficeTimetableItem customsOfficeTimetableItem) {
        return customsOfficeTimetableItems.add(customsOfficeTimetableItem);
    }

    public boolean CustomsOfficeTimetableItem(
            CustomsOfficeTimetableItem customsOfficeTimetableItem) {
        return customsOfficeTimetableItems.remove(customsOfficeTimetableItem);
    }

    public void removeAllCustomsOfficeTimetableItems() {
        customsOfficeTimetableItems.clear();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getGeoInfoCode() {
        return geoInfoCode;
    }

    public Date getNctsEntryDate() {
        return nctsEntryDate;
    }

    public String getNearestOffice() {
        return nearestOffice;
    }

    public String getNearestOfficeLNG() {
        return nearestOfficeLNG;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getReferenceNumberAuthorityOfEnquiry() {
        return referenceNumberAuthorityOfEnquiry;
    }

    public String getReferenceNumberAuthorityOfRecovery() {
        return referenceNumberAuthorityOfRecovery;
    }

    public String getReferenceNumberHigherAuthority() {
        return referenceNumberHigherAuthority;
    }

    public String getReferenceNumberMainOffice() {
        return referenceNumberMainOffice;
    }

    public String getReferenceNumberTakeOver() {
        return referenceNumberTakeOver;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getTelexNumber() {
        return telexNumber;
    }

    public Boolean getTraderDedicated() {
        return traderDedicated;
    }

    public String getUnLocodeId() {
        return unLocodeId;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setGeoInfoCode(String geoInfoCode) {
        this.geoInfoCode = geoInfoCode;
    }

    public void setNctsEntryDate(Date nctsEntryDate) {
        this.nctsEntryDate = nctsEntryDate;
    }

    public void setNearestOffice(String nearestOffice) {
        this.nearestOffice = nearestOffice;
    }

    public void setNearestOfficeLNG(String nearestOfficeLNG) {
        this.nearestOfficeLNG = nearestOfficeLNG;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setReferenceNumberAuthorityOfEnquiry(
            String referenceNumberAuthorityOfEnquiry) {
        this.referenceNumberAuthorityOfEnquiry = referenceNumberAuthorityOfEnquiry;
    }

    public void setReferenceNumberAuthorityOfRecovery(
            String referenceNumberAuthorityOfRecovery) {
        this.referenceNumberAuthorityOfRecovery = referenceNumberAuthorityOfRecovery;
    }

    public void setReferenceNumberHigherAuthority(
            String referenceNumberHigherAuthority) {
        this.referenceNumberHigherAuthority = referenceNumberHigherAuthority;
    }

    public void setReferenceNumberMainOffice(String referenceNumberMainOffice) {
        this.referenceNumberMainOffice = referenceNumberMainOffice;
    }

    public void setReferenceNumberTakeOver(String referenceNumberTakeOver) {
        this.referenceNumberTakeOver = referenceNumberTakeOver;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public void setTelexNumber(String telexNumber) {
        this.telexNumber = telexNumber;
    }

    public void setTraderDedicated(Boolean traderDedicated) {
        this.traderDedicated = traderDedicated;
    }

    public void setUnLocodeId(String unLocodeId) {
        this.unLocodeId = unLocodeId;
    }

    @Override
    public DictionaryItem getDictionaryItem(String locale) {

        CustomsOfficeLSDItem item = NameAndDescriptionItem.getLocalizedItem(customsOfficeLSDItems, locale);

        List<String> specificNotesCodeList = new ArrayList<String>();
        List<CustomsOfficeTimetable> customsOfficeTimetableList = new ArrayList<CustomsOfficeTimetable>();
        List<CustomsOfficeDedicatedTrader> customsOfficeDedicatedTraderList = new ArrayList<CustomsOfficeDedicatedTrader>();

        for (CustomsOfficeSpecificNotesItem cosni : customsOfficeSpecificNotesItems) {
            specificNotesCodeList.add(cosni.getSpecificNotesCode());
        }
        for (CustomsOfficeTimetableItem coti : customsOfficeTimetableItems) {
            customsOfficeTimetableList.add(coti.getCustomsOfficeTimetable());
        }
        for (CustomsOfficeDedicatedTraderItem codti : customsOfficeDedicatedTraderItems) {
            // TODO: find out if this list should be filtered by codti.languageCode
            customsOfficeDedicatedTraderList.add(codti.getCustomsOfficeDedicatedTrader());
        }

        if (item == null) {
            throw new RuntimeException("CustomsOffice with refNo '" + referenceNumber + "' has no CustomsOfficeLSDItem list.size = " + customsOfficeLSDItems.size());
        }

        return new CustomsOfficeImpl(this.referenceNumber, this.referenceNumberMainOffice,
                this.referenceNumberHigherAuthority,
                this.referenceNumberTakeOver,
                this.referenceNumberAuthorityOfEnquiry,
                this.referenceNumberAuthorityOfRecovery, this.countryCode,
                this.unLocodeId, this.regionCode, this.nctsEntryDate,
                this.nearestOffice, this.nearestOfficeLNG, this.postalCode,
                this.phoneNumber, this.faxNumber, this.telexNumber,
                this.emailAddress, this.geoInfoCode, this.traderDedicated,
                item.getCity(),
                item.getPrefixSuffixLevel(),
                item.getPrefixSuffixName(),
                item.getStreetAndNumber(),
                item.getUsualName(),
                item.isPrefixSuffixFlag(),
                item.isSpaceToAdd(),
                specificNotesCodeList,
                customsOfficeTimetableList,
                customsOfficeDedicatedTraderList
        );

//		return new CustomsOfficeImpl(
//				this.referenceNumber, this.referenceNumberMainOffice,
//				this.referenceNumberHigherAuthority,
//				this.referenceNumberTakeOver,
//				this.referenceNumberAuthorityOfEnquiry,
//				this.referenceNumberAuthorityOfRecovery, this.countryCode,
//				this.unLocodeId, this.regionCode, this.nctsEntryDate,
//				this.nearestOffice, this.nearestOfficeLNG, this.postalCode,
//				this.phoneNumber, this.faxNumber, this.telexNumber,
//				this.emailAddress, this.geoInfoCode, this.traderDedicated);
    }

}
