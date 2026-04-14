<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная страница</title>
</head>
<body>

<h2>Главная страница</h2>

<h3>Лица</h3>
<div>
    <#list persons as person>
        <div>
            <span>${person.id}</span>
            <span>${person.name}</span>
            <#if person.phone??>
                <span>${person.phone.number!''}</span>
            <#else>
                <span></span>
            </#if>
        </div>
    </#list>

</div>

<a href="/persons/new">Добавить лицо</a>

</body>
</html>

