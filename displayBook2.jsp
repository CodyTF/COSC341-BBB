<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title><s:property value="actionTitle"/> BOOK - 3B.com</title>
  </head>
  <body>
    <center>
      <h1><s:property value="actionTitle"/> BOOK - 3B.com</h1>
        <table><tr><td align="left"><s:actionerror/></td></tr></table>
		<s:form action="/insertUpdateBook">
		<s:hidden name="onHand" value="%{onHand}"/>
		<s:hidden name="actionTitle" value="%{actionTitle}"/>
        <table>
        <tr>
          <td>
            <table>
            <tr>
              <th align="right">ISBN:</th>
              <td>
                <s:if test="%{actionTitle == 'INSERT NEW'}">
                  <s:textfield name="isbn" size="13"/>
                </s:if>
                <s:else>
                  <s:property value="isbn"/>&nbsp;&nbsp;&nbsp;<strong>Status:</strong>&nbsp;
                  <s:select name="active" size="1" list="activeValues"/>
          		  <s:hidden name="isbn" value="%{isbn}"/>     
                </s:else>
              </td>
            </tr>           
            <tr>
              <th align="right">Title:</th>
              <td><s:textfield name="title" size="35"/></td>
           </tr>
           <tr>
              <th align="right" valign="top"><br/><br/>Author(s):</th>
              <td>
                <table>
                <tr>
                  <td>
                    <table border="1">
                    <tr>
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>*</th>
                    </tr>
        	        <s:iterator status="authorIndex" value="authors">
                    <tr>
                      <td><s:textfield name="authors[%{#authorIndex.index}].firstName" size="15"/></td>
                      <td><s:textfield name="authors[%{#authorIndex.index}].lastName" size="15"/></td>
                      <td><s:checkbox name="authors[%{#authorIndex.index}].delCheck"/></td>
                    </tr>
                    </s:iterator>
                    </table>
                  </td>
                  <td valign="top">
		            <table>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                      <td><s:submit name="submit" value="    Add Author     "/></td>
                    </tr>
                    <tr>
                      <td><s:submit name="submit" value="Remove Author(*)"/></td>
                    </tr>
                    </table>
                  </td>
                </tr>
                </table>
              </td>
            </tr>
            <tr>
              <th align="right">Publisher:</th>
              <td>                
                 <s:select name="publisher" size="1" list="#application.publishers"/>
                 &nbsp;&nbsp;<strong>Year:</strong>&nbsp<s:textfield name="yearPublished" size="4"/>
              </td>
            </tr>
            <tr>
              <th align="right">Category:</th>
              <td>
                <s:select name="category" size="1" list="#application.categories"/>
              </td>
            </tr>
           <tr>
              <th align="right">Price:</th>
              <td><s:textfield name="price" size="6"/>&nbsp;&nbsp;&nbsp;&nbsp;<strong>Min. Required In-Stock:</strong>&nbsp;<s:textfield name="minQuantity" size="5"/></td>
           </tr>
           <tr>
              <th align="right" valign="top"><br/><br/>Review(s):</th>
              <td>
                <table>
                <tr>
                  <td>
                    <table border="1">
                    <tr>
                      <th>Text</th>
                      <th>*</th>
                    </tr>
        	        <s:iterator status="reviewIndex" value="reviews">
                    <tr>
                      <td><s:textarea name="reviews[%{#reviewIndex.index}].text" cols="30" rows="4"/></td>
                      <td><s:checkbox name="reviews[%{#reviewIndex.index}].delCheck"/></td>
                    </tr>
                    </s:iterator>
                    <tr>
                    </table>
                  </td>
                  <td valign="top">
		            <table>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                      <td><s:submit name="submit" value="    Add Review     "/></td>
                    </tr>
                    <tr>
                      <td><s:submit name="submit" value="Remove Review(*)"/></td>
                    </tr>
                    </table>
                  </td>
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
             <s:if test="%{actionTitle == 'INSERT NEW'}">
               <td><s:submit name="submit" value="     Insert     "/></td>
             </s:if>
             <s:else>
               <td><s:submit name="submit" value="     Update     "/></td>
             </s:else>
             <td><s:submit name="submit" value="     Cancel     "/></td>
           </tr>
           </table>
           </center>
         </td>
       </tr>
       </table>
      </s:form>
    </center>
  </body>
</html>

