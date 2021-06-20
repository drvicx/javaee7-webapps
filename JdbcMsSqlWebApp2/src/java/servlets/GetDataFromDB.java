package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
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
import java.util.List;
import java.util.ArrayList;


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
        //PrintWriter pw = response.getWriter();
        

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
            cstmt.setInt("PAR_YEAR", param1);           //--Год рождения;
            cstmt.setInt("PAR_PAGE_SIZE", param2);      //--Кол-во записей для выборки;
            cstmt.setInt("PAR_SORT", param3);           //--Флаг сортировки (1:сортировать по "birthdate", 0:сортировать по "ouid");
            
            //--выполняем запрос и результат помещаем в Объект-Итератор rs (RecordSet/ResultSet)
            ResultSet rs = cstmt.executeQuery();

            //--создаем массив в который будем помещать данные из ResultSet (rs)
            List dataList = new ArrayList();

            //--считываем RS в цикле и помещаем данные в массив
            while (rs.next()) {
                //--считываем значения полей в rs по именам
                dataList.add(rs.getString("ouid"));                         //--1:ID
                dataList.add(rs.getString("a_reg_orgname"));                //--2:ORGID
                dataList.add(rs.getString("a_surname_str"));                //--3:FNAME
                dataList.add(rs.getString("a_name_str"));                   //--4:LNAME
                dataList.add(rs.getString("a_secondname_str"));             //--5:MNAME
                dataList.add(rs.getString("birthdate").substring(0,10));    //--6:DOB
                dataList.add(rs.getString("a_pcstatus"));                   //--7:PCSTAT
                dataList.add(rs.getString("a_status"));                     //--8:OBSTAT
                dataList.add(rs.getString("ts").substring(0,19));           //--9:TS
            }
            //--закрываем объект подключения (в прошлом проекте CallableStatement явно не закрывался)
            cstmt.close();
            
            //--помещаем массив с данными в поле "data" в тело запроса(?) --почему не в response?
            request.setAttribute("data", dataList);
          
            //--перенаправление http-запроса на страницу "result.jsp" с результатом которая и будет выводить данные в таблицу
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/result.jsp");         
            if (dispatcher != null){
                dispatcher.forward(request, response);
            }

        }  
        //--перехватываем все ошибки которые могли произойти Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

  } //--doGet();
    
}
