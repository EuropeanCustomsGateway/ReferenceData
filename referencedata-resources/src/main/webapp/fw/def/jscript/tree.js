/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/def/jscript/tree.js,v 1.50 2007/02/17 08:14:27 P001001 Exp $
 * $Revision: 1.50 $
 * $Date: 2007/02/17 08:14:27 $
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

/***********************************************************************
 * Name:
 *        tree.js
 *
 * Function:
 *        Provide a dynamic tree in a web browser.
 *
 * Author:
 *        Gernot Schulz (gschulz@scc-gmbh.com)
 *
 * Status:
 *        Version 1, Release 1
 *
 * Environment:
 *        This is a PLATFORM-INDEPENDENT source file. As such it may
 *        contain no dependencies on any specific operating system
 *        environment or hardware platform.
 *
 * Description:
 *        
 *
 * Dependencies:
 *        resourcemap.js
 *        environment.js
 *        common.js
 *
 * TESTED ON:  - InternetExplorer
 *             - Netscape Navigator
 *             - Mozilla
 *             - Firefox
 *             - Safari
 *
 ***********************************************************************/


/*
+ ---------------------------------------------------------------------------------+
| Object.....: Tree()
| Function...: 
|
| date        author            description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function Tree(id, formElement) {
	this.id            = id;                                            // identifier for the tree
	this.root          = null;                                          // the root element of the tree (an object of type treegroup)
	this.runAt         = RunAt.SERVER;
	this.linesAtRoot   = true;                                          // Show lines at the top level
	this.expandMode    = ExpandMode.SINGLE;
	this.showLines     = true;
	this.showRoot      = true;                                          // false, if the root element should be hidden
	this.action        = '';                                            // action to process if an item is clicked
	this.checkboxes    = false;                                         // true if checkboxes should be displayed
	this.imageMap      = null;                                          // imagemap used for tree icons
	this.formElement   = (arguments.length >= 2) ? formElement : false; // indicates if the TabSet should act as a form element.
	this.disabled      = false;                                         // control is disabled
	this.maxlength     = -1;                                            // Maxlength of the labels
	this.groupSelect   = true;                                          // Drilldow on a group allowd
	this.styleClass    = null;                                          // Customized sytlesheet class
	this.imageMap      = null;                                          // an optional associated imagemap for image resources
	this.selectNode    = true;                                          // highlight the node if it was selected on a drilldown
	this.ajax          = false;
}
function Tree_getId() {
	return this.id;
}
function Tree_setLinesAtRoot(flag) {
	this.linesAtRoot = flag;
}
function Tree_setExpandMode(expandMode) {
	this.expandMode = expandMode;
}
function Tree_getExpandMode() {
	return this.expandMode;
}
function Tree_setCheckboxes(checkboxes) {
	this.checkboxes = checkboxes;
}
function Tree_isShowCheckboxes() {
	return this.checkboxes;
}
function Tree_setShowRoot(showRoot) {
	this.showRoot = showRoot;
}
function Tree_setRunAt(runat) {
	this.runAt = runat;
}
function Tree_getRunAt() {
	return this.runAt;
}
function Tree_setRoot(root) {
	if ((root instanceof TreeGroup) || (root instanceof TreeNode)) {
		this.root = root;
	}
}
function Tree_getRoot(root) {
	return this.root;
}
function Tree_setAction(action) {
	this.action = action;
}
function Tree_getAction() {
	return this.action;
}
function Tree_setImageMap(imageMap) {
	if (imageMap instanceof ImageMap) {
		this.imageMap = imageMap;
	}
}
function Tree_getImageMap() {
	return this.imageMap;
}
function Tree_isFormElement() {
	return this.formElement;
}
function Tree_setFormElement(formElement) {
	this.formElement = formElement;
}
function Tree_isDisabled() {
	return this.disabled;
}
function Tree_setDisabled(disabled) {
	this.disabled = disabled;
}
function Tree_getMaxlength() {
	return this.maxlength;
}
function Tree_setMaxlength(maxlength) {
	this.maxlength = maxlength;
}
function Tree_isGroupSelect() {
	return this.groupSelect;
}
function Tree_setGroupSelect(groupSelect) {
	this.groupSelect = groupSelect;
}
function Tree_isShowLines() {
	return this.showLines;
}
function Tree_setShowLines(showLines){
	this.showLines = showLines;
}
function Tree_setLocal(local) {
	//this.local = local;
}
function Tree_setStyleClass(styleClass) {
	this.styleClass = styleClass;
}
function Tree_getStyleClass() {
	return this.styleClass;
}
function Tree_setSelectNode(selectNode) {
	this.selectNode = selectNode;
}
function Tree_isSelectNode() {
	return this.selectNode;
}
function Tree_serializeState() {
	return TreeHelp.serializeState(this.root);
}
function Tree_setAjax(flag) {
	this.ajax = flag;
}
function Tree_isAjax() {
	return this.ajax;
}
function Tree_toString() {
	var out = '';
	out += '********* Tree ***********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'ExpandMode.....: ' + this.expandMode + LF;
	out += 'LinesAtRoot....: ' + this.linesAtRoot + LF;
	out += 'Show Root......: ' + this.showRoot + LF;
	out += 'Show Lines.....: ' + this.showLines + LF;
	out += 'GroupSelect....: ' + this.groupSelect + LF;
	out += 'Action.........: ' + this.action + LF;
	out += 'Checkboxes.....: ' + this.checkboxes + LF;
	out += 'FormElement....: ' + this.formElement + LF;
	out += 'Disabled.......: ' + this.disabled + LF;
	out += 'StyleClass.....: ' + this.styleClass + LF;
	out += 'Maxlength......: ' + this.maxlength + LF;
	out += 'ImageMap.......: ' + (this.imageMap != null) + LF;
	out += 'AJAX...........: ' + this.ajax + LF;
	return out;
}
new Tree();
Tree.prototype.getId             = Tree_getId;
Tree.prototype.setLinesAtRoot    = Tree_setLinesAtRoot;
Tree.prototype.setExpandMode     = Tree_setExpandMode;
Tree.prototype.getExpandMode     = Tree_getExpandMode;
Tree.prototype.setRoot           = Tree_setRoot;
Tree.prototype.getRoot           = Tree_getRoot;
Tree.prototype.setAction         = Tree_setAction;
Tree.prototype.getAction         = Tree_getAction;
Tree.prototype.setImageMap       = Tree_setImageMap;
Tree.prototype.isFormElement     = Tree_isFormElement;
Tree.prototype.setFormElement    = Tree_setFormElement;
Tree.prototype.setCheckboxes     = Tree_setCheckboxes;
Tree.prototype.isShowCheckboxes  = Tree_isShowCheckboxes;
Tree.prototype.setShowRoot       = Tree_setShowRoot;
Tree.prototype.setRunAt          = Tree_setRunAt;
Tree.prototype.getRunAt          = Tree_getRunAt;
Tree.prototype.setDisabled       = Tree_setDisabled;
Tree.prototype.isGroupSelect     = Tree_isGroupSelect;
Tree.prototype.setGroupSelect    = Tree_setGroupSelect;
Tree.prototype.isShowLines       = Tree_isShowLines;
Tree.prototype.setShowLines      = Tree_setShowLines;
Tree.prototype.setLocal          = Tree_setLocal;
Tree.prototype.getMaxlength      = Tree_getMaxlength;
Tree.prototype.setMaxlength      = Tree_setMaxlength;
Tree.prototype.setStyleClass     = Tree_setStyleClass;
Tree.prototype.getStyleClass     = Tree_getStyleClass;
Tree.prototype.setImageMap       = Tree_setImageMap;
Tree.prototype.getImageMap       = Tree_getImageMap;
Tree.prototype.setSelectNode     = Tree_setSelectNode;
Tree.prototype.isSelectNode      = Tree_isSelectNode;
Tree.prototype.serializeState    = Tree_serializeState;
Tree.prototype.setAjax           = Tree_setAjax;
Tree.prototype.isAjax            = Tree_isAjax;
Tree.prototype.toString          = Tree_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object.....: TreeGroup ()
| Function...: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Inital Version
|
+ ---------------------------------------------------------------------------------+
*/
function TreeGroup(id, label, tooltip, nodeState, checkState, selected, exprEvalStr, target, enabled) {
	var a = arguments;
	
	this.childNodes     = new Array();                                          // Buffer for the child nodes
	this.clientHandler  = new Array();                                          // ClientHandler associated with this node
	
	// properties
	this.id            = id;                                                    // identifier for the tree node
	this.parent        = null;                                                  // the parent or root node
	this.label         = label;                                                 // label to display on the node
	this.tooltip       = (a.length >= 3) ? tooltip : '';                        // Tooltip
	this.nodeState     = (a.length >= 4) ? nodeState : NodeState.COLLAPSE;      // state of the node (expand, collapse, expandex)
	this.checkState    = (a.length >= 5) ? checkState : CheckState.UNCHECKED;   // indicates if the node is checked
	this.selected      = (a.length >= 6) ? selected : false;                    // true, if the node is selected
	this.exprEvalStr   = (a.length >= 7) ? exprEvalStr : null;                  // RegEx to match for the image
	this.target        = (a.length >= 8) ? target : null;                       // the target attribute
	this.enabled       = (a.length >= 9) ? enabled : true;                      // indicates if this node was enabled
	this.type          = NodeType.GROUP;                                        // indicates a tree group
	this.rowObject     = null;                                                  // The HTML row Object
}
function TreeGroup_getId() {
	return this.id;
}
function TreeGroup_getLabel() {
	return this.label;
}
function TreeGroup_getType() {
	return this.type;
}
function TreeGroup_getCheckState() {
	return this.checkState;
}
function TreeGroup_setCheckState(checkState) {
	this.checkState = checkState;
}
function TreeGroup_setSelected(selected) {
	this.selected = selected;
}
function TreeGroup_isSelected() {
	return this.selected;
}
function TreeGroup_isExpanded() {
	return (this.nodeState == NodeState.EXPAND);
}
function TreeGroup_isExpandedEx() {
	return (this.nodeState == NodeState.EXPANDEX);
}
function TreeGroup_isCollapse() {
	return (this.nodeState == NodeState.COLLAPSE);
}
function TreeGroup_setNodeState(nodeState) {
	this.nodeState =nodeState;
}
function TreeGroup_getNodeState() {
	return this.nodeState;
}
function TreeGroup_appendChild(child) {
	this.childNodes[this.childNodes.length] = child;
	child.setParent(this);
	return this;
}
function TreeGroup_getChild(index) {
	return this.childNodes[index];
}
function TreeGroup_getChildNodes() {
	return this.childNodes;
}
function TreeGroup_hasChildNodes() {
	return this.childNodes.length > 0;
}
function TreeGroup_getPreviousNode(node) {
	
	if (node == null) return null;
	
	for (var i=0; i < this.childNodes.length; i++) {
		if (this.childNodes[i].getId() == node.getId() && i > 0) {
				return this.childNodes[i-1];
		}
	}
	
	return null;
}
function TreeGroup_getNextNode(node) {

	if (node == null) return null;
	
	for (var i=0; i < this.childNodes.length; i++) {
		if (this.childNodes[i].getId() == node.getId() && i < this.childNodes.length) {
			return this.childNodes[i+1];
		}
	}
	
	return null;
}
function TreeGroup_getParent() {
	return this.parent;
}
function TreeGroup_setParent(parent) {
	if (parent instanceof TreeGroup) {
		this.parent = parent;
	}
}
function TreeGroup_hasParent() {
	return (null != this.parent);
}
function TreeGroup_isLast() {
	if (null != this.parent) {
		var childs = this.parent.getChildNodes();
		return childs[childs.length-1]['id'] == this.id;
	} else {
		// it's the root element
		return true;
	}
}
function TreeGroup_getLast() {
	if (this.childNodes.length > 0) {
		return this.childNodes[this.childNodes.length-1];
	} else {
		return null;
	}
}
function TreeGroup_getFirst() {
	if (this.childNodes.length > 0) {
		return this.childNodes[0];
	} else {
		return null;
	}
}
function TreeGroup_getCheckedChildNodes() {
	var arr = new Array();
	
	for (var i=0; i < this.childNodes.length; i++) {
		if (this.childNodes[i]['checkState'] == CheckState.CHECKED || this.childNodes[i]['checkState'] == CheckState.UNDEFINED) {
			arr[arr.length] = this.childNodes[i];
		}
	}
	return arr;
}
function TreeGroup_addHandler(handler, script) {
	this.clientHandler[handler] = script;
	return this;
}
function TreeGroup_getHandler(handler) {
	if (handler in this.clientHandler) {
		return this.clientHandler[handler];
	} else {
		return null;
	}
}
function TreeGroup_toString() {
	var out = '';
	out += '********* TreeGroup ***********' + LF
	out += 'Id...........: ' + this.id + LF;
	out += 'Label........: ' + this.label + LF;
	out += 'Tooltip......: ' + this.tooltip + LF;
	out += 'NodeState....: ' + this.nodeState + LF;
	out += 'Selected.....: ' + this.selected + LF;
	out += 'CheckState...: ' + this.checkState + LF;
	out += 'Children.....: ' + this.childNodes.length + LF;
	out += 'Type.........: ' + this.type + LF;
	out += 'ExprEvalStr..: ' + this.exprEvalStr + LF;
	out += 'Target.......: ' + this.target + LF;
	return out;
}
new TreeGroup();
TreeGroup.prototype.getId                 = TreeGroup_getId;
TreeGroup.prototype.getLabel              = TreeGroup_getLabel;
TreeGroup.prototype.getType               = TreeGroup_getType;
TreeGroup.prototype.getCheckState         = TreeGroup_getCheckState;
TreeGroup.prototype.setCheckState         = TreeGroup_setCheckState;
TreeGroup.prototype.setSelected           = TreeGroup_setSelected;
TreeGroup.prototype.isSelected            = TreeGroup_isSelected;
TreeGroup.prototype.isExpanded            = TreeGroup_isExpanded;
TreeGroup.prototype.isExpandedEx          = TreeGroup_isExpandedEx;
TreeGroup.prototype.isCollapse            = TreeGroup_isCollapse;
TreeGroup.prototype.setNodeState          = TreeGroup_setNodeState;
TreeGroup.prototype.getNodeState          = TreeGroup_getNodeState;
TreeGroup.prototype.appendChild           = TreeGroup_appendChild;
TreeGroup.prototype.getChild              = TreeGroup_getChild;
TreeGroup.prototype.hasChildNodes         = TreeGroup_hasChildNodes;
TreeGroup.prototype.getChildNodes         = TreeGroup_getChildNodes;
TreeGroup.prototype.getCheckedChildNodes  = TreeGroup_getCheckedChildNodes;
TreeGroup.prototype.getPreviousNode       = TreeGroup_getPreviousNode;
TreeGroup.prototype.getNextNode           = TreeGroup_getNextNode;
TreeGroup.prototype.setParent             = TreeGroup_setParent;
TreeGroup.prototype.getParent             = TreeGroup_getParent;
TreeGroup.prototype.hasParent             = TreeGroup_hasParent;
TreeGroup.prototype.isLast                = TreeGroup_isLast;
TreeGroup.prototype.getLast               = TreeGroup_getLast;
TreeGroup.prototype.getFirst              = TreeGroup_getFirst;
TreeGroup.prototype.addHandler            = TreeGroup_addHandler;
TreeGroup.prototype.getHandler            = TreeGroup_getHandler;
TreeGroup.prototype.toString              = TreeGroup_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object.....: TreeNode()
| Function...: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Inital Version
|
+ ---------------------------------------------------------------------------------+
*/
function TreeNode(id, label, tooltip, checkState, selected, exprEvalStr, target, enabled) {
	var a = arguments;
	
	this.clientHandler  = new Array();                                          // ClientHandler associated with this node
	
	this.id            = id;                                                    // identifier for the tree node
	this.label         = label;                                                 // label to display on the node
	this.parent        = null;                                                  // the parent -> a tree group
	this.tooltip       = (a.length >= 3) ? tooltip : '';                        // Tooltip
	this.checkState    = (a.length >= 4) ? checkState : CheckState.UNCHECKED;   // indicates if the node is checked
	this.selected      = (a.length >= 5) ? selected : false;                    // true, if this is the selected node
	this.exprEvalStr   = (a.length >= 6) ? exprEvalStr : null;                  // RegEx to match for the image
	this.target        = (a.length >= 7) ? target : null;                       // The target attribute
	this.enabled       = (a.length >= 8) ? enabled : true;                      // indicates if this node was enabled
	this.type          = NodeType.NODE;                                         // indicates a tree node
}
function TreeNode_getId() {
	return this.id;
}
function TreeNode_getType() {
	return this.type;
}
function TreeNode_getLabel() {
	return this.label;
}
function TreeNode_setSelected(selected) {
	this.selected = selected;
}
function TreeNode_isSelected() {
	return this.selected;
}
function TreeNode_getCheckState() {
	return this.checkState;
}
function TreeNode_setCheckState(flag) {
	this.checkState = flag;
}
function TreeNode_isChecked() {
	return this.checked;
}
function TreeNode_getParent() {
	return this.parent;
}
function TreeNode_setParent(parent) {
	if (parent instanceof TreeGroup) {
		this.parent = parent;
	}
}
function TreeNode_isFist() {
	var childs = this.parent.getChildNodes();
	return childs[0]['id'] == this.id;
}
function TreeNode_isLast() {
	var childs = this.parent.getChildNodes();
	return childs[childs.length-1]['id'] == this.id;
}
function TreeNode_addHandler(handler, script) {
	this.clientHandler[handler] = script;
	return this;
}
function TreeNode_getHandler(handler) {
	if (handler in this.clientHandler) {
		return this.clientHandler[handler];
	} else {
		return null;
	}
}
function TreeNode_toString() {
	var out = '';
	out += '********* TreeNode ***********' + LF
	out += 'Id...........: ' + this.id + LF;
	out += 'Label........: ' + this.label + LF;
	out += 'Tooltip  ....: ' + this.tooltip + LF;
	out += 'CheckSate....: ' + this.checkState + LF;
	out += 'Selected.....: ' + this.selected + LF;
	out += 'Type.........: ' + this.type + LF;
	out += 'ExprEvalStr..: ' + this.exprEvalStr + LF;
	out += 'Target.......: ' + this.target + LF;
	return out;
}
new TreeNode();
TreeNode.prototype.getId            = TreeNode_getId;
TreeNode.prototype.getType          = TreeNode_getType;
TreeNode.prototype.getLabel         = TreeNode_getLabel;
TreeNode.prototype.setSelected      = TreeNode_setSelected;
TreeNode.prototype.isSelected       = TreeNode_isSelected;
TreeNode.prototype.getCheckState    = TreeNode_getCheckState;
TreeNode.prototype.setCheckState    = TreeNode_setCheckState;
TreeNode.prototype.setParent        = TreeNode_setParent;
TreeNode.prototype.getParent        = TreeNode_getParent;
TreeNode.prototype.isFist           = TreeNode_isFist;
TreeNode.prototype.isLast           = TreeNode_isLast;
TreeNode.prototype.addHandler       = TreeNode_addHandler;
TreeNode.prototype.getHandler       = TreeNode_getHandler;
TreeNode.prototype.toString         = TreeNode_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object.....: TreeHelp()
| Function...: Provides some helper and utility functions to manage the tree
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Inital Version
| 06.07.2005  G.Schulz (SCC)    serialization Tree-State added
+ ---------------------------------------------------------------------------------+
*/
function TreeHelp() {
}
function TreeHelp_createOutline(obj, indent) {
	var indent_amount = (arguments.length == 2) ? indent : 4;
	var root = null;
	
	// check argument
	if (obj instanceof Tree) {
		root = obj.getRoot();
	} else if ((obj instanceof TreeGroup) || (obj instanceof TreeNode)) {
		root = obj;
	} else {
		return 'Error: Argument not of Type Tree, TreeGroup or TreeNode';
	}

	// return outline
	return this.insertIndent(root, indent_amount, 0);
}
function TreeHelp_insertIndent(node, indent_amount, level) {

	if (null == node) return '';

	var out = StringHelp.filler(level++ * indent_amount, ' ') + node.getId() + LF;

	// check child nodes
	if (node instanceof TreeGroup && node.hasChildNodes()) {
		var childNodes = node.getChildNodes();

		for (var i = 0; i < childNodes.length; i++) {
			out += this.insertIndent(childNodes[i], indent_amount, level);
		}
	}
	
	return out;
}
function TreeHelp_getNodeById(nodelist, id) {
	
	if (null == nodelist) {
		return null;
	}

	// create iterator
	var ni = new NodeIterator(nodelist);

	var e = null;

	while (null != (e = ni.nextNode())) {
		if (e.getId() == id) {
			return e;
		}
	}
	
	return null;
}
function TreeHelp_createNodeArray(nodelist, nodefilter) {
	var arr = new Array();

	if (null != nodelist) {
		this.serializeNodes(arr, nodelist, nodefilter);
	}
	
	return arr;
}
function TreeHelp_serializeNodes(arr, node, nodefilter) {

	if (null == node) return;
	if (null == nodefilter) {
		arr[arr.length] = node;
	} else if (nodefilter == NodeFilter.GROUP && node.getType() == NodeType.GROUP) {
		arr[arr.length] = node;
	} else if (nodefilter == NodeFilter.NODE && node.getType() == NodeType.NODE) {
		arr[arr.length] = node;
	}

	// check child nodes
	if (node instanceof TreeGroup && node.hasChildNodes()) {
		var childNodes = node.getChildNodes();

		for (var i = 0; i < childNodes.length; i++) {
			this.serializeNodes(arr, childNodes[i], nodefilter);
		}
	}
}
function TreeHelp_checkNode(node, checkState) {

	// check node
	node['checkState'] = checkState;

	// set check state for all childes
	if (node instanceof TreeGroup) {
		
		var nodes = TreeHelp.createNodeArray(node);
		
		for (var i=0; i < nodes.length; i++) {
			nodes[i]['checkState'] = checkState;
		}
	}
	
	// update check state for parent (groups)
	var group = node.getParent();
	while (group != null) {
		var children = group.getChildNodes().length;

		// Wenn ein Gruppenknoten selbst über keine
		// Markierungseigenschaft verfügt, dann wird
		// der Markierungszustand für die gesamte Gruppe
		// berechnet.
		var checked			= 0;
		var intermediate	= 0;
		var invalid			= 0;

		for (var i = 0; i < children; i++) {
			var child = group.getChild(i);

			switch (child['checkState']) {

				case -1 :
					++invalid;
					break;

				case 1 :
					++checked;
					break;

				case 2 :
					++intermediate;
					break;

				default :
					;
			}
		}

		if (children == -1) {
			// Die Gruppe ist noch nicht in den Speicher geladen.
			// Der Selektionszustand ist daher unbekannt
			group['checkState'] = CheckState.UNSELECTABLE;
		} else if (invalid == children) {
			// Die Gruppe verfügt über keine selektierbaren Kinder.
			// Sie ist damit selbst nicht selektierbar.
			group['checkState'] = CheckState.UNSELECTABLE;
		} else if ((checked == 0) && (intermediate == 0)) {
			// Keines der Kinder ist markiert
			group['checkState'] = CheckState.UNCHECKED;
		} else {
			// Einige Kinder sind markiert, aber nicht alle
			group['checkState'] = (checked == children)
				? CheckState.CHECKED : CheckState.UNDEFINED;
		}

		group = group.getParent();

	}
}
function TreeHelp_collapseAllNodes(node) {
	this.changeNodeState(node, NodeState.COLLAPSE);
}
function TreeHelp_expandAllNodes(node) {
	this.changeNodeState(node, NodeState.EXPAND);
}
function TreeHelp_changeNodeState(node, nodeState) {

	if (node instanceof TreeGroup) {
		var nodes = TreeHelp.createNodeArray(node, NodeFilter.GROUP);
		
		for (var i=0; i < nodes.length; i++) {
			// only change the state for collapsed or expanded nodes
			if (nodes[i].getNodeState() != NodeState.EXPANDEX) {
				nodes[i].setNodeState(nodeState);
			}
		}
	}
}
function TreeHelp_getAncestorNodes(arr, node) {
	
	if (node.hasParent()) {
		var parent = node.getParent();
		arr[arr.length] = parent;
		this.getAncestorNodes(arr, parent);
	}
}
function TreeHelp_unSelect(node) {
	var nodes = TreeHelp.createNodeArray(node);
		
	for (var i=0; i < nodes.length; i++) {
		nodes[i].setSelected(false);
	}
}
function TreeHelp_serializeState(node) {
	var stateNodes      = 'expanded=';
	var stateSelected   = 'selected=';
	var stateCheckboxes = 'checked=';

	// 1) get the state for the nodes
	var nodes = TreeHelp.createNodeArray(node, NodeFilter.GROUP);
	for (var i=0; i < nodes.length; i++) {
		if (nodes[i].isExpanded()) {
			stateNodes += nodes[i].getId() + ',';
		}
	}	

	// 2) get the state for the selected node
	var nodes = TreeHelp.createNodeArray(node);
	for (var i=0; i < nodes.length; i++) {
		if (nodes[i].isSelected()) {
			stateSelected += nodes[i].getId() + ',';
			break;
		}
	}	

	// 3) get the state for thecheckboxes
	nodes = TreeHelp.createNodeArray(node, NodeFilter.NODE);
	for (var i=0; i < nodes.length; i++) {
		if (nodes[i].getCheckState() == CheckState.CHECKED) {
			stateCheckboxes += nodes[i].getId() + ',';
		}
	}

	return stateNodes + ';' + stateSelected + ';' + stateCheckboxes;
}
new TreeHelp();
TreeHelp.createOutline          = TreeHelp_createOutline;
TreeHelp.getNodeById            = TreeHelp_getNodeById;
TreeHelp.createNodeArray        = TreeHelp_createNodeArray;
TreeHelp.serializeNodes         = TreeHelp_serializeNodes;
TreeHelp.insertIndent           = TreeHelp_insertIndent;
TreeHelp.checkNode              = TreeHelp_checkNode;
TreeHelp.collapseAllNodes       = TreeHelp_collapseAllNodes;
TreeHelp.expandAllNodes         = TreeHelp_expandAllNodes;
TreeHelp.changeNodeState        = TreeHelp_changeNodeState;
TreeHelp.getAncestorNodes       = TreeHelp_getAncestorNodes;
TreeHelp.unSelect               = TreeHelp_unSelect;
TreeHelp.serializeState         = TreeHelp_serializeState;

