/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/def/jscript/tabset.js,v 1.51 2007/06/13 16:02:05 P001001 Exp $
 * $Revision: 1.51 $
 * $Date: 2007/06/13 16:02:05 $
 *
 * ====================================================================
 *
 * Copyright (c) 2000 - 2006 SCC Informationssysteme GmbH. All rights
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

/***********************************************************************
 * Name:
 *        TabSet.js
 *
 * Function:
 *        Provide a Dynamic TabSet in a web browser.
 *
 * Author:
 *        G Schulz
 *
 * Environment:
 *        This is a PLATFORM-INDEPENDENT source file. As such it may
 *        contain no dependencies on any specific operating system
 *        environment or hardware platform.
 *
 * Description:
 *
 * TESTED ON:  - InternetExplorer   > 5.0
 *             - Netscape Navigator > 7.0
 *             - Mozilla            > 1.6
 *             - Safari             > 1.2
 *
 ***********************************************************************/

/*
+ ---------------------------------------------------------------------------------+
| Object....: Tab (id, label, selected, tooltip, disabled)
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 26.05.2005  H.Schulz (SCC)    Overlapping tabs
| 04.03.2003  G.Schulz (SCC)    Initial version
| 27.12.2003  G.Schulz (SCC)    Height, Width for the additinal icon added
| 22.04.2004  G.Schulz (SCC)    NS hand cursor problem fixed. 
|								TabsetImages were not displayed in NS (runAt=client)
| 01.08.2004  G.Schulz (SCC)    Problems with style.cursor = 'pointer' in IE fixed
| 13.10.2004  G.Schulz (SCC)    TabSet supports now the "formElement" attribute
+ ---------------------------------------------------------------------------------+
*/
function Tab(id, label, selected, tooltip, disabled, icon, iconwidth, iconheight) {
	// Note: if changed the constructor of TabSet_addTab(...) must also be changed!
	this.id          = id;	                                           // identifier for the tab
	this.parent      = null;                                           // the TabSet object containing the Tab (parent)
	this.label       = label;                                          // text to display on the tab
	this.selected    = (arguments.length >= 3) ? selected : false;     // indicates that the tab is selected
	this.tooltip     = tooltip;                                        // ToolTip
	this.disabled    = (arguments.length >= 5) ? disabled : false;     // false if the Tabe can not be selected
	this.icon        = icon;                                           // optional icon in front of the tab
	this.iconwidth   = iconwidth;                                      // the width of the optional icon
	this.iconheight  = iconheight;                                     // the height of the optional icon

	this.clientHandler  = new Array();                                 // ClientHandler associated with this tab
}
function Tab_getId() {
	return this.id;
}
function Tab_getLabel() {
	return this.label;
}
function Tab_getTooltip() {
	return this.tooltip;
}
function Tab_isDisabled() {
	return this.disabled;
}
function Tab_setDisabled(disabled) {
	this.disabled = disabled;
}
function Tab_isSelected() {
	return this.selected;
}
function Tab_setSelected(selected) {
	this.selected = selected;
}
function Tab_setParent(obj) {
	if (obj instanceof TabSet) {
		this.parent = obj;
	}
}
function Tab_addHandler(handler, script) {
	this.clientHandler[handler] = script;
	return this;
}
function Tab_getHandler(handler) {
	if (handler in this.clientHandler) {
		return this.clientHandler[handler];
	} else {
		return null;
	}
}

