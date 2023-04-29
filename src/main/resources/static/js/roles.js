const rolesAddForm = $('#rolesAddForm');
const rolesEditForm = $('#rolesEditForm')

// load all roles
$(document).ready(() => {
    $('#reloadRolesFilterButton').click(function (e) {
        e.preventDefault();
        $('#roles-table').DataTable().ajax.reload()
    });
    initRolesTable()
})

function initRolesTable() {
    $('#roles-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}admin/roles/filter`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchRoleForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'id', orderable: true},
            {data: 'name', orderable: false},
            {data: 'id', render: formatActionForAuditableColumns, orderable: false}
        ]
    })
}

function formatActionForAuditableColumns(data, type, row) {
    return `<td>
                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#rolesEditForm"
                onclick="loadAndShowRoleEditForm(${row['id']})"> <i class="bi bi-pencil-square"></i> 
                </button>

                <button type="button" class="btn btn-danger" onclick="deleteRole(${row['id']})"> <i class="bi bi-trash3"></i> </button>
            </td>`
}
function addNewRole() {
    let roleName = rolesAddForm.find('#roleName').val();
    let roleId = rolesAddForm.find('#rolesId').val();

    fetch(new Request('/admin/roles', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                'id' : roleId,
                'name' : roleName
            })
        })
    ).then(resp => {
        if (resp.ok) {
            $('#roles-table').DataTable().ajax.reload()
            rolesAddForm.modal('hide')
        } else {
            alert('Ошибка')
        }
    })
}
function deleteRole(id) {
    fetch(new Request('/admin/roles/' + id, { method: 'DELETE', headers: {'content-type': 'application/json'}}))
        .then(function () {
            $('#roles-table').DataTable().ajax.reload()
        });
}
function loadAndShowRoleEditForm(id) {
    fetch('/admin/roles/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (role) {
                rolesEditForm.find('#rolesEditId').val(id);
                rolesEditForm.find('#roleEditName').val(role.name);

                $(document.getElementById('editRoleButton')).on('click', async function () {
                    fetch(new Request('/admin/roles', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify({
                            'id': rolesEditForm.find('#rolesEditId').val(),
                            'name': rolesEditForm.find('#roleEditName').val(),
                        })
                    })).then(resp => {
                        if (resp.ok) {
                            $('#roles-table').DataTable().ajax.reload()
                            rolesEditForm.modal('hide')
                        } else {
                            alert('Ошибка')
                            throw Error('Что-то пошло не так: ' + resp.status)
                        }
                    })
                });
            })
        })
}