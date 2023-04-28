(function ($) {
    /** Сериализует форму, включая disabled поля */
    $.fn.serializeAll = function () {
        var data = $(this).serializeArray();

        $(':disabled[name]', this).each(function () {
            data.push({name: this.name, value: $(this).val()});
        });

        return $.param(data);
    }

    /** Сериализует форму в JSON, включая disabled поля */
    $.fn.serializeAllJson = function () {
        let data = $(this).serializeArray();
        let multiples = []
        $('select[multiple]',this).each(function(){ // собираем имена полей формы с мультивыбором
            multiples.push(this.name)
        })

        $(':disabled[name]', this).each(function () {
            data.push({name: this.name, value: $(this).val()});
        });

        $('input[type="checkbox"]:not(:checked)', this).each(function () {
            data.push({name: this.name, value: false});
        });

        $('input[type="checkbox"]:checked', this).each(function () {
            data.push({name: this.name, value: true});
        });

        let result = {}
        $.map(data, function(n, i){
            let key = n['name'];
            let value = n['value'];

            // Если есть поля с мультивыбором, то нужно сделать массивом
            if (multiples.includes(key)) {
                if (Array.isArray(result[key])) {
                    result[key].push(value);
                } else {
                    let arrayValue = [];
                    arrayValue.push(value)
                    result[key] = arrayValue;
                }
            } else {
                result[key] = value
            }
        });

        return result;
    }

})(jQuery);

const DATATABLES_RU_LANGUAGE_CONFIG = {
    "processing": "Подождите...",
    "search": "Поиск:",
    "lengthMenu": "Показать _MENU_ записей",
    "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
    "infoEmpty": "Записи с 0 до 0 из 0 записей",
    "infoFiltered": "(отфильтровано из _MAX_ записей)",
    "loadingRecords": "Загрузка записей...",
    "zeroRecords": "Записи отсутствуют.",
    "emptyTable": "В таблице отсутствуют данные",
    "paginate": {
        "first": "Первая",
        "previous": "Предыдущая",
        "next": "Следующая",
        "last": "Последняя"
    },
    "aria": {
        "sortAscending": ": активировать для сортировки столбца по возрастанию",
        "sortDescending": ": активировать для сортировки столбца по убыванию"
    },
    "select": {
        "rows": {
            "_": "Выбрано записей: %d",
            "0": "Кликните по записи для выбора",
            "1": "Выбрана одна запись"
        }
    }
}
