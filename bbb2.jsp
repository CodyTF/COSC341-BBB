<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
     <title>Welcome to Best Buy Online Bookstore!</title>
  </head>
  <body>
    <center>
      <h1>Best Book Buy (3-B.com)</h1>
      <h3>Online Bookstore</h3>
      <s:form action="selectStart"> 
        <table border="2" bgcolor="#ffcccc">
          <tr><td>
            <table align="left">
              <tr><td align="left"><input type="radio" name="selection" value="Search">Search Only</input></td></tr>
              <tr><td align="left"><input type="radio" name="selection" value="New"/>New Customer</input></td></tr>
              <tr><td align="left"><input type="radio" name="selection" value="Returning"/>Returning Customer</input></td></tr>
              <tr><td align="left"><input type="radio" name="selection" value="Admin"/>Administrator</input></td></tr>
            </table>
          </td></tr>
        </table>
        <p></p>
        <s:submit value="ENTER"/>
      </s:form>
    </center>
  </body>
</html>

