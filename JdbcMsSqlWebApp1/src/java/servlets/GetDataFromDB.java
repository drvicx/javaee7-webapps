package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.io.IOException;
import java.io.PrintWriter;


/**
 *=Class GetDataFromDB
 * - получение данных из хранимой процедуры базы MSSQL
 * - HTTP-ответ формируется сервлетом (логика обработки данных и логика отображения смешаны)
 */
public class GetDataFromDB extends HttpServlet {
    
    //--HTTP Get request/response handler
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //--ПАРАМЕТРЫ ПОДКЛЮЧЕНИЯ К БД / DATA SOURCE
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("KM321z7avQ");
        ds.setServerName("172.16.1.221");
        ds.setPortNumber(1433);
        ds.setDatabaseName("esrn_test");
        //*connUrl = "jdbc:sqlserver://172.16.1.221:1433;databaseName=esrn_test;user=sa;password=KM321z7avQ";
        
        
        //--REQUEST PROCESSING--
        //--принимаем параметры из GET-запроса и преообразуем их в целые числа;
        int param1 = Integer.parseInt(request.getParameter("param1"));
        int param2 = Integer.parseInt(request.getParameter("param2"));
        int param3 = Integer.parseInt(request.getParameter("param3"));
      
        //--RESPONSE PROCESSING--
        //--устраняем проблему с выводом кириллицы в response из сервлета; 
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //--Объект для печати данных в response
        PrintWriter pw = response.getWriter();
        

        //--ЗАГРУЗКА ДРАЙВЕРА БД
        //--перед выполнением подключения к БД необходимо загрузить/подключить драйвер для выбранной СУБД к нашему приложению
        //  иначе будет ошибка (no suitable driver found) ДАЖЕ при подключенной JDBC библиотеке с драйвером
        // !т.к это критически важная часть, необходимо завернуть ее в try-catch блок
        //  чтобы была возможность перехватывать ошибки!
        // !если этого не сделать, то при недоступности драйвера в памяти,
        //  приложение просто не сможет подключится к базе и при этом не будет никаких ВИДИМЫХ ошибок
        //
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    
        //--ОТКРЫВАЕМ ПОДКЛЮЧЕНИЕ К БД
        // !обрабатываем ошибку подключения к БД в блоке try-catch
        //  *если сервер не запущен, или не доступен, или не доступна БД, или допущена любая ошибка в строке подключения
        
        //--TRY-CATCH
        //--создем объект который будет выполнять SQL-запрос к Хранимой Процедуре (ХП) в БД
        try (Connection con = ds.getConnection(); CallableStatement cstmt = con.prepareCall("{call dbo.nveGetPCByBirthYear(?, ?, ?)}");) {
            
            //--уставливаем параметры для ХП
            cstmt.setInt("PAR_YEAR", param1);           //--2021
            cstmt.setInt("PAR_PAGE_SIZE", param2);      //--3
            cstmt.setInt("PAR_SORT", param3);           //--1
            
            //--выполняем запрос и результат помещаем в Объект-Итератор rs (RecordSet/ResultSet)
            ResultSet rs = cstmt.executeQuery();

            //--заголовки данных для вывода
            String h1 = "ID";
            String h2 = "ORGID";
            String h3 = "FNAME";
            String h4 = "LNAME";
            String h5 = "MNAME";
            String h6 = "DOB";
            String h7 = "PCSTATUS";
            String h8 = "OBSTATUS";
            String h9 = "TS";
    
            pw.println("<!DOCTYPE html>");
            pw.println("<html>"); 
            pw.println("<head>");
            pw.println("<title>Collected Data</title>");
            pw.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
            pw.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles2.css\">");
            pw.println("</head>");
                        
            pw.println("<body>");
            pw.println("<p class=\"component-header\">РЕЗУЛЬТАТ ЧТЕНИЯ ИЗ БД MSSQL ЧЕРЕЗ ХП</p>");

            pw.println("<table id=\"userdata\">");
            
            //--выводим заголовки
            pw.println("<tr>");
            pw.println("<th>" + h1 + "</th><th>" + h2 + "</th><th>" + h3 + "</th><th>" + h4 + "</th><th>" + h5 + "</th><th>" + h6 + "</th><th>" + h9 + "</th>");
            pw.println("</tr>");

            //--print data lines
            while (rs.next()) {
                //--выводим значения полей по именам
                pw.println("<tr>");
                pw.println("<td>" + rs.getString("ouid") + "</td>");
                pw.println("<td>" + rs.getString("a_reg_orgname") + "</td>");
                pw.println("<td>" + rs.getString("a_surname_str") + "</td>");
                pw.println("<td>" + rs.getString("a_name_str") + "</td>");
                pw.println("<td>" + rs.getString("a_secondname_str") + "</td>");
                pw.println("<td>" + rs.getString("birthdate").substring(0,10) + "</td>");
                //pw.println("<td>" + rs.getString("a_pcstatus") + "</td>");
                //pw.println("<td>" + rs.getString("a_status") + "</td>");
                pw.println("<td>" + rs.getString("ts").substring(0,19) + "</td>");
                pw.println("</tr>");
                pw.println("\n");
            }
            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");
            pw.println("\n");
            
            //--закрываем объект подключения (в прошлом проекте CallableStatement явно не закрывался)
            cstmt.close();

        }  
        //--перехватываем все ошибки которые могли произойти Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

  } //--doGet();
    
}
