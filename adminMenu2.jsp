<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>ADMINISTRATION MENU - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>ADMINISTRATION MENU - 3B.com</h1>
         <table>
          <tr><td>
            <s:form action="reposition"> 
              <s:submit value="   Insert New Book    "/>
              <s:hidden name="submit" value="InsertNewBook"/>
      		</s:form>
          </td></tr>
          <tr><td>
            <s:form action="reposition"> 
              <s:submit value="Update/Delete Books"/>
              <s:hidden name="submit" value="Search"/>
      		</s:form>
          </td></tr>
          <tr><td>
            <s:form action="reposition"> 
              <s:submit value=" Place Book Orders  "/>
              <s:hidden name="submit" value="PlaceOrders"/>
      		</s:form>
          </td></tr>
          <tr><td>
            <s:form action="reposition"> 
              <s:submit value="          Reports          "/>
              <s:hidden name="submit" value="Reports"/>
      		</s:form>
          </td></tr>
          <tr><td>
            <s:form action="reposition"> 
              <s:submit value="      Update Profile     "/>
              <s:hidden name="submit" value="AdminProfile"/>
      		</s:form>
          </td></tr>
          <tr><td>
            <s:form action="/reposition"> 
              <s:submit name="submit" value="     EXIT 3-B.com      "/>
      		</s:form>
          </td></tr>
        </table>
    </center>
  </body>
</html>

