<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import ="java.util.ListIterator" %>
<!DOCTYPE html>
<html>
<head>
    <title>Result</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="result.css">
</head>
<body>        
    <!--ТЕСТОВЫЙ ВЫВОД-->
    <%--
        //--Заголовок страницы
        out.print("<h1>РЕЗУЛЬТАТ ЗАПРОСА</h1>");
        out.print("<hr/>");

        //--Массив содержащий результат чтения таблицы БД через ХП
        ArrayList listAcc = (ArrayList) request.getAttribute("data");
        ListIterator<String> iter = listAcc.listIterator();

        out.print("<table>");
        while(iter.hasNext()) {
            out.print("<tr><td>"+ iter.next() +"</td></tr>");
        }
        out.print("</table>");
    --%>


    <!--ЧИСТОВОЙ ВЫВОД-->    
    <%
        //--Массив содержащий результат чтения таблицы БД через ХП
        ArrayList listAcc = (ArrayList) request.getAttribute("data");
        ListIterator<String> iter = listAcc.listIterator();
        
        //--# | Заголовок HTML таблицы | поле MSSQL таблицы
        //  1   ID                       ouid
        //  2   ORGID                    a_reg_orgname
        //  3   FNAME                    a_surname_str
        //  4   LNAME                    a_name_str
        //  5   MNAME                    a_secondname_str
        //  6   DOB                      birthdate
        //  7   PCSTAT                   a_pcstatus
        //  8   OBSTAT                   a_status
        //  9   TS                       ts
    %>
    <div id="pageContainer">

        <div id="header">
            <h1>Результат чтения из БД MSSQL через ХП</h1>
        </div>

        <div id="centerBlock">

            <table id="userdata">
                <!--ЗАГОЛОВОК таблицы-->
                <tr>
                    <th>ID</th><th>ORGID</th><th>FNAME</th><th>LNAME</th><th>MNAME</th><th>DOB</th><th>TS</th>
                </tr>
                <!--СТРОКИ ДАННЫХ-->
            <%
                while (iter.hasNext()) {
                    String f1 = iter.next();    //--ID
                    String f2 = iter.next();    //--ORGID
                    String f3 = iter.next();    //--FNAME
                    String f4 = iter.next();    //--LNAME
                    String f5 = iter.next();    //--MNAME
                    String f6 = iter.next();    //--DOB
                    String f7 = iter.next();    //--PCSTAT
                    String f8 = iter.next();    //--OBSTAT
                    String f9 = iter.next();    //--TS
                    
                //--выводим значения полей по именам
                    out.print("<tr>");
                    out.print("<td><a href=\"http://172.16.1.221:8081/main_test/admin/edit.htm?id="+ f1 +"@wmPersonalCard\" target=\"_blank\">" + f1 + "</a></td>");
                    out.print("<td>" + f2 + "</td>");
                    out.print("<td>" + f3 + "</td>");
                    out.print("<td>" + f4 + "</td>");
                    out.print("<td>" + f5 + "</td>");
                    out.print("<td>" + f6 + "</td>");
                    out.print("<td>" + f9 + "</td>");
                    out.println("</tr>");
                }
            %>
            </table>

        </div> <!--centerBlock-->
    </div> <!--pageContainer-->

</body>
</html>
