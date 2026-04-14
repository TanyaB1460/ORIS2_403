<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Новое лицо</title>
</head>
<body>
<h2>Новое лицо</h2>

<form action="/persons" method="post">
    <label>Имя:
        <input type="text" name="name"/>
    </label>
    <br/>

    <label>Тип:
        <select name="type">
            <option value="ADMIN">Админ</option>
            <option value="CLIENT">Клиент</option>
        </select>
    </label>
    <br/>

    <label>Номер телефона:
        <input type="text" name="phoneNumber"/>
    </label>
    <br/>

    <label>Email (для админа):
        <input type="email" name="email"/>
    </label>
    <br/>

    <label>Адрес (для клиента):
        <input type="text" name="address"/>
    </label>
    <br/>

    <button type="submit">Сохранить</button>
</form>

<a href="/">На главную</a>
</body>
</html>
