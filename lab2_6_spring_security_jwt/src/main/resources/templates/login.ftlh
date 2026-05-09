<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Бронирования</title>
</head>
<body>
<div id="login_section">
    <h2>Вход</h2>
    <form id="login_form">
        <input name="username" type="text" placeholder="Логин"/>
        <input name="password" type="password" placeholder="Пароль"/>
        <button type="button" onclick="login()">Войти</button>
    </form>
    <p id="login_error" hidden>Неверный логин или пароль</p>
</div>

<div id="content_section" hidden>
    <h1>Список бронирований</h1>
    <button type="button" onclick="show_bookings()">Обновить</button>
    <button type="button" onclick="logout()">Выйти</button>

    <p></p>

    <table id="booking_table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Дата заезда</th>
            <th>Дата выезда</th>
            <th>Имя гостя</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>

    <p></p>

    <div id="edit_section" hidden>
        <h3>Редактирование</h3>
        <form id="edit_form">
            <input type="hidden" id="edit_id"/>
            <p>Дата заезда: <input type="date" id="edit_arrivalDate"/></p>
            <p>Дата выезда: <input type="date" id="edit_stayingDate"/></p>
            <p>Имя гостя: <input type="text" id="edit_name"/></p>
            <p id="save_error" hidden>Ошибка сохранения</p>
            <p id="save_success" hidden>Сохранено!</p>
            <button type="button" onclick="save_booking()">Сохранить</button>
            <button type="button" onclick="cancel_edit()">Отмена</button>
        </form>
    </div>
</div>

<script>
    var jwt_token;
    var selectedRow = null;

    function login() {
        var formElement = document.getElementById('login_form');
        var formData = new FormData(formElement);
        var jsonData = JSON.stringify(Object.fromEntries(formData));

        fetch('http://localhost:8090/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: jsonData
        })
            .then(function(response) {
                if (!response.ok) throw new Error('Error');
                return response.json();
            })
            .then(function(json) {
                jwt_token = json.token;
                document.getElementById("login_section").hidden = true;
                document.getElementById("content_section").hidden = false;
                document.getElementById("login_error").hidden = true;
                show_bookings();
            })
            .catch(function() {
                document.getElementById('login_error').hidden = false;
            });
    }

    function logout() {
        jwt_token = null;
        document.getElementById("login_section").hidden = false;
        document.getElementById("content_section").hidden = true;
        document.getElementById("edit_section").hidden = true;
        document.getElementById("login_error").hidden = true;
    }

    function show_bookings() {
        fetch('http://localhost:8090/api/booking/allview', {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + jwt_token } //серверу отправляется JWT, полученный после логина
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                make_table(data);
            })
            .catch(function(error) {
                console.error('Error:', error);
            });
    }

    function make_table(data) {
        var tbody = document.querySelector('#booking_table tbody');
        tbody.innerHTML = '';

        if (!data.bookings || data.bookings.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">Нет данных</td></tr>';
            return;
        }

        for (var i = 0; i < data.bookings.length; i++) {
            var b = data.bookings[i];
            var tr = document.createElement('tr');
            tr.setAttribute('data-id', b.id);
            tr.onclick = function() { //обработчик клика по строке
                edit_booking(this.getAttribute('data-id'), this);
            };

            tr.innerHTML = '<td>' + b.id + '</td>' +
                '<td>' + (b.arrivaldate || '-') + '</td>' +
                '<td>' + (b.stayingdate || '-') + '</td>' +
                '<td>' + (b.name || '-') + '</td>';

            tbody.appendChild(tr);
        }
    }

    function edit_booking(id, row) {
        selectedRow = row;

        fetch('http://localhost:8090/api/booking/get/' + id, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + jwt_token }
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(data) {
                document.getElementById('edit_id').value = data.id;
                document.getElementById('edit_arrivalDate').value = data.arrivalDate ? data.arrivalDate.split('T')[0] : '';
                document.getElementById('edit_stayingDate').value = data.stayingDate ? data.stayingDate.split('T')[0] : '';
                document.getElementById('edit_name').value = data.name || '';
                document.getElementById('save_error').hidden = true;
                document.getElementById('save_success').hidden = true;
                document.getElementById('edit_section').hidden = false;
            });
    }

    function save_booking() {
        var id = document.getElementById('edit_id').value;
        var data = {
            arrivalDate: document.getElementById('edit_arrivalDate').value,
            stayingDate: document.getElementById('edit_stayingDate').value,
            departureDate: document.getElementById('edit_stayingDate').value,
            personName: document.getElementById('edit_name').value
        };

        fetch('http://localhost:8090/api/booking/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwt_token
            },
            body: JSON.stringify(data)
        })
            .then(function(response) {
                if (!response.ok) throw new Error('Error');
                return response.json();
            })
            .then(function(result) {
                document.getElementById('save_success').hidden = false;
                document.getElementById('save_error').hidden = true;
                show_bookings();
            })
            .catch(function(error) {
                document.getElementById('save_error').hidden = false;
                document.getElementById('save_success').hidden = true;
            });
    }

    function cancel_edit() {
        document.getElementById('edit_section').hidden = true;
        document.getElementById('save_error').hidden = true;
        document.getElementById('save_success').hidden = true;
    }
</script>
</body>
</html>