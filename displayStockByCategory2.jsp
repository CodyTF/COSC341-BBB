<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>TOTAL STOCK BY CATEGORY</title>
  </head>
  <body>
    <center>
      <h1>TOTAL STOCK BY CATEGORY</h1>
        <table>
          <tr>
            <th>Category</th>
            <th>Qty. in Stock</th>
          </tr>
 		  <s:iterator value="#request.results">
          <tr>
            <td width="150"><s:property value="category"/></td>  
            <td width="150" align="right"><s:property value="stock"/></td>  
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