function Tab_toString() {
	var out = '';
	out += '******* TabSet *********' + LF
	out += 'Id.........: ' + this.id + LF;
	out += 'Label......: ' + this.label + LF;
	out += 'Selected...: ' + this.selected + LF;
	out += 'Tooltip....: ' + this.tooltip + LF;
	out += 'Disabled...: ' + this.disabled + LF;
	out += 'Icon.......: ' + this.icon + LF;
	out += 'Icon width.: ' + this.iconwidth + LF;
	out += 'Icon height: ' + this.iconheight;
	return out;
}
new Tab();
Tab.prototype.getId        = Tab_getId;
Tab.prototype.getLabel     = Tab_getLabel;
Tab.prototype.getTooltip   = Tab_getTooltip;
Tab.prototype.isSelected   = Tab_isSelected;
Tab.prototype.setSelected  = Tab_setSelected;
Tab.prototype.setParent    = Tab_setParent;
Tab.prototype.isDisabled   = Tab_isDisabled;
Tab.prototype.setDisabled  = Tab_setDisabled;
Tab.prototype.addHandler   = Tab_addHandler;
Tab.prototype.getHandler   = Tab_getHandler;
Tab.prototype.toString     = Tab_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object.....: TabSet (id)
| Function...: Generates a TabSet Object
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/
function TabSet(id, formElement) {
	this.id          = id;                                               // Id of the TabSets (=styleId within jsp)
	this.formElement = (arguments.length >= 2) ? formElement : false;    // indicates if the TabSet should act as a form element.
	this.tabs        = new Array();                                      // Collection for the tab pages
	this.length      = 0;                                                // total number of tab pages
	this.runAt       = RunAt.SERVER;                                     // indicates if a click on a tab page should make a server roundtrip or not.
	this.firstVisibleTabId = null;
}
function TabSet_getId() {
	return this.id;
}
function TabSet_addTab(obj, label, selected, tooltip, disabled, icon, iconwidth, iconheight) {
	var tab = null;

	// workaround for function overloading in JS
	if (obj instanceof Tab) {
		tab = obj;
	} else {
		var id = obj;
		tab = new Tab(id, label, selected, tooltip, disabled, icon, iconwidth, iconheight)
	}
	
	this.tabs[this.tabs.length] = tab;
	tab.setParent(this);
	this.length = this.tabs.length;
	return tab;
}
function TabSet_getTab(index) {
	// Check overflow
	if (index < 0 || index > this.length) {
		return null;
	} else {
		return this.tabs[index];
	}
}
function TabSet_getTabById(id) {

	for (var i=0; i < this.length; i++) {
		if (this.tabs[i]['id'] == id) {
			return this.tabs[i];
		}
	}
	
	return null;
}
function TabSet_getTabIndex(tabid) {

	for (var i=0; i < this.length; i++) {
		if (this.tabs[i]['id'] == tabid) {
			return i;
		}
	}

	return -1;
}
function TabSet_selectTab(index) {
	// check Index
	if (index < 0 || index > this.length) {
		return;
	}
	// select the tab
	for (var i=0; i < this.length; i++) {
		this.tabs[i]['selected'] = (index == i) ? true : false;
	}
}
function TabSet_selectTabById(id) {
	for (var i=0; i < this.length; i++) {
		this.tabs[i]['selected'] = (this.tabs[i]['id'] == id) ? true : false;
	}
}
function TabSet_getSelectedIndex() {
	for (var i=0; i < this.length; i++) {
		if (this.tabs[i]['selected'] == true) return i;
	}
	return -1;
}
function TabSet_setRunAt(obj) {
	this.runAt = obj;
}
function TabSet_setFirstVisibleTab(firstVisibleTabId) {
	this.firstVisibleTabId = firstVisibleTabId;
}
function TabSet_getFirstVisibleTab() {
	return this.firstVisibleTabId;
}
function TabSet_isFormElement() {
	return this.formElement;
}
function TabSet_setFormElement(formElement) {
	this.formElement = formElement;
}
function TabSet_toString() {
	var out = '';
	out += '******* TabSet *********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'Length.........: ' + this.length + LF;
	out += 'RunAt..........: ' + this.runAt + LF;
	out += 'SelectedIndex..: ' + this.getSelectedIndex();
	out += 'FormElement....: ' + this.formElement;
	return out;
}
new TabSet();
TabSet.prototype.getId				= TabSet_getId;
TabSet.prototype.selectTab			= TabSet_selectTab;
TabSet.prototype.selectTabById		= TabSet_selectTabById;
TabSet.prototype.getTabIndex        = TabSet_getTabIndex;
TabSet.prototype.getSelectedIndex	= TabSet_getSelectedIndex;
TabSet.prototype.addTab				= TabSet_addTab;
TabSet.prototype.getTab				= TabSet_getTab;
TabSet.prototype.getTabById         = TabSet_getTabById;
TabSet.prototype.setRunAt			= TabSet_setRunAt;
TabSet.prototype.setFirstVisibleTab = TabSet_setFirstVisibleTab;
TabSet.prototype.getFirstVisibleTab = TabSet_getFirstVisibleTab;
TabSet.prototype.setFormElement     = TabSet_setFormElement;
TabSet.prototype.isFormElement      = TabSet_isFormElement;
TabSet.prototype.toString			= TabSet_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object....: TabSetPainterData(tabSet, resPath, bgColor, maxVisibleTabs, maxLabelLength)
| Fubction..: Holds the Information needed by the TabSetPainter to draw a TabSet
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/
var	CSS_TABLE               = 'tsc';
var	LABEL_SUFFIX            = '..';
var	DIV_PREVIX              = 'tabset_';            // Prefix for DIV-Tag which embbedds the tabset