/*
+ ---------------------------------------------------------------------------------+
| Object.....: NodeIterator()
| Function...:
| Usage......:  var ni = new NodeIterator(root);
|               for (var e = ni.nextNode(); e != null; e = ni.nextNode()) {
|                  // do something with the node
|               }
|
| date        author            description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Inital Version
|
+ ---------------------------------------------------------------------------------+
*/
function NodeIterator(root, nodefilter) {
	this.nodes = new Array();
	this.pos = 0;
	this.nodefilter = (arguments.length == 2) ? nodefilter : null;
	
	// Constructor
	if (null != root) {
		this.nodes = TreeHelp.createNodeArray(root, nodefilter);
	}
}
function NodeIterator_nextNode() {
	var node = null;

	if (this.pos < this.nodes.length) {
		node = this.nodes[this.pos];
		this.pos++;
	}
	
	return node;
}
function NodeIterator_previousNode() {
	var node = null;
	
	if (this.pos >= 0) {
		node = this.nodes[this.pos];
		this.pos--;
	}
	
	return node;
}
function NodeIterator_reset() {
	this.pos = 0;
}
function NodeIterator_first() {
	this.pos = 0;
	return this.nodes[this.pos];
}
function NodeIterator_last() {
	this.pos = this.nodes.length;
	return this.nodes[this.pos];
}

