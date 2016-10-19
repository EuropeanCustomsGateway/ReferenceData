/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/global/jscript/utility.js,v 1.39 2008/06/11 19:51:15 p001002 Exp $
 * $Revision: 1.39 $
 * $Date: 2008/06/11 19:51:15 $
 *
 * ====================================================================
 *
 * Copyright (c) 2000 - 2007 SCC Informationssysteme GmbH. All rights
 * reserved.
 * Vendor URL : http://www.scc-gmbh.com
 * Product URL: http://www.common-controls.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL SCC INFORMATIONSSYSTEME GMBH OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * Note: This file belongs to the Common Controls Presentation
 *       Framework. Permission is given to use this script only
 *       together with the Common Controls Presentation Framework
 *
 * ====================================================================
 */

/////////////////////////////////////////////////////////////
// Helper for the Common Controls Framework
/////////////////////////////////////////////////////////////


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Helper for the Common Controls Framework
|             
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial version
| 24.10.2004  G.Schulz (SCC)    JScript support for CCUtility_crtCtrla function
|                               New function CCUtility_submitEnclosingForm
| 06.07.2005  G.Schulz (SCC)    form.onsubmit(); added within method submitEnclosingForm()
| 14.03.2006  G.Schulz (SCC)    decodeURIComponent Problem IE 5.0 fixed.
|                               New setStylePointer() method
+ ---------------------------------------------------------------------------------+
*/

function CCUtility() {}
function CCUtility_getEnclosingForm(node) {

	if (null != node && node.form != null) { 
		return node.form;
	}

	if (null != node && node.nodeName == 'FORM') {
		return node;
	}

	// search the form which embeds the Element
	var parent = node.parentNode;
	
	if (null == parent) return null;

	if (parent.nodeName == 'FORM') {
		return parent;
	} else {
		return arguments.callee(parent);
	}
}
function CCUtility_submitEnclosingForm(node) {

	var form = this.getEnclosingForm(node);
	
	if (null == form) {
		// form not specified -> do nothing
		return;
	} else {
		var rtc = true;
		
		if (null != form.onsubmit) {
			rtc = form.onsubmit();
		}

		if (rtc) form.submit();
	}
}
function CCUtility_resetEnclosingForm(node) {

	var form = this.getEnclosingForm(node);
	
	if (null != form) {
		form.reset();
	}

	// do not submit the form
	return false;
}
function CCUtility_submitForm(form) {

	if (null == form) {
		// form not specified -> do nothing
		return;
	} else {
		var rtc = true;
		
		if (null != form.onsubmit) {
			rtc = form.onsubmit();
		}

		if (rtc) {
			// check for ajax request
			var ajaxRequest = false;
			
			if (ajaxRequest) {
				this.sendAjaxInterceptPost(form);
			} else {
				form.submit();
			}
		}
	}
}
function CCUtility_sendAjaxInterceptPost(form) {
	// for overwriting if another ajax library should be used
	ajaxInterceptPost(form);
}
function CCUtility_setActiveButton(name) {
	
	// clear last 
	var input = document.getElementsByTagName('Input');
	
	for (var i=0; i < input.length; i++) {
		var btnName = input[i];
		
		if (null != btnName.name && btnName.name.indexOf('btn') > 0 && btnName.name.lastIndexOf('Hidden') > 0) {
			input[i].value = '';
		}
	}

	document.getElementById(name).value = 'clicked';
}

function CCUtility_createHidden(fldName, fldValue) {
	
	var exists = false;
	var isCtrla = (fldValue.indexOf('ctrl') != -1);

//alert(isCtrla + ' --> ' + fldValue);	
	
	if (!isCtrla) {
		// check if the hidden field already exist
		var input = document.getElementById(fldName);

		if (null != input) {
			input.value = fldValue;
			exists = true;
		}
	}
	else {
		var ctrlName1 = fldValue.split(';')[0].split('=');
		var arr = document.getElementsByTagName('input');

		for (var i = 0; i < arr.length; i++) {
			var input = arr[i];
			
			if (input.type == 'hidden' && null != input.value && input.value.indexOf('ctrl') != -1) {
				var ctrlName2 = input.value.split(';')[0].split('=');

				// if it's for the same control update the field
				if (ctrlName1 == ctrlName2) {
					input.value = fldValue;
					exists = true;
				}
			}		
		}
	}
	
	if (!exists) {
		var input=document.createElement('INPUT');
		input.type='hidden';
		input.id=fldName;
		input.name=fldName;
		input.value=fldValue;
		return input;
	}
	
	return input;
}

