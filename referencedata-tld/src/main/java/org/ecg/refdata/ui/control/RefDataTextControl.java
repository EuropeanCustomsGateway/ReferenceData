package org.ecg.refdata.ui.control;

import com.cc.framework.ui.control.TextControl;
import org.ecg.refdata.ui.model.RefDataTextDesignModel;

/**
 * Current class extends <code>TextControl</code> from Common-Controls. Adds
 * couple of new required features. Change CC reference data text control to
 * SKG <code>RefDataTextDesignModel</code>.
 *
 */
public class RefDataTextControl extends TextControl {

    private static final long serialVersionUID = 1L;

    public RefDataTextControl(RefDataTextDesignModel designModel) {
        super(designModel);
    }

    public RefDataTextDesignModel getRefDataDesignModel() {
        return (RefDataTextDesignModel) getDesignModel();
    }

}