new NodeIterator();
NodeIterator.prototype.nextNode         = NodeIterator_nextNode;
NodeIterator.prototype.previousNode     = NodeIterator_previousNode;
NodeIterator.prototype.reset            = NodeIterator_reset;
NodeIterator.prototype.first            = NodeIterator_first;
NodeIterator.prototype.last             = NodeIterator_last;

/*
+ ---------------------------------------------------------------------------------+
| Object.....: TreePainterData()
| Function...: Collection for the resources used to render the tree
|
| date        author            description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Inital Version
|
+ ---------------------------------------------------------------------------------+
*/
function TreePainterData(tree, contextPath, arr_imageRes) {
	this.tree                  = tree;                 // The tree data
	this.contextPath           = contextPath;          // Needed for SSL problem IE
	this.arr_imageRes          = arr_imageRes;	       // array including all the images to paint the tabset
	this.DIV_PREVIX            = 'tree_';              // Prefix for DIV-Tag which embedds the tree
	this.initialized           = false;
	this.tableObject           = null;                 // HTML table Object

	// StyleSheet
	this.CSS_TABLE	           = 'tc';                 // TreeControl
	this.CSS_ROW_EVEN          = 'tleven';             // odd row
	this.CSS_ROW_ODD           = 'tlodd';              // even row
	this.CSS_TOL               = 'tol';                // outline
	this.CSS_TI                = 'ti'                  // tree item
	this.CSS_TIS               = 'tis'                 // tree item selected

	this.nodeWithFocus         = null;

	if (null != tree) {
		this.IMG_ICON_NONE         = arr_imageRes[CHECKBOX_NONE];
		this.IMG_ICON_INVALID      = arr_imageRes[CHECKBOX_INVALID];
		this.IMG_ICON_UNCHECKED    = arr_imageRes[CHECKBOX_UNCHECKED];
		this.IMG_ICON_CHECKED      = arr_imageRes[CHECKBOX_CHECKED];
		this.IMG_ICON_INDETERMINATE= arr_imageRes[CHECKBOX_INDETERMINATE];

		this.IMG_FOLDER_OPEN       = arr_imageRes[TREE_FOLDEROPEN];
		this.IMG_FOLDER_CLOSED     = arr_imageRes[TREE_FOLDERCLOSED];
		this.IMG_NODE              = arr_imageRes[TREE_ITEM];
			
		this.LINE0                 = arr_imageRes[TREE_STRUCTURE];
		this.LINE10                = arr_imageRes[TREE_STRUCTURE_10];
		this.LINE12                = arr_imageRes[TREE_STRUCTURE_12];
		this.LINE14                = arr_imageRes[TREE_STRUCTURE_14];
		this.LINE16                = arr_imageRes[TREE_STRUCTURE_16];
		this.LINE18                = arr_imageRes[TREE_STRUCTURE_18];
		this.LINE2                 = arr_imageRes[TREE_STRUCTURE_2];
		this.LINE26                = arr_imageRes[TREE_STRUCTURE_26];
		this.LINE30                = arr_imageRes[TREE_STRUCTURE_30];
		this.LINE32                = arr_imageRes[TREE_STRUCTURE_32];
		this.LINE34                = arr_imageRes[TREE_STRUCTURE_34];
		this.LINE42                = arr_imageRes[TREE_STRUCTURE_42];
		this.LINE46                = arr_imageRes[TREE_STRUCTURE_46];
	}
}
new TreePainterData();

