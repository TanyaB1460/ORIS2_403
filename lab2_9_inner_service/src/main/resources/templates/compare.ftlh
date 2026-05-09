<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Сравнение HTTP vs NATS</title>
</head>
<body>

<h2>Сравнение времени отклика: HTTP (прямой вызов) vs NATS (через брокер)</h2>

<div>
    <form action="/compare" method="post" enctype="multipart/form-data">
        <label>Выберите изображение:</label><br/>
        <input type="file" name="image"/><br/><br/>
        <label>Количество итераций:</label><br/>
        <input type="number" name="iterations" value="5" min="1" max="10"/><br/><br/>
        <button type="submit">Сравнить</button>
    </form>
</div>

<#if error??>
    <div style="color: red; margin-top: 20px;">
        <strong>Ошибка:</strong> ${error}
    </div>
</#if>

<#if httpTimes??>
    <hr/>
    <h3>Результаты сравнения</h3>

    <p><strong>HTTP (прямой вызов):</strong> среднее время ${httpAvg} ms</p>
    <p><strong>NATS (через брокер):</strong> среднее время ${natsAvg} ms</p>

    <p><strong>Вывод:</strong>
        <#if httpAvg < natsAvg>
            HTTP быстрее на ${diff} ms
        <#elseif natsAvg < httpAvg>
            NATS быстрее на ${diff} ms
        <#else>
            Время одинаковое
        </#if>
    </p>

    <h4>Детализация по итерациям:</h4>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr bgcolor="#cccccc">
            <th>Итерация</th>
            <th>HTTP (мс)</th>
            <th>NATS (мс)</th>
            <th>Разница</th>
        </tr>
        <#list httpTimes as i>
            <tr>
                <td>${i_index + 1}</td>
                <td>${httpTimes[i_index]}</td>
                <td>${natsTimes[i_index]}</td>
                <td>
                    <#assign diffIter = httpTimes[i_index] - natsTimes[i_index]>
                    <#if diffIter < 0>
                        ${-diffIter} мс (HTTP быстрее)
                    <#elseif diffIter > 0>
                        ${diffIter} мс (NATS быстрее)
                    <#else>
                        равно
                    </#if>
                </td>
            </tr>
        </#list>
    </table>
</#if>


</body>
</html>