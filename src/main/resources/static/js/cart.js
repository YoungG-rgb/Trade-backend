const userOrders = $('#userItems-table');
const userCoupons = $('#couponsByUserId');
let $liveToast = $('#liveToast');

$(document).ready(() => {
    let userId = $('#userId').val();
    console.log(userId)
    if (userId) {
        fetch('/api/order/' + userId)
            .then(function (response) {
                response.json().then(function (orders) {
                    let orderModel = orders.result;
                    if (orderModel !== null) {
                        let itemArray = orderModel.items;
                        for (let i = 0; i < itemArray.length; i++) {
                            userOrders.append(
                                `<div class="card mb-3">
                                        <div class="row no-gutters">
                                            <div class="col-md-5">
                                                <img style="width: 124.16px; height: 137.11px;" 
                                                    src="data:image/png;base64,${itemArray[i].imageModels[0].picture}"  alt="empty">
                                            </div>
                                            <div class="col-md-7">
                                                <div class="card-body">
                                                    <h5 class="card-title">${itemArray[i].name}</h5>
                                                    <p class="card-text">${itemArray[i].price}с</p>
                                                    <p style="width: 20px;
                                                              height: 20px;
                                                              border-radius: 7.40653px;
                                                              background-color: ${itemArray[i].colorCode}">
                                                              
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`
                            )
                        }
                        console.log(orderModel.total)
                        $('#total-price-order').val(orderModel.total + 'c')
                    }
                })
            })


        fetch('/api/coupons/' + userId)
            .then(function (response) {
                response.json().then(function (responseModel) {
                    let couponModels = responseModel.result;
                    if (couponModels !== null && couponModels.length > 0) {
                        for (let i = 0; i < couponModels.length; i++) {
                            userCoupons.append(`
                                <tr>
                                    <td>${couponModels[i].uuid}</td>
                                    <td>${couponModels[i].bonus}</td>
                                    <td>
                                        <button 
                                            type="button" 
                                            style="background: #735CFF; border: none" 
                                            onclick="applyFreeCoupon('${couponModels[i].uuid}', ${userId})"
                                        > 
                                        <i class="bi bi-plus"></i> 
                                        </button>
                                    </td>
                                </tr>   
                            `)
                        }
                    }
                })
            }).catch(function (response) {
                console.error(response)
        })
    }

})

function applyFreeCoupon(uuid, userId) {
    fetch(new Request('/api/coupons/' + userId + '?couponUuid=' + uuid, {
        method: 'POST',
        headers: {'content-type': 'application/json'}
    }))
        .then(function (response) {
            response.json().then(function (couponModel) {
                let message = 'Получено ' + couponModel.result.bonus + 'бонусов'
                $('#view-user-coupons').modal('hide')
                showResponse(message)
            })
        });
}

function showResponse(message) {
    $('#body-message').val(message)
    $liveToast.toast('show')
    $liveToast.on('hidden.bs.toast', function () {
        location.reload()
    })
}