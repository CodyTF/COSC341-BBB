<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>CUSTOMER REGISTRATION - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>CUSTOMER REGISTRATION - 3B.com</h1>
        <table><tr><td align="left"><s:actionerror/></td></tr></table>
		<s:form action="saveRegCust">
        <table>
        <tr>
          <td>
            <table>
            <tr>
              <th align="right">Username:</th>
              <td><s:textfield name="userName" size="10"/></td>
            </tr>
            <tr>
              <th align="right">PIN:</th>
              <td><s:password name="pin" size="6" showPassword="true"/>&nbsp;&nbsp;<strong>Re-type PIN:</strong>&nbsp;<s:password name="pin2" size="6" showPassword="true" /></td>
            </tr>
            <tr>
              <th align="right">First Name:</th>
              <td><s:textfield name="firstName" size="15"/></td>
            </tr>
            <tr>
              <th align="right">Last Name:</th>
              <td><s:textfield name="lastName" size="15"/></td>
            </tr>
            <tr>
              <th align="right">Address:</th>
              <td><s:textfield name="address" size="15"/></td>
            </tr>
            <tr>
              <th align="right">City:</th>
              <td><s:textfield name="city" size="15"/></td>
            </tr>
            <tr>
              <th align="right">State:</th>
              <td>
				<s:select name="state" size="1" list="#application.states" listKey="stateCode" listValue="stateName"/>&nbsp;&nbsp;<strong>ZIP:</strong>&nbsp;<s:textfield name="zipcode" size="5"/>
              </td>
            </tr>
            <tr>
              <th align="right">Credit Card:</th>
              <td>
                <s:select name="creditCardType" size="1" list="#application.ccTypes" listKey="ccType" listValue="ccType"/>&nbsp;&nbsp<s:textfield name="creditCardNumber" size="15"/>
              </td>
            </tr>
            <tr>
              <th align="right"></th>
              <td><strong>Expiration Date:<strong>&nbsp;<s:textfield name="creditCardExprDateString" size="5"/></td>
            </tr>
            </table>
          </td>
	</tr>
	<tr>
          <td>
            <center>
            <table>
            <tr>
               <td><s:submit name="submit" value="     Register     "/></td>
               <td><s:submit name="submit" value="Don't Register"/></td>
            </tr>
            </table>
            </center>
          </td>
        </tr>
        </table>
        <p></p>     
      </s:form>
    </center>
  </body>
</html>

