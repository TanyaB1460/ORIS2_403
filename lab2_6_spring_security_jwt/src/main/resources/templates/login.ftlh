<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Бронирования</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        h2 { color: #333; }

        /* Login */
        #login_section { max-width: 320px; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        #login_section input[type="text"], #login_section input[type="password"] {
            width: 100%; padding: 8px; margin: 6px 0 12px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px;
        }
        #login_section input[type="button"] {
            background: #01696f; color: #fff; border: none; padding: 10px 24px; border-radius: 4px; cursor: pointer; font-size: 14px;
        }
        #login_section input[type="button"]:hover { background: #0c4e54; }
        #login_error { color: red; font-size: 13px; margin-top: 8px; }

        /* Content */
        #content_section { display: none; }
        #content_section h2 { margin-bottom: 12px; }

        /* Table */
        #booking_table { width: 100%; border-collapse: collapse; background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.08); border-radius: 8px; overflow: hidden; }
        #booking_table th { background: #01696f; color: #fff; padding: 10px 14px; text-align: left; font-size: 14px; }
        #booking_table td { padding: 10px 14px; border-bottom: 1px solid #eee; font-size: 14px; }
        #booking_table tr:last-child td { border-bottom: none; }
        #booking_table tr:hover td { background: #f0f9f9; cursor: pointer; }
        .active-row td { background: #e0f2f2 !important; font-weight: bold; }

        /* Booking Form */
        #booking_form_section {
            display: none;
            margin-top: 28px;
            background: #fff;
            padding: 24px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            max-width: 500px;
        }
        #booking_form_section h3 { margin-top: 0; color: #01696f; }
        .form-row { margin-bottom: 14px; }
        .form-row label { display: block; font-size: 13px; color: #555; margin-bottom: 4px; }
        .form-row input[type="text"], .form-row input[type="date"] {
            width: 100%; padding: 8px 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 14px;
        }
        .form-row input:focus { border-color: #01696f; outline: none; box-shadow: 0 0 0 2px rgba(1,105,111,0.15); }
        .form-separator { border: none; border-top: 1px solid #e0e0e0; margin: 16px 0; }
        #save_btn {
            background: #01696f; color: #fff; border: none; padding: 10px 28px;
            border-radius: 4px; cursor: pointer; font-size: 15px; margin-top: 4px;
        }
        #save_btn:hover { background: #0c4e54; }
        #save_status { margin-top: 10px; font-size: 13px; }
        #save_status.ok { color: #437a22; }
        #save_status.err { color: #a12c7b; }
    </style>
</head>
<body>

<!-- Блок логина -->
<div id="login_section">
    <h2>Вход</h2>

    <form id="form" method="post" autocomplete="off">
        <!-- фейковые поля против автоподстановки браузера -->
        <input type="text" name="fake_username" style="display:none">
        <input type="password" name="fake_password" style="display:none">

        <div>
            <input id="username"
                   name="real_username"
                   type="text"
                   placeholder="Логин"
                   autocomplete="off"/>
        </div>

        <div>
            <input id="password"
                   name="real_password"
                   type="password"
                   placeholder="Пароль"
                   autocomplete="new-password"
                   data-lpignore="true"
                   data-1p-ignore
                   data-bwignore="true"/>
        </div>

        <div>
            <input type="button" value="Войти" onclick="login()"/>
        </div>
    </form>

    <div id="login_error"></div>
</div>

<!-- Основной контент -->
<div id="content_section">
    <h2>Данные по бронированиям в гостинице:</h2>
    <table id="booking_table">
        <thead>
        <tr>
            <th>Номер комнаты</th>
            <th>Дата заезда</th>
            <th>Дата выезда</th>
        </tr>
        </thead>
        <tbody id="booking_tbody"></tbody>
    </table>

    <!-- Форма просмотра/редактирования брони -->
    <div id="booking_form_section">
        <h3>Бронирование #<span id="form_booking_id"></span></h3>

        <p style="font-size:13px;color:#888;margin-top:-8px;margin-bottom:16px;">
            Нажмите на строку в таблице, чтобы загрузить данные бронирования. Измените нужные поля и нажмите «Сохранить».
        </p>

        <div class="form-row">
            <label>Дата заезда</label>
            <input type="date" id="f_arrivaldate"/>
        </div>
        <div class="form-row">
            <label>Дата проживания (до)</label>
            <input type="date" id="f_stayingdate"/>
        </div>
        <div class="form-row">
            <label>Дата выезда</label>
            <input type="date" id="f_departuredate"/>
        </div>

        <hr class="form-separator"/>
        <div style="font-size:13px;color:#888;margin-bottom:10px;">Данные гостя</div>

        <div class="form-row">
            <label>Имя</label>
            <input type="text" id="f_name"/>
        </div>
        <div class="form-row">
            <label>Пол</label>
            <input type="text" id="f_gender" placeholder="М / Ж"/>
        </div>
        <div class="form-row">
            <label>Дата рождения</label>
            <input type="date" id="f_birthdate"/>
        </div>

        <button id="save_btn" onclick="save_booking()">Сохранить</button>
        <div id="save_status"></div>
    </div>
</div>

<script>
    let jwt_token;
    let current_booking_id;

    // ========================
    // Авторизация
    // ========================
    function login() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const jsonData = JSON.stringify({
            username: username,
            password: password
        });

        fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=utf-8' },
            body: jsonData
        })
            .then(response => {
                if (!response.ok) throw new Error('Неверный логин или пароль');
                return response.json();
            })
            .then(json => {
                jwt_token = json.token;
                document.getElementById('login_section').style.display = 'none';
                document.getElementById('content_section').style.display = 'block';
                show_bookings();
            })
            .catch(err => {
                document.getElementById('login_error').textContent = err.message;
            });
    }

    // ========================
    // Загрузка списка броней
    // ========================
    function show_bookings() {
        fetch('/api/booking/allview', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + jwt_token
            }
        })
            .then(response => {
                console.log('ALLVIEW STATUS =', response.status);
                return response.json();
            })
            .then(data => {
                console.log('ALLVIEW DATA =', data);
                make_booking_table(data);
            })
            .catch(err => {
                console.log('ALLVIEW ERROR =', err);
            });
    }

    function make_booking_table(data) {
        const tbody = document.getElementById('booking_tbody');
        tbody.innerHTML = '';

        const list = Array.isArray(data) ? data : data.bookings;
        console.log('TABLE LIST =', list);

        if (!list || list.length === 0) {
            tbody.innerHTML = '<tr><td colspan="3">Нет данных</td></tr>';
            return;
        }

        list.forEach(function(b) {
            console.log('BOOKING ITEM =', b);

            const tr = document.createElement('tr');
            tr.setAttribute('data-id', b.id);
            tr.onclick = function() { open_booking(b.id, tr); };

            const td_room = document.createElement('td');
            td_room.textContent = b.room ?? '-';

            const td_arrival = document.createElement('td');
            td_arrival.textContent = b.arrivaldate ?? '-';

            const td_staying = document.createElement('td');
            td_staying.textContent = b.stayingdate ?? '-';

            tr.appendChild(td_room);
            tr.appendChild(td_arrival);
            tr.appendChild(td_staying);
            tbody.appendChild(tr);
        });
    }

    // ========================
    // Открыть бронь → заполнить форму
    // ========================
    function open_booking(booking_id, row) {
        // Подсветка активной строки
        document.querySelectorAll('#booking_tbody tr').forEach(r => r.classList.remove('active-row'));
        if (row) row.classList.add('active-row');

        fetch('http://localhost:8090/api/booking/get/' + booking_id, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + jwt_token }
        })
            .then(response => response.json())
            .then(json => fill_booking_form(json));
    }

    function toDateInputValue(dateVal) {
        if (!dateVal) return '';
        const d = new Date(dateVal);
        return d.toISOString().split('T')[0];
    }

    function fill_booking_form(data) {
        current_booking_id = data.id;
        document.getElementById('form_booking_id').textContent = data.id;
        document.getElementById('f_arrivaldate').value   = toDateInputValue(data.arrivaldate);
        document.getElementById('f_stayingdate').value   = toDateInputValue(data.stayingdate);
        document.getElementById('f_departuredate').value = toDateInputValue(data.departuredate);
        document.getElementById('f_name').value          = data.name || '';
        document.getElementById('f_gender').value        = data.gender || '';
        document.getElementById('f_birthdate').value     = toDateInputValue(data.birthDate);

        document.getElementById('save_status').textContent = '';
        document.getElementById('save_status').className = '';
        document.getElementById('booking_form_section').style.display = 'block';
    }

    // ========================
    // Сохранение изменений
    // ========================
    function save_booking() {
        if (!current_booking_id) return;

        const payload = {
            arrivaldate:   document.getElementById('f_arrivaldate').value,
            stayingdate:   document.getElementById('f_stayingdate').value,
            departuredate: document.getElementById('f_departuredate').value,
            name:          document.getElementById('f_name').value,
            gender:        document.getElementById('f_gender').value,
            birthDate:     document.getElementById('f_birthdate').value
        };

        fetch('http://localhost:8090/api/booking/update/' + current_booking_id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': 'Bearer ' + jwt_token
            },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (!response.ok) throw new Error('Ошибка сохранения');
                return response.json();
            })
            .then(json => {
                const status = document.getElementById('save_status');
                status.textContent = '✓ Изменения сохранены';
                status.className = 'ok';
                // Обновляем таблицу, не покидая страницу
                show_bookings();
            })
            .catch(err => {
                const status = document.getElementById('save_status');
                status.textContent = '✗ ' + err.message;
                status.className = 'err';
            });
    }

</script>

</body>
</html>