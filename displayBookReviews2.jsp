<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>BOOK REVIEWS - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>BOOK REVIEWS - 3B.com</h1>
        <table>
        <tr>
          <td>
            <table>
            <tr>
              <th align="right">ISBN:</th>
              <td><s:property value="#request.book.isbn"/></td>
            </tr>
            <tr>
              <th align="right">Title:</th>
              <td><s:property value="#request.book.title"/></td>
            </tr>
           <tr>
              <th align="right">Author(s):</th>
              <td><s:property value="#request.book.authorString"/></td></td>
            </tr>
            <tr>
              <th align="right">Publisher:</th>
              <td><s:property value="#request.book.publisher"/></td>
            </tr>
            <tr>
              <th align="right">Category:</th>
              <td><s:property value="#request.book.category"/></td>
            </tr>
           <tr>
              <th align="right">Price:</th>
              <td><s:property value="#request.book.price"/></td>
           </tr>
           <tr>
              <th align="right" valign="top">Review(s):</th>
              <td><table cellspacing="0" cellpadding="0">
        	        <s:iterator value="#request.book.reviews">
                        <tr><td width="200"><s:property value="review"/></td></tr>
                        <tr><td>&nbsp;</td></tr>
                    </s:iterator>
                  </table>
              </td>
           </tr>
           </table>
         </td>
       </tr>
       </table>
    </center>
  </body>
</html>

