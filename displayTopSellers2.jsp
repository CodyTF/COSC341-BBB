<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>TOP 10 BEST SELLERS</title>
  </head>
  <body>
    <center>
      <h1>TOP 10 BEST SELLERS</h1>
      <h3>in the last three months</h3>
        <table>
          <tr>
            <th>Rank</th>
            <th>Book Decription</th>
            <th>Category</th>
            <th>Total Sales</th>
          </tr>
          <s:iterator status="iteminx" value="#request.results">
          <tr>
            <td width="50" align="center">#<s:property value="%{#iteminx.index+1}"/></td>  
            <td width="250" align="left"><s:property value="title"/></td>  
            <td width="100" align="left"><s:property value="category"/></td>  
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

