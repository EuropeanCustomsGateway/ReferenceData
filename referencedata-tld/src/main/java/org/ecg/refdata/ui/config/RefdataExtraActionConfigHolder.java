package org.ecg.refdata.ui.config;

/**
 *
 * For holding object responsible for creating additional actions for refdata
 * form inputs
 *
 */
public class RefdataExtraActionConfigHolder {

    private RefdataExtraActionConfig config;

    private static RefdataExtraActionConfigHolder instance;

    static {
        instance = new RefdataExtraActionConfigHolder();
    }

    public static RefdataExtraActionConfigHolder getInstance() {
        return instance;
    }

    //cannot create instance
    private RefdataExtraActionConfigHolder() {
    }

    public synchronized void setExtraActionConfig(RefdataExtraActionConfig config) {
        this.config = config;
    }

    /**
     * may be null!
     *
     * @return
     */
    public synchronized RefdataExtraActionConfig getExtraActionConfig() {
        return config;
    }
}