function TabSetPainterData(tabSet, type, contextPath, arr_imageRes, arr_stringRes, bgColor, maxVisibleTabs, maxLabelLength, styleClass, overlapping) {
	this.tabSet             = tabSet;               // TabSet object containing the tab pages
	this.contextPath        = contextPath;          // Needed for SSL problem IE
	this.arr_imageRes       = arr_imageRes;	        // array including all the images to paint the tabset
	this.arr_stringsRes     = arr_stringRes;        // array including string resources
	this.TAB_BGCOLOR	    = bgColor;	            // background-color for this tabset
	this.maxVisibleTabs     = maxVisibleTabs;       // maximal number of tabpages to show
	this.maxLabelLength     = maxLabelLength;       // maximal length of the labels on the tab page
	this.currentPos         = -1;                   // actual position while browsing the tabset
	this.overlapping		= overlapping;			// Draw overlapping tabs

	// Init-Block. Only initialize when the Object is really used.
	if (null != tabSet) {
		this.isTabset             = (type.toUpperCase() == 'TABSET') ? true : false;
		
		// Scroll buttons
		this.ARROW_LEFT           = this.isTabset ? arr_imageRes[BUTTON_TABSET_PREVIOUS_1]    : arr_imageRes[BUTTON_TABBAR_PREVIOUS_1];
		this.ARROW_LEFT_DISABLED  = this.isTabset ? arr_imageRes[BUTTON_TABSET_PREVIOUS_2]    : arr_imageRes[BUTTON_TABBAR_PREVIOUS_2];
		this.ARROW_RIGHT          = this.isTabset ? arr_imageRes[BUTTON_TABSET_NEXT_1]        : arr_imageRes[BUTTON_TABBAR_NEXT_1];
		this.ARROW_RIGHT_DISABLED = this.isTabset ? arr_imageRes[BUTTON_TABSET_NEXT_2]        : arr_imageRes[BUTTON_TABBAR_NEXT_2];
	
		// More-Tabs Image
		this.TAB_PREV             = this.isTabset ? arr_imageRes[BUTTON_TABSET_MORE_PREVIOUS] : arr_imageRes[BUTTON_TABBAR_MORE_PREVIOUS];
		this.TAB_PREV_EMPTY       = this.isTabset ? arr_imageRes[BUTTON_TABSET_MORE_EMPTY]    : arr_imageRes[BUTTON_TABBAR_MORE_EMPTY];
		this.TAB_NEXT             = this.isTabset ? arr_imageRes[BUTTON_TABSET_MORE_NEXT]     : arr_imageRes[BUTTON_TABBAR_MORE_NEXT];
	
		// selected tab images
		this.TAB_SEL_BG           = this.isTabset ? arr_imageRes[TABSET_SEL_BG]               : arr_imageRes[TABBAR_SEL_BG];
		this.TAB_SEL_NONE         = this.isTabset ? arr_imageRes[TABSET_SEL_NONE]             : arr_imageRes[TABBAR_SEL_NONE];
		this.TAB_SEL_UNSEL        = this.isTabset ? arr_imageRes[TABSET_SEL_UNSEL]            : arr_imageRes[TABBAR_SEL_UNSEL];
		this.TAB_SEL_DIS          = this.isTabset ? arr_imageRes[TABSET_SEL_DIS]              : arr_imageRes[TABBAR_SEL_DIS];
		this.TAB_NONE_SEL         = this.isTabset ? arr_imageRes[TABSET_NONE_SEL]             : arr_imageRes[TABBAR_NONE_SEL];

		// unselected tab images
		this.TAB_UNSEL_BG         = this.isTabset ? arr_imageRes[TABSET_UNSEL_BG]             : arr_imageRes[TABBAR_UNSEL_BG];
		this.TAB_UNSEL_NONE       = this.isTabset ? arr_imageRes[TABSET_UNSEL_NONE]           : arr_imageRes[TABBAR_UNSEL_NONE];
		this.TAB_UNSEL_SEL        = this.isTabset ? arr_imageRes[TABSET_UNSEL_SEL]            : arr_imageRes[TABBAR_UNSEL_SEL];
		this.TAB_UNSEL_UNSEL      = this.isTabset ? arr_imageRes[TABSET_UNSEL_UNSEL]          : arr_imageRes[TABBAR_UNSEL_UNSEL];
		this.TAB_UNSEL_DIS        = this.isTabset ? arr_imageRes[TABSET_UNSEL_DIS]            : arr_imageRes[TABBAR_UNSEL_DIS];
		this.TAB_NONE_UNSEL       = this.isTabset ? arr_imageRes[TABSET_NONE_UNSEL]           : arr_imageRes[TABBAR_NONE_UNSEL];

		// disabled tab images
		this.TAB_DIS_BG           = this.isTabset ? arr_imageRes[TABSET_DIS_BG]               : arr_imageRes[TABBAR_DIS_BG];
		this.TAB_DIS_NONE         = this.isTabset ? arr_imageRes[TABSET_DIS_NONE]             : arr_imageRes[TABBAR_DIS_NONE];
		this.TAB_DIS_SEL          = this.isTabset ? arr_imageRes[TABSET_DIS_SEL]              : arr_imageRes[TABBAR_DIS_SEL];
		this.TAB_DIS_UNSEL        = this.isTabset ? arr_imageRes[TABSET_DIS_UNSEL]            : arr_imageRes[TABBAR_DIS_UNSEL];
		this.TAB_DIS_DIS          = this.isTabset ? arr_imageRes[TABSET_DIS_DIS]              : arr_imageRes[TABBAR_DIS_DIS];
		this.TAB_NONE_DIS         = this.isTabset ? arr_imageRes[TABSET_NONE_DIS]             : arr_imageRes[TABBAR_NONE_DIS];
	
		// String ressource
		this.TEXT_RANGE           = this.isTabset ? arr_stringRes[FW_TABSET_RANGE]            : arr_stringRes[FW_TABBAR_RANGE];

		// RunAtClient
		var firstVisibleTab      = this.tabSet.getTabIndex(this.tabSet.getFirstVisibleTab());
		//this.currentPos           = (-1 == tabSet.getSelectedIndex()) ? 0 : tabSet.getSelectedIndex();
		this.currentPos           = (-1 == firstVisibleTab) ? 0 : firstVisibleTab;
		
		// Bei RunAtServer
		// aus hiddendata
		
		this.maxLabelLength       = (-1 == maxLabelLength) ? 900 : maxLabelLength;
		this.maxVisibleTabs       = (-1 == maxVisibleTabs) ? tabSet.length : maxVisibleTabs;

	}
}
new TabSetPainterData();

