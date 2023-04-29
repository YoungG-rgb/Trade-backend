const $_BASE_ROOT_URL = /*[[@{/}]]*/ '/';

const userAddModal = $('')
// const userAddModal = $('')
// const userAddModal = $('')
// const userAddModal = $('')

// load all users
$(document).ready(() => {
    debugger
    $('#reloadFilterButton').click(function (e) {
        e.preventDefault();
        $('#users-table').DataTable().ajax.reload()
    });
    initQrGroupTable()
})
function initQrGroupTable() {
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
                debugger;
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
            {data: 'id', render: formatActionForAuditableColumns, orderable: false}
        ]
    })
}
function formatActionForAuditableColumns(data, type, row) {
    return `<td>
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#groupInfoForm"
                onclick="loadAndShowModalInfoForm(${row['id']})">
                    <i class="bi bi-info-circle"></i>
                </button>

                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#groupEditForm"
                onclick="loadAndShowModalEditForm(${row['id']})"> <i class="bi bi-pencil-square"></i> 
                </button>

                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#groupDeleteForm"
                    onclick="loadAndShowDeleteModal(${row['id']})"> <i class="bi bi-trash3"></i>
                </button>
            </td>`
}