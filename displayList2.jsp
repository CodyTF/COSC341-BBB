<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
  <head>
     <title>
     	<s:if test="%{#session.administrator ne null}">UPDATE/DELETE BOOKS</s:if>
        <s:elseif test="%{session.welcome ne null}"><s:property value="#session.welcome"/></s:elseif>
        <s:else>SEARCH RESULT</s:else>
        - 3B.com</title>
  </head>
  <body>
    <center>
      <h1>
     	<s:if test="%{#session.administrator ne null}">UPDATE/DELETE BOOKS</s:if>
        <s:elseif test="%{session.welcome ne null}"><s:property value="#session.welcome"/></s:elseif>
        <s:else>SEARCH RESULT</s:else>
        - 3B.com</h1>
        <table>
        <s:if test="%{#session.administrator == null}">
        <tr>
          <td>
            <s:form action="reposition"> 
              <strong>Your Shopping Cart has 
              <s:if test="%{#session.cart == null}">0</s:if>
              <s:else><s:property value="#session.cart.numberItems"/></s:else>
              items</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit name="submit" value="Manage Shopping Cart"/>
            </s:form>
          </td>
        </tr>
        </s:if>
        <tr>
          <td>
            <table border="1" >
            <tr>
              <td>
                <table>
 			      <s:iterator value="#session.bookQuery">
                    <tr>
                      <td valign="top">
                        <table>
     		  			  <s:if test="%{#session.administrator == null}">
                            <s:form action="updateCart"> 
                            <tr>
                              <td>
                                <s:submit name="submit" value="Add to Cart"/>
                                <s:hidden name="isbn" value="%{isbn}"/>
                              </td>
                            </tr>
                            </s:form>
                            <tr>
                              <td><s:submit name="viewreviews" value="  Reviews   "
                              onClick="window.open('getBookReview.action?isbn=${isbn}','reviews','height=400,width=350,top=200,left=500,resizable=no,scrollbars=yes'); return false;"/></td>
                            </tr>
     		  			  </s:if>
              			  <s:else>
                            <s:form action="getUpdateBook"> 
                            <tr>
                              <td height="30">
                                <s:submit name="submit" value=" Update  "/>
                                <s:hidden name="isbn" value="%{isbn}"/>
                            </td>
                            </tr>
                            </s:form>
              			  </s:else>           			
                        </table>
                      </td> 
                    <td valign="top" align="left" width="500">
                        <s:property value="title"/><br/><strong>By:</strong>&nbsp;<s:property value="authorString"/><br/><strong>Publisher:</strong>&nbsp;<s:property value="publisher"/><br/><strong>ISBN:</strong>&nbsp;<s:property value="isbn"/>&nbsp;&nbsp;<strong>Price:</strong>&nbsp;<s:property value="price"/>
                        <s:if test="%{!active}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><font color="#ff0000">D&nbsp;E&nbsp;L&nbsp;E&nbsp;T&nbsp;E&nbsp;D</font></strong></s:if>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2"><hr/></td>
                  </tr>
  			      </s:iterator>
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
     		  <s:if test="%{#session.administrator == null}">
                <s:form action="reposition"> 
                  <td><s:submit name="submit" value="Proceed to Checkout"/></td>
                  <td><s:submit name="submit" value="         New Search         "/></td>
                  <td><s:submit name="submit" value="       EXIT 3-B.com       "/></td>
                </s:form>
              </s:if>
              <s:else>
                <td>
                  <s:form action="/reposition">
                    <s:submit name="submit" value="         New Search         "/>
                   </s:form>
                </td>
                <td>
                  <s:form action="reposition">
                     <s:submit name="submit" value="                Done             "/>
                    </s:form>
                </td>
              </s:else>           
            </tr>
            </table>
            </center>
          </td>
        </tr>
        </table>
    </center>
  </body>
</html>

