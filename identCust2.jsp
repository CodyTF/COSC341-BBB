<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>LOGIN NEEDED - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>LOGIN NEEDED - 3B.com</h1>
    <p>In order to proceed with payment, you must first login.</br>If you do not have a Username, please use the New Customer button</p>
        <s:actionerror/>
		<s:form action="identLogon">
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
              <td><s:password name="pin" size="6" redisplay="false"/></td>
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
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <s:submit name="submit" value="           New Customer          "/>
          </td>
        </tr>
        </table>
      </s:form>
    </center>
  </body>
</html>

