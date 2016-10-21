/*
 * $Header$
 * $Revision$
 * $Date$
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

/*
+ ---------------------------------------------------------------------------------+
| Object....: ISOption();
| Function..: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 12.02.2006  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ISOption(key, label, state, style, styleClass, indent, src, alt, width, height) {
	this.key             = key;               // Parameter 1: the options key
	this.label           = label;             // Parameter 2: the options label
	this.state           = state;             // Parameter 3: the selection state
	this.style           = style;             // Parameter 4: Style (option element)
	this.styleClass      = styleClass         // Parameter 5: Style class (option element)
	this.indent          = indent;            // Parameter 6: indent
	this.src             = src;               // Parameter 7: image name
	this.alt             = alt;               // Parameter 8: image alternate name
	this.width           = width;             // Parameter 9: image width
	this.height          = height;            // Parameter 10: image height
}
function ISOption_getKey() {
	return this.key;
}
function ISOption_getLabel() {
	return this.label;
}
function ISOption_isSelected() {
	return this.state;
}
function ISOption_setSelected(flag) {
	this.state = flag;
}
function ISOption_getStyle() {
	return this.style;
}
function ISOption_getStyleClass() {
	return this.styleClass;
}
function ISOption_getIndent() {
	return this.indent;
}
function ISOption_getSrc() {
	return this.src;
}
function ISOption_getAlt() {
	return this.alt;
}
function ISOption_getWidth() {
	return this.width;
}
function ISOption_getHeight() {
	return this.height;
}
function ISOption_toString() {
	var out = '';
	out += '******* ISOption *********' + LF
	out += 'Key........: ' + this.key + LF;
	out += 'Label......: ' + this.label + LF;
	out += 'Selected...: ' + this.state + LF;
	out += 'Indent.....: ' + this.indent + LF;
	out += 'Source.....: ' + this.src + LF;
	out += 'Alt........: ' + this.alt + LF;
	out += 'Width......: ' + this.width + LF;
	out += 'Height.....: ' + this.height + LF;
	return out;
}
new ISOption();
ISOption.prototype.getKey        = ISOption_getKey;
ISOption.prototype.getLabel      = ISOption_getLabel;
ISOption.prototype.isSelected    = ISOption_isSelected;
ISOption.prototype.setSelected   = ISOption_setSelected;
ISOption.prototype.getStyle      = ISOption_getStyle;
ISOption.prototype.getStyleClass = ISOption_getStyleClass;
ISOption.prototype.getIndent     = ISOption_getIndent;
ISOption.prototype.getSrc        = ISOption_getSrc;
ISOption.prototype.getAlt        = ISOption_getAlt;
ISOption.prototype.getWidth      = ISOption_getWidth;
ISOption.prototype.getHeight     = ISOption_getHeight;
ISOption.prototype.toString      = ISOption_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageSelectControl();
| Function..: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 12.02.2006  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ImageSelectControl(id, property, size, style, styleClass, disabled) {
	this.id             = id;
	this.property       = (arguments.length >= 2) ? property : id;        // Property
	this.size           = (arguments.length >= 3) ? size : 1;             // Not supported within this version
	this.style          = (arguments.length >= 4) ? style : null;         // style for the control
	this.styleClass     = (arguments.length >= 5) ? styleClass : null;    // Styleclass
	this.disabled       = (arguments.length >= 6) ? disabled : false;     // disabled flag. Not supported within this version
	this.options        = new Array();
	this.width          = '100%';
	
	this.objImage       = null;
	this.divImageSelect = null;
	this.objArrow       = null;
	this.arrowCell      = null;
	this.divOptions     = null;
	this.maxImgWidth    = 0;
	this.maxImgHeight   = 0;
	
	this.PREFIX_IS_IMG     = 'img_';
	this.PREFIX_IS_ARROW   = 'arrow_';
	this.PREFIX_IS_OPTIONS = 'options_';
	this.PREFIX_IS_HIDDEN  = 'hidden_';
	this.PREFIX_IS_LABEL   = 'label_';
	
	this.STYLE_ITEM     = (null != this.styleClass) ? this.styleClass + ' cbitem'    : 'cbitem';
	this.STYLE_ITEM_SEL = (null != this.styleClass) ? this.styleClass + ' cbitemsel' : 'cbitemsel';
}
function ImageSelectControl_addOption(key, label, state, style, styleClass, indent, src, alt, width, height) {
	var isoption = new ISOption(key, label, state, style, styleClass, indent, src, alt, width, height);
	this.options[this.options.length] = isoption;
	
	if (width > this.maxImgWidth) {
		this.maxImgWidth = width;
	}
	
	if (height > this.maxImgHeight) {
		this.maxImgHeight = height;
	}
}
function ImageSelectControl_getId() {
	return this.id;
}
function ImageSelectControl_getProperty() {
	return this.property;
}
function ImageSelectControl_getSize() {
	return this.size;
}
function ImageSelectControl_getStyle() {
	return this.style;
}
function ImageSelectControl_getStyleClass() {
	return this.styleClass;
}
function ImageSelectControl_isDisabled() {
	return this.disabled;
}
function ImageSelectControl_getWidth() {
	return this.width;
}
function ImageSelectControl_getOption(key) {
	for (var i=0; i < this.options.length; i++) {
		if (this.options[i].getKey() == key) {
			return this.options[i];
		}
	}
	
	return null;
}
function ImageSelectControl_getOptions() {
	return this.options;
}
function ImageSelectControl_getSelectedOption() {
	for (var i=0; i < this.options.length; i++) {
		if (this.options[i].isSelected()) {
			return this.options[i];
		}
	}
	
	return null;
}
function ImageSelectControl_selectedOption(key) {
	// reset the current selected option
	var isoption = this.getSelectedOption();
	
	if (null != isoption) {
		isoption.setSelected(false);
	}
	
	isoption = this.getOption(key);
	
	if (null != isoption) {
		isoption.setSelected(true);
	}
}
function ImageSelectControl_setupHandler() {
	this.divImageSelect = document.getElementById(this.id);
	this.objImage       = document.getElementById(this.PREFIX_IS_IMG + this.id);
	this.objLabel       = document.getElementById(this.PREFIX_IS_LABEL + this.id);
	this.objArrow       = document.getElementById(this.PREFIX_IS_ARROW + this.id);
	this.divOptions     = document.getElementById(this.PREFIX_IS_OPTIONS + this.id);
	this.arrowCell      = this.objArrow.parentNode;
	
	var ctrl = this;
	
	// handler to open the dropdownlist
	this.arrowCell.onclick = function(event) {
		var height = (null != ctrl.getSelectedOption()) ? ctrl.getSelectedOption().getHeight(): 18;
		var width  = ctrl.divImageSelect.style.width;
		
		ctrl.divOptions.style.left  = ctrl.divImageSelect.style.left;
		ctrl.divOptions.style.top   = height + 5;
		ctrl.divOptions.style.width = (width.indexOf('%') > 0) ? '100%' : ctrl.divImageSelect.style.width;
		
		var visible = (ctrl.divOptions.style.display == 'block') ? true : false;
		
		if (visible) {
			// if visible close the list box
			ctrl.divOptions.style.display = 'none';
		} else {
			// show the listbox
			ctrl.divOptions.style.display = 'block';
			
			// register handlers for the options
			var options = ctrl.divOptions.getElementsByTagName('div');
			
			for (var i=0; i < options.length; i++) {
				var item = options[i].childNodes[0]; // set style on table
				item.className = ctrl.STYLE_ITEM;     // rest the style
				
				item.onmouseover = function() {
					this.className = ctrl.STYLE_ITEM_SEL;
				}
				
				item.onmouseout = function() {
					this.className = ctrl.STYLE_ITEM;
				}
				
				item.onclick = function() {
					// If the option has an image --> swap the image
					ctrl.swapOption(ctrl.objImage, ctrl.objLabel, this.parentNode);
					
					// 
					ctrl.selectedOption(this.parentNode.getAttribute('value'));
					
					// close list
					ctrl.divOptions.style.display = 'none';
					
					// update hidden field
					ImageSelectControlPainter.doCreateHidden(ctrl);
				}
				
				item.oncontextmenu = function() {
					// disable contextmenu
					return false;
				}
			}
			
			ctrl.divOptions.focus();
	//	ctrl.moveToItem(ctrl.selectedIndex);
		}
	};
	
	this.arrowCell.ondblclick = function(event) {
		this.fireEvent('onclick');
	}
}
function ImageSelectControl_swapOption(image, label, node) {
	var newKey    = node.getAttribute('value');
	var newOption = this.getOption(newKey);
	
	// check if we have an image
	if (null != image && null != newOption) {
		image.src    = newOption.getSrc();
		image.height = newOption.getHeight();
		image.width  = newOption.getWidth();
		image.alt    = newOption.getAlt();
		image.hspace = 3;
		image.vspace = 1;
		
		// set td-width
		image.parentNode.width = image.width;
	} else {
		image.src    = '';
		image.height = 0;
		image.width  = 0;
		image.hspace = 3;
		image.vspace = 1;
		
		// set td-width
		image.parentNode.width = image.width;
	}
	
	// check if we have a label
	if (null != label && null != newOption) {
		label.innerHTML = newOption.getLabel();
	}
}
function ImageSelectControl_toString() {
	var out = '';
	out += '******* ImageSelect *********' + LF
	out += 'Id..........: ' + this.id + LF;
	out += 'Property....: ' + this.property + LF;
	out += 'Size........: ' + this.size + LF;
	out += 'Style.......: ' + this.style + LF;
	out += 'StyleClass..: ' + this.styleClass + LF;
	out += 'Disabled....: ' + this.disabled + LF;
	out += 'Option Size.: ' + this.getOptions().length + LF;
	return out;
}
new ImageSelectControl();
ImageSelectControl.prototype.getId             = ImageSelectControl_getId;
ImageSelectControl.prototype.getProperty       = ImageSelectControl_getProperty;
ImageSelectControl.prototype.getSize           = ImageSelectControl_getSize;
ImageSelectControl.prototype.getStyle          = ImageSelectControl_getStyle
ImageSelectControl.prototype.getStyleClass     = ImageSelectControl_getStyleClass
ImageSelectControl.prototype.isDisabled        = ImageSelectControl_isDisabled;
ImageSelectControl.prototype.getWidth          = ImageSelectControl_getWidth;
ImageSelectControl.prototype.addOption         = ImageSelectControl_addOption;
ImageSelectControl.prototype.getOption         = ImageSelectControl_getOption;
ImageSelectControl.prototype.getOptions        = ImageSelectControl_getOptions;
ImageSelectControl.prototype.getSelectedOption = ImageSelectControl_getSelectedOption;
ImageSelectControl.prototype.selectedOption    = ImageSelectControl_selectedOption;
ImageSelectControl.prototype.setupHandler      = ImageSelectControl_setupHandler;
ImageSelectControl.prototype.swapOption        = ImageSelectControl_swapOption;
ImageSelectControl.prototype.toString          = ImageSelectControl_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageSelectControlPainterData();
| Function..: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 12.02.2006  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ImageSelectControlPainterData(imgSelect) {
	this.imgSelect    = imgSelect;
	this.IMG_ARROW    = 'fw/html/image/select/arrow.gif';
	this.IMG_BGARROW  = 'fw/html/image/select/background.gif'
}
new ImageSelectControlPainterData();


/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageSelectControlPainter();
| Function..: Responsible for drawing the TabSet
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 12.02.2006  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ImageSelectControlPainter() {

}
function ImageSelectControlPainter_render(ispData) {
	var imgSelect = ispData['imgSelect'];
	var className = (null != imgSelect.getStyleClass()) ? imgSelect.getStyleClass() : 'cbt';

	var div = document.getElementById(imgSelect.getId());

	// Element found?
	if (null == div) {
		return; //exit
	}

	var table = document.createElement('table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border      = 0;
	table.className   = className;
	table.width       = imgSelect.getWidth();
	
	// create Row
	var row  = table.insertRow(0);
	
	// create cells
	ImageSelectControlPainter.doCreateImageCell(row, ispData);
	ImageSelectControlPainter.doCreateLabelCell(row, ispData);
	ImageSelectControlPainter.doCreateArrowCell(row, ispData);
	div.appendChild(table);
	div.appendChild(ImageSelectControlPainter.doCreateHidden(imgSelect));
	div.appendChild(ImageSelectControlPainter.doCreateOptions(ispData));
	imgSelect.setupHandler();
}

function ImageSelectControlPainter_doCreateImageCell(row, ispData) {
	var imgSelect = ispData['imgSelect'];
	var id = imgSelect['PREFIX_IS_IMG'] + imgSelect.getId();;
	var selectedOption = imgSelect.getSelectedOption();
	var node = null;

	var cell = row.insertCell(0);
	
	if (null != selectedOption) {
		var img    = document.createElement('img');
		img.src    = selectedOption.getSrc();
		img.alt    = selectedOption.getAlt();
		img.width  = selectedOption.getWidth();
		img.height = selectedOption.getHeight();
		img.id     = id;
		img.hspace = 3;
		img.vspace = 1;
		
		cell.setAttribute('width', img.width);
		node = img;
	} else {
		node = document.createTextNode('Please select');
		cell.width = 0;
	}
	
	cell.appendChild(node);
}
function ImageSelectControlPainter_doCreateLabelCell(row, ispData) {
	var imgSelect = ispData['imgSelect'];
	var id = imgSelect['PREFIX_IS_LABEL'] + imgSelect['id'];
	var selectedOption = imgSelect.getSelectedOption();
	
	var div = document.createElement('div');
	div.id = id;
	div.style.paddingLeft  = '1px';
	div.style.paddingRight = '5px';
	div.style.top  = 0;
	div.style.left = 0;
	div.className = 'cblabel';
	
	//var nobr = document.createElement('nobr');
	//nobr.appendChild(document.createTextNode(selectedOption.getLabel()))
	//div.appendChild(nobr);
	div.innerHTML = '<nobr>' + selectedOption.getLabel() + '</nobr>';
	
	var cell = row.insertCell(row.cells.length);
	cell.appendChild(div);
}
function ImageSelectControlPainter_doCreateArrowCell(row, ispData) {
	var imgSelect = ispData['imgSelect'];
	var id = imgSelect['PREFIX_IS_ARROW'] + imgSelect['id'];
	
	var cell = row.insertCell(row.cells.length);
	cell.setAttribute('background', ispData['IMG_BGARROW']);
	cell.setAttribute('height', '100%');
	cell.setAttribute('width', '15');
	cell.className = 'cbarrow';
	
	var img = document.createElement('img');
	img.id   = id;
	img.src =  ispData['IMG_ARROW'];
	
	cell.appendChild(img);
}
function ImageSelectControlPainter_doCreateOptions(ispData) {
	var imgSelect = ispData['imgSelect'];
	var id = imgSelect['PREFIX_IS_OPTIONS'] + imgSelect['id'];
	var options   = imgSelect.getOptions();
	
	var div = document.createElement('div');
	div.id = id;
	div.style.display = 'none';
	div.style.left = 0;
	div.style.top  = 0;
	div.style.position = 'absolute';
	div.className = 'cbol';
	
	for (var i=0 ; i < options.length; i++) {
		var option = ImageSelectControlPainter.doCreateOption(options[i]);
		div.appendChild(option);
	}
	
	return div;
}
function ImageSelectControlPainter_doCreateOption(option) {
	var div = document.createElement('div');
	div.className = 'cbitem';
	div.setAttribute('value', option.getKey());
	div.style.top = 0;
	div.style.left = 0;
	
	var img = document.createElement('img');
	img.src    = option.src;
	img.align  = 'middle';
	img.vspace = "1";
	img.hspace = "1";
	img.border = "0";
	img.alt    = option.getAlt();
	img.style.height = option.getHeight();
	img.style.width  = option.getWidth();
	
	var table = document.createElement('table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border      = 0;
	table.width = '98%';
	
	var row     = table.insertRow(0);
	row.valign  = 'middle';
	
	var cellA   = row.insertCell(0);
	cellA.width = option.getWidth() + 5;
	
	var cellB   = row.insertCell(row.cells.length);
	cellB.className = 'cblabel';
	cellB.align     = 'left';
	cellB.setAttribute('nowrap', 'nowrap');
	
	cellA.appendChild(img);
	//cellB.appendChild(document.createTextNode(option.getLabel()));
	cellB.innerHTML = option.getLabel();
	
	div.appendChild(table);
	return div;
}
function ImageSelectControlPainter_doCreateHidden(iscontrol) {
	var isoption  = iscontrol.getSelectedOption();
	var id        = iscontrol['PREFIX_IS_HIDDEN'] + iscontrol.getId();
	var input     = null;
	
	input = document.getElementById(id);
	
	if (null == input) {
		input       = document.createElement('input');
		input.type  =  'hidden';
		input.name  = iscontrol.getProperty();
		input.id    = id;
	}
	
	// set selected value
	input.value = isoption.getKey();
	return input;
}
new ImageSelectControlPainter();
ImageSelectControlPainter.render            = ImageSelectControlPainter_render;
ImageSelectControlPainter.doCreateImageCell = ImageSelectControlPainter_doCreateImageCell;
ImageSelectControlPainter.doCreateLabelCell = ImageSelectControlPainter_doCreateLabelCell;
ImageSelectControlPainter.doCreateArrowCell = ImageSelectControlPainter_doCreateArrowCell;
ImageSelectControlPainter.doCreateOptions   = ImageSelectControlPainter_doCreateOptions;
ImageSelectControlPainter.doCreateOption    = ImageSelectControlPainter_doCreateOption;
ImageSelectControlPainter.doCreateHidden    = ImageSelectControlPainter_doCreateHidden;

/*
+ ---------------------------------------------------------------------------------+
| Object.....: MultiLevelSelect()
| Function...:
|
| date        author            description
| ----------  ----------------  ----------------------------------------------------
| 11.09.2007  H.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function MLSelect(id, formElement) {
	this.id            = id;                                            // identifier for the tree
	this.root          = null;                                          // the root element of the tree (an object of type treegroup)
	this.disabled      = false;                                         // control is disabled
	this.maxlength     = -1;                                            // Maxlength of the labels
	this.styleClass    = null;                                          // Customized sytlesheet class
	this.levels        = 0;                                             // Number of selectboxes
	this.emptyText     = " ";                                           // the default empty text

	this.onChange = function(oSelect, nLevel) {
		var key = "";
		var cascade = false;

		// clear and disable all following select boxes
		for (var i = nLevel + 1; i <= this.levels; i++) {
			var oNextSelect = document.getElementById(this.id + "_" + i);
			while (oNextSelect.firstChild != null) {
				oNextSelect.removeChild(oNextSelect.firstChild);
			}
			
			oNextSelect.disabled = true;
		}

		var hasSelection = false;
		
		if ((oSelect.selectedIndex >= 0) && (oSelect.selectedIndex < oSelect.options.length)) {
			hasSelection = oSelect.options[oSelect.selectedIndex].value != "";
		}

		if (!hasSelection) {
			// select the first element
			oSelect.selectedIndex = 0;

			// retrieve the selected value from the previous select box
			if (nLevel > 1) {
				var oPrevSelect = document.getElementById(this.id + "_" + (nLevel - 1));
				if (oPrevSelect.selectedIndex >= 0) {
					key = oPrevSelect.options[oPrevSelect.selectedIndex].value;
				}
			}				
		} else {
			key = oSelect.options[oSelect.selectedIndex].value;

			// fill option list of select box nLevel+1 and enable
			if ((nLevel + 1) <= this.levels) {
				var node = TreeHelp.getNodeById(this.root, key);
				if (node instanceof TreeGroup) {
					var oNextSelect = document.getElementById(this.id + "_" + (nLevel + 1));
					var oOption = null;
					
					// add a empty option when the node is selectable
					if (node.isSelected()) {
						oOption = document.createElement("option")
						oOption.value = "";
						oOption.selected = true;
						oOption.appendChild(document.createTextNode(this.emptyText));
						oNextSelect.appendChild(oOption);
					}

					// add all child options
					for (var i = 0; i < node.getChildNodes().length; i++) {
						var child = node.getChild(i);

						oOption = document.createElement("option")
						oOption.value = child.getId();
				        oOption.appendChild(document.createTextNode(child.getLabel()));
						oNextSelect.appendChild(oOption);
					}
	
					// when the node is not selectable select the fist element
					// in the child collection
					if (!node.isSelected() && (oNextSelect.options.length > 0)) {
						oNextSelect.selectedIndex = 0;
						oNextSelect.onchange();
						cascade = true;
					}

					oNextSelect.disabled = false;
				}
			}
		}

		// update the hidden field with the selected value
		// from the previous select box
		if (!cascade) {
			var oHidden = document.getElementById(this.id + "_h");
			oHidden.value = key;
		}
	};
}

function MLSelect_getId() {
	return this.id;
}
function MLSelect_setRoot(root) {
	if ((root instanceof TreeGroup) || (root instanceof TreeNode)) {
		this.root = root;
	}
}
function MLSelect_getRoot(root) {
	return this.root;
}
function MLSelect_isDisabled() {
	return this.disabled;
}
function MLSelect_setDisabled(disabled) {
	this.disabled = disabled;
}
function MLSelect_getMaxlength() {
	return this.maxlength;
}
function MLSelect_setMaxlength(maxlength) {
	this.maxlength = maxlength;
}
function MLSelect_getLevels() {
	return this.levels;
}
function MLSelect_setLevels(levels) {
	this.levels = levels;
}
function MLSelect_setStyleClass(styleClass) {
	this.styleClass = styleClass;
}
function MLSelect_getStyleClass() {
	return this.styleClass;
}
function MLSelect_getEmptyText() {
	return this.emptyText;
}
function MLSelect_setEmptyText(emptyText) {
	this.emptyText = emptyText;
}
function MLSelect_toString() {
	var out = '';
	out += '********* Tree ***********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'Disabled.......: ' + this.disabled + LF;
	out += 'StyleClass.....: ' + this.styleClass + LF;
	out += 'Maxlength......: ' + this.maxlength + LF;
	out += 'Empty Text.....: ' + this.emptyText + LF;
	return out;
}

new MLSelect();
MLSelect.prototype.getId             = MLSelect_getId;
MLSelect.prototype.setRoot           = MLSelect_setRoot;
MLSelect.prototype.getRoot           = MLSelect_getRoot;
MLSelect.prototype.setDisabled       = MLSelect_setDisabled;
MLSelect.prototype.getMaxlength      = MLSelect_getMaxlength;
MLSelect.prototype.setMaxlength      = MLSelect_setMaxlength;
MLSelect.prototype.getLevels         = MLSelect_getLevels;
MLSelect.prototype.setLevels         = MLSelect_setLevels;
MLSelect.prototype.setStyleClass     = MLSelect_setStyleClass;
MLSelect.prototype.getStyleClass     = MLSelect_getStyleClass;
MLSelect.prototype.setEmptyText      = MLSelect_setEmptyText;
MLSelect.prototype.getEmptyText      = MLSelect_getEmptyText;
MLSelect.prototype.toString          = MLSelect_toString;