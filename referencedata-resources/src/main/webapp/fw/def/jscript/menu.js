// Vertical menu orientation
var CCMENU_VERTICAL				= 1;

// Horizontal menu orientation
var CCMENU_HORIZONTAL			= 2;

// ************************************
// Menu Flags
// ************************************

// +-+-+-+-+-+-+-+-+
// |7|6|5|4|3|2|1|0|
// +-+-+-+-+-+-+-+-+
//  | | | |   | | |
//  | | | |   | | +-- CCMENU_CHECKED
//  | | | |   | +---- CCMENU_ENABLED
//  | | | |   +------ CCMENU_VISIBLE
//  | | | | 
//  | | | +---------- CCMENU_GRAYED
//  | | +------------ CCMENU_POPUP
//  | +-------------- CCMENU_SEPARATOR
//  +---------------- CCMENU_SELECTED

// Acts as a toggle with CCMENU_UNCHECKED to place the default
// check mark next to the item.
var CCMENU_CHECKED				= 0x0001;

// Enables the menu item so that it can be selected and restores
// it from its dimmed state.
var CCMENU_ENABLED				= 0x0002;

// The menu item is visible
var CCMENU_VISIBLE				= 0x0004;

// Disables the menu item so that it cannot be selected and dims it.
var CCMENU_GRAYED				= 0x0010;

// Specifies that the menu item has a pop-up menu associated with
// it. The ID parameter specifies a handle to a pop-up menu that
// is to be associated with the item. This is used for adding
// either a top-level pop-up menu or a hierarchical pop-up menu
// to a pop-up menu item.
var CCMENU_POPUP				= 0x0020;

// Draws a horizontal dividing line. Can only be used in a pop-up menu.
// This line cannot be dimmed, disabled, or highlighted. Other parameters
// are ignored.
var CCMENU_SEPARATOR			= 0x0040;

// Specifies that the menu item is a character string.
var CCMENU_SELECTED				= 0x0080;

// ************************************
// Visible elements of an menu item
// ************************************

// Show the checkbox element
var CCMENU_SHOW_CHECKBOX		= 0x0001;

// Show the items image
var CCMENU_SHOW_IMAGE			= 0x0002;

// Show the label
var CCMENU_SHOW_LABEL			= 0x0004;

// Show the popup menu indicator
var CCMENU_SHOW_POPUP			= 0x0008;

// ************************************
// CC.MenuRegistry
// ************************************

