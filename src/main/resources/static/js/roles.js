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