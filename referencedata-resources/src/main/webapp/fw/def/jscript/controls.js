/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/def/jscript/controls.js,v 1.32 2006/06/13 11:45:59 P001002 Exp $
 * $Revision: 1.32 $
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
| Purpose....: Initialize the eventhandlers needed by the common controls framework
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/
function init() {
	// initialize Eventhandlers for MouseOver, MouseOut-Events
	setupEventHandler();
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose....: Captures the events needed by the common controls framework and
|              registers the Eventhandlers
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial Version
| 31.03.2004  G.Schulz (SCC)    Safari support added
+ ---------------------------------------------------------------------------------+
*/
function setupEventHandler() {

	if (ie || opera) {
		// register Mouse-Handler in IE
		document.onmouseover = HandleMouseover;
		document.onmouseout  = HandleMouseout;
	} else if (ns || safari || firefox || mozilla) {
		// Event-Capturing in NS
		window.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT);
		window.onmouseover = HandleMouseover;
		window.onmouseout  = HandleMouseout;
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Eventhandler for HandleMouseover
|             Changes the image for a button/menuitem, if the user moves the coursor
|             over the button/menuitem (element).
|             For this buttons must of type 'img' or 'input' and the id must start with "btn".
|             - the image (gif) with the ending 1.gif stands for an active element
|             - the image (gif) with the ending 3.gif stands for an active element hover
|             - the image (gif) with the ending 5.gif stands for a selected element
|             - the image (gif) with the ending 6.gif stands for a selected element (hover)
|
| Datum       Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial Version
| 16.01.2004  G.Schulz (SCC)    Suppports now all type of images, not only gif's
| 31.03.2004  G.Schulz (SCC)    Safari support added
| 30.08.2004  G.Schulz (SCC)    Opera support was missing
+ ---------------------------------------------------------------------------------+
*/
function HandleMouseover(event) {
	var eSrc = null;

	if (ie || opera) {
		eSrc = window.event.srcElement;
	}
	else if(ns || safari || firefox || mozilla) {
		eSrc = event.target;
	}

	if (null == eSrc || null == eSrc.tagName ) {
		return;
	}

	var tagName = eSrc.tagName.toUpperCase();

	// Hover for image buttons
	if (("IMG" == tagName || "INPUT" == tagName) && eSrc.id.indexOf('btn') != -1) {
		doSwapButtonImgMouseOver(eSrc);
	}

	// Hover vor text buttons (check for IMG, TD, SPAN)
	// At the moment only for IE / Firefox / Mozilla
	if (ie || firefox || mozilla) {
		if ("IMG" == tagName || "TD" == tagName || "SPAN" == tagName || "BUTTON" == tagName) {
			if (null ==  eSrc.id || eSrc.id.indexOf('btn') == -1) {
				return;
			}
			
			var node = getEnclosingDiv(eSrc);
	
			if (null != node && null != node.id && "DIV" == node.tagName.toUpperCase() && node.id.indexOf('btn') != -1) {
				var td = node.getElementsByTagName('TD');
				
				// left image
				doSwapButtonImgMouseOver(td[0].firstChild);
	
				// middle image
				doSwapButtonImgMouseOver(td[1], 'background');
	
				// right image
				doSwapButtonImgMouseOver(td[2].firstChild);
			}
		}
	} 
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Returns the enclosing Div for a given node
|
| Datum       Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 30.08.2004  G.Schulz (SCC)    Initial Version
+ ---------------------------------------------------------------------------------+
*/
function getEnclosingDiv(node) {
	// search the div wich embbeds this node
	var parent = node.parentNode;
	
	if (null == parent) return null;
	
	if (parent.nodeName == 'DIV' ) {
		return parent;
	} else {
		return arguments.callee(parent);
	}
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Changes the image for a button/menuitem, if the user moves the coursor
|             over the button/menuitem (element).
|
| Datum       Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 30.08.2004  G.Schulz (SCC)    Initial Version
+ ---------------------------------------------------------------------------------+
*/
function doSwapButtonImgMouseOver(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');		// example btnLogin.gif or http://www.domain.com/.../btnLogin.gif
	var imgtype = tokens[tokens.length - 1];			// should return: gif, jpg, etc...

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('1.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '3.' + imgtype);
		}
		if (node.getAttribute(att).lastIndexOf('5.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '6.' + imgtype);
		}
	}
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Changes the image for a button/menuitem, if the user moves the coursor
|             out of the button/menuitem (element).
|
| Datum       Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 30.08.2004  G.Schulz (SCC)    Initial Version
+ ---------------------------------------------------------------------------------+
*/
function doSwapButtonImgMouseOut(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');		// example btnLogin.gif or http://www.domain.com/.../btnLogin.gif
	var imgtype = tokens[tokens.length - 1];			// should return: gif, jpg, etc...

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('3.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '1.' + imgtype);
		}
		if (node.getAttribute(att).lastIndexOf('6.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '5.' + imgtype);
		}
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Eventhandler for HandleMouseout
|             Changes the image for a button/menuitem, if the user moves the coursor
|             away from the button/menuitem (element).
|             For this buttons must of type 'img' or 'input' and the id must start with "btn".
|             - the image (gif) with the ending 1.gif stands for an active element
|             - the image (gif) with the ending 3.gif stands for an active element hover
|             - the image (gif) with the ending 5.gif stands for a selected element
|             - the image (gif) with the ending 6.gif stands for a selected element (hover)
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial Version
| 16.01.2004  G.Schulz (SCC)    Suppports now all type of images, not only gif's
| 31.03.2004  G.Schulz (SCC)    Safari support added
| 30.08.2004  G.Schulz (SCC)    Opera support was missing
+ ---------------------------------------------------------------------------------+
*/
function HandleMouseout(event) {
	var eSrc = null;

	if (ie || opera) {
		eSrc = window.event.srcElement;
	}
	else if(ns || safari || firefox || mozilla) {
		eSrc = event.target;
	}

	if (null == eSrc || null == eSrc.tagName ) {
		return;
	}
	
	var tagName = eSrc.tagName.toUpperCase();
	
	// Hover vor image buttons
	if (("IMG" == tagName || "INPUT" == tagName) && eSrc.id.indexOf('btn') != -1) {
		doSwapButtonImgMouseOut(eSrc);
	}
	
	// Hover vor text buttons
	// At the moment only for IE / Firefox / Mozilla
	if (ie || firefox || mozilla) {
		if ("IMG" == tagName || "TD" == tagName || "SPAN" == tagName) {
			if (null ==  eSrc.id || eSrc.id.indexOf('btn') == -1) {
				return;
			}

			var node = getEnclosingDiv(eSrc);
	
			if (null != node && null != node.id && "DIV" == node.tagName.toUpperCase() && node.id.indexOf('btn') != -1) {
				var td = node.getElementsByTagName('TD');
				
				// left image
				doSwapButtonImgMouseOut(td[0].firstChild);
	
				// middle image
				doSwapButtonImgMouseOut(td[1], 'background');
				
				// right image
				doSwapButtonImgMouseOut(td[2].firstChild);
			}
		}
	}
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose......:  Sets the color in a row of the Listcontrol if the cursors is moved
|                 over the row. 
|
| Requirements.:  resourcemap.js
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial Version
| 05.02.2005  G.Schulz (SCC)    Now the color is defined in resourcemap.js file.
|
+ ---------------------------------------------------------------------------------+
*/
var saveListBackgroundColor = null;

function high(obj) {
	saveListBackgroundColor = obj.style.backgroundColor ;
	
	if (obj.style != null) {
		obj.style.backgroundColor = LIST_ROW_HOVER_BGCOLOR;
	}
}

function low(obj, isEvenLine) {
	if (obj.style != null) {
		obj.style.backgroundColor = saveListBackgroundColor;
	}
}

// *****************************************************************************
// ** Popup Window                                                            **
// *****************************************************************************

var popupTimeCount	= 425;
var popupTimerID	= null;
var popupLast		= null;

// +----------------------------------------------------------------------------
// | Funktion.: startPopupTimer()
// | Zweck....: Starts the popup timer. When the popup timer fires it closes all
// |			open popup windows.
// |
// | Argumente: -
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)    First revision
// |
// +----------------------------------------------------------------------------
function startPopupTimer() {
	popupTimerID = setTimeout("resetPopup()", popupTimeCount);
}

// +----------------------------------------------------------------------------
// | Funktion.: stopTimer()
// | Zweck....: Stops the popup timer
// |
// | Argumente: -
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)    First revision
// |
// +----------------------------------------------------------------------------
function stopPopupTimer() {
	if (popupTimerID != null) {
		clearTimeout(popupTimerID);
		popupTimerID = null;
	}
}

// +----------------------------------------------------------------------------
// | Funktion.: resetPopup()
// | Zweck....: Closes all open popup windows
// |
// | Argumente: -
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)    First revision
// |
// +----------------------------------------------------------------------------
function resetPopup() {
	if (popupLast != null) {
		var hideElement = document.getElementById(popupLast);
		hideElement.style.visibility = 'hidden';

		popupLast	= null;
	}
}

// +----------------------------------------------------------------------------
// | Funktion.: showPopup(popupId, event)
// | Zweck....: Shows the given popup window
// |
// | Argumente: popupId - The unique id of the popup window
// |            event   - HTML Event
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)     First revision
// |
// +----------------------------------------------------------------------------
function showPopup(popupId, event) {

	// Close all currently open windows
	resetPopup();

	var popup = document.getElementById(popupId);
	if (popup != null) {
		// read mouse position
		var x = getClientX(event) + document.body.scrollLeft + 15;
		var y = getClientY(event) + document.body.scrollTop  + 15;
	
		// adjust x position of popup window
		var maxX		= document.body.clientWidth + document.body.scrollLeft;
		var popupWidth	= popup.offsetWidth;
	
		if ((x + popupWidth) > maxX) {
			x = x + (maxX - x - popupWidth);
		}
	
		if (x < 0) {
			x = 0;
		}
	
		// adjust y position of popup window
		var maxY		= document.body.clientHeight + document.body.scrollTop;
		var popupHeight	= popup.offsetHeight;
	
		if ((y + popupHeight) > maxY) {
			y = y + (maxY - y - popupHeight);
		}
	
		if (y < 0) {
			y = 0;
		}
	
		popup.style.top			= y;
		popup.style.left		= x;
		popup.style.zIndex		= 10;
		popup.style.visibility	= "visible";
	
		// remember the current open popup window
		popupLast = popupId;
	}
}
