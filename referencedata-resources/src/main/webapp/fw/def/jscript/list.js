/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/def/jscript/list.js,v 1.21 2006/06/13 11:45:59 P001002 Exp $
 * $Revision: 1.21 $
 * $Date: 2006/06/13 11:45:59 $
 *
 * ====================================================================
 *
 * Copyright (c) 2000 - 2004 SCC Informationssysteme GmbH. All rights
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
| Purpose..: The ListControl object provides some javascript helper functions
|            
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 08.04.2004  G.Schulz (SCC)    Inital version
| 10.12.2004  G.Schulz (SCC)    Changed: getCheckBoxes / getRadioButtons / getElementsByType
| 14.12.2004  G.Schulz (SCC)    New function getCheckBoxes
| 28.01.2005  G.Schulz (SCC)    Fix: SelectMode.SINGLE - Allow uncheck item
|                               New function hasCheckedItem
| 07.02.2005  G.Schulz (SCC)    New function to set the cursor to the first input element within a list
| 06.07.2005  G.Schulz (SCC)    function isFormElement added
+ ---------------------------------------------------------------------------------+
*/
function ListControl(id, formElement) {
	this.id          = id;                                               // Id of the ListControl (=styleId within the jsp)
	this.formElement = (arguments.length >= 2) ? formElement : false;    // indicates if the TabSet should act as a form element.
	this.PREFIX      = 'lc_';
}
function ListControl_isFormElement() {
	return this.formElement;
}
function ListControl_handleCheckState(obj, column, selectMode) {

	if (!obj.checked) {
		return;
	}
	
	var items = this.getCheckBoxes(column);

	// if mode is set to single select
	// delete all checkboxes
	if (selectMode == SelectMode.SINGLE && items.length > 1) {
		ListHelp.uncheckAll(items);

		// check the one which was checked at least
		
		obj.checked = true;
	}
}
function ListControl_getSpan() {
	// return the node (=span) where the control is embedded
	return document.getElementById(this.PREFIX + this.id);
}
function ListControl_getCheckBoxes(column) {
	return this.getElementsByType('checkbox', column);
}
function ListControl_getRadioButtons(column) {
	return this.getElementsByType('radio', column);
}
function ListControl_hasCheckedItem(type, column) {
	// test if the column has a check item

	if ('radio' != type.toLowerCase() && 'checkbox' != type.toLowerCase()) {
		return false;
	}

	var arr = this.getElementsByType(type, column);

	for (var i = 0; i < arr.length; i++) {
		if (true == arr[i].checked) {
			return true;
		}
	}

	return false;
}
function ListControl_getElementsByType(type, column) {
	var elements = new Array();
	var root = this.getSpan();

	var arr = root.getElementsByTagName('Input');

	for (var i=0; i < arr.length; i++) {

		if (arr[i].type == type.toLowerCase()) {

		/*
			if (null != arr[i].id && '' != arr[i].id) {
				var name = arr[i].id.split('_');

				// belongs the field to the column?
				if (name.length == 2 && name[0] == column) {
					elements[elements.length] = arr[i];
				}
			} 
		*/
			if (null != arr[i].name && '' != arr[i].name) {
				// The name includes the information about the column name 
				var decName  = CCUtility.decodeURIComponent(arr[i].name);
				var property = decName.split(';')[0].split('=')[1];

				// belongs the field to the column?
				if (property == column) {
					elements[elements.length] = arr[i];
				}
			}
		}
	}

	return elements;
}
function ListControl_checkAll(column) {
	var items = this.getCheckBoxes(column);
	ListHelp.checkAll(items);
}
function ListControl_uncheckAll(column) {
	var items = this.getCheckBoxes(column);
	ListHelp.uncheckAll(items);
}
function ListControl_getRows(includeheader) {
	var arr = this.getSpan().getElementsByTagName('tr');
	var rows = new Array();

	// collect all rows
	for (var i=0; i < arr.length; i++) {
		var className = arr[i].className;
		
		if (null != includeheader && className == 'header') {
			rows[rows.length] = arr[i];
		}
		
		if (className == 'even' || className == 'evens' || className == 'odd' || className == 'odds') {
			rows[rows.length] = arr[i];
		}
	}

	return rows;
}
function ListControl_getRow(rowIndex) {
	var rows = this.getRows();
	
	if (rowIndex < rows.length) {
		return rows[rowIndex];
	} else {
		return null;
	}
}
function ListControl_getCell(rowIndex, cellIndex) {
	var row = this.getRow(rowIndex);

	if (null == row) {
		return null;
	}

	if (cellIndex < row.cells.length) {
		return row.cells[cellIndex];
	} else {
		return null;
	}
}
function ListControl_getFirstInputElement(cell) {
// TODO ----- what if textarea is the first element 
// or maybe an other element should receive focus

	// check input
	var arr  = cell.getElementsByTagName('input');

	for (var i=0; i < arr.length; i++) {
		if (arr[i].type == 'text') {
			return arr[i];
		}
	}

	// check textarea
	var arr  = cell.getElementsByTagName('textarea');

	if (null != arr && arr.length > 0) {
		return arr[0];
	}
	
	return null;
}

