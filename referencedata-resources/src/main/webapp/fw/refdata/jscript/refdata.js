var __refDataCCT__ajaxProcessing = false;

var __refDataCCT__currentImg = null;
var __refDataCCT__currentEditProperty = null;
var __refDataCCT__currentEditCursor = null;
var __refDataCCT__DATA = null;

var __refDataCCT__closeImage = null;
var __refDataCCT__currentCursor = null;
var __refDataCCT__mainDivRel = null;
var __refDataCCT__mainDivRelInfo = null;
var __refDataCCT__mainDiv = null;
var __refDataCCT__mainInfo = null;
var __refDataCCT__parent = null;
var __refDataCCT__infoText = null;

var __refDataCCT__cache = new Object();

RefDataSupport = {
    process : function(img, data) {
        if (__refDataCCT__ajaxProcessing == false) {
            __refDataCCT__currentImg = img;
            __refDataCCT__currentImg.src = "fw/refdata/image/img2.gif";
            __refDataCCT__DATA = data;
            this.getRefData();
        } else {
            this.showInfoDiv(this.messageInfo("loading"));
        }
    },

    importsLocalizedTexts : function(url) {
        var randnr = Math.floor(Math.random() * 100000);
        if (__refDataCCT__infoText == null) {
            new Ajax.Request(url, {
                method :'get',
                parameters : {
                    "resources" :randnr
                },
                onSuccess : function(transport) {
                    eval(transport.responseText);
                },
                onFailure : function() {

                }

            });
        }
    },

    messageInfo : function(type, text) {
        /*
                 * if (text == null || text == "undefined") { text = ""; } var langList =
                 * __refDataCCT__text[language]; if (langList != null && langList[type] !=
                 * null) { return langList[type].replace("{0}", text); } else { return
                 * __refDataCCT__defaultText[type].replace("{0}", text); }
                 */
        if (text == null || text == "undefined") {
            text = "";
        }
        return __refDataCCT__infoText != null &&
        __refDataCCT__infoText[type] != null ?
        __refDataCCT__infoText[type].replace("[0]", text) : "Unknown message type: "+type;
    },

    endProcess : function() {
        __refDataCCT__currentImg.src = "fw/refdata/image/img1.gif";
        this.closeInfoDiv();
        window.document.body.style.cursor = __refDataCCT__currentCursor;
        if (__refDataCCT__DATA.currentEditable == 'true') {
            __refDataCCT__currentEditProperty.style.cursor = __refDataCCT__currentEditCursor;
        }
        __refDataCCT__ajaxProcessing = false;
    },

    showNewDiv : function(text) {
        __refDataCCT__parent = __refDataCCT__currentImg.parentNode.parentNode.previousSibling;
        __refDataCCT__mainDivRel = new Element('div')
        .setStyle('position: relative; width: 100%');
        __refDataCCT__parent.appendChild(__refDataCCT__mainDivRel);
        __refDataCCT__mainDiv = new Element('div')
        .setStyle('position: absolute; padding: 0; border: 0px solid; display:block; z-index: 100');
        __refDataCCT__mainDivRel.appendChild(__refDataCCT__mainDiv);
        __refDataCCT__mainDiv.innerHTML = text;
    },

    closeNewDiv : function(evt) {
        __refDataCCT__mainDivRel.removeChild(__refDataCCT__mainDiv);
        __refDataCCT__parent.removeChild(__refDataCCT__mainDivRel);
        __refDataCCT__mainDivRel = null;
        __refDataCCT__mainDiv = null;
    },

    showInfoDivImg : function(text, img) {
        __refDataCCT__currentImg = img;
        this.showInfoDiv(text);
    },

    showInfoDiv : function(text) {
        this.closeInfoDiv();
        __refDataCCT__parent = __refDataCCT__currentImg.parentNode.parentNode.previousSibling;
        __refDataCCT__mainDivRelInfo = new Element('div')
        .setStyle('position: relative; width: auto;');
        __refDataCCT__parent.appendChild(__refDataCCT__mainDivRelInfo);
        __refDataCCT__mainInfo = new Element('div')
        .setStyle('position: absolute;  width: 180px; background-color:#EEE; padding: 0; border: 1px solid #111; display:block; z-index: 101')
        __refDataCCT__mainDivRelInfo.appendChild(__refDataCCT__mainInfo);
        __refDataCCT__mainInfo.innerHTML = "<table style='font-size:8pt;' border='0' width='100%'><tr><td onclick='RefDataSupport.closeInfoDiv()'><img src='fw/refdata/image/bcls.gif'></td><td width='100%'>"
        + text + "</td></tr></table>";
    },

    closeInfoDiv : function(evt) {
        try {
            __refDataCCT__mainDivRelInfo.removeChild(__refDataCCT__mainInfo);
            __refDataCCT__parent.removeChild(__refDataCCT__mainDivRelInfo);
            __refDataCCT__mainDivRelInfo = null;
            __refDataCCT__mainInfo = null;
        } catch (e) {
        }
    },

    putToCache : function(text) {
        __refDataCCT__cache[__refDataCCT__DATA.currentSlw + '-'
        + __refDataCCT__DATA.currentProperty + '-'
        + __refDataCCT__DATA.currentItemsStart + '-'
        + __refDataCCT__DATA.currentItemsOnPage] = text;
    },

    getFromCache : function() {
        return __refDataCCT__cache[__refDataCCT__DATA.currentSlw + '-'
        + __refDataCCT__DATA.currentProperty + '-'
        + __refDataCCT__DATA.currentItemsStart + '-'
        + __refDataCCT__DATA.currentItemsOnPage];
    },

    getRefData : function() {
        if (__refDataCCT__ajaxProcessing == true) {
            this.showInfoDiv(this.messageInfo("loading"));
            return;
        }

        __refDataCCT__ajaxProcessing = true;

        if (__refDataCCT__mainDiv != null) {
            this.closeNewDiv();
        }
        __refDataCCT__currentCursor = window.document.body.style.cursor;
        window.document.body.style.cursor = 'wait';

        if (this.getFromCache() == null
            || __refDataCCT__DATA.currentEditable == 'true') {
            var __refDataCCT__currentEditPropertyValue = null;
            if (__refDataCCT__DATA.currentEditable == 'true') {
                __refDataCCT__currentEditProperty = document
                .getElementsByName(__refDataCCT__DATA.currentProperty)[0];
                __refDataCCT__currentEditCursor = __refDataCCT__currentEditProperty.style.cursor;
                __refDataCCT__currentEditProperty.style.cursor = 'wait';
                __refDataCCT__currentEditPropertyValue = document
                .getElementsByName(__refDataCCT__DATA.currentProperty)[0].value;
                if (__refDataCCT__currentEditPropertyValue == null
                    || __refDataCCT__currentEditPropertyValue.length < __refDataCCT__DATA.currentMinTextLength) {
                    this.endProcess();
                    this.showInfoDiv(this.messageInfo("search_len"));

                    return;
                } else {
                    this.closeInfoDiv();
                }
            } else {
                __refDataCCT__currentEditPropertyValue = __refDataCCT__DATA.currentProperty;
            }

            var randnr = Math.floor(Math.random() * 100000);

            new Ajax.Request(__refDataCCT__DATA.currentUrl, {
                method :'get',
                parameters : {
                    "randnr" :randnr,
                    "metadata" : __refDataCCT__DATA.metadata,
                    "dictionaryId" :__refDataCCT__DATA.currentSlw,
                    "itemsStart" :__refDataCCT__DATA.currentItemsStart,
                    "itemsOnPage" :__refDataCCT__DATA.currentItemsOnPage,
                    "value" :__refDataCCT__currentEditPropertyValue,
                    "column" :__refDataCCT__DATA.currentSearchColumn,
                    "languageCode" :__refDataCCT__DATA.currentLanguageCode,
                    "displayOnly" :__refDataCCT__DATA.currentEditable
                },
                onSuccess : function(transport) {
                    RefDataSupport.endProcess();
                    if (transport.responseText.length > 0) {
                        RefDataSupport.putToCache(transport.responseText);
                        RefDataSupport.showNewDiv(transport.responseText);
                    } else {
                        RefDataSupport.showInfoDiv(RefDataSupport.messageInfo(
                            "error_dictionary",
                            __refDataCCT__DATA.currentSlw));
                    }
                },
                onFailure : function() {
                    RefDataSupport.showInfoDiv(RefDataSupport
                        .messageInfo("error_loading"));
                }

            });
        } else {
            this.endProcess();
            this.showNewDiv(this.getFromCache());
        }
    },

    selectItem : function(index) {
        eval(document.getElementById("RefDataArray").innerHTML);
        try {
            if (__refDataCCT__DATA.currentSearchColumn == 'null'
                || __refDataCCT__DATA.currentEditable != 'true') {
                RefDataSupport.closeNewDiv();
                return;
            }
            if (__refDataCCT__DATA.currentMultipleColumnsMapping == 'null') {
                __refDataCCT__DATA.currentMultipleColumnsMapping = __refDataCCT__DATA.currentProperty
                + ":" + __refDataCCT__DATA.currentSearchColumn;
            } else {
                __refDataCCT__DATA.currentMultipleColumnsMapping = __refDataCCT__DATA.currentMultipleColumnsMapping
                + ";"
                + __refDataCCT__DATA.currentProperty
                + ":"
                + __refDataCCT__DATA.currentSearchColumn;
            }
            var itemsMap = __refDataCCT__DATA.currentMultipleColumnsMapping
            .split(";");
            for (i = 0; i < itemsMap.length; i++) {
                var item = itemsMap[i].replace(" ", "");
                if (item.length > 0) {
                    var fieldColumnMap = item.split(":");
                    var fieldName = fieldColumnMap[0];
                    var columnName = fieldColumnMap[1];
                    if (fieldName.length > 0 && columnName.length > 0) {
                        fieldElement = document.getElementsByName(fieldName)[0];
                        if (fieldElement != null &&
                            fieldElement != "undefined") {
                            var itemIndex = columnName + ''+index;
                            itemIndex = itemIndex.toLowerCase();
                            var fieldValue = items_array[itemIndex];
                            if (fieldValue != null &&
                                fieldValue != "undefined") {
                                if (fieldElement.tagName.toLowerCase() == "input") {
                                    if (fieldElement.maxLength &&
                                        fieldElement.maxLength > 0) {
                                        fieldValue = fieldValue.substring(0,fieldElement.maxLength);
                                    }
                                    fieldElement.value = fieldValue;
                                } else if (fieldElement.tagName.toLowerCase() == "select") {
                                    if (fieldElement.options != null &&
                                        fieldElement.options.length > 0) {
                                        for (var k = 0; k < fieldElement.options.length ; k++) {
                                            // TODO remove toLoverCase - change in model
                                            if (fieldValue.toLowerCase() == fieldElement.options[k].value.toLowerCase()) {
                                                fieldElement.options[k].selected = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                RefDataSupport.showInfoDiv(RefDataSupport
                                    .messageInfo("error_result",itemIndex));
                            }
                        }
                    }
                }
            }
            RefDataSupport.closeNewDiv();
        } catch (e) {
            RefDataSupport.showInfoDiv(RefDataSupport
                .messageInfo("error_field"));

        }
    },

    changePage : function(index) {
        __refDataCCT__DATA.currentItemsStart = __refDataCCT__DATA.currentItemsOnPage
        * index;
        this.process(__refDataCCT__currentImg, __refDataCCT__DATA);
    },

    openSlw : function(obj, event) {
        /* if ( obj == null || obj == "undefined" || obj.value == "" ) return; */
        if (obj == null || obj == "undefined")
            return;
        var keyCode = null;
        if (event.which) {
            keyCode = event.which;
        } else if (event.keyCode) {
            keyCode = event.keyCode;
        }
        /* if ENTER is clicked, then run dictionary */
        if (keyCode == 13) {
            obj.onchange();
            var destObj = obj.parentNode.nextSibling.childNodes[0].childNodes[0];
            if (destObj != null && destObj != "undefined") {
                destObj.onclick();
            }
        }
    },

    changeCase : function(obj, forceUpperCase) {
        /* Change letters depending on style */
        if (obj == null || obj == "undefined" || obj.value == "")
            return;
        if (obj.style == null || obj.style.cssText == null
            || obj.style.cssText == "undefined")
            return;
        var textStyle = obj.style.cssText.replace(/\s+/g, "").toLowerCase();
        if (textStyle.indexOf("text-transform") >= 0) {
            if (textStyle.indexOf("text-transform:uppercase") >= 0) {
                obj.value = obj.value.toUpperCase();
            }
            if (textStyle.indexOf("text-transform:lowercase") >= 0) {
                obj.value = obj.value.toLowerCase();
            }
        }
        /* Force upperCase for current object */
        if (forceUpperCase != null && forceUpperCase == true) {
            obj.value = obj.value.toUpperCase();
        }

    }

}

        /*
         * var __refDataCCT__defaultText = { loading :"Loading in progress",
         * error_loading :"Loading error", error_field :"Loading field error",
         * search_len :"Too less characters", error_dictionary :"Dictionary {0} doesn't
         * exist" };
         *
         * var __refDataCCT__text = { EN : { loading :"Loading in progress",
         * error_loading :"Loading error", error_field :"Loading field error",
         * search_len :"Too less characters", error_dictionary :"Dictionary {0} doesn't
         * exist" }, PL : { loading :"Trwa \u0142adowanie", error_loading
         * :"B\u0142\u0105d \u0142adownia", error_field :"B\u0142\u0105d \u0142adowania
         * do p\u00F3l", search_len :"Za ma\u0142o znak\u00F3w", error_dictionary
         * :"S\u0142ownik {0} nie istnieje" } };
         */
