<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>REPORT MENU - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>REPORT MENU - 3B.com</h1>
      <s:form action="getReport"> 
        <table>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'SalesByCategory':''}"/><td>
            <td width="400">
              Total sales from last month for each category.
            </td>
          </tr>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'StockByCategory':''}"/><td>
            <td width="400">
              Total number of books in stock for each category.
            </td>
          </tr>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'TopSellers':''}"/><td>
            <td width="400">
              List top ten best sellers, in descending order of sales for the last three months.
            </td>
          </tr>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'ExpensiveBooks':''}"/><td>
            <td width="400">
              List of the most expensive books for each book category in descending order.
             </td>
          </tr>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'DistinctBuyersByCategory':''}"/><td>
            <td width="400">
              For each book category, list the total number of distinct buyers (as itendified by their usernames) in the last month.
            </td>
          </tr>
          <tr>
            <td width="20"><s:radio name="selection" list="#{'Statistics':''}"/><td>
            <td width="400">
              Statistical report (1) Average amount of sale per customer, last month; (2) Average number of books per purchase per transaction; and (3) Average number of customers per day.
            </td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
          <tr>
          <td colspan="3" align="center">
            <s:submit name="submit"  value="    Print    "/>
            <s:submit name="submit"  value="    Cancel   "/>
          </td>
          </tr>
        </table>
      </s:form>
    </center>
  </body>
</html>

