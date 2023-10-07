// Функция для проверки валидности URL
function isValidURL(url) {
    try {
        new URL(url);
        return true;
    } catch (error) {
        return false;
    }
}

// Функция, которая будет вызвана при отправке формы
function submitLinkForm(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы (перезагрузку страницы)

    // Получаем данные из формы
    const originalUrl = document.getElementById('original-url').value;

    // Проверяем валидность URL
    if (!isValidURL(originalUrl)) {
        alert('Пожалуйста, введите корректный URL.');
        return;
    }

    // Создаем объект данных для отправки на сервер
    const data = {
        originalUrl: originalUrl
    };

    // Отправляем POST-запрос на сервер
    fetch('/create-edit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            // Обработка ответа от сервера
            if (data.shortUrl) {
                alert('Короткая ссылка создана: ' + data.shortUrl);
                // Дополнительная логика, например, перенаправление пользователя
                // window.location.href = '/create-edit';
            } else {
                alert('Произошла ошибка при создании ссылки.');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

// Получаем форму и добавляем обработчик события на отправку
const linkForm = document.getElementById('link-form');
linkForm.addEventListener('submit', submitLinkForm);