/**
 * CCUtility_crtCtrla(node, param, formId, userscript)
 *
 * node......: Current node which generates the event
 * param.....: action used by the control to identify the event
 * formId....: optional id of the form, if null the enclosing form is searched by the script
 * userscript: optional jscript which must return true before the form is submitted
 */
function CCUtility_crtCtrla(node, param, formId, userscript) {
	var form = null;

	// if a user script was defined execute the script
	// To replace by CCUtility_executeUserScript
	if (null != userscript) {
		var rtc = true;

		var userfct = new Function(userscript);
		var obj = userfct();

		if (typeof obj == 'boolean') {
			rtc = new Boolean(obj);
		}

		if (!rtc.valueOf()) {	
			// old: return false;
			return;
		}
	}

	// first check if the formId was specified
	if (null != formId && '' != formId) {
		form = document.getElementById(formId);
	} else {
		// try to find the enclosing form
		form = this.getEnclosingForm(node);
	}

	if (null != form) {
		var hidden = null;
	
		// append the hidden field which contains the 
		// name of the control, action and additional parameter 
		if (param.length != 0 && param.substring(0,4) != 'null') {
			hidden = this.createHidden('ctrla', param);
			form.appendChild(hidden);
		}
		
		// submit the form
		this.submitForm(form);
		
		// reset the information
		hidden.value = '';
		
	} else {
		// not found. Do nothing
	}
}
function CCUtility_executeUserScript(userscript) {

	if (null != userscript && '' != userscript) {
		var userfct = new Function(userscript);
		var obj = userfct();

		if (typeof obj == 'boolean') {
			var rtc = new Boolean(obj);
			return rtc.valueOf();
		}
	}

	return false;
}
function CCUtility_showFormElement(obj, show) {

	var row = this.getObject(obj);

	if (row == null) {
		return;
	}
 
	var display = (arguments.length >= 2) ? show : '';
	
	row.style.display = display;
	
	var nextRow = row.nextSibling;

	if ((nextRow != null) && (nextRow.getAttribute('rowtype') == 'separator')) {
		nextRow.style.display = display;
	}
}
function CCUtility_hideFormElement(obj) {
	this.showFormElement(obj, 'none');
}
function CCUtility_showSingleFormElement(arr, id) {

	if (null == arr) {
		return;
	}

	for (var i=0; i < arr.length; i++) {
		var row = this.getObject(arr[i]);
		
		if (row.id != id) {
			this.hideFormElement(row);
		} else {
			this.showFormElement(row);
		}
	}
}
function CCUtility_getObject(obj) {

	// check argument
	if (obj == null) {
		return null;
	} else if (typeof obj == 'string') {
		return document.getElementById(obj);
	} else if (typeof obj == 'object') {
		return obj;
	} else {
		return null;
	}
}
function CCUtility_decodeURIComponent(uri) {
//	notSupported = Browser.extractVersion() < 5.5;
	var newUrl = new String(uri); 
	
	if (typeof decodeURIComponent == 'undefined') {
		newUrl = newUrl.split('%3D').join('=');
		newUrl = newUrl.split('%3B').join(';');
		newUrl = newUrl.split('%5B').join('[');
		newUrl = newUrl.split('%5D').join(']');
		newUrl = newUrl.split('%20').join(' ');
		return newUrl;
	} else {
		return decodeURIComponent(newUrl);
	}
}
function CCUtility_setStylePointer(obj) {

	if (ie || safari || opera) {
		obj.style.cursor = 'hand';
	} else {
		obj.style.cursor = 'pointer';
	}
}
new CCUtility();
CCUtility.getEnclosingForm      = CCUtility_getEnclosingForm;
CCUtility.submitForm            = CCUtility_submitForm;
CCUtility.sendAjaxInterceptPost = CCUtility_sendAjaxInterceptPost;
CCUtility.submitEnclosingForm   = CCUtility_submitEnclosingForm;
CCUtility.resetEnclosingForm    = CCUtility_resetEnclosingForm;
CCUtility.setActiveButton       = CCUtility_setActiveButton;
CCUtility.createHidden          = CCUtility_createHidden;
CCUtility.crtCtrla              = CCUtility_crtCtrla;
CCUtility.executeUserScript     = CCUtility_executeUserScript;
CCUtility.showFormElement       = CCUtility_showFormElement;
CCUtility.hideFormElement       = CCUtility_hideFormElement;
CCUtility.showSingleFormElement = CCUtility_showSingleFormElement;
CCUtility.getObject             = CCUtility_getObject;
CCUtility.decodeURIComponent    = CCUtility_decodeURIComponent;
CCUtility.setStylePointer       = CCUtility_setStylePointer;



