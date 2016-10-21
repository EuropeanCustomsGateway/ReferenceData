package org.ecg.refdata.ui.config;

/**
 *
 *  Object holds information about extra action tha should be added to
 *  refdata text field.
 *  ExtraAction consists of:
 *
 *  -actionJS - name of JavaScript function that has to be defined in js file and will be executed when action icon
 *  is clicked (will be executed as onclick handler and event argument may be passed)
 *  -enabledJS -name of JavaSCript function that has to be defined in js file and will be executed to check
 *  whether action should be available in current state ( will be executed as onchange handler of corresponding refdata field and
 *  event argument may be passed
 *  -icon is a url to image that will be an icon for this action
 *  - tooltip will be.... a tooltip for the action
 *
 *
 */
public class RefdataExtraAction {

	private String actionJS;
	private String enabledJS;
	private String icon;
	private String tooltip;

	public RefdataExtraAction(String actionJS, String enabledJS, String icon, String tooltip) {
		this.actionJS = actionJS;
		this.enabledJS = enabledJS;
		this.icon = icon;
		this.tooltip = tooltip;
	}

	public RefdataExtraAction() {

	}

	public String getActionJS() {
		return actionJS;
	}

	public void setActionJS(String actionJS) {
		this.actionJS = actionJS;
	}

	public String getEnabledJS() {
		return enabledJS;
	}

	public void setEnabledJS(String enabledJS) {
		this.enabledJS = enabledJS;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
