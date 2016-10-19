package org.ecg.refdata.ui.config;

import org.ecg.refdata.ui.model.RefDataCommonTextDesignModel;

/**
 *
 * This interface if to manage extra actions that may be added to refdata fields
 * (as an icon that will be placed next to the field and maybe next to the
 * magnyfing glass icon).
 *
 * Implementing object should return actions that should be added to the field
 * that will be created with given RefDataCommonTextDesignModel object.
 *
 * The implementing object should be set with @link
 * #RefdataExtraActionConfigHolder.
 *
 *
 *
 */
public interface RefdataExtraActionConfig {

    /**
     * Method should return an array of extra actions that will be added to
     * reference data field which will be created from given model
     *
     * @param model - model for which refdata input element will be created
     * @param value - value of the element
     * @return an array of actions that should be added to the form input.
     */
    public RefdataExtraAction[] getExtraActionsForField(RefDataCommonTextDesignModel model, String value);

}
