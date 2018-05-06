<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>PROOF OF PURCHASE - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>PROOF OF PURCHASE - 3B.com</h1>
        <table>
        <tr>
          <td>
            <table width="500">
            <tr>
              <td valign="top"><strong>Shipping Address:</strong></br><s:property value="#session.cart.shipToFirstName"/>&nbsp;<s:property value="#session.cart.shipToLastName"/></br><s:property value="#session.cart.shipToAddress"/></br><s:property value="#session.cart.shipToCity"/>,&nbsp<s:property value="#session.cart.shipToState"/>.&nbsp;<s:property value="#session.cart.shipToZipcode"/></td>
              <td align="right">
                <table>
                <tr>
                  <th align="right">UserID:<th>
                  <td align="left"><s:property value="#session.cart.userName"/></td>
                </tr>
                <tr>
                  <th align="right">Date:<th>
                  <td align="left"><s:property value="#session.cart.orderDate"/></td>
                </tr>
                <tr>
                  <th align="right">Credit Card:<td>
                  <td ><s:property value="#session.cart.creditCardType"/> - <s:property value="#session.cart.creditCardNumber"/></td>
                </tr>
                </table>
              </td>
            </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table border="1" >
            <tr>
              <th>Book Description</th>
              <th>Qty</th>
              <th>Price</th>
            </tr>
  			<s:iterator value="#session.cart.orderItems">
            <tr>
              <td valign="top" align="left" width="350">
                   <s:property value="title"/><br/><strong>By:</strong>&nbsp;<s:property value="authorString"/><br/><strong>Price:</strong>&nbsp;$&nbsp;<s:property value="priceAtPurchase"/>
              </td>
              <td align="center" width="75"><s:property value="quantityOrdered"/></td>
              <td align="right" width="75">$&nbsp;<s:property value="itemTotalString"/></td>
            </tr>
            </s:iterator>
            <tr>
              <td rowspan="3" align="left" valign="top" width="350"><strong>SHIPPING NOTE:</strong> The books will be delivered within 5 business days</td>
              <td align="right"><strong>Subtotal</strong></td>
              <td align="right">$&nbsp;<s:property value="#session.cart.subtotalString"/></td>
            </tr>
            <tr>
              <td align="right"><strong>S & H</strong></td>
              <td align="right">$&nbsp;<s:property value="#session.cart.shippingHandlingString"/></td>
            </tr>
            <tr>
              <td align="right"><strong>Total</strong></td>
              <td align="right">$&nbsp;<s:property value="#session.cart.totalString"/></td>
            </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <center>
              <table>
              <tr>
                <td>        	  
                  <s:form action="reposition"> 
                    <s:submit value="         Print        "/></td>
                    <s:hidden name="submit" property="submit" value=""/>
      		      </s:form>
                <td>
                  <s:form action="reposition"> 
                    <s:submit value="     New Search     "/></td>
                    <s:hidden name="submit" value="ClearAndSearch"/>
      		      </s:form>
                <td>
                  <s:form action="reposition"> 
                    <s:submit name="submit" value="  EXIT 3-B.com  "/></td>
      		      </s:form>
              </tr>
              </table>
            </center>
          </td>
        </tr>
        </table>
    </center>
  </body>
</html>

