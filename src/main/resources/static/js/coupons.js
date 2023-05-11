let couponsAddForm = $('#couponsAddForm')

$(document).ready(() => {
    $('#reloadCouponsFilterButton').click(function (e) {
        e.preventDefault();
        $('#coupons-table').DataTable().ajax.reload()
    });
    initCouponsTable()
})
function initCouponsTable() {
    $('#coupons-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}admin/coupons/filter`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchCouponsForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'id', orderable: true},
            {data: 'uuid', orderable: false},
            {data: 'bonus', orderable: false},
            {data: 'valid', orderable: false},
            {data: 'id', render: formatActionForCouponsColumns, orderable: false}
        ]
    })
}

function formatActionForCouponsColumns(data, type, row){
    return `<td>
                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#itemEditForm"
                onclick="loadAndShowItemEditForm(${row['id']})"> <i class="bi bi-pencil-square"></i> 
                </button>

                <button type="button" class="btn btn-danger" onclick="deleteCoupons(${row['id']})"> <i class="bi bi-trash3"></i> </button>
            </td>`
}

function deleteCoupons(id) {
    fetch(new Request('/admin/coupons/' + id, { method: 'DELETE', headers: {'content-type': 'application/json'}}))
        .then(function () {
            $('#coupons-table').DataTable().ajax.reload()
        });
}
function addNewCoupon(){
    let couponBonus = couponsAddForm.find('#bonusCoupon').val()

    fetch(new Request('/admin/coupons/' + couponBonus, {
            method: 'POST',
            headers: {'content-type': 'application/json'}
        })
    ).then(resp => {
        if (resp.ok) {
            $('#coupons-table').DataTable().ajax.reload()
            couponsAddForm.modal('hide')
        } else {
            alert('Ошибка')
        }
    })
}