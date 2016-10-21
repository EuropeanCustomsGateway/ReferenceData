<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Application which generates sample data for EJB datasource</title>
    </head>
    <body>

        <H2>Please select options to generate proper testing data for EJB datasource</H2><BR>

        <CENTER>

            <H3>Datasource bean options:</H3><BR>

            <form name="sample_name" method="POST" action="/referencedata-sampledata-generator/generate">
                <table border="0">
                    <tr>       
                        <TD>
                            Host and port(eg. 192.168.0.1:1099): 
                        </TD>
                        <td>
                            <input type="text" name="ip" value="http://localhost:8080/tomee/ejb">
                        </td>
                    </tr>

                    <tr>       
                        <TD>
                            JNP name of bean: 
                        </TD>
                        <td>
                            <input type="text" name="jnp" value="PersistenceDataSourceSBBean">
                        </td>
                    </tr>
                    <tr>       
                        <TD>
                            Dictionaries source :
                        </TD>
                        <td>
                            <input type="text" name="src" value="/dict/source">
                        </td>
                    </tr>
                    <!--tr>       
                        <TD>
                            Interface type (local/remote):
                        </TD>
                        <td>
                            <input type="radio" name="group1" value="remote" checked>Remote<br>
                            <input type="radio" name="group1" value="local">Local<br>
                        </td>
                    </tr-->
                    <tr>       
                        <TD colspan="1">
                            <input type="submit" name="Generate data" value="Generate data" title="Generate data">
                        </TD>
                    </tr>
                </table>

            </form>
        </CENTER>

    </body>
</html>