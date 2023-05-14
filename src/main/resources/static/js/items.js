const itemsAddForm = $('#itemAddForm')
const itemsEditForm = $('#itemEditForm')

$(document).ready(() => {
    $('#reloadItemsFilterButton').click(function (e) {
        e.preventDefault();
        $('#items-table').DataTable().ajax.reload()
    });
    initItemsTable()
})
function initItemsTable() {
    $('#items-table').DataTable({
        "autoWidth": false,
        serverSide: true,
        responsive: true,
        searching: false,
        searchDelay: 500,
        scrollX: false,
        language: DATATABLES_RU_LANGUAGE_CONFIG,
        "ajax": {
            "url": `${$_BASE_ROOT_URL}admin/items/filter`,
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "dataType": 'json',
            "data": function (d) {
                let extendedData = $("#searchItemForm").serializeAllJson()
                let searchData = $.extend({}, d, extendedData);
                return JSON.stringify(searchData)
            }
        },
        columns: [
            {data: 'id', orderable: true},
            {data: 'name', orderable: true},
            {data: 'imageModels', render: convertPhoto, orderable: false},
            {data: 'count', orderable: true},
            {data: 'description', orderable: false},
            {data: 'dialColor', orderable: false},
            {data: 'glass', orderable: false},
            {data: 'price', orderable: true},
            {data: 'rating', orderable: true},
            {data: 'standardBatteryLife', orderable: false},
            {data: 'straps', orderable: false},
            {data: 'waterResistance', orderable: false},
            {data: 'id', render: formatActionForItemsColumns, orderable: false}
        ]
    })
}

function formatActionForItemsColumns(data, type, row){
    return `<td>
                <button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#itemEditForm"
                onclick="loadAndShowItemEditForm(${row['id']})"> <i class="bi bi-pencil-square"></i> 
                </button>

                <button type="button" class="btn btn-danger" onclick="deleteItem(${row['id']})"> <i class="bi bi-trash3"></i> </button>
            </td>`
}

function convertPhoto(data, type, row) {
    debugger;
    if (row.imageModels.length === 0) {
        return `<td>empty</td>`
    } else {
        return `<td>
                <img class="d-block w-100" src="data:image/png;base64,${data.picture}"  alt="empty">
            </td>`
    }
}

function addNewItem(){
    let itemColor;
    let colorOptions = document.querySelector('#selectedColors').options
    for (let i = 0; i < colorOptions.length; i++) {
        if (colorOptions[i].selected) {
            itemColor = colorOptions[i].value
        }
    }

    fetch(new Request('/admin/items', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                'name' : itemsAddForm.find('#itemName').val(),
                'count' : itemsAddForm.find('#itemCount').val(),
                'description' : itemsAddForm.find('#itemDescription').val(),
                'dialColor' : itemColor,
                'glass' : itemsAddForm.find('#itemGlass').val(),
                'price' : itemsAddForm.find('#itemPrice').val(),
                'rating' : itemsAddForm.find('#rating').val(),
                'standardBatteryLife' : itemsAddForm.find('#batteryItem').val(),
                'straps' : itemsAddForm.find('#straps').val(),
                'waterResistance' : itemsAddForm.find('#waterResistance').val(),
            })
        })
    ).then(resp => {
        if (resp.ok) {
            $('#items-table').DataTable().ajax.reload()
            itemsAddForm.modal('hide')
        } else {
            alert('Ошибка')
        }
    })
}
function deleteItem(id) {
    fetch(new Request('/admin/items/' + id, { method: 'DELETE', headers: {'content-type': 'application/json'}}))
        .then(function () {
            $('#items-table').DataTable().ajax.reload()
        });
}
function loadAndShowItemEditForm(id) {
    fetch('/admin/items/' + id, {method: 'GET'})
        .then(function (response) {
            response.json().then(function (item) {
                let images = item.imageModels;
                let color = item.dialColor;
                itemsEditForm.find('#itemEditId').val(id);
                itemsEditForm.find('#itemEditName').val(item.name);
                itemsEditForm.find('#itemEditCount').val(item.count);
                itemsEditForm.find('#itemEditDescription').val(item.description);
                itemsEditForm.find('#itemEditGlass').val(item.glass);
                itemsEditForm.find('#itemEditPrice').val(item.price);
                itemsEditForm.find('#ratingEdit').val(item.rating);
                itemsEditForm.find('#batteryItemEdit').val(item.standardBatteryLife);
                itemsEditForm.find('#strapsEdit').val(item.straps);
                itemsEditForm.find('#waterResistanceEdit').val(item.waterResistance);

                $(document.getElementById('editItemButton')).on('click', async function () {
                    fetch(new Request('/admin/items', {
                        method: 'PUT',
                        headers: {'content-type': 'application/json'},
                        body: JSON.stringify({
                            'id': itemsEditForm.find('#itemEditId').val(),
                            'name': itemsEditForm.find('#itemEditName').val(),
                            'count': itemsEditForm.find('#itemEditCount').val(),
                            'description': itemsEditForm.find('#itemEditDescription').val(),
                            'glass': itemsEditForm.find('#itemEditGlass').val(),
                            'price': itemsEditForm.find('#itemEditPrice').val(),
                            'rating': itemsEditForm.find('#ratingEdit').val(),
                            'standardBatteryLife': itemsEditForm.find('#batteryItemEdit').val(),
                            'straps': itemsEditForm.find('#strapsEdit').val(),
                            'waterResistance': itemsEditForm.find('#waterResistanceEdit').val(),
                            'imageModels' : images,
                            'dialColor' : color
                        })
                    })).then(resp => {
                        if (resp.ok) {
                            $('#items-table').DataTable().ajax.reload()
                            itemsEditForm.modal('hide')
                        } else {
                            alert('Ошибка')
                            throw Error('Что-то пошло не так: ' + resp.status)
                        }
                    })
                });
            })
        })
}
