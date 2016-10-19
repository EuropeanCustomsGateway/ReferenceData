package org.ecg.refdata;

/**
 * makes possible to include configuration inside reference data source
 *
 */
public interface ConfiguredReferenceDataSource extends ReferenceDataSource{

    IReferenceDataConfig getConfig();
    
}
