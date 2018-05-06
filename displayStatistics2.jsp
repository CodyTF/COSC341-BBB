<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>SALES STATISCTICS</title>
  </head>
  <body>
    <center>
      <h1>SALES STATISCTICS</h1>
        <table>
          <tr>
            <th align="left">Month:</th>
            <td align="right"><s:property value="#request.results.month"/></td>
          </tr>
          <tr>
            <th align="left">Average amount of sale per customer:</th>
            <td align="right"><s:property value="#request.results.averageSalePerCustomer"/></td>
          </tr>
          <tr>
            <th align="left">Average number of books per order:</th>
            <td align="right"><s:property value="#request.results.averageNoBooksPerOrder"/></td>
          </tr>
          <tr>
            <th align="left">Average number of customers per day:</th>
            <td align="right"><s:property value="#request.results.averageNoCustomersPerDay"/></td>
          </tr>
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

