<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>

    <div id="pageContainer">
        
        <div id="header">
            <h1>Выборка ЛД по Году рождения</h1>
        </div>
        
        <div id="centerBlock">
    
            <h2>Параметры запроса</h2>
            <br><br>
            
            <form action="getdata" target="_blank">
                <table id="formTable">
                    <tr> <th class="colA">Параметр</th>     <th class="colB">Значение</th>  </tr>

                    <tr> <td class="colA">Год</td>          <td class="colB"><input name="param1" type="number" min="1900" max="2021" placeholder="год" value="2021"/></td></tr>
                    <tr> <td class="colA">Записей</td>      <td class="colB"><input name="param2" type="number" min="1" placeholder="колво записей" value="5"/></td></tr>
                    <tr> <td class="colA">Сортировка</td>   <td class="colB"><input name="param3" type="number" min="0" max="1" placeholder="0:нет, 1:да" value="1"/></td></tr>
                <!--<tr> <td class="colA">Сортировка</td>   <td class="colB"><input type="text" name="param3" placeholder="0:нет, 1:да" value=""/></td></tr>-->

                    <tr id="emptyRow" style="color: red;"><td colspan="2"></td></tr>
                    <tr>
                        <td><button class="submitButton" type="submit">ОТПРАВИТЬ</button</td>
                        <td><button class="submitButton" type="reset">СБРОСИТЬ</button</td>
                    </tr>
                </table>
            </form>

        </div> <!--centerBlock-->
    </div> <!--pageContainer-->
    
    <br><br>
    <hr/>
    <h3>Тестовые ссылки</h3>
    <p>1: <a href="test1?param1=value1&param2=value2&param3=value3">Проверка передачи GET-параметров</a></p>

</body>
</html>
