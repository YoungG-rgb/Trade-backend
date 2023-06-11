let $product_toast = $('#product-toast');
let $product_toast_body = $('#product-toast-body');
let selectedColor = '';
const $_BASE_ROOT_URL = /*[[@{/}]]*/ '/';

function fillCustomColor(color, style) {
    $product_toast_body.empty()
    $product_toast_body.append(`<span>Выбран</span> <div style="${style}"></div>`)
    $product_toast.toast('show')
    selectedColor = color
}

$(document).ready(() => {
    $('#reloadItemsExtFilterButton').click(function (e) {
        e.preventDefault();
        $('#selectedColor').val(selectedColor)
        $('#ext-items-table').DataTable().ajax.reload()
        selectedColor = ''
    });
    initExtItemsTable()
})

function initExtItemsTable(){
    $('#ext-items-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}ext-api/items/filter`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchExtItemForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'name', orderable: true},
            {data: 'imageModels', render: convertPhoto, orderable: false},
            {data: 'price', orderable: false},
            {data: 'id', render: getInfo, orderable: false}
        ]
    })
}
function getInfo(data, type, row) {
    return `<td>
                <div>${row.info}</div>
            </td>`
}

function convertPhoto(data, type, row) {
    if (row.imageModels.length === 0) {
        return `<td>empty</td>`
    } else {
        debugger;
        return `<td>
                <img style="width: 337px; height: 320px" src="data:image/png;base64,${row.imageModels[0].picture}"  alt="empty">
            </td>`
    }
}

function addToCart(id) {
    fetch(new Request('/api/carts/' + id, {method: 'POST', headers: {'content-type': 'application/json'}})
    ).then(resp => {
        if (resp.ok) {
            $product_toast_body.empty()
            $product_toast_body.append(`
                <i class="bi bi-check2-square"></i>
                <span>Успешно добавлено в корзину</span>
            `)

            $product_toast.toast('show')
        } else {
            alert('Ошибка')
        }
    })
}