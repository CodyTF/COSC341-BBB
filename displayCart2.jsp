<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>SHOPPING CART - 3B.com</title>
  </head>
  <body>
    <s:form action="updateCart">
    <center>
      <h1>SHOPPING CART - 3B.com</h1>
        <table>
        <tr>
          <td>
            <s:if test="%{items.size == 0}">
              <strong>You have no items in your shopping cart.</strong><br/><br/>
            </s:if>
            <s:else>
              <table border="1" >
              <tr>
                <th>Remove</th>
                <th>Book Description</th>
                <th>Qty</th>
                <th>Price</th>
              </tr>
              <s:iterator status="itemIndex" value="items">
  		        <tr>
                  <td><s:submit name="items[%{#itemIndex.index}].submit" value="Remove"/><s:hidden name="items[%{#itemIndex.index}].isbn" value="%{isbn}"/></td>
                  <td valign="top" align="left" width="500">
                    <s:property value="title"/><br/><strong>By:</strong>&nbsp;<s:property value="authorString"/><br/><strong>Price:</strong>&nbsp;$&nbsp;<s:property value="price"/>
                  </td>
                  <td align="center"><s:textfield size="3" name="items[%{#itemIndex.index}].quantity" /></td>
                  <td align="right">$&nbsp;<s:property value="itemTotal"/></td>
              </tr>
              </s:iterator>
              <tr>
                <td colspan=2><s:submit name="submit" value="Recalculate Payment"/></td>
                <td><strong>Subtotal</strong></td>
                <td align="right">$&nbsp;<s:property value="subtotal"/></td>
              </tr>
              </table>
            </s:else>
          </td>
        </tr>
        <tr>
          <td>
            <center>
             </table>
             <tr>
               <s:if test="%{items.size != 0}">
                <td><s:submit name="submit" value="Proceed to Checkout"/></td>
               </s:if>
               <td><s:submit name="submit" value="         New Search         "/></td>
               <td><s:submit name="submit" value="       EXIT 3-B.com       "/></td>
             </tr>
             </table>
             </center>
          </td>
        </tr>
        </table>
    </center>
    </s:form>
  </body>
</html>

