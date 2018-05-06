<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<html>
  <head>
     <title>PLACING RESTOCK ORDERS - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>PLACING RESTOCK ORDERS - 3B.com</h1>
      <table><tr><td align="left"><s:actionerror/></td></tr></table>
      <s:form action="placeRestockOrder"> 
      <s:hidden name="isbn" value="%{isbn}"/>
      <s:hidden name="orderIndex" value="%{orderIndex}"/>
      <s:hidden name="orderCount" value="%{orderCount}"/>
      <s:hidden name="placedCount" value="%{placedCount}"/>
      <s:hidden name="orderNumber" value="%{orderNumber}"/>
      <table>
        <tr>
          <td>
            <s:if test="%{orderCount == 0}">
              <strong>There are no pending orders</strong>
            </s:if>
            <s:else>
            <table>
              <tr>
                <th align="right">Pending Order:</th>
                <td align="left" width="250"><s:property value="orderIndex"/>&nbsp<strong>of</strong>&nbsp;<s:property value="orderCount"/></td>
              </tr>
              <tr>
                <th align="right">Order&nbsp;#:</th>
                <td align="left"><s:property value="orderNumber"/></td>
              </tr>
              <tr>
                <th align="right">ISBN:</th>
                <td align="left"><s:property value="isbn"/></td>
              </tr>
              <tr>
                <th align="right">Title:</th>
                <td align="left"><s:property value="title"/></td>
              </tr>
              <tr>
                <th align="right">Author:</th>
                <td align="left"><s:property value="authorString"/></td>
              </tr>
              <tr>
                <th align="right">Quantity in Stock:</th>
                <td align="left"><s:property value="onHand"/></td>
              </tr>
              <tr>
                <th align="right">Minimum Required:</th>
                <td align="left"><s:property value="orderPoint"/></td>
              </tr>
              <tr>
                <th align="right">Order Quantity:</th>
                <td align="left"><s:textfield name="quantity" size="5"/></td>
              </tr>
              <tr>
                <th align="right">Total Orders Placed:</th>
                <td align="left"><s:property value="placedCount"/></td>
              </tr>
            </table>
            </s:else>
          </td>
          <td width="20">&nbsp;</td>
          <td>
            <table>
              <s:if test="%{orderCount != 0}">
              <s:if test="%{orderIndex > 1}">
              <tr height="44"><td>
                <s:submit name="submit" value="  << Previous Order  "/>
              </td></tr>
              </s:if>
              <tr height="44"><td>
                <s:submit name="submit" value="       Skip Order        "/>
              </td></tr>
              <s:if test="%{orderIndex < orderCount}">
              <tr height="44"><td>
                <s:submit name="submit" value="      Next Order >>    "/>
              </td></tr>
              </s:if>
              <tr height="44"><td>
                <s:submit name="submit" value="      Submit Order     "/>
              </td></tr>
              </s:if>
              <tr height="44"><td>
                <s:submit name="submit" value="          Cancel          "/>
              </td></tr> 
            </table>
          </td>
        </tr>
      </table>
      </s:form>
    </center>
  </body>
</html>

