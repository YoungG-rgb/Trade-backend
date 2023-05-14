const $_BASE_ROOT_URL = /*[[@{/}]]*/ '/';
const userAddModal = $('#userAddForm')
const userEditModal = $('#userEditForm')

// load all users
$(document).ready(() => {
    $('#reloadUserFilterButton').click(function (e) {
        e.preventDefault();
        $('#users-table').DataTable().ajax.reload()
    });
    initUsersTable()
})
function initUsersTable() {
    $('#users-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}admin/users/filter`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchUserForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'username', orderable: true},
            {data: 'email', orderable: false},
            {data: 'balance', orderable: false},
            {data: 'addressModel', render: data => JSON.stringify(data), orderable: false},
            {data: 'phoneModels', render: data => data.map(i => i.number), orderable: false},
            {data: 'id', render: formatActionForUserAuditableColumns, orderable: false}
        ]
    })
}

function formatActionForUserAuditableColumns(data, type, row) {
    return `<td>
                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#userEditForm"
                onclick="loadAndShowModalUserEditForm(${row['id']})"> <i class="bi bi-pencil-square"></i> 
                </button>

                <button type="button" class="btn btn-danger" onclick="deleteUser(${row['id']})"> <i class="bi bi-trash3"></i> </button>
            </td>`
}

function deleteUser(id) {
    fetch(new Request('/admin/users/' + id, { method: 'DELETE', headers: {'content-type': 'application/json'}}))
        .then(function () {
            $('#users-table').DataTable().ajax.reload()
        });
}

function loadAndShowModalUserEditForm(id){
    fetch('/admin/users/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (user) {
                userEditModal.find('#idUserEdit').val(id);
                userEditModal.find('#mailEdit').val(user.email);
                userEditModal.find('#usernameEdit').val(user.username);
                userEditModal.find('#balanceEdit').val(user.balance);

                $(document.getElementById('editUserButton')).on('click', async function () {
                    fetch(new Request('/admin/users', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify({
                            'id': userEditModal.find('#idUserEdit').val(),
                            'username': userEditModal.find('#usernameEdit').val(),
                            'email': userEditModal.find('#mailEdit').val(),
                            'balance': userEditModal.find('#balanceEdit').val(),
                        })
                    })).then(resp => {
                        if (resp.ok) {
                            $('#users-table').DataTable().ajax.reload()
                            userEditModal.modal('hide')
                        } else {
                            alert('Ошибка')
                            throw Error('Что-то пошло не так: ' + resp.status)
                        }
                    })
                });
            })
        })
}

function addNewUserForm() {
    let arrayRoles = []
    let options = document.querySelector('#selectedRoles').options
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {id: options[i].value, name: options[i].text}
            arrayRoles.push(role)
        }
    }

    fetch(new Request('/admin/users', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                'username' : userAddModal.find('#username').val(),
                'email' : userAddModal.find('#mail').val(),
                'password' : userAddModal.find('#password').val(),
                'roleModels' : arrayRoles,
                'balance' : userAddModal.find('#balance').val(),
                'createdAt' : new Date()
            })
        })
    ).then(resp => {
        if (resp.ok) {
            $('#users-table').DataTable().ajax.reload()
            userAddModal.modal('hide')
            const { userAddForm } = document.forms;
            userAddForm.reset();
        } else {
            alert('Ошибка')
        }
    })
}