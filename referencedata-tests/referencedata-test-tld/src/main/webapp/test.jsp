
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://www.common-controls.com/cc/tags-base" prefix="base"%>
<%@ taglib uri="http://www.common-controls.com/cc/tags-ctrl" prefix="ctrl"%>
<%@ taglib uri="http://www.common-controls.com/cc/tags-util" prefix="util"%>
<%@ taglib uri="http://www.common-controls.com/cc/tags-forms" prefix="forms"%>
<%@ taglib  uri="http://www.ecg.pl/cc/tags-refdata" prefix="refdata"%>
<html>
    <head>
        <script type="text/javascript" src="fw/global/jscript/formatter.js"></script>
	<script type="text/javascript" src="fw/global/jscript/utility.js"></script>
	<script type="text/javascript" src="fw/global/jscript/environment.js"></script>
	<script type="text/javascript" src="fw/global/jscript/common.js"></script>
        <util:jsp directive="includes"/>
        <script src="fw/refdata/jscript/refdata.js" type="text/javascript"/>
        

    </head>
    <body>


        <a href="<html:rewrite page='/test.do?painter=def1'/>">painter</a> or
        <a href="<html:rewrite page='/test.do?painter=def2'/>">painter2</a> <br>  

        <a href="<html:rewrite page='/test.do?language=PL'/>">locale - PL</a> or
        <a href="<html:rewrite page='/test.do?language=EN'/>">locale - EN</a> <br>  
        <html:form action="/test">
            <h2>Edit form using refdata:formtext tag</h2>

            <forms:form type="edit" locale="false" width="100%" caption="test form, edit" formid="testForm">

                <refdata:formtext property="simpleValue1" label="form text 1" size="10" refDataSlwNo="32E" refDataItemsOnPage="10" />
                <refdata:formtext property="simpleValue2" label="form text 2" size="20" refDataSlwNo="97E" refDataForceUpperCase="false" />
                <refdata:formtext property="simpleValue3" label="form text 3" size="30" refDataSlwNo="14E" />

            </forms:form>

            <h2>Edit form using refdata:formtext tag with multiple fields propagation</h2>

            <forms:form type="edit" locale="false" width="100%" caption="test form, edit" formid="testForm">

                <refdata:formtext property="multiValue1" label="form text 1" size="10" refDataSlwNo="8" refDataItemsOnPage="5" refDataMultipleColumnsMapping="multiValue1:code; multiValue2:code; multiValue3:description;"/>
                <forms:text property="multiValue2" label="form text 2" size="20"/>
                <forms:text property="multiValue3" label="form text 3" size="30"/>

            </forms:form>



            <h2>Display form using refdata:formtext tag</h2>	
            <forms:form type="display" locale="false" width="100%" caption="test form, display" formid="testForm">

                <refdata:formtext property="simpleValue7" label="form text 4" size="10" refDataSlwNo="18" />
                <refdata:formtext property="simpleValue8" label="form text 5" size="20" refDataSlwNo="38" />
                <refdata:formtext property="simpleValue9" label="form text 6" size="30" refDataSlwNo="48" />

            </forms:form>

            <h2>Using refdata:ctrltext tag - with refDataFormEditable="false"</h2>	

            <refdata:ctrltext name="testForm" property="simpleValue10" size="40" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="false"/>
            <refdata:ctrltext name="testForm" property="simpleValue11" size="50" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="false"/>
            <refdata:ctrltext name="testForm" property="simpleValue12" size="60" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="false"/>

            <h2>Using refdata:ctrltext tag - with refDataFormEditable="true"</h2>	

            <refdata:ctrltext name="testForm" property="simpleValue13" size="40" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="true" refDataForceUpperCase="false"/>
            <refdata:ctrltext name="testForm" property="simpleValue14" size="50" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="true"/>
            <refdata:ctrltext name="testForm" property="simpleValue15" size="60" refDataSlwNo="8" refDataFormName="testForm" refDataFormEditable="true"/>


            <h2>Using refdata:columntext with ctrl:list tag (read only)</h2>	

            <ctrl:list 
                action="/test"
                name="testList"
                title="Test list"
                rows="15">

                <ctrl:columndrilldown title="Value1" property="value1" width="200"/>
                <refdata:columntext title="Value2" property="value2" width="200" refDataSlwNo="8"/>
                <refdata:columntext title="Value2" property="value2" width="200" refDataSlwNo="8"/>
            </ctrl:list>

        </html:form>

        <util:jsp directive="endofpage"/>
    </body>
</html>
