const carousel = $('#custom-carousel-id');

$(document).ready(() => {
    fetch(new Request('/ext-api/home', { method: 'GET', headers: {'content-type': 'application/json'}}))
        .then(function (res) {
            res.json().then(function(responseModel) {
                let items = responseModel.result

                carousel.append(
                    `<div class="carousel-item active ">
                        <img class="custom-carousel-style" src="data:image/png;base64,${items[0].imageModels[0].picture}"  alt="empty">
                        <div>
                            <span style="color: #939393; line-height: 12px;font-size: 16px;">${items[0].name}</span>
                            <br>
                            <span style="color: #000000;font-size: 26px;line-height: 25px;">${items[0].price}</span>
                        </div>
                    </div>`
                )
                for (let i = 1; i < items.length; i++) {
                    carousel.append(
                        `<div class="carousel-item ">
                            <img class="custom-carousel-style" src="data:image/png;base64,${items[i].imageModels[0].picture}"  alt="empty">
                            <div>
                                <span style="color: #939393; line-height: 12px;font-size: 16px;">${items[i].name}</span>
                                <br>
                                <span style="color: #000000;font-size: 26px;line-height: 25px;">${items[i].price} c</span>
                            </div>
                        </div>`
                    )
                }

            })
        });
})

function addNewUser(){
    const userModel = {
        username: $('#regName').val(),
        password: $('#regPassword').val(),
        email: $('#regEmail').val(),
        roleModels: [
            {
                id: '2',
                name: 'ROLE_USER'
            }
        ]
    }

    fetch(new Request('/ext-api/users', {
            method: 'POST',
            headers: {'content-type': 'application/json'},
            body: JSON.stringify(userModel)
        })
    ).then(resp => {
        if (resp.ok) {
            alert('Поздравляю, вы успешно зарегистрировались! логин и пароль высланы по почте')
        } else {
            alert('технические проблемы с сервером')
        }
    })
}