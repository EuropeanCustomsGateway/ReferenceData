package org.ecg.refdata.datasource.entities.simpleItem;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ecg.refdata.datasource.entities.commons.ReferenceDataAbstractDataType;

/**
 * Class represents a type of SimpleItemDataType dictionary.
 *
 */
@Entity()
@Table(name = "ref_simple_item_dt")
@DiscriminatorValue(value = "SimpleItemDataType")
public class SimpleItemDataType extends ReferenceDataAbstractDataType {

    private static final long serialVersionUID = -501577135761505115L;

    public SimpleItemDataType() {
        super(SimpleItemItem.class);
    }
}