/*
+ ---------------------------------------------------------------------------------+
| Object.....: TreePainter()
| Function...: 
|
| date        author            description
| ----------  ----------------  ----------------------------------------------------
| 04.03.2003  G.Schulz (SCC)    Erstversion
| 30.05.2006  G.Schulz (SCC)    ClientHandler-Support for Labels added
| 15.07.2006  H.Schulz (SCC)    Improvement TreePerformance
| 09.08.2006  G.Schulz (SCC)    KeyBord support for JS-Tree added
+ ---------------------------------------------------------------------------------+
*/
function TreePainter() {
	// empty constructor
}
function TreePainter_render(tpData) {
	var tree     = tpData.tree;

	// add the onSubmitHandler if the tree should work without
	// server roundtrips. The state needs to be send to the server
	if (!tpData.initialized && tree.getRunAt() == RunAt.CLIENT) {
		this.addFormOnSubmitHandler(tpData);
		tpData.initialized = true;
	}

	// create Root
	var doc = document.createElement('SPAN');

	//create Table
	var table = document.createElement('table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	
	// remember the trees table object
	tpData['tableObject'] = table;

	if (null != tree.getStyleClass() &&  '' != tree.getStyleClass()) {
		table.className = tree.getStyleClass();
	} else {
		table.className = tpData.CSS_TABLE;
	}

	doc.appendChild(table);

	// create tree
	this.createTreeElements(table, tree.getRoot(), tpData, 0);

	// get the Span-Element from the form and ...
	var span = this.getTreeNode(tpData.DIV_PREVIX + tree.getId());

	// ... replace the tree
	if (span.hasChildNodes()) {
		span.innerHTML = '';
	}
	
	span.appendChild(table);
}

function TreePainter_renderNode(tpData, node, rowIndex) {
	if ((tpData.tableObject == null) || (node.rowObject == null)) {
		// No html object available - render the whole tree
		this.render(tpData);
	} else {
		var nextIndex = this.createTreeElements(tpData.tableObject, node, tpData, rowIndex);

		this.adjustZebraPattern(tpData, nextIndex);
	}
}

function TreePainter_adjustZebraPattern(tpData, startIndex) {
	var oTable = tpData.tableObject;

	if ((oTable == null) || (startIndex >= oTable.rows.length)) {
		return;
	}

	var patternClass = ((startIndex % 2) == 1) ? tpData.CSS_ROW_EVEN : tpData.CSS_ROW_ODD;

	// check the node at start index for matching pattern
	if (oTable.rows[startIndex].className == patternClass) {
		// patern matches. no work required
		return;
	} else {
		// the pattern does not match, so change the pattern of all
		// following nodes
		for (var i = startIndex; i < oTable.rows.length; i++) {
			oTable.rows[i].className = ((i % 2) == 1) ? tpData.CSS_ROW_EVEN : tpData.CSS_ROW_ODD;
		}
	}
}

function TreePainter_getRowIndex(table, row) {
	if ((table == null) || (row == null)) {
		// row not available
		return -1;
	}

	var col = table.rows;
	if (col != null) {
		for (i=0; i < col.length; i++) {
			if (col[i] == row) {
				return i;
			}
		}
	}

	// row not found
	return -1;
}

function TreePainter_deleteNodes(table, node, rowIndex) {
	table.deleteRow(rowIndex);

	if (node instanceof TreeGroup) {
		// proccess child nodes
		if (node.hasChildNodes() && node.isExpanded()) {
			var childNodes = node.getChildNodes();
	
			for (var i = 0; i < childNodes.length; i++) {
				this.deleteNodes(table, childNodes[i], rowIndex);
			}
		}
	}
}

function TreePainter_createTreeElements(table, node, tpData, index) {

	if (null == node) {
		return index; // changed 20.3.2006
	}

	// check if the root node should be displayed
	if ((null != node.getParent()) || tpData['tree']['showRoot']) {
		var row;

		// insert cell 
		if ((index == 0) && safari) {
			row = table.insertRow(-1);
		} else {
			row = table.insertRow(index);
		}

		// store the HTML object in our tree node so that we can locate
		// it later
		if (node instanceof TreeGroup) {
			node['rowObject'] = row;
		}

		this.renderTreeElement(row, node, tpData, index++);
	}

	if (node instanceof TreeGroup) {
		// proccess child nodes
		if (node.hasChildNodes() && node.isExpanded()) {
			var childNodes = node.getChildNodes();

			for (var i = 0; i < childNodes.length; i++) {
				index = this.createTreeElements(table, childNodes[i], tpData, index);
			}
		}
	}

	return index;
}
function TreePainter_renderTreeElement(row, node, tpData, index) {
	var cell = null;

	// Container for all elements / rows
	var elementContainer = new Array();

	// create the lines in front of the nodes
	this.doCreateLine(elementContainer, node, tpData);
	elementContainer.reverse();	

	// create labels
	this.doCreateLabelLink(elementContainer, node, tpData, index);

	row.className = ((index % 2) == 1) ? tpData.CSS_ROW_EVEN : tpData.CSS_ROW_ODD;

	cell = row.insertCell(row.cells.length);

	// Create table element for this row
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	table.width  = '100%';
	table.height = '100%';
	table.className = tpData.CSS_TOL;
	cell.appendChild(table);

	row = table.insertRow(table.rows.length);
	row.vAlign = 'top';
	cell = row.insertCell(row.cells.length);

	// append elements to the current row
	for (var i=0; i < elementContainer.length; i++) {
		row.appendChild(elementContainer[i]);
	}
}
// 0x20 = Plus Symbol in front of an item
// 0x10 = Minus symbol in front of an item
// 0x08 = Connector to the nord
// 0x04 = Connector to the south
// 0x02 = Connector to the east
// 0x01 = Connector to the west
function TreePainter_doCreateLine(elementContainer, node, tpData) {
	var showLines       = tpData['tree']['showLines'];
	var showLinesAtRoot = tpData['tree']['linesAtRoot'];
	var showRoot        = tpData['tree']['showRoot'];
	var expandMode      = tpData['tree']['expandMode'];
	var showCheckBoxes  = tpData['tree']['checkboxes'];
	var nodeState       = NodeState.COLLAPSE;
	var showButtons     = true;
	var checkState      = CheckState.UNSELECTABLE;
	var selected        = node.isSelected();
	var key             = node.getId();
	var children        = 0;
	

	var img = (showLines == true) ? 0x02 : 0x00;
	var childLevel = true;
	var rootLevel = false;
	var isGroup = (node instanceof TreeGroup);
	
	if (isGroup) {
		nodeState = node.getNodeState();
		
		if (nodeState == NodeState.EXPANDEX) {
			children = -1;
		} else {
			children = node.getChildNodes().length;
		}
	}
		
	// Die Icons zum auf und Zuklappen der Aeste werden nur dann angezeigt,
	// wenn der Baum nicht ohnehin vollstaendig aufgeklappt ist.
	var showButtons = (showButtons && expandMode != ExpandMode.FULL);

	this.doCreateIcon(elementContainer, node, nodeState, tpData);
	if (showCheckBoxes) {
		this.doCreateCheckBox(elementContainer, node, tpData);
	}

	while (node != null) {
		var parent = node.getParent();

		if (null == parent && !showRoot) {
			break;
		}
		
		// Wir befinden uns auf der obersten Anzeigeebene das Baumes,
		// wenn der aktuelle Knoten die Baumwurzel ist, oder wenn
		// bei nicht angezeigter Wurzel der uebergeordnete Knoten die
		// Wurzel ist.
		rootLevel = (parent == null) || (!showRoot && (parent.getParent() == null));

		// Wenn auf der Wurzelebene Keine Verbindungslinien gezeichnet
		// werden soll, dann kann an dieser Stelle abgebrochen werden
		if (rootLevel && !showLinesAtRoot) {
			break;
		}
		
		if (!rootLevel && showLines) {
			// Eine Verbindungslinie zum Parent nach Norden zeichnen
			if (childLevel) {
				img |= 0x08;
			}

			if (!node.isLast()) {
				// Es gibt eine Verbindungslinie zu weiteren Soehnen
				// in Richtung Sueden
				img |= 0x08 | 0x04;
			}
		}

		// Der folgende Block wird NUR fuer den and die Prozedur
		// uebergebenen Knoten ausgewerter => childLevel == true
		if (childLevel && showButtons) {
			if (-1 == children) {
				// Die Anzahl der Kinder steht noch nicht fest.
				// Es wird deshalb ein (+) Symbol zum aufklappen angeboten
				img |= 0x20;
			} else if (children > 0) {
				// Der Knoten hat Kinder. Es entscheidet nun der
				// Expansionsstatus ueber das angezeigte Image.
				if (nodeState == NodeState.EXPAND) {
					// Symbol zum Schliessen vorsehen
					img |= 0x10;
				} else {
					// Symbol zum oeffnen vorsehen
					img |= 0x20;
				}
			}
		}

		if (!childLevel) {
			img &= 0x0C;
		}

		if ((img & 0x20) > 0) {
			// Es wird ein (+) Zeichen angezeigt.
			// Es muss also ein Hyperlink zum oeffnen des Knoten eingefuegt werden.
			// Wenn die Kinder des Knotens zu diesem Zeitpunkt bereits geladen sind,
			// dann kann dieses von der JSP-Seite selbst gehandelt werden. Im
			// anderen Fall muss das Controller-Servlet aufgefordert werden.
			if (-1 == children) {
				this.doCreateImageLink(elementContainer, node, tpData, img, NodeState.EXPANDEX);
			} else {
				this.doCreateImageLink(elementContainer, node, tpData, img, NodeState.EXPAND);
			}
			
		} else if ((img & 0x10) > 0) {
			// Es wird ein (-) Zeichen angezeigt.
			// Es muss also ein Hyperlink zum schliessen des Knoten eingefuegt werden.
			this.doCreateImageLink(elementContainer, node, tpData, img, NodeState.COLLAPSE);
		} else {
			// Es handelt sich um eine einfache Verbindungslinie welche ueber keinen Link verfuegt
			this.doCreateImage(elementContainer, node, tpData, img);
		}

		node = parent;
		img = 0;
		childLevel = false;
	}
}
function TreePainter_doCreateImage(arr, node, tpData, image) {
	var resource = 'LINE' + image;
	var imgRes = tpData[resource];

	var img = document.createElement('IMG');
	img.src    = imgRes.src;
	img.border = 0;
	img.width  = imgRes.width;
	img.height = imgRes.height;

	var td = document.createElement('TD');
	if ((image & 0x0c) == 0x0c) {
		td.setAttribute('background', tpData.LINE12.src);
	}
	td.appendChild(img);
	
	arr[arr.length] = td;
}
function TreePainter_doCreateIcon(arr, node, nodestate, tpData) {
	var tree       = tpData['tree'];
	var expandMode = tpData['tree']['expandMode'];
	var src        = null;

	// image to create
	var img = document.createElement('img');
	img.border = 0;
	
	// was an imagemap specified or use default images ?
	var imageMap =  tree.getImageMap();

	if (null == imageMap) {
		var imgRes = null;

		// use default images
		if (node instanceof TreeGroup) {
			if (nodestate == NodeState.EXPAND || nodestate == NodeState.EXPANDEX) {
				imgRes = tpData.IMG_FOLDER_OPEN;
			} else if (nodestate == NodeState.COLLAPSE || expandMode == ExpandMode.FULL) {
				imgRes = tpData.IMG_FOLDER_CLOSED;
			}
		} else if (node instanceof TreeNode) {
			imgRes = tpData.IMG_NODE;
		}

		img.src    = imgRes.src;
		img.width  = imgRes.width;
		img.height = imgRes.height;
		
	} else {

		// browse the imagemap for the matching image
		var imageMappings = imageMap.getImageMappings();
		var exprEvalStr   = node['exprEvalStr'];
		var baseDir       = imageMap['base'];
		
		if (null != exprEvalStr) {
			
			if (node instanceof TreeGroup) {
				// append '.open' or '.closed' for groups
				if (nodestate == NodeState.EXPAND || nodestate == NodeState.EXPANDEX) {
						exprEvalStr = exprEvalStr + '.open';
				} else if (nodestate == NodeState.COLLAPSE || expandMode == ExpandMode.FULL) {
					exprEvalStr = exprEvalStr + '.closed';
				}
			}
			
			// now check the map 
			for (var i=0; i < imageMappings.length; i++) {
				var mapping = imageMappings[i];
				var rule    = mapping['rule'];
				
				var regexp = new RegExp(rule);
				var match  = regexp.exec(exprEvalStr);
				
				// set the image attributes
				if (null != match) {
					img.src    = baseDir + mapping.getSource();
					
					if (null != mapping.getWidth()) {
						img.width  = mapping.getWidth();
					}

					if (null != mapping.getHeight()) {
						img.height  = mapping.getHeight();
					}
					
					if (null != mapping.getTooltip()) {
						img.title  = mapping.getTooltip();
					}
					
					if (null != mapping.getAlt()) {
						img.alt  = mapping.getAlt();
					}
				}
			}
		}
	}
	
	var td = document.createElement('TD');
	td.appendChild(img);
	
	arr[arr.length] = td;
}
function TreePainter_doCreateImageLink(arr, node, tpData, image, nodestate) {
	var ctrlDisabled = tpData['tree']['disabled'];
	var nodeDisabled = node['disabled'];
	var resource     = 'LINE' + image;
	var imgRes       = tpData[resource];

	// create cell
	var td = document.createElement('TD');
	if ((image & 0x0c) == 0x0c) {
		td.setAttribute('background', tpData.LINE12.src);
	}
	
	// create image
	var img = document.createElement('IMG');
	img.src    = imgRes.src;
	img.border = 0;
	img.width  = imgRes.width;
	img.height = imgRes.height;
	
	// check if control or node was disabled
	if (!ctrlDisabled && !nodeDisabled) {
		// create anchor
		var a = document.createElement('A');
		a.id = node['id'];
		a.appendChild(img);
		a.href ='#';
		a.tabIndex = -1;

		a.onclick = function() {
			var tree = tpData['tree'];

			if (node.getNodeState() == NodeState.EXPANDEX) {

				if (tree.isAjax()) {
					// 1) get state which needs to be synchronized
					var param = 'ctrl=' + tree.getId() + ';' + tree.serializeState();
//			alert(param);
					// send ajax request
// TODO					
				} else if (!tree.isFormElement()) {
					//this.href =  tree['action'] + '.do?ctrl=' + tree['id'] + '&action=ExpandEx&param=' + node['id'];
					alert("The ExpandEx Event for an client side tree only will work if the formElement-Attribute is set to 'true'\nOtherwise the tree state can not be synchronized.");
					return false;
				} else {
					// create and update hidden fiels and submit the form
					var span = document.getElementById(tpData.DIV_PREVIX + tree.getId());
					var form = CCUtility.getEnclosingForm(span);

					if (null != form) {
						var param = 'ctrl=' + tree.getId() + ';' + tree.serializeState();
						var state = CCUtility.createHidden('com.cc.framework.state', param);
						form.appendChild(state);
					}

					form.action = form.action + '?action=ExpandEx&ctrl=' + tree['id'] + '&param=' + node['id'];
					CCUtility.submitForm(form);
					return false;
				}

			} else {
				// check if expandmode was set to single or multiple
				if (tree.getExpandMode() == ExpandMode.MULTIPLE) {
					// in this case only set the new state
					// for this node

					var rowIndex = TreePainter.getRowIndex(tpData.tableObject, node.rowObject);

					if (rowIndex != -1) {
						TreePainter.deleteNodes(tpData.tableObject, node, rowIndex);
					}

					// change the nodes expansion state
					node['nodeState'] = (node['nodeState'] == NodeState.COLLAPSE) ? NodeState.EXPAND : NodeState.COLLAPSE;

					// render the changed node (and its children) only
					TreePainter.renderNode(tpData, node, rowIndex);
					return false;

				} else if (tree.getExpandMode() == ExpandMode.SINGLE) {
					// we must close all other groups where this node/group
					// does not belong to. So we have to check the parents
					var oldState = node['nodeState'];
					// 1te close all nodes
					TreeHelp.collapseAllNodes(tree.getRoot());

					// 2nd get and open the parent nodes
					var nodes = new Array();
					TreeHelp.getAncestorNodes(nodes, node);

					for (var i=0; i < nodes.length; i++) {
						nodes[i].setNodeState(NodeState.EXPAND);
					}

					// set new state for the current node
					node['nodeState'] = (oldState == NodeState.COLLAPSE) ? NodeState.EXPAND : NodeState.COLLAPSE;

					// render the whole tree.
					TreePainter.render(tpData);
					return false;
				}
			}
		}; // end of function()

		td.appendChild(a);
	} else {
		td.appendChild(img);
	}
	
	arr[arr.length] = td;
}
function TreePainter_doCreateLabelLink(arr, node, tpData, index) {
	var tree         = tpData['tree'];
	var ctrldisabled = tree['disabled'];
	var nodeDisabled = node['disabled'];	
	var selectNode   = tree['selectNode'];
	var maxlength    = tree['maxlength'];
	var target       = node['target'];
	var tooltip      = node['tooltip'];
	var isGroup      = (node instanceof TreeGroup);
	var isRoot       = (isGroup && node.getParent() == null);
	var selectable   = (isGroup) ? tree['groupSelect'] : true;
			
	var span  = document.createElement('span');
	if (maxlength == -1) {
		span.innerHTML = node['label']; //  + " " +index;
	} else {
		span.innerHTML = StringHelp.truncate(node['label'], maxlength);
	}
	
	var td = document.createElement('TD');
	var a  = document.createElement('A');
	if (!isRoot) a.tabIndex = -1;
	
	// only add a href if not disabled or groupSelect is allowed
	if (!ctrldisabled && !nodeDisabled && selectable) {
		
		if (tree['action'] != null) {
			a.href = tree['action'] + '.do?ctrl=' + tree['id'] + '&action=Drilldown&param=' + node['id'];
		
			// check if the target attribute needs to be set
			if (null != target && 'null' != target) {
				a.target = target;
			}

			a.onclick = function() {
				var userscript = node.getHandler(ClientEvent.ONCLICK);
	
				if (null != userscript) {
					var rtc = CCUtility.executeUserScript(userscript);
					if (!rtc) return false;
				}
	
				TreeHelp.unSelect(tree.getRoot());
				node.setSelected(true);
				TreePainter.resetSelectStyle(tpData);
				td.className = tpData.CSS_TIS;
	
				CCUtility.submitEnclosingForm(td);
				return true;
			}; // end of function()

		} else {
			a.href = '';
			
			a.onclick = function() {return false;};
		}

		a.ondblclick = function() {
			var userscript = node.getHandler(ClientEvent.ONDBLCLICK);

			if (null != userscript) {
				var rtc = CCUtility.executeUserScript(userscript);
				if (!rtc) return false;
			}
		}; // end of function()
		

		a.onfocus = function() {
			// Is node selectable?
			if (node instanceof TreeGroup && !tree.isGroupSelect()) {
				return false;
			}

			tpData['nodeWithFocus'] = node;
	
			this.style.backgroundColor = 'blue';
			this.style.color = 'white';

			TreePainter.onFocus(tpData['tree']['id'], node);
			return true;
			
		}; // end of function()

		a.onblur = function() {
			this.style.backgroundColor = 'transparent';
			this.style.color = 'black';
			return true;
		}; // end of function()

		a.onkeydown = function(event) {
			
			if (!event) event = window.event;
			
			var keyCode  = event.keyCode;

			var parent = node.getParent();
			
			if (keyCode == 40) {
				// Arrow down or Tab
				var nNode = TreePainter.getNextNode(node);
				if (nNode != null) {
					TreePainter.setFocus(tpData, nNode);
				}
				
				return false;
								
			} else if (keyCode == 38) {
				// Arrow up
				var pNode = TreePainter.getPreviousNode(node);
				if (pNode != null) {
						TreePainter.setFocus(tpData, pNode);
				}
				
				return false;
				
			} else if (keyCode == 37) {
				// Arrow left (close)
				
				if (tree.getExpandMode() == ExpandMode.FULL) {
					return; // no action
				}
				
				var nodeDisabled   = node['disabled'];
				
				if (node instanceof TreeGroup && !node.isCollapse() && !nodeDisabled) {
					node.setNodeState(NodeState.COLLAPSE);
					TreePainter.render(tpData);
					TreePainter.setFocus(tpData, node);
				}
				
				return false;
				
			} else if (keyCode == 39) {
				// Arrow right (open)
				
				var nodeDisabled   = node['disabled'];
				
				if (node instanceof TreeGroup && !node.isExpanded() && !nodeDisabled) {

					if (tree.getExpandMode() == ExpandMode.MULTIPLE) {

						if (node.isExpandedEx()) {
							// roundtrip required
					// TODO
						} else if (tree.getExpandMode() != ExpandMode.FULL) {
							// only if tree is not full expanded 
							node.setNodeState(NodeState.EXPAND);
							TreePainter.render(tpData);
							TreePainter.setFocus(tpData, node);
						}
							
					} else if (tree.getExpandMode() == ExpandMode.SINGLE) {
						
						TreeHelp.collapseAllNodes(tree.getRoot());

						node.setNodeState(NodeState.EXPAND);

						// 2nd get and open the parent nodes
						var nodes = new Array();
						TreeHelp.getAncestorNodes(nodes, node);

						for (var i=0; i < nodes.length; i++) {
							nodes[i].setNodeState(NodeState.EXPAND);
						}

						TreePainter.render(tpData);
						TreePainter.setFocus(tpData, node);
					}
				}
				
				return false;
				
			} else if (keyCode == 32) {
				// space
				var showCheckBoxes = tpData['tree']['checkboxes'];
				var nodeDisabled   = node['disabled'];
				
				if (showCheckBoxes && !nodeDisabled) {
					var checkState = (node['checkState'] == CheckState.CHECKED) ? CheckState.UNCHECKED : CheckState.CHECKED;
					TreeHelp.checkNode(node, checkState);
					TreePainter.render(tpData);
					TreePainter.setFocus(tpData, node);
				}
				
				return false;
				
			} else if (keyCode == 13) {
				// Enter --> Submit Link
				a.onclick();
			}
			
			return true;
		}; // end of function()
		
	}

	if (null != tooltip) {
		a.setAttribute('title', tooltip);
	}

	if (null != node.getId()) {
		a.id = 'lnk_' + node.getId();
	}
	
	a.appendChild(span);
	td.appendChild(a);

	if (selectNode && node.isSelected()) {
		td.className = tpData.CSS_TIS;
	} else {
		td.className = tpData.CSS_TI;
	}
	
	arr[arr.length] = td;
}
function TreePainter_doCreateCheckBox(arr, node, tpData) {
	var disabled        = tpData['tree']['disabled'];
//	var showCheckBoxes  = tpData['tree']['checkboxes'];
	var imgRes = null;

//	if (!showCheckBoxes) return;

	if (node['checkState'] == CheckState.NONE) {
		imgRes = tpData.IMG_ICON_NONE;
	} else if (node['checkState'] == CheckState.UNSELECTABLE) {
		imgRes = tpData.IMG_ICON_INVALID;
	} else if (node['checkState'] == CheckState.UNCHECKED) {
		imgRes = tpData.IMG_ICON_UNCHECKED;
	} else if (node['checkState'] == CheckState.CHECKED) {
		imgRes = tpData.IMG_ICON_CHECKED;
	} else if (node['checkState'] == CheckState.UNDEFINED) {
		imgRes = tpData.IMG_ICON_INDETERMINATE;
	} else {
		imgRes = tpData.IMG_ICON_NONE;
	}

	// create checkbox image
	var img    = document.createElement('IMG');
	img.src    = imgRes.src;
	img.border = 0;
	img.width  = imgRes.width;
	img.height = imgRes.height;


	// embed image within the anchor
	var a = document.createElement('A');
	a.id = node['id'];
	a.appendChild(img);
	a.tabIndex = -1;
	
	if (!disabled) {
		a.href = '#';

		a.onclick = function() {
			var tree = tpData['tree'];
			var treeId = tree.getId();

			if (tree['runAt'] == RunAt.SERVER) {
				// if a RoundTrip is required or the number of children
				// of a node is unknown, we must submit the Form
				return true; // changed 20.3.2006
			} else {
				// 1) set the new state
				var checkState = (node['checkState'] == CheckState.CHECKED)
					? CheckState.UNCHECKED : CheckState.CHECKED;

				TreeHelp.checkNode(node, checkState);
				TreePainter.render(tpData);
				
				// 2) Is there a client handler which needs to be executed?
				var handler = (checkState == CheckState.CHECKED) ? ClientEvent.ONCHECK : ClientEvent.ONUNCHECK;
				var userscript = node.getHandler(handler);
	
				if (null != userscript) {
					var rtc = CCUtility.executeUserScript(userscript);
					if (!rtc) return false;
				}
				
				return false;
			}
		}; // end of function()
	}

	// add to cell
	var td = document.createElement('TD');
	td.appendChild(a);

	arr[arr.length] = td;
}
function TreePainter_resetSelectStyle(tpData) {
	var root  = this.getTreeNode(tpData.DIV_PREVIX + tpData.tree.getId());
	var cells = root.getElementsByTagName('TD');

	for (var i=0; i < cells.length; i++) {
		var className = cells[i].className;

		if (null != className && className == tpData.CSS_TIS) {
			cells[i].className = tpData.CSS_TI;
		}
	}
}
function TreePainter_getTreeNode(id) {
	return document.getElementById(id);
}
function TreePainter_addFormOnSubmitHandler(tpData) {
	var tree = tpData['tree'];
	var span = this.getTreeNode(tpData.DIV_PREVIX + tree.getId());
	var form = CCUtility.getEnclosingForm(span);

	if (null != form) {
		form.onsubmit = function() {
			var param = 'ctrl=' + tree.getId() + ';' + tree.serializeState();
			var state = CCUtility.createHidden('com.cc.framework.state', param);
			this.appendChild(state);
			return true;
		}; // end of function()
	}
}
function TreePainter_getPreviousNode(node) {
	var parent = node.getParent();
	
	// check parent
	if (null == parent) {
		return node;
		
	} 

	if (node instanceof TreeNode) {
	
		var prevNode = parent.getPreviousNode(node);		
		
		if (null != prevNode) {
				return prevNode;
		} else {
				return parent;
		}
				
	} else if (node instanceof TreeGroup) {
		
		var prevNode = parent.getPreviousNode(node);
		
		if (null == prevNode) {
			return parent;
		}
		
		// leaf ?
		if (prevNode instanceof TreeNode) {
			return prevNode;
		}
		
		// Group ?
		if (!prevNode.isExpanded()) {
			// GroupNodes not expanded
			return prevNode;
		} else if (prevNode.isExpanded() && !prevNode.hasChildNodes()) {
			// GroupNodes expanded but without childes
			return prevNode;
		} else {

			// otherwise find the last node within this expanded group
			var found = false;
				
			while (!found) {
					prevNode = prevNode.getLast();
				
				if (prevNode instanceof TreeNode) {
					found = true;
				} else if (prevNode instanceof TreeGroup) {
						
						if (!prevNode.isExpanded() || !prevNode.hasChildNodes()) {
							found = true;
						} else {
							// keep on searching within the expanded subtree
							found = false
						}
				}
			}
	
			return prevNode;
		}
	}
} // end of getPreviousNode()

function TreePainter_getNextNode(node) {

	if (node instanceof TreeNode) {
		var parent = node.getParent();
		var nextNode = parent.getNextNode(node);	
		
		if (null != nextNode) {
				return nextNode;
		} else {

			var found = false;

			var nextNode = null;
		
			while (!found) {
			
				nextNode = node.getParent().getNextNode(node);
	
				if (null != nextNode) {
					found = true;
				} else {
					// keep on searching within the expanded subtree
					node = node.getParent();
				}
			}
		
			return nextNode;	
		}
		
	} else if (node instanceof TreeGroup) {

		// 1) check current subtree
		if (node.isExpanded() && node.hasChildNodes()) {
			return node.getFirst();
		}

		// 2) otherwise find the next node within this expanded group
		// if none exists find the next parent first child
		var found = false;

		var nextNode = null;
		
		while (!found) {
			
			if (null != node.getParent()) {
				nextNode = node.getParent().getNextNode(node);
			} else {
				return node;
			}
	
			if (null != nextNode) {
				found = true;
			} else {
				// keep on searching within the expanded subtree
				node = node.getParent();
			}
		}

		return nextNode;
	
	}
} // end of getNextNode()
function TreePainter_setFocus(tpData, node) {
	
	if (null == node) {
		return;
	}
	
	var a = document.getElementById('lnk_' + node.getId());
		
	if (null != a) {
		a.focus();
	}
	
	TreePainter.onFocus(tpData['tree']['id'], node);
}
function TreePainter_onFocus(treeId, node) {
	// alert(treeId + 'NodeId: ' + node.getId());
}
function TreePainter_handleCheckState(nodeId, tpData) {
	var tree = tpData['tree'];
	var showCheckBoxes = tpData['tree']['checkboxes'];

	if (!showCheckBoxes) {
		return;
	}
	
	var node = TreeHelp.getNodeById(tree.getRoot(), nodeId);
	var nodeDisabled   = node['disabled'];

	if (!nodeDisabled) {
		var checkState = (node.getCheckState() == CheckState.CHECKED) ? CheckState.UNCHECKED : CheckState.CHECKED;
		TreeHelp.checkNode(node, checkState);
		TreePainter.render(tpData);
	}
}
new TreePainter();
TreePainter.render                 = TreePainter_render;
TreePainter.renderNode             = TreePainter_renderNode;
TreePainter.getTreeNode            = TreePainter_getTreeNode;
TreePainter.createTreeElements     = TreePainter_createTreeElements;
TreePainter.renderTreeElement      = TreePainter_renderTreeElement;
TreePainter.doCreateLine           = TreePainter_doCreateLine;
TreePainter.doCreateCheckBox       = TreePainter_doCreateCheckBox;
TreePainter.doCreateImage          = TreePainter_doCreateImage;
TreePainter.doCreateImageLink      = TreePainter_doCreateImageLink;
TreePainter.doCreateLabelLink      = TreePainter_doCreateLabelLink;
TreePainter.doCreateIcon           = TreePainter_doCreateIcon;
TreePainter.resetSelectStyle       = TreePainter_resetSelectStyle;
TreePainter.addFormOnSubmitHandler = TreePainter_addFormOnSubmitHandler;
TreePainter.deleteNodes            = TreePainter_deleteNodes;
TreePainter.getRowIndex            = TreePainter_getRowIndex;
TreePainter.adjustZebraPattern     = TreePainter_adjustZebraPattern;
TreePainter.getPreviousNode        = TreePainter_getPreviousNode;
TreePainter.getNextNode            = TreePainter_getNextNode;
TreePainter.setFocus               = TreePainter_setFocus;
TreePainter.onFocus                = TreePainter_onFocus;
TreePainter.handleCheckState       = TreePainter_handleCheckState;
