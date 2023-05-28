const userOrders = $('#userItems-table');

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
                                                    <p class="card-text"><small class="text-muted">${itemArray[i].dialColor}</small></p>
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
    }

})