<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>
       <s:if test="%{#session.administrator == null}">SEARCH</s:if>
       <s:else>UPDATE/DELETE BOOKS [Search]</s:else> - 3B.com
     </title>
  </head>
  <body>
    <center>
      <h1><s:if test="%{#session.administrator == null}">SEARCH</s:if>
       <s:else>UPDATE/DELETE BOOKS [Search]</s:else> - 3B.com
      </h1>
      <s:actionerror/>
      <s:fielderror/>  
      <s:form action="startSearch"> 
        <table>
        <tr>
          <td>
            <table>
            <tr>
              <th>Search For:</th>
              <td><s:textfield name="searchText"/></td>
            </tr>
            <tr>
              <th>Search In:</th>
              <td>
                <s:select name="searchIn" size="5" list="#{'anywhere':'Keyword anywhere', 'title':'Title', 'author':'Author', 'publisher':'Publisher', 'isbn':'ISBN'}" />
              </td>
            </tr>
            <tr>
              <th>Category:</th>
              <td>
                <s:select name="category" size="1" list="#application.categories"/>
              </td>
            </tr>
            </table>
          </td>
          <td width="40">&nbsp;</td>
          <td>
            <table>
            <tr>
               <td height="50"><s:submit name="submit" value="           Search            "/></td>
            </tr>
     		  <s:if test="%{#session.administrator == null}">
          	    <tr>
                   <td height="50"><s:submit name="submit" value="Manage Shopping Cart"/></td>
                </tr>
                <tr>
                   <td height="50"><s:submit name="submit"  value="       EXIT 3-B.com      "/></td>
                </tr>
              </s:if>
              <s:else>
          	    <tr>
                   <td height="50"><s:submit name="submit" value="           Cancel            "/></td>
                </tr>
              </s:else>          
            </table>
          </td>
        </tr>
        </table>
        <p></p>     
      </s:form>
    </center>
  </body>
</html>

