/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/global/jscript/common.js,v 1.115 2008/06/11 19:51:15 p001002 Exp $
 * $Revision: 1.115 $
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
// Global Variables used by the CC-Framework
/////////////////////////////////////////////////////////////


var LF = '\n';
var NBSP = '';

var Direction = new Object();
Direction.UP   = 0;
Direction.DOWN = 1;

var RunAt = new Object();
RunAt.SERVER = 0;
RunAt.CLIENT = 1;

var SelectMode = new Object();
SelectMode.NONE = 0;
SelectMode.SINGLE = 1;
SelectMode.MULTIPLE = 2;

var NodeType = new Object();
NodeType.NODE  = 1;
NodeType.GROUP = 2;

var ExpandMode = new Object();
ExpandMode.SINGLE   = 0;
ExpandMode.MULTIPLE = 1;
ExpandMode.FULL     = 2;

var NodeState = new Object();
NodeState.NONE      = 0;
NodeState.EXPAND    = 1;
NodeState.COLLAPSE  = 2;
NodeState.EXPANDEX  = 3;

var NodeFilter = new Object();
NodeFilter.ROOT     = 2;
NodeFilter.GROUP    = 3;
NodeFilter.NODE     = 4;

var CheckState = new Object();
CheckState.NONE          = -2;
CheckState.UNSELECTABLE  = -1;
CheckState.UNCHECKED     = 0;
CheckState.CHECKED       = 1;
CheckState.UNDEFINED     = 2;

var Orientation = new Object();
Orientation.VERTICAL   = 1;
Orientation.HORIZONTAL = 2;


var ButtonType = new Object();
ButtonType.IMAGE   = 0;
ButtonType.TEXT    = 1;
ButtonType.BUTTON  = ButtonType.TEXT ; // default
ButtonType.CANCEL  = 3;
ButtonType.RESET   = 4;
ButtonType.USERDEF = 5;


// TODO remove
var ClientHandler = new Object();
ClientHandler.ONCHANGE = 1;
ClientHandler.ONCLICK  = 2;
ClientHandler.ONFOCUS  = 3;

var ClientEvent = new Object();
ClientEvent.ONCLICK        = 'onClick';
ClientEvent.ONCONTEXTMENU  = 'onContextMenu';
ClientEvent.ONFOCUS        = 'onFocus';
ClientEvent.ONMOUSEOVER    = 'onMouseOver';
ClientEvent.ONCHANGE       = 'onChange';
ClientEvent.ONCHECK        = 'onCheck';
ClientEvent.ONUNCHECK      = 'onUnCheck';

var SortOrder = new Object();
SortOrder.NONE             = 0;
SortOrder.ASC              = 1;
SortOrder.DESC             = 2;

var Globals = new Object();
Globals.STRUTS_CANCEL_KEY  = 'org.apache.struts.taglib.html.CANCEL';

String.prototype.LTrim = function() {var re = /\s*((\S+\s*)*)/; return this.replace(re, "$1");};
String.prototype.RTrim = function() {var re = /((\s*\S+)*)\s*/; return this.replace(re, "$1");};
String.prototype.trim  = function() {return this.LTrim().RTrim();};   // {return this.replace(/^\s+|\s+$/, '');};



/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window which shows a datetime picker.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 22.04.2004  G.Schulz (SCC)    Inital version
| 28.06.2005  G.Schulz (SCC)    Reduce the width if no time mask is present
+ ---------------------------------------------------------------------------------+
*/
var calendar = null;