/*
+ ---------------------------------------------------------------------------------+
| Purpose..: Helper for the ListControl object
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 08.04.2004  G.Schulz (SCC)    Erstversion
| 08.06.2004  G.Schulz (SCC)    ListHelp.checkAll added; ListHelp_uncheck renamed
|
+ ---------------------------------------------------------------------------------+
*/
function ListHelp() {
}
/**
 * Unchecks all Checkboxes specified in the argument
 * param: items An array of input fields
 */
function ListHelp_uncheckAll(items) {

	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' ) {

			// uncheck checkbox
			items[i].checked = false;
		}
	}
}
function ListHelp_checkAll(items) {
	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' ) {

			// uncheck checkbox
			items[i].checked = true;
		}
	}
}
function ListHelp_isChecked(items) {
	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' || items[i].type == 'radio') {
			if (items[i].checked) {
				return true;
			}
		}
	}
	
	return false;
}
new ListHelp();
ListHelp.uncheckAll     = ListHelp_uncheckAll;
ListHelp.checkAll       = ListHelp_checkAll;
ListHelp.isChecked      = ListHelp_isChecked;


/*
+ ---------------------------------------------------------------------------------+
| Object....: HTTPUtil
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function HTTPUtil() {
}
function HTTPUtil_getParameters(location) {
	var url = new String(location);
	
	if (null == url) {
		return null;
	}
	var qString = url.split('?');

	if (null == qString || qString.length == 1) {
		return null;
	}
	
	var params = qString[1].split('&');
	return params;
}
function HTTPUtil_getParameter(key, params) {
	
	if (null == params || null == key) {
		return '';
	}
	
	for (var i=0; i < params.length; i++) {
		var arr = params[i].split('=');
		
		if (arr[0].toUpperCase() == key.toUpperCase()) {
			return arr[1];
		}
	}
	
	return '';
}
function HTTPUtil_isSecure() {
	return ('https:' == window.location.protocol);
}
new HTTPUtil();
HTTPUtil.getParameters  = HTTPUtil_getParameters;
HTTPUtil.getParameter   = HTTPUtil_getParameter;
HTTPUtil.isSecure       = HTTPUtil_isSecure;


function OptionUtil() {}
function OptionUtil_doMoveOption(objSelect, direction) {
	var changed  = false;
	var arrIndex = new Array();
	var offset   = (direction == Direction.UP) ? -1 : 1;
	
	if (objSelect.options.length == 0) {
		return false;
	}

	// get the selected items	
	for (var i=0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].selected) {
			arrIndex[arrIndex.length] = i;
		}
	}
	
	// selected items found ?
	if (arrIndex.length == 0) {
		return false;
	}
	
	if (direction == Direction.DOWN) {
		arrIndex.reverse();
	}
	
	// now check if the range of items can be moved
	var firstSelectedItem = arrIndex[0];

	if (direction == Direction.UP && firstSelectedItem == 0) {
		return false;
	} else if (direction == Direction.DOWN && firstSelectedItem == objSelect.options.length - 1) {
		return false;
	}
	
	// move the selected options
	for (var i=0; i < arrIndex.length; i++) {
		var currIndex = arrIndex[i];

		var source = objSelect.options[currIndex];
		var target = objSelect.options[currIndex + offset];
		objSelect.options[currIndex]          = new Option(target.text, target.value, false, false);
		objSelect.options[currIndex + offset] = new Option(source.text, source.value, false, false);

		// select the options again
		objSelect.options[currIndex + offset].selected = true;
		
		changed = true;
	}
	
	return changed;
}
new OptionUtil();
OptionUtil.doMoveOption = OptionUtil_doMoveOption;