CC.MenuRegistry = function() {
	this.menus			= new Array();

	this.items			= new Array();

	// the currently active menu
	this.activeMenu		= null;

	this.pendingRequest	= null;
	
	this.pendingParent	= null
	
	// +------------------------------------------------------------------------
	// | Function.: getMenu(menuIndex)
	// +------------------------------------------------------------------------
	this.onClick = function(event) {
		var e = CC.getElementFromEvent(event);
		
		var menu = this.menuFromEvent(e);

		if (menu == null) {
			// user did not hit a menu item so close any visible existing
			// popup menu
			this.hide();
		} else {
			// cancel event processing
			e.cancelBubble = true;
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: getMenu(menuIndex)
	// +------------------------------------------------------------------------
	this.getMenu = function(menuIndex) {
		return this.menus[menuIndex];
	}

	// +------------------------------------------------------------------------
	// | Function.: getMenuItem(menuIndex)
	// +------------------------------------------------------------------------
	this.getMenuItem = function(itemIndex) {
		return this.items[itemIndex];
	}

	// +------------------------------------------------------------------------
	// | Function.: getMenuFromId(id, x, y)
	// +------------------------------------------------------------------------
	this.getMenuFromId = function(id, x, y) {
		for (var i = 0; i < this.menus.length; i++) {
			if (id == this.menus[i].getId()) {
				return this.menus[i];
			}
		}

		// menu not found
		return null;
	}

	// +------------------------------------------------------------------------
	// | Function.: getMenuFromId(id, x, y)
	// +------------------------------------------------------------------------
	this.getOrCreateMenu = function(menu, id, x, y) {
		var popup = this.getMenuFromId(id);

		if ((popup == null) || popup.isDynamicMenu()) {
			// The menu is currently not available on the client.
			// So send an AJAX Request to the server and let the server
			// create the menu
			var href = menu.getTopLevelMenu().getAction();
			href += "?ctrl=" + menu.getTopLevelMenu().getId();	// TODO das ist nicht die ControlId!
			href += "&action=LoadMenu";
			href += "&param=" + id;

			AjaxRequest.get(
					{
						'url':href,
						'onSuccess':CC.MenuRegistry.handleLoadMenu
						/*, 'timeout':AJAX_TIMEOUT,
						'onTimeout':CC.MenuRegistry.handleTimeout */
					});
		}
		
		return popup;
	}
	
	// +------------------------------------------------------------------------
	// | Function.: getParentMenuFromId(id, x, y)
	// +------------------------------------------------------------------------
	this.getParentMenuFromId = function(id, x, y) {
		for (var i = 0; i < this.items.length; i++) {
			if (id == this.items[i].getId()) {
				return this.items[i].getMenu();
			}
		}

		// menu not found
		return null;
	}

	// +------------------------------------------------------------------------
	// | Function.: isMenuElement(element)
	// +------------------------------------------------------------------------
	this.isMenuElement = function(element) {
		return (element.id != null) && (element.id.indexOf("menu") == 0);
	}

	// +------------------------------------------------------------------------
	// | Function.: searchMenuElement(element)
	// +------------------------------------------------------------------------
	this.searchMenuElement = function(element) {
		while ((element != null) && !this.isMenuElement(element)) {
			element = element.parentNode;
		}

		return element;
	}

	// +------------------------------------------------------------------------
	// | Function.: menuFromEvent(event)
	// +------------------------------------------------------------------------
	this.menuFromEvent = function(event) {
		var oSource = null;

		if (event.srcElement == null) {
			oSource = event.explicitOriginalTarget;
		} else {
			oSource = event.srcElement;
		}

		return this.searchMenuElement(oSource);
	}

	// +------------------------------------------------------------------------
	// | Function.: hide()
	// +------------------------------------------------------------------------
	this.hide = function() {
		this.activeMenu	= null;

		for (var i = 0; i < this.menus.length; i++) {
			this.menus[i].hide();
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: reset()
	// +------------------------------------------------------------------------
	this.reset = function() {
		this.activeMenu	= null;

		for (var i = 0; i < this.menus.length; i++) {
			this.menus[i].hide();
		}

		this.menus		= new Array();
		this.items		= new Array();
	}

	// +------------------------------------------------------------------------
	// | Function.: registerMenu(menu)
	// +------------------------------------------------------------------------
	this.registerMenu = function(menu) {
		var index = this.menus.length;

		this.menus[index] = menu;

		return index;
	}

	// +------------------------------------------------------------------------
	// | Function.: registerItem(item)
	// +------------------------------------------------------------------------
	this.registerItem = function(item) {
		var index = this.items.length;

		this.items[index] = item;

		return index;
	}
}

CC.MenuRegistry.handleLoadMenu = function(req) {
	if (req.responseText == null) {
		// no response was sent from the server
	} else if (req.responseXML == null) {
		// we received a complete HTML page for replacement	
		CCAjax.replacePage(req.responseText);
	} else {
		// document node
		var xmlDocument = req.responseXML;

		var ajaxResponse = null;

		// test for common-controls AJAX response.
		if (null != xmlDocument.childNodes[0]) {
			if (xmlDocument.childNodes[0].nodeName == XML_XML) {
				ajaxResponse = xmlDocument.childNodes[1]; // IE
			} else if (xmlDocument.childNodes[0].nodeName == XML_AJAX_RESPONSE) {
				ajaxResponse = xmlDocument.childNodes[0]; // FireFox
			}
		}

		// test for XML_AJAX_RESPONSE Element
		if (null != ajaxResponse) {
			var nodeList = ajaxResponse.childNodes;

			for (var i = 0; i < nodeList.length; i++) {
				if (nodeList[i].nodeName == XML_MENUS) {
alert(nodeList[i]); // TODO
				}
			}
		} else {
			// this is not an valid common-controls AJAX response.
			// So just replace the whole page
			CCAjax.replacePage(req.responseText);
		}
	}

	// finally release the AJAX token
//	CCAjax.releaseSynchronizeToken();
}

CC.getMenuRegistry = function() {
	// create the menu registry when not already existing
	if (CC.theRegistry == null) {
		CC.theRegistry = new CC.MenuRegistry();
	}

	return CC.theRegistry;
}

// ************************************
// CC.Menu
// ************************************

CC.Menu = function(id, action) {

	// Reference to the parent menu
	this.parent			= null;

	// the action to call when an menu event occurs
	this.action			= action;
	
	// The control identifier
	this.id				= id;

	// control is disabled
	this.disabled		= false;

	// Customized sytlesheet class
	this.styleClass		= null;

	// List with menu items
	this.items			= new Array();

	// the HTML element to render this menu
	this.element		= null;

	// Register this menu in the global menu registry
	this.index			= CC.getMenuRegistry().registerMenu(this);

	// The orientation of the menu
	this.orientation	= CCMENU_VERTICAL;

	// This is a dynamic menu item that needs to be pulled from the server
	// every time the user opens the menu
	this.dynamicMenu	= false;
	
	// +------------------------------------------------------------------------
	// | Function.: onClick(oElement)
	// +------------------------------------------------------------------------
	this.onClick = function(event) {
		var e = CC.getEvent(event);

		if (this.items.length == 0) {
			// no menu items loaded so far
			var menu = CC.getMenuRegistry().getOrCreateMenu(this, this.getId(), this.x, this.y);
			
			if (menu != null) {
				this.items = menu.items;
			}
		}

		var oElement = CC.getElementFromEvent(e);

		// show the menu
		var elem = oElement.parentNode.parentNode;

		this.show(CC.absLeft(elem), CC.absTop(elem) + elem.offsetHeight);

		// cancel event processing
		e.cancelBubble = true;
	}

	// +------------------------------------------------------------------------
	// | Function.: onMouseOver(event)
	// +------------------------------------------------------------------------
	this.onMouseOver = function(event) {
		return true;
	};

	// +------------------------------------------------------------------------
	// | Function.: onMouseOut(event)
	// +------------------------------------------------------------------------
	this.onMouseOut = function(event) {
		return true;
	};

	// +------------------------------------------------------------------------
	// | Function.: getTopLevelMenu()
	// +------------------------------------------------------------------------
	this.getTopLevelMenu = function() {
		if (this.parentMenu == null) {
			return this;
		} else {
			return this.parentMenu.getTopLevelMenu();
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: getAction()
	// +------------------------------------------------------------------------
	this.getAction = function() {
		return this.action;
	}

	// +------------------------------------------------------------------------
	// | Function.: getId()
	// +------------------------------------------------------------------------
	this.getId = function() {
		return this.id;
	}

	// +------------------------------------------------------------------------
	// | Function.: getId()
	// +------------------------------------------------------------------------
	this.setId = function(id) {
		this.id = id;
	}

	// +------------------------------------------------------------------------
	// | Function.: isDynamicMenu()
	// +------------------------------------------------------------------------
	this.isDynamicMenu = function() {
		return this.dynamicMenu;
	}

	// +------------------------------------------------------------------------
	// | Function.: markAsDynamic()
	// +------------------------------------------------------------------------
	this.markAsDynamic = function() {
		this.dynamicMenu = true;
	}

	// +------------------------------------------------------------------------
	// | Function.: getOrientation()
	// +------------------------------------------------------------------------
	this.getOrientation = function() {
		return this.orientation;
	}

	// +------------------------------------------------------------------------
	// | Function.: getOrientation()
	// +------------------------------------------------------------------------
	this.setOrientation = function(orientation) {
		this.orientation = orientation;
	}

	// +------------------------------------------------------------------------
	// | Function.: getDisplayFlags()
	// +------------------------------------------------------------------------
	this.getDisplayFlags = function() {
		var flags = CCMENU_SHOW_LABEL;

		for (var i = 0; i < this.items.length; i++) {
			if (this.items[i].image != null) {
				flags |= CCMENU_SHOW_IMAGE;
			}

			if ((this.items[i].getStyle() & CCMENU_CHECKED) != 0) {
				flags |= CCMENU_SHOW_CHECKBOX;
			}

			if ((this.items[i].getStyle() & CCMENU_POPUP) != 0) {
				flags |= CCMENU_SHOW_POPUP;
			}
		}

		return flags;
	}

	// +------------------------------------------------------------------------
	// | Function.: addItem(item)
	// +------------------------------------------------------------------------
	this.addItem = function(item) {
		this.items[this.items.length] = item;
		item.setMenu(this);
	}

	// +------------------------------------------------------------------------
	// | Function.: appendMenu(style, id, label, image)
	// +------------------------------------------------------------------------
	this.appendMenu = function(style, id, label, image) {
		var item = new CC.MenuItem(id, label);
		item.style = style;
		item.image = image;

		this.addItem(item);

		return item;
	}

	// +------------------------------------------------------------------------
	// | Function.: appendSeparator()
	// +------------------------------------------------------------------------
	this.appendSeparator = function() {
		return this.appendMenu(CCMENU_SEPARATOR, null, null);
	}
	
	// +------------------------------------------------------------------------
	// | Function.: show(x, y)
	// +------------------------------------------------------------------------
	this.show = function(x, y) {
		if ((this.element == null) && (this.items.length > 0)) {
			this.draw();
		}

		if (this.element != null) {
			// move the menu to the correct coordinates
			this.element.style.left	= x + "px";
			this.element.style.top	= y + "px";

			document.body.appendChild(this.element);
/*
			if (ie) {
				// Internet Explorer z-order patch
				var oIFrame = document.createElement("iframe");
				oIFrame.style.zIndex	= "999";

				oIFrame.style.position	= "absolute";
				oIFrame.style.left		= this.element.style.left;
				oIFrame.style.top		= this.element.style.top;
				oIFrame.style.width		= this.element.clientWidth + "px";
				oIFrame.style.height	= this.element.clientHeight + "px";

				this.iframe = oIFrame;

				document.body.appendChild(this.iframe);
			}
*/
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: hide()
	// +------------------------------------------------------------------------
	this.hide = function() {
		// unselect all items
		this.selectItem(null);

		// remove the DOM node from screen
		if (this.iframe != null) {
			document.body.removeChild(this.iframe);

			this.iframe = null;
		}

		if (this.element != null) {
			document.body.removeChild(this.element);

			this.element = null;
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: draw()
	// +------------------------------------------------------------------------
	this.draw = function() {
		var oElement = document.createElement("table");
		oElement.border			= 0;
		oElement.cellSpacing	= 0;
		oElement.cellPadding	= 3;

		oElement.className		= "ccmenu";
		oElement.id				= "menu";
		oElement.menuIndex		= this.index;
		oElement.style.position	= "absolute";
		oElement.style.zIndex	= "1000";
//		oElement.style.left		= this.x + "px";
//		oElement.style.top		= this.y + "px";
		oElement.onmouseover	= function(event) {return CC.getMenuRegistry().getMenu(this.menuIndex).onMouseOver(event);};
		oElement.onmouseout		= function(event) {return CC.getMenuRegistry().getMenu(this.menuIndex).onMouseOut(event);};

		var oBody = document.createElement("tbody");
		oElement.appendChild(oBody);

		var dspFlags = this.getDisplayFlags();

		if ((this.getOrientation() & CCMENU_HORIZONTAL) != 0) {
			// draw a horizontal menu
			var oRow = document.createElement("tr");
			oBody.appendChild(oRow);

			for (var i = 0; i < this.items.length; i++) {
				var oCell = document.createElement("td");
				oRow.appendChild(oCell);
				
				var oCellTable = document.createElement("table");
				oCell.appendChild(oCellTable);
				oCellTable.border		= 0;
				oCellTable.cellSpacing	= 0;
				oCellTable.cellPadding	= 3;
				oCellTable.className	= "ccmenu";
				
				oCellTable.appendChild(this.items[i].draw(dspFlags));
			}
		} else {
			// draw a vertical menu
			for (var i = 0; i < this.items.length; i++) {
				oBody.appendChild(this.items[i].draw(dspFlags));
			}
		}

		// remember the DOM Node for this menu	this.element = oElement;
		this.element = oElement;
	}

	// +------------------------------------------------------------------------
	// | Function.: selectItem(menuItem)
	// +------------------------------------------------------------------------
	this.selectItem = function(menuItem) {
		// Search any previously selected item
		for (var i = 0; i < this.items.length; i++) {
			if ((this.items[i].getStyle() & CCMENU_SELECTED) != 0) {
				if ((menuItem == null) || (menuItem.id != this.items[i].id)) {
					this.items[i].unselect();
				}
			}
		}

		if ((menuItem != null) && (menuItem.getStyle() & CCMENU_SELECTED) == 0) {
			menuItem.select();
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: isSelected()
	// +------------------------------------------------------------------------
	this.isSelected = function() {
		for (var i = 0; i < menuItemRegistry.length; i++) {
			if (this.id == menuItemRegistry[i].getId()) {
				return menuItemRegistry[i].isSelected();
			}
		}

		// the menu is the root of the menu hierarchy
		return true;
	}
}

// ************************************
// CC.MenuItem
// ************************************

CC.MenuItem = function(strId, strLabel) {
	// reference to the parent menu control
	this.menu			= null;

	// the menu id
	this.id				= strId;

	// the items label
	this.label			= strLabel;

	this.style			= CCMENU_ENABLED | CCMENU_SHOW_LABEL;

	// the HTML element to render this menu
	this.element		= null;

	// optional image
	this.image			= null;

	// register this item in the global item registry
	this.index			= CC.getMenuRegistry().registerItem(this);

	// +------------------------------------------------------------------------
	// | Function.: onMouseOver(event)
	// +------------------------------------------------------------------------
	this.onMouseOver = function(event) {
		// select this menu item. This will unselect all currently
		// selected items of this menu
		this.menu.selectItem(this);

		return true;
	};

	// +------------------------------------------------------------------------
	// | Function.: onMouseOut(event)
	// +------------------------------------------------------------------------
	this.onMouseOut = function(event) {
		// no action
		return true;
	};

	// +------------------------------------------------------------------------
	// | Function.: onSelect()
	// +------------------------------------------------------------------------
	this.onSelect = function() {
		if (this.element != null) {
			this.element.className = "selected";
		}
		return true;
	};

	// +------------------------------------------------------------------------
	// | Function.: onUnSelect()
	// +------------------------------------------------------------------------
	this.onUnSelect = function() {
		if (this.element != null) {
			this.element.className = "item";
		}
		return true;
	}

	// +------------------------------------------------------------------------
	// | Function.: onClick(event)
	// +------------------------------------------------------------------------
	this.onClick = function(event) {
		if ((this.style & CCMENU_POPUP) == 0) {
			// hide all visible menus
			CC.getMenuRegistry().hide();

			alert("menuitem '" + this.getId() + "' clicked");

			return true;
		}

		return false;
	};

	// +------------------------------------------------------------------------
	// | Function.: setMenu(menu)
	// +------------------------------------------------------------------------
	this.setMenu = function(menu) {
		this.menu = menu;
	}

	// +------------------------------------------------------------------------
	// | Function.: getMenu()
	// +------------------------------------------------------------------------
	this.getMenu = function() {
		return this.menu;
	}

	// +------------------------------------------------------------------------
	// | Function.: getTopLevelMenu()
	// +------------------------------------------------------------------------
	this.getTopLevelMenu = function() {
		if (this.menu == null) {
			// unknown menu!
			return null;
		} else {
			return this.menu.getTopLevelMenu();
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: getId()
	// +------------------------------------------------------------------------
	this.getId = function() {
		return this.id;
	}

	// +------------------------------------------------------------------------
	// | Function.: getLabel()
	// +------------------------------------------------------------------------
	this.getLabel = function() {
		return this.label;
	}

	// +------------------------------------------------------------------------
	// | Function.: getStyle()
	// +------------------------------------------------------------------------
	this.getStyle = function() {
		return this.style;
	}

	// +------------------------------------------------------------------------
	// | Function.: isSelected()
	// +------------------------------------------------------------------------
	this.isSelected = function() {
		return ((this.style & CCMENU_SELECTED) != 0);
	}

	// +------------------------------------------------------------------------
	// | Function.: select()
	// +------------------------------------------------------------------------
	this.select = function() {
		this.style |= CCMENU_SELECTED;

		// change the visual selection state
		this.onSelect();

		if ((this.style & CCMENU_POPUP) != 0) {
			var x = 0;
			var y = 0;

			if (this.element != null) {
				x = this.element.offsetParent.offsetLeft + this.element.clientWidth - 3;
				y = this.element.offsetParent.offsetTop + this.element.offsetTop - 3;
			}

			// Show popup menu
			var menu = CC.getMenuRegistry().getOrCreateMenu(this.menu, this.getId(), x, y);

			if (menu != null) {
				menu.show(x, y);
			}
		}
	}

	// +------------------------------------------------------------------------
	// | Function.: unselect()
	// +------------------------------------------------------------------------
	this.unselect = function() {
		if ((this.style & CCMENU_POPUP) != 0) {
			// Close any visible popup menus
			var menu = CC.getMenuRegistry().getMenuFromId(this.getId());

			if (menu != null) {
				menu.hide();
			}
		}

		// change the visual selection state
		this.onUnSelect();

		this.style &= ~CCMENU_SELECTED;
	}


	// +------------------------------------------------------------------------
	// | Function.: countBits()
	// +------------------------------------------------------------------------
	this.countBits = function(flags) {
		// calculate the number of bits
		var bits = 0;
		var i = 1;
		while (i <= 0x80) {
			if ((flags & i) != 0) {
				++bits;
			}

			i *= 2;
		}

		return bits;
	}

	// +------------------------------------------------------------------------
	// | Function.: draw()
	// +------------------------------------------------------------------------
	this.draw = function(flags) {
		var oElement		= document.createElement("tr");
		oElement.id			= this.getId();
		oElement.itemIndex	= this.index;

		if ((this.getStyle() & CCMENU_ENABLED) != 0) {
			oElement.onmouseover	= function(event) {return CC.getMenuRegistry().getMenuItem(this.itemIndex).onMouseOver(event);};
			oElement.onmouseout		= function(event) {return CC.getMenuRegistry().getMenuItem(this.itemIndex).onMouseOut(event);};
			oElement.onclick		= function(event) {return CC.getMenuRegistry().getMenuItem(this.itemIndex).onClick(event);};
		}

		if ((this.getStyle() & CCMENU_SEPARATOR) != 0) {
			oElement.className	= "separator";
		} if ((this.getStyle() & CCMENU_ENABLED) != 0) {
			oElement.className	= "item";
		} if ((this.getStyle() & CCMENU_GRAYED) != 0) {
			oElement.className	= "grayed";
		}

		var oCell = null;

		if ((this.getStyle() & CCMENU_SEPARATOR) != 0) {
			oCell = document.createElement("td");
			oElement.appendChild(oCell);

			oCell.appendChild(document.createElement("hr"));
			oCell.colSpan = this.countBits(flags);
		} else {

			if ((flags & CCMENU_SHOW_CHECKBOX) != 0) {
				oCell = document.createElement("td");
				oElement.appendChild(oCell);

				if ((this.getStyle() & CCMENU_CHECKED) != 0) {
					var oImg	= document.createElement("img");
					oImg.src 	= "fw/def/image/menu/check.gif";
					oImg.align	= "absmiddle";

					oCell.appendChild(oImg);
				} else {
					oCell.appendChild(document.createTextNode(String.fromCharCode(160)));
				}
			}

			if ((flags & CCMENU_SHOW_IMAGE) != 0) {
				oCell = document.createElement("td");
				oElement.appendChild(oCell);

				if (this.image != null) {
					var oImg	= document.createElement("img");
					oImg.src 	= this.image;
					oImg.align	= "absmiddle";

					oCell.appendChild(oImg);
				} else {
					oCell.appendChild(document.createTextNode(String.fromCharCode(160)));
				}
			}

			if ((flags & CCMENU_SHOW_LABEL) != 0) {
				oCell = document.createElement("td");
				oElement.appendChild(oCell);

				oCell.appendChild(document.createTextNode(this.label));
			}

			if ((flags & CCMENU_SHOW_POPUP) != 0) {
				oCell = document.createElement("td");
				oElement.appendChild(oCell);

				if ((this.getStyle() & CCMENU_POPUP) != 0) {
					var oImg	= document.createElement("img");
					oImg.src 	= "fw/def/image/menu/popup.gif";
					oImg.align	= "absmiddle";

					oCell.appendChild(oImg);
				} else {
					oCell.appendChild(document.createTextNode(String.fromCharCode(160)));
				}
			}
		}

		// remember the DOM Node for this menu item
		this.element = oElement;

		return oElement;
	}
};

// ************************************
// CC.MenuButton
// ************************************

CC.MenuButton = function(id, action) {
	this.constructor(id, action);
}

CC.MenuButton.prototype = new CC.Menu();