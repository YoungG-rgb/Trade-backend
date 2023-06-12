const userOrders = $('#userItems-table');
const userCoupons = $('#couponsByUserId');
const orderHistory = $('#orderHistory');
let $liveToast = $('#liveToast');
let $liveToastError = $('#liveToastError');
let orderId;

$(document).ready(() => {
    let userId = $('#userId').val();
    if (userId) {
        fetch('/api/order/' + userId)
            .then(function (response) {
                response.json().then(function (orders) {
                    let orderModel = orders.result;
                    orderId = orderModel.id;
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
                        $('#total-price-order').val(orderModel.total + 'c')

                        const histories = orderModel.history.split('<br>');
                        for (let i = 0; i < histories.length; i++) {
                            orderHistory.append(`<tr>
                                                    <td>
                                                        <p>${histories[i]}</p>
                                                    </td>
                                                </tr>`)
                        }
                    } else {
                        userOrders.append(
                            `<div class="card mb-3">
                                <div class="row no-gutters">
                                    <h1>Корзина пуста</h1>
                                </div>
                            </div>`
                        )
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
                if (couponModel.resultCode === 'EXCEPTION') {
                    let message = 'Ошибка, у вас нет товаров в корзине';
                    $('#view-user-coupons').modal('hide')
                    showError(message)
                } else {
                    let message = 'Получено ' + couponModel.result.bonus + 'бонусов'
                    $('#view-user-coupons').modal('hide')
                    showSuccess(message)
                }
            })
        })
}

function showSuccess(message) {
    $('#body-message').val(message)
    $liveToast.toast('show')
    $liveToast.on('hidden.bs.toast', function () {
        location.reload()
    })
}
function showError(message) {
    $('#body-message-error').val(message)
    $liveToastError.toast('show')
    $liveToastError.on('hidden.bs.toast', function () {
        location.reload()
    })
}

function completeOrder(payMethod) {
    let transport;
    if ($('#ups-express').is(":checked")) {
        transport = 'PLANE'
    } else {
        transport = 'MACHINE'
    }

    fetch(new Request('/api/order/apply-order', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify({
                'orderId': orderId,
                'userId': $('#userId').val(),
                'paymentMethod': payMethod,
                'transport': transport
            })
        })
    ).then(function (response) {
        response.json().then(function (responseModel) {
            if (responseModel.result === 'SUCCESS') {
                $('#bakai-info').modal('hide')
                alert('Заказ успешно оформлен')
                location.reload()
            } else {
                $('#bakai-info').modal('hide')
                alert('Ошибка, проверьте корректность данных')
                location.reload()
            }
        })
    })
}