function popupCalendar(fieldId, locale, formatMask, width, height, template, initDate) {
	var obj    = document.getElementById(fieldId);
	var id     = obj.id;
	var target = '';
	var value  = null;
	
	if (initDate != null && initDate != '') {
		value = initDate;
	} else {
		value = (null != obj.value) ? obj.value : '';
	}

	if (null == width || '' == width) {
		width = 350;
	}
	if (null == height || '' == height) {
		height = 250;
	}
	
	var isTime = DateFormat.isTimeMask(formatMask);
	var isDate = DateFormat.isDateMask(formatMask);
	
	if (!isTime) {
		width = 215;
	} else if (!isDate) {
		width = 250;
		height = 110;
	}
	
	target += template;
	target += '?datetime=' + value;
	target += '&fieldid=' + id;
	target += '&locale=' + locale.toUpperCase();
	target += '&mask=' + formatMask;

	// get the coordinate to display the new window
	var left = (screen.width - width) / 2;
	var top  = (screen.height - height) / 2;

	// Check if the window is already opened
	if (null == calendar || calendar.closed) {
		calendar = window.open(target, '', 'top=' + top + ',left=' + left + ',width=' + width + ',height=' + height + ',status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no');
	} else {
		calendar.focus();
	}
	
	return calendar;
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a Inline Popup Calendar Window
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Inital version
+ ---------------------------------------------------------------------------------+
*/
function createCalendarButtons(resPath, locale) {

	var btnLabel     = null;
	var btnTooltip   = null;
	var btnWidth     = null;
	var btnCancel    = null;
	var btnOk        = null;
	var btnAlt       = null;
	var imgPrevYear  = null;
	var imgNextYear  = null;
	var imgPrevMonth = null;
	var imgNextMonth = null;
	
	
	var imgSrc    = ['btnBkg1_left.gif', 'btnBkg1_middle.gif', 'btnBkg1_right.gif'];
	var imgWidth  = [7, 0, 7];
	var imgHeight = 24;

	// (1) CANCEL Button
	btnLabel    = DTPRes.getButtonLabel(locale,'Cancel');
	btnTooltip  = DTPRes.getButtonLabel(locale,'Cancel');
	btnWidth    = 90;
	btnCancel   = new TextButton('btnCancel', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

	// (2) OK Button
	btnLabel    = DTPRes.getButtonLabel(locale,'OK');
	btnTooltip  = DTPRes.getButtonLabel(locale,'OK');
	btnWidth    = 90;
	btnOk       = new TextButton('btnOk', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

	// (3) Previous Year
	btnTooltip  = DTPRes.getButtonLabel(locale,'PrevYearLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'PrevYearAlt');
	imgPrevYear = new Icon('imgPrevYear', resPath, 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

	// (4) Next Year	
	btnTooltip  = DTPRes.getButtonLabel(locale,'NextYearLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'NextYearAlt');
	imgNextYear = new Icon('imgNextYear', resPath, 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);

	// (5) Previous Month
	btnTooltip  = DTPRes.getButtonLabel(locale,'PrevMonthLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'PrevMonthAlt');
	imgPrevMonth = new Icon('imgPrevMonth', resPath, 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

	// (6) Next Month	
	btnTooltip  = DTPRes.getButtonLabel(locale,'NextMonthLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'NextMonthAlt');
	imgNextMonth = new Icon('imgNextMonth', resPath, 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);
	
	// (2) Create array including all buttons and images
	var buttons = [btnCancel, btnOk, imgPrevYear, imgNextYear, imgPrevMonth, imgNextMonth];

	return buttons;
}

function showInlineCalendar(obj, fieldId, locale, formatMask, width, height, resPath) {

	// default width/height
	var popupWidth  = 0;
	var popupHeight = 0;

	// Currently only the default template is supported
	resPath = 'fw/html/calendar/layout1/'; // = (null == resPath) ? 'fw/html/calendar/layout1/' : resPath;

	// get the localized button resources
	var buttons = createCalendarButtons(resPath, locale);

	var isTime = DateFormat.isTimeMask(formatMask);
	var isDate = DateFormat.isDateMask(formatMask);
	
	if (isDate && isTime) {
		popupWidth  = 390;
		popupHeight = 190 + 20;
	} else if (isTime) {
		popupWidth = 180;
		popupHeight = (ie || opera || safari) ? 70 : 85;
	} else if (isDate) {
		popupWidth  = 190;
		popupHeight = 190 + 20;
	}

	// create and open an inline popup
	var popup =  new PopUp(fieldId, obj, popupWidth, popupHeight);
	popup.open();

	// Configure the calendar
	var crtl_calendar = new Calendar(popup.div.id);
	crtl_calendar.setOpener(popup);
	crtl_calendar.setLocale(locale);
	crtl_calendar.setDayNameFormat(DayNameFormat.FIRSTTWOLETTER);   // display the first two characters of the dayname
	crtl_calendar.setDayNameToUppercase(DayNameToUppercase.FULL);   // convert the first characters of the dayname to uppercase
	crtl_calendar.setShowTimePicker(isTime);
	crtl_calendar.setFirstDayOfWeek(1);                             // start with monday
	crtl_calendar.setShowDaysOfOtherMonths(false);                  // do not display days form the prev or next month
	crtl_calendar.setCloseOnSelect(!isTime)                         // 28.07.2005: display ok/cancel button only if a time mask is present
	crtl_calendar.setShowTodaySelector(true);                       // display "today" selector
	crtl_calendar.setFieldId(fieldId);                              // id of the associated input field
	crtl_calendar.setFormatMask(formatMask);                        // set the format mask
	crtl_calendar.setPreselect(true);                               // open calendar with the date specified in the input field
	
	// set up the painter informations
	var caldata = new CalendarPainterData(crtl_calendar, resPath, locale, 0, buttons);
	
	// render the component
	CalendarPainter.render(caldata);
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window which shows a color picker.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 22.04.2004  G.Schulz (SCC)    Inital version
+ ---------------------------------------------------------------------------------+
*/
var colorpicker = null;

function popupCPicker(target, fieldId, locale, palette) {
	var obj    = document.getElementById(fieldId);
	var id     = obj.id;
	var value  = (null != obj.value) ? obj.value : '';

	target += '?fieldid=' + id;
	target += '&locale='  + locale.toUpperCase();
	target += '&value='   + value;
	target += '&palette=' + palette;

	// get the coordinate to display the new window
	var left = (screen.width - 255) / 2;
	var top  = (screen.height - 250) / 2;

	// Check if the window is already opened
	if (null == colorpicker || colorpicker.closed) {
		colorpicker = window.open(target, '', 'top=' + top + ',left=' + left + ',width=255,height=250,status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no');
	} else {
		colorpicker.focus();
	}
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window to edit the textarea within a separate window.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 21.10.2004  G.Schulz (SCC)    Inital version
| 15.11.2004  G.Schulz (SCC)    New readonly attribute
+ ---------------------------------------------------------------------------------+
*/
var textpopup = null;

function popupTextPopup(fieldId, locale, maxlength, readonly, width, height, rows, template) {
	var target    = '';
	var obj       = document.getElementById(fieldId);
	var id        = obj.id;
	var winWidth  = (null == width || isNaN(width)) ? 350 : width;
	var winHeight = (null == height || isNaN(height)) ? 150 : height;

	target += template;
	target += '?fieldid=' + id;
	target += '&maxlength=' + maxlength;
	target += '&locale='  + locale.toUpperCase();
	target += '&height='  + winHeight;
	target += '&readonly=' + readonly;
	
	// get the coordinate to display the new window
	var left = (screen.width - winWidth) / 2;
	var top  = (screen.height - winHeight) / 2;

	// Check if the window is already opened
	if (null == textpopup || textpopup.closed) {
		var options = 'top=' + top + ',left=' + left + ',width=' + winWidth + ',height=' + winHeight + ',status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no'
		textpopup = window.open(target, '', options);
	} else {
		textpopup.focus();
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Provides some helper functions for a textarea
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 12.06.2004  G.Schulz (SCC)    Initial version
| 01.11.2004  G.Schulz (SCC)    dirty flag added
| 13.02.2005  G.Schulz (SCC)    Input not longer accepted if maximum no. of characters is exceeded
| 10.03.2007  G.Schulz (SCC)    support for onChange-Handler added
+ ---------------------------------------------------------------------------------+
*/
function Textarea(id, maxlength, message, nobreak, disabled, readonly) {
	var a = arguments;
	this.id                = id;
	this.obj               = document.getElementById(id);
	this.maxlength         = (a.length > 1) ? a[1] : null;
	this.limited           = (maxlength != null) ? true : false;
	this.infoLine          = (a.length > 2) ? a[2] : 'Characters remaining: <b>{0}</b>/{1}';    // Text to display for remaining characters
	this.nobreak           = (a.length > 3) ? a[3] : false;
	this.disabled          = (a.length > 4) ? a[4] : false;
	this.readonly          = (a.length > 5) ? a[5] : false;
	this.infoLineNode      = null;                                                               // span which contains the text
	this.textWarningtAt    = 0;                                                                  // default 10 characters remaining
	this.CSS_INFOLINE      = 'ltail';
	this.dirty             = false;
	this.allowOverflow     = false;
	this.clientHandler     = new Array();
	
	// add eventhandler
	if (null != this.obj && this.limited) {
		
		// check if the textarea is readonly or disabled
		// In both cases no message is displayed
		if (!this.disabled && !this.readonly) {
			this.createInfoLine();
			this.setUpHandler();
			this.checkLimit();
		}
	}
}
function Textarea_getId() {
	return this.id;
}
function Textarea_getMaxLength() {
	return this.maxlength;
}
function Textarea_setInfoLine(infoLine) {
	this.infoLine = infoLine;
}
function Textarea_setUpHandler() {
	var _textarea = this;

	// check if the textarea is readonly or disabled
	// In both cases no message is displayed and
	// no handlers where needed
	if (this.isReadonly() || this.isDisabled()) {
		return;
	}
	
	this.obj.onchange = function(event) {
		_textarea.dirty = true;
		_textarea.checkLimit();
		
		var userfct = _textarea.getClientHandler(ClientHandler.ONCHANGE);
		CCUtility.executeUserScript(userfct);
	}
	this.obj.onkeyup = function(event) {
		_textarea.checkLimit();
	}
	this.obj.onkeypress = function(event) {
		var acceptKey = _textarea.checkLimit();

		if (!acceptKey && ie) {
			return false;
		} else {
			return true;
		}
	}
	this.obj.onpaste = function(event) {
		return _textarea.checkLimit();

		if (!acceptKey && ie) {
			return false;
		} else {
			return true;
		}
	}
}
function Textarea_checkLimit() {

	if (0 == this.maxlength) {
		// maxlength must not be checked
		return true;
	}

	var remaining = parseInt(this.maxlength) - parseInt(this.obj.value.length);

	if (!this.allowOverflow) {
		if (remaining == 0){
			this.updateInfoLine(0, 0);
			return false;
		}
		else if (remaining < 0) {
			this.obj.value = this.obj.value.substr(0, this.maxlength);
			this.updateInfoLine(0, this.textWarningtAt);
			return false;
		}
		else {
			this.updateInfoLine(remaining, this.textWarningtAt);
			return true;
		}
	} else {
		this.updateInfoLine(remaining, this.textWarningtAt);
		return true;
	}
}
function Textarea_updateInfoLine(remaining, warningtAt) {
	var warning = (remaining < warningtAt) ? true : false;

	var	out = this.infoLine;
	
	if (this.infoLine.indexOf('{0}') != -1) {
		out = out.replace('{0}', remaining);
	}
	if (this.infoLine.indexOf('{1}') != -1) {
		out = out.replace('{1}', this.maxlength);
	}
	
	if (warning) {
		out = out.fontcolor('red');
	}

	if (!this.nobreak) {
		out = '<br>' + out;
	}

	this.infoLineNode.innerHTML = out;
}
function Textarea_createInfoLine() {
	var text = document.createTextNode(this.infoLine);
	var span = document.createElement('Span');
	span.appendChild(text);
	span.className = this.CSS_INFOLINE;
	this.obj.parentNode.appendChild(span);
	
	this.infoLineNode = span;
}
function Textarea_encodeHTML(s) {
	s = s.replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g, "<BR>");

	while (/\s\s/.test(s)) {
		s = s.replace(/\s\s/, "&nbsp; ");
	}
	
	return s.replace(/\s/g, " ");
}
function Textarea_isDirty() {
	return this.dirty;
}
function Textarea_insertTag(textareaId, tag) {
	var ta = document.getElementById(textareaId);
	var tagOpen = '[' + tag.toLowerCase() + ']';
	var tagClose = '[/' + tag.toLowerCase() + ']';
	
	if (ie) {
		var selected = document.selection.createRange().text;

		if (selected) {
            var addSpace = false;
            if (selected.charAt(selected.length-1) == ' ') {
                selected = selected.substring(0, selected.length-1);
                addSpace = true;
            }
            document.selection.createRange().text = tagOpen + selected + tagClose + ((addSpace) ? ' ': '');
        } else {
            ta.value += tagOpen + tagClose;
        }
    } else {
        ta.value += tagOpen + tagClose;
    }
    
    ta.focus();
    return;
}
function Textarea_isReadonly() {
	return this.readonly;
/*
	if (null == this.obj) {
		return false;
	} else {
		if (ie) {
			return this.obj.getAttribute('readonly');
		} else {
			return this.obj.hasAttribute('readonly');
		}
	}
*/
}
function Textarea_isDisabled() {
	return this.disabled;
/*
	if (null == this.obj) {
		return false;
	} else {
		if (ie) {
			return this.obj.getAttribute('disabled');
		} else {
			return this.obj.hasAttribute('disabled');
		}
	}
*/
}
function Textarea_setAllowOverflow(allowOverflow) {
	this.allowOverflow = allowOverflow;
}
function Textarea_isAllowOverflow(allowOverflow) {
	return this.allowOverflow;
}
function Textarea_addClientHandler(type, script) {
	this.clientHandler[type] = script;
}
function Textarea_getClientHandler(type) {
	var handler = this.clientHandler[type];
	
	if (null != handler) {
		return handler;
	} else {
		return null;
	}
}
function Textarea_toString() {
	var out = '';
	out += '******** TextArea ******* ' + LF;
	out += 'Id.........: ' + this.id + LF;
	out += 'MaxLenght..: ' + this.maxlength + LF;
	out += 'Limited....: ' + this.limited + LF;
	out += 'InfoLine...: ' + this.infoLine + LF;
	out += 'Dirty......: ' + this.dirty + LF;
	out += 'Overflow...: ' + this.allowOverflow + LF;
	return out;
}
new Textarea();
Textarea.prototype.setUpHandler     = Textarea_setUpHandler;
Textarea.prototype.checkLimit       = Textarea_checkLimit;
Textarea.prototype.createInfoLine   = Textarea_createInfoLine;
Textarea.prototype.updateInfoLine   = Textarea_updateInfoLine;
Textarea.prototype.isDirty          = Textarea_isDirty;
Textarea.prototype.isReadonly       = Textarea_isReadonly
Textarea.prototype.isDisabled       = Textarea_isDisabled;
Textarea.prototype.setAllowOverflow = Textarea_setAllowOverflow;
Textarea.prototype.isAllowOverflow  = Textarea_isAllowOverflow;
Textarea.encodeHTML                 = Textarea_encodeHTML;
Textarea.insertTag                  = Textarea_insertTag;
Textarea.prototype.addClientHandler = Textarea_addClientHandler;
Textarea.prototype.getClientHandler = Textarea_getClientHandler;
Textarea.prototype.toString         = Textarea_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageMapping
| Function..: 
| Arguments.: 
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 05.01.2005  G.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function ImageMapping(rule, src, width, height, tooltip, alt, resource) {
	var a = arguments;

	this.rule     = rule;                              // RegExpression the image must match
	this.src      = src;                               // image source
	this.width    = width;                             // image width
	this.height   = height;                            // image height
	this.tooltip  = (a.length >= 5) ? tooltip : '';    // additional tooltip
	this.alt      = (a.length >= 6) ? alt : '';        // alt attribute
	this.resource = (a.length >= 7) ? resource : '';
}
function ImageMapping_getSource() {
	return this.src;
}
function ImageMapping_getWidth() {
	return this.width;
}
function ImageMapping_getHeight() {
	return this.height;
}
function ImageMapping_getTooltip() {
	return this.tooltip;
}
function ImageMapping_getAlt() {
	return this.alt;
}
function ImageMapping_toString() {
	var out = '';
	out += '********* ImageMapping ***********' + LF;
	out += 'Rule...........: ' + this.rule + LF;
	out += 'Source.........: ' + this.src + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Resource.......: ' + this.resource + LF;
	return out;
}
new ImageMapping();
ImageMapping.prototype.getSource  = ImageMapping_getSource;
ImageMapping.prototype.getWidth   = ImageMapping_getWidth;
ImageMapping.prototype.getHeight  = ImageMapping_getHeight;
ImageMapping.prototype.getTooltip = ImageMapping_getTooltip;
ImageMapping.prototype.getAlt     = ImageMapping_getAlt;
ImageMapping.prototype.toString   = ImageMapping_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageMap
| Function..: Collection for ImageMapping objects
| Arguments.: id, runAt
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 05.01.2005  G.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function ImageMap(id, runAt, base) {
	var a = arguments;
	
	this.arrImageMappings = new Array();                            // Collection
	this.id     = id;                                               // the id for this image map
	this.runAt  = (a.length >= 2) ? runAt : RunAt.SERVER;           // indicates if the control should work with or without round trips
	this.base   = (a.length >= 3) ? base : '';                      // The base directory for all the images
}
function ImageMap_addImageMapping(mapping) {
	if (mapping instanceof ImageMapping) {
		this.arrImageMappings[this.arrImageMappings.length] = mapping;
	}
}
function ImageMap_getImageMapping(rule) {
	for (var i=0; i <= this.arrImageMappings.length; i++) {
		var imRule = this.arrImageMappings[i]['rule'];
		
		if (imRule == rule) {
			return this.arrImageMappings[i];
		}
	}

	return null;
}
function ImageMap_getImageMappings() {
	// return the collection
	return this.arrImageMappings;
}
function ImageMap_toString() {
	var out = '';
	out += '********* ImageMap ***********' + LF;
	out += 'Id.............: ' + this.id + LF;
	out += 'RunAt..........: ' + this.runAt + LF;
	out += 'Base...........: ' + this.base + LF;
	return out;
}
new ImageMap();
ImageMap.prototype.toString           = ImageMap_toString;
ImageMap.prototype.addImageMapping    = ImageMap_addImageMapping;
ImageMap.prototype.getImageMapping    = ImageMap_getImageMapping;
ImageMap.prototype.getImageMappings   = ImageMap_getImageMappings;



/*
+ ---------------------------------------------------------------------------------+
| Object....: Icon
| Function..: An Image object
| Arguments.: id, resPath, src, width, height, tooltip, alt
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 17.08.2004  G.Schulz (SCC)    Initial version
| 22.11.2004  G.Schulz (SCC)    Hide ALT + TITLE if null
+ ---------------------------------------------------------------------------------+
*/
function Icon(id, resPath, src, width, height, tooltip, alt) {
	this.id        = id;             // unique button id
	this.width     = width;          // button width
	this.height    = height;         // button height
	this.src       = src;            // src-Attribute
	this.onclick   = Icon_onclick;
	this.resPath   = resPath;
	this.tooltip   = tooltip;        // Tooltip
	this.alt       = alt;
	this.border    = 0;
}
function Icon_onclick() {
	return;
}
function Icon_create() {
	var img    = document.createElement('Img');
	img.src    = (null != this.resPath) ? this.resPath + this.src : this.src;
	img.title  = (null != this.tooltip) ? this.tooltip : '';
	img.alt    = (null != this.alt) ? this.alt : '';
	img.border = 0;
	img.width  = this.width;
	img.height = this.height;
	img.setAttribute('vspace', 0);
	img.border = this.border;
	//img.id = 'btn' + this.id;
	return img;
}
function Icon_toString() {
	var out = '';
	out += '******* Icon *********' + LF;
	out += 'Id.............: ' + this.id + LF;
	out += 'ResPath........: ' + this.resPath + LF;
	out += 'Source.........: ' + this.src + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Border.........: ' + this.border + LF;
	return out;
}
new Icon();
Icon.prototype.onclick    = Icon_onclick;       // used to assign an onclick handler
Icon.prototype.toString   = Icon_toString;      // toString
Icon.prototype.create     = Icon_create;        // creates a html img object

/*
+ ---------------------------------------------------------------------------------+
| Object....: TextButton
| Function..: Provides a TextButton object
| Arguments.: id, label, resPath, imgSrc, imgWidth, height, tooltip
| 
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function TextButton(id, label, width, resPath, imgSrc, imgWidth, height, tooltip) {
	this.id        = id;             // unique button id
	this.label     = label;          // the button label
	this.width     = width;          //
	this.imgWidth  = imgWidth;       // array including width of the bgimages (left, middle, right)
	this.imgSrc    = imgSrc;         // array including the image resources (left, middle, right)
	this.height    = height;         // button height
	this.onclick   = TextButton_onclick;
	this.resPath   = resPath;
	this.tooltip   = tooltip;

	if (arguments.length <= 4) {
		// register some default images
		this.imgSrc   = ['btnBkg1_left.gif', 'btnBkg1_middle.gif', 'btnBkg1_right.gif'];
		this.imgWidth = [7, 0, 7];
		this.height   = 24;
		this.tooltip  = label;
	}
}
function TextButton_create() {
	var row   = null;
	var cell  = null;
	var img   = null;
	var span  = null;

	// ensure table width > 0
	var btnTotalWidth = TextButton.getWidth(this.imgWidth);
	var width = ((this.width - btnTotalWidth) < 0) ? this.width : (this.width - btnTotalWidth);

	// create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.width = width;
	table.className = 'tbtn';
	table.onclick = this.onclick;
	
	row = table.insertRow(table.rows.length);
	row.setAttribute('valign', 'middle');
	
	// left
	cell = row.insertCell(row.cells.length);
	img = document.createElement('Img');
	img.width  = this.imgWidth[0];
	img.height = this.height;
	img.src    = this.resPath + this.imgSrc[0];
	img.border = 0;
	cell.appendChild(img);
	
	// middle
	cell = row.insertCell(row.cells.length);
	cell.width = '100%';
	cell.setAttribute('background-position', 'right');
	cell.setAttribute('word-wrap', true);
	cell.setAttribute('background', this.resPath + this.imgSrc[1]);
	
	span = document.createElement('Span');
	span.appendChild(document.createTextNode(this.label));
	span.title  = this.tooltip;
	cell.appendChild(span);
	
	// right
	cell = row.insertCell(row.cells.length);
	img = document.createElement('Img');
	img.width  = this.imgWidth[2];
	img.height = this.height;
	img.src    = this.resPath + this.imgSrc[2];
	img.border = 0;
	cell.appendChild(img);

	return table;
}
function TextButton_onclick() {
	return;
}
function TextButton_getWidth(widths) {
	var total = 0;
	
	for(var i=0; i < widths.length; i++) {
		var val = parseInt(widths[i]);
		
		if (!isNaN(val)) {
			total =+ val;
		}
	}
	return total;
}
function TextButton_toString() {
	var out = '';
	out += '******* TextButton *********' + LF;
	out += 'Id.............: ' + this.id + LF;
	out += 'Label..........: ' + this.label + LF;
	out += 'BGImages.......: ' + this.imgSrc + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	
	return out;
}
new TextButton();
TextButton.prototype.create     = TextButton_create;
TextButton.prototype.onclick    = TextButton_onclick;
TextButton.prototype.toString   = TextButton_toString;
TextButton.getWidth             = TextButton_getWidth;


/*
+ ---------------------------------------------------------------------------------+
| Object...: SwapSelect
| Function.: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 29.12.2004  G.Schulz          Inital version
| 03.03.2005  G.Schulz          onChange Handler Support added
| 30.05.2007  G.Schulz          MultiSwapSelect Feature added
+ ---------------------------------------------------------------------------------+
*/
function SwapSelect(id, runat, hiddenName, disabled, sortOrder, groupsize) {
	var a = arguments;
	
	this.id            = id;                                               // the id for the control
	this.runAt         = (a.length >= 2) ? runat : RunAt.SERVER;           // indicates if the control should work with or without round trips
	this.hiddenName    = (a.length >= 3) ? hiddenName : id;                // Name of the hidden field
	this.disabled      = (a.length >= 4) ? disabled : false;
	this.sortOrder     = (a.length >= 5) ? sortOrder : SortOrder.NONE;     // SortOrder
	this.groupsize     = (a.length >= 6) ? groupsize : 1;                  // Number of select elements (1 if multiple is false)
	this.orientation   = Orientation.VERTICAL;                             // select elements are displayed vertical or horizontal
	this.retainOrder   = true;                                             // move item at the end (false) or use the original position
	this.keepSelection = true;                                             // keep selection for moved options 
	this.PREFIX        = 'swc_';                                           // Prefix used for scripting variables
	this.BTNPREFIX     = 'btnswc_';                                        // Prefix for button names used by the control
	this.ROOT          = 'swc_span_'+ id;                                  // Name of the surrounding span
	this.clientHandler = new Array();                                      // Array for javascript client handlers
	this.multiple      = (this.groupsize > 1);                             // Multiple swapselect or not
	
	if (null != id) {
		this.optionsSrc = document.getElementById('swcl_' + id);
		this.optionsSrc.swc_type = 'L';
		this.optionsSel = new Array();

		// Array including the select elements with the selected items
		for (var i = 0; i < this.groupsize; i++) {
			var suffix = this.multiple ? i : '';
			var oSel = document.getElementById('swcr_' + id + suffix);
			oSel.swc_type = 'R';
			this.optionsSel[this.optionsSel.length] = oSel;
		}
		
		if (this.runAt == RunAt.CLIENT && !this.disabled) {
			// assign the client handlers		
			this.setupEventHandler();
		}
	}
}
function SwapSelect_isMultiple() {
	return this.groupsize > 0;
}
function SwapSelect_setRunAt(runat) {
	this.runAt = runat;
}
function SwapSelect_getRunAt() {
	return this.runAt;
}
function SwapSelect_setOrientation(orientation) {
	this.orientation = orientation;
}
function SwapSelect_getOrientation() {
	return this.orientation;
}
function SwapSelect_setDisabled(disabled) {
	// set attribute
	this.disabled = disabled;

	if (this.optionsSrc == null || this.optionsSel == null) {
		return;
	}

	if (this.disabled) {
		// disable control
		this.optionsSrc.disabled = true;
		
		for (var i=0; i < this.groupsize; i++) {
			this.optionsSel[i].disabled = true;
		}
		
		this.removeEventHandler();
	} else {
		// enable control
		this.optionsSrc.disabled = false;
		
		for (var i=0; i < this.groupsize; i++) {
			this.optionsSel[i].disabled = false;
		}
		
		this.setupEventHandler();
	}
}
function SwapSelect_isDisabled() {
	return this.disabled;
}
function SwapSelect_addClientHandler(type, script) {
	this.clientHandler[type] = script;
}
function SwapSelect_getClientHandler(type) {
	var handler = this.clientHandler[type];
	
	if (null != handler) {
		return handler;
	} else {
		return null;
	}
}
function SwapSelect_setupEventHandler() {

	// replace the handlers for all buttons
	for (var i=0; i < this.groupsize; i++) {
		var suffix = this.multiple ? i : '';
	
		var objAddAll    = document.getElementById(this.BTNPREFIX + this.id + '_AddAll' + suffix);
		var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add' + suffix);
		var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove' + suffix);
		var objRemoveAll = document.getElementById(this.BTNPREFIX + this.id + '_RemoveAll' + suffix);
		var objMoveUp    = document.getElementById(this.BTNPREFIX + this.id + '_MoveUp' + suffix);
		var objMoveDown  = document.getElementById(this.BTNPREFIX + this.id + '_MoveDown' + suffix);

		var ctrl = this;

		if (null != objAddAll) {
			objAddAll.index = i;
			objAddAll.onclick = function() {
								var self = this;
								var changed = ctrl.doSwap(ctrl.optionsSrc, ctrl.optionsSel[self.index], true);
								
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
								
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								} else {
									return false;
								}
							}
		}
		
		if (null != objAdd) {
			objAdd.index = i;
			objAdd.onclick = function() {
								var self = this;
								
								var rtc=  ctrl.onBeforeSwap(ctrl.optionsSrc, ctrl.optionsSel[self.index], 'R') ;
								if (!rtc) return false;
								
								var changed = ctrl.doSwap(ctrl.optionsSrc, ctrl.optionsSel[self.index], false);
		
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								} else {
									return false;
								}
							}
		}
					
		if (null != objRemove) {	
			objRemove.index = i;
			objRemove.onclick   = function() {
								var self = this;
								
								var rtc=  ctrl.onBeforeSwap(ctrl.optionsSel[self.index], ctrl.optionsSrc, 'L') ;
								if (!rtc) return false;
								
								var changed = ctrl.doSwap(ctrl.optionsSel[self.index], ctrl.optionsSrc, false);
	
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								} else {
									return false;
								}
							}
		}
		
		if (null != objRemoveAll) {
			objRemoveAll.index = i;
			objRemoveAll.onclick = function() {
								var self = this;
								var changed = ctrl.doSwap(ctrl.optionsSel[self.index], ctrl.optionsSrc, true);
	
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								} else {
									return false;
								}
							}
		}
		
		if (null != objMoveUp) {
			objMoveUp.index = i;					
			objMoveUp.onclick = function() {
								var self = this;
								var changed = ctrl.doMove(ctrl.optionsSel[self.index], Direction.UP);
	
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								}
								
								return false;
							}
		}
		
		if (null != objMoveDown) {
			objMoveDown.index = i;
			objMoveDown.onclick = function() {
								var self = this;
								var changed = ctrl.doMove(ctrl.optionsSel[self.index], Direction.DOWN);
	
								if (changed) {
									ctrl.updateHiddenFields(ctrl.optionsSel[self.index], self.index);
	
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									return CCUtility.executeUserScript(userfct);
								}
								
								return false;
							}
		}
		
		this.optionsSel[i].onclick = function() {	
								ctrl.unselectOptionList(ctrl.optionsSrc);
							}
	}
	
	this.optionsSrc.onclick = function() {
						for (var group=0; group < ctrl.groupsize; group++) {
							ctrl.unselectOptionList(ctrl.optionsSel[group]);
						}
					}
	
}
function SwapSelect_removeEventHandler() {

	for (var i=0; i < this.groupsize; i++) {
		var suffix = this.multiple ? i : '';

		var objAddAll    = document.getElementById(this.BTNPREFIX + this.id + '_AddAll' + suffix);
		var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add' + suffix);
		var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove' + suffix);
		var objRemoveAll = document.getElementById(this.BTNPREFIX + this.id + '_RemoveAll' + suffix);
		var objMoveUp    = document.getElementById(this.BTNPREFIX + this.id + '_MoveUp' + suffix);
		var objMoveDown  = document.getElementById(this.BTNPREFIX + this.id + '_MoveDown' + suffix);
	
		objAddAll.onclick = function(){return false;};
		objAdd.onclick    = function(){return false;};
		objRemove.onclick = function(){return false;};
		objRemoveAll.onclick = function(){return false;};
		
		if (null != objMoveUp) {
			objMoveUp.onclick = function(){return false;};
		}
		
		if (null != objMoveDown) {
			objMoveDown.onclick = function(){return false;};
		}
	}

}
function SwapSelect_unselectOptionList(optionList) {
	// unselect option elements
	var options =  optionList.options;
	for (var i=0; i < options.length; i++) {
		options[i].selected = false;
	}
}
function SwapSelect_doMove(objSelect, direction) {
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
		objSelect.options[currIndex]                    = new Option(target.text, target.value, false, false);
		objSelect.options[currIndex].swc_index          = target.getAttribute('swc_index');
		objSelect.options[currIndex + offset]           = new Option(source.text, source.value, false, false);
		objSelect.options[currIndex + offset].swc_index = source.getAttribute('swc_index');
		
		// select the options again
		objSelect.options[currIndex + offset].selected = true;
		
		changed = true;
	}
	
	return changed;
}
function SwapSelect_onBeforeSwap(source, target, direction) {
	return true;
}
function SwapSelect_doSwap(source, target, moveall) {
	// flag indicates an option has been moved
	var flagChanged = false;
	
	if (null == source || null == target) {
		return flagChanged;
	}

	if (this.keepSelection) {
		this.unselectOptionList(target);
	}

	if (moveall) {
		// copy all options
		var opt = source.options;

		for (var i=0; i < opt.length; i++) {
			var newOption = new Option(opt[i].text, opt[i].value, false, this.keepSelection);
			newOption.swc_index  = opt[i].getAttribute('swc_index');
			target.options[target.options.length] = newOption;
			flagChanged = true;
		}

		// delete all options from the source element
		source.options.length = 0;
		
	} else {
		// copy only the selected options
		while (source.selectedIndex != -1) {
			var opt = source.options[source.selectedIndex];
			
			// delete the selected option from the source element
			source.options[source.selectedIndex] = null;
				
			// insert the selected option into the target element
			var newOption =  new Option(opt.text, opt.value, false, this.keepSelection);
			newOption.swc_index  = opt.getAttribute('swc_index');
			target.options[target.options.length] = newOption;
				
			flagChanged = true;
		}
	}

	if (flagChanged) {
		this.doSort(target);
	}	
	
	return flagChanged;
}
function SwapSelect_doSort(optionList) {

	if (null == optionList) {
		return;
	}

	if (!this.retainOrder && optionList.swc_type == 'L') {
		return;
	}
	
	if (!this.retainOrder && this.sortOrder == SortOrder.NONE) {
		return;
	}

	var arr  = new Array();
	
	// add options to array
	for(var i=0; i < optionList.options.length; i++) {
		arr[arr.length] = optionList.options[i];
	}
	
	// Detect the sort Algorithm
	var functionSort = null;
	
	if (this.retainOrder && this.sortOrder == SortOrder.NONE) {
		functionSort = SwapSelect_sortIndex;
	} else if (!this.retainOrder && this.sortOrder == SortOrder.ASC) {
		functionSort = SwapSelect_sortASC;
	} else if (!this.retainOrder && this.sortOrder == SortOrder.DESC) {
		functionSort = SwapSelect_sortDESC;
	}
	
try {
	// sort the array
	arr.sort(functionSort);
} catch(e) {
//TODO
}
	// reset select element
	optionList.options.length = 0;
	
	// add sorted options
	for (var i=0; i < arr.length; i++) {
		var opt = arr[i];
		var newOption = new Option(opt.text, opt.value, false, opt.selected);
		newOption.swc_index = opt.getAttribute('swc_index');
	//		newOption.selected  = arr[i].selected;
		optionList.options[optionList.options.length] = newOption;
	}
}
function SwapSelect_sortIndex(a, b) {
	a = a.getAttribute('swc_index');
	b = b.getAttribute('swc_index');
	var va = parseInt(a);
	var vb = parseInt(b);

	if (va > vb) {
		return 1;
	} else if (va < vb) {
		return -1;
	} else {
		return 0;
	}
}
function SwapSelect_sortASC(a, b) {
	a = a.text.toUpperCase(); b = b.text.toUpperCase();

	if (a < b) {
		return -1;
	} else if (a > b) {
		return 1;
	} else {
		return 0;
	}
}
function SwapSelect_sortDESC(a, b) {
	a = a.text.toUpperCase(); b = b.text.toUpperCase();
	
	if (a < b) {
		return 1;
	} else if (a > b) {
		return -1;
	} else {
		return 0;
	}
}
function SwapSelect_getOptionList(side) {
	if (side.toUpperCase() == 'L') {
		return this.optionsSrc;
	} else if (side.toUpperCase() == 'R') {
		return this.optionsSel;
	} else {
		return null;
	}
}
function SwapSelect_setRetainOrder(retainOrder) {
	this.retainOrder = retainOrder;
}
function SwapSelect_isRetainOrder() {
	return this.retainOrder;
}
function SwapSelect_setKeepSelection(keepSelection) {
	this.keepSelection = keepSelection;
}
function SwapSelect_isKeepSelection() {
	return this.keepSelection;
}
function SwapSelect_updateHiddenFields(optionList, groupindex) {
	// This function creates the required hidden fields
	// which are used to synchronize the selected items
	// after a server round trip.

	var span       = document.getElementById(this.ROOT);
	var suffix     = this.multiple ? '[' + groupindex + ']' : '';
	var hiddenName = this.hiddenName + suffix;

	// remove existing hidden fields
	var inputfields = span.getElementsByTagName('input');
	var length = inputfields.length;
	
	for (var i = length - 1; i >= 0; i--) {
		var node = inputfields[i];
		if (node.type == 'hidden' && node.name == hiddenName) {
			//alert(i + ") " + inputfields[i].value);
			var parent = node.parentNode;
			parent.removeChild(node);
		}
	}

	// add the new hidden fields for the selected options
	if (optionList.options.length != 0) {
		for (var i=0; i < optionList.options.length; i++) {
			var option = optionList.options[i];
			//alert(hiddenName + ' ' +  option.text);
			var hidden = this.createHidden(hiddenName, option.value);
			span.appendChild(hidden);
		}
	} else {
		// we must create a dummy hidden field otherwise
		// the list will not be reseted because the parameter
		// is missing in the http request
		var hidden = this.createHidden(hiddenName, '');
		span.appendChild(hidden);
	}
}
function SwapSelect_createHidden(hiddenName, value) {
	var input   = document.createElement('input');
	input.type  = 'hidden';
	input.name  = hiddenName;
	input.value = value;
	return input;
}
function SwapSelect_toString() {
	var out = '';
	out += '******* SwapSelect *********' + LF;
	out += 'Id..............: ' + this.id + LF;
	out += 'Orientation.....: ' + this.orientation + LF;
	out += 'Retain Order....: ' + this.retainOrder + LF;
	out += 'Keep Selection..: ' + this.keepSelection + LF;
	out += 'Disabled........: ' + this.disabled + LF;
	out += 'RunAt...........: ' + this.runAt + LF;
	return out;
}
new SwapSelect();
SwapSelect.prototype.isMultiple         = SwapSelect_isMultiple;
SwapSelect.prototype.setRunAt           = SwapSelect_setRunAt;
SwapSelect.prototype.getRunAt           = SwapSelect_getRunAt;
SwapSelect.prototype.setOrientation     = SwapSelect_setOrientation;
SwapSelect.prototype.getOrientation     = SwapSelect_getOrientation;
SwapSelect.prototype.setDisabled        = SwapSelect_setDisabled;
SwapSelect.prototype.isDisabled         = SwapSelect_isDisabled;
SwapSelect.prototype.setupEventHandler  = SwapSelect_setupEventHandler;
SwapSelect.prototype.removeEventHandler = SwapSelect_removeEventHandler;
SwapSelect.prototype.unselectOptionList = SwapSelect_unselectOptionList;
SwapSelect.prototype.doMove             = SwapSelect_doMove;
SwapSelect.prototype.onBeforeSwap       = SwapSelect_onBeforeSwap;
SwapSelect.prototype.doSwap             = SwapSelect_doSwap;
SwapSelect.prototype.doSort             = SwapSelect_doSort;
SwapSelect.prototype.getOptionList      = SwapSelect_getOptionList;
SwapSelect.prototype.setRetainOrder     = SwapSelect_setRetainOrder;
SwapSelect.prototype.isRetainOrder      = SwapSelect_isRetainOrder;
SwapSelect.prototype.setKeepSelection   = SwapSelect_setKeepSelection;
SwapSelect.prototype.isKeepSelection    = SwapSelect_isKeepSelection;
SwapSelect.prototype.updateHiddenFields = SwapSelect_updateHiddenFields;
SwapSelect.prototype.createHidden       = SwapSelect_createHidden;
SwapSelect.prototype.addClientHandler   = SwapSelect_addClientHandler;
SwapSelect.prototype.getClientHandler   = SwapSelect_getClientHandler;
SwapSelect.prototype.toString           = SwapSelect_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object...: TextList
| Function.: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 21.02.2007  G.Schulz          Inital version
+ ---------------------------------------------------------------------------------+
*/
function TextList(id, runat, hiddenName, disabled, sortOrder, multiple) {
	var a = arguments;
	
	this.id            = id;                                               // the id for the control
	this.runAt         = (a.length >= 2) ? runat : RunAt.SERVER;           // indicates if the control should work with or without round trips
	this.hiddenName    = (a.length >= 3) ? hiddenName : id;                // Name of the hidden field
	this.disabled      = (a.length >= 4) ? disabled : false;
	this.sortOrder     = (a.length >= 5) ? sortOrder : SortOrder.NONE;    // SortOrder
	this.multiple      = (a.length >= 6) ? multiple : true;
	this.PREFIX        = 'txtlc_';
	this.BTNPREFIX     = 'btntxtlc_';
	this.ROOT          = 'txtlc_span_'+ id;
	this.clientHandler = new Array();

	if (null != id) {
		this.objInput  = document.getElementById('txtlc_' + id);			// the input element
		this.objSelect = document.getElementById('txtlcoptions_' + id);		// the select element
		this.nextValue = this.objSelect.options.length;
		
		if (ie) {
			var ieVersion = Browser.extractVersion();
			var isIE6 = (ieVersion.charAt(0) == '6');
			if (isIE6) {
				this.multiple = false; // BUG in IE6.0 with selecedIndex if multiple is true
			}
		}
		
		if (this.multiple) {
			this.objSelect.multiple = true;
		}
		
		this.setupEventHandler();
	}
}
function TextList_setupEventHandler() {
	// get the buttons
	var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add');
	var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove');
	var objMoveUp    = document.getElementById(this.BTNPREFIX + this.id + '_MoveUp');
	var objMoveDown  = document.getElementById(this.BTNPREFIX + this.id + '_MoveDown');

	var ctrl = this;

	objAdd.onclick = function() {
								var value = ctrl.objInput.value;

								if (value == null || value.trim() == '') {
									return false;
								} 
								else if (!ctrl.isExistOption(value)) {
									ctrl.nextValue ++;
									var newOption = new Option(value.trim(), ctrl.nextValue, false, false);
									var nextIndex = ctrl.objSelect.options.length;
									ctrl.objSelect.options[nextIndex] = newOption;
									ctrl.updateHiddenFields(ctrl.objSelect);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									CCUtility.executeUserScript(userfct);
try {
									ctrl.doSort(ctrl.objSelect, ctrl.sortOrder);
} catch  (e) {
// FIXME Problem IE6 and sortfunction
//	alert(e.message);
}
								}
								
								ctrl.objInput.value = '';
								ctrl.objInput.focus();
								
								return false;

							};

	objRemove.onclick = function() {
								var changed = false;
								
								// remove the selected options
								while (ctrl.objSelect.selectedIndex != -1) {
									ctrl.objSelect.options[ctrl.objSelect.selectedIndex] = null;
									changed = true;
								}
								
								ctrl.objInput.value = '';
								
								if (changed) {
									ctrl.updateHiddenFields(ctrl.objSelect);
									
									var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
									CCUtility.executeUserScript(userfct);
								}
								
								return false;
							};

	if (null != objMoveUp) {
		objMoveUp.onclick = function() {
								var changed = ctrl.doMove(ctrl.objSelect, Direction.UP);
								
								if (changed) {
									ctrl.updateHiddenFields(ctrl.objSelect);
								}

								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								CCUtility.executeUserScript(userfct);
						
								return false;
							};
	}

	if (null != objMoveDown) {
		objMoveDown.onclick = function() {
								var changed = ctrl.doMove(ctrl.objSelect, Direction.DOWN);
	
								if (changed) {
									ctrl.updateHiddenFields(ctrl.objSelect);
								}

								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								CCUtility.executeUserScript(userfct);
						
								return false;
							};
	}
	
	this.objSelect.onclick = function() {
								var self = this;
								if (self.options.length > 0 && self.selectedIndex >= 0) {
									var selOption = self.options[this.selectedIndex];
									ctrl.objInput.value = selOption.text;
								}
							};
	
}

function TextList_removeEventHandler() {
	var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add');
	var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove');
	var objMoveUp    = document.getElementById(this.BTNPREFIX + this.id + '_MoveUp');
	var objMoveDown  = document.getElementById(this.BTNPREFIX + this.id + '_MoveDown');

	objAdd.onclick      = function(){return false;};
	objRemove.onclick   = function(){return false;};
	objMoveUp.onclick   = function(){return false;};
	objMoveDown.onclick = function(){return false;};
}
function TextList_doMove(objSelect, direction) {
	return OptionUtil.doMoveOption(objSelect, direction);
}
function TextList_isExistOption(text) {
	var arr = this.objSelect.options;
	var rtc = false;
	
	for( var i=0; i < arr.length; i++) {
		if (arr[i].text == text) {
			rtc = true;
		}
	}
	return rtc;
}
function TextList_setDisabled(disabled) {
	// set attribute
	this.disabled = disabled;

	if (this.disabled) {
		// disable control
		this.objInput.disabled = true;
		this.objSelect.disabled = true;
		this.removeEventHandler();
	} else {
		// enable control
		this.objInput.disabled  = false;
		this.objSelect.disabled = false;
		this.setupEventHandler();
	}
}
function TextList_isDisabled() {
	return this.disabled;
}
function TextList_updateHiddenFields(optionList) {
	// This function creates the required hidden fields
	// which are used to synchronize the selected items
	// after a server round trip.
	

	var span = document.getElementById(this.ROOT);

	// remove existing hidden fields
	var inputfields = span.getElementsByTagName('input');
	var length = inputfields.length;
	
	for (var i = length - 1; i >= 0; i--) {
		var node = inputfields[i];
		
		if (node.type == 'hidden' && node.name == this.hiddenName) {
			//alert(i + ") " + inputfields[i].value);
			var parent = node.parentNode;
			parent.removeChild(node);
		}
	}

	// add the new hidden fields for the selected options
	if (optionList.options.length != 0) {
		for (var i=0; i < optionList.options.length; i++) {
			var option = optionList.options[i];
			var hidden = this.createHidden(option.text);
			span.appendChild(hidden);
		}
	} else {
		// we must create a dummy hidden field otherwise
		// the list will not be reseted because the parameter
		// is missing in the http request
		var hidden = this.createHidden('');
		span.appendChild(hidden);
	}
}
function TextList_createHidden(value) {
	var input   = document.createElement('input');
	input.type  = 'hidden';
	input.name  = this.hiddenName;
	input.value = value;
	return input;
}
function TextList_addClientHandler(type, script) {
	this.clientHandler[type] = script;
}
function TextList_getClientHandler(type) {
	var handler = this.clientHandler[type];
	
	if (null != handler) {
		return handler;
	} else {
		return null;
	}
}
function TextList_doSort(optionList, sortOrder) {

	if (sortOrder == SortOrder.NONE) {
		return;
	}
	
	var arr = new Array();
	
	// add options to array
	var options = optionList.options;
	
	for (var i=0; i < options.length; i++) {
		arr[arr.length] = options[i];
	}
	
	// sort the array
	if (sortOrder == SortOrder.ASC) {
		arr.sort(TextList_sortASC);
	} 
	else if (sortOrder == SortOrder.DESC) {
		arr.sort(TextList_sortDESC);
	}
	
	// reset select element
	optionList.options.length = 0;

	// add sorted options
	for (var i=0; i < arr.length; i++) {
		var newOption = new Option(arr[i].text, arr[i].value, false, false);
		optionList.options[optionList.options.length] = newOption;
	}
}
function TextList_sortASC(a, b) {
	a = a.text.toUpperCase(); 
	b = b.text.toUpperCase();

	if (a < b) {
		return -1;
	} else if (a > b) {
		return 1;
	} else {
		return 0;
	}
}
function TextList_sortDESC(a, b) {
	a = a.text.toUpperCase();
	b = b.text.toUpperCase();
	
	if (a < b) {
		return 1;
	} else if (a > b) {
		return -1;
	} else {
		return 0;
	}
}
function TextList_toString() {
	var out = '';
	out += '******* TextList *********' + LF;
	out += 'Id..............: ' + this.id + LF;
	out += 'Disabled........: ' + this.disabled + LF;
	out += 'RunAt...........: ' + this.runAt + LF;
	return out;
}
new TextList();
TextList.prototype.setupEventHandler  = TextList_setupEventHandler;
TextList.prototype.removeEventHandler = TextList_removeEventHandler;
TextList.prototype.doMove             = TextList_doMove;
TextList.prototype.isExistOption      = TextList_isExistOption;
TextList.prototype.setDisabled        = TextList_setDisabled;
TextList.prototype.isDisabled         = TextList_isDisabled;
TextList.prototype.updateHiddenFields = TextList_updateHiddenFields;
TextList.prototype.createHidden       = TextList_createHidden;
TextList.prototype.addClientHandler   = TextList_addClientHandler;
TextList.prototype.getClientHandler   = TextList_getClientHandler;
TextList.prototype.doSort             = TextList_doSort;
TextList.prototype.toString           = TextList_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object...: Button
| Function.: used to manage text buttons
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 14.05.2005  G.Schulz          Inital version
| 26.02.2006  G.Schulz          Support Image/Text-Buttons added
+ ---------------------------------------------------------------------------------+
*/
function Button(id, buttonType, target) {
	var a = arguments;
	this.id			    = id;
	this.buttonType     = (a.length >= 2) ? buttonType : ButtonType.IMAGE;
	this.target         = (a.length >= 3) ? target : null;
	this.onclickHandler = null;
	this.clientHandler  = new Array();
	
	return this;
}
function Button_doDisableImage(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');
	var imgtype = tokens[tokens.length - 1];

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('1.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '2.' + imgtype);
		}
	}
}
function Button_doEnableImage(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');
	var imgtype = tokens[tokens.length - 1];

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('2.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '1.' + imgtype);
		}
	}
}
function Button_getButton() {
	return document.getElementById(this.id);
}
function Button_disable() {
	var node = this.getButton();

	if (null == node) {
		return;
	}

	this.onclickHandler = node.onclick;
	node.onclick = '';

	if (this.isImageButton()) {
		this.doDisableImage(node);
	}
	else if (this.isTextButton()) {
		var td = node.getElementsByTagName('td');

		// left image
		this.doDisableImage(td[0].firstChild);
	
		// middle image
		this.doDisableImage(td[1], 'background');
				
		// right image
		this.doDisableImage(td[2].firstChild);
		
		var span = document.getElementById(this.id + 'Label');
		span.style.color = '#CECECE';
	}
}
function Button_enable() {
	var node = this.getButton();

	if (null == node) {
		return;
	}

	if (null != this.onclickHandler) {
		node.onclick = this.onclickHandler;
	}
	
	if (this.isImageButton()) {
		this.doEnableImage(node);
	}
	else if (this.isTextButton()) {
		var td = node.getElementsByTagName('td');

		// left image
		this.doEnableImage(td[0].firstChild);
		
		// middle image
		this.doEnableImage(td[1], 'background');
					
		// right image
		this.doEnableImage(td[2].firstChild);
		
		var span = document.getElementById(this.id + 'Label');
		span.style.color = '#000000';
	}
}
function Button_hide() {
	var div = this.getButton();

	if (null == div) {
		return;
	}
	
	div.style.display = 'none';
}
function Button_show() {
	var div = this.getButton();

	if (null == div) {
		return;
	}
	
	div.style.display = 'block';
}
function Button_submit() {

	// This button should submit the form
	// so we have to set the 'clicked' value
	// for the hidden field. If there was a
	// target-attribute specified for the form-element
	// the hidden field needs to be reseted after
	// the form was submitted. Otherwise the hidden
	// filed can become part of the next request and
	// can trigger a wrong function

	if (this.isTextButton()) {

		if (this.buttonType == ButtonType.USERDEF) {
			var userfct = this.getClientHandler(ClientEvent.ONCLICK);
			var rtc = CCUtility.executeUserScript(userfct);
			if (!rtc) return false;
		}

		var node = document.getElementById(this.id);
		var form = CCUtility.getEnclosingForm(node);

		if (form != null) {
			var oldTarget = form.target;

			var hidden = this.createHidden();

			if (this.getTarget() != null) {
				form.target = this.getTarget();
			}
			if (this.buttonType == ButtonType.RESET) {
				CCUtility.resetEnclosingForm(form);
			}

			if (!ie) {
				form.appendChild(hidden);
				CCUtility.submitEnclosingForm(form);
				form.removeChild(hidden);
			} 
			else { // removeChild bug in ie6
				var div = document.createElement('div');				
				div.style.display = 'none';
				div.appendChild(hidden);
				form.appendChild(div);
				CCUtility.submitEnclosingForm(form);
				div.innerHTML = '';
			}
	
			if (this.getTarget() != null) {
				form.target = oldTarget;
			}
			
			/*
			if (form.target != null && form.target != '') {
				form.removeChild(hidden);
			}*/
		}
	}
}
function Button_createHidden() {
	var input   = document.createElement('input');
	input.type  = 'hidden';
	input.id    = this.id + 'Hidden';
	
	if (this.buttonType == ButtonType.CANCEL) {
		// send the struts cancel property in the request
		input.name  = Globals.STRUTS_CANCEL_KEY;
	} else {
		// the framework needs the 'btn' prefix to recognize
		// button click actions
		if ((this.id != null) && (this.id.indexOf('btn') != 0)) {
			input.name  = 'btn' + this.id;
		} else {
			input.name  = this.id;
		}
	}
	input.value = 'clicked';
	return input;
}
function Button_isTextButton() {
	return (this.buttonType !=  ButtonType.IMAGE);
}
function Button_isImageButton() {
	return (this.buttonType ==  ButtonType.IMAGE);
}
function Button_getButtonType() {
	return this.buttonType;
}
function Button_getTarget() {
	return (this.target != null && this.target.trim().length > 0) ? this.target : null;
}
function Button_addClientHandler(type, script) {
	this.clientHandler[type] = script;
	return this;
}
function Button_getClientHandler(type) {
	var handler = this.clientHandler[type];
	
	if (null != handler) {
		return handler;
	} else {
		return null;
	}
}
function Button_toString() {
	var out = '';
	out += '******* Button *********' + LF;
	out += 'Id..............: ' + this.id + LF;
	out += 'ButtonType......: ' + this.buttonType + LF;
	return out;
}
new Button();
Button.prototype.doDisableImage    = Button_doDisableImage;
Button.prototype.doEnableImage     = Button_doEnableImage;
Button.prototype.getButton         = Button_getButton;
Button.prototype.disable           = Button_disable;
Button.prototype.enable            = Button_enable;
Button.prototype.hide              = Button_hide;
Button.prototype.show              = Button_show;
Button.prototype.submit            = Button_submit;
Button.prototype.createHidden      = Button_createHidden;
Button.prototype.isTextButton      = Button_isTextButton;
Button.prototype.isImageButton     = Button_isImageButton;
Button.prototype.getButtonType     = Button_getButtonType;
Button.prototype.getTarget         = Button_getTarget;
Button.prototype.addClientHandler  = Button_addClientHandler;
Button.prototype.getClientHandler  = Button_getClientHandler;
Button.prototype.toString          = Button_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object...: StringHelp
| Function.: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 08.01.2005  G.Schulz          Inital version
|
+ ---------------------------------------------------------------------------------+
*/

function StringHelp() {
}
function StringHelp_truncate(str, maxlength) {
	if ((maxlength == -1) || (str == null) || (str.length <= maxlength)) {
		return str;
	} else {
		return str.substring(0, maxlength) + '...';
	}
}
new StringHelp();
StringHelp.truncate = StringHelp_truncate;


/*
+ ---------------------------------------------------------------------------------+
| Object...: RecurrenceControl
| Function.: JavaScript Object to manage a RecurrenceControl
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 26.06.2005  G.Schulz          Inital version
|
+ ---------------------------------------------------------------------------------+
*/
function RecurrenceControl(id, prefix) {
	this.id         = id;
	this.prefix     = prefix;
	
	if (null != id) {
		this.divNone    = document.getElementById(id + 'none');
		this.divDaily   = document.getElementById(id + 'daily');
		this.divWeekly  = document.getElementById(id + 'weekly')
		this.divMontly  = document.getElementById(id + 'monthly')
		this.divYearly  = document.getElementById(id + 'yearly');
		
		// setup handlers
		this.setupHandler();
	}
}
function RecurrenceControl_setupHandler() {
	var ctrl = this;
	var type = document.getElementsByName(this.prefix + '.type');

	for (var i=0; i < type.length; i++) {
		type[i].onclick = function() {ctrl.showRecurringType(this.value);}
	}

	// ---------------------------------
	// Handler Daily
	// ---------------------------------
	var dayDayMask  = document.getElementsByName(this.prefix + '.dayDayMask')[0];
	var dayInterval = document.getElementsByName(this.prefix + '.dayInterval')[0];
	
	dayInterval.onclick = function() { dayDayMask.checked = true;}
	dayInterval.onchange = function() { dayDayMask.checked = true;}
	
	// ---------------------------------
	// Handler Daily
	// ---------------------------------
	var monthSubtype = document.getElementsByName(this.prefix + '.monthSubtype');
	var monthDayOfMonth = document.getElementsByName(this.prefix + '.monthDayOfMonth')[0];
	var monthInterval = document.getElementsByName(this.prefix + '.monthInterval')[0];
	var monthNthInterval = document.getElementsByName(this.prefix + '.monthNthInterval')[0];
	var monthNthInstance = document.getElementsByName(this.prefix + '.monthNthInstance')[0];
	var monthNthDayMask = document.getElementsByName(this.prefix + '.monthNthDayMask')[0];
	
	monthDayOfMonth.onclick = function() {monthSubtype[0].checked = true;}
	monthDayOfMonth.onchange = function() {monthSubtype[0].checked = true;}
	monthInterval.onclick = function() {monthSubtype[0].checked = true;}
	monthInterval.onchange = function() {monthSubtype[0].checked = true;}
	monthNthInterval.onclick = function() {monthSubtype[1].checked = true;}
	monthNthInterval.onchange = function() {monthSubtype[1].checked = true;}
	monthNthInstance.onchange = function() {monthSubtype[1].checked = true;}
	monthNthDayMask.onchange = function() {monthSubtype[1].checked = true;}
	
	// ---------------------------------
	// Handler Yearly
	// ---------------------------------
	var yearSubtype = document.getElementsByName(this.prefix + '.yearSubtype');
	var yearDayOfMonth = document.getElementsByName(this.prefix + '.yearDayOfMonth')[0];
	var yearMonthOfYear = document.getElementsByName(this.prefix + '.yearMonthOfYear')[0];
	var yearNthInstance = document.getElementsByName(this.prefix + '.yearNthInstance')[0];
	var yearNthDayMask = document.getElementsByName(this.prefix + '.yearNthDayMask')[0];
	var yearNthMonthOfYear = document.getElementsByName(this.prefix + '.yearNthMonthOfYear')[0];
	
	yearDayOfMonth.onclick = function() {yearSubtype[0].checked = true;}
	yearDayOfMonth.onchange = function() {yearSubtype[0].checked = true;}
	yearMonthOfYear.onchange = function() {yearSubtype[0].checked = true;}
	yearNthInstance.onchange = function() {yearSubtype[1].checked = true;}
	yearNthDayMask.onchange = function() {yearSubtype[1].checked = true;}
	yearNthMonthOfYear.onchange = function() {yearSubtype[1].checked = true;}
}
function RecurrenceControl_showRecurringType(type) {
	// initialize
	this.reset();
	
	// switch
	this.divNone.style.display = 'none';
	this.divDaily.style.display = 'none';
	this.divWeekly.style.display = 'none';
	this.divMontly.style.display = 'none';
	this.divYearly.style.display = 'none';
	
	var div = document.getElementById(this.id + type);
	div.style.display = 'block';
}
function RecurrenceControl_reset() {
	// ---------------------------------
	// reset Daily
	// ---------------------------------
	document.getElementsByName(this.prefix + '.dayDayMask')[0].checked = true;
	document.getElementsByName(this.prefix + '.dayInterval')[0].value = 1;

	// ---------------------------------
	// reset Weekly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.weekInterval')[0].value = 1;
	var chekboxes = document.getElementsByTagName('input');
	
	for (var i=0; i < chekboxes.length; i++) {
		if (chekboxes[i].type == 'checkbox') {
			chekboxes[i].checked = false;
		}
	}
	
	// ---------------------------------
	// reset Monthly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.monthSubtype')[0].checked = true;
	document.getElementsByName(this.prefix + '.monthDayOfMonth')[0].value = 0;
	document.getElementsByName(this.prefix + '.monthInterval')[0].value = 1;
	document.getElementsByName(this.prefix + '.monthNthInterval')[0].value = 1;
	document.getElementsByName(this.prefix + '.monthNthInstance')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.monthNthDayMask')[0].selectedIndex = 0;
	
	// ---------------------------------
	// reset Yearly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.yearSubtype')[0].checked = true;
	document.getElementsByName(this.prefix + '.yearDayOfMonth')[0].value = 1;
	document.getElementsByName(this.prefix + '.yearMonthOfYear')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthInstance')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthDayMask')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthMonthOfYear')[0].selectedIndex = 0;
}

new RecurrenceControl();
RecurrenceControl.prototype.reset             = RecurrenceControl_reset;
RecurrenceControl.prototype.showRecurringType = RecurrenceControl_showRecurringType;
RecurrenceControl.prototype.setupHandler      = RecurrenceControl_setupHandler;


/*
+ ---------------------------------------------------------------------------------+
| Array to manage all popup-windows on a page

| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/

var popups = new Array();

/*
+ ---------------------------------------------------------------------------------+
| Object....: PopUp
| Function..: Provides a popup window
| Arguments.: id, referrer, width, height
| 
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function PopUp(id, referer, width, height) {
	this.id              = id;                  // Id for the popup window
	this.referer         = referer;             // opener
	this.width           = width;               // the width for the popup window
	this.height          = height;              // the height for the popup window
	this.popupTimeCount  = 500;                 // Time when the popup should be closed
	this.popupTimerID    = null;                // unique timer Id
	this.iframe          = null;                // IFrame used to create this popup
	this.div             = null;                // Div used to create this popup
	this.oldHandler      = null;                // old javascript handler 
	this.PREFIX          = 'popup_';

	if (null != id) {
		this.init();
		this.setupHandler();
		
		popups[id] = this;
	}
}
function PopUp_open() {

	// create div
	var x = 0;
	var y = 0;

	if (ie || opera) {
		x = getClientX(event) + document.body.scrollLeft;
		y = getClientY(event) + document.body.scrollTop;
	} else {
		x = this.referer.x;
		y = this.referer.y + this.referer.offsetHeight + 1;
	}

	// adjust x position of popup window
	var maxX		= document.body.clientWidth + document.body.scrollLeft;
	var popupWidth	= (null != this.width) ? this.width : 190;

	if ((x + popupWidth) > maxX) {
		x = x + (maxX - x - popupWidth);
	}

	if (x < 0) {
		x = 0;
	}

	// adjust y position of popup window
	var maxY		= document.body.clientHeight + document.body.scrollTop;
	var popupHeight	= (null != this.height) ? this.height : 190 + 20;

	if ((y + popupHeight) > maxY) {
		y = y + (maxY - y - popupHeight);
	}

	if (y < 0) {
		y = 0;
	}


	this.div.style.left     = x;
	this.div.style.top      = y;
	this.div.style.zIndex   = 99;
	this.div.style.width    = popupWidth;
	this.div.style.height   = popupHeight;
	this.div.innerHTML      = '';
	
	// create iframe
	if (ie) {
		this.iframe.style.left   = this.div.style.left;
		this.iframe.style.top    = this.div.style.top;
		this.iframe.style.width  = this.div.style.width;
		this.iframe.style.height = this.div.style.height;
		this.iframe.style.zIndex = this.div.style.zIndex - 1;
	}
	
	// show
	this.show(true);
}
function PopUp_close() {
	this.show(false);
	
	var body = document.getElementsByTagName('body')[0];
	
	// assign old handler
	if ( null != this.referer && null != this.referer.onclick && null != this.oldHandler) {
		this.referer.onclick = this.oldHandler;
	}
	
	// remove popup
	body.removeChild(this.div);
	body.removeChild(this.iframe);
}
function PopUp_show(flag) {
	var style = (flag) ? 'block' : 'none';

	this.div.style.display    = style;
	
	if (ie) {
		this.iframe.style.display = style;
	}
}
function PopUp_resize() {
	if (ie) {
		this.iframe.style.width  = this.div.style.width;
		this.iframe.style.height = this.div.style.height;
	}
}
function PopUp_init() {
	var body = document.getElementsByTagName('body')[0];
	
	this.div = document.createElement('DIV');
	this.div.id = this.PREFIX + 'div_' + this.id;
	this.div.style.position = 'absolute';
	this.div.style.left = 0;
	this.div.style.top = 0;
	this.div.style.backgroundColor = '#EFEFEF';
	this.div.style.border = '2px outset gray';
	this.div.style.display = 'none';
	body.appendChild(this.div);
	
	if (ie) {
		this.iframe = document.createElement('IFRAME');
		this.iframe.id = this.PREFIX + 'iframe_' + this.id;
		this.iframe.style.position = 'absolute';
		this.iframe.style.left = 0;
		this.iframe.style.top = 0;
		this.iframe.style.display = 'none';
		body.appendChild(this.iframe);
	}
}
function PopUp_setupHandler() {
	this.oldHandler = this.referer.onclick;
	var _ctrl = this;

	this.referer.onclick = function(event) {
					if (_ctrl.isVisible()) {
						_ctrl.close();
					} else {
						_ctrl.open();
					}
					return false;
				} 
	
	this.div.onmouseover = function(event) {
					_ctrl.stopPopupTimer();
				}

	this.div.onmouseout  = function(event) {
					// start timer if mouse is moved
					// out of the popupwindow
					if (!_ctrl.isChildNode(event)) {
						_ctrl.startPopupTimer();
					}
				}
}
function PopUp_isVisible() {
	return (this.div.style.display == 'block');
}
function PopUp_startPopupTimer() {
	var callback = "PopUp_timerclose('" + this.id + "')";
	this.popupTimerID = setTimeout(callback, this.popupTimeCount);
}
function PopUp_stopPopupTimer() {
	if (this.popupTimerID != null) {
		clearTimeout(this.popupTimerID);
		this.popupTimerID = null;
	}
}
function PopUp_timerclose(id) {
	var popup = popups[id];
	
	if (null != popup) {
		popup.close();
		popups[id] = null;
	}
}
function PopUp_resetPopupTimer() {
	this.stopPopupTimer();
	this.startPopupTimer();
}
function PopUp_isChildNode(event) {
	var wevent = null;

	if (ie || opera) {
		wevent = window.event;
	}
	else if(ns || safari || firefox || mozilla) {
		wevent = event;
	}
	
	if (null == wevent) {
		return false;
	}
	
	var x = wevent.x;
	var y = wevent.y


	if (x > this.div.offsetLeft && x <  this.div.offsetLeft +  this.div.offsetWidth) {
		return true;
	}
	
	if (y > this.div.offsetTop && x <  this.div.offsetTop +  this.div.offsetHeight) {
		return true;
	}

	if (x < 0 || y <0) {
		return true;
	}

	return false;
}
function PopUp_getDiv() {
	return this.div;
}
new PopUp();
PopUp.prototype.open            = PopUp_open;
PopUp.prototype.close           = PopUp_close;
PopUp.prototype.show            = PopUp_show;
PopUp.prototype.resize          = PopUp_resize;
PopUp.prototype.init            = PopUp_init;
PopUp.prototype.isVisible       = PopUp_isVisible;
PopUp.prototype.setupHandler    = PopUp_setupHandler;
PopUp.prototype.startPopupTimer = PopUp_startPopupTimer;
PopUp.prototype.stopPopupTimer  = PopUp_stopPopupTimer;
PopUp.prototype.resetPopupTimer = PopUp_resetPopupTimer;
PopUp.prototype.isChildNode     = PopUp_isChildNode;
//PopUp.prototype.timerclose      = PopUp_timerclose;

//************************************
// Common Controls JavaScript Library
//************************************

//namespace placeholder
CC = function() {

	// +------------------------------------------------------------------------
	// | Function.: init()
	// +------------------------------------------------------------------------
	this.init = function() {
		// initialize event handlers for various mouse events
		this.setupEventHandler();
	}

	// -------------------------------------------------------------------------
	// Purpose....: Captures the events needed by the common controls framework
	//              and registers the Eventhandlers
	//
	// Date        Author            Notice
	// ----------  ----------------  -------------------------------------------
	// 23.12.2002  G.Schulz (SCC)    Initial Version
	// 31.03.2004  G.Schulz (SCC)    Safari support added
	// -------------------------------------------------------------------------
	this.setupEventHandler = function() {

		if (ie || opera) {
			// register Mouse-Handler in IE
			document.onmouseover = HandleMouseover;
			document.onmouseout  = HandleMouseout;
			document.onclick     = CC.handleEvent;
		} else if (ns || safari || firefox || mozilla) {
			// Event-Capturing in NS
			window.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT | Event.CLICK);
			window.onmouseover = HandleMouseover;
			window.onmouseout  = HandleMouseout;
			window.onclick     = CC.handleEvent;
		}
	}
	
	// +------------------------------------------------------------------------
	// | Function.: onClick(event)
	// +------------------------------------------------------------------------
	this.onClick = function(event) {
		CC.getMenuRegistry().onClick(event);
	}

	// +------------------------------------------------------------------------
	// | Function.: onClick(event)
	// +------------------------------------------------------------------------
	this.onContextMenu = function(event) {
		alert("onContextMenu");
		CC.getMenuRegistry().onClick(event);
	}
}

// +----------------------------------------------------------------------------
// | Function.: handleEvent(event)
// +----------------------------------------------------------------------------
CC.handleEvent = function(event) {
	var e = CC.getEvent(event);

	if (e.type == "click") {
		cclib.onClick(event);
	} else if (e.type == "contextmenu") {
		cclib.onContextMenu(event);
	} else {
		alert("unhandled event: " + e.type);
	}
}

CC.absLeft = function(el) {
	return (el.offsetParent) ? (el.offsetLeft + CC.absLeft(el.offsetParent)) : el.offsetLeft;
}

CC.absTop = function(el) {
	return (el.offsetParent) ? (el.offsetTop + CC.absTop(el.offsetParent)) : el.offsetTop;
}

CC.getEvent = function(event) {
	if (!event) {
		return window.event;
	} else {
		return event;
	}	
}

CC.getElementFromEvent = function(event) {
	var e = CC.getEvent(event);

	var oElement;

	if (e.target) {
		oElement = e.target;
	} else if (e.srcElement) {
		oElement = e.srcElement;
	}

	if (oElement.nodeType == 3) {
		// defeat Safari bug
		oElement = oElement.parentNode;
	}

	return oElement;
}

var cclib = new CC(); 