function ListControl_focus(rowIndex, cellIndex) {
	var arg = arguments.length;

	// no rowIndex, cellIndex was specified
	if (arg == 0) {
		var rows = this.getRows();
		
		if (null == rows) {
			return;
		}
		
		for (var i=0; i < rows.length; i++) {
			for (var j=0; j < rows[i].cells.length; j++) {
				var cell  = this.getCell(i, j);
				var input = this.getFirstInputElement(cell);
				
				if (null != input) {
					input.focus();
					return;
				}
			}
		}
	}
	
	// only the rowIndex was specified
	if (arg == 1) {
		var row = this.getRow(rowIndex);

		if (null == row) {
			return;
		}

		for (var i=0; i < row.cells.length; i++) {
			var input = this.getFirstInputElement(row.cells[i]);
				
			if (null != input) {
				input.focus();
				return;
			}
		}
	}
		
	// rowIndex, cellIndex was specified
	if (arg == 2) {
		var cell  = this.getCell(rowIndex, cellIndex);
		var input = this.getFirstInputElement(cell);

		if (null != input) {
			input.focus();
		}
	}
}
function ListControl_displayColumn(index, flag) {
	var rows  = this.getRows(true);
	var style = (flag != null && flag == true) ? 'block' : 'none';
	
	for (var i=0; i < rows.length; i++) {
		var cells = rows[i].cells;
			
		if (index < cells.length) {
			var cell = cells[index];
			cell.style.display = style;
		}
	}
}

function ListControl_moveColumn(index, newpos) {
}

new ListControl();
ListControl.prototype.isFormElement        = ListControl_isFormElement;
ListControl.prototype.handleCheckState     = ListControl_handleCheckState;
ListControl.prototype.getSpan              = ListControl_getSpan;
ListControl.prototype.getCheckBoxes        = ListControl_getCheckBoxes;
ListControl.prototype.getRadioButtons      = ListControl_getRadioButtons;
ListControl.prototype.getElementsByType    = ListControl_getElementsByType;
ListControl.prototype.hasCheckedItem       = ListControl_hasCheckedItem;
ListControl.prototype.checkAll             = ListControl_checkAll;
ListControl.prototype.uncheckAll           = ListControl_uncheckAll;
ListControl.prototype.getRows              = ListControl_getRows;
ListControl.prototype.getRow               = ListControl_getRow;
ListControl.prototype.getCell              = ListControl_getCell;
ListControl.prototype.getFirstInputElement = ListControl_getFirstInputElement;
ListControl.prototype.focus                = ListControl_focus;
ListControl.prototype.displayColumn        = ListControl_displayColumn;
ListControl.prototype.moveColumn           = ListControl_moveColumn;
