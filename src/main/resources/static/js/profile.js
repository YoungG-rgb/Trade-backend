const profileForm = $('#profileForm')

function updateProfile() {
    fetch(new Request('/admin/users/update', {
        method: 'PUT',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({
            'id': profileForm.find('#user-id').val(),
            'username' : profileForm.find('#login').val(),
            'password' : profileForm.find('#exampleInputPassword1').val(),
            'email' : profileForm.find('#exampleInputEmail1').val(),
            'balance' : profileForm.find('#prof_balance').val(),
            'updatedAt' : profileForm.find('#updatedAt').val(),
            'createdAt' : profileForm.find('#createdAt').val(),
            'addressModel' : {
                'id' : profileForm.find('#address_id').val(),
                'town' : profileForm.find('#address_town').val(),
                'street' : profileForm.find('#address_street').val(),
                'houseNumber' : profileForm.find('#address_houseNumber').val(),
                'houseType' : profileForm.find('#address_houseType').val()
            },
            'creditCardModel' : {
                'id' : profileForm.find('#card_id').val(),
                'cardNumber' : profileForm.find('#card_number').val(),
                'expiryDate' : profileForm.find('#card_expiryDate').val(),
                'cvc_and_cvv' : profileForm.find('#card_cvc').val()
            }
        })
    })).then(resp => {
        if (resp.ok) {
            alert('Успешно')
            location.reload()
        } else {
            alert('Ошибка')
            throw Error('Что-то пошло не так: ' + resp.status)
        }
    })
}

function addNewNumber(){
    fetch(new Request('/api/phones/' + profileForm.find('#user-id').val(), {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({
            'number' : profileForm.find('#newUserNumber').val(),
        })
    })).then(resp => {
        if (resp.ok) {
            alert('Успешно')
            location.reload()
        } else {
            alert('Ошибка')
            throw Error('Что-то пошло не так: ' + resp.status)
        }
    })
}