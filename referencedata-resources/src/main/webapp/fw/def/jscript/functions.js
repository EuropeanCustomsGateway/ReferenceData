/*
 * $Header: d:/repository/cvs/cc-framework/web/fw/def/jscript/functions.js,v 1.13 2006/06/13 11:45:59 P001002 Exp $
 * $Revision: 1.13 $
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

// +----------------------------------------------------------------------------
// | Funktion.: getClientX()
// | Zweck....: Retrieves the x-coordinate for a mouse event
// |
// | Argumente: event - The event
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)    Initial revision
// |
// +----------------------------------------------------------------------------
function getClientX(event) {
	if (ie || opera) {
		return window.event.clientX;
	} else {
		return event.clientX;
	}
}

// +----------------------------------------------------------------------------
// | Funktion.: getClientY()
// | Zweck....: Retrieves the x-coordinate for a mouse event
// |
// | Argumente: event - The event
// |
// | Datum       Author            Bemerkung
// | ----------  ----------------  ---------------------------------------------
// | 11.09.2001  H.Schulz (SCC)    Initial revision
// |
// +----------------------------------------------------------------------------
function getClientY(event) {
	if (ie || opera) {
		return window.event.clientY;
	} else {
		return event.clientY;
	}
}

function setHyperLink(id, urlName) {
	var node = document.getElementById(id);
	node.href = url[urlName];
}

/*
+ ---------------------------------------------------------------------------------+
| Funktion.: checkInt(object)
| Zweck....: 
|
| Argumente: object
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 12.05.2001  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/

function checkInt(value) {
	var iValue = 0;
	
	if ( isNaN(value) ) {
		return false;
	} else {
		return true;
	}
	
	/* Laeuft nicht unter IE4.0 !!!!
	try{
		iValue = parseInt(value);
		return true;
	} catch (e) {
		// Error
		// alert(e);
		return false;
	}*/
} // end checkInt()

/*
+ ---------------------------------------------------------------------------------+
| Funktion.: spinUp(Object)
| Zweck....: Erhöht einen Integerwert
|
| Argumente: Object
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 02.01.2001  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/

function spinUp(obj) {
	if ( checkInt (obj.value) ){
		obj.value = parseInt(obj.value) + 1;
		// TODO obj.fireEvent("onchange");
	} else {
		alert(">" + obj.value +"< ist not a valid Number!");
	}

	return false;
}

/*
+ ---------------------------------------------------------------------------------+
| Funktion.: spinDown(Object)
| Zweck....: vermindert einen Integerwert
|
| Argumente: Object
|
| Datum       Author            Bemerkung
| ----------  ----------------  ----------------------------------------------------
| 02.01.2001  G.Schulz (SCC)    Erstversion
|
+ ---------------------------------------------------------------------------------+
*/

function spinDown(obj) {
	if ( checkInt (obj.value) ){
		obj.value = parseInt(obj.value) - 1;
		// TODO obj.fireEvent("onchange");
	} else {
		alert(">" + obj.value +"< ist not a valid Number!");
	}

	return false;
}


function inc(obj){
	var val = parseInt(obj.value);

	if (val == Number.NaN) {
		alert("[" + val + "] is not a Number!");
	} else {
		val++;
		obj.value = val;
	}
}

function dec(obj){
	var val = parseInt(obj.value);

	if (val == Number.NaN) {
		alert("[" + val + "] is not a Number!");
	} else {
		val--;
		obj.value = val;
	}
}

