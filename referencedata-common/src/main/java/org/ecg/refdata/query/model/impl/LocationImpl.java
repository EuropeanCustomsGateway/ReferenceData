package org.ecg.refdata.query.model.impl;

import org.ecg.refdata.query.model.Location;

/**
 * Class stores an information about location. Unified structure used for
 * storing information about locations like warehouses, authorized locations
 * etc.
 * 
 */
public class LocationImpl implements Location {

	private static final long serialVersionUID = -2870942005046911878L;
	/**
	 * @serial City
	 */
	private String city;
	/**
	 * @serial Name
	 */
	private String name;
	/**
	 * @serial Postal code
	 */
	private String postalCode;
	/**
	 * @serial Reference number
	 */
	private String referenceNumber;
	/**
	 * @serial Street and number
	 */
	private String streetAndNumber;
	/**
	 * @serial Country code
	 */
	private String countryCode;

	/**
	 * Basic constructor takes few following parameters
	 * 
	 * @param city
	 *            the city
	 * @param name
	 *            the name
	 * @param postalCode
	 *            the postal code
	 * @param referenceNumber
	 *            the reference number
	 * @param streetAndNumber
	 *            the street and number
	 */
	public LocationImpl(String city, String name, String postalCode,
			String referenceNumber, String streetAndNumber, String countryCode) {
		this.city = city;
		this.name = name;
		this.postalCode = postalCode;
		this.referenceNumber = referenceNumber;
		this.streetAndNumber = streetAndNumber;
		this.countryCode = countryCode;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getCity()
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getPostalCode()
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getReferenceNumber()
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getStreetAndNumber()
	 */
	public String getStreetAndNumber() {
		return streetAndNumber;
	}

	/**
	 * @see org.ecg.refdata.query.model.Location#getCountryCode()
	 */
	public String getCountryCode() {
		return countryCode;
	}

    public String getCode() {
        return getCountryCode();
    }

    public String getDescription() {
        return getName();
    }
}
