<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>MOST EXPENSIVE BOOKS BY CATEGORY</title>
  </head>
  <body>
    <center>
      <h1>MOST EXPENSIVE BOOKS BY CATEGORY</h1>
        <table>
          <tr>
            <th>Category</th>
            <th>Book Decription</th>
            <th>Price</th>
            <th>Total Sales</th>
          </tr>
 		  <s:iterator value="#request.results">
          <tr>
            <td width="100" align="left"><s:property value="category"/></td>  
            <td width="250" align="left"><s:property value="title"/></td>  
            <td width="100" align="right"><s:property value="price"/></td>  
            <td width="100" align="right"><s:property value="sales"/></td>  
          </tr>
  	      </s:iterator>
  	      <tr><td height="20"></td></tr>
          <tr>
            <td colspan="4" align="center">
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

