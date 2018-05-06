<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>USER LOGIN - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>USER LOGIN - 3B.com</h1>
        <table><tr><td align="left"><s:actionerror/></td></tr></table>
		<s:form action="logon">
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
              <td><s:password name="pin" size="6" showPassword="true"/></td>
            </tr>
            </table>
          </td>
          <td>
            <table>
            <tr>
               <td><s:submit name="submit" value="     Login     "/></td>
            </tr>
            <tr>
               <td><s:submit name="submit" value="     Cancel    "/></td>
            </tr>
             </table>
          </td>
        </tr>
        </table>
      </s:form>
    </center>
  </body>
</html>

