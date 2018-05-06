<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>UPDATE ADMINISTRATOR PROFILE - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>UPDATE ADMINISTRATOR PROFILE - 3B.com</h1>
        <table><tr><td align="left"><s:actionerror/></td></tr></table>
		<s:form action="updateAdmin">
        <table>
        <tr>
          <td>
            <table>
            <tr>
              <th align="right">Username:</th>
              <td colspan="2"><s:property value="userName"/><s:hidden name="userName" value="%{userName}"/></td>
            </tr>
            <tr>
              <th align="right">PIN:</th>
              <td colspan="2"><s:password name="pin" size="6" showPassword="true"/>&nbsp;&nbsp;<strong>Re-type PIN:</strong>&nbsp;<s:password name="pin2" size="6" showPassword="true" /></td>
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
              <th align="right" valign="top">Phone(s):</th>
              <td valign="top">
                <table border="1">
        	        <s:iterator status="phoneIndex" value="phones">
                    <tr>
                      <td><s:textfield name="phones[%{#phoneIndex.index}].phone" size="10"/></td>
                      <td><s:checkbox name="phones[%{#phoneIndex.index}].delCheck"/></td>
                    </tr>
                    </s:iterator>
                </table>
              </td>
              <td valign="top">
		        <table>
                  <tr>
                    <td><s:submit name="submit" value="Add Phone"/></td>
                  </tr>
                  <tr>
                    <td><s:submit name="submit" value="Remove (*)"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            </table>
          </td>
	</tr>
	<tr>
          <td>
            <center>
            <table>
            <tr>
               <td><s:submit name="submit" value="     Update     "/></td>
               <td><s:submit name="submit" value="     Cancel     "/></td>
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