/*
+ ---------------------------------------------------------------------------------+
| Object....: TabSetPainter();
| Function..: Responsible for drawing the TabSet
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Erstversion
| 06.07.2005  G.Schulz (SCC)    Fix postion of selected tab after server roundtrip
+ ---------------------------------------------------------------------------------+
*/
var	FLAG_STATE         = 0x000F;               // Tab State Mask
var	FLAG_UNSELECTED    = 0x0001;               // Tab is unselected
var	FLAG_SELECTED      = 0x0002;               // Tab is selected
var	FLAG_DISABLED      = 0x0003;               // Tab is disabled
var	FLAG_FIRST         = 0x1000;               // This flag is set for the first visible tab
var	FLAG_LAST          = 0x2000;               // This flag is set for the last visible tab

function TabSetPainter() {
}
function TabSetPainter_getTabState(tab, index, first, last) {
	var state = 0;

	if (tab.isDisabled()) {
		state |= FLAG_DISABLED;
	} else if (tab.isSelected()) {
		state |= FLAG_SELECTED;
	} else {
		state |= FLAG_UNSELECTED;
	}

	if (index == first) {
		state |= FLAG_FIRST;
	}

	if (index == last) {
		state |= FLAG_FIRST;
	}

	return state;
}
function TabSetPainter_browse(tspData, direction) {
	var pos = tspData['currentPos'];
	var tabSet = tspData['tabSet'];
	var maxVisibleTabs = tspData['maxVisibleTabs'];
	var firstVisibleTab = 0;

	if (direction.toUpperCase() == 'PREV') {
		tspData['currentPos'] = (pos - 1 <= 0) ? 0 : pos - 1;
	}
	else if (direction.toUpperCase() == 'NEXT') {
		tspData['currentPos'] = (pos + maxVisibleTabs + 1 > tabSet.length) ? pos : pos + 1;
	}
	// redraw
	TabSetPainter.render(tspData);
}
function TabSetPainter_render(tspData) {
	var maxVisibleTabs	= tspData['maxVisibleTabs'];
	var maxLabelLength	= tspData['maxLabelLength'];
	var tabSet			= tspData['tabSet'];
	var	currentPos		= tspData['currentPos'];

	// create Documentfragment
	var doc = document.createElement('SPAN');
	
	//create Table
	var table = document.createElement('Table');
	table.cellSpacing	= 0;
	table.cellPadding	= 0;
	table.border		= 0;
	table.className		= CSS_TABLE;

	doc.appendChild(table);

	// create Row
	var row = table.insertRow(0);
	row.verticalAlign = 'middle';
	
	TabSetPainter.createScrollButtons(row, tspData, 'L');
	TabSetPainter.createScrollButtons(row, tspData, 'R');
	TabSetPainter.createImgNodeMore(row, tspData, 'L');
	
	var first = (tabSet.length <= maxVisibleTabs) ? 0 : currentPos;
	var last  = (currentPos + maxVisibleTabs >= tabSet.length) ? tabSet.length: currentPos + maxVisibleTabs;

	// Create a new TabSet
	var lstate = 0;
	var state  = 0;
	for (var i=first; i < last; i++) {
		var tab = tabSet.getTab(i);

		// Calculate the Tabs state
		lstate	= state;
		state	= TabSetPainter.getTabState(tab, i, first, last);

		// render the tabs
		if (tspData['overlapping']) {
			var rstate	= 0;

			if ((i + 1) < last) {
				rstate = TabSetPainter.getTabState(tabSet.getTab(i + 1), i + 1, first, last);
			}

			TabSetPainter.createTab(row, tab, tspData, lstate, state, rstate, first);
		} else {
			TabSetPainter.createTab(row, tab, tspData, 0, state, 0, first);
		}
	}
	
	TabSetPainter.createImgNodeMore(row, tspData, 'R');
	TabSetPainter.createDetailNode(row, tspData);
	
	// get the DIV-Element from the form, where the
	// TabSet should be inserted / replaced
	var div = TabSetPainter.getTabSetNode(tabSet.getId());
	if ( div.hasChildNodes() ) {
		//	div.replaceChild(doc, div.childNodes[0]);  // Problems if used with SSL and IE
		div.innerHTML = '';
		div.appendChild(table);
	} else {
		div.appendChild(table);
	}

	// If our tabset works on the client side
	// only dispay the content for the selected tab (span)
	// and hide all other html span elements.
	// First check if a tabset was selected
	if (tabSet.runAt == RunAt.CLIENT || tabSet.getSelectedIndex() >= 0) {
		TabSetPainter.displayTab(tabSet.getId(), tabSet.getTab(tabSet.getSelectedIndex()).getId());
	}
}
function TabSetPainter_createTab(row, tab, tspData, lstate, state, rstate, first) {

	if (lstate == 0) {
		// There is no left neighbour
		TabSetPainter.createImgNode(row, tspData, lstate, state);
	}

	TabSetPainter.createTabNode(row, tab, tspData, state, first);
	TabSetPainter.createImgNode(row, tspData, state, rstate);
}
function TabSetPainter_getStyleClass(state) {

	if ((state & FLAG_STATE) == FLAG_SELECTED) {
		return 'sel';
	} else if ((state & FLAG_STATE) == FLAG_UNSELECTED) {
		return 'unsel';
	} else if ((state & FLAG_STATE) == FLAG_DISABLED) {
		return 'disabled';
	} else {
		return '';
	}
}
function TabSetPainter_getBgImage(tspData, state) {
	switch (state & FLAG_STATE) {
		case FLAG_SELECTED :
			return TabSetPainter.createImage(tspData.TAB_SEL_BG, tspData.TAB_BGCOLOR);
		case FLAG_UNSELECTED :
			return TabSetPainter.createImage(tspData.TAB_UNSEL_BG, tspData.TAB_BGCOLOR);
		case FLAG_DISABLED :
			return TabSetPainter.createImage(tspData.TAB_DIS_BG, tspData.TAB_BGCOLOR);
		default :
			return null;
	}
}
function TabSetPainter_createImgNode(row, tspData, lstate, rstate) {
	var img = null;

	if ((lstate & FLAG_STATE) == FLAG_SELECTED) {
		if ((rstate & FLAG_STATE) == FLAG_SELECTED) {
			img = null;
		} else if ((rstate & FLAG_STATE) == FLAG_UNSELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_SEL_UNSEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_DISABLED) {
			img = TabSetPainter.createImage(tspData.TAB_SEL_DIS, tspData.TAB_BGCOLOR);
		} else {
			img = TabSetPainter.createImage(tspData.TAB_SEL_NONE, tspData.TAB_BGCOLOR);
		}
	} else if ((lstate & FLAG_STATE) == FLAG_UNSELECTED) {
		if ((rstate & FLAG_STATE) == FLAG_SELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_UNSEL_SEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_UNSELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_UNSEL_UNSEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_DISABLED) {
			img = TabSetPainter.createImage(tspData.TAB_UNSEL_DIS, tspData.TAB_BGCOLOR);
		} else {
			img = TabSetPainter.createImage(tspData.TAB_UNSEL_NONE, tspData.TAB_BGCOLOR);
		}
	} else if ((lstate & FLAG_STATE) == FLAG_DISABLED) {
		if ((rstate & FLAG_STATE) == FLAG_SELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_DIS_SEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_UNSELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_DIS_UNSEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_DISABLED) {
			img = TabSetPainter.createImage(tspData.TAB_DIS_DIS, tspData.TAB_BGCOLOR);
		} else {
			img = TabSetPainter.createImage(tspData.TAB_DIS_NONE, tspData.TAB_BGCOLOR);
		}
	} else {
		if ((rstate & FLAG_STATE) == FLAG_SELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_NONE_SEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_UNSELECTED) {
			img = TabSetPainter.createImage(tspData.TAB_NONE_UNSEL, tspData.TAB_BGCOLOR);
		} else if ((rstate & FLAG_STATE) == FLAG_DISABLED) {
			img = TabSetPainter.createImage(tspData.TAB_NONE_DIS, tspData.TAB_BGCOLOR);
		} else {
			img = null;
		}
	}

	row.insertCell(row.cells.length).appendChild(img);
}
function TabSetPainter_createTabNode(row, tab, tspData, state, first) {
	var cell			= null;
	var bgImage			= TabSetPainter.getBgImage(tspData, state);
	var className		= TabSetPainter.getStyleClass(state);
	var maxLabelLength	= tspData['maxLabelLength'];
	var bgImageSrc		= bgImage.src
	var img             = null;

	// problem in IE under https
	if(ie & HTTPUtil.isSecure() & (bgImageSrc.indexOf('https') == -1)) {
		var host	= window.location.host;
		bgImageSrc	= 'https://' + host +  tspData.contextPath + '/' + bgImageSrc;
	}

	// Draw an optional icon in front of the Text in the Tab
	if (null != tab['icon']) {
		var icon	= tab['icon'];
		var width	= tab['iconwidth'];
		var height	= tab['iconheight'];

		img 		= document.createElement('Img');
		img.src		= icon;
		img.border	= 0;
		img.width	= width;
		img.height	= height;
		img.setAttribute('vspace', 0);
		img.setAttribute('align', 'absmiddle');
	}

	// Draw the Label
	cell = row.insertCell(row.cells.length);
	cell.setAttribute('background', bgImageSrc);
	cell.noWrap = true;	
	cell.className = className;

	var span = document.createElement('Span');
	if (!ie) {
		span.style.cursor = 'pointer';
	} 
	span.style.cursor = 'hand';
	span.title = (tab.getTooltip()!= null) ? tab.getTooltip() : tab.getLabel();

	// Add the onclick handler only if the Tab is enabled
	if (!tab.isDisabled()) {
		span.onclick = function() {
			var tabSet   = tspData['tabSet'];
			var tabSetId = tab.parent.getId();
			var firstTab = tabSet.getTab(first);
			
			var userscript = tab.getHandler(ClientEvent.ONCLICK);

			if (null != userscript) {
				var rtc = CCUtility.executeUserScript(userscript);
				if (!rtc) return false;
			}

			// if a server roundtrip is required, we must submit the Form.
			// Therefore search the form which embbeds this TabSet and submit the form.
			var form = TabSetPainter.getFormElement(document.getElementById(DIV_PREVIX + tabSetId));

// -------------- 
			// Set the tabsets property to the selected page
			if ((form != null) && (tabSet.id != null)) {

				var field = form[tabSet.id];
				
				if (field != null) {
					field.value = tab.id;
				}
			}
// -------------- 

			if (tabSet.runAt == RunAt.SERVER) {

				if (null == form) {
					var msg = ''
					msg += 'Error: TabSet ' + tabSet.getId() + ' is not embedded in a form!\n';
					msg += 'If you want to use the client side scrolling feature you must\n';
					msg += 'embedd the TabSetControl in a HTML-Form!';
					alert(msg);
					return false;
				}
				
				if (tabSet.isFormElement()) {
					var param = tabSet.getId() + '=TabClick=' + tab.getId();
				
					var ctrla = document.getElementById('ctrla');
					if (ctrla == null) {
						var hidden = CCUtility.createHidden('ctrla', param);
						form.appendChild(hidden);
						
					} else {
						ctrla.value = param;
					}
					
					// save state
					var param = 'ctrl=' + tabSet.getId() + ';first=' + firstTab.getId();
					var state = CCUtility.createHidden('com.cc.framework.state', param);
					form.appendChild(state);
					CCUtility.submitForm(form);
					
				} else {
					// save state
					var param = 'ctrl=' + tabSet.getId() + ';first=' + firstTab.getId();
					var state = CCUtility.createHidden('com.cc.framework.state', param);
					form.appendChild(state);

					// Set the parameter to identify the tab, so that the event handler can be called.
					form.action += '?ctrl=' + tabSet.getId() + '&action=TabClick&param=' + tab.getId();
					CCUtility.submitForm(form);
				}			
			} else if (tabSet.runAt == RunAt.CLIENT) {
				// paint the TabSet again using the selected index
				tabSet.selectTabById(tab.getId());
				TabSetPainter.render(tspData);

				if (null != form) {
	/*				
					var param = tabSet.getId() + '=TabClick=' + tab.getId();
				
					var ctrla = document.getElementById('ctrla');
					if (ctrla == null) {
						var hidden = CCUtility.createHidden('ctrla', param);
						form.appendChild(hidden);
					} else {
						ctrla.value = param;
					}
	*/				
	
					// save state
					var param = 'ctrl=' + tabSet.getId() + ';first=' + firstTab.getId() + ';selected=' + tab.getId();
//				alert(param);			
					//var hidden = document.getElementById('com.cc.framework.state');

					//if (null == hidden) {
						var hidden = CCUtility.createHidden('com.cc.framework.state', param);
						form.appendChild(hidden);
				//	} else {
				//		hidden.value = param;
				//	}
				}
				
			}
			
			return true;
		};
	}	
	if (null != img) {
		span.appendChild(img);
		span.appendChild(document.createTextNode(' '));
	}

	span.appendChild(document.createTextNode(TabSetPainter.clipLabel(tab, maxLabelLength)));
	cell.appendChild(span);
}
function TabSetPainter_clipLabel(tab, maxLabelLength) {
	var label = tab.getLabel();

	if (label.length > (maxLabelLength + 2) ) {
		label = label.slice(0, maxLabelLength) + LABEL_SUFFIX;
	}
	return label;
}
function TabSetPainter_createScrollButtons(row, tspData, side) {
	var maxVisibleTabs = tspData['maxVisibleTabs'];
	var maxLabelLength = tspData['maxLabelLength'];
	var currentPos     = tspData['currentPos'];
	var tabSet         = tspData['tabSet'];

	var imgBtn = document.createElement('Img');

	var pos = (tabSet.length <= maxVisibleTabs) ? 0 : currentPos;

	if (side.toUpperCase() == 'L' && pos > 0) {
		imgBtn.src = tspData.ARROW_LEFT.src;
		imgBtn.border = 0;
		imgBtn.id = 'btnNextTab_' + tabSet.getId();
		imgBtn.onclick = function() { TabSetPainter.browse(tspData, 'PREV'); };
		if (!ie) {
			imgBtn.style.cursor = 'pointer';
		}
		imgBtn.style.cursor = 'hand';
		row.insertCell(row.cells.length).appendChild(imgBtn);
	} else if (side.toUpperCase() == 'L' &&  maxVisibleTabs < tabSet.length){
		imgBtn.src = tspData.ARROW_LEFT_DISABLED.src;
		imgBtn.border = 0;
		row.insertCell(row.cells.length).appendChild(imgBtn);
	}
	
	if ((side.toUpperCase() == 'R') && (currentPos + maxVisibleTabs < tabSet.length) ) {
		imgBtn.src = tspData.ARROW_RIGHT.src;
		imgBtn.border = 0;
		imgBtn.id = 'btnPrevTab_' + tabSet.getId();
		imgBtn.onclick = function() { TabSetPainter.browse(tspData, 'NEXT'); };
		if (!ie) {
			imgBtn.style.cursor = 'pointer';
		}
		imgBtn.style.cursor = 'hand';
		var cell = row.insertCell(row.cells.length);
		cell.style.paddingLeft = '1px';
		cell.appendChild(imgBtn);
	} else if (side.toUpperCase() == 'R' && pos > 0) {
		imgBtn.src = tspData.ARROW_RIGHT_DISABLED.src;
		imgBtn.border = 0;
		var cell = row.insertCell(row.cells.length);
		cell.style.paddingLeft = '1px';
		cell.appendChild(imgBtn);
	}
}
function TabSetPainter_createImgNodeMore(row, tspData, side) {
	var maxVisibleTabs	= tspData['maxVisibleTabs'];
	var maxLabelLength	= tspData['maxLabelLength'];
	var currentPos		= tspData['currentPos'];
	var tabSet			= tspData['tabSet'];
	
	var imgTab = document.createElement('Img');
	
	var pos = (tabSet.length <= maxVisibleTabs) ? 0 : currentPos;

	if (side.toUpperCase() == 'L' && pos > 0) {
		imgTab.src = tspData.TAB_PREV.src;
		imgTab.border = 0;
		var cell = row.insertCell(row.cells.length);
		cell.className = 'tabScrollBtnL';
		cell.appendChild(imgTab);
	} else if (side.toUpperCase() == 'L' &&  maxVisibleTabs < tabSet.length) {
		imgTab.src = tspData.TAB_PREV_EMPTY.src;
		imgTab.border = 0;
		var cell = row.insertCell(row.cells.length);
		cell.appendChild(imgTab);
	}
	
	if ((side.toUpperCase() == 'R') && (currentPos + maxVisibleTabs < tabSet.length) ) {
		imgTab.src = tspData.TAB_NEXT.src;
		imgTab.border = 0;
		var cell = row.insertCell(row.cells.length);
		cell.className = 'tabScrollBtnR';
		cell.appendChild(imgTab);
	}
}
function TabSetPainter_createDetailNode(row, tspData) {
	var tabSet = tspData['tabSet'];
	var maxVisibleTabs = tspData['maxVisibleTabs'];
	var currentPos = tspData['currentPos'];
	
	var start  = currentPos + 1;
	var end    = (currentPos + maxVisibleTabs > tabSet.length) ? tabSet.length : currentPos + maxVisibleTabs;
	var detail = tspData.TEXT_RANGE;

	detail = detail.replace('{0}', start);
	detail = detail.replace('{1}', end);
	detail = detail.replace('{2}', tabSet.length);
	
	if (tabSet.length <= maxVisibleTabs) return;
	
	var span = document.createElement('Span');
	span.appendChild(document.createTextNode(detail));
	span.className = 'tabDetail';
	
	var cell = row.insertCell(row.cells.length);
	cell.noWrap = true;
	cell.appendChild(span);
}
function TabSetPainter_getTabSetNode(id) {
	return document.getElementById(DIV_PREVIX + id);
}
function TabSetPainter_getFormElement(node) {
	// Search the Form wich embeds the DIV-Element
	var parent = node.parentNode;
	
	if (null == parent) return null;
	
	if (parent.nodeName == 'FORM' ) {
		return parent;
	} else {
		return arguments.callee(parent);
	}
}
function TabSetPainter_displayTab(tabSetId, tabId) {
	var spanId = 'tab_' + tabSetId + '_' + tabId;
	var nodes = document.getElementsByTagName('div');

	for (var i=0; i < nodes.length; i++) {
		if (nodes[i].id == spanId) {
			nodes[i].style.display = 'block';
		}
		else if (nodes[i].id.indexOf('tab_' + tabSetId) != -1) {
			nodes[i].style.display = 'none';
		}
	}
}
function TabSetPainter_createImage(image, bgColor) {
	if (null != bgColor) {
		// change the image src from tabLSel_{0} to the selected bgcolor
		image.src = image.src.replace('{0}', bgColor);
	}
	
	return image.create();
}

new TabSetPainter();
TabSetPainter.browse               = TabSetPainter_browse;
TabSetPainter.getTabState          = TabSetPainter_getTabState;
TabSetPainter.render               = TabSetPainter_render;
TabSetPainter.clipLabel            = TabSetPainter_clipLabel;
TabSetPainter.createTab            = TabSetPainter_createTab;
TabSetPainter.createTabNode        = TabSetPainter_createTabNode;
TabSetPainter.createImgNode        = TabSetPainter_createImgNode;
TabSetPainter.createImgNodeMore    = TabSetPainter_createImgNodeMore;
TabSetPainter.createScrollButtons  = TabSetPainter_createScrollButtons;
TabSetPainter.getTabSetNode        = TabSetPainter_getTabSetNode;
TabSetPainter.createDetailNode     = TabSetPainter_createDetailNode;
TabSetPainter.getFormElement       = TabSetPainter_getFormElement;
TabSetPainter.displayTab           = TabSetPainter_displayTab;
TabSetPainter.createImage          = TabSetPainter_createImage;
TabSetPainter.getStyleClass        = TabSetPainter_getStyleClass;
TabSetPainter.getBgImage           = TabSetPainter_getBgImage;