const $_BASE_ROOT_URL = /*[[@{/}]]*/ '/';

$(document).ready(() => {
    $('#my-orders-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}api/order/my-orders`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchOrderForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'createdAt', orderable: false},
            {data: 'deliverDate', orderable: false},
            {data: 'total', orderable: true},
            {data: 'paymentMethod', orderable: false},
            {data: 'status', orderable: false},
            {data: 'transport', orderable: false},
            {data: 'history', orderable: false},
            {data: 'id', render: renderMap, orderable: false}
        ]
    })
})

function renderMap(data, type, row){
    return `<td>
                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#loadMap" onclick="loadMap(${row['id']})"> 
                    <i class="bi bi-globe-americas"></i>
                </button>
            </td>`
}

function loadMap(id) {

}
