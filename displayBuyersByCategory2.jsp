<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>BUYERS BY CATEGORY</title>
  </head>
  <body>
    <center>
      <h1>BUYERS BY CATEGORY</h1>
        <table>
          <tr>
            <th>Category</th>
            <th># of Buyers</th>
          </tr>
 		  <s:iterator value="#request.results">
          <tr>
            <td width="150"><s:property value="category"/></td>  
            <td width="150" align="right"><s:property value="buyers"/></td>  
          </tr>
  	      </s:iterator>
  	      <tr><td height="20"></td></tr>
          <tr>
            <td colspan="2" align="center">
              <s:form action="reposition">
              <s:submit value="    Close     "/>
              <s:hidden name="submit" value="Reports"/>
              </s:form>
            </td>
          </tr>
        </table>
    </center>
  </body>
</